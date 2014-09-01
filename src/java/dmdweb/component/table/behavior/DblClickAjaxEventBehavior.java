package dmdweb.component.table.behavior;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

abstract public class DblClickAjaxEventBehavior<T> extends AjaxEventBehavior {

	private static final long serialVersionUID = 1L;
	
	private IModel<T> model;

	public DblClickAjaxEventBehavior(IModel<T> model) {
		super("onDblClick");
		this.model = model;
	}

	@Override
	final protected void onEvent(AjaxRequestTarget target) {
		this.onEvent(target, model);
	}
	
	abstract protected void onEvent(AjaxRequestTarget target, IModel<T> model);
}
