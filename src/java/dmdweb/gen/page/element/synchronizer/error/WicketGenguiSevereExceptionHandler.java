package dmdweb.gen.page.element.synchronizer.error;

import gengui.util.DomainProperties;
import gengui.util.SevereExceptionHandler;
import gengui.util.SevereGUIException;
import dmdweb.gen.domain.WebDomainProperties;

public class WicketGenguiSevereExceptionHandler implements SevereExceptionHandler {

    public static void init() {
        new WebDomainProperties().setProperty(DomainProperties.GLOBAL_EXCEPTIONHANDLER_CLASS,
                WicketGenguiSevereExceptionHandler.class.getName());
    }

    @Override
    public RuntimeException process(Throwable t) {
        return new RuntimeException(t);
    }

    @Override
    public RuntimeException process(String problem) {
        return new RuntimeException(problem);
    }

    @Override
    public RuntimeException process(String problem, Throwable t) {
        return new RuntimeException(problem, t);
    }

    @Override
    public void displayErrorMessage(Throwable e) {
        //throw exception to display as error page in wicket
        throw new SevereGUIException(e);
    }

}
