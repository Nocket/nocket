package dmdweb.gen.i18n;

import gengui.util.I18nPropertyBasedImpl;

import org.apache.wicket.Application;
import org.apache.wicket.Component;

import dmdweb.gen.domain.WebDomainProperties;

public class I18nOrWicketTranslator {

    public static String translate(Component component, String key, String defaultValue,
            Class<? extends Object> domainClass) {
        WebDomainProperties configuration = new WebDomainProperties(domainClass);
        String translatedText;
        if (configuration.isLocalizationWicket()) {
            translatedText = Application.get().getResourceSettings().getLocalizer()
                    .getString(key, component, defaultValue);
        } else {
            translatedText = new I18nPropertyBasedImpl().translate(key, defaultValue);
        }
        return translatedText != null ? translatedText : defaultValue;
    }
}
