/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.bootstrap2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ecs.Element;
import org.apache.ecs.html.Div;
import org.apache.ecs.html.Input;
import org.apache.ecs.html.Label;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.nocket.gen.domain.element.DomainElementI;
import org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder.Bootstrap2ButtonBuilder;
import org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder.Bootstrap2CheckBoxBuilder;
import org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder.Bootstrap2DropDownBuilder;
import org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder.Bootstrap2FeedbackPanelBuilder;
import org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder.Bootstrap2FileUploadFieldBuilder;
import org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder.Bootstrap2ImageBuilder;
import org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder.Bootstrap2LinkBuilder;
import org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder.Bootstrap2ListMultipleChoiceBuilder;
import org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder.Bootstrap2PasswordTextFieldBuilder;
import org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder.Bootstrap2RadioChoiceBuilder;
import org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder.Bootstrap2RepeatingViewBuilder;
import org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder.Bootstrap2TabbedPanelBuilder;
import org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder.Bootstrap2TablePanelBuilder;
import org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder.Bootstrap2TextAreaBuilder;
import org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder.Bootstrap2TextFieldBuilder;
import org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder.Bootstrap2WebMenuBuilder;
import org.nocket.gen.domain.visitor.html.styling.common.AbstractStylingStrategyImpl;
import org.nocket.gen.domain.visitor.html.styling.common.ButtonBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.CheckBoxBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.DropDownBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.FeedbackPanelBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.FileUploadBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.ImageBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.LinkBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.ListMultipleChoiceBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.PasswordTextFieldBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.RadioChoiceBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.RepeatingViewBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.TabbedPanelBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.TablePanelBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.TextAreaBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.TextFieldBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.WebMenuBuilderI;

/**
 * Styling-Strategie für BootStrap2 Optik
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class Bootstrap2StylingStrategy extends AbstractStylingStrategyImpl {

	@Override
	protected Element wrapComponent(DomainElementI<?> e, Element element) {
        //<div class="control-group">
        Div controlGroup = new Div();
        controlGroup.addAttribute(ATTR_CLASS, "control-group");
        if (isInputButton(element))
            ((Input) element).setValue(e.getPrompt());
        else {
            //  <label for="vertrag.Vertragsnummer" class="control-label">Salutation</label>
            Label label = createLabel(e, element);
            label.addAttribute(ATTR_CLASS, "control-label");
            controlGroup.addElement(label);
        }
        //  <div class="controls controls-row">
        Div controlsRow = new Div();
        controlsRow.addAttribute(ATTR_CLASS, "controls");
        //    <select class="span4" wicket:id="vertrag.Vertragsnummer" id="vertrag.Vertragsnummer" size="1"><option>[option]</option></select>
        controlsRow.addElement(element);
        //  </div>
        controlGroup.addElement(controlsRow);
        //</div> 
        return controlGroup;
    }
	
	@Override
	public WebMenuBuilderI getWebMenuBuilder() {
		return new Bootstrap2WebMenuBuilder();
	}

	@Override
	public FeedbackPanelBuilderI getFeedbackPanelBuilder() {
		return new Bootstrap2FeedbackPanelBuilder();
	}

	@Override
	public ListMultipleChoiceBuilderI<String> getListMultipleChoiceBuilder() {
		return new Bootstrap2ListMultipleChoiceBuilder();
	}

	@Override
	public RadioChoiceBuilderI<?> getRadioChoiceBuilder() {
		return new Bootstrap2RadioChoiceBuilder();
	}

	@Override
	public TabbedPanelBuilderI<ITab> getTabbedPanelBuilder() {
		return new Bootstrap2TabbedPanelBuilder();
	}

	@Override
	public TablePanelBuilderI<?> getTablePanelBuilder() {
		return new Bootstrap2TablePanelBuilder();
	}

	
	@Override
	public List<CssResourceReference> getCssStyleFiles() {
		return new ArrayList<CssResourceReference>() {{
			add(new CssResourceReference(getClass(), "resources/css/bootstrap.min.css"));
		}};
	}
	
	@Override
	public List<JavaScriptResourceReference> getJavascriptFiles() {
		return new ArrayList<JavaScriptResourceReference>() {{
			add(new JavaScriptResourceReference(getClass(), "resources/js/bootstrap.min.js"));
		}};
	}
	
	@Override
	public Map<String, ResourceReference> getGlobalResources() {
		return new HashMap<String, ResourceReference>() {{
			put("/img/glyphicons-halflings-white.png", new PackageResourceReference(getClass(), "resources/img/glyphicons-halflings-white.png"));
			put("/img/glyphicons-halflings.png", new PackageResourceReference(getClass(), "resources/img/glyphicons-halflings.png"));
		}};
	}

	@Override
	public TextFieldBuilderI getTextFieldBuilder() {
		return new Bootstrap2TextFieldBuilder();
	}

	@Override
	public FileUploadBuilderI getFileUploadBuilder() {
		return new Bootstrap2FileUploadFieldBuilder();
	}

	@Override
	public PasswordTextFieldBuilderI getPasswordTextFieldBuilder() {
		return new Bootstrap2PasswordTextFieldBuilder();
	}

	@Override
	public TextAreaBuilderI getTextAreaBuilder() {
		return new Bootstrap2TextAreaBuilder();
	}

	@Override
	public CheckBoxBuilderI getCheckBoxBuilder() {
		return new Bootstrap2CheckBoxBuilder();
	}

	@Override
	public ImageBuilderI getImageBuilder() {
		return new Bootstrap2ImageBuilder();
	}

	@Override
	public LinkBuilderI getLinkBuilder() {
		return new Bootstrap2LinkBuilder();
	}
	
	@Override
	public DropDownBuilderI getDropDownBuilder() {
		return new Bootstrap2DropDownBuilder();
	}

	@Override
	public ButtonBuilderI getButtonBuilder() {
		return new Bootstrap2ButtonBuilder();
	}
	
	@Override
	public RepeatingViewBuilderI getRepeatingViewBuilder() {
		return new Bootstrap2RepeatingViewBuilder();
	}
}
