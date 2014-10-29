package org.nocket.component.form;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IObjectClassAwareModel;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.string.Strings;
import org.nocket.gen.domain.WebDomainProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * In Wicket werden Parameter bevor sie in den Setter geschrieben werden
 * getrimmt und wenn es dann ein Leestring ist zu NULL gemacht. Dies ist nur
 * bedingt gut. Es gibt doch Projekte, die ihre Daten, auch wenn sie schlecht
 * sind, behalten wollen. Allerdings kann es sinnvoll sein einen richtigen
 * Leerstring zu NULL zu wandeln. Diese EntitÃ€t liesst den Defaultwert fÃŒr das
 * Verhalten aus den gengui.properties (InputStringConvertStrategy). GeÃ€ndert
 * kann die verwendete Strategie mit der Methode <code>
 * setConvertInputStringModus(InputStringConvertStrategy convertInputStringModus)
 * </code> FÃŒr die InputStringConvertStrategy gibt es folgende MÃ¶glichkeiten:<br>
 * <ul>
 * <li>none = keine Konvertiertung</li>
 * <li>trimAndEmptyToNull = nur Leerstrings werden zu null (kein Trim)</li>
 * <li>noTrimButEmptyToNull = Trimmern und danach einen Leerstring zu null
 * konvertieren</li>
 * </ul>
 */
public class DMDTextField<T> extends TextField<T> {

    public enum InputStringConvertStrategy {
        none, trimAndEmptyToNull, noTrimButEmptyToNull
    }

    private static final long serialVersionUID = 1L;
    private boolean convertBlankInputStringToNull;

    /** Log for reporting. */
    private static final Logger log = LoggerFactory.getLogger(DMDTextField.class);

    public DMDTextField(String id, Class<T> type) {
        super(id, type);
        setConvertInputStringModus(new WebDomainProperties().getInputStringConvertStrategy());
    }

    public DMDTextField(String id, IModel<T> model, Class<T> type) {
        super(id, model, type);
        setConvertInputStringModus(new WebDomainProperties().getInputStringConvertStrategy());
    }

    public DMDTextField(String id, IModel<T> model) {
        super(id, model);
        setConvertInputStringModus(new WebDomainProperties().getInputStringConvertStrategy());
    }

    public DMDTextField(String id) {
        super(id);
        setConvertInputStringModus(new WebDomainProperties().getInputStringConvertStrategy());
    }

    public void setConvertInputStringModus(InputStringConvertStrategy convertInputStringModus) {
        if (convertInputStringModus == null || InputStringConvertStrategy.none.equals(convertInputStringModus)) {
            setConvertBlankInputStringToNull(false);
            setConvertEmptyInputStringToNull(false);
        } else if (InputStringConvertStrategy.noTrimButEmptyToNull.equals(convertInputStringModus)) {
            setConvertBlankInputStringToNull(true);
            setConvertEmptyInputStringToNull(false);
        } else if (InputStringConvertStrategy.trimAndEmptyToNull.equals(convertInputStringModus)) {
            setConvertBlankInputStringToNull(false);
            setConvertEmptyInputStringToNull(true);
        }
    }

    public void setConvertBlankInputStringToNull(boolean trimInputString) {
        this.convertBlankInputStringToNull = trimInputString;
    }

    /**
     * Subclasses should overwrite this if the conversion is not done through
     * the type field and the {@link IConverter}. <strong>WARNING: this method
     * may be removed in future versions.</strong>
     * 
     * If conversion fails then a ConversionException should be thrown
     * 
     * @param value
     *            The value can be the getInput() or through a cookie
     * 
     * @return The converted value. default returns just the given value
     * @throws ConversionException
     *             If input can't be converted
     */
    @SuppressWarnings("unchecked")
    protected T convertValue(String[] value) throws ConversionException
    {
        if (value == null || value.length <= 0 || value[0] == null) {
            return null;
        }

        if (!convertBlankInputStringToNull) {
            return (T) (value[0].length() != 0 ? value[0] : null);
        }
        return (T) trim(value[0]);
    }

    /**
     * Convert the input respecting the flag convertEmptyInputStringToNull.
     * Subclasses that override this method should test this flag also.
     * 
     * @see org.apache.wicket.markup.html.form.FormComponent#convertInput()
     */
    @Override
    protected void convertInput()
    {
        // Stateless forms don't have to be rendered first, convertInput could be called before
        // onBeforeRender calling resolve type here again to check if the type is correctly set.
        resolveType();
        String[] value = getInputAsArray();
        String tmp = value != null && value.length > 0 ? value[0] : null;

        if (convertBlankInputStringToNull && StringUtils.isBlank(tmp))
        {
            setConvertedInput(null);
        }
        else if (getConvertEmptyInputStringToNull() && Strings.isEmpty(tmp))
        {
            setConvertedInput(null);
        }
        else
        {
            super.convertInput();
        }
    }

    // Kopie der Wicket-Methode resolveType aus AbstractTextComponent, die private ist.
    private static final int TYPE_RESOLVED = Component.FLAG_RESERVED4;

    /**
     * Kopie der Wicket-Methode resolveType aus AbstractTextComponent, die
     * private ist.
     */
    private void resolveType()
    {
        if (!getFlag(TYPE_RESOLVED) && getType() == null)
        {
            Class<?> type = getModelType(getDefaultModel());
            setType(type);
            setFlag(TYPE_RESOLVED, true);
        }
    }

    /**
     * Kopie der Wicket-Methode resolveType aus AbstractTextComponent, die
     * private ist.
     * 
     * @param model
     * @return the type of the model object or <code>null</code>
     */
    private Class<?> getModelType(IModel<?> model)
    {
        if (model instanceof IObjectClassAwareModel)
        {
            Class<?> objectClass = ((IObjectClassAwareModel<?>) model).getObjectClass();
            if (objectClass == null)
            {
                log.warn("Couldn't resolve model type of " + model + " for " + this +
                        ", please set the type yourself.");
            }
            return objectClass;
        }
        else
        {
            return null;
        }
    }

    protected boolean shouldTrimInput()
    {
        return getConvertEmptyInputStringToNull();
    }
}
