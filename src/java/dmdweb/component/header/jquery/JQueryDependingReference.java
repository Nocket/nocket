package dmdweb.component.header.jquery;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * This resource reference assures, that jQuery JS files are rendered always
 * before desired reference file. This should be used, when JS files depend on
 * the JQuery library.
 * 
 * @author blaz02
 * 
 */
public class JQueryDependingReference extends JavaScriptResourceReference {

    private static final long serialVersionUID = 5662488552406148266L;

    public JQueryDependingReference(Class<?> scope, String name, Locale locale, String style, String variation) {
        super(scope, name, locale, style, variation);
    }

    public JQueryDependingReference(Class<?> scope, String name) {
        super(scope, name);
    }

    public JQueryDependingReference(Key key) {
        super(key);
    }

    public Iterable<? extends HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = new ArrayList<HeaderItem>();
        dependencies.add(JavaScriptHeaderItem.forReference(new PackageResourceReference(JQueryHelper.class,
                JQueryHelper.getCurrentVersion())));
        return dependencies;
    }

}
