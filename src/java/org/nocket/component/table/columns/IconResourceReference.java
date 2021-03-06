package org.nocket.component.table.columns;

import org.apache.wicket.request.resource.PackageResourceReference;

// TODO: Auto-generated Javadoc
/**
 * Wrapper resource reference for delivering of cacheable icons for ex.
 * GenericDataTablePanel
 * 
 * Because of issues with web browser caching, icons are accessed over
 * PackageResourceReference.
 * 
 * They must be accessible over classpath and stored in same package as this
 * class.
 * 
 * @author blaz02
 * 
 */
public class IconResourceReference extends PackageResourceReference {

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            Name of the Icon
	 */
	public IconResourceReference(String name) {
		super(IconResourceReference.class, name);
	}

}
