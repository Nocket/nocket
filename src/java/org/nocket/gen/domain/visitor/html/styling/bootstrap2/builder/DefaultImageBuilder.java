/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder;

import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.model.IModel;
import org.nocket.gen.domain.visitor.html.styling.common.ImageBuilderI;

/**
 * Builder für das Anzeigen von Bildern aus dem Context-Pfad
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class DefaultImageBuilder implements ImageBuilderI {
	
	private ContextImage image = null;

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.ImageBuilderI#initImageBuilder(java.lang.String, org.apache.wicket.model.IModel)
	 */
	@Override
	public void initImageBuilder(String wicketId, IModel contextRelativePath) {
		image = new ContextImage(wicketId, contextRelativePath);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.ImageBuilderI#initImageBuilder(java.lang.String, java.lang.String)
	 */
	@Override
	public void initImageBuilder(String wicketId, String contextRelativePath) {
		image = new ContextImage(wicketId, contextRelativePath);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.ImageBuilderI#getImage()
	 */
	@Override
	public ContextImage getImage() {
		return image;
	}

}
