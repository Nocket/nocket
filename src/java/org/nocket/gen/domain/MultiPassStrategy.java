package org.nocket.gen.domain;

import gengui.annotations.Group;
import gengui.domain.AbstractDomainReference;
import gengui.util.ReflectionUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nocket.gen.domain.ref.WrappedDomainReferenceI;
import org.nocket.gen.domain.visitor.DomainElementVisitorI;

/**
 * Im Gegensatz zur @see org.nocket.gen.domain.SinglePassStrategy, die benutzt wird,
 * wenn die @see DomainRegistry aufgebaut wird, wird die MultiPassStrategy bei
 * der Generierung der HTML- und Java-Artefakte benutzt. Hat ein Domain-Objekt
 * Attribute, die mit der @see gengui.annotations.Group-Annotation versehen
 * sind, so muss diesem Domain-Objekt mehr als ein HTML-File erzeugt werden. Es
 * entsteht das HTML für die Page und ein oder mehrere HTML-Seiten für die
 * Panels, die für die @see GeneratedGroupTabbedPanel Komponente benötigt
 * werden. <br>
 * <br>
 * Weiterführende Beschreibung @see org.nocket.gen.domain.DomainProcessorStrategy
 * 
 * @author meis026
 * 
 * @param <E>
 */
public abstract class MultiPassStrategy<E extends AbstractDomainReference> extends
        DomainProcessorStrategy<E> {

    private GroupNameFileAndClassNameStrategy<E> currentFileStrategy;

    public abstract DomainElementVisitorI<E> createVisitor(DMDWebGenContext<E> dmdWebGenContext);

    @Override
    protected void process(DMDWebGenContext<E> context, WrappedDomainReferenceI<E> ref) {
        Method[] propertyMethods = ReflectionUtil.extractOrderedPropertyMethods(ref.getRef(), true);

        Map<String, GroupNameFileAndClassNameStrategy<E>> checkForGlobalAnnotations = checkForGlobalAnnotations(
                context,
                ref,
                propertyMethods);

        for (GroupNameFileAndClassNameStrategy<E> strategy : checkForGlobalAnnotations.values()) {
            visitor = createVisitor(context);
            currentFileStrategy = strategy;
            context.setFileAndClassNameStrategy(currentFileStrategy);
            processDomainClass(context, ref);
            visitor.finish();
        }
    }

    @Override
    protected void processDomainClass(DMDWebGenContext<E> context, WrappedDomainReferenceI<E> ref) {
        processPass(context, ref, currentFileStrategy.getMethodsForRef(ref));
    }

    private Map<String, GroupNameFileAndClassNameStrategy<E>> checkForGlobalAnnotations(
            DMDWebGenContext<E> context, WrappedDomainReferenceI<E> ref, Method[] propertyMethods) {
        Map<String, GroupNameFileAndClassNameStrategy<E>> result = new HashMap<String, GroupNameFileAndClassNameStrategy<E>>();

        // If there is no nothing in the domain objects, create just the main page
        if (propertyMethods == null || propertyMethods.length == 0) {
            GroupNameFileAndClassNameStrategy<E> data = new GroupNameFileAndClassNameStrategy<E>(context, ref, "");
            result.put("", data);
            return result;
        }

        for (int i = 0; propertyMethods != null && i < propertyMethods.length; i++) {
            Method method = propertyMethods[i];

            Group groupAnnotation = method.getAnnotation(Group.class);
            String name = groupAnnotation != null ? groupAnnotation.value() : "";

            GroupNameFileAndClassNameStrategy<E> data = result.get(name);

            if (data == null) {
                data = new GroupNameFileAndClassNameStrategy<E>(context, ref, name);
                result.put(name, data);
            }
            data.methods.add(method);
        }

        return result;
    }

    protected Method[] extractOrderedButtonMethods(WrappedDomainReferenceI<E> ref, boolean withCollectionMethods) {
        Method[] extractOrderedButtonMethods = ReflectionUtil.extractOrderedButtonMethods(ref.getRef(),
                withCollectionMethods);

        return filterMethodsByGroupAnnotationAndGroupname(extractOrderedButtonMethods);

    }

    protected Method[] filterMethodsByGroupAnnotationAndGroupname(Method[] extractOrderedButtonMethods) {
        if (extractOrderedButtonMethods == null) {
            return null;
        }

        List<Method> copiedList = new ArrayList<Method>();
        for (int i = 0; i < extractOrderedButtonMethods.length; i++) {
            Method method = extractOrderedButtonMethods[i];
            if (currentFileStrategy.hasMatchingGroupAnnotation(method)) {
                copiedList.add(method);
            }
        }
        return copiedList.toArray(new Method[0]);
    }

    @Override
    protected Method[] extractOrderedCollectionButtonMethods(WrappedDomainReferenceI<E> subRef) {
        Method[] buttonMethods = ReflectionUtil.extractOrderedCollectionButtonMethods(subRef.getRef());
        return filterMethodsByGroupAnnotationAndGroupname(buttonMethods);
    }
}