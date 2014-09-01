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

/**
 * DMD implementation of a panel that displays
 * {@link org.apache.wicket.feedback.FeedbackMessage}s in a list view. It works
 * similar to FeedbackPanel, with the difference that the messages are
 * surrounded by div container with according CSS-Class, depending on this if
 * there are errors or not.
 * 
 * @see org.apache.wicket.markup.html.panel.FeedbackPanel
 * 
 * @author blaz02
 */
public class DMDFeedbackPanel extends FeedbackPanel {

    private static final long serialVersionUID = 1L;

    private boolean errorTitle = true;

    public DMDFeedbackPanel(String id) {
        super(id);
        visitChildren(new FeedbackPanelVisitor());
    }

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
     * @param showInfoOnly
     * 
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
     * @return Name of the CSS-Class used for the errorbox
     */
    protected String getErrorCSSClass() {
        return "alert alert-error";
    }

    /**
     * @return Name of the CSS-Class used for the errorbox
     */
    protected String getInfoCSSClass() {
        return "alert alert-success";
    }

    class FeedbackPanelVisitor implements IVisitor<Component, Component>, IClusterable {

        private static final long serialVersionUID = 1L;

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
