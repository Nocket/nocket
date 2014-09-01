package dmdweb.component.table.columns;

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.model.IModel;

public abstract class DMDAbstractColumn<T> extends AbstractColumn<T, String> implements IDMDStyledColumn<T, String> {

    private static final long serialVersionUID = 1L;

    private String columStyle;

    public DMDAbstractColumn(IModel<String> displayModel) {
        super(displayModel);
    }

    public DMDAbstractColumn(final IModel<String> displayModel, final String sortProperty) {
        super(displayModel, sortProperty);
    }

    public void setColumnStyle(String style) {
        this.columStyle = style;
    }

    @Override
    public String getCssStyleAttribute() {
        return columStyle;
    }

}
