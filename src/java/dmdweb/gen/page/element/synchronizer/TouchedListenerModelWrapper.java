package dmdweb.gen.page.element.synchronizer;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.lang.Objects;

import dmdweb.gen.page.element.PageElementI;
import dmdweb.gen.page.guiservice.TouchedListener;

@SuppressWarnings("serial")
public class TouchedListenerModelWrapper<E> implements IModel<E> {

    private final Set<IModel<? extends TouchedListener>> listeners = new HashSet<IModel<? extends TouchedListener>>();
    private final SynchronizerHelper helper;
    private final IModel<E> delegate;
    private boolean touched;

    private State<E> preservedState;

    public TouchedListenerModelWrapper(PageElementI<E> e, IModel<E> delegate) {
        this.helper = new SynchronizerHelper(e);
        this.delegate = delegate;
        e.getContext().getTouchedRegistryData().registerModel(this);

        preserveState(null);
    }

    protected State<E> preserveState(Component object) {
        return preservedState = StateFactory.create(this, object, helper);
    }

    public boolean registerTouchedListener(IModel<? extends TouchedListener> listener) {
        return listeners.add(listener);
    }

    public boolean unregisterTouchedListener(IModel<? extends TouchedListener> listener) {
        return listeners.remove(listener);
    }

    public String getWicketId() {
        return helper.getWicketId();
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        boolean changed = this.touched != touched;
        this.touched = touched;
        if (changed) {
            for (IModel<? extends TouchedListener> l : listeners) {
                if (touched) {
                    l.getObject().touched(helper.getWicketId());
                } else {
                    l.getObject().untouched(helper.getWicketId());
                }
            }
        }
    }

    @Override
    public void detach() {
        delegate.detach();
    }

    @Override
    public E getObject() {
        E object = delegate.getObject();
        return object;
    }

    /**
     * @return true if object changed between the begin of the request and
     *         construction of the response
     */
    public boolean modelChangedBetweenRequestProcessing(Component component) {
        return !StateFactory.create(this, component, helper).equals(preservedState);
    }

    @Override
    public void setObject(E object) {
        if (!touched && !modelUnchanged(object)) {
            setTouched(true);
        }

        /**
         * Dies ist ein halbgarer Workaround. Bisher stand an dieser Stelle der
         * direkte zugriff auf den Delegate "delegate.setObject(object);". Dies
         * funktioniert zwar immer, aber ist nicht korrekt. Ist ein Setter
         * intercepted, so wuerde die Interception ignoriert. Für die Fälle,
         * dass der Delegate ein PropertyModel ist, d.h. direkt auf das Model
         * zugegriffen wird, wird nun der SynchronizerHelper benutzt. In den
         * Fällen, dass es ein anderes Model ist, wird gehofft, dass diese
         * Aufgabe das "andere" Model sprich das Delegate uebernimmt.<br>
         * Offen:<br>
         * - FileDownloadElement<br>
         * - LinkElement<br>
         * - RepeatingPanelElement<br>
         * - TableElement<br>
         * Bei diesen Elementen wuerder ein Intercepted des Setters nicht
         * funktionieren.
         */
        if (delegate instanceof PropertyModel<?>) {
            helper.invokeSetterMethod(object);
        } else {
            delegate.setObject(object);
        }
    }

    private boolean modelUnchanged(E newValue) {
        E oldValue = getObject();
        return modelUnchanged(oldValue, newValue);
    }

    boolean modelUnchanged(E oldValue, E newValue) {
        String format = helper.getFormat();
        if (helper.isBooleanType()) {
            boolean oldValueFormatted = BooleanUtils.isTrue((Boolean) oldValue);
            boolean newValueFormatted = BooleanUtils.isTrue((Boolean) newValue);
            return Objects.equal(oldValueFormatted, newValueFormatted);
        }
        if (format != null) {
            if (helper.isNumberType()) {
                String oldValueFormatted = formatNumberNullSafe(format, oldValue);
                String newValueFormatted = formatNumberNullSafe(format, newValue);
                return Objects.equal(oldValueFormatted, newValueFormatted);
            } else if (helper.isDateType()) {
                String oldValueFormatted = formatDateNullSafe(format, oldValue);
                String newValueFormatted = formatDateNullSafe(format, newValue);
                return Objects.equal(oldValueFormatted, newValueFormatted);
            }
        }
        return Objects.equal(oldValue, newValue);
    }

    private String formatNumberNullSafe(String format, E value) {
        if (value == null) {
            return null;
        }
        DecimalFormat df = new DecimalFormat(format);
        return df.format(value);
    }

    private String formatDateNullSafe(String format, E value) {
        if (value == null) {
            return null;
        }
        FastDateFormat df = FastDateFormat.getInstance(format);
        return df.format(value);
    }

    protected IModel<E> getDelegate() {
        return delegate;
    }
}
