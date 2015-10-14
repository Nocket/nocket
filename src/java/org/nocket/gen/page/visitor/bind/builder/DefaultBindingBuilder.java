package org.nocket.gen.page.visitor.bind.builder;

import java.io.File;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.nocket.gen.domain.visitor.html.styling.StylingFactory;
import org.nocket.gen.domain.visitor.html.styling.common.ButtonBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.CheckBoxBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.DropDownBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.FeedbackPanelBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.FileUploadBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.ImageBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.LinkBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.ListMultipleChoiceBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.ModalWindowBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.PasswordTextFieldBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.RadioChoiceBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.RepeatingViewBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.TabbedPanelBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.TextAreaBuilderI;
import org.nocket.gen.domain.visitor.html.styling.common.TextFieldBuilderI;
import org.nocket.gen.page.element.ButtonElement;
import org.nocket.gen.page.element.CheckboxInputElement;
import org.nocket.gen.page.element.DivElement;
import org.nocket.gen.page.element.FeedbackElement;
import org.nocket.gen.page.element.FileDownloadElement;
import org.nocket.gen.page.element.FileInputElement;
import org.nocket.gen.page.element.FormElement;
import org.nocket.gen.page.element.GroupTabbedPanelElement;
import org.nocket.gen.page.element.ImageElement;
import org.nocket.gen.page.element.LabelElement;
import org.nocket.gen.page.element.LinkElement;
import org.nocket.gen.page.element.ModalElement;
import org.nocket.gen.page.element.PasswordInputElement;
import org.nocket.gen.page.element.PromptElement;
import org.nocket.gen.page.element.RadioInputElement;
import org.nocket.gen.page.element.RepeatingPanelElement;
import org.nocket.gen.page.element.SelectElement;
import org.nocket.gen.page.element.TableElement;
import org.nocket.gen.page.element.TextAreaElement;
import org.nocket.gen.page.element.TextInputElement;
import org.nocket.gen.page.element.UnknownPageElementI;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedBeanValidationForm;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedGenericDataTableFactory;

public class DefaultBindingBuilder implements BindingBuilderI {

    @Override
    public Component createModal(ModalElement e) {
         ModalWindowBuilderI builder = StylingFactory.getStylingStrategy().getModalWindowBuilder();
         builder.initModalWindowBuilder(e.getWicketId());
         Panel modal = builder.getModalWindow();
        return modal;
    }

    @Override
    public Component createFeedback(FeedbackElement e) {
    	FeedbackPanelBuilderI builder = StylingFactory.getStylingStrategy().getFeedbackPanelBuilder();
    	FeedbackPanel feedback = builder.getFeedbackPanel();
        feedback.setOutputMarkupId(true);
        return feedback;
    }

    @Override
    public Component createForm(FormElement e) {
        GeneratedBeanValidationForm<Object> form = new GeneratedBeanValidationForm<Object>(e.getContext(),
                e.getWicketId(), e.getModel());
        form.setOutputMarkupId(true);
        return form;
    }

    @Override
    public Component createLabel(LabelElement e) {
        Label label = new Label(e.getWicketId(), e.getModel());
        return label;
    }

    @Override
    public Component createPrompt(PromptElement e) {
        Label label = new Label(e.getWicketId(), e.getModel());
        label.setEscapeModelStrings(false);
        return label;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Component createTextInput(TextInputElement e) {
        TextFieldBuilderI builder = StylingFactory.getStylingStrategy().getTextFieldBuilder();
        builder.initTextFieldBuilder(e);
        return builder.getTextField();
    }

    @Override
    public Component createFileInput(FileInputElement e) {
        FileUploadBuilderI builder = StylingFactory.getStylingStrategy().getFileUploadBuilder();
        builder.initFileUploadBuilder(e.getWicketId());
        return builder.getFileUploadField();
    }

    @Override
    public Component createPasswordInput(PasswordInputElement e) {
        PasswordTextFieldBuilderI builder = StylingFactory.getStylingStrategy().getPasswordTextFieldBuilder();
        builder.initPasswordTextFieldBuilder(e.getWicketId(), (IModel) e.getModel());
        return builder.getPasswordField();
    }

    @Override
    public Component createFileDownload(FileDownloadElement e) {
        IModel<File> model = e.getModel();
        DownloadLink downloadLink = new DownloadLink(e.getWicketId(), model);
        return downloadLink;
    }

    @Override
    public Component createTextArea(TextAreaElement e) {
        TextAreaBuilderI builder = StylingFactory.getStylingStrategy().getTextAreaBuilder();
        builder.initTextAreaBuilder(e.getWicketId(), e.getModel());
        return builder.getTextArea();
    }

    @Override
    public Component createCheckboxInput(CheckboxInputElement e) {
        CheckBoxBuilderI builder = StylingFactory.getStylingStrategy().getCheckBoxBuilder();
        builder.initCheckBoxBuilder(e.getWicketId(), e.getModel());
        return builder.getCheckBox();
    }

    @Override
    public Component createRadioInput(RadioInputElement e) {
    	RadioChoiceBuilderI builder = StylingFactory.getStylingStrategy().getRadioChoiceBuilder();
    	builder.initRadioChoiceBuilder(e.getWicketId(), e.getModel(), e.getChoicesModel(), e.getChoicesRenderer());
        return builder.getRadioChoice();
    }

    @Override
    public Component createImage(ImageElement e) {
        ImageBuilderI builder = StylingFactory.getStylingStrategy().getImageBuilder();
        builder.initImageBuilder(e.getWicketId(), e.getModel());
        return builder.getImage();
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public Component createLink(LinkElement e) {
    	LinkBuilderI builder = StylingFactory.getStylingStrategy().getLinkBuilder();
    	builder.initLinkBuilder(e);
    	return builder.getLink();
    }

    @SuppressWarnings("unchecked")
	@Override
    public Component createSelect(SelectElement e) {
        if (!e.isMultiselect()) {
        	DropDownBuilderI builder = StylingFactory.getStylingStrategy().getDropDownBuilder();
        	builder.initDropDownBuilder(e.getWicketId(), e.getModel(),
                    e.getChoicesModel(), e.getChoicesRenderer());
        	return builder.getDropDown();
        } else {
			ListMultipleChoiceBuilderI builder = StylingFactory.getStylingStrategy().getListMultipleChoiceBuilder();
        	builder.initMultipleChoiceBuilder(e.getWicketId(), e.getModel(), e.getChoicesModel());
        	return builder.getListMultipleChoice();
        }
    }

    @Override
    public Component createTable(TableElement e) {
        return new GeneratedGenericDataTableFactory(e).createTable();
    }

    @Override
    public Component createButton(final ButtonElement e) {
        ButtonBuilderI builder = StylingFactory.getStylingStrategy().getButtonBuilder();
        builder.initButtonBuilder(e);
        return builder.getButton();
    }

    @Override
    public Component createUnknown(UnknownPageElementI<?> e) {
        return null;
    }

    @Override
    public Component createListView(RepeatingPanelElement e) {
    	RepeatingViewBuilderI builder = StylingFactory.getStylingStrategy().getRepeatingViewBuilder();
    	builder.initRepeatingViewBuilder(e.getWicketId(), e.getModel());
        return builder.getRepeatingView();
    }

    @Override
    public Component createGroupTabbedPanel(GroupTabbedPanelElement e) {
    	TabbedPanelBuilderI builder = StylingFactory.getStylingStrategy().getTabbedPanelBuilder();
    	builder.initTabbedPanelBuilder(e);
    	return builder.getTabbedPanel();
    }

    @Override
    public Component createDiv(DivElement e) {
        WebMarkupContainer div = new WebMarkupContainer(e.getWicketId());
        return div;
    }
}
