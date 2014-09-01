package dmdweb.gen.domain;

import gengui.annotations.Group;
import gengui.domain.AbstractDomainReference;
import gengui.util.ReflectionUtil;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import dmdweb.gen.domain.ref.WrappedDomainReferenceI;

public class GroupNameFileAndClassNameStrategy<E extends AbstractDomainReference> extends
        FileAndClassNameStrategy<E> {
    protected List<Method> methods = new ArrayList<Method>();
    protected String groupName;
    private boolean domainObjectWithGroupAnnotations;

    public GroupNameFileAndClassNameStrategy(DMDWebGenContext<E> context, WrappedDomainReferenceI<E> ref,
            String groupName) {
        super(context, ref);
        this.groupName = groupName;

        // Is it a class with group annotations
        Method[] declaredMethods = ref.getRef().getDomainClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(Group.class)) {
                domainObjectWithGroupAnnotations = true;
                break;
            }
        }
    }

    public String getFilenamePart() {
        String result = ref.getRef().getDomainClass().getName();
        return extractAndEscapeGroupName(result);
    }

    public List<Method> getMethodsForRef(WrappedDomainReferenceI<E> methodsForRef) {
        if (methodsForRef.getRef().getDomainClass().equals(ref.getRef().getDomainClass())) {
            return methods;
        } else {
            List<Method> result = new ArrayList<Method>();
            Method[] propertyMethods = ReflectionUtil.extractOrderedPropertyMethods(methodsForRef.getRef(), true);
            for (int i = 0; propertyMethods != null && i < propertyMethods.length; i++) {
                result.add(propertyMethods[i]);

            }
            return result;
        }
    }

    @Override
    public boolean isPanel() {
        return StringUtils.isNotBlank(groupName);
    }

    public boolean isStrategyForMainObject() {
        return StringUtils.isBlank(groupName);
    }

    public boolean hasMatchingGroupAnnotation(Method method) {
        if (method == null) {
            return false;
        }

        Group group = method.getAnnotation(Group.class);
        if (group == null && !"".equals(groupName)) {
            return false;
        }

        String name = group != null ? group.value() : "";
        return name.equals(groupName);
    }

    public String getJavaClassPackageNameAsPath() {
        return getJavaClassPackageName().replace(".", File.separator);
    }

    protected String extractAndEscapeGroupName(String result) {
        if (StringUtils.isBlank(groupName)) {
            return result;
        }

        return result + "_" + groupName.replace(".", "_");
    }

    public String getJavaClassNamePart() {
        String result = context.getRefFactory().getRootReference().getRef().getDomainClass().getSimpleName();
        return extractAndEscapeGroupName(result);
    }

    public boolean isDomainObjectWithGroupAnnotations() {
        return domainObjectWithGroupAnnotations;
    }

}