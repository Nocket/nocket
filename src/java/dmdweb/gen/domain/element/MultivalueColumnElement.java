package dmdweb.gen.domain.element;

import gengui.domain.AbstractDomainReference;
import gengui.guiadapter.ConnectionReuse;
import gengui.util.ReflectionUtil;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

import dmdweb.gen.domain.ref.WrappedDomainReferenceI;
import dmdweb.gen.domain.visitor.DomainElementVisitorI;

public class MultivalueColumnElement<E extends AbstractDomainReference> extends SimplePropertyElement<E> {

    private final String columnName;
    private final String prompt;

    public MultivalueColumnElement(WrappedDomainReferenceI<E> accessor, String name, String prompt) {
        this(accessor, name, prompt, findMethod(accessor, name));
    }

    public MultivalueColumnElement(WrappedDomainReferenceI<E> accessor, String name, String prompt, Method method) {
        super(accessor, method);
        this.columnName = name;
        this.prompt = prompt;
    }

    private static Method findMethod(WrappedDomainReferenceI<?> accessor, String name) {
        String propertyName = StringUtils.capitalize(name);
        Method method = accessor.getRef().findPropertyAccessor(propertyName, ReflectionUtil.GETTER_PREFIX);
        return method;
    }

    public String getColumnName() {
        return columnName;
    }

    @Override
    public String getPrompt() {
        return prompt;
    }

    public String getPropertiesWicketId() {
        return super.getWicketId().replace(getPropertyName(), "columns." + getPropertyName());
    }

    @Deprecated
    @Override
    public void accept(DomainElementVisitorI<E> visitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean repeated() {
        return true;
    }

    @Override
    public DomainElementI<E> replicate(ConnectionReuse reuse) {
        return new MultivalueColumnElement<E>(this.getAccessor().replicate(reuse), columnName, prompt,
                getMethod());
    }

}