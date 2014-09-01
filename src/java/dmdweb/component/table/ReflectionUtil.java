package dmdweb.component.table;

import gengui.util.SevereGUIException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectionUtil {
    // TODO meis026: In das Gengui-ReflectionUtil umziehen?

    /**
     * Creates a new instance of an object defined by the given class. Invokes
     * the constructor withe the given paramter types and the given values.
     * Typical checked exceptions will be catched an passed as a
     * RuntimeException.
     * 
     * @param clazz
     *            class of the object to be created
     * @param classParam
     *            classes of the parameter of the desired constructor
     * @param valueParam
     *            values for the constructor
     * @return an instance
     */
    public static Object newInstance(Class clazz, Class<?>[] classParam, Object[] valueParam) {
        try {
            Constructor constructor = clazz.getConstructor(classParam);
            return constructor.newInstance(valueParam);
        } catch (IllegalArgumentException e) {
            throw new SevereGUIException(e);
        } catch (InstantiationException e) {
            throw new SevereGUIException(e);
        } catch (IllegalAccessException e) {
            throw new SevereGUIException(e);
        } catch (InvocationTargetException e) {
            throw new SevereGUIException(e);
        } catch (SecurityException e) {
            throw new SevereGUIException(e);
        } catch (NoSuchMethodException e) {
            throw new SevereGUIException(e);
        }
    }
}
