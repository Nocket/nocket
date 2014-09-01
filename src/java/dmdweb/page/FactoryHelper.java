package dmdweb.page;

import gengui.util.SevereGUIException;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.PageReference;
import org.apache.wicket.application.IClassResolver;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.page.CouldNotLockPageException;
import org.apache.wicket.page.PageAccessSynchronizer;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.time.Duration;

import dmdweb.NocketSession;
import dmdweb.util.SevereWebException;

/** Helper class */
public final class FactoryHelper {

    private final Object view;

    public FactoryHelper(Object view) {
        if (view == null)
            throw new SevereWebException("View specified for response page cannot be null.");
        this.view = view;
    }

    @SuppressWarnings("unchecked")
    public DMDWebPage getPageInstance() {
        DMDWebPage instance = null;
        List<PageReference> pageReferences = NocketSession.get().getPageReferences(view.hashCode());
        // Iteriert über alle PageReferences, die zu dem HashCode der View hinterlegt sind.
        // Dies ist im Normalfall genau eine PageReference, deshalb ist die Iteration hierüber nicht teuer 
        for (PageReference pageReference : pageReferences) {
            try {
                //try to lock failing fast: http://mail-archives.apache.org/mod_mbox/wicket-users/201211.mbox/%3CCANgwjP4xsMKo6kKjVSOOnf_qKvdV+nbhXh8bkZ0R6oZN1BS8YA@mail.gmail.com%3E
                PageAccessSynchronizer synchronizer = new PageAccessSynchronizer(Duration.ONE_SECOND);
                synchronizer.lockPage(pageReference.getPageId());
                Page page = pageReference.getPage();
                if (view.equals(page.getDefaultModel().getObject())) {
                    instance = (DMDWebPage) page;
                    //Das Modelobject muss ein sinnvolles Equals implementieren, dennoch kann sich innerhalb des 
                    //View-Objektes etwas geŠndert haben, deshalb muss hier Das ModelObject neu gesetzt werden
                    instance.modelChanging();
                    ((IModel<Object>) instance.getDefaultModel()).setObject(view);
                    instance.modelChanged();
                    break;
                }
                synchronizer.unlockPage(pageReference.getPageId());
            } catch (CouldNotLockPageException e) {
                //if lock not possible, ignore this page and worst case create a new one
            }
        }
        if (instance == null) {
            instance = newPageInstance();
        }
        storePageForView(instance);
        return instance;
    }

    private DMDWebPage newPageInstance() {
        DMDWebPage instance = null;
        Class<?> responsePage = null;
        try {
            responsePage = this.getPageClass();
            Constructor<?> constructor = responsePage.getConstructor(IModel.class);
            instance = (DMDWebPage) createObject(constructor, createModel(responsePage));
        } catch (NoSuchMethodException e) {
            String m = "Expected constructor " + responsePage.getSimpleName() + "(IModel<"
                    + view.getClass().getSimpleName() + ">) could not be found.";
            throw newSevereWebException(m, e);
        }
        return instance;
    }

    public Panel getPanelInstance(String id) {
        return newPanelInstance(id);
    }

    private Panel newPanelInstance(String id) {
        Object instance = null;
        Class<?> responsePanel = null;
        try {
            responsePanel = this.getPanelClass();
            Constructor<?> constructor = responsePanel.getConstructor(String.class, IModel.class);
            instance = createObject(constructor, id, new Model((Serializable) view));
        } catch (NoSuchMethodException e) {
            String m = "Expected constructor " + responsePanel.getSimpleName() + "(IModel<"
                    + view.getClass().getSimpleName() + ">) could not be found.";
            throw newSevereWebException(m, e);
        }
        return (Panel) instance;
    }

    /**
     * Erzeugt das Model für eine neue Page. Entweder gibt es in der Page-Klasse
     * eine statische Methode mit Namen 'createDefaultModel' oder es wir ein
     * Model.of(...) erezugt. Mit der createDefaultModel-Methode gibt es die
     * Möglichekeit ein eigenes Model (z.b. ein LoadableDetachableModel) zu
     * erzeugen.
     * 
     */
    private IModel<? extends Serializable> createModel(Class<?> responsePage) {
        IModel<? extends Serializable> result = null;
        try {
            Method method = responsePage.getMethod("createDefaultModel", new Class[0]);
            if (method != null) {
                Object invoke = method.invoke(null, new Object[0]);
                result = (IModel<? extends Serializable>) invoke;
            }
        } catch (NoSuchMethodException e) {

        } catch (SecurityException e) {
            throw new SevereGUIException(e);
        } catch (IllegalAccessException e) {
            throw new SevereGUIException(e);
        } catch (IllegalArgumentException e) {
            throw new SevereGUIException(e);
        } catch (InvocationTargetException e) {
            throw new SevereGUIException(e);
        }
        if (result == null) {
            result = Model.of((Serializable) view);
        }
        return result;
    }

    /**
     * Legt zum HashCode der View die PageReference der Page in der Session ab.
     * Falls es bereits eine PageReference zu der View in der Session gibt, wird
     * diese entfernt
     * 
     * @param page
     *            - the Page (PageReference) to store
     */
    public void storePageForView(DMDWebPage page) {
        // Remove other PageReferences for the same Object
        List<PageReference> pageReferences = NocketSession.get().getPageReferences(view.hashCode());
        //use copy to iterate to prevent ConcurrentModificationException
        List<PageReference> pageReferencesCopy = new ArrayList<PageReference>(pageReferences);
        for (PageReference pageReference : pageReferencesCopy) {
            try {
                //try to lock failing fast: http://mail-archives.apache.org/mod_mbox/wicket-users/201211.mbox/%3CCANgwjP4xsMKo6kKjVSOOnf_qKvdV+nbhXh8bkZ0R6oZN1BS8YA@mail.gmail.com%3E
                PageAccessSynchronizer synchronizer = new PageAccessSynchronizer(Duration.ONE_SECOND);
                synchronizer.lockPage(pageReference.getPageId());
                if (pageReference.getPage() != null && pageReference.getPage().getDefaultModel() != null
                        && view.equals(page.getDefaultModel().getObject())) {
                    pageReferences.remove(pageReference);
                    // We have stored only one PageReference for this view, so we can stop here
                    break;
                }
                //this should behave like a ReentrantLock
                synchronizer.unlockPage(pageReference.getPageId());
            } catch (CouldNotLockPageException e) {
                //if lock not possible, ignore this page, maybe the next call will remove this reference
            }

        }
        pageReferences.add(page.getPageReference());
    }

    public Class<DMDWebPage> getPageClass() {
        return getPageClass(view.getClass());
    }

    public static Class<DMDWebPage> getPageClass(Class<?> domainClass) {
        Class<DMDWebPage> responsePage = null;
        String classNamePageSuffix = getPageClassNameforView(domainClass, "page.");
        try {
            responsePage = loadPage(domainClass, classNamePageSuffix);
        } catch (ClassNotFoundException e) {
            String classNameNoSuffix = getPageClassNameforView(domainClass, "");
            try {
                responsePage = loadPage(domainClass, classNameNoSuffix);
            } catch (ClassNotFoundException e1) {
                try {
                    // One last chance: If we have an InMemoryClassResolver installed we
                    // can create the page class on-the-fly in memory
                    IClassResolver classResolver = WebApplication.get().getApplicationSettings().getClassResolver();
                    if (classResolver instanceof InMemoryClassResolver) {
                        InMemoryClassResolver inMemoryResolver = (InMemoryClassResolver) classResolver;
                        responsePage = (Class<DMDWebPage>) inMemoryResolver.buildPageClassInMemory(domainClass);
                    } else {
                        throw new ClassNotFoundException();
                    }
                } catch (ClassNotFoundException e2) {
                    String m = "Expected class " + classNamePageSuffix + " or " + classNameNoSuffix
                            + " could not be found.";
                    throw newSevereWebException(domainClass, m, e1);
                }
            }
        }
        return responsePage;
    }

    public Class<Panel> getPanelClass() {
        return getPanelClass(view.getClass());
    }

    public Class<Panel> getPanelClass(Class<?> domainClass) {
        Class<Panel> responsePage = null;
        String classNamePageSuffix = getPanelClassNameforView(domainClass, "panel.");
        try {
            responsePage = loadPanel(classNamePageSuffix);
        } catch (ClassNotFoundException e) {
            String classNameNoSuffix = getPanelClassNameforView(domainClass, "");
            try {
                responsePage = loadPanel(classNameNoSuffix);
            } catch (ClassNotFoundException e1) {
                try {
                    // One last chance: If we have an InMemoryClassResolver installed we
                    // can create the panel class on-the-fly in memory
                    IClassResolver classResolver = WebApplication.get().getApplicationSettings().getClassResolver();
                    if (classResolver instanceof InMemoryClassResolver) {
                        InMemoryClassResolver inMemoryResolver = (InMemoryClassResolver) classResolver;
                        responsePage = (Class<Panel>) inMemoryResolver.buildPanelClassInMemory(view.getClass());
                    } else {
                        throw new ClassNotFoundException();
                    }
                } catch (ClassNotFoundException e2) {
                    String m = "Expected class " + classNamePageSuffix + " or " + classNameNoSuffix
                            + " could not be found.";
                    throw newSevereWebException(m, e1);
                }
            }
        }
        return responsePage;
    }

    @SuppressWarnings("unchecked")
    private static Class<DMDWebPage> loadPage(Class<?> domainClass, String className) throws ClassNotFoundException {
        Class<DMDWebPage> responsePage;
        responsePage = (Class<DMDWebPage>) Class.forName(className);
        if (!DMDWebPage.class.isAssignableFrom(responsePage))
            throw newSevereWebException(domainClass, "Expected class " + className + " must extend DMDWebPage.", null);
        return responsePage;
    }

    @SuppressWarnings("unchecked")
    private Class<Panel> loadPanel(String className) throws ClassNotFoundException {
        Class<Panel> responsePanel;
        responsePanel = (Class<Panel>) Class.forName(className);
        if (!Panel.class.isAssignableFrom(responsePanel))
            throw newSevereWebException("Expected class " + className + " must extend Panel.", null);
        return responsePanel;
    }

    private Object createObject(Constructor<?> constructor, Object... initargs) {
        Object res = null;
        try {
            res = constructor.newInstance(initargs);
        } catch (Exception e) {
            throw newSevereWebException(null, e);
        }
        return res;
    }

    private static String getPageClassNameforView(Class<?> domainClass, String suffixPackage) {
        String srcClassName = domainClass.getName();
        String srcSimpleName = domainClass.getSimpleName();
        return srcClassName.replaceAll(srcSimpleName + "$", suffixPackage + srcSimpleName + "Page");
    }

    private static String getPanelClassNameforView(Class<?> domainClass, String suffixPackage) {
        String srcClassName = domainClass.getName();
        String srcSimpleName = domainClass.getSimpleName();
        return srcClassName.replaceAll(srcSimpleName + "$", suffixPackage + srcSimpleName + "Panel");
    }

    private SevereWebException newSevereWebException(String msg, Throwable e) {
        throw newSevereWebException(view.getClass(), msg, e);
    }

    private static SevereWebException newSevereWebException(Class<?> domainClass, String msg, Throwable e) {
        final String m = "Cannot create response page for view class " + domainClass.getCanonicalName() + ". ";
        return new SevereWebException((msg == null ? m : m + msg), e);
    }

}