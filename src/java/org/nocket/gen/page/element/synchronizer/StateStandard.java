package org.nocket.gen.page.element.synchronizer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.wicket.Component;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessages;

// TODO: Auto-generated Javadoc
/**
 * The Class StateStandard.
 *
 * @param <E> the element type
 */
@SuppressWarnings("serial")
public class StateStandard<E> implements State<E> {
    
    /** The getter. */
    E getter;
    
    /** The choicer. */
    Object[] choicer;
    
    /** The enabled. */
    boolean enabled;
    
    /** The feedback messages. */
    private final Collection<String> feedbackMessages;
    
    /** The touched listener model wrapper. */
    private TouchedListenerModelWrapper<E> touchedListenerModelWrapper;

    /**
     * Instantiates a new state standard.
     *
     * @param touchedListenerModelWrapper the touched listener model wrapper
     * @param component the component
     * @param helper the helper
     */
    @SuppressWarnings("unchecked")
    public StateStandard(TouchedListenerModelWrapper<E> touchedListenerModelWrapper, Component component,
            SynchronizerHelper helper) {

        this.touchedListenerModelWrapper = touchedListenerModelWrapper;
        feedbackMessages = component != null ? feedbackMessageToStringList(component.getFeedbackMessages()) : null;
        enabled = helper.isEnabled();

        if (helper.getChoicerMethod() != null) {
            try {
                choicer = pimpArray(helper.invokeChoicer());
            } catch (Exception e) {
                // during invocation an exception could be, it doesn't matter here
                choicer = null;
            }
        }

        if (helper.getGetterMethod() != null) {
            try {
                getter = (E) helper.invokeGetterMethod();
                getter = pimpCollections(getter);
            } catch (Exception e) {
                // during invocation an exception could be, it doesn't matter here
                getter = null;
            }
        }
    }

    /**
     * Wenn es eine Collection ist, dann nutzt es nicht sich den Wert zu holfen,
     * da es nunmal eine Referenz ist. Also muss diese Collection geclont
     * werden. Der Rest, sprich der Inhalt der Collection interessiert nicht.
     *
     * @param object the object
     * @return the e
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private E pimpCollections(E object) {
        if (object instanceof Collection) {
            return (E) pimpCollection((Collection) object);
        } else if (object instanceof Object[]) {
            return (E) pimpArray((Object[]) object);
        }
        return object;
    }

    /**
     * Pimp array.
     *
     * @param <T> the generic type
     * @param object the object
     * @return the t[]
     */
    @SuppressWarnings({ "unchecked" })
    private <T> T[] pimpArray(T[] object) {
        Class<? extends T[]> newType = (Class<? extends T[]>) object.getClass();
        T[] copy = ((Object) newType == (Object) Object[].class)
                ? (T[]) new Object[object.length]
                : (T[]) Array.newInstance(newType.getComponentType(), object.length);

        for (int i = 0; i < object.length; i++) {
            copy[i] = object[i];
        }
        return copy;
    }

    /**
     * Pimp collection.
     *
     * @param oldCollection the old collection
     * @return the collection
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected Collection pimpCollection(Collection oldCollection) {
        Collection newCollection;
        try {
            newCollection = oldCollection.getClass().newInstance();
            newCollection.addAll(oldCollection);
            return newCollection;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + getOuterType().hashCode();
        result = prime * result + ((choicer == null) ? 0 : choicer.hashCode());
        result = prime * result + (enabled ? 1 : 0);
        result = prime * result + ((feedbackMessages == null) ? 0 : feedbackMessages.hashCode());
        result = prime * result + ((getter == null) ? 0 : getter.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked")
        StateStandard<E> other = (StateStandard<E>) obj;
        if (!getOuterType().equals(other.getOuterType())) {
            return false;
        }

        boolean hasError = feedbackMessages != null && !feedbackMessages.isEmpty();
        boolean otherHasError = other.feedbackMessages != null && !other.feedbackMessages.isEmpty();

        if ((hasError && !otherHasError) || (!hasError && otherHasError)) {
            return false;
        }

        if (hasError) {
            return CollectionUtils.isEqualCollection(feedbackMessages, other.feedbackMessages);
        }

        if (choicer == null) {
            if (other.choicer != null) {
                return false;
            }
        } else {
            if (!ArrayUtils.isEquals(choicer, other.choicer)) {
                return false;
            }
        }

        if (enabled != other.enabled) {
            return false;
        }
        if (getter == null) {
            if (other.getter != null) {
                return false;
            }
        } else if (!getOuterType().modelUnchanged(other.getter, this.getter)) {
            return false;
        }
        return true;

    }

    /**
     * Feedback message to string list.
     *
     * @param feedbackMessages the feedback messages
     * @return the collection
     */
    private Collection<String> feedbackMessageToStringList(FeedbackMessages feedbackMessages) {
        ArrayList<String> result = new ArrayList<String>();
        for (FeedbackMessage feedbackMessage : feedbackMessages) {
            result.add(feedbackMessage.toString());
        }
        return result;
    }

    /**
     * Gets the outer type.
     *
     * @return the outer type
     */
    private TouchedListenerModelWrapper<E> getOuterType() {
        return touchedListenerModelWrapper;
    }

}