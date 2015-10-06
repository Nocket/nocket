/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder;

import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.nocket.gen.domain.visitor.html.styling.common.RepeatingViewBuilderI;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedRepeatingPanel;

/**
 * Builder für Repeating Views in Bootstrap2
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class Bootstrap2RepeatingViewBuilder implements RepeatingViewBuilderI {
	
	private GeneratedRepeatingPanel view = null;

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.RepeatingViewBuilderI#initRepeatingViewBuilder(java.lang.String)
	 */
	@Override
	public void initRepeatingViewBuilder(String id) {
		view = new GeneratedRepeatingPanel(id);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.RepeatingViewBuilderI#initRepeatingViewBuilder(java.lang.String, org.apache.wicket.model.IModel)
	 */
	@Override
	public void initRepeatingViewBuilder(String id, IModel<?> model) {
		view = new GeneratedRepeatingPanel(id, model);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.RepeatingViewBuilderI#getRepeatingView()
	 */
	@Override
	public RepeatingView getRepeatingView() {
		return view;
	}

}
