package dmdweb.component.table.behavior;

import org.apache.wicket.model.IModel;

import dmdweb.component.table.GenericDataTablePanel;

/**
 * Tables implementing this interface are able to handle click and double-click
 * events in the row.
 * 
 * Rather do not use this interface directly. See: {@link GenericDataTablePanel#newOnClickEvent(IModel)}
 * and {@link GenericDataTablePanel#newOnDblClickEvent(IModel)}
 * 
 * @author blaz02
 * 
 * @param <T>
 *          Type which is used as a model in the table.
 */
public interface IRowClickEventAware<T> {

	/**
	 * Returns new instance of the event handler for click event.
	 *  
	 * @param model Model with the object shown in the row.
	 *  
	 * @return Instance of the event behavior which handles click event.
	 */
	public ClickAjaxEventBehavior<T> newOnClickEvent(IModel<T> model);

	/**
	 * Returns new instance of the event handler for click event.
	 *  
	 * @param model Model with the object shown in the row.
	 *  
	 * @return Instance of the event behavior which handles click event.
	 */

	public DblClickAjaxEventBehavior<T> newOnDblClickEvent(IModel<T> model);

}
