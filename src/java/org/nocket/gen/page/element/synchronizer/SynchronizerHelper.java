package org.nocket.gen.page.element.synchronizer;

import gengui.annotations.Assisted;
import gengui.annotations.Eager;
import gengui.annotations.Forced;
import gengui.annotations.Prompt;
import gengui.annotations.Validate;
import gengui.domain.AbstractDomainReference;
import gengui.domain.DomainObjectDecoration;
import gengui.domain.DomainObjectReference;
import gengui.guiadapter.DateSynchronizer;
import gengui.guiadapter.GUIItemFactory;
import gengui.guibuilder.FormBuilder;
import gengui.swing.AbstractRootFrame;
import gengui.util.AnnotationHelper;
import gengui.util.DefaultSevereExceptionHandler;
import gengui.util.ReflectionUtil;
import gengui.util.SevereGUIException;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.visit.ClassVisitFilter;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.apache.wicket.util.visit.Visits;
import org.nocket.component.table.GenericDataTablePanel;
import org.nocket.gen.domain.element.DomainElementI;
import org.nocket.gen.i18n.I18NLabelModelFactory;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.element.FeedbackElement;
import org.nocket.gen.page.element.PageElementI;
import org.nocket.gen.page.element.synchronizer.error.MethodExceptionHandlerI;
import org.nocket.gen.page.element.synchronizer.error.WicketGenguiSevereExceptionHandler;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedButton;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedNumberTextField;

// TODO: Auto-generated Javadoc
/**
 * The Class SynchronizerHelper.
 */
public class SynchronizerHelper implements Serializable {

    /** The context. */
    private DMDWebGenPageContext context;
    
    /** The wicket id. */
    private String wicketId;
    
    /** The property name. */
    private String propertyName;
    
    /** The enable though unmodifiable. */
    private boolean enableThoughUnmodifiable;

    /** The ref. */
    private transient DomainObjectReference ref;
    
    /** The button method. */
    private transient Method buttonMethod;
    
    /** The choicer method. */
    private transient Method choicerMethod;
    
    /** The getter method. */
    private transient Method getterMethod;
    
    /** The setter method. */
    private transient Method setterMethod;
    
    /** The disabler method. */
    private transient Method disablerMethod;
    
    /** The hider method. */
    private transient Method hiderMethod;
    
    /** The validator method. */
    private transient Method validatorMethod;
    
    /** The remover button method. */
    private transient Method removerButtonMethod;
    
    /** The is eager. */
    private transient Boolean isEager;
    
    /** The is statically disabled. */
    private transient Boolean isStaticallyDisabled;
    
    /** The help. */
    private transient String help;

    /** The forced method target object. */
    private transient Object forcedMethodTargetObject;
    
    /** The transient fields initialized from prototype. */
    private transient boolean transientFieldsInitializedFromPrototype;

    /** The Constant synchronizerPrototypes. */
    private static final Map<String, SynchronizerHelper> synchronizerPrototypes = new HashMap<String, SynchronizerHelper>();
    
    /** The Constant NULL_METHOD. */
    private static final Method NULL_METHOD;

    static {
	WicketGenguiSevereExceptionHandler.init();
	Method m = null;
	try {
	    m = SynchronizerHelper.class.getDeclaredMethod("NULL_METHOD");
	} catch (NoSuchMethodException nsmx) {
	    System.err.println("Can't initialize NULL_METHOD");
	    nsmx.printStackTrace();
	}
	NULL_METHOD = m;
    }

    /**
     * Instantiates a new synchronizer helper.
     *
     * @param element the element
     */
    public SynchronizerHelper(PageElementI<?> element) {
	this(element.getContext(), element.getDomainElement().getWicketId(),
		element.getDomainElement().getPropertyName(), element.enableThoughUnmodifiable());
    }

    /**
     * Instantiates a new synchronizer helper.
     *
     * @param context the context
     * @param domainElement the domain element
     */
    public SynchronizerHelper(DMDWebGenPageContext context, DomainElementI<DomainObjectReference> domainElement) {
	this(context, domainElement.getWicketId(), domainElement.getPropertyName(), false);
    }

    /**
     * Instantiates a new synchronizer helper.
     *
     * @param context the context
     * @param wicketId the wicket id
     * @param propertyName the property name
     * @param enableThoughUnmodifiable the enable though unmodifiable
     */
    public SynchronizerHelper(DMDWebGenPageContext context, String wicketId, String propertyName,
	    boolean enableThoughUnmodifiable) {
	this.context = context;
	this.enableThoughUnmodifiable = enableThoughUnmodifiable;
	context.getPage().add(new Behavior() {
	    @Override
	    public void afterRender(Component component) {
		super.afterRender(component);
		//domainreference needs to be reset on ajax updates
		ref = null;
	    }
	});
	this.wicketId = wicketId;
	this.propertyName = propertyName;
	saveAsPrototype();
    }

    /**
     * Save as prototype.
     */
    protected void saveAsPrototype() {
	// Some SynchronizerHelpers are not instanciated for being part of a model but really just
	// as a temporary helper (e.g. see method GeneratedGenericDataTableFactory#addColumnContentConverter).
	// This may have the effect that there is not (yet) a valid object reference available which in turn would
	// inhibit the crucial meta data retrieval required for a real good prototype. So we just don't consider these
	// instances for prototypes.
	if (getRef() == null)
	    return;
	synchronized (SynchronizerHelper.class) {
	    String syncName = synchronizerLookupName();
	    SynchronizerHelper syncPrototype = synchronizerPrototypes.get(syncName);
	    if (syncPrototype == null) {
		// Following get... function calls ensure a maximum retrieval of meta data for
		// the prototype so that derived instances have a minimum retrieval work.
		// SynchronizerHelpers are serialized and deserialized very often so that
		// meta data retrieval from reflection calls would otherwise make up a significant
		// percentage of time (according to profiling from 23.03.2013)
		getGetterMethod();
		getSetterMethod();
		getChoicerMethod();
		getDisablerMethod();
		getHiderMethod();
		getValidatorMethod();
		getButtonMethod();
		getRemoverButtonMethod();
		isEager();
		transientFieldsInitializedFromPrototype = true;
		synchronizerPrototypes.put(syncName, this);
	    }
	}
    }

    /**
     * Synchronizer lookup name.
     *
     * @return the string
     */
    protected String synchronizerLookupName() {
	String domainClassName = context.getPage().getDefaultModelObject().getClass().getName();
	return domainClassName + "#" + wicketId;
    }

    /**
     * Gets the class for resource lookup.
     *
     * @return the class for resource lookup
     */
    protected Class<?> getClassForResourceLookup() {
	return context.getDomainRegistry().getElement(wicketId).getAccessor().getClassRef()
		.getDomainClass();
    }

    /**
     * Gets the wicket id.
     *
     * @return the wicket id
     */
    public String getWicketId() {
	return wicketId;
    }

    /**
     * Gets the property name.
     *
     * @return the property name
     */
    public String getPropertyName() {
	return propertyName;
    }

    /**
     * Gets the context.
     *
     * @return the context
     */
    public DMDWebGenPageContext getContext() {
	return context;
    }

    /**
     * Invoke button method.
     *
     * @param methodExceptionHandler the method exception handler
     * @return the object
     */
    public Object invokeButtonMethod(MethodExceptionHandlerI methodExceptionHandler) {
	return invokeMethod(methodExceptionHandler, "", getButtonMethod(), new Object[] {});
    }

    /**
     * Invoke choicer method.
     *
     * @return the object
     */
    public Object invokeChoicerMethod() {
	return invokeMethod(null, ReflectionUtil.CHOICE_PREFIX, getChoicerMethod());
    }

    /**
     * Invoke getter method.
     *
     * @return the object
     */
    public Object invokeGetterMethod() {
	return invokeMethod(null, ReflectionUtil.GETTER_PREFIX, getGetterMethod());
    }

    /**
     * Invoke setter method.
     *
     * @param params the params
     * @return the object
     */
    public Object invokeSetterMethod(Object... params) {
	return invokeMethod(null, ReflectionUtil.SETTER_PREFIX, getSetterMethod(), params);
    }

    /**
     * Invoke disabler method.
     *
     * @return the object
     */
    public Object invokeDisablerMethod() {
	return invokeMethod(null, ReflectionUtil.DISABLER_PREFIX, getDisablerMethod());
    }

    /**
     * Invoke hider method.
     *
     * @return the object
     */
    public Object invokeHiderMethod() {
	return invokeMethod(null, ReflectionUtil.HIDE_PREFIX, getHiderMethod());
    }

    /**
     * Invoke validator method.
     *
     * @param newValue the new value
     * @return the object
     */
    public Object invokeValidatorMethod(Object newValue) {
	return invokeMethod(null, ReflectionUtil.VALIDATOR_PREFIX, getValidatorMethod(), newValue);
    }

    /**
     * Invoke remover button method.
     *
     * @param methodExceptionHandler the method exception handler
     * @param removedValues the removed values
     */
    public void invokeRemoverButtonMethod(MethodExceptionHandlerI methodExceptionHandler, Object... removedValues) {
	Object[] params = removedValues;
	if (ReflectionUtil.hasMultivaluedParameter(getRemoverButtonMethod())) {
	    Class<?>[] paramTypes = getRemoverButtonMethod().getParameterTypes();
	    Class<?> multiselectionType = paramTypes[0].getComponentType();
	    if (multiselectionType != null) {
		Object[] typedArray = (Object[]) Array.newInstance(multiselectionType, removedValues.length);
		for (int i = 0; i < removedValues.length; i++) {
		    typedArray[i] = removedValues[i];
		}
		params = new Object[] { typedArray };
	    }
	}
	invokeMethod(methodExceptionHandler, ReflectionUtil.REMOVER_PREFIX, getRemoverButtonMethod(), params);
    }

    /**
     * Invoke method.
     *
     * @param exHandler the ex handler
     * @param methodType the method type
     * @param method the method
     * @param params the params
     * @return the object
     */
    private Object invokeMethod(MethodExceptionHandlerI exHandler, String methodType, Method method, Object... params) {
	Object domainObject = getRef().getDomainObject();
	if (domainObject == null)
	    return null;
	Object targetObject = getMethodTargetObject(methodType);
	try {
	    return ReflectionUtil.invokeMethodAllowExceptions(method, targetObject, params);
	} catch (InvocationTargetException e) {
	    Object domainObjectForException = domainObject instanceof Collection || domainObject.getClass().isArray() ? targetObject
		    : domainObject;
	    if (exHandler == null || !handleException(exHandler, domainObjectForException, e.getTargetException())) {
		throw DefaultSevereExceptionHandler.handler().process(e.getTargetException());
	    }
	    return null;
	}
    }

    /**
     * Handle exception.
     *
     * @param exHandler the ex handler
     * @param domainObject the domain object
     * @param exception the exception
     * @return true, if successful
     */
    private synchronized boolean handleException(MethodExceptionHandlerI exHandler, Object domainObject,
	    Throwable exception) {
	boolean swallowed = false;

	boolean swallowable = true;
	Throwable child = exception;
	while (child != null) {
	    if (ReflectionUtil.isUnswallowable(child) || child instanceof RuntimeException || child instanceof Error) {
		swallowable = false;
		break;
	    }
	    child = child.getCause();
	}

	if (swallowable) {
	    if (exception.getMessage() != null) {
		String title = (domainObject instanceof Class) ? FormBuilder.buildPrompt((Class<?>) domainObject) : // Exception in constructor
			ReflectionUtil.toTitle(domainObject);
		String message = localizeException(domainObject, exception);
		exHandler.displayError(domainObject, exception, title, message);
	    } else {
		exHandler.exceptionSwallowed(domainObject, exception);
	    }
	    swallowed = true;
	}

	if (exception instanceof RuntimeException) {
	    throw (RuntimeException) exception;
	} else if (exception instanceof Error) {
	    throw (Error) exception;
	} else if (ReflectionUtil.isUnswallowable(child)) {
	    throw new SevereGUIException(child);
	}

	return swallowed;
    }

    /**
     * Localize exception.
     *
     * @param domainObject the domain object
     * @param exception the exception
     * @return the string
     */
    private String localizeException(Object domainObject, Throwable exception) {
	Class<?> domainClass = getClassForResourceLookup();
	String resourceMessage = I18NLabelModelFactory.createLabelModel(context, domainClass, exception.getMessage())
		.getObject();
	if (StringUtils.isNotBlank(resourceMessage)) {
	    return resourceMessage;
	} else {
	    return AbstractRootFrame.localizeException(domainObject, exception);
	}
    }

    /**
     * Gets the ref.
     *
     * @return the ref
     */
    public DomainObjectReference getRef() {
	if (ref == null) {
	    DomainElementI<DomainObjectReference> domainElement = context.getDomainRegistry().getElement(wicketId);
	    ref = (domainElement != null) ? domainElement.getAccessor().getRef() : null;
	}
	return ref;
    }

    /**
     * Gets the help.
     *
     * @param relevantMethod the relevant method
     * @return the help
     */
    protected String getHelp(Method relevantMethod) {
	if (help == null) {
	    if (relevantMethod != null) {
		Prompt prompt = new AnnotationHelper(relevantMethod).getAnnotation(Prompt.class);
		if (prompt != null && !StringUtils.isEmpty(prompt.example()))
		    help = prompt.example().trim();
	    }
	}
	return help;
    }

    /**
     * Nullable method.
     *
     * @param method the method
     * @return the method
     */
    private Method nullableMethod(Method method) {
	return (method == NULL_METHOD) ? null : method;
    }

    /**
     * Gets the getter method.
     *
     * @return the getter method
     */
    public Method getGetterMethod() {
	initializeTransientMembersFromPrototype();
	if (getterMethod == null) {
	    getterMethod = getRef().findPropertyAccessor(propertyName, ReflectionUtil.GETTER_PREFIX);
	    if (getterMethod == null) {
		getterMethod = getRef().findPropertyAccessor(propertyName, ReflectionUtil.BOOLEAN_GETTER_PREFIX);
	    }
	    if (getterMethod == null)
		getterMethod = NULL_METHOD;
	}
	return nullableMethod(getterMethod);
    }

    /**
     * Gets the button method.
     *
     * @return the button method
     */
    public Method getButtonMethod() {
	initializeTransientMembersFromPrototype();
	if (buttonMethod == null) {
	    buttonMethod = ReflectionUtil.findMethod(getRef().getDomainClass(), propertyName);
	    if (buttonMethod == null)
		buttonMethod = NULL_METHOD;
	}
	return nullableMethod(buttonMethod);
    }

    /**
     * Gets the choicer method.
     *
     * @return the choicer method
     */
    public Method getChoicerMethod() {
	initializeTransientMembersFromPrototype();
	if (choicerMethod == null) {
	    choicerMethod = getRef().findPropertyAccessor(propertyName, ReflectionUtil.CHOICE_PREFIX);
	    if (choicerMethod == null)
		choicerMethod = NULL_METHOD;
	}
	return nullableMethod(choicerMethod);
    }

    /**
     * Gets the setter method.
     *
     * @return the setter method
     */
    public Method getSetterMethod() {
	initializeTransientMembersFromPrototype();
	if (setterMethod == null) {
	    setterMethod = getRef().findPropertyAccessor(propertyName, ReflectionUtil.SETTER_PREFIX);
	    if (setterMethod == null)
		setterMethod = NULL_METHOD;
	}
	return nullableMethod(setterMethod);
    }

    /**
     * Gets the disabler method.
     *
     * @return the disabler method
     */
    public Method getDisablerMethod() {
	initializeTransientMembersFromPrototype();
	if (disablerMethod == null) {
	    // capitalize to find button disablers as well!
	    disablerMethod = getRef().findPropertyAccessor(StringUtils.capitalize(propertyName),
		    ReflectionUtil.DISABLER_PREFIX);
	    if (disablerMethod == null)
		disablerMethod = NULL_METHOD;
	}
	return nullableMethod(disablerMethod);
    }

    /**
     * Gets the hider method.
     *
     * @return the hider method
     */
    public Method getHiderMethod() {
	initializeTransientMembersFromPrototype();
	if (hiderMethod == null) {
	    // capitalize to find button disablers aswell!
	    hiderMethod = getRef().findPropertyAccessor(StringUtils.capitalize(propertyName),
		    ReflectionUtil.HIDE_PREFIX);
	    if (hiderMethod == null)
		hiderMethod = NULL_METHOD;
	}
	return nullableMethod(hiderMethod);
    }

    /**
     * Gets the validator method.
     *
     * @return the validator method
     */
    public Method getValidatorMethod() {
	initializeTransientMembersFromPrototype();
	if (validatorMethod == null) {
	    validatorMethod = getRef().findPropertyAccessor(propertyName, ReflectionUtil.VALIDATOR_PREFIX);
	    if (validatorMethod == null)
		validatorMethod = NULL_METHOD;
	}
	return nullableMethod(validatorMethod);
    }

    /**
     * Gets the remover button method.
     *
     * @return the remover button method
     */
    public Method getRemoverButtonMethod() {
	initializeTransientMembersFromPrototype();
	if (removerButtonMethod == null) {
	    if (propertyName.startsWith(ReflectionUtil.REMOVER_PREFIX)) {
		removerButtonMethod = getButtonMethod();
	    } else {
		removerButtonMethod = getRef().findPropertyAccessor(propertyName, ReflectionUtil.REMOVER_PREFIX);
	    }
	    if (removerButtonMethod == null)
		removerButtonMethod = NULL_METHOD;
	}
	return nullableMethod(removerButtonMethod);
    }

    /**
     * Gets the button method target object.
     *
     * @return the button method target object
     */
    public Object getButtonMethodTargetObject() {
	return getMethodTargetObject("");
    }

    /**
     * Gets the method target object.
     *
     * @param methodType the method type
     * @return the method target object
     */
    private Object getMethodTargetObject(String methodType) {
	if (forcedMethodTargetObject != null) {
	    return forcedMethodTargetObject;
	} else {
	    DomainObjectDecoration interception = getRef().getDecorationsAbsolute(wicketId);
	    Object targetObject = interception.getTarget(methodType, getRef());
	    if (targetObject == null && ReflectionUtil.GETTER_PREFIX.equals(methodType)) {
		targetObject = interception.getTarget(ReflectionUtil.BOOLEAN_GETTER_PREFIX, getRef());
	    }
	    return targetObject;
	}
    }

    /**
     * Sets the forced method target object.
     *
     * @param forcedMethodTargetObject the new forced method target object
     */
    public void setForcedMethodTargetObject(Object forcedMethodTargetObject) {
	this.forcedMethodTargetObject = forcedMethodTargetObject;
    }

    /**
     * This is tricky: The general rule is the same as in gengui. The component
     * is statically disabled if there exists a getter but if the corresponsing
     * setter is statically not available (directly or indirectly) . For the web
     * we additionally have to take the case of a download button into account
     * which is rendered for a read-only file attribute. In this case the
     * corresponding download button must <b>not</b> be statically disabled.
     *
     * @return true, if is statically disabled
     */
    public boolean isStaticallyDisabled() {
	initializeTransientMembersFromPrototype();
	if (isStaticallyDisabled == null) {
	    isStaticallyDisabled = !isFileType(getGetterMethod())
		    && isStaticallyDisabled(getGetterMethod(), getSetterMethod(), getRef());
	}
	return isStaticallyDisabled;
    }

    /**
     * Checks if is statically disabled.
     *
     * @param pGetterMethod the getter method
     * @param pSetterMethod the setter method
     * @param ref the ref
     * @return true, if is statically disabled
     */
    public boolean isStaticallyDisabled(Method pGetterMethod, Method pSetterMethod, AbstractDomainReference ref) {
	return pGetterMethod != null && //
		(pSetterMethod == null || ReflectionUtil.isStaticallyDisabled(pSetterMethod, ref)) && //
		!enableThoughUnmodifiable; //
    }

    /**
     * Checks if is enabled.
     *
     * @return true, if is enabled
     */
    public boolean isEnabled() {
	return !isStaticallyDisabled() &&
		!getRef().isDisabled() &&
		(getDisablerMethod() == null || invokeDisablerMethod() == null) &&
		!isInvalidReadonlyFile();
    }

    /**
     * Checks if is readonly file provider.
     *
     * @return true, if is readonly file provider
     */
    protected boolean isReadonlyFileProvider() {
	return isFileType(getGetterMethod()) && //
		(getSetterMethod() == null || ReflectionUtil.isStaticallyDisabled(getSetterMethod(), getRef()));
    }

    /**
     * Checks if is invalid readonly file.
     *
     * @return true, if is invalid readonly file
     */
    protected boolean isInvalidReadonlyFile() {
	return isReadonlyFileProvider() && invalidFileValueMessage() != null;
    }

    /**
     * Invalid file value message.
     *
     * @return the string
     */
    protected String invalidFileValueMessage() {
	File file = (File) invokeGetterMethod();
	if (file != null && !file.isAbsolute())
	    file = new File(WebApplication.get().getServletContext().getRealPath(".") + "/" + file.getPath());
	if (file == null)
	    return "file.error.null";
	if (!file.exists())
	    return "file.error.invalid";
	if (!file.canRead())
	    return "file.error.not.readable";
	if (file.isDirectory())
	    return "file.error.directory";
	return null;
    }

    /**
     * Gets the tooltip.
     *
     * @param relevantMethod the relevant method
     * @return the tooltip
     */
    protected String getTooltip(Method relevantMethod) {
	String disabledMessage = null;
	if (getDisablerMethod() != null) {
	    disabledMessage = (String) invokeDisablerMethod();
	}
	if (disabledMessage == null && isReadonlyFileProvider()) {
	    disabledMessage = invalidFileValueMessage();
	}
	if (disabledMessage != null) {
	    Class<?> domainClass = getClassForResourceLookup();
	    String resourceMessage = I18NLabelModelFactory.createLabelModel(context, domainClass, disabledMessage)
		    .getObject();
	    if (StringUtils.isNotBlank(resourceMessage)) {
		return resourceMessage;
	    } else {
		return disabledMessage;
	    }
	} else {
	    return getHelp(relevantMethod);
	}
    }

    /**
     * Gets the button tooltip.
     *
     * @return the button tooltip
     */
    public String getButtonTooltip() {
	return getTooltip(getButtonMethod());
    }

    /**
     * Gets the field tooltip.
     *
     * @return the field tooltip
     */
    public String getFieldTooltip() {
	return getTooltip(getGetterMethod());
    }

    /**
     * Checks if is eager.
     *
     * @return true, if is eager
     */
    public boolean isEager() {
	initializeTransientMembersFromPrototype();
	if (isEager == null) {
	    isEager = isAnnotationPresent(getSetterMethod(), Eager.class);
	}
	return isEager;
    }

    /**
     * Checks if is assisted.
     *
     * @return true, if is assisted
     */
    public boolean isAssisted() {
	return isAnnotationPresent(getSetterMethod(), Assisted.class);
    }

    /**
     * Checks if is forced.
     *
     * @return true, if is forced
     */
    public boolean isForced() {
	return isAnnotationPresent(getButtonMethod(), Forced.class);
    }

    /**
     * Checks if is annotation present.
     *
     * @param method the method
     * @param annotation the annotation
     * @return true, if is annotation present
     */
    private boolean isAnnotationPresent(Method method, Class<? extends Annotation> annotation) {
	if (method != null) {
	    AnnotationHelper annotationHelper = new AnnotationHelper(method);
	    return annotationHelper.isAnnotationPresent(annotation);
	} else {
	    return false;
	}
    }

    /**
     * IF there is not the getter is not annotated with @notnull, a null vlaue
     * should be adde.
     *
     * @param method            the getter
     * @param choices            the possible values
     * @return the possible value and null (perhaps)
     */
    private Object[] addNullIfNullable(Method method, Object[] choices) {
	if (isRequired(method)) {
	    return choices;
	}
	Object[] result = new Object[choices.length + 1];
	for (int i = 0; i < choices.length; i++)
	    result[i + 1] = choices[i];
	return result;
    }

    /**
     * Checks if is required.
     *
     * @param method the method
     * @return true, if is required
     */
    public boolean isRequired(Method method) {
	return method.getAnnotation(NotNull.class) != null;
    }

    /**
     * Gets the choices.
     *
     * @return the choices
     */
    public Object[] getChoices() {
	Class<?> propertyType = getGetterMethod().getReturnType();
	if (propertyType == boolean.class) {
	    return new Object[] { true, false };
	} else if (propertyType == Boolean.class) {
	    return new Object[] { Boolean.TRUE, Boolean.FALSE, null };
	} else if (propertyType.isEnum()) {
	    if (getChoicerMethod() != null)
		return invokeChoicer();
	    if (getGetterMethod().getReturnType().isPrimitive())
		return propertyType.getEnumConstants();
	    else {
		// IF there is not the getter is not annotated with @notnull, a null vlaue should be adde
		return addNullIfNullable(getGetterMethod(), propertyType.getEnumConstants());
	    }
	} else {
	    return invokeChoicer();
	}
    }

    /**
     * Invoke choicer.
     *
     * @return the object[]
     */
    Object[] invokeChoicer() {
	Object result = invokeChoicerMethod();
	if (result != null && !result.getClass().isArray() && !Collection.class.isAssignableFrom(result.getClass())) {
	    Method choicerMethod = getChoicerMethod();
	    throw new IllegalArgumentException("Return type " + result.getClass().getName() + " is not valid for "
		    + choicerMethod.getName() + " in class " + choicerMethod.getDeclaringClass().getName());
	}
	if (result == null)
	    return new Object[0];
	if (Collection.class.isAssignableFrom(result.getClass())) {
	    return ReflectionUtil.boxCollection((Collection<?>) result);
	} else {
	    return ReflectionUtil.boxArray(result);
	}
    }

    /**
     * Checks if is choices null valid.
     *
     * @return true, if is choices null valid
     */
    public boolean isChoicesNullValid() {
	for (Object c : getChoices()) {
	    if (c == null) {
		return true;
	    }
	}
	return false;
    }

    /**
     * Gets the format.
     *
     * @return the format
     */
    public String getFormat() {
	return getFormat(getGetterMethod());
    }

    /**
     * Gets the format.
     *
     * @param getterMethod the getter method
     * @return the format
     */
    public String getFormat(Method getterMethod) {
	if (getterMethod != null) {
	    String format = GUIItemFactory.extractOutputFormat(getterMethod);
	    if (format == null && isDateType(getterMethod)) {
		return DateSynchronizer.defaultOutFormat;
	    } else if (format == null && isNumberType(getterMethod)) {
		return GeneratedNumberTextField.DEFAULT_DECIMAL_FORMAT_STR;
	    } else {
		return format;
	    }
	} else {
	    return null;
	}
    }

    /**
     * Assert validate.
     */
    public void assertValidate() {
	if (getValidatorMethod() == null && isAnnotationPresent(getGetterMethod(), Validate.class)) {
	    throw new SevereGUIException("Property " + propertyName + " requires a validation method");
	}
    }

    /**
     * Let the Ajax response update all components in all forms. Will be used by
     * ajax buttons.
     *
     * @param ctx the ctx
     * @param target the target
     */
    public static void updateAllFormsFromPage(DMDWebGenPageContext ctx, final AjaxRequestTarget target) {
	MarkupContainer root = findRoot(ctx.getPage());
	root.visitChildren(new IVisitor<Component, Object>() {
	    @Override
	    public void component(Component object, IVisit<Object> visit) {
		if (object instanceof Form || object instanceof ModalWindow || object instanceof TabbedPanel) {
		    Component component = object;
		    target.add(component);
		}
		IModel<?> defaultModel = object.getDefaultModel();

		if (defaultModel instanceof TouchedListenerModelWrapper<?>) {
		    try {
			((TouchedListenerModelWrapper<?>) defaultModel).preserveState(object);
		    } catch (Exception e) {
			// Bei Fehler nix tun
		    }
		}
	    }
	});
	Component feedback = ctx.getComponentRegistry().getComponent(FeedbackElement.DEFAULT_WICKET_ID);
	if (feedback != null) {
	    target.add(feedback);
	}
    }

    /**
     * Update all forms.
     *
     * @param target the target
     */
    public void updateAllForms(final AjaxRequestTarget target) {
	updateAllForms(getContext(), target);
    }

    /**
     * Update all forms.
     *
     * @param ctx the ctx
     * @param target the target
     */
    public static void updateAllForms(DMDWebGenPageContext ctx, final AjaxRequestTarget target) {
	MarkupContainer root = findRoot(ctx.getPage());

	root.visitChildren(new IVisitor<Component, Object>() {
	    @Override
	    public void component(Component object, IVisit<Object> visit) {
		IModel<?> defaultModel = object.getDefaultModel();
		if (defaultModel instanceof TouchedListenerModelWrapper<?>) {
		    boolean isChanged = false;
		    try {
			isChanged = ((TouchedListenerModelWrapper<?>) defaultModel)
				.modelChangedBetweenRequestProcessing(object);
		    } catch (Exception e) {
			// Bei Fehler nix tun
		    }
		    if (isChanged) {
			((TouchedListenerModelWrapper<?>) defaultModel).preserveState(object);
			target.add(object);
		    }
		    // TODO blaz02: Wicket6. Zuerst den Checkbox oder RadioChoice immer zum
		    // Target hinzufügen. Die Methode "modelChangedBetweenrEquestsProcessing"
		    // liefert immer false. -> Für eine Checkbox oder ein RadioChoice muss es entweder einen eigenen TouchedListenerModelWrapper oder ein eigenes State, so das das eqausl anders programmiert wird. Der Weg ist ein schlechter Hack.
		    if (object instanceof CheckBox || object instanceof RadioChoice) {
			target.add(object);
		    }
		} else if (object instanceof GeneratedButton) {
		    GeneratedButton button = (GeneratedButton) object;
		    //  vocke03: DMDVIER-155
		    if (button.hasButtonStateChanged()) {
			target.add(button);
		    }
		}
		// Zwar ist das GenericDataTablePanel eine generische Komponente, aber der Inhalt dieser Tabelle nicht. So kann nicht festgestellt werden, ob sich der Inhalt
		// der Tabelle geändert hat. Deswegen muss jedesmal die komplette Tabelle in das Ajax-Target geschrieben werden. Blöde Lösung! meis026
		if (object instanceof GenericDataTablePanel) {
		    target.add(object);
		}
	    }
	});
	Component feedback = ctx.getComponentRegistry().getComponent(FeedbackElement.DEFAULT_WICKET_ID);
	if (feedback != null) {
	    target.add(feedback);
	}
    }

    /**
     * Find root.
     *
     * @param page the page
     * @return the markup container
     */
    public static MarkupContainer findRoot(MarkupContainer page) {
	MarkupContainer root = null;
	if (page != null) {
	    root = findRoot(page.getParent());
	}
	return root != null ? root : page;
    }

    /**
     * Checks if is boolean type.
     *
     * @return true, if is boolean type
     */
    public boolean isBooleanType() {
	return isBooleanType(getGetterMethod());
    }

    /**
     * Checks if is boolean type.
     *
     * @param getter the getter
     * @return true, if is boolean type
     */
    public static boolean isBooleanType(Method getter) {
	Class<?> returnType = getter.getReturnType();
	return returnType == boolean.class || returnType == Boolean.class;
    }

    /**
     * Checks if is date type.
     *
     * @return true, if is date type
     */
    public boolean isDateType() {
	return isDateType(getGetterMethod());
    }

    /**
     * Checks if is date type.
     *
     * @param getter the getter
     * @return true, if is date type
     */
    public static boolean isDateType(Method getter) {
	return Date.class.isAssignableFrom(getter.getReturnType());
    }

    /**
     * Checks if is file type.
     *
     * @param getter the getter
     * @return true, if is file type
     */
    public static boolean isFileType(Method getter) {
	return getter != null && File.class.isAssignableFrom(getter.getReturnType());
    }

    /**
     * Checks if is number type.
     *
     * @return true, if is number type
     */
    public boolean isNumberType() {
	return isNumberType(getGetterMethod());
    }

    /**
     * Checks if is number type.
     *
     * @param getter the getter
     * @return true, if is number type
     */
    public static boolean isNumberType(Method getter) {
	Class<?> returnType = getter.getReturnType();
	for (Class<?> clazz : new Class[] { Number.class, byte.class, short.class, int.class, long.class, float.class,
		double.class }) {
	    if (clazz.isAssignableFrom(returnType)) {
		return true;
	    }
	}
	return false;
    }

    /**
     * Checks if is ranged number type.
     *
     * @return true, if is ranged number type
     */
    public boolean isRangedNumberType() {
	return isRangedNumberType(getGetterMethod());
    }

    /**
     * Checks if is input stream type.
     *
     * @return true, if is input stream type
     */
    public boolean isInputStreamType() {
	return isInputStreamType(getGetterMethod());
    }

    /**
     * Checks if is input stream type.
     *
     * @param getter the getter
     * @return true, if is input stream type
     */
    public static boolean isInputStreamType(Method getter) {
	return getter != null && InputStream.class.isAssignableFrom(getter.getReturnType());
    }

    /**
     * Can be used to check if range validation is possible on this number.
     *
     * @param getter the getter
     * @return true, if is ranged number type
     */
     public static boolean isRangedNumberType(Method getter) {
	 Class<?> returnType = getter.getReturnType();
	 for (Class<?> clazz : new Class[] { Byte.class, byte.class, Short.class, short.class, Integer.class, int.class,
		 Long.class, long.class, Float.class, float.class, double.class }) {
	     if (clazz.isAssignableFrom(returnType)) {
		 return true;
	     }
	 }
	 return false;
     }

     /**
      * Performs a lazy initialization of all transient members which can be
      * derived from a cached prototype instance. Running this initialization
      * lazy has the advantage that it works for the case of new construction as
      * well as for deserialization. Calling the initialization from within a
      * readObject deserialization method turn out not to work well because the
      * dependent members may not completely be initializwed at that time.
      */
     protected void initializeTransientMembersFromPrototype() {
	 if (!transientFieldsInitializedFromPrototype) {
	     transientFieldsInitializedFromPrototype = true;
	     String syncName = synchronizerLookupName();
	     SynchronizerHelper syncPrototype = synchronizerPrototypes.get(syncName);
	     if (syncPrototype != null) {
		 //                System.out.print("########### Init from prototype for " + syncName + ": ");
		 //                System.out.println(
		 //                        ((syncPrototype.getterMethod != null) ? "getter " : "") +
		 //                                ((syncPrototype.setterMethod != null) ? "setter " : "") +
		 //                                ((syncPrototype.choicerMethod != null) ? "choicer " : "") +
		 //                                ((syncPrototype.disablerMethod != null) ? "disabler " : "") +
		 //                                ((syncPrototype.validatorMethod != null) ? "validator " : "") +
		 //                                ((syncPrototype.buttonMethod != null) ? "button " : "") +
		 //                                ((syncPrototype.removerButtonMethod != null) ? "remover " : "") +
		 //                                ((syncPrototype.isEager != null) ? "isEager " : "") +
		 //                                ((syncPrototype.help != null) ? "help " : "")
		 //                        );
		 getterMethod = syncPrototype.getterMethod;
		 setterMethod = syncPrototype.setterMethod;
		 choicerMethod = syncPrototype.choicerMethod;
		 disablerMethod = syncPrototype.disablerMethod;
		 hiderMethod = syncPrototype.hiderMethod;
		 validatorMethod = syncPrototype.validatorMethod;
		 buttonMethod = syncPrototype.buttonMethod;
		 removerButtonMethod = syncPrototype.removerButtonMethod;
		 isEager = syncPrototype.isEager;
		 isStaticallyDisabled = syncPrototype.isStaticallyDisabled;
		 help = syncPrototype.help;
	     }
	 }
     }

     /**
      * This method has the only intention to be there and serve as an indicator
      * for non-existent methods. See the initialization of the static member
      * NULL_METHOD. Will be used by Reflection.
      */
     @SuppressWarnings("unused")
     private static void NULL_METHOD() {
     }

     /**
      * The main method.
      *
      * @param args the arguments
      * @throws Exception the exception
      */
     public static void main(String[] args) throws Exception {
	 File file = new File("eins/zwei.drei");
	 System.out.println(file.isAbsolute());
	 System.out.println(file.getPath());
	 System.out.println(file.getName());
	 file = new File("vier.jpg");
	 System.out.println(FilenameUtils.getPath("vier.jpg"));
	 System.out.println(FilenameUtils.getPrefix("vier.jpg"));
	 System.out.println(file.getPath());
	 System.out.println(file.getAbsolutePath());
	 System.out.println(file.getName());
     }

     /**
      * Gets the annotation on button methode.
      *
      * @param <T> the generic type
      * @param classAnnotation the class annotation
      * @return the annotation on button methode
      */
     public <T extends Annotation> T getAnnotationOnButtonMethode(Class<T> classAnnotation) {
	 return buttonMethod.getAnnotation(classAnnotation);
     }

     /**
      * Synchronize models for valid input.
      *
      * @param form the form
      */
     public static void synchronizeModelsForValidInput(Form<?> form) {
	 FormComponent.visitFormComponentsPostOrder(form, new ValidFormComponentSynchronizationVisitor(form));
	 synchronizeModelsForValidNestedInput(form);
     }

     /**
      * Synchronize models for valid nested input.
      *
      * @param form the form
      */
     protected static void synchronizeModelsForValidNestedInput(Form<?> form) {
	 Visits.visitChildren(form, new IVisitor<Form<?>, Void>() {
	     @Override
	     public void component(final Form<?> form, final IVisit<Void> visit) {
		 if (form.isEnabledInHierarchy() && form.isVisibleInHierarchy()) {
		     synchronizeModelsForValidInput(form);
		 }
		 else {
		     visit.dontGoDeeper();
		 }
	     }
	 }, new ClassVisitFilter(Form.class));
     }

     /**
      * Don't believe that I know Wicket so well that I could invent things like
      * that by my own This traversal is mainly stolen from Wicket's Form class
      * and its inner private class FormModelUpdateVisitor.
      *
      * @author less02
      */
     protected static class ValidFormComponentSynchronizationVisitor implements IVisitor<FormComponent<?>, Void> {
	 
 	/** The form to visit. */
 	private final Form<?> formToVisit;

	 /**
 	 * Instantiates a new valid form component synchronization visitor.
 	 *
 	 * @param formToVisit the form to visit
 	 */
 	public ValidFormComponentSynchronizationVisitor(Form<?> formToVisit) {
	     this.formToVisit = formToVisit;
	 }

	 /* (non-Javadoc)
 	 * @see org.apache.wicket.util.visit.IVisitor#component(java.lang.Object, org.apache.wicket.util.visit.IVisit)
 	 */
 	@Override
	 public void component(final FormComponent<?> formComponent, final IVisit<Void> visit) {
	     final Form<?> componentsParent = Form.findForm(formComponent);
	     if (componentsParent == formToVisit) {
		 if (componentsParent.isEnabledInHierarchy() &&
			 formComponent.isVisibleInHierarchy() &&
			 formComponent.isEnabledInHierarchy()) {
		     formComponent.validate();
		     if (formComponent.isValid()) {
			 formComponent.updateModel();
		     }
		 }
	     }
	 }
     }

}
