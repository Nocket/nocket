package dmdweb.component.form.beans;

import java.beans.PropertyDescriptor;

/**
 * Holds information about one property of the class.
 * 
 * @author blaz02
 */
@SuppressWarnings("rawtypes")
public class BeanInfoPropertyDescriptor {

	private Class entityClass;
	private PropertyDescriptor descriptor;
	
	public BeanInfoPropertyDescriptor(Class entityClass, PropertyDescriptor descriptor) {
		super();
		this.entityClass = entityClass;
		this.descriptor = descriptor;
	}
	
	public Class getEntityClass() { return entityClass; }
	public void setEntityClass(Class entityClass) { this.entityClass = entityClass; }
	
	public PropertyDescriptor getDescriptor() {	return descriptor; }
	public void setDescriptor(PropertyDescriptor descriptor) { this.descriptor = descriptor; }
		
}
