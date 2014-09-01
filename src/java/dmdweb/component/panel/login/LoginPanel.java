package dmdweb.component.panel.login;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import dmdweb.component.form.BeanValidationForm;

/**
 * Abstract panel with the login form.
 * 
 * @author blaz02
 * 
 */
@SuppressWarnings("serial")
public abstract class LoginPanel extends Panel {

    public LoginPanel(String id, IModel<UserLoginData> model) {
        super(id, model);
        final TextField<String> userName = new TextField<String>("username");
        final PasswordTextField<String> password = new PasswordTextField<String>("password");
        final Label authError = new Label("autherror", new ResourceModel("autherror.message", null));
        authError.setVisible(false);
        final Form<UserLoginData> signinform = new BeanValidationForm<UserLoginData>("loginform", model) {
            protected void onError() {
                authError.setVisible(false);
            }
        };
        signinform.add(userName);
        signinform.add(password);
        signinform.add(authError);
        signinform.add(new Button("login") {
            public void onSubmit() {
                final UserLoginData userLoginData = signinform.getModelObject();
                if (LoginPanel.this.doAuthentication(userLoginData)) {
                    continueToOriginalDestination();
                    setResponsePage(getDefaultRedirectPage());
                } else {
                    authError.setVisible(true);
                }
            }
        });
        add(signinform);
    }

    /**
     * Name og the page, wher the user will be redirected after
     * 
     * @return
     */
    protected Class<? extends Page> getDefaultRedirectPage() {
        return getApplication().getHomePage();
    }

    /**
     * Method which performs authorization of the user.
     * 
     * @return True if authorization was successful. False otherwise.
     */
    public abstract boolean doAuthentication(UserLoginData userLogin);

    class PasswordTextField<T> extends TextField<T> {
        private static final long serialVersionUID = 1L;

        public PasswordTextField(String id) {
            super(id);
        }

        @Override
        protected String getInputType() {
            return "password";
        }
    }

}
