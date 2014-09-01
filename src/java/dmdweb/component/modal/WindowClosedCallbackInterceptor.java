package dmdweb.component.modal;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;

public interface WindowClosedCallbackInterceptor extends Serializable {

    boolean onClose(AjaxRequestTarget target);

}
