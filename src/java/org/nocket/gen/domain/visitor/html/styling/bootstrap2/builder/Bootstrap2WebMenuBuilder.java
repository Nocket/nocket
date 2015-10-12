/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.nocket.NocketSession;
import org.nocket.component.menu.MenuItem;
import org.nocket.component.menu.MenuPanel;
import org.nocket.gen.domain.visitor.html.styling.common.WebMenuBuilderI;

/**
 * Menü-Builder für das Hauptmenü unter Bootstrap 2
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class Bootstrap2WebMenuBuilder implements WebMenuBuilderI {
	
	private ItemList menu = null;

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.WebMenuBuilderI#initMenuBuilder(java.lang.String, java.util.List)
	 */
	@Override
	public void initMenuBuilder(String wicketId, List<MenuItem> menuItems) {
		menu = new ItemList(wicketId, menuItems);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.WebMenuBuilderI#getMenu()
	 */
	@Override
	public ListView getMenu() {
		return menu;
	}

	
	
	
	
	
	
	
	
	/**
	 * Private Klasse für die Darstellung der Menüpunkte. 
	 * Implementierung wurde aus dem MenuPanel hier her verschoben
	 *
	 * @author Thomas.Veit@Bertelsmann.de
	 *
	 */
	private static class ItemList extends ListView<MenuItem> {

        private static final String CSS_CLASS_UL = "nav nav-tabs nav-stacked";
        private static final String ACTIVE = "active";

        public ItemList(String name, List<MenuItem> list) {
            super(name, list);
        }

        @Override
        protected void populateItem(ListItem<MenuItem> item) {
            MenuItem menuEntry = item.getModelObject();

            BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>("link", menuEntry.getTargetPage());
            link.add(new Label("linklabel", menuEntry.getLabel()));
            item.add(link);

            final WebMarkupContainer container = new WebMarkupContainer("nestedContainer");
            container.add(new AttributeModifier("class", CSS_CLASS_UL));

            ListView<MenuItem> menuList = new ListView<MenuItem>("nested", menuEntry.getSubItems()) {
                @Override
                protected void populateItem(ListItem<MenuItem> item) {
                    MenuItem menuEntry = item.getModelObject();
                    Label label = new Label("nestedLinkLabel", menuEntry.getLabel());
                    AjaxLink<MenuItem> link = new AjaxLink<MenuItem>("nestedLink", item.getModel()) {
                        @Override
                        public void onClick(AjaxRequestTarget target) {
                            MenuItem item = getModelObject();
                            boolean itemSuccessfullySelected = item.onClick(this, target);
                            if (itemSuccessfullySelected) {
                                NocketSession.get().setLastSelectedMenuItem(item);
                            }
                        }
                    };
                    link.add(label);
                    item.add(link);

                    if (displayMenuOpenedForItem(menuEntry)) {
                        item.add(new AttributeModifier("class", ACTIVE));
                        container.add(new AttributeModifier("class", CSS_CLASS_UL + " " + ACTIVE));
                    }
                }

            };

            container.add(menuList);
            container.setVisible(menuEntry.getSubItems().size() > 0);
            item.add(container);
        }
        


        @Override
        public void renderHead(IHeaderResponse response) {
            response.render(JavaScriptHeaderItem
                    .forReference(new PackageResourceReference(MenuPanel.class, "MenuPanel.js")));
            response.render(CssHeaderItem.forReference(new PackageResourceReference(MenuPanel.class, "MenuPanel.css")));
        }
        

        protected boolean displayMenuOpenedForItem(MenuItem menuEntry) {
            return menuEntry.equals(NocketSession.get().getLastSelectedMenuItem());
            // This was the original logic but it causes problems when navigating from one page to another
            // We keep it here for a while until we are sure that the new logic is suitable. It may cause trouble
            // with derived MenuItem classes which don't properly care for registering themself in the session
            // as being the last selected item
            //return this.getPage().getClass() == menuEntry.getTargetPage();
        }

    }
}
