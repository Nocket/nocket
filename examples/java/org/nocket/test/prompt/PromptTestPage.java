package org.nocket.test.prompt;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.nocket.gen.page.GeneratedBinding;
import org.nocket.test.page.BrowserTestsPage;

public class PromptTestPage extends BrowserTestsPage {
	private static final long serialVersionUID = 5839698372140412574L;

	public PromptTestPage() {
		this(Model.of(new PromptTest()));
	}

	public PromptTestPage(IModel<PromptTest> model) {
		super(model);
		new GeneratedBinding(this).bind();
	}

}
