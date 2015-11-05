/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;
import org.nocket.gen.domain.visitor.html.styling.common.TextAreaBuilderI;

/**
 * Textbereiche für Bootstrap2 erstellen
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class DefaultTextAreaBuilder implements TextAreaBuilderI {

	private TextArea<Object> textArea = null;
	
	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.TextAreaBuilderI#initTextAreaBuilder(java.lang.String)
	 */
	@Override
	public void initTextAreaBuilder(String wicketId) {
		textArea = new TextArea<Object>(wicketId);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.TextAreaBuilderI#initTextAreaBuilder(java.lang.String, org.apache.wicket.model.IModel)
	 */
	@Override
	public void initTextAreaBuilder(String wicketId, IModel model) {
		textArea = new TextArea<Object>(wicketId, model);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.TextAreaBuilderI#getTextArea()
	 */
	@Override
	public TextArea getTextArea() {
		return textArea;
	}

}
