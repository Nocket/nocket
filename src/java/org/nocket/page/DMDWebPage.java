package org.nocket.page;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.nocket.component.header.lesscss.LessCSSHelper;
import org.nocket.gen.page.DMDWebGenPageContext;

/**
 * Parent page for every page in DMDWeb application.
 * 
 * @author blaz02
 */
@SuppressWarnings("serial")
abstract public class DMDWebPage extends WebPage {

    private DMDWebGenPageContext pageContext;

    public DMDWebPage() {
        this((IModel<?>) null);
    }

    public DMDWebPage(IModel<?> model) {
        super(model);
        if (model != null) {
            DMDPageFactory.storePageForView(this, model.getObject());
        }
    }

    /*
     * This is done, because we do not have GenGUI for the web. In the feature
     * these methods will not be called directly.
     */

    /**
     * This is default way to set target response page for the specified view.
     * The change to the page is statefull, what means that the URL is only
     * valid for current user session.
     * <p>
     * Page class for the view is found using convention. For details see
     * {@link DMDPageFactory}.
     * <p>
     * IMPORTANT: This method is required, because there is no GenGUI for the
     * web. In the feature these methods will not be called directly.
     * 
     * @param view
     *            Instance of the view. Cannot be null.
     * 
     */
    public void setResponsePageForView(Object view) {
        setResponsePage(DMDPageFactory.getViewPageInstance(view));
    }

    /**
     * Method sets target response page for the specified view. The change to
     * the page is stateless, what means that the browser will be redirected to
     * the URL with the parameters taken form PageParameters argument. Target
     * page must define Constructor(PageParametrs params).
     * <p>
     * Page class for the view is found using convention. For details see
     * {@link DMDPageFactory}.
     * <p>
     * IMPORTANT: This method is required, because there is no GenGUI for the
     * web. In the feature these methods will not be called directly.
     * 
     * @param view
     *            Instance of the view. Cannot be null.
     * 
     */
    public void setRedirectPageForView(Object view, PageParameters params) {
        setResponsePage(DMDPageFactory.getViewPageClass(view), params);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        // FIXME: funktioniert das wirklich? Im Forscher wird kein Link erzeugt
        LessCSSHelper.initBootstrapLessCSS(this.getClass(), response, getRequestCycle(), new LessCSSHelper.PathTupel(
                this.getClass(), "less/application.less", "css/application.css"));
    }

    public DMDWebGenPageContext getPageContext() {
        return this.pageContext;
    }

    public void setPagecontext(DMDWebGenPageContext pageContext) {
        this.pageContext = pageContext;
    }

}
