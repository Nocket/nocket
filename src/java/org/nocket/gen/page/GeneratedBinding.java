package org.nocket.gen.page;

import gengui.annotations.Group;
import gengui.domain.DomainObjectReference;
import gengui.guiadapter.ElementNotFoundException;
import gengui.util.ReflectionUtil;
import gengui.util.SevereGUIException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.MarkupContainer;
import org.nocket.NocketSession;
import org.nocket.gen.domain.element.DomainElementI;
import org.nocket.gen.page.inject.PageComponentInjection;
import org.nocket.gen.page.visitor.GeneratedBindingVisitor;
import org.nocket.gen.page.visitor.PageElementVisitorI;
import org.nocket.gen.page.visitor.bind.builder.BindingInterceptor;

public class GeneratedBinding {

    private transient DMDWebGenPageContext context;
    private transient MarkupContainer page;
    private transient boolean rebound;
    private List<BindingInterceptor> interceptors = new ArrayList<BindingInterceptor>();
    private List<PageElementVisitorI> additionalPageVisitors = new ArrayList<PageElementVisitorI>();

    /**
     * TODO JL: Using this cache is still experimental as there are some open
     * issues about it. It would be the fastest way of setting up a new page's
     * binding and it is based on the fact that all internal data structures
     * referred by a DMDWebGenPageContext (components, models, registries, etc)
     * can completely be reused. Any access to the underlying domain POJO is
     * based on the persistent relation between the DMDWebGenPageContext and the
     * page's default model object (which is the POJO). All other relations are
     * transient and refer to this one and only persistent relationship. See the
     * UML model for the internal structures as a prove. However, the ere are
     * the following issues to complete:
     * <ul>
     * <li>When reinitializing the {@link DMDWebGenPageContext} with the new
     * page (see method {@link DMDWebGenPageContext#rebind(MarkupContainer)}),
     * any existing transient relationships in all dependent objects must
     * somehow be reseted.
     * <li>In case of a repeating panel element, we need multiple bindings for
     * the same type at the same time. In this case the
     * {@link DMDWebGenPageContext} must somehow be cloned. Ini particular we
     * must find out if cloning is necessary. That's quite the same challenge as
     * in gengui's concept of reconnection and replication.
     * <ul>
     * As the context caching is still in an experimental state, the caching is
     * by default switched off, i.e. the context map in here is empty.
     */
    protected static final Map<String, DMDWebGenPageContext> contextCache = new HashMap<String, DMDWebGenPageContext>();

    public GeneratedBinding(MarkupContainer page, boolean allowCaching) {
        this.page = page;
        if (!allowCaching) {
            this.context = new DMDWebGenPageContext(page);
        }
        else {
            synchronized (GeneratedBinding.class) {
                String pageClassName = page.getClass().getName();
                this.context = contextCache.get(pageClassName);
                if (context == null) {
                    this.context = new DMDWebGenPageContext(page);
                    contextCache.put(pageClassName, context);
                }
                else {
                    rebound = true;
                }
            }
        }
    }

    public GeneratedBinding(MarkupContainer page) {
        this(page, false);
    }

    public DMDWebGenPageContext getContext() {
        return context;
    }

    public GeneratedBinding withVisitors(PageElementVisitorI... visitors) {
    	return withVisitors(Arrays.asList(visitors));
    }

    public GeneratedBinding withVisitors(List<PageElementVisitorI> visitors) {
    	this.additionalPageVisitors.addAll(visitors);
    	return this;
    }

    /**
     * Interceptors will be called in the order in which they have been added.
     * The first one to provide a component instead of null will get used.
     * 
     * As a fallback when no interceptor provided a component, the default
     * binding is used then.
     */
    public GeneratedBinding withInterceptors(BindingInterceptor... interceptors) {
        return withInterceptors(Arrays.asList(interceptors));
    }

    /**
     * Interceptors will be called in the order in which they have been added.
     * The first one to provide a component instead of null will get used.
     * 
     * As a fallback when no interceptor provided a component, the default
     * binding is used then.
     */
    public GeneratedBinding withInterceptors(List<BindingInterceptor> interceptors) {
        this.interceptors.addAll(interceptors);
        return this;
    }

    /**
     * Interceptors will be called in the order in which they have been added.
     * The first one to provide a component instead of null will get used.
     * 
     * As a fallback when no interceptor provided a component, the default
     * binding is used then.
     */
    public List<BindingInterceptor> getInterceptors() {
        return interceptors;
    }

    public void bind() {
        if (!rebound) {
            GeneratedBindingVisitor visitor = new GeneratedBindingVisitor(this, additionalPageVisitors.toArray(new PageElementVisitorI[0]));
            try {
                new PageProcessor(visitor).process();
            } catch (ElementNotFoundException enfx1) {
                if (!getContext().acceptMissingElements() && !checkMethodAndLookForGlobalAnnotation(enfx1.getMessage())) {
                    if (getContext().mergeHtmlFileOnTheFly()) {
                        context.clearPage();
                        this.context = new DMDWebGenPageContext(page);
                        visitor = new GeneratedBindingVisitor(this);
                        try {
                            new PageProcessor(visitor).process();
                        } catch (ElementNotFoundException enfx2) {
                            throw new SevereGUIException(enfx2);
                        }
                    }
                    else {
                        throw new SevereGUIException(enfx1);
                    }
                }
            }
        }
        else {
            this.getContext().rebind(page);
        }
        new PageComponentInjection(context).inject();
        NocketSession.get().getDMDWebGenGuiServiceProvider().onGeneratedBinding(context);
    }

    /**
     * Check if behind this wicketid is a method with an group annotation. The
     * whole path will be analysed.
     * 
     * @param wicketId
     * @return true if it is a annotated method behind this wicket:id
     */
    private boolean checkMethodAndLookForGlobalAnnotation(String wicketId) {
        DomainElementI<DomainObjectReference> element = context.getDomainRegistry().getElement(wicketId);

        if (element == null) {
            return false;
        }

        if (element.getMethod().getAnnotation(Group.class) != null) {
            return true;
        }

        Class<?> clazz = context.getPage().getDefaultModelObject().getClass();
        if (checkForGlobalAnnotationInPath(wicketId, clazz)) {
            return true;
        }

        String name = context.getPage().getClass().getSimpleName();
        int index = name.indexOf("_");
        if (index > -1 && name.endsWith("Panel")) {
            String panelGetterName = StringUtils.substring(name, index + 1, -5);
            if (StringUtils.isNotBlank(panelGetterName) && checkForGlobalAnnotationInObject(panelGetterName, clazz)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkForGlobalAnnotationInObject(String panelGetterName, Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        // Determing the name of the panel class during the generation process, the value of the group annotation will be used.
        // If the value contains '.' they have to be replaced by '_'.
        // For a comparison it is necessary to withdraw this change
        panelGetterName = panelGetterName.replace("_", ".");
        for (Method method : methods) {
            Group groupAnnotation = method.getAnnotation(Group.class);
            if (groupAnnotation != null && StringUtils.equalsIgnoreCase(panelGetterName, groupAnnotation.value())) {
                return true;
            }
        }
        return false;
    }

    protected boolean checkForGlobalAnnotationInPath(String wicketId, Class<?> clazz) {
        String[] parts = StringUtils.split(wicketId, ".");
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];

            Method method = ReflectionUtil.findGetterMethod(clazz, part);
            if (method == null) {
                // if there is no getter, check a button method
                method = ReflectionUtil.findMethod(clazz, part);
            }

            if (method == null) {
                // if there is no method, there will be no group annotation
                return false;
            } else if (method.getAnnotation(Group.class) != null) {
                return true;
            }

            // check the return type for global annotation
            clazz = method.getReturnType();
        }
        return false;
    }

}
