package dmdweb.page;

import org.apache.wicket.markup.html.panel.Panel;

/**
 * This class allows two create generic Page classes at runtime using the
 * in-memory runtime compiler from package dmdweb.rtcompile. The structure of a
 * fully generic page class is absolutely canonic and therefore can completely
 * be derived from the domain class to display.
 * 
 * @author less02
 */
public class InMemoryPanelClassBuilder extends InMemoryClassBuilder {
    protected final Class<? extends Panel> panelBaseClass;
    protected final String panelClassName;

    public InMemoryPanelClassBuilder(Class<?> domainClass, Class<? extends Panel> panelBaseClass) {
	super(domainClass);
	this.panelBaseClass = panelBaseClass;
	this.panelClassName = domainClass.getSimpleName() + "Panel";
    }

    @Override
    protected String constructSourceCode() {
	return String.format(
		"package %1$s;" +
			"public class %2$s extends %3$s {" +
			"   public %2$s(String id, org.apache.wicket.model.IModel model) {" +
			"       super(id, model);" +
			"       new dmdweb.gen.page.GeneratedBinding(this).bind();" +
			"   }" +
			"}",
			packageName, panelClassName, panelBaseClass.getName());
    }

    @Override
    protected String fullyQualifiedClassName() {
	return packageName + "." + panelClassName;
    }
}
