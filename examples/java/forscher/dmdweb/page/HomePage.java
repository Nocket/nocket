package forscher.dmdweb.page;

import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import dmdweb.component.menu.MenuItem;

@SuppressWarnings("serial")
public class HomePage extends ForscherPage {

	public HomePage(final PageParameters parameters) {
		add(new ListView<MenuItem>("menuList", Model.ofList(this.getMenuItems())) {
			@Override
			protected void populateItem(ListItem<MenuItem> item) {
				final MenuItem menuEntry = item.getModelObject();
				item.add(new Label("label", menuEntry.getLabel()));
				WebMarkupContainer container = new WebMarkupContainer("nestedcontainer");
				ListView<MenuItem> menuList = new ListView<MenuItem>("nested", menuEntry.getSubItems()) {
					@Override
					protected void populateItem(ListItem<MenuItem> item) {
						MenuItem menuEntry = item.getModelObject();
						BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>("nestedlink", menuEntry.getTargetPage());
						link.add(new Label("nestedlinklabel", menuEntry.getLabel()));
						item.add(link);						
					}
				};
				container.add(menuList);
				item.add(container);
			}
		});
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
	}

}
