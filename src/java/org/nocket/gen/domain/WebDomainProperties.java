package org.nocket.gen.domain;

import gengui.domain.DomainClassReference;
import gengui.util.DomainProperties;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.protocol.http.WebApplication;
import org.nocket.component.form.DMDTextField.InputStringConvertStrategy;
import org.nocket.gen.domain.visitor.html.styling.bootstrap2.DefaultStylingStrategy;
import org.nocket.page.DMDWebPage;

/**
 * This class is a derivation from gengui's DomainProperties which adds a few
 * web-specific properties. Maybe this class vanishes some day in case the
 * generic web stuff merges with gengui. Therefore we don't put too much effort
 * in it yet and we leave out things like checking allowed values sets etc.
 * 
 * @author less02
 */
public class WebDomainProperties extends DomainProperties {

    public static final String HTML_FILE_PATH = "html.file.path";
    public static final String SRC_FILE_PATH = "src.file.path";
    public static final String HTML_TABLE_EXAMPLE_CONTENT = "html.table.example.content";
    public static final String HTML_LAYOUT_STRATEGY = "html.layout.strategy";
    public static final String HTML_HEADER_LINKS = "html.header.links";
    public static final String HTML_DOCUMENT_CACHING_POLICY = "html.document.caching.policy";
    public static final String HTML_PAGE_BASE_CLASS = "html.page.base.class";
    public static final String HTML_PANEL_BASE_CLASS = "html.panel.base.class";
    public static final String VALIDATION_ERROR_PRESENTATION = "validation.error.presentation";
    public static final String DMDTEXTFIELD_INPUTSTRING_CONVERT_STRATEGY = "dmdtextfield.inputstring.convert.strategy";
    public static final String LOCALIZATION_WICKET = "wicket";
    public static final String STYLING_STRATEGY = "nocket.styling.strategy";

    public WebDomainProperties() {
        super();
    }

    public WebDomainProperties(Class<?> domainObjClass) {
        super(domainObjClass);
    }

    public WebDomainProperties(DomainClassReference ref) {
        super(ref);
    }

    public WebDomainProperties(String domainClassName) {
        super(domainClassName);
    }

    public String getHTMLFilePath() {
        return getProperty(HTML_FILE_PATH, getJFDFilePath());
    }

    public String getSrcFilePath() {
        return getProperty(SRC_FILE_PATH, getJFDFilePath());
    }

    public InputStringConvertStrategy getInputStringConvertStrategy() {
        return InputStringConvertStrategy.valueOf(getProperty(DMDTEXTFIELD_INPUTSTRING_CONVERT_STRATEGY,
                InputStringConvertStrategy.trimAndEmptyToNull.name()));
    }

    public LayoutStrategy getHTMLLayoutStrategie() {
        String value = getProperty(HTML_LAYOUT_STRATEGY, LayoutStrategy.BOOTSTRAP.name());
        return LayoutStrategy.valueOf(value.toUpperCase());
    }

    public boolean getHTMLTableExampleContent() {
        String value = getProperty(HTML_TABLE_EXAMPLE_CONTENT, Boolean.TRUE.toString()).trim();
        return !value.equals(Boolean.FALSE.toString());
    }

    public String[] getHTMLHeaderLinks() {
        return getProperty(HTML_HEADER_LINKS, "").split(",");
    }

    public HTMLDocumentCachingPolicy getHTMLDocumentCachingPolicy() {
        String stringValue = getProperty(HTML_DOCUMENT_CACHING_POLICY, HTMLDocumentCachingPolicy.wicket.name());
        HTMLDocumentCachingPolicy value = HTMLDocumentCachingPolicy.valueOf(stringValue);
        if (value == HTMLDocumentCachingPolicy.wicket) {
            return (WebApplication.get().getConfigurationType() == RuntimeConfigurationType.DEPLOYMENT) ?
                    HTMLDocumentCachingPolicy.permanent : HTMLDocumentCachingPolicy.none;
        }
        return HTMLDocumentCachingPolicy.valueOf(stringValue);
    }

    public Class<? extends WebPage> getHTMLPageBaseClass() {
        String className = getProperty(HTML_PAGE_BASE_CLASS, DMDWebPage.class.getName());
        try {
            return (Class<? extends WebPage>) Class.forName(className);
        } catch (ClassNotFoundException cnfx) {
            throw new IllegalArgumentException(cnfx);
        }
    }

    public Class<? extends Panel> getHTMLPanelBaseClass() {
        String className = getProperty(HTML_PANEL_BASE_CLASS, Panel.class.getName());
        try {
            return (Class<? extends Panel>) Class.forName(className);
        } catch (ClassNotFoundException cnfx) {
            throw new IllegalArgumentException(cnfx);
        }
    }

    public ValidationErrorPresentation getValidationErrorPresentation() {
        String value = getProperty(VALIDATION_ERROR_PRESENTATION, ValidationErrorPresentation.PLAIN.name());
        return ValidationErrorPresentation.valueOf(value.toUpperCase());
    }

    public boolean isLocalizationWicket() {
        return getJFDLocalization().equalsIgnoreCase(LOCALIZATION_WICKET);
    }

    @Override
    protected boolean lazyinitAllowedConstants() {
        synchronized (WebDomainProperties.class) {
            boolean justInitialized = super.lazyinitAllowedConstants();
            if (justInitialized) {
                extendAllowedConstants(JFD_LOCALIZATION, LOCALIZATION_WICKET);
                return true;
            }
            return false;
        }
    }

    /**
     * This method allows to initialize the DomainProperties with web-specific
     * additional values by an explicit call at a very early state.
     */
    public void init() {
        lazyinitAllowedConstants();
    }
    
    /**
     * This Method get the configured Styling-Strategy.
     * If no Configuration is found so the Bootstrap2StylingStrategy is used.
     */
    public String getStylingStrategyClass() {
        String value = getProperty(STYLING_STRATEGY, DefaultStylingStrategy.class.getName());
        return value;
    }
}
