package org.nocket.gen.page.element.synchronizer;

import gengui.domain.DomainObjectReference;

import java.io.Serializable;

import org.nocket.gen.domain.element.MultivalueButtonElement;
import org.nocket.gen.domain.element.MultivalueColumnElement;
import org.nocket.gen.page.DMDWebGenPageContext;

// TODO: Auto-generated Javadoc
/**
 * The Class TableCallback.
 */
public class TableCallback implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The helper. */
    protected final SynchronizerHelper helper;
    
    /** The properties wicket id. */
    protected final String propertiesWicketId;
    
    /** The prompt. */
    protected final String prompt;

    /**
     * Instantiates a new table callback.
     *
     * @param context the context
     * @param columnElement the column element
     */
    public TableCallback(DMDWebGenPageContext context, MultivalueColumnElement<DomainObjectReference> columnElement) {
        this.helper = new SynchronizerHelper(context, columnElement);
        this.prompt = columnElement.getPrompt();
        this.propertiesWicketId = columnElement.getPropertiesWicketId();
    }

    /**
     * Instantiates a new table callback.
     *
     * @param context the context
     * @param element the element
     */
    public TableCallback(DMDWebGenPageContext context, MultivalueButtonElement<DomainObjectReference> element) {
        this.helper = new SynchronizerHelper(context, element);
        this.propertiesWicketId = element.getPropertiesWicketId();
        this.prompt = element.getPrompt();
    }

}
