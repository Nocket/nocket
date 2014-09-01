package dmdweb.component.header.lesscss;

import java.text.MessageFormat;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.StringHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.UrlUtils;
import org.apache.wicket.request.resource.PackageResourceReference;

public class LessCSSHelper {

    private static final String LESS_JS = "less-1.7.3.min.js";
    private static final String STYLESHEET_LESS = "<link rel=\"stylesheet/less\" href=\"{0}\" type=\"text/css}\">\n";

    public static class PathTupel {
        private final String lessPath;
        private final String cssPath;
        private final Class<? extends WebPage> pageClass;

        public PathTupel(Class<? extends WebPage> pageClass, String lessPath, String cssPath) {
            super();
            this.lessPath = lessPath;
            this.cssPath = cssPath;
            this.pageClass = pageClass;
        }
    }

    /**
     * Includes the Bootstrap stylesheet in the page header. Checks if the
     * WebApplication is running in DEV or in PROD mode and uses bootstrap.less
     * for DEV or the compiled bootstrap.css for PROD mode.
     * 
     * @param response
     */
    public static void initBootstrapLessCSS(Class<? extends WebPage> appClass, IHeaderResponse response,
            IRequestCycle requestCycle, PathTupel... pathTupels) {
        //        addStylesheetLessCSS(response, requestCycle, new PathTupel(appClass, BOOTSTRAP_LESS, BOOTSTRAP_CSS));
        //        initLessCSS(appClass, response, requestCycle, pathTupels);
    }

    public static void initLessCSS(Class<? extends WebPage> appClass, IHeaderResponse response,
            IRequestCycle requestCycle, PathTupel... pathTupels) {

        for (int i = 0; pathTupels != null && i < pathTupels.length; i++) {
            PathTupel pathTupel = pathTupels[i];
            addStylesheetLessCSS(response, requestCycle, pathTupel);
        }

        if (isDevelopement()) {
            response.render(JavaScriptHeaderItem
                    .forReference(new PackageResourceReference(LessCSSHelper.class, LESS_JS)));
        }
    }

    public static void addStylesheetLessCSS(IHeaderResponse response, IRequestCycle requestCycle, PathTupel pathTupel) {
        if (isDevelopement()) {
            // dev mode
            response.render(StringHeaderItem.forString(MessageFormat.format(STYLESHEET_LESS,
                    UrlUtils.rewriteToContextRelative(pathTupel.lessPath, requestCycle))));
        } else {
            //prod mode
            response.render(CssHeaderItem.forUrl(pathTupel.cssPath));
        }
    }

    private static boolean isDevelopement() {
        return WebApplication.get().getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT;
    }

}
