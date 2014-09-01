package org.nocket.component.menu;

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
import org.nocket.component.header.jquery.JQueryHelper;

/**
 * A MenuPanel component which displays the provided List of MenuItems in a
 * navigation
 * 
 * @author vocke03
 * 
 */
@SuppressWarnings({ "serial" })
public class MenuPanel extends Panel {

    public MenuPanel(String id, List<MenuItem> list) {
        super(id);
        add(new ItemList("menuList", list));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        JQueryHelper.initJQuery(response);
        response.render(JavaScriptHeaderItem
                .forReference(new PackageResourceReference(MenuPanel.class, "MenuPanel.js")));
        response.render(CssHeaderItem.forReference(new PackageResourceReference(MenuPanel.class, "MenuPanel.css")));
    }

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
