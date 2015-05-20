package org.nocket.test.addons.annotation;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.nocket.addons.annotation.CSSResource;
import org.nocket.addons.annotation.CSSResourceRef;
import org.nocket.addons.annotation.JavaScriptResource;
import org.nocket.addons.annotation.JavaScriptResourceRef;
import org.nocket.gen.page.GeneratedBinding;
import org.nocket.gen.page.inject.PageComponent;
import org.nocket.test.page.BrowserTestsPage;

@CSSResource(path="css/annotationTest.css")
@CSSResourceRef(cssResourceClass=AnnotationTestCssResourceReference1.class)
@JavaScriptResourceRef(javaScriptResourceClass=AnnotationTestJavaScriptResourceReference1.class)
public class AnnotationTestPage extends BrowserTestsPage {

	private static final long serialVersionUID = 1L;

	@CSSResourceRef(cssResourceClass=AnnotationTestCssResourceReference2.class)
	@PageComponent(AnnotationTestConstants.Field1)
	TextField<?> field1;
	
	@JavaScriptResource(path="js/annotationTest_2.js")
	@PageComponent(AnnotationTestConstants.Field2)
	TextField<?> field2;
	
	public AnnotationTestPage() {
		this(Model.of(new AnnotationTest()));
	}

	public AnnotationTestPage(final IModel<AnnotationTest> model) {
		super(model);
		GeneratedBinding generatedBinding = new GeneratedBinding(this);
		generatedBinding.bind();
	}
}
