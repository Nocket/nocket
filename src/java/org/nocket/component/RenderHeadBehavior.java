package org.nocket.component;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;

public class RenderHeadBehavior extends Behavior {
	private static final long serialVersionUID = 1L;
	private final HeaderItem headerItem;

	public RenderHeadBehavior(HeaderItem headerItem) {
		super();
		this.headerItem = headerItem;
	}

	public void renderHead(Component component, IHeaderResponse response) {
		response.render(headerItem);
	}
}