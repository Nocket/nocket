package org.nocket.component.button;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

// TODO: Auto-generated Javadoc
/**
 * TheDMDOnClickIndicatorAttributeModifier is adds a blocker overlay script to 
 * ajax buttons and links. The script call is added upfront to the onlick event. 
 * If a button is clicked, we call a javascript function which activates a overlay 
 * div to the page and filters all keypressed and keydown events. The overlay div 
 * blocks the mouse and keyboard input and shows a loading indicator
 * image next to a "Please wait" text.
 * 
 * See blocker.js for the javascript functions in this package and the mighty
 * org.nocket.less file for css of the blocker div.
 * 
 * This indicator is basically added to all generated buttons. To prevent showing of it
 * on certain elements just add data-show-overlay="no" to the corresponsding HTML attribute 
 * tag.
 * 
 * @author wund013, blaz02
 */
public class DMDOnClickIndicatorAttributeModifier extends AttributeModifier {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The Constant BLOCKER_SCRIPT. */
    private static final String BLOCKER_SCRIPT = "block('%s', '%s', '%s');";
    
    /** The Constant BLOCKER_REMOVE_SCRIPT. */
    private static final String BLOCKER_REMOVE_SCRIPT = "unblock();";
    
    /** The Constant LOADING_TITLE. */
    private static final String LOADING_TITLE = "loading.title";
    
    /** The Constant OVERLAY_ICON. */
    private static final String OVERLAY_ICON = "blockericon.gif";
    
    /** The Constant BLOCKER_JS. */
    private static final String BLOCKER_JS = "blocker.js";
    
    /** The Constant ONCLICK. */
    private static final String ONCLICK = "onclick";

    /** The Constant INDICATOR. */
    private static final ResourceReference INDICATOR = new PackageResourceReference(
            DMDOnClickIndicatorAttributeModifier.class, OVERLAY_ICON);

    /**
     * Constructor of the DMDOnClickIndicatorAttributeModifier. Just create a
     * new instance and attach it to a AjaxLink, AjaxSubmitLink or AjaxButton.
     * 
     * @param parent
     *            A parent component is needed to load the indicator text from a
     *            property file attached to the main page. We use the component
     *            to finde the page.
     */
    public DMDOnClickIndicatorAttributeModifier(final Component parent) {
        super(ONCLICK, new LoadableDetachableModel<String>() {
            private static final long serialVersionUID = 1L;

            /**
             * @see org.apache.wicket.model.LoadableDetachableModel#load()
             */
            @Override
            protected String load() {
                return String.format(BLOCKER_SCRIPT, parent.getMarkupId(), getIndicatorUrl(),
                        parent.getPage().getString(LOADING_TITLE));
            }

            /**
             * @return url of the animated indicator image
             */
            private CharSequence getIndicatorUrl() {
                return RequestCycle.get().urlFor(new ResourceReferenceRequestHandler(INDICATOR));
            }
        });
    }

    /**
     * We override the newValue method of the AttributeAppender to create a
     * AttributePrepender. Scripts are add pre the normal wicket onclick
     * scripts.
     *
     * @param currentValue the current value
     * @param replacementValue the replacement value
     * @return the string
     * @see org.apache.wicket.AttributeModifier#newValue(java.lang.String,
     *      java.lang.String)
     */
    @Override
    protected String newValue(String currentValue, String replacementValue) {
        return super.newValue(currentValue, replacementValue + currentValue);
    }

    /**
     * Returns the Blocker remove script. Add this script to your
     * {@link AjaxRequestTarget} to remove the blocker overlay.
     *
     * @return the blocker remove script
     */
    public static String getBlockerRemoveScript() {
        return BLOCKER_REMOVE_SCRIPT;
    }

    /**
     * We override the model value of the replacement model if a component is
     * deactivated. No deactivated component should have a onclick blocker.
     *
     * @param component the component
     * @see org.apache.wicket.behavior.Behavior#onConfigure(org.apache.wicket.Component)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onConfigure(Component component) {
        if (!(component.isEnabled() && component.isEnableAllowed())) {
            ((IModel<String>) getReplaceModel()).setObject(StringUtils.EMPTY); // Wicket m(
        }
        super.onConfigure(component);
    }

    /**
     * RenderHead adds the blocker javascript file to the page.
     *
     * @param component the component
     * @param response the response
     * @see org.apache.wicket.behavior.Behavior#renderHead(Component, IHeaderResponse)
     */
    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        response.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(
                DMDOnClickIndicatorAttributeModifier.class,
                BLOCKER_JS)));
        super.renderHead(component, response);
    }
}
