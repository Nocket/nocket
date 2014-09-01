package dmdweb.gen.page.visitor.bind.builder;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;

import dmdweb.gen.page.DMDWebGenPageContext;
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
import dmdweb.gen.page.element.PasswordInputElement;
import dmdweb.gen.page.element.PromptElement;
import dmdweb.gen.page.element.RadioInputElement;
import dmdweb.gen.page.element.RepeatingPanelElement;
import dmdweb.gen.page.element.SelectElement;
import dmdweb.gen.page.element.TableElement;
import dmdweb.gen.page.element.TextAreaElement;
import dmdweb.gen.page.element.TextInputElement;
import dmdweb.gen.page.element.UnknownPageElementI;

public class InterceptingBindingBuilder implements BindingBuilderI {

    private final List<BindingBuilderI> orderedBuilders;

    public InterceptingBindingBuilder(List<BindingInterceptor> interceptors) {
        this.orderedBuilders = new ArrayList<BindingBuilderI>(interceptors);
        this.orderedBuilders.addAll(DMDWebGenPageContext.getDefaultBindingInterceptors());
        this.orderedBuilders.add(new CustomBindingBuilder());
        this.orderedBuilders.add(new DefaultBindingBuilder());
    }

    @Override
    public Component createModal(ModalElement e) {
        for (BindingBuilderI builder : orderedBuilders) {
            Component c = builder.createModal(e);
            if (c != null) {
                c.setOutputMarkupId(true);
                return c;
            }
        }
        return null;
    }

    @Override
    public Component createFeedback(FeedbackElement e) {
        for (BindingBuilderI builder : orderedBuilders) {
            Component c = builder.createFeedback(e);
            if (c != null) {
                c.setOutputMarkupId(true);
                return c;
            }
        }
        return null;
    }

    @Override
    public Component createForm(FormElement e) {
        for (BindingBuilderI builder : orderedBuilders) {
            Component c = builder.createForm(e);
            if (c != null) {
                c.setOutputMarkupId(true);
                return c;
            }
        }
        return null;
    }

    @Override
    public Component createLabel(LabelElement e) {
        for (BindingBuilderI builder : orderedBuilders) {
            Component c = builder.createLabel(e);
            if (c != null) {
                c.setOutputMarkupId(true);
                return c;
            }
        }
        return null;
    }

    @Override
    public Component createPrompt(PromptElement e) {
        for (BindingBuilderI builder : orderedBuilders) {
            Component c = builder.createPrompt(e);
            if (c != null) {
                c.setOutputMarkupId(true);
                return c;
            }
        }
        return null;
    }

    @Override
    public Component createTextInput(TextInputElement e) {
        for (BindingBuilderI builder : orderedBuilders) {
            Component c = builder.createTextInput(e);
            if (c != null) {
                c.setOutputMarkupId(true);
                return c;
            }
        }
        return null;
    }

    @Override
    public Component createPasswordInput(PasswordInputElement e) {
        for (BindingBuilderI builder : orderedBuilders) {
            Component c = builder.createPasswordInput(e);
            if (c != null) {
                c.setOutputMarkupId(true);
                return c;
            }
        }
        return null;
    }

    @Override
    public Component createFileInput(FileInputElement e) {
        for (BindingBuilderI builder : orderedBuilders) {
            Component c = builder.createFileInput(e);
            if (c != null) {
                c.setOutputMarkupId(true);
                return c;
            }
        }
        return null;
    }

    @Override
    public Component createFileDownload(FileDownloadElement e) {
        for (BindingBuilderI builder : orderedBuilders) {
            Component c = builder.createFileDownload(e);
            if (c != null) {
                c.setOutputMarkupId(true);
                return c;
            }
        }
        return null;
    }

    @Override
    public Component createTextArea(TextAreaElement e) {
        for (BindingBuilderI builder : orderedBuilders) {
            Component c = builder.createTextArea(e);
            if (c != null) {
                c.setOutputMarkupId(true);
                return c;
            }
        }
        return null;
    }

    @Override
    public Component createCheckboxInput(CheckboxInputElement e) {
        for (BindingBuilderI builder : orderedBuilders) {
            Component c = builder.createCheckboxInput(e);
            if (c != null) {
                c.setOutputMarkupId(true);
                return c;
            }
        }
        return null;
    }

    @Override
    public Component createRadioInput(RadioInputElement e) {
        for (BindingBuilderI builder : orderedBuilders) {
            Component c = builder.createRadioInput(e);
            if (c != null) {
                c.setOutputMarkupId(true);
                return c;
            }
        }
        return null;
    }

    @Override
    public Component createSelect(SelectElement e) {
        for (BindingBuilderI builder : orderedBuilders) {
            Component c = builder.createSelect(e);
            if (c != null) {
                c.setOutputMarkupId(true);
                return c;
            }
        }
        return null;
    }

    @Override
    public Component createImage(ImageElement e) {
        for (BindingBuilderI builder : orderedBuilders) {
            Component c = builder.createImage(e);
            if (c != null) {
                c.setOutputMarkupId(true);
                return c;
            }
        }
        return null;
    }

    @Override
    public Component createLink(LinkElement e) {
        for (BindingBuilderI builder : orderedBuilders) {
            Component c = builder.createLink(e);
            if (c != null) {
                c.setOutputMarkupId(true);
                return c;
            }
        }
        return null;
    }

    @Override
    public Component createTable(TableElement e) {
        for (BindingBuilderI builder : orderedBuilders) {
            Component c = builder.createTable(e);
            if (c != null) {
                c.setOutputMarkupId(true);
                return c;
            }
        }
        return null;
    }

    @Override
    public Component createButton(ButtonElement e) {
        for (BindingBuilderI builder : orderedBuilders) {
            Component c = builder.createButton(e);
            if (c != null) {
                c.setOutputMarkupId(true);
                return c;
            }
        }
        return null;
    }

    @Override
    public Component createUnknown(UnknownPageElementI<?> e) {
        for (BindingBuilderI builder : orderedBuilders) {
            Component c = builder.createUnknown(e);
            if (c != null) {
                c.setOutputMarkupId(true);
                return c;
            }
        }
        return null;
    }

    @Override
    public Component createListView(RepeatingPanelElement e) {
        for (BindingBuilderI builder : orderedBuilders) {
            Component c = builder.createListView(e);
            if (c != null) {
                c.setOutputMarkupId(true);
                return c;
            }
        }
        return null;
    }

    @Override
    public Component createGroupTabbedPanel(GroupTabbedPanelElement e) {
        for (BindingBuilderI builder : orderedBuilders) {
            Component c = builder.createGroupTabbedPanel(e);
            if (c != null) {
                c.setOutputMarkupId(true);
                return c;
            }
        }
        return null;
    }

    @Override
    public Component createDiv(DivElement e) {
        for (BindingBuilderI builder : orderedBuilders) {
            Component c = builder.createDiv(e);
            if (c != null) {
                c.setOutputMarkupId(true);
                return c;
            }
        }
        return null;
    }
}
