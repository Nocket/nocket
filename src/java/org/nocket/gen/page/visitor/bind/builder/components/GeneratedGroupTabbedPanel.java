package org.nocket.gen.page.visitor.bind.builder.components;

import gengui.annotations.Group;
import gengui.util.ReflectionUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.nocket.NocketSession;
import org.nocket.component.button.DMDOnClickIndicatorAttributeModifier;
import org.nocket.component.tabs.DMDTabbedPanel;
import org.nocket.gen.i18n.I18NLabelModel;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.element.GroupTabbedPanelElement;
import org.nocket.gen.page.element.synchronizer.SynchronizerHelper;
import org.nocket.gen.page.guiservice.DMDWebGenGuiServiceProvider;
import org.nocket.util.Assert;
import org.nocket.util.NocketRuntimeException;

public class GeneratedGroupTabbedPanel extends DMDTabbedPanel<ITab> {

	private Class<? extends Object> domainObjectClass;
	private HashMap<String, Class<?>> mapGroupNamePanelClass;

	private int lastSelectedTabIndex = -1;
	private boolean isLocalizationWicket;

	public GeneratedGroupTabbedPanel(GroupTabbedPanelElement e) {
		super(e.getWicketId(), new ArrayList<ITab>());

		IModel model = e.getModel();
		domainObjectClass = model.getObject().getClass();

		isLocalizationWicket = e.getContext().getConfiguration()
				.isLocalizationWicket();

		addGroupTabs(model);
	}

	public GeneratedGroupTabbedPanel(String id, IModel<?> model) {
		super(id, new ArrayList<ITab>());

		domainObjectClass = model.getObject().getClass();

		isLocalizationWicket = false;

		addGroupTabs(model);
	}

	/**
	 * Analyse the domain class and create a tab for each name of the group
	 * annotations. Fills the map with the tupel <group name, class name of the
	 * panel class>
	 * 
	 * @param model
	 */
	protected void addGroupTabs(final IModel<?> model) {
		mapGroupNamePanelClass = new HashMap<String, Class<?>>();

		Method[] methods = domainObjectClass.getMethods();
		for (int i = 0; i < methods.length; i++) {
			analyseMethod(domainObjectClass, mapGroupNamePanelClass, methods[i]);
		}

		for (Map.Entry<String, Class<?>> entry : mapGroupNamePanelClass
				.entrySet()) {
			getTabs().add(createTab(entry.getKey(), entry.getValue(), model));
		}
	}

	private void analyseMethod(Class<? extends Object> domainObjectClass,
			Map<String, Class<?>> mapGroupNamePanelClass, Method method) {
		Group annotation = method.getAnnotation(Group.class);

		if (annotation == null) {
			return;
		}

		String groupname = annotation.value();

		if (!mapGroupNamePanelClass.containsKey(groupname)) {
			mapGroupNamePanelClass.put(groupname,
					getPanelClass(domainObjectClass, groupname));
		}

	}

	private Class<?> getPanelClass(Class<? extends Object> domainObjectClass,
			String groupname) {

		String panelClassName = domainObjectClass.getName() + "_"
				+ groupname.replace(".", "_") + "Panel";

		Class<?> clazz = null;
		try {
			clazz = Class.forName(panelClassName);
		} catch (ClassNotFoundException e) {
			throw new NocketRuntimeException(e);
		}
		return clazz;
	}

	@SuppressWarnings("serial")
	protected ITab createTab(String groupname, final Class<?> panelClass,
			final IModel<?> model) {

		return new AbstractTab(Model.of(groupname)) {

			@SuppressWarnings("unchecked")
			@Override
			public WebMarkupContainer getPanel(String panelId) {
				Constructor<Panel> constructor;
				try {
					constructor = (Constructor<Panel>) panelClass
							.getConstructor(new Class<?>[] { String.class,
									IModel.class });
					Panel newInstance = constructor.newInstance(panelId, model);
					return newInstance;
				} catch (NoSuchMethodException|SecurityException|InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
					throw new NocketRuntimeException(e);
				}
			}
		};
	}

	@Override
	protected WebMarkupContainer newLink(String linkId, final int index) {
		final AjaxSubmitLink link = new AjaxSubmitLink(linkId) {

			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> form) {
				setSelectedTab(index);
				if (target != null) {
					target.add(target.getPage());
				}
				target.appendJavaScript(DMDOnClickIndicatorAttributeModifier
						.getBlockerRemoveScript());
				onAjaxUpdate(target);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.appendJavaScript(DMDOnClickIndicatorAttributeModifier
						.getBlockerRemoveScript());
				DMDWebGenGuiServiceProvider webGenGuiServiceProvider = NocketSession
						.get().getDMDWebGenGuiServiceProvider();
				DMDWebGenPageContext context = new DMDWebGenPageContext(
						getParent());
				try {
					webGenGuiServiceProvider.registerAjaxRequestTarget(context,
							target);
					SynchronizerHelper.updateAllForms(context, target);
				} finally {
					webGenGuiServiceProvider.unregisterAjaxRequestTarget(
							context, target);
				}
				super.onError(target, form);
			}
		};

		link.add(new DMDOnClickIndicatorAttributeModifier(this));
		return link;
	}

	@Override
	protected void onConfigure() {
		if (getSelectedTab() == -1) {
			// If no tab was selected, call GroupChoicer
			rearrangeByGroupChoicer();
			// and selecet the first tab
			setSelectedTab(0);
		} else {
			// check, if tab change is allowed
			allowTabChange();
			rearrangeByGroupChoicer();
		}

		lastSelectedTabIndex = getSelectedTab();
		super.onConfigure();
	}

	protected void rearrangeByGroupChoicer() {
		Method groupChoicer = ReflectionUtil
				.findGroupChoicer(domainObjectClass);

		if (groupChoicer != null) {
			configureTabsByCroupChoicer(groupChoicer);
			adjustSelectedTabIfNecessarry();
		}
	}

	protected void allowTabChange() {
		String oldTab = null;
		if (lastSelectedTabIndex != -1) {
			oldTab = ((AbstractTab) getTabs().get(lastSelectedTabIndex))
					.getTitle().getObject();
		}
		String newTab = ((AbstractTab) getTabs().get(getSelectedTab()))
				.getTitle().getObject();

		if (!allowTabChange(oldTab, newTab)) {
			if (lastSelectedTabIndex != -1) {
				setSelectedTab(lastSelectedTabIndex);
			}
		}
	}

	/**
	 * Returns true if the tab should be allowed to be changed. Override this
	 * methods if allow always is not the desired behavior.
	 * 
	 * @param oldTab
	 *            the group name of the tabs, that should be left
	 * @param newTab
	 *            the group name of the tabs, that shown next
	 * @return true if changing tabs is allowed, otherwise false
	 */
	protected boolean allowTabChange(String oldTab, String newTab) {
		return true;
	}

	private void adjustSelectedTabIfNecessarry() {
		if (lastSelectedTabIndex > getTabs().size() - 1) {
			setSelectedTab(getTabs().size() - 1);
		}

	}

	private void configureTabsByCroupChoicer(Method groupChoicer) {
		List<ITab> tabList = getTabs();
		tabList.clear();

		try {
			Object defaultModelObject = getPage().getDefaultModelObject();
			IModel<?> defaultModel = getPage().getDefaultModel();

			String[] groupnames = (String[]) groupChoicer.invoke(
					defaultModelObject, (Object[]) null);
			for (int i = 0; i < groupnames.length; i++) {
				Assert.notNull(mapGroupNamePanelClass.get(groupnames[i]),
						"Invalid group name in group choicer found: name = '"
								+ groupnames[i] + "'");
				getTabs().add(
						createTab(groupnames[i],
								mapGroupNamePanelClass.get(groupnames[i]),
								defaultModel));
			}
		} catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
			throw new NocketRuntimeException(e);
		}
	}

	@Override
	protected Component newTitle(final String titleId,
			final IModel<?> titleModel, final int index) {
		String key = (String) titleModel.getObject();
		IModel<String> model;
		if (isLocalizationWicket) {
			model = new ResourceModel(key, key).wrapOnAssignment(getPage());
		} else {
			model = new I18NLabelModel(domainObjectClass, key);
		}
		return new Label(titleId, model);
	}
}
