package org.nocket.gen.page.element.synchronizer;

import gengui.domain.DomainObjectReference;

import java.io.Serializable;

import org.nocket.gen.domain.element.MultivalueButtonElement;
import org.nocket.gen.domain.element.MultivalueColumnElement;
import org.nocket.gen.page.DMDWebGenPageContext;

public class TableCallback implements Serializable {

    private static final long serialVersionUID = 1L;

    protected final SynchronizerHelper helper;
    protected final String propertiesWicketId;
    protected final String prompt;

    public TableCallback(DMDWebGenPageContext context, MultivalueColumnElement<DomainObjectReference> columnElement) {
        this.helper = new SynchronizerHelper(context, columnElement);
        this.prompt = columnElement.getPrompt();
        this.propertiesWicketId = columnElement.getPropertiesWicketId();
    }

    public TableCallback(DMDWebGenPageContext context, MultivalueButtonElement<DomainObjectReference> element) {
        this.helper = new SynchronizerHelper(context, element);
        this.propertiesWicketId = element.getPropertiesWicketId();
        this.prompt = element.getPrompt();
    }

}
