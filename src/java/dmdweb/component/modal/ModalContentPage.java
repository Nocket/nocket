package dmdweb.component.modal;

import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.iterator.ComponentHierarchyIterator;

/**
 * This is the content of the message box.
 * 
 * @author blaz02
 */
public class ModalContentPage extends WebPage {

    private static final long serialVersionUID = 1L;

    public ModalContentPage(final DMDMessageBox modalBox) {
        final PageReference pageReference = modalBox.getPage().getPageReference();
        if (pageReference == null) {
            throw new IllegalStateException("PageReference is null. Did you forget to add DMDMessageBox to the page?");
        }
        add(new Label("text", new Model<String>(modalBox.getModalSettings().getText())));
        ModalAjaxLink btnOK = new ModalAjaxLink(modalBox, pageReference, ButtonFlag.OK, new Model<ModalSettings>(
                modalBox.getModalSettings()));
        ModalAjaxLink btnYES = new ModalAjaxLink(modalBox, pageReference, ButtonFlag.YES, new Model<ModalSettings>(
                modalBox.getModalSettings()));
        ModalAjaxLink btnNO = new ModalAjaxLink(modalBox, pageReference, ButtonFlag.NO, new Model<ModalSettings>(
                modalBox.getModalSettings()));
        add(btnOK);
        add(btnYES);
        add(btnNO);
        btnOK.add(new Label("label", new ResourceModel(modalBox.getId() + ".modalbutton.OK", new ResourceModel(
                "modalbutton.OK").wrapOnAssignment(this).getObject()).wrapOnAssignment(this)));
        btnYES.add(new Label("label", new ResourceModel(modalBox.getId() + ".modalbutton.YES", new ResourceModel(
                "modalbutton.YES").wrapOnAssignment(this).getObject()).wrapOnAssignment(this)));
        btnNO.add(new Label("label", new ResourceModel(modalBox.getId() + ".modalbutton.NO", new ResourceModel(
                "modalbutton.NO").wrapOnAssignment(this).getObject()).wrapOnAssignment(this)));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(CssHeaderItem.forReference(new PackageResourceReference(this.getClass(), "style_modal.css")));
    }

    class ModalAjaxLink extends AjaxLink<ModalSettings> {

        private static final long serialVersionUID = 1L;

        private final DMDMessageBox box;
        private final ButtonFlag flag;

        private final PageReference pageReference;

        public ModalAjaxLink(DMDMessageBox box, final PageReference pageReference, ButtonFlag flag,
                IModel<ModalSettings> model) {
            super(flag.getId(), model);
            this.box = box;
            this.flag = flag;
            this.pageReference = pageReference;
        }

        @Override
        public boolean isVisible() {
            return getModelObject().containsFlag(flag);
        }

        @Override
        public void onClick(AjaxRequestTarget target) {
            final DMDMessageBox box = findMessageBoxInPage();
            box.clickedButton = flag;
            box.close(target);
        }

        private DMDMessageBox findMessageBoxInPage() {
            DMDMessageBox first = (DMDMessageBox) new ComponentHierarchyIterator(pageReference.getPage(),
                    DMDMessageBox.class).filterById(box.getId()).getFirst(false);
            if (first == null) {
                throw new IllegalStateException(
                        "Cannot find the DMDMessageBox in page. Did you forget to add DMDMessageBox to a page?");
            }
            return first;
        }

    }

}
