package dmdweb.gen.page.guiservice;

import gengui.annotations.Closer;

import java.io.Serializable;
import java.util.Collection;

import org.apache.wicket.ajax.AjaxRequestTarget;

import de.bertelsmann.coins.general.error.Assert;
import dmdweb.NocketSession;
import dmdweb.gen.domain.element.ButtonElement;
import dmdweb.gen.page.DMDWebGenPageContext;
import dmdweb.gen.page.element.PageElementI;
import dmdweb.gen.page.element.synchronizer.ButtonCallback;

public class CloserHandler implements Serializable {
    private static final long serialVersionUID = 1L;

    private ButtonCallback buttonCallback;
    private boolean hideCloseButton;
    private DMDWebGenPageContext dmdWebGenPageContext;

    public CloserHandler(DMDWebGenPageContext dmdWebGenPageContext) {
        this(dmdWebGenPageContext, false);
    }

    public CloserHandler(DMDWebGenPageContext dmdWebGenPageContext, boolean hideCloseButton) {
        this.hideCloseButton = hideCloseButton;
        this.buttonCallback = initButtonCallback(dmdWebGenPageContext);
        this.dmdWebGenPageContext = dmdWebGenPageContext;
    }

    public void onSubmit(AjaxRequestTarget target) {
        if (buttonCallback != null) {
            buttonCallback.onSubmit(target);
        } else {
            DMDWebGenGuiServiceProvider webGuiServiceProvider = NocketSession.get().getDMDWebGenGuiServiceProvider();
            webGuiServiceProvider.registerAjaxRequestTarget(dmdWebGenPageContext, target);
            webGuiServiceProvider.closeModalPanel();
            webGuiServiceProvider.unregisterAjaxRequestTarget(dmdWebGenPageContext, target);
        }
    }

    private ButtonCallback initButtonCallback(DMDWebGenPageContext context) {
        Collection<? extends ButtonElement> elements = context.getDomainRegistry().getElements(ButtonElement.class);

        for (ButtonElement<?> buttonElement : elements) {
            Closer closer = buttonElement.getMethod().getAnnotation(Closer.class);

            if (closer != null && Closer.Type.DEFAULT.equals(closer.value())) {
                String wicketId = buttonElement.getWicketId();
                PageElementI<?> element = context.getPageRegistry().getElement(wicketId);
                Assert.test(element instanceof dmdweb.gen.page.element.ButtonElement);
                return ((dmdweb.gen.page.element.ButtonElement) element).getButtonCallback();
            }
        }
        return null;
    }

    public boolean hideCloseButton() {
        return hideCloseButton;
    }
}