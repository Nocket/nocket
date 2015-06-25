package org.nocket.test.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.nocket.component.menu.MenuItem;
import org.nocket.component.menu.MenuPanel;
import org.nocket.component.panel.login.LoginStatusBar;
import org.nocket.page.DMDWebPage;
import org.nocket.test.eager.disable.EagerDisableTestPage;
import org.nocket.test.eager.simple.EagerSimpleTestPage;
import org.nocket.test.eager.validation.EagerValidation2TestPage;
import org.nocket.test.eager.validation.EagerValidationTestPage;
import org.nocket.test.prompt.PromptTestPage;
import org.nocket.test.widgets.choices.ChoicesTestsPage;

@SuppressWarnings("serial")
public class BrowserTestsPage extends DMDWebPage {

    public BrowserTestsPage() {
        this((IModel<?>) null);
    }

    public BrowserTestsPage(IModel<?> model) {
        super(model);

        MenuPanel menuPanel = new MenuPanel("menu", getMenuItems());

        add(menuPanel);
        add(new DebugBar("debug"));

        add(new LoginStatusBar("loginstatus") {
            private static final long serialVersionUID = 1L;

            @Override
            protected String getLoggedInUserName() {
        		// TODO meis026 Muss das Login nach SWJ?
        		return "TODO"; //NocketSession.get().getUser();;
            }

            @Override
            protected Class<? extends WebPage> getLoginPage() {
                return BrowserTestsPage.this.getClass();
            }
        });
    }

    protected List<MenuItem> getMenuItems() {
        List<MenuItem> menuEntries = new ArrayList<MenuItem>();

        MenuItem widgets = new MenuItem("Widgets", BrowserTestsPage.class);
        widgets.addSubItem(new MenuItem("Choices", ChoicesTestsPage.class));

        MenuItem eager = new MenuItem("Eager", BrowserTestsPage.class);
        eager.addSubItem(new MenuItem("Simple test", EagerSimpleTestPage.class));
        eager.addSubItem(new MenuItem("Validation test", EagerValidationTestPage.class));
        eager.addSubItem(new MenuItem("Validation 2 test", EagerValidation2TestPage.class));
        eager.addSubItem(new MenuItem("Disable test", EagerDisableTestPage.class));

        MenuItem prompt = new MenuItem("@Prompt", PromptTestPage.class);
        
        menuEntries.add(widgets);
        menuEntries.add(eager);
        menuEntries.add(prompt);


        return menuEntries;
    }
}
