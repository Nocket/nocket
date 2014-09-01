package dmdweb.gen.page.guiservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import dmdweb.gen.page.DMDWebGenPageContext;
import dmdweb.gen.page.element.synchronizer.SynchronizerHelper;
import dmdweb.gen.page.element.synchronizer.TouchedListenerModelWrapper;

public class TouchedRegistry implements Serializable {

    private DMDWebGenPageContext parentCtx;

    public TouchedRegistry(DMDWebGenPageContext ctx) {
        this.parentCtx = ctx;
    }

    public boolean registerTouchedListener(final IModel<? extends TouchedListener> listener, String... wicketIdPrefixes) {
        final MutableBoolean changed = new MutableBoolean(false);
        doWithMatchingModels(new ModelCallback() {
            @Override
            public boolean doWithModel(TouchedListenerModelWrapper<?> model) {
                if (model.registerTouchedListener(listener)) {
                    changed.setValue(true);
                }
                return true;
            }
        }, wicketIdPrefixes);
        return changed.booleanValue();
    }

    public boolean unregisterTouchedListener(final IModel<? extends TouchedListener> listener,
            String... wicketIdPrefixes) {
        final MutableBoolean changed = new MutableBoolean(false);
        doWithMatchingModels(new ModelCallback() {
            @Override
            public boolean doWithModel(TouchedListenerModelWrapper<?> model) {
                if (model.unregisterTouchedListener(listener)) {
                    changed.setValue(true);
                }
                return true;
            }
        }, wicketIdPrefixes);
        return changed.booleanValue();
    }

    public boolean touched(String... wicketIdPrefixes) {
        final MutableBoolean touched = new MutableBoolean(false);
        doWithMatchingModels(new ModelCallback() {
            @Override
            public boolean doWithModel(TouchedListenerModelWrapper<?> model) {
                touched.setValue(model.isTouched());
                return !touched.booleanValue();
            }
        }, wicketIdPrefixes);
        return touched.booleanValue();
    }

    public void touch(String... wicketIdPrefixes) {
        setTouched(true, wicketIdPrefixes);
    }

    public void untouch(String... wicketIdPrefixes) {
        setTouched(false, wicketIdPrefixes);
    }

    private void setTouched(final boolean touched, String... wicketIdPrefixes) {
        doWithMatchingModels(new ModelCallback() {
            @Override
            public boolean doWithModel(TouchedListenerModelWrapper<?> model) {
                model.setTouched(touched);
                return true;
            }
        }, wicketIdPrefixes);
    }

    private void doWithMatchingModels(ModelCallback callback, String... wicketIdPrefixes) {
        // TODO ES: startsWith ist nicht die beste Lösung. Bei einem Prefix von hallo.name würde hallo.name, halle.nameDenIchGarnichtWill gefunden werden. Letzteres ist nicht korrekt
        for (TouchedRegistryData data : getDatas()) {
            for (TouchedListenerModelWrapper<?> l : data.getModels().values()) {
                if (wicketIdPrefixes == null || wicketIdPrefixes.length == 0
                        || StringUtils.startsWithAny(l.getWicketId(), wicketIdPrefixes)) {
                    boolean abort = !callback.doWithModel(l);
                    if (abort) {
                        return;
                    }
                }
            }
        }
    }

    private List<TouchedRegistryData> getDatas() {
        final List<TouchedRegistryData> datas = new ArrayList<TouchedRegistryData>();
        MarkupContainer root = SynchronizerHelper.findRoot(parentCtx.getPage());
        IVisitor<Component, Object> visitor = new IVisitor<Component, Object>() {
            @Override
            public void component(Component object, IVisit<Object> visit) {
                DMDWebGenPageContext ctx = object.getMetaData(DMDWebGenPageContext.CONTEXT_KEY);
                if (ctx != null) {
                    datas.add(ctx.getTouchedRegistryData());
                }
            }
        };
        visitor.component(root, null);
        root.visitChildren(visitor);
        return datas;
    }

    private interface ModelCallback {
        boolean doWithModel(TouchedListenerModelWrapper<?> model);
    }

}
