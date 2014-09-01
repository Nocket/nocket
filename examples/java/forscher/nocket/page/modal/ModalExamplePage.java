package forscher.nocket.page.modal;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;
import org.nocket.component.modal.ButtonFlag;
import org.nocket.component.modal.DMDMessageBox;
import org.nocket.component.modal.DMDModalWindow;
import org.nocket.component.modal.ModalCallback;

import forscher.nocket.page.ForscherPage;
import forscher.nocket.page.HomePage;

@SuppressWarnings("serial")
public class ModalExamplePage extends ForscherPage {

    private static final long serialVersionUID = 1L;
    private String status;

    final DMDMessageBox messagebox = new DMDMessageBox("modal1");

    public ModalExamplePage() {

        add(messagebox);
        final WebMarkupContainer cont = new WebMarkupContainer("cont", new PropertyModel<String>(this, "status"));
        cont.setOutputMarkupId(true);

        messagebox.get(messagebox.getContentId()).setVisibilityAllowed(true);
        messagebox.get(messagebox.getContentId()).setVisible(true);

        add(new AjaxLink<Void>("link") {
            public void onClick(AjaxRequestTarget target) {
                messagebox.showInfo(target, "Example of MessageBox.", new ModalCallback() {
                    public boolean doAction(AjaxRequestTarget target, ButtonFlag flag) {
                        ModalExamplePage.this.setStatus("Answer form the box is: " + flag);
                        target.add(cont);
                        return true;
                    }
                });
            }
        });

        add(new AjaxLink<Void>("link2") {
            public void onClick(AjaxRequestTarget target) {
                messagebox.showConfirm(target, "Example of ConfirmBox.", new ModalCallback() {
                    public boolean doAction(AjaxRequestTarget target, ButtonFlag flag) {
                        ModalExamplePage.this.setStatus(flag + " has been choosen.");
                        target.add(cont);
                        return true;
                    }
                });
            }
        });

        add(new AjaxLink<Void>("link3") {
            public void onClick(AjaxRequestTarget target) {
                messagebox.showConfirm(target, "Do you want to go back to home page?", new ModalCallback() {
                    public boolean doAction(AjaxRequestTarget target, ButtonFlag flag) {
                        if (flag == ButtonFlag.YES) {
                            setResponsePage(HomePage.class);
                        } else {
                            ModalExamplePage.this.setStatus("So you stay here!");
                            target.add(cont);
                        }
                        return true;
                    }
                });
            }
        });

        final DMDModalWindow dmdModalWindow = new DMDModalWindow("modal-panel");
        add(dmdModalWindow);

        add(new AjaxLink<Void>("linkB1") {
            public void onClick(AjaxRequestTarget target) {
                target.add(ModalExamplePage.this);
                dmdModalWindow.showInfo("Example of Bootstrap modal info box.", new ModalCallback() {
                    public boolean doAction(AjaxRequestTarget target, ButtonFlag flag) {
                        ModalExamplePage.this.setStatus("Answer form the box is: " + flag);
                        target.add(cont);
                        return true;
                    }
                });
            }
        });

        add(new AjaxLink<Void>("linkB2") {
            public void onClick(AjaxRequestTarget target) {
                target.add(ModalExamplePage.this);
                dmdModalWindow.showConfirm("Example of Bootstrap modal confirmation box.", new ModalCallback() {
                    public boolean doAction(AjaxRequestTarget target, ButtonFlag flag) {
                        ModalExamplePage.this.setStatus("Answer form the box is: " + flag);
                        target.add(cont);
                        return true;
                    }
                });
            }
        });

        cont.add(new Label("statuslabel", new PropertyModel<String>(this, "status")));
        cont.add(new AjaxLink<String>("remove", new PropertyModel<String>(this, "status")) {
            public void onClick(AjaxRequestTarget target) {
                ModalExamplePage.this.setStatus(null);
                target.add(cont);
            }

            @Override
            public boolean isVisible() {
                return getDefaultModelObject() != null;
            }
        });
        add(cont);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
