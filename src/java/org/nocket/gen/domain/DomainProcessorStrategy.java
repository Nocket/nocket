package org.nocket.gen.domain;

import gengui.annotations.Choicetype;
import gengui.domain.AbstractDomainReference;
import gengui.util.ReflectionUtil;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.nocket.gen.domain.element.ButtonElement;
import org.nocket.gen.domain.element.CheckboxPropertyElement;
import org.nocket.gen.domain.element.ChoicerPropertyElement;
import org.nocket.gen.domain.element.HeadlineElement;
import org.nocket.gen.domain.element.HiddenPropertyElement;
import org.nocket.gen.domain.element.MultivalueButtonElement;
import org.nocket.gen.domain.element.MultivaluePropertyElement;
import org.nocket.gen.domain.element.ResourceElement;
import org.nocket.gen.domain.element.SimplePropertyElement;
import org.nocket.gen.domain.ref.WrappedDomainReferenceI;
import org.nocket.gen.domain.visitor.DomainElementVisitorI;
import org.nocket.gen.page.element.synchronizer.SynchronizerHelper;

/**
 * Die DomainProcessorStrategy traversiert über das Domain-Objekt und ruft
 * Visitor auf (siehe Visitor-Pattern).
 * 
 * @author meis026
 * 
 * @param <E>
 */
public class DomainProcessorStrategy<E extends AbstractDomainReference> {

    protected DomainElementVisitorI<E> visitor;

    protected void process(DMDWebGenContext<E> context, WrappedDomainReferenceI<E> ref) {
        processDomainClass(context, ref);
    }

    protected void processDomainClass(DMDWebGenContext<E> context, WrappedDomainReferenceI<E> ref) {
        Method[] propertyMethods = ReflectionUtil.extractOrderedPropertyMethods(ref.getRef(), true);
        if (propertyMethods == null) {
            return;
        }

        List<Method> methodList = Arrays.asList(propertyMethods);

        processPass(context, ref, methodList);
        visitor.finish();
    }

    protected void processPass(DMDWebGenContext<E> context, WrappedDomainReferenceI<E> ref, List<Method> methods) {
        List<Method> subPropertyMethods = new ArrayList<Method>();
        Map<Method, Boolean> multivaluePropertyMethods_withButtons = new HashMap<Method, Boolean>();

        // 1. Choicer and Simple
        for (Method method : methods) {
            if (ReflectionUtil.isHiddenProperty(method.getName(), ref.getRef())) {
                new HiddenPropertyElement<E>(ref, method).accept(visitor);
            } else if (!ReflectionUtil.isSimpleType(method) && !ReflectionUtil.isMultivalued(method)
                    && !ReflectionUtil.hasChoicer(ref.getRef(), method)) {
                subPropertyMethods.add(method);
            } else {
                if (ReflectionUtil.isMultivalued(method) && !ReflectionUtil.hasChoicer(ref.getRef(), method)) {
                    multivaluePropertyMethods_withButtons.put(method, true);
                } else if (method.getReturnType().isEnum() || ReflectionUtil.hasChoicer(ref.getRef(), method)) {
                    Choicetype.Type ct = ReflectionUtil.getChoiceType(context.getRefFactory()
                            .getRootReference()
                            .getRef(), method);
                    if (ct != null && ct == Choicetype.Type.TABLE) {
                        multivaluePropertyMethods_withButtons.put(method, false);
                    } else {
                        // choicer
                        new ChoicerPropertyElement<E>(ref, method).accept(visitor);
                    }
                } else {
                    if (SynchronizerHelper.isBooleanType(method)) {
                        // checkbox property
                        new CheckboxPropertyElement<E>(ref, method).accept(visitor);
                    } else {
                        // simple property
                        new SimplePropertyElement<E>(ref, method).accept(visitor);
                    }
                }
            }
        }

        // 2. Complex Members
        for (Method method : subPropertyMethods) {
            // sub properties
            WrappedDomainReferenceI<E> subRef = context.getRefFactory().newSubReference(ref, method);
            new HeadlineElement<E>(ref, method).accept(visitor);

            processDomainClass(context, subRef);

            visitor.visitFieldsetClose();
        }

        // 3. Tables (with Table Buttons)
        for (Entry<Method, Boolean> e : multivaluePropertyMethods_withButtons.entrySet()) {
            Method method = e.getKey();
            boolean withButtons = e.getValue();
            // collection
            new HeadlineElement<E>(ref, method).accept(visitor);
            List<MultivalueButtonElement<E>> multivalueButtons = new ArrayList<MultivalueButtonElement<E>>();
            WrappedDomainReferenceI<E> subRef = context.getRefFactory().newSubReference(ref, method);
            if (withButtons) {
                // collection buttons
                Method[] buttonMethods = extractOrderedCollectionButtonMethods(subRef);
                for (Method subMethod : buttonMethods) {
                    multivalueButtons.add(new MultivalueButtonElement<E>(subRef, subMethod));
                }
                Method remover = ReflectionUtil.findMethod(ref.getClassRef().getDomainClass(),
                        ReflectionUtil.REMOVER_PREFIX, ReflectionUtil.removePrefix(method.getName()));
                if (remover != null) {
                    multivalueButtons.add(new MultivalueButtonElement<E>(ref, remover));
                }
            }
            new MultivaluePropertyElement<E>(ref, method, subRef, multivalueButtons).accept(visitor);
            visitor.visitFieldsetClose();
        }

        // 4. Simple Buttons
        Method[] buttonMethods = extractOrderedButtonMethods(ref, true);
        for (Method method : buttonMethods) {
            if (!method.getName().startsWith(ReflectionUtil.REMOVER_PREFIX)) {
                if (isResource(method)) {
                    new ResourceElement<E>(ref, method).accept(visitor);
                } else {
                    new ButtonElement<E>(ref, method).accept(visitor);
                }
            }
        }
    }

    protected boolean isResource(Method method) {
        return method.getReturnType() != null
                && (InputStream.class.isAssignableFrom(method.getReturnType()) || File.class.isAssignableFrom(method
                        .getReturnType()));
    }

    protected Method[] extractOrderedCollectionButtonMethods(WrappedDomainReferenceI<E> subRef) {
        return ReflectionUtil.extractOrderedCollectionButtonMethods(subRef.getRef());
    }

    protected Method[] extractOrderedButtonMethods(WrappedDomainReferenceI<E> ref, boolean withCollectionMethods) {
        return ReflectionUtil.extractOrderedButtonMethods(ref.getRef(), withCollectionMethods);
    }

}