package org.nocket.component.panel;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.io.IClusterable;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

// TODO: Auto-generated Javadoc
/**
 * DMD implementation of a panel that displays
 * {@link org.apache.wicket.feedback.FeedbackMessage}s in a list view. It works
 * similar to FeedbackPanel, with the difference that the messages are
 * surrounded by div container with according CSS-Class, depending on this if
 * there are errors or not.
 *
 * @author blaz02
 * @see org.apache.wicket.markup.html.panel.FeedbackPanel
 */
public class DMDFeedbackPanel extends FeedbackPanel {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The error title. */
    private boolean errorTitle = true;

    /**
     * Instantiates a new DMD feedback panel.
     *
     * @param id the id
     */
    public DMDFeedbackPanel(String id) {
        super(id);
        visitChildren(new FeedbackPanelVisitor());
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.markup.html.panel.FeedbackPanel#getCSSClass(org.apache.wicket.feedback.FeedbackMessage)
     */
    @Override
    protected String getCSSClass(final FeedbackMessage message) {
        return null;
    }

    /**
     * Sets whether error title will be shown in the box.
     * 
     * @param showErrorTitle
     *            If set to false, there will be no title show in the box with
     *            the error messages.
     * 
     * @return DMDFeedbackPanel reference
     */
    public DMDFeedbackPanel setShowErrorTitle(boolean showErrorTitle) {
        errorTitle = showErrorTitle;
        return this;
    }

    /**
     * Defines which message levels should be ignored.
     *
     * @param l the l
     * @return DMDFeedbackPanel reference
     */
    public DMDFeedbackPanel setIgnoreLevels(Integer... l) {
        final List<Integer> levels = Arrays.asList(l);
        getFeedbackMessagesModel().setFilter(new IFeedbackMessageFilter() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept(FeedbackMessage message) {
                return !levels.contains(message.getLevel());
            }
        });
        return this;
    }

    /**
     * Gets the error css class.
     *
     * @return Name of the CSS-Class used for the errorbox
     */
    protected String getErrorCSSClass() {
        return "alert alert-error";
    }

    /**
     * Gets the info css class.
     *
     * @return Name of the CSS-Class used for the errorbox
     */
    protected String getInfoCSSClass() {
        return "alert alert-success";
    }

    /**
     * The Class FeedbackPanelVisitor.
     */
    class FeedbackPanelVisitor implements IVisitor<Component, Component>, IClusterable {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 1L;

        /* (non-Javadoc)
         * @see org.apache.wicket.util.visit.IVisitor#component(java.lang.Object, org.apache.wicket.util.visit.IVisit)
         */
        @Override
        public void component(Component component, IVisit<Component> visit) {
            if (component.getId().equals("feedbackul")) {
                if (component instanceof MarkupContainer) {
                    MarkupContainer container = (MarkupContainer) component;
                    container.add(new AttributeModifier("class",
                            new AbstractReadOnlyModel<String>() {
                                private static final long serialVersionUID = 1L;

                                @Override
                                public String getObject() {
                                    return anyErrorMessage() ? getErrorCSSClass() : getInfoCSSClass();
                                }
                            }
                            ));
                    container.add(
                            new Label("feedbacktitle", new ResourceModel("feedbacktitle")) {
                                private static final long serialVersionUID = 1L;

                                @Override
                                public boolean isVisible() {
                                    return errorTitle && anyErrorMessage();
                                }
                            }
                            );
                }
                visit.stop(component);
            }
        }
    }

}
