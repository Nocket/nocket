package forscher.nocket.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.model.IModel;
import org.nocket.component.menu.MenuItem;
import org.nocket.component.menu.MenuPanel;
import org.nocket.component.panel.login.LoginStatusBar;
import org.nocket.gen.GenericMenuItem;
import org.nocket.page.DMDWebPage;

import forscher.nocket.generator.TestPojo;
import forscher.nocket.page.error.TestErrorPage;
import forscher.nocket.page.gen.GeneratedPage;
import forscher.nocket.page.gen.ajax.AjaxTargetUpdateTest;
import forscher.nocket.page.gen.i18n.GenguiLocalizedPage;
import forscher.nocket.page.gen.layout.AllComponentsBootstrapPlain;
import forscher.nocket.page.gen.layout.AllComponentsBootstrapTooltip;
import forscher.nocket.page.gen.layout.AllComponentsTablePlain;
import forscher.nocket.page.gen.layout.AllComponentsTableTooltip;
import forscher.nocket.page.gen.modalByGuiService.BookLendingPage;
import forscher.nocket.page.gen.tabs.KundePage;
import forscher.nocket.page.modal.ModalExamplePage;
import forscher.nocket.page.modal.NocketModalViewPage;

@SuppressWarnings("serial")
public class ForscherPage extends DMDWebPage {

    public ForscherPage() {
	this((IModel<?>) null);
    }

    public ForscherPage(IModel<?> model) {
	super(model);

	MenuPanel menuPanel = new MenuPanel("menu", getMenuItems());
	
	LoginStatusBar loginBar = new LoginStatusBar("loginstatus", "TODO", null);

	loginBar.setRenderBodyOnly(true);
	menuPanel.setRenderBodyOnly(true);

	// menuPanel.setVisible(false);
	add(menuPanel);
	add(loginBar);
	add(new DebugBar("debug"));
    }

    protected List<MenuItem> getMenuItems() {
	List<MenuItem> menuEntries = new ArrayList<MenuItem>();

	MenuItem forms = new MenuItem("Forms", HomePage.class);
	MenuItem generated = new MenuItem("Generated", HomePage.class);
	MenuItem auth = new MenuItem("Authentication", HomePage.class);
	MenuItem tables = new MenuItem("Tables", HomePage.class);
	MenuItem modal = new MenuItem("Modal", HomePage.class);
	MenuItem ex = new MenuItem("Exceptions", HomePage.class);
	MenuItem deprecated = new MenuItem("Deprecated", HomePage.class);

	//	menuEntries.add(forms);
	menuEntries.add(generated);
	//	menuEntries.add(auth);
	//	menuEntries.add(tables);
	menuEntries.add(modal);
	menuEntries.add(ex);
	menuEntries.add(deprecated);

	//	forms.addSubItem(new MenuItem("Simple Form", FormPage.class));
	//	forms.addSubItem(new SpecialFunctionMenuItem());
	//	forms.addSubItem(new MenuItem("Bean Validation", BeanValidationFormPage.class));
	//	forms.addSubItem(new MenuItem("Bean Validation Tooltip", BeanValidationTooltipFormPage.class));
	//	forms.addSubItem(new MenuItem("Bean Validation Ajax", BeanValidationFormPageAjaxButton.class));
	//	forms.addSubItem(new MenuItem("Member Entity", MemberEntityPage.class));

	generated.addSubItem(new MenuItem("Generated Form", GeneratedPage.class));
	generated.addSubItem(new MenuItem("Modal dialog with GuiServiceI", BookLendingPage.class));
	generated.addSubItem(new MenuItem("I18N", GenguiLocalizedPage.class));
	generated.addSubItem(new MenuItem("Group", KundePage.class));
	generated.addSubItem(new GenericMenuItem("Bootstrap/Plain", AllComponentsBootstrapPlain.class));

	generated.addSubItem(new GenericMenuItem("Bootstrap/Tooltip", AllComponentsBootstrapTooltip.class));

	generated.addSubItem(new GenericMenuItem("AjaxTarget update test", AjaxTargetUpdateTest.class));
	generated.addSubItem(new GenericMenuItem("Generation", TestPojo.class));
	
	// TODO meist026 Die Empty Page funktioniert noch nicht
	//        generated.addSubItem(new GenericMenuItem("Empty page", EmptyDomainObject.class));

	//tabs.addSubItem(new MenuItem("Choices Example", ChoicesTestsPage.class));

	//	auth.addSubItem(new MenuItem("Login Page", LoginPage.class));
	//	auth.addSubItem(new MenuItem("Secure Page", SecurePage.class));
	//
	//	tables.addSubItem(new MenuItem("Tables", TableExamplePage.class));
	//	tables.addSubItem(new MenuItem("PageReference", BenutzerTabelleViewPage.class));

	modal.addSubItem(new MenuItem("MessageBox", ModalExamplePage.class));
	modal.addSubItem(new MenuItem("Nocket Modal", NocketModalViewPage.class));
	
	ex.addSubItem(new MenuItem("Exception Test", TestErrorPage.class));

	deprecated.addSubItem(new GenericMenuItem("Table/Tooltip", AllComponentsTableTooltip.class));
	deprecated.addSubItem(new GenericMenuItem("Table/Plain", AllComponentsTablePlain.class));

	return menuEntries;
    }

}
