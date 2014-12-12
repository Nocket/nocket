package org.nocket.gen.page.element.synchronizer;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.nocket.NocketSession;
import org.nocket.gen.page.guiservice.DMDWebGenGuiServiceProvider;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedButton;

/**
 * Ajax behavior attached to components, which setter property is annotated by
 * "@Eager". It causes form submit right after the value in the component was 
 * changed.
 * 
 * @author blaz02
 */
public class EagerAjaxFormSubmitBehavior extends AjaxFormSubmitBehavior {

	private static final long serialVersionUID = 1L;

	public static final String NOCKET_EAGER = "Nocket-Eager";
	public static final String NOCKET_FORCED = "Nocket-Forced";

	private SynchronizerHelper helper;

	public EagerAjaxFormSubmitBehavior(String event, SynchronizerHelper helper) {
		super(event);
		this.helper = helper;
	}

	@Override
	protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
		super.updateAjaxAttributes(attributes);
		attributes.getExtraParameters().put(NOCKET_EAGER, Boolean.TRUE);
		if(helper.isForced()) {
			attributes.getExtraParameters().put(NOCKET_FORCED, Boolean.TRUE);	
		}
	}

	@Override
	protected void onSubmit(AjaxRequestTarget target) {
		finalize(target);
	}

	@Override
	protected void onError(final AjaxRequestTarget target) {
		//SynchronizerHelper.synchronizeModelsForValidInput(Form.findForm(getComponent()));
		finalize(target);
	}

	protected void finalize(final AjaxRequestTarget target) {
		DMDWebGenGuiServiceProvider webGenGuiServiceProvider = NocketSession.get().getDMDWebGenGuiServiceProvider();
		try {
			webGenGuiServiceProvider.registerAjaxRequestTarget(helper.getContext(), target);
			helper.updateAllForms(target);
		} finally {
			webGenGuiServiceProvider.unregisterAjaxRequestTarget(helper.getContext(), target);
		}
		// Remove glass panel, if there is a disabled or invisible button in the
		// form
		if (formCreatesInvisibleOrDisabledButton(target)) {
			target.appendJavaScript("unblock();");
		}
	}

	/**
	 * This method checks whether the form contains an invisible or disabled
	 * button that should now be re-enabled or re-displayed by sending it with
	 * the AjaxRequestTarget
	 * 
	 * @param target
	 * @return
	 */
	protected boolean formCreatesInvisibleOrDisabledButton(AjaxRequestTarget target) {
		for (Component comp : target.getComponents()) {
			if (comp instanceof GeneratedButton) {
				GeneratedButton button = (GeneratedButton) comp;
				return button.isEnabled() || button.determineVisibility();
			}
		}
		return false;
	}

}
