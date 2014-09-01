package dmdweb.gen.page.visitor.bind.builder;

import gengui.util.SevereGUIException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Component;

import dmdweb.gen.page.element.ButtonElement;
import dmdweb.gen.page.element.CheckboxInputElement;
import dmdweb.gen.page.element.DivElement;
import dmdweb.gen.page.element.FeedbackElement;
import dmdweb.gen.page.element.FileDownloadElement;
import dmdweb.gen.page.element.FileInputElement;
import dmdweb.gen.page.element.FormElement;
import dmdweb.gen.page.element.GroupTabbedPanelElement;
import dmdweb.gen.page.element.ImageElement;
import dmdweb.gen.page.element.LabelElement;
import dmdweb.gen.page.element.LinkElement;
import dmdweb.gen.page.element.ModalElement;
import dmdweb.gen.page.element.PageElementI;
import dmdweb.gen.page.element.PasswordInputElement;
import dmdweb.gen.page.element.PromptElement;
import dmdweb.gen.page.element.RadioInputElement;
import dmdweb.gen.page.element.RepeatingPanelElement;
import dmdweb.gen.page.element.SelectElement;
import dmdweb.gen.page.element.TableElement;
import dmdweb.gen.page.element.TextAreaElement;
import dmdweb.gen.page.element.TextInputElement;
import dmdweb.gen.page.element.UnknownPageElementI;
import dmdweb.gen.page.inject.PageComponentInjection;
import dmdweb.gen.page.visitor.bind.builder.components.GeneratedDateTextField;
import dmdweb.gen.page.visitor.bind.builder.components.GeneratedNumberTextField;

/**
 * This class tries to find custom-built components from the page class'
 * members. This is the opposite of what class {@link PageComponentInjection}
 * does, so to speak. While PageComponentInjections injects auto-generated
 * Wicket components into the Page class' members after the binding process,
 * this class here interprets the page class as a component <i>provider</i>. It
 * looks for non-null members providing components with wicket IDs which match
 * the required components wicket IDs and uses these components avoiding the
 * internal auto-creation. This allows developers to provide components with
 * special instrumentation. The components could as well be provided by
 * self-written binding interceptors, but providing by members is much more
 * convenient (as long as it is suitable ;-)
 * 
 * The framework's pre-defined components like {@link GeneratedNumberTextFields}
 * and {@link GeneratedDaeTextield} provide simplified constructors to
 * instanciate them with minimal parameterization as a member and
 * post-initialize them later by this binding builder here.
 * 
 * @author less02
 */
public class CustomBindingBuilder implements BindingBuilderI {

    protected Map<String, Field> potentialProviderMembers = null;

    protected Component findCustomComponent(PageElementI<?> forElement) {
        try {
            Map<String, Field> potentialProviders = getPotentialProviderMembers(forElement);
            Field provider = potentialProviders.get(forElement.getWicketId());
            if (provider != null) {
                provider.setAccessible(true);
                Component customComponent = (Component) provider.get(forElement.getContext().getPage());
                // Do we have to perform a general type check here, if the provided component
                // looks somehow suitable for the type of page element to represent? Custom
                // components may be something totally unknown, so we don't check anything at all.
                if (customComponent.getDefaultModel() == null)
                    customComponent.setDefaultModel(forElement.getModel());
                return customComponent;
            }
            return null;
        } catch (IllegalAccessException iax) {
            throw new SevereGUIException(iax);
        }
    }

    protected Map<String, Field> getPotentialProviderMembers(PageElementI<?> forElement) throws IllegalAccessException {
        if (potentialProviderMembers == null) {
            potentialProviderMembers = new HashMap<String, Field>();
            Class<?> pageClass = forElement.getContext().getPage().getClass();
            addComponentFields(forElement.getContext().getPage(), pageClass);
        }
        return potentialProviderMembers;
    }

    protected void addComponentFields(Object page, Class<?> clazz) throws IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object content = field.get(page);
            if (content instanceof Component) {
                Component component = (Component) content;
                potentialProviderMembers.put(component.getId(), field);
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            addComponentFields(page, superClass);
        }
    }

    @Override
    public Component createModal(ModalElement e) {
        return findCustomComponent(e);
    }

    @Override
    public Component createFeedback(FeedbackElement e) {
        return findCustomComponent(e);
    }

    @Override
    public Component createForm(FormElement e) {
        return findCustomComponent(e);
    }

    @Override
    public Component createLabel(LabelElement e) {
        return findCustomComponent(e);
    }

    @Override
    public Component createPrompt(PromptElement e) {
        return findCustomComponent(e);
    }

    @Override
    public Component createTextInput(TextInputElement e) {
        Component component = findCustomComponent(e);
        if (component instanceof GeneratedNumberTextField) {
            ((GeneratedNumberTextField) component).postInit(e);
        } else if (component instanceof GeneratedDateTextField) {
            ((GeneratedDateTextField) component).postInit(e);
        }
        return component;
    }

    @Override
    public Component createFileInput(FileInputElement e) {
        return findCustomComponent(e);
    }

    @Override
    public Component createPasswordInput(PasswordInputElement e) {
        return findCustomComponent(e);
    }

    @Override
    public Component createFileDownload(FileDownloadElement e) {
        return findCustomComponent(e);
    }

    @Override
    public Component createTextArea(TextAreaElement e) {
        return findCustomComponent(e);
    }

    @Override
    public Component createCheckboxInput(CheckboxInputElement e) {
        return findCustomComponent(e);
    }

    @Override
    public Component createRadioInput(RadioInputElement e) {
        return findCustomComponent(e);
    }

    @Override
    public Component createSelect(SelectElement e) {
        return findCustomComponent(e);
    }

    @Override
    public Component createImage(ImageElement e) {
        return findCustomComponent(e);
    }

    @Override
    public Component createLink(LinkElement e) {
        return findCustomComponent(e);
    }

    @Override
    public Component createTable(TableElement e) {
        return findCustomComponent(e);
    }

    @Override
    public Component createButton(ButtonElement e) {
        return findCustomComponent(e);
    }

    @Override
    public Component createUnknown(UnknownPageElementI<?> e) {
        return findCustomComponent(e);
    }

    @Override
    public Component createListView(RepeatingPanelElement e) {
        return findCustomComponent(e);
    }

    @Override
    public Component createGroupTabbedPanel(GroupTabbedPanelElement e) {
        return findCustomComponent(e);
    }

    @Override
    public Component createDiv(DivElement e) {
        return findCustomComponent(e);
    }
}
