package forscher.nocket.page.gen;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.nocket.component.form.behaviors.DefaultFocusBehavior;
import org.nocket.gen.page.GeneratedBinding;
import org.nocket.gen.page.element.PageElementI;
import org.nocket.gen.page.element.TableElement;
import org.nocket.gen.page.inject.PageComponent;
import org.nocket.gen.page.visitor.bind.builder.BindingInterceptor;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedGenericDataTableFactory;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedNumberTextField;

import forscher.nocket.page.ForscherPage;

@SuppressWarnings("serial")
public class GeneratedPage extends ForscherPage {

    private static final String ID_SEPERATE_FORM = "seperateForm";

    private static final String ID_SOMETHING_UNKNOWN = "somethingUnknown";

    private static final String ID_FILE_DOWNLOAD = "Download";

    @PageComponent(GeneratedConstants.Merge_IchBinDasSelbeFeldReadOnly)
    private Component readOnlyComponent;

    @PageComponent(GeneratedConstants.Credential)
    private FormComponent credential;

    private Component Merge_IchBinDasSelbeFeldDasZweiteMalReadOnly;

    private GeneratedNumberTextField<Integer> blueValue = new GeneratedNumberTextField<Integer>(
            GeneratedConstants.BlueValue) {
        @Override
        protected void onComponentTag(ComponentTag tag) {
            super.onComponentTag(tag);
            Integer value = (Integer) getDefaultModelObject();
            if (value != null) {
                tag.put("style", "font-weight:bold;color:rgb(0, 0," + value + ")");
            }
        }
    };

    public GeneratedPage() {
        this(Model.of(new Generated()));
    }

    public GeneratedPage(final IModel<Generated> model) {
        super(model);
        GeneratedBinding generatedBinding = new GeneratedBinding(this);
        generatedBinding.withInterceptors(new BindingInterceptor() {

            @Override
            public Component create(PageElementI<?> e) {
                // add custom component
                if (e.getWicketId().equals(ID_SOMETHING_UNKNOWN)) {
                    Label label = new Label(e.getWicketId(), "Mapped Unknown Label");
                    return label;
                }
                if (e.getWicketId().equals(ID_SEPERATE_FORM)) {
                    return new GeneratedSeperatedFormPanel(ID_SEPERATE_FORM, Model.of(new GeneratedSeperatedForm()));
                }
                return null;
            }

            @Override
            public Component createTable(TableElement e) {
                return new GeneratedGenericDataTableFactory(e).withRowsPerPage(2).createTable();
            }
        });
        
        
        generatedBinding.bind();

        // fetch and modify custom component later
        Label somethingUnknownLabel = (Label) generatedBinding.getContext().getComponentRegistry()
                .getComponent(ID_SOMETHING_UNKNOWN);
        somethingUnknownLabel.add(new AttributeModifier("style", "color: green; font-size: 200%"));

        readOnlyComponent.add(new AttributeModifier("style", "border-style: solid; border-color: red;"));

        Merge_IchBinDasSelbeFeldDasZweiteMalReadOnly.add(new AttributeModifier("style",
                "border-style: solid; border-color: orange;"));

        generatedBinding.getContext().getComponentRegistry().getComponent(GeneratedConstants.RadioChoiceNullable)
                .add(new DefaultFocusBehavior());

        credential.setRequired(false);

    }
}
