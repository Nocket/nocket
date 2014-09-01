package org.nocket.component.tabs;

import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.list.LoopItem;

/**
 * Die TabbedPanel-Konponente von Wicket benutzt andere class-Definitionen als
 * Bootstrap benötigt. Deswegen, war es notwendig die Wicket-Komponente
 * abzuleiten. Wicket benutzt eine andere Markierung für das gerade aktive Tab
 * und in HTML muss tab
 * <ul>
 * -Element an Bootstrap angepasst werden. Außerdem verwenden wir für DMD ein
 * <i>Ajax</i>TabbedPanel, weil dieses erlaubt, über ein Überschreiben der
 * Methode {@link #newLink(String, int)} beim Tabwechsel das Synchronisieren der
 * Modelle aller Forms anzustoßen. Andernfalls gehen alle Eingaben im jeweils
 * verlassenen Tab verloren. Außerdem sieht es viel gefälliger aus, wenn sich
 * beim Tabwechsel nur der Inhalt des Tab-Bereichs ändert und nicht die ganze
 * Seite neu aufbaut.
 * 
 * @author meis026
 * 
 */
public class DMDTabbedPanel<T extends ITab> extends AjaxTabbedPanel<T> {

    public DMDTabbedPanel(String id, List<T> tabs) {
        super(id, tabs);
    }

    @Override
    /**
     * Setzt das class-Attribut auf 'active', wenn dieses Tab das selektierte ist.
     */
    protected LoopItem newTabContainer(final int tabIndex) {
        return new LoopItem(tabIndex) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onComponentTag(final ComponentTag tag) {
                super.onComponentTag(tag);
                String cssClass = tag.getAttribute("class");
                if (cssClass == null) {
                    cssClass = " ";
                }

                if (getIndex() == getSelectedTab()) {
                    cssClass += " active";
                }

                tag.put("class", cssClass.trim());
            }

            @Override
            public boolean isVisible() {
                return getTabs().get(tabIndex).isVisible();
            }
        };
    }

}
