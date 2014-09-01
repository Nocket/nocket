package dmdweb.gen.i18n;

import gengui.util.I18nPropertyBasedImpl;

import org.apache.wicket.model.AbstractReadOnlyModel;

/**
 * IModel for localized label Strings which are provided by gengui's I18N
 * concept. You should not use this class directly but through
 * {@link I18NLabelModelFactory} which creates the models depending on the
 * framework's configuration.
 * 
 * @author less02
 */
public class I18NLabelModel extends AbstractReadOnlyModel<String> {

    private static final long serialVersionUID = 1L;

    protected String label;

    public I18NLabelModel(String key) {
        this(null, key, null);
    }

    public I18NLabelModel(String key, String defaultValue) {
        this(null, key, defaultValue);
    }

    public I18NLabelModel(Class<?> domainClass, String key) {
        this(domainClass, key, null);
    }

    public I18NLabelModel(Class<?> domainClass, String key, String defaultValue) {
        label = new I18nPropertyBasedImpl().translate(domainClass, key, defaultValue);
    }

    @Override
    public String getObject() {
        return label;
    }

}
