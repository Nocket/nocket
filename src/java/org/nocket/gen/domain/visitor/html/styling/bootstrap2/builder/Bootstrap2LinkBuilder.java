/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.IModel;
import org.nocket.component.link.DMDResourceLink;
import org.nocket.gen.domain.visitor.html.styling.common.LinkBuilderI;
import org.nocket.gen.page.element.LinkElement;

/**
 * Builder für interne und externe Links unter Bootstrap2
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class Bootstrap2LinkBuilder implements LinkBuilderI {

	private Component component = null;
	
	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.LinkBuilderI#initLinkBuilder(org.nocket.gen.page.element.LinkElement)
	 */
	@Override
	public void initLinkBuilder(LinkElement e) {
        if (e.isResourceLink()) {
            component = new DMDResourceLink(e.getWicketId(), (IModel<?>) e.getModel());
        } else {
            component = new ExternalLink(e.getWicketId(), e.getModel());
        }
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.LinkBuilderI#getLink()
	 */
	@Override
	public Component getLink() {
        return component;
	}

}
