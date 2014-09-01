package dmdweb.component.table;

/**
 * Possible sort types for the tables which can be rendered 
 * from within {@link GenericDataTablePanel}.
 * 
 * @author blaz02
 */
public enum TableSortType {

	/** 
	 * Sorting is done on the server with standard HTTP request.
	 * The page is rebuild after request. 
	 */
	DEFAULT,
	
	/**
	 *  Sorting is done on the server with Ajax Request. 
	 *  The table is rebuild after request.
	 */
	AJAX,
	
	/**
	 *  Sorting is done in browser with JQuery table sorter.
	 *  There is no interaction with the server. 
	 */
	JS;
	
}
