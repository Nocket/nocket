package org.nocket.gen.page.element.synchronizer;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.lang.Objects;
import org.nocket.gen.page.element.PageElementI;
import org.nocket.gen.page.guiservice.TouchedListener;

// TODO: Auto-generated Javadoc
/**
 * The Class TouchedListenerModelWrapper.
 *
 * @param <E> the element type
 */
@SuppressWarnings("serial")
public class TouchedListenerModelWrapper<E> implements IModel<E> {

    /** The listeners. */
    private final Set<IModel<? extends TouchedListener>> listeners = new HashSet<IModel<? extends TouchedListener>>();
    
    /** The helper. */
    private final SynchronizerHelper helper;
    
    /** The delegate. */
    private final IModel<E> delegate;
    
    /** The touched. */
    private boolean touched;

    /** The preserved state. */
    private State<E> preservedState;

    /**
     * Instantiates a new touched listener model wrapper.
     *
     * @param e the e
     * @param delegate the delegate
     */
    public TouchedListenerModelWrapper(PageElementI<E> e, IModel<E> delegate) {
        this.helper = new SynchronizerHelper(e);
        this.delegate = delegate;
        e.getContext().getTouchedRegistryData().registerModel(this);

        preserveState(null);
    }

    /**
     * Preserve state.
     *
     * @param object the object
     * @return the state
     */
    protected State<E> preserveState(Component object) {
        return preservedState = StateFactory.create(this, object, helper);
    }

    /**
     * Register touched listener.
     *
     * @param listener the listener
     * @return true, if successful
     */
    public boolean registerTouchedListener(IModel<? extends TouchedListener> listener) {
        return listeners.add(listener);
    }

    /**
     * Unregister touched listener.
     *
     * @param listener the listener
     * @return true, if successful
     */
    public boolean unregisterTouchedListener(IModel<? extends TouchedListener> listener) {
        return listeners.remove(listener);
    }

    /**
     * Gets the wicket id.
     *
     * @return the wicket id
     */
    public String getWicketId() {
        return helper.getWicketId();
    }

    /**
     * Checks if is touched.
     *
     * @return true, if is touched
     */
    public boolean isTouched() {
        return touched;
    }

    /**
     * Sets the touched.
     *
     * @param touched the new touched
     */
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

    /* (non-Javadoc)
     * @see org.apache.wicket.model.IDetachable#detach()
     */
    @Override
    public void detach() {
        delegate.detach();
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.model.IModel#getObject()
     */
    @Override
    public E getObject() {
        E object = delegate.getObject();
        return object;
    }

    /**
     * Model changed between request processing.
     *
     * @param component the component
     * @return true if object changed between the begin of the request and
     *         construction of the response
     */
    public boolean modelChangedBetweenRequestProcessing(Component component) {
        return !StateFactory.create(this, component, helper).equals(preservedState);
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.model.IModel#setObject(java.lang.Object)
     */
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

    /**
     * Model unchanged.
     *
     * @param newValue the new value
     * @return true, if successful
     */
    private boolean modelUnchanged(E newValue) {
        E oldValue = getObject();
        return modelUnchanged(oldValue, newValue);
    }

    /**
     * Model unchanged.
     *
     * @param oldValue the old value
     * @param newValue the new value
     * @return true, if successful
     */
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

    /**
     * Format number null safe.
     *
     * @param format the format
     * @param value the value
     * @return the string
     */
    private String formatNumberNullSafe(String format, E value) {
        if (value == null) {
            return null;
        }
        DecimalFormat df = new DecimalFormat(format);
        return df.format(value);
    }

    /**
     * Format date null safe.
     *
     * @param format the format
     * @param value the value
     * @return the string
     */
    private String formatDateNullSafe(String format, E value) {
        if (value == null) {
            return null;
        }
        FastDateFormat df = FastDateFormat.getInstance(format);
        return df.format(value);
    }

    /**
     * Gets the delegate.
     *
     * @return the delegate
     */
    protected IModel<E> getDelegate() {
        return delegate;
    }
}
