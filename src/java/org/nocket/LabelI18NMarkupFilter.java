package org.nocket;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.MarkupElement;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.RawMarkup;
import org.apache.wicket.markup.html.TransparentWebMarkupContainer;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.parser.AbstractMarkupFilter;
import org.apache.wicket.markup.resolver.IComponentResolver;
import org.apache.wicket.util.string.Strings;
import org.nocket.gen.i18n.I18nOrWicketTranslator;

/**
 * In einer HTML-Datei wird es immer wieder statischen Text geben, der
 * internationalisiert werden soll. Das hei�t, dass dieser Text je nach
 * geforderter Sprache (locale der Page) in einer anderen Sprache gerendert
 * werden muss. Diese Texte sollten nicht �ber Wicket verwaltet werden m�ssen
 * und somit eine Wicket-ID haben m�ssen. Das Ziel ist, dass im HTML-File ein
 * <label>mein.statischer.text</label> steht, �bersetzt wird. Dazu wird bei
 * Labels, die kein wicket:id-Attribut, kein wicket:for-Attribut und kein
 * for-Attribut haben, der Wert des Labels als Key f�r einen �bersetzten Text
 * angenommen. �ber den I18nOrWicketTranslator wird dieser, wenn m�glich,
 * �bersetzt. Wird dieser Key nicht gefunden, wird der Original-Text
 * dargestellt.<br>
 * Leider ist es nicht m�glich, den Wert eines Labels w�hrend des Renders
 * auszulesen und zu manipulieren ohne die Label-Klasse abzuleiten. Dieses ist
 * jedoch zu vermeiden. Deswegen ist ein zweistufiger Prozess notwendigen.
 * LabelI18NMarkupFilter ist sowohl ein MarkupFilter als auch ComponentResolver.
 * Der MarkupFilter-Teil wird beim Einlesen des HTML-Files ausgef�hrt. Dort wird
 * nach label-Tags gesucht, die bestimmte Eigenschaften haben (siehe Methode
 * <code>isLabelTagToTranslate</code>). Bei diesen Labels wird der Wert
 * ausgelesen und in ein dem Label-Tag neu hinzugef�gtes Attribut
 * "_dmdI18N_message_" hinzugef�gt. Der Wert dieses Attributes kann sp�ter
 * ausgelesen und �bersetzt werden.
 * 
 * Ein ComponentResolver ist daf�r zust�ndlich ein Tag mit einer
 * Wicket-Komponete zu verbinden. Die resolve()-Methode des
 * LabelI18NMarkupFilter ersetzt, wenn das Attribut "_dmdI18N_message_"
 * vorhanden ist, durch einen speziellen TransparentWebMarkupContainer. Beim
 * Rendern ist das locale der Seite bekant. Dann wird an Stelle des
 * TransparentWebMarkupContainer wiederum ein <label>-Tag mit dem �bersetzten in
 * den Response eingesetzt.
 * 
 * @author meis026
 * 
 */
@SuppressWarnings("serial")
public class LabelI18NMarkupFilter extends AbstractMarkupFilter implements IComponentResolver {
    private static final String DMD_I18N_MESSAGE = "_dmdI18N_message_";

    @Override
    public void postProcess(Markup markup) {
        MarkupElement lastMarkupElement = null;
        int count = 0;
        for (MarkupElement markupElement : markup) {
            // Search for a component tag &quot;label&quot; that is followed by a RawMarkup 
            if (lastMarkupElement instanceof ComponentTag && markupElement instanceof RawMarkup) {
                ComponentTag tag = (ComponentTag) lastMarkupElement;
                RawMarkup rawMarkup = (RawMarkup) markupElement;
                if (isLabelTagToTranslate(tag)) {
                    tag.append(getWicketMessageAttrName(), rawMarkup.toString(), "");
                }

            }

            lastMarkupElement = markupElement;
            count++;
        }
        super.postProcess(markup);
    }

    protected boolean isLabelTagToTranslate(ComponentTag tag) {
        boolean result = "label".equalsIgnoreCase(tag.getName()) && tag.isOpen();
        result = result && StringUtils.isBlank(tag.getAttribute("wicket:id"));
        result = result && StringUtils.isBlank(tag.getAttribute("wicket:for"));
        result = result && StringUtils.isBlank(tag.getAttribute("for"));
        return result;
    }

    @Override
    protected MarkupElement onComponentTag(ComponentTag tag) throws ParseException {
        if (isLabelTagToTranslate(tag)) {
            tag.setId(DMD_I18N_MESSAGE);
            tag.setAutoComponentTag(true);
            tag.setModified(true);
        }
        return tag;
    }

    private String getWicketMessageAttrName() {
        return DMD_I18N_MESSAGE;
    }

    private final class TransparentWebMarkupContainerExtension extends TransparentWebMarkupContainer {

        final private String key;
        final private String label;

        private TransparentWebMarkupContainerExtension(String id, String key, String label) {
            super(id);
            this.key = key;
            this.label = label;
        }

        @Override
        protected void onRender() {
            Page page = this.getPage();
            String trimmedKey = StringUtils.trimToEmpty(key);
            String translatedText = key;
            if (page.getDefaultModelObject() != null) {
                Class<? extends Object> domainClass = page.getDefaultModelObject().getClass();
                translatedText = I18nOrWicketTranslator.translate(this, trimmedKey, key, domainClass);
            }

            String removeStirng = DMD_I18N_MESSAGE + "=\"" + Strings.escapeMarkup(key) + "\"";
            String labelTag = StringUtils.remove(label, removeStirng);
            getResponse().write(labelTag + translatedText + "</label>");

        }
    }

    @Override
    public Component resolve(MarkupContainer container, MarkupStream markupStream, ComponentTag tag) {
        // localize any raw markup that has wicket:message attrs
        if ((tag != null) && (tag.getId().startsWith(DMD_I18N_MESSAGE))) {
            Component wc;
            int autoIndex = container.getPage().getAutoIndex();
            String id = DMD_I18N_MESSAGE + autoIndex;

            String key = tag.getAttribute(DMD_I18N_MESSAGE);

            if (tag.isOpenClose()) {
                wc = new WebComponent(id);
            } else {
                wc = new TransparentWebMarkupContainerExtension(id, key, tag.toString());
            }
            return wc;
        }

        return null;
    }
}