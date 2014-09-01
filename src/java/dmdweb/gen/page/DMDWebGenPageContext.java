package dmdweb.gen.page;

import gengui.domain.DomainObjectReference;
import gengui.guiadapter.ElementNotFoundException;
import gengui.util.DomainProperties.JfdRetentionStrategy;
import gengui.util.SevereGUIException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.core.util.resource.locator.IResourceStreamLocator;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import dmdweb.gen.domain.DMDWebGen;
import dmdweb.gen.domain.DMDWebGenContext;
import dmdweb.gen.domain.DomainProcessor;
import dmdweb.gen.domain.HTMLDocumentCachingPolicy;
import dmdweb.gen.domain.WebDomainProperties;
import dmdweb.gen.domain.ref.DomainObjectReferenceFactory;
import dmdweb.gen.domain.visitor.registry.DomainRegistry;
import dmdweb.gen.domain.visitor.registry.DomainRegistryVisitor;
import dmdweb.gen.page.element.ModalElement;
import dmdweb.gen.page.element.synchronizer.SynchronizerHelper;
import dmdweb.gen.page.element.synchronizer.error.AjaxAlertMethodExceptionHandler;
import dmdweb.gen.page.element.synchronizer.error.MethodExceptionHandlerI;
import dmdweb.gen.page.guiservice.TouchedRegistry;
import dmdweb.gen.page.guiservice.TouchedRegistryData;
import dmdweb.gen.page.visitor.bind.ComponentRegistry;
import dmdweb.gen.page.visitor.bind.builder.BindingInterceptor;
import dmdweb.gen.page.visitor.registry.PageRegistry;
import dmdweb.gen.page.visitor.registry.PageRegistryVisitor;
import dmdweb.gen.resources.DMDCachingResourceStreamLocator;
import dmdweb.page.DMDWebPage;

public class DMDWebGenPageContext implements Serializable {

    public static final MetaDataKey<DMDWebGenPageContext> CONTEXT_KEY = new MetaDataKey<DMDWebGenPageContext>() {
        private static final long serialVersionUID = 1L;
    };

    private static MethodExceptionHandlerI defaultMethodExceptionHandler;
    private static final List<BindingInterceptor> defaultBindingInterceptors = new ArrayList<BindingInterceptor>();
    private static final Map<String, Document> documentCache = new HashMap<String, Document>();
    private static final Map<String, DomainRegistry<DomainObjectReference>> domainRegistryPrototypes = new HashMap<String, DomainRegistry<DomainObjectReference>>();

    private final TouchedRegistryData touchedRegistryData;
    private final TouchedRegistry touchedRegistry;
    private MethodExceptionHandlerI methodExceptionHandler;
    private transient Boolean pageUpdated;
    private MarkupContainer page;
    private transient DomainObjectReferenceFactory refFactory;
    private transient DomainRegistry<DomainObjectReference> domainRegistry;
    private transient PageRegistry pageRegistry;
    private transient ComponentRegistry componentRegistry;
    private transient Document htmlDocument;
    private transient List<Component> inheritedChildren = new ArrayList<Component>();
    private transient WebDomainProperties configuration;
    private transient Behavior resetAfterRenderBehaviour = new Behavior() {
        @Override
        public void afterRender(Component component) {
            super.afterRender(component);
            //domainreference needs to be reset on ajax updates
            refFactory = null;
            domainRegistry = null;
        }
    };

    static {
        defaultMethodExceptionHandler = new AjaxAlertMethodExceptionHandler();
    }

    public DMDWebGenPageContext(MarkupContainer page) {
        this.page = page;
        if (dmdweb.page.DMDWebPage.class.isAssignableFrom(page.getClass())) {
            ((DMDWebPage) page).setPagecontext(this);
        }
        page.add(resetAfterRenderBehaviour);
        this.pageUpdated = true;
        this.componentRegistry = new ComponentRegistry();
        // initial pageRegistry gets filled during first bind run
        this.pageRegistry = new PageRegistry();
        this.touchedRegistryData = new TouchedRegistryData();
        this.touchedRegistry = new TouchedRegistry(this);
        this.page.setMetaData(CONTEXT_KEY, this);
        registerInheritedChildren();
    }

    protected void registerInheritedChildren() {
        page.visitChildren(new IVisitor<Component, Object>() {
            @Override
            public void component(Component child, IVisit<Object> visit) {
                if (child.getParent() == page) {
                    inheritedChildren.add(child);
                }
            }
        });
    }

    public void updatePage(AjaxRequestTarget target) {
        MarkupContainer root = SynchronizerHelper.findRoot(target.getPage());
        IVisitor<Component, Object> visitor = new IVisitor<Component, Object>() {
            @Override
            public void component(Component object, IVisit<Object> visit) {
                if (object instanceof MarkupContainer && object.getClass().equals(page.getClass())
                        && StringUtils.equals(page.getId(), object.getId())) {
                    DMDWebGenPageContext.this.page = (MarkupContainer) object;
                    pageUpdated = true;
                }
            }
        };
        visitor.component(root, null);
        root.visitChildren(visitor);
        if (pageUpdated == null) {
            pageUpdated = false;
        }
    }

    public static void setDefaultMethodExceptionHandler(MethodExceptionHandlerI defaultMethodExceptionHandler) {
        DMDWebGenPageContext.defaultMethodExceptionHandler = defaultMethodExceptionHandler;
    }

    public static List<BindingInterceptor> getDefaultBindingInterceptors() {
        return defaultBindingInterceptors;
    }

    public MethodExceptionHandlerI getMethodExceptionHandler() {
        if (methodExceptionHandler != null) {
            return methodExceptionHandler;
        } else {
            return defaultMethodExceptionHandler;
        }
    }

    public void setMethodExceptionHandler(MethodExceptionHandlerI methodExceptionHandler) {
        this.methodExceptionHandler = methodExceptionHandler;
    }

    private DomainObjectReferenceFactory loadRefFactory() {
        return new DomainObjectReferenceFactory(getPage().getDefaultModelObject());
    }

    private DomainRegistry<DomainObjectReference> loadDomainRegistry() {
        // Wicket's development mode allows to reload classes at runtime, so in that mode
        // we must not cache any data structures which depend on class structures. The
        // domain registry prototypes are such a candidate.
        if (WebApplication.get().getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT)
            return constructDomainRegistry();

        synchronized (DMDWebGenPageContext.class) {
            String domainClassName = page.getDefaultModelObject().getClass().getName();
            DomainRegistry<DomainObjectReference> registry = domainRegistryPrototypes.get(domainClassName);
            if (registry == null) {
                registry = constructDomainRegistry();
                domainRegistryPrototypes.put(domainClassName, registry);
                return registry;
            } else {
                // Construct a new DomainRegistry from an existing prototype rather than from
                // traversing the domain class structure. Creation from prototype is much faster.
                return registry.replicate(page.getDefaultModelObject());
            }
        }
    }

    protected DomainRegistry<DomainObjectReference> constructDomainRegistry() {
        DomainRegistry<DomainObjectReference> registry;
        DMDWebGenContext<DomainObjectReference> ctx = new DMDWebGenContext<DomainObjectReference>(null, null,
                null, null, getRefFactory());
        DomainRegistryVisitor<DomainObjectReference> visitor = new DomainRegistryVisitor<DomainObjectReference>(
                ctx);
        new DomainProcessor<DomainObjectReference>(ctx, visitor).process();
        registry = visitor.getDomainRegistry();
        return registry;
    }

    private Document loadDocument() {
        synchronized (DMDWebGenPageContext.class) {
            String pagePath = htmlPath(getPage().getClass());
            WebDomainProperties props = getConfiguration();
            HTMLDocumentCachingPolicy cachingPolicy = props.getHTMLDocumentCachingPolicy();
            Document document = readCachedDocument(getPage().getClass(), props);
            if (document == null) {
                IResourceStream rstream = getInputStreamForHtmlFile(getPage().getClass(), props);
                InputStream in = openResourceStream(rstream);
                if (in == null) {
                    in = createHtmlFileOnTheFly(props);
                }
                try {
                    String html = IOUtils.toString(in);
                    document = Jsoup.parse(html);
                    if (cachingPolicy != HTMLDocumentCachingPolicy.none)
                        documentCache.put(pagePath, document);
                } catch (IOException e) {
                    throw new SevereGUIException(e);
                } finally {
                    IOUtils.closeQuietly(in);
                }
            }
            return document;
        }
    }

    public WebDomainProperties getConfiguration() {
        synchronized (DMDWebGenPageContext.class) {
            if (configuration == null)
                configuration = new WebDomainProperties(getRefFactory().getRootReference().getClassRef());
        }
        return configuration;
    }

    protected Document readCachedDocument(Class<? extends MarkupContainer> pageClass, WebDomainProperties props) {
        String pagePath = htmlPath(pageClass);
        HTMLDocumentCachingPolicy cachingPolicy = props.getHTMLDocumentCachingPolicy();
        switch (cachingPolicy) {
        case none:
            return null;
        case age:
            IResourceStream rstream = getInputStreamForHtmlFile(pageClass, props);
            if (rstream == null)
                return null;
            //TODO JL: HERE WE ARE!!!!!!!!!!!!!!!!!
            // Fall through
        default:
            return documentCache.get(pagePath);
        }
    }

    protected InputStream createHtmlFileOnTheFly(WebDomainProperties props) {
        IResourceStreamLocator locator = Application.get().getResourceSettings().getResourceStreamLocator();
        // Without a DMDCachinResourceStreamLocator we could create the HTML but we could
        // not force a re-allocation of the created file.
        if (locator instanceof DMDCachingResourceStreamLocator) {
            DMDCachingResourceStreamLocator dmdLocator = (DMDCachingResourceStreamLocator) locator;
            Class<? extends MarkupContainer> pageClass = getPage().getClass();
            dmdLocator.drop(pageClass, htmlLookupPaths(pageClass));
            JfdRetentionStrategy strategy = props.getJFDRetentionStrategy();
            if (strategy != JfdRetentionStrategy.none) {
                runHtmlGenerator(props);
                IResourceStream rstream = getInputStreamForHtmlFile(pageClass, props);
                InputStream in = openResourceStream(rstream);
                if (in != null)
                    return in;
            }
        }
        throw new SevereGUIException("No HTML file found for page " + getPage().getClass());
    }

    public boolean mergeHtmlFileOnTheFly() {
        WebDomainProperties props = getConfiguration();
        if (props.getJFDRetentionStrategy() != JfdRetentionStrategy.merge &&
                props.getJFDRetentionStrategy() != JfdRetentionStrategy.silentmerge)
            return false;
        runHtmlGenerator(props);
        return true;
    }

    protected void runHtmlGenerator(WebDomainProperties props) {
        Class<? extends MarkupContainer> pageClass = getPage().getClass();
        Class<?> domainClass = getRefFactory().getRootReference().getClassRef().getDomainClass();
        boolean generatePanel = props.getHTMLPanelBaseClass().isAssignableFrom(pageClass);
        new DMDWebGen().generateHTML(domainClass, generatePanel, null, null, null, null);
    }

    protected InputStream openResourceStream(IResourceStream rstream) {
        if (rstream == null)
            return null;
        try {
            return rstream.getInputStream();
        } catch (ResourceStreamNotFoundException rsnfx) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    protected IResourceStream getInputStreamForHtmlFile(Class<? extends MarkupContainer> pageClass,
            WebDomainProperties props) {
        IResourceStream rstream = null;
        IResourceStreamLocator resourceStreamLocator = Application.get().getResourceSettings()
                .getResourceStreamLocator();
        for (String htmlLookupPath : htmlLookupPaths(pageClass)) {
            rstream = fetchStream(pageClass, htmlLookupPath, resourceStreamLocator);
            if (rstream != null)
                break;
        }
        if (rstream == null) {
            Class<?> superclass = pageClass.getSuperclass();
            if (superclass != props.getHTMLPageBaseClass() &&
                    MarkupContainer.class.isAssignableFrom(superclass)) {
                rstream = getInputStreamForHtmlFile((Class<? extends MarkupContainer>) superclass, props);
            }
        }
        return rstream;
    }

    private String htmlPath(Class<? extends MarkupContainer> pageClass) {
        return pageClass.getName().replace(".", "/") + ".html";
    }

    private String[] htmlLookupPaths(Class<? extends MarkupContainer> pageClass) {
        String htmlPath = htmlPath(pageClass);
        return new String[] { htmlPath, "/" + htmlPath };
    }

    private IResourceStream fetchStream(Class<? extends MarkupContainer> clazz, String pagePath,
            IResourceStreamLocator resourceStreamLocator) {
        return resourceStreamLocator.locate(clazz, pagePath);
    }

    public MarkupContainer getPage() {
        if (pageUpdated == null || pageUpdated == false) {
            AjaxRequestTarget target = RequestCycle.get().find(AjaxRequestTarget.class);
            if (target != null) {
                updatePage(target);
            }
        }
        return page;
    }

    public DomainObjectReferenceFactory getRefFactory() {
        if (refFactory == null) {
            refFactory = loadRefFactory();
        }
        return refFactory;
    }

    public DomainRegistry<DomainObjectReference> getDomainRegistry() {
        if (domainRegistry == null) {
            domainRegistry = loadDomainRegistry();
        }
        return domainRegistry;
    }

    public PageRegistry getPageRegistry() {
        if (pageRegistry == null) {
            pageRegistry = new PageRegistry();
            // page registry has to be refilled again since it was discarded in
            // the meantime
            try {
                new PageProcessor(new PageRegistryVisitor(this)).process();
            } catch (ElementNotFoundException enfx) {
                throw new SevereGUIException(enfx);
            }
        }
        return pageRegistry;
    }

    public static ComponentRegistry getComponentRegistry(MarkupContainer page) {
        final ComponentRegistry result = new ComponentRegistry();
        MarkupContainer root = SynchronizerHelper.findRoot(page);
        root.visitChildren(new IVisitor<Component, Object>() {
            @Override
            public void component(Component object, IVisit<Object> visit) {
                if (object.getId().equals(ModalElement.DEFAULT_WICKET_ID)) {
                    //use the very first instance of this component!
                    if (result.getComponent(object.getId()) == null) {
                        result.addComponent(object);
                        visit.stop();
                    }
                }
            }
        });
        page.visitChildren(new IVisitor<Component, Object>() {
            @Override
            public void component(Component object, IVisit<Object> visit) {
                if (result.getComponent(object.getId()) == null) {
                    result.addComponent(object);
                }
            }
        });
        return result;
    }

    public ComponentRegistry getComponentRegistry() {
        if (componentRegistry == null) {
            componentRegistry = getComponentRegistry(getPage());
        }
        return componentRegistry;
    }

    public Document getHtmlDocument() {
        if (htmlDocument == null) {
            htmlDocument = loadDocument();
        }
        return htmlDocument;
    }

    public TouchedRegistryData getTouchedRegistryData() {
        return touchedRegistryData;
    }

    public TouchedRegistry getTouchedRegistry() {
        return touchedRegistry;
    }

    @Deprecated
    /**
     * This method is not really deprecated but the very opposite: it is not yet fully functional
     */
    public void rebind(final MarkupContainer newPage) {
        // Alle direkten Kinder übertragen, welche die Klasse *nicht* von der Basisklasse geerbt hat
        page.visitChildren(new IVisitor<Component, Object>() {
            @Override
            public void component(Component child, IVisit<Object> visit) {
                if (child.getParent() == page && !inheritedChildren.contains(child)) {
                    newPage.add(child);
                }
            }
        });
        this.page = newPage;
        //TODO JL: There are probably some more things which need to be reseted
        pageUpdated = true;
        refFactory = null;
        //TODO JL: What about the DomainRegistry? It looks to be an expensive thing to
        // re-create as it traverses the class structure. This is not necessary most of the
        // time as the class structure changes only rarely at runtime. Only in case Wicket
        // reloads the classes in development mode. 
    }

    public void clearPage() {
        page.remove(resetAfterRenderBehaviour);
        final List<Component> nonInheritedChildren = new ArrayList<Component>();
        page.visitChildren(new IVisitor<Component, Object>() {
            @Override
            public void component(Component child, IVisit<Object> visit) {
                if (child.getParent() == page && !inheritedChildren.contains(child)) {
                    nonInheritedChildren.add(child);
                }
            }
        });
        for (Component child : nonInheritedChildren)
            page.remove(child);
    }

    public boolean acceptMissingElements() {
        return getConfiguration().getJFDRetentionStrategy() == JfdRetentionStrategy.silentmerge;
    }

    /**
     * Returns true if the GUI represented by this context, is a page rather
     * than a (modal) dialog. This is derived from the page attribute's base
     * class which differs between pages and (modal) panels
     */
    public boolean isPage() {
        return getConfiguration().getHTMLPageBaseClass().isAssignableFrom(page.getClass());
    }

    //    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    //        System.out.println("DMDWebGenPageContext deserialized");
    //        s.defaultReadObject();
    //    }

}
