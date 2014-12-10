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

public class SynchronizerHelper implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private DMDWebGenPageContext context;
	private String wicketId;
	private String propertyName;
	private boolean enableThoughUnmodifiable;

	private transient DomainObjectReference ref;
	private transient Method buttonMethod;
	private transient Method choicerMethod;
	private transient Method getterMethod;
	private transient Method setterMethod;
	private transient Method disablerMethod;
	private transient Method hiderMethod;
	private transient Method validatorMethod;
	private transient Method removerButtonMethod;
	private transient Boolean isEager;
	private transient Boolean isStaticallyDisabled;
	private transient String help;

	private transient Object forcedMethodTargetObject;
	private transient boolean transientFieldsInitializedFromPrototype;

	private static final Map<String, SynchronizerHelper> synchronizerPrototypes = new HashMap<String, SynchronizerHelper>();
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

	public SynchronizerHelper(PageElementI<?> element) {
		this(element.getContext(), element.getDomainElement().getWicketId(), element.getDomainElement().getPropertyName(), element
				.enableThoughUnmodifiable());
	}

	public SynchronizerHelper(DMDWebGenPageContext context, DomainElementI<DomainObjectReference> domainElement) {
		this(context, domainElement.getWicketId(), domainElement.getPropertyName(), false);
	}

	public SynchronizerHelper(DMDWebGenPageContext context, String wicketId, String propertyName, boolean enableThoughUnmodifiable) {
		this.context = context;
		this.enableThoughUnmodifiable = enableThoughUnmodifiable;
		context.getPage().add(new Behavior() {
			private static final long serialVersionUID = 1L;
			@Override
			public void afterRender(Component component) {
				super.afterRender(component);
				// domainreference needs to be reset on ajax updates
				ref = null;
			}
		});
		this.wicketId = wicketId;
		this.propertyName = propertyName;
		saveAsPrototype();
	}

	protected void saveAsPrototype() {
		// Some SynchronizerHelpers are not instanciated for being part of a model but really just
		// as a temporary helper (e.g. see method GeneratedGenericDataTableFactory#addColumnContentConverter).
		// This may have the effect that there is not (yet) a valid object
		// reference available which in turn would inhibit the crucial meta data retrieval required for a real good
		// prototype. So we just don't consider these instances for prototypes.
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

	protected String synchronizerLookupName() {
		String domainClassName = context.getPage().getDefaultModelObject().getClass().getName();
		return domainClassName + "#" + wicketId;
	}

	protected Class<?> getClassForResourceLookup() {
		return context.getDomainRegistry().getElement(wicketId).getAccessor().getClassRef().getDomainClass();
	}

	public String getWicketId() {
		return wicketId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public DMDWebGenPageContext getContext() {
		return context;
	}

	public Object invokeButtonMethod(MethodExceptionHandlerI methodExceptionHandler) {
		return invokeMethod(methodExceptionHandler, "", getButtonMethod(), new Object[] {});
	}

	public Object invokeChoicerMethod() {
		return invokeMethod(null, ReflectionUtil.CHOICE_PREFIX, getChoicerMethod());
	}

	public Object invokeGetterMethod() {
		return invokeMethod(null, ReflectionUtil.GETTER_PREFIX, getGetterMethod());
	}

	public Object invokeSetterMethod(Object... params) {
		return invokeMethod(null, ReflectionUtil.SETTER_PREFIX, getSetterMethod(), params);
	}

	public Object invokeDisablerMethod() {
		return invokeMethod(null, ReflectionUtil.DISABLER_PREFIX, getDisablerMethod());
	}

	public Object invokeHiderMethod() {
		return invokeMethod(null, ReflectionUtil.HIDE_PREFIX, getHiderMethod());
	}

	public Object invokeValidatorMethod(Object newValue) {
		return invokeMethod(null, ReflectionUtil.VALIDATOR_PREFIX, getValidatorMethod(), newValue);
	}

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

	private synchronized boolean handleException(MethodExceptionHandlerI exHandler, Object domainObject, Throwable exception) {
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
				String title = (domainObject instanceof Class) ? FormBuilder.buildPrompt((Class<?>) domainObject) : // Exception
																													// in
																													// constructor
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

	private String localizeException(Object domainObject, Throwable exception) {
		Class<?> domainClass = getClassForResourceLookup();
		String resourceMessage = I18NLabelModelFactory.createLabelModel(context, domainClass, exception.getMessage()).getObject();
		if (StringUtils.isNotBlank(resourceMessage)) {
			return resourceMessage;
		} else {
			return AbstractRootFrame.localizeException(domainObject, exception);
		}
	}

	public DomainObjectReference getRef() {
		if (ref == null) {
			DomainElementI<DomainObjectReference> domainElement = context.getDomainRegistry().getElement(wicketId);
			ref = (domainElement != null) ? domainElement.getAccessor().getRef() : null;
		}
		return ref;
	}

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

	private Method nullableMethod(Method method) {
		return (method == NULL_METHOD) ? null : method;
	}

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

	public Method getButtonMethod() {
		initializeTransientMembersFromPrototype();
		if (buttonMethod == null) {
			buttonMethod = ReflectionUtil.findMethod(getRef().getDomainClass(), propertyName);
			if (buttonMethod == null)
				buttonMethod = NULL_METHOD;
		}
		return nullableMethod(buttonMethod);
	}

	public Method getChoicerMethod() {
		initializeTransientMembersFromPrototype();
		if (choicerMethod == null) {
			choicerMethod = getRef().findPropertyAccessor(propertyName, ReflectionUtil.CHOICE_PREFIX);
			if (choicerMethod == null)
				choicerMethod = NULL_METHOD;
		}
		return nullableMethod(choicerMethod);
	}

	public Method getSetterMethod() {
		initializeTransientMembersFromPrototype();
		if (setterMethod == null) {
			setterMethod = getRef().findPropertyAccessor(propertyName, ReflectionUtil.SETTER_PREFIX);
			if (setterMethod == null)
				setterMethod = NULL_METHOD;
		}
		return nullableMethod(setterMethod);
	}

	public Method getDisablerMethod() {
		initializeTransientMembersFromPrototype();
		if (disablerMethod == null) {
			// capitalize to find button disablers as well!
			disablerMethod = getRef().findPropertyAccessor(StringUtils.capitalize(propertyName), ReflectionUtil.DISABLER_PREFIX);
			if (disablerMethod == null)
				disablerMethod = NULL_METHOD;
		}
		return nullableMethod(disablerMethod);
	}

	public Method getHiderMethod() {
		initializeTransientMembersFromPrototype();
		if (hiderMethod == null) {
			// capitalize to find button disablers aswell!
			hiderMethod = getRef().findPropertyAccessor(StringUtils.capitalize(propertyName), ReflectionUtil.HIDE_PREFIX);
			if (hiderMethod == null)
				hiderMethod = NULL_METHOD;
		}
		return nullableMethod(hiderMethod);
	}

	public Method getValidatorMethod() {
		initializeTransientMembersFromPrototype();
		if (validatorMethod == null) {
			validatorMethod = getRef().findPropertyAccessor(propertyName, ReflectionUtil.VALIDATOR_PREFIX);
			if (validatorMethod == null)
				validatorMethod = NULL_METHOD;
		}
		return nullableMethod(validatorMethod);
	}

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

	public Object getButtonMethodTargetObject() {
		return getMethodTargetObject("");
	}

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
	 */
	public boolean isStaticallyDisabled() {
		initializeTransientMembersFromPrototype();
		if (isStaticallyDisabled == null) {
			isStaticallyDisabled = !isFileType(getGetterMethod()) && isStaticallyDisabled(getGetterMethod(), getSetterMethod(), getRef());
		}
		return isStaticallyDisabled;
	}

	public boolean isStaticallyDisabled(Method pGetterMethod, Method pSetterMethod, AbstractDomainReference ref) {
		return pGetterMethod != null && //
				(pSetterMethod == null || ReflectionUtil.isStaticallyDisabled(pSetterMethod, ref)) && //
				!enableThoughUnmodifiable; //
	}

	public boolean isEnabled() {
		return !isStaticallyDisabled() && !getRef().isDisabled() && (getDisablerMethod() == null || invokeDisablerMethod() == null)
				&& !isInvalidReadonlyFile();
	}

	protected boolean isReadonlyFileProvider() {
		return isFileType(getGetterMethod()) && //
				(getSetterMethod() == null || ReflectionUtil.isStaticallyDisabled(getSetterMethod(), getRef()));
	}

	protected boolean isInvalidReadonlyFile() {
		return isReadonlyFileProvider() && invalidFileValueMessage() != null;
	}

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
			String resourceMessage = I18NLabelModelFactory.createLabelModel(context, domainClass, disabledMessage).getObject();
			if (StringUtils.isNotBlank(resourceMessage)) {
				return resourceMessage;
			} else {
				return disabledMessage;
			}
		} else {
			return getHelp(relevantMethod);
		}
	}

	public String getButtonTooltip() {
		return getTooltip(getButtonMethod());
	}

	public String getFieldTooltip() {
		return getTooltip(getGetterMethod());
	}

	public boolean isEager() {
		initializeTransientMembersFromPrototype();
		if (isEager == null) {
			isEager = isAnnotationPresent(getSetterMethod(), Eager.class);
		}
		return isEager;
	}

	public boolean isProperty() {
		Method method = getRef().findPropertyAccessor(getPropertyName(), ReflectionUtil.SETTER_PREFIX);
		return method != null;
	}
	
	public boolean isEagerProperty(String propertyName) {
		Method method = getRef().findPropertyAccessor(propertyName, ReflectionUtil.SETTER_PREFIX);
		return isAnnotationPresent(method, Eager.class);
	}

	public boolean isAssisted() {
		return isAnnotationPresent(getSetterMethod(), Assisted.class);
	}

	public boolean isForced() {
		if(getButtonMethod() != null) {
			return isAnnotationPresent(getButtonMethod(), Forced.class);
		} else {
			return isAnnotationPresent(getSetterMethod(), Forced.class);
		}
	}

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
	 * should be adde
	 * 
	 * @param method
	 *            the getter
	 * @param choices
	 *            the possible values
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

	public boolean isRequired(Method method) {
		return method.getAnnotation(NotNull.class) != null;
	}

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
				// IF there is not the getter is not annotated with @notnull, a
				// null vlaue should be adde
				return addNullIfNullable(getGetterMethod(), propertyType.getEnumConstants());
			}
		} else {
			return invokeChoicer();
		}
	}

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

	public boolean isChoicesNullValid() {
		for (Object c : getChoices()) {
			if (c == null) {
				return true;
			}
		}
		return false;
	}

	public String getFormat() {
		return getFormat(getGetterMethod());
	}

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

	public void assertValidate() {
		if (getValidatorMethod() == null && isAnnotationPresent(getGetterMethod(), Validate.class)) {
			throw new SevereGUIException("Property " + propertyName + " requires a validation method");
		}
	}

	/**
	 * Let the Ajax response update all components in all forms. Will be used by
	 * ajax buttons.
	 * 
	 * @param ctx
	 * @param target
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

	public void updateAllForms(final AjaxRequestTarget target) {
		updateAllForms(getContext(), target);
	}

	public static void updateAllForms(DMDWebGenPageContext ctx, final AjaxRequestTarget target) {
		MarkupContainer root = findRoot(ctx.getPage());

		root.visitChildren(new IVisitor<Component, Object>() {
			@Override
			public void component(Component object, IVisit<Object> visit) {
				IModel<?> defaultModel = object.getDefaultModel();
				if (defaultModel instanceof TouchedListenerModelWrapper<?>) {
					boolean isChanged = false;
					try {
						isChanged = ((TouchedListenerModelWrapper<?>) defaultModel).modelChangedBetweenRequestProcessing(object);
					} catch (Exception e) {
						// Bei Fehler nix tun
					}
					if (isChanged) {
						((TouchedListenerModelWrapper<?>) defaultModel).preserveState(object);
						target.add(object);
					}
					// TODO blaz02: Wicket6. Zuerst den Checkbox oder
					// RadioChoice immer zum
					// Target hinzufügen. Die Methode
					// "modelChangedBetweenrEquestsProcessing"
					// liefert immer false. -> Für eine Checkbox oder ein
					// RadioChoice muss es entweder einen eigenen
					// TouchedListenerModelWrapper oder ein eigenes State, so
					// das das eqausl anders programmiert wird. Der Weg ist ein
					// schlechter Hack.
					if (object instanceof CheckBox || object instanceof RadioChoice) {
						target.add(object);
					}
				} else if (object instanceof GeneratedButton) {
					GeneratedButton button = (GeneratedButton) object;
					// vocke03: DMDVIER-155
					if (button.hasButtonStateChanged()) {
						target.add(button);
					}
				}
				// Zwar ist das GenericDataTablePanel eine generische
				// Komponente, aber der Inhalt dieser Tabelle nicht. So kann
				// nicht festgestellt werden, ob sich der Inhalt
				// der Tabelle geändert hat. Deswegen muss jedesmal die
				// komplette Tabelle in das Ajax-Target geschrieben werden.
				// Blöde Lösung! meis026
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

	public static MarkupContainer findRoot(MarkupContainer page) {
		MarkupContainer root = null;
		if (page != null) {
			root = findRoot(page.getParent());
		}
		return root != null ? root : page;
	}

	public boolean isBooleanType() {
		return isBooleanType(getGetterMethod());
	}

	public static boolean isBooleanType(Method getter) {
		Class<?> returnType = getter.getReturnType();
		return returnType == boolean.class || returnType == Boolean.class;
	}

	public boolean isDateType() {
		return isDateType(getGetterMethod());
	}

	public static boolean isDateType(Method getter) {
		return Date.class.isAssignableFrom(getter.getReturnType());
	}

	public static boolean isFileType(Method getter) {
		return getter != null && File.class.isAssignableFrom(getter.getReturnType());
	}

	public boolean isNumberType() {
		return isNumberType(getGetterMethod());
	}

	public static boolean isNumberType(Method getter) {
		Class<?> returnType = getter.getReturnType();
		for (Class<?> clazz : new Class[] { Number.class, byte.class, short.class, int.class, long.class, float.class, double.class }) {
			if (clazz.isAssignableFrom(returnType)) {
				return true;
			}
		}
		return false;
	}

	public boolean isRangedNumberType() {
		return isRangedNumberType(getGetterMethod());
	}

	public boolean isInputStreamType() {
		return isInputStreamType(getGetterMethod());
	}

	public static boolean isInputStreamType(Method getter) {
		return getter != null && InputStream.class.isAssignableFrom(getter.getReturnType());
	}

	/**
	 * Can be used to check if range validation is possible on this number.
	 */
	public static boolean isRangedNumberType(Method getter) {
		Class<?> returnType = getter.getReturnType();
		for (Class<?> clazz : new Class[] { Byte.class, byte.class, Short.class, short.class, Integer.class, int.class, Long.class,
				long.class, Float.class, float.class, double.class }) {
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

	public <T extends Annotation> T getAnnotationOnButtonMethode(Class<T> classAnnotation) {
		return buttonMethod.getAnnotation(classAnnotation);
	}

	public static void synchronizeModelsForValidInput(Form<?> form) {
		FormComponent.visitFormComponentsPostOrder(form, new ValidFormComponentSynchronizationVisitor(form));
		synchronizeModelsForValidNestedInput(form);
	}

	protected static void synchronizeModelsForValidNestedInput(Form<?> form) {
		Visits.visitChildren(form, new IVisitor<Form<?>, Void>() {
			@Override
			public void component(final Form<?> form, final IVisit<Void> visit) {
				if (form.isEnabledInHierarchy() && form.isVisibleInHierarchy()) {
					synchronizeModelsForValidInput(form);
				} else {
					visit.dontGoDeeper();
				}
			}
		}, new ClassVisitFilter(Form.class));
	}

	protected static class ValidFormComponentSynchronizationVisitor implements IVisitor<FormComponent<?>, Void> {

		private final Form<?> formToVisit;

		public ValidFormComponentSynchronizationVisitor(Form<?> formToVisit) {
			this.formToVisit = formToVisit;
		}

		@Override
		public void component(final FormComponent<?> formComponent, final IVisit<Void> visit) {
			final Form<?> componentsParent = Form.findForm(formComponent);
			if (componentsParent == formToVisit) {
				if (componentsParent.isEnabledInHierarchy() && formComponent.isVisibleInHierarchy() && formComponent.isEnabledInHierarchy()) {
					formComponent.validate();
					if (formComponent.isValid()) {
						formComponent.updateModel();
					}
				}
			}
		}
	}

}
