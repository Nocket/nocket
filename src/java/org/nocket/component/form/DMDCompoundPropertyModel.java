package org.nocket.component.form;

import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

/**
 * A compound property model based on Wicket's CompoundPropertyModel, which uses the component's name 
 * as the property expression to retrieve properties on the nested model object.
 * 
 * It stores extra the model object type as a property. This is required for the JSR302
 * {@link BeanValidationForm}.   
 * 
 * @author blaz02
 *
 * @param <T>
 * 						The model object type  
 */
public class DMDCompoundPropertyModel<T> extends CompoundPropertyModel<T> {

	private static final long serialVersionUID = 1L;

	private Class<?> targetClass;

	/**
	 * Constructor.
	 * 
	 * @param model 
	 */
	public DMDCompoundPropertyModel(final IModel<T> model) {
		super(model);
		if(model == null)
			throw new IllegalArgumentException("Model is null.");
		if(model.getObject() == null)
			throw new IllegalArgumentException("Model object is null. Did you forget to initialize nested objects?");
		this.targetClass = model.getObject().getClass();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param object
	 */
	public DMDCompoundPropertyModel(final T object) {
		super(object);
		if(object == null)
			throw new IllegalArgumentException("Model object is null. Did you forget to initialize nested objects?");
		this.targetClass = object.getClass();
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}
	
}
