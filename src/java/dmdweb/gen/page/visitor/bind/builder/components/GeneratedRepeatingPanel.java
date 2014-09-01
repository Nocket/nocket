package dmdweb.gen.page.visitor.bind.builder.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;

import dmdweb.gen.page.element.RepeatingPanelElement;
import dmdweb.page.DMDPanelFactory;

@SuppressWarnings("serial")
public class GeneratedRepeatingPanel extends RepeatingView {

    private Collection lastState = null;

    public GeneratedRepeatingPanel(RepeatingPanelElement e) {
        super(e.getWicketId(), e.getModel());
    }

    public Collection<?> collectionAuspacken(Object defaultModel) {
        if (defaultModel instanceof Collection) {
            return (Collection<?>) defaultModel;
        } else if (defaultModel == null) {
            return null;
        } else if (defaultModel instanceof IModel) {
            return collectionAuspacken(((IModel<?>) defaultModel).getObject());
        }
        return null;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void onBeforeRender() {
        /*
         * FIXME: vocke03 Das hier ist nur ein Quickfix für DER-Touristik
         * (DMDVIER-210), der dafür sorgt, dass beim Rendern von
         * GeneratedRepeatingPanels immer jeder bisherige Inhalt weggeworfen
         * wird (removeAll()) und danach der korrekt Inhalt neu hinzugefügt
         * wird. Vorher hatte die Abfrage von hasBeenRendered() verhindert, dass
         * GeneratedRepeatingPanels neu gerendert wurden, sobald in ihren
         * Children irgendwo Elemente in einer List-Property hinzugefügt oder
         * entfernt wurden (siehe auch test.dmdweb.tests.gen.listviews.OuterView
         * als Testfall). Problematisch an dieser Lösung ist, dass jetzt _immer_
         * removeAll() aufgerufen und neu gerendert wird, obwohl es in den
         * meisten Fällen überhaupt nicht notwendig ist.
         * 
         * Eine Behebung des Problems könnte so aussehen, dass sich im Rahmen
         * dieses GeneratedRepeatingPanels der Status der Children in Form eines
         * HashCodes gemerkt werden, und beim nächsten Aufruf von onBeforeRender
         * geprüft wird, ob sich dieser Status geändert hat und in diesem Fall
         * ein removeAll() ausgeführt und das erneute Rendering angestoßen wird.
         */
        //        removeAll();
        //if(!hasBeenRendered()) {
        Collection<?> collection = collectionAuspacken(getDefaultModel());

        List<Component> listWithNewModels = new ArrayList<Component>();
        List<Component> existingModels = new ArrayList<Component>();
        for (Iterator<Component> iterator = iterator(); iterator.hasNext();) {
            Component component = iterator.next();
            if (isRemoved(component, collection)) {
                iterator.remove();
                continue;
            } else {
                existingModels.add(component);
            }
        }
        addNewObjects(existingModels, collection);
        lastState = collection;
        super.onBeforeRender();
    }

    private void addNewObjects(List<Component> existingComponents, Collection<?> collection) {
        Collection existingModelObjects = new ArrayList(existingComponents.size());
        for (Component component : existingComponents) {
            existingModelObjects.add(component.getDefaultModelObject());
        }
        Collection subtract = CollectionUtils.subtract(collection, existingModelObjects);

        ArrayList items = new ArrayList(subtract);

        for (int i = 0; i < items.size(); i++) {
            Object view = items.get(i);
            // Wenn die Liste ein Nullelement liefert, ist es das Problem des Lieferanten der Liste. 
            // Also wird das Element kommentarlos übergangen.
            if (view != null) {
                Panel panel = DMDPanelFactory.getViewPanelInstance(view, newChildId());

                panel.add(new Behavior() {

                    @Override
                    public void onComponentTag(Component component, ComponentTag tag) {
                        // Hiermit wird der Tag-Name des Parents geändert. Ziel ist es das <ul> in ein <div> zu verändern, so dass wir nicht dem ul-CSS unterliegen. Warum Wicket das aber bei dem Panel macht, weiß nur Wicket!
                        tag.setName("div");
                        super.onComponentTag(component, tag);
                    }
                });
                add(panel);
            }
        }
    }

    private boolean isRemoved(Component component, Collection<?> collection) {
        Object modelObject = component.getDefaultModelObject();
        for (Object object : collection) {
            if (modelObject != null && modelObject.equals(object)) {
                return false;
            }
        }
        return true;
    }

    private boolean isNew() {
        // TODO Auto-generated method stub
        return false;
    }
}
