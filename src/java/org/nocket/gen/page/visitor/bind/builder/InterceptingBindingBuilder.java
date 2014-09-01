package org.nocket.gen.page.visitor.bind.builder;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.element.ButtonElement;
import org.nocket.gen.page.element.CheckboxInputElement;
import org.nocket.gen.page.element.DivElement;
import org.nocket.gen.page.element.FeedbackElement;
import org.nocket.gen.page.element.FileDownloadElement;
import org.nocket.gen.page.element.FileInputElement;
import org.nocket.gen.page.element.FormElement;
import org.nocket.gen.page.element.GroupTabbedPanelElement;
import org.nocket.gen.page.element.ImageElement;
import org.nocket.gen.page.element.LabelElement;
import org.nocket.gen.page.element.LinkElement;
import org.nocket.gen.page.element.ModalElement;
import org.nocket.gen.page.element.PasswordInputElement;
import org.nocket.gen.page.element.PromptElement;
import org.nocket.gen.page.element.RadioInputElement;
import org.nocket.gen.page.element.RepeatingPanelElement;
import org.nocket.gen.page.element.SelectElement;
import org.nocket.gen.page.element.TableElement;
import org.nocket.gen.page.element.TextAreaElement;
import org.nocket.gen.page.element.TextInputElement;
import org.nocket.gen.page.element.UnknownPageElementI;

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
