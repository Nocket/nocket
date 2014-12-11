package org.nocket.gen.page.visitor.bind.builder.components;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IFormSubmitter;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.nocket.component.form.BeanValidationForm;
import org.nocket.component.form.behaviors.ValidationTooltipStyleBehavior;
import org.nocket.component.form.behaviors.ValidationTooltipStyleGroupBehavior;
import org.nocket.gen.domain.ValidationErrorPresentation;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.element.FeedbackElement;
import org.nocket.gen.page.element.PageElementI;
import org.nocket.gen.page.element.synchronizer.EagerAjaxFormSubmitBehavior;

public class GeneratedBeanValidationForm<T> extends BeanValidationForm<T> {

	private static final Logger log = Logger.getLogger(GeneratedBeanValidationForm.class);

	private static final long serialVersionUID = 1L;

	/**
	 * If an input field, that is connected with an model attribute, that is
	 * annotated with an eager annotation, will send a ajax request to the server.
	 * While processing this request it is necessary that the setter of the
	 * model attribute is called after all other setters are called. The reason
	 * is, that all data has to synchronized to their models, because the setter
	 * could use the synchronized values.<br>
	 * In Wicket it seemed to be impossible to change the order of setter calls
	 * except the iterator of a form.<br>
	 * It's not a good idea to copy the original wicket iterator, so it was
	 * necessary to put something between the wicket iterator and the consumer
	 * of the iterator.<br>
	 * This FormEagerComponentDelayingIterator checks the request to look for
	 * the attribute, that is responsible for the ajax call. Than the
	 * FormEagerComponentDelayingIterator encapsulates the wicket iterator and
	 * holds back the desired setter call. This setter call will be processed
	 * last.
	 */
	private final class FormEagerComponentDelayingIterator implements Iterator<Component> {

		private final Iterator<Component> realIterator;

		private String holdBackComponentName = null;
		private Component holdBackComponent = null;

		private FormEagerComponentDelayingIterator(Iterator<Component> realIterator) {
			this.realIterator = realIterator;
			this.holdBackComponentName = extractSubmittingComponentIdFromRequest();
		}

		public boolean hasNext() {
			return holdBackComponent != null || realIterator.hasNext();
		}

		public Component next() {
			if (!realIterator.hasNext()) {
				if (holdBackComponent == null) {
					throw new NoSuchElementException();
				}
				Component result = holdBackComponent;
				holdBackComponent = null;
				return result;
			}

			Component next = realIterator.next();
			if (next.getId().equals(holdBackComponentName)) {
				holdBackComponent = next;
				return realIterator.hasNext() ? realIterator.next() : holdBackComponent;
			}

			return next;
		}

		public void remove() {
			realIterator.remove();
		}
	}

	private String submittingComponentId;
	private boolean eagerProcessing;
	private boolean forcedProcessing;

	private DMDWebGenPageContext context;

	public GeneratedBeanValidationForm(DMDWebGenPageContext context, String id, IModel<T> model) {
		super(id, model);
		this.context = context;
		setValidationErrorPresentation();
	}

	protected void setValidationErrorPresentation() {
		if (context.getConfiguration().getValidationErrorPresentation() == ValidationErrorPresentation.TOOLTIP) {
			setValidationStyleBehaviorClass(ValidationTooltipStyleBehavior.class);
			setValidationStyleGroupBehaviorClass(ValidationTooltipStyleGroupBehavior.class);
		}
	}

	@Override
	protected String getComponentPropertyPath(FormComponent component) {
		if (context.getComponentRegistry().getComponent(FeedbackElement.DEFAULT_WICKET_ID) != null) {
			return null;
		} else {
			PageElementI<?> element = context.getPageRegistry().getElement(component.getId());
			if (element == null) {
				return null;
			} else if (element.isDomainElement()) {
				return element.getBeanValidationExpression();
			} else {
				return null;
			}
		}
	}

	@Override
	protected String getValidatorPropertyPath(FormComponent c) {
		PageElementI<?> element = context.getPageRegistry().getElement(c.getId());
		if (element == null) {
			return null;
		} else if (element.isDomainElement()) {
			return element.getBeanValidationPropertyName();
		} else {
			return null;
		}
	}

	@Override
	protected String getValidatorPropertyPrompt(FormComponent c) {
		PageElementI<?> element = context.getPageRegistry().getElement(c.getId());
		if (element == null) {
			return c.getId();
		} else if (element.isDomainElement()) {
			return element.getDomainElement().getPrompt();
		} else {
			return c.getId();
		}
	}

	public Iterator<Component> iterator() {
		final Iterator<Component> realIterator = super.iterator();
		return new FormEagerComponentDelayingIterator(realIterator);
	}

	public void process(IFormSubmitter submittingComponent) {
		this.submittingComponentId = extractSubmittingComponentIdFromRequest();
		this.eagerProcessing = isEagerSubmit();
		this.forcedProcessing = isForcedSubmit();
		log.debug("Processing request: submittingComponentId=" + submittingComponentId + ", isEagerProcessing=" + eagerProcessing + ", isForcedProcessing=" + forcedProcessing);
		super.process(submittingComponent);
	}

	public boolean isForcedProcessing() {
		return forcedProcessing;
	}

	public boolean isEagerProcessing() {
		return eagerProcessing;
	}

	public String getSubmittingComponentId() {
		return submittingComponentId;
	}

	private boolean isEagerSubmit() {
		return getPostParameterAsBoolean(EagerAjaxFormSubmitBehavior.NOCKET_EAGER);
	}

	private boolean isForcedSubmit() {
		return getPostParameterAsBoolean(EagerAjaxFormSubmitBehavior.NOCKET_FORCED);
	}
	
	private boolean getPostParameterAsBoolean(String name) {
		IRequestParameters postParameters = getRequest().getPostParameters();
		return postParameters == null ? false : postParameters.getParameterValue(name).toBoolean();		
	}
	
	protected String extractSubmittingComponentIdFromRequest() {
		String res = null;
		Set<String> parameterNames = getRequest().getQueryParameters().getParameterNames();
		if (parameterNames != null) {
			for (String string : parameterNames) {
				// a useless paramter that is added by
				if (!"random".equalsIgnoreCase(string)) {
					res = StringUtils.substringAfterLast(string, "-");
					break;
				}
			}
		}
		return res;
	}
}
