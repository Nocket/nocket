package dmdweb.page;

import org.apache.wicket.markup.html.WebPage;

/**
 * This class allows two create generic Page classes at runtime using the
 * in-memory runtime compiler from package dmdweb.rtcompile. The structure of a
 * fully generic page class is absolutely canonic and therefore can completely
 * be derived from the domain class to display.
 * 
 * @author less02
 */
public class InMemoryPageClassBuilder extends InMemoryClassBuilder {
    protected final Class<? extends WebPage> pageBaseClass;
    protected final String pageClassName;

    public InMemoryPageClassBuilder(Class<?> domainClass, Class<? extends WebPage> pageBaseClass) {
	super(domainClass);
	this.pageBaseClass = pageBaseClass;
	this.pageClassName = domainClass.getSimpleName() + "Page";
    }

    @Override
    protected String constructSourceCode() {
	return String.format(
		"package %1$s;" +
			"public class %2$s extends %3$s {" +
			"   public %2$s() {" +
			"       this(org.apache.wicket.model.Model.of(new %4$s()));" +
			"   }" +
			"   public %2$s(org.apache.wicket.model.IModel model) {" +
			"       super(model);" +
			"       new dmdweb.gen.page.GeneratedBinding(this).bind();" +
			"   }" +
			"}",
			packageName, pageClassName, pageBaseClass.getName(), domainClass.getName());
    }

    @Override
    protected String fullyQualifiedClassName() {
	return packageName + "." + pageClassName;
    }
}
