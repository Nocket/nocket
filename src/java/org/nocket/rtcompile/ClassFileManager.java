// Source: http://www.javablogging.com/dynamic-in-memory-compilation/
package org.nocket.rtcompile;

import java.io.IOException;
import java.security.SecureClassLoader;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;

// TODO: Auto-generated Javadoc
/**
 * The Class ClassFileManager.
 */
public class ClassFileManager extends ForwardingJavaFileManager {
    
    /** Instance of JavaClassObject that will store the compiled bytecode of our class. */
    private JavaClassObject jclassObject;

    /**
     * Will initialize the manager with the specified standard java file manager.
     *
     * @param standardManager the standard manager
     */
    public ClassFileManager(StandardJavaFileManager standardManager) {
        super(standardManager);
    }

    /**
     * Will be used by us to get the class loader for our compiled class. It
     * creates an anonymous class extending the SecureClassLoader which uses the
     * byte code created by the compiler and stored in the JavaClassObject, and
     * returns the Class for it
     *
     * @param location the location
     * @return the class loader
     */
    @Override
    public ClassLoader getClassLoader(Location location) {
        return new SecureClassLoader() {
            @Override
            protected Class<?> findClass(String name)
                    throws ClassNotFoundException {
                byte[] b = jclassObject.getBytes();
                return super.defineClass(name, jclassObject.getBytes(), 0,
                        b.length);
            }
        };
    }

    /**
     * Gives the compiler an instance of the JavaClassObject so that the
     * compiler can write the byte code into it.
     *
     * @param location the location
     * @param className the class name
     * @param kind the kind
     * @param sibling the sibling
     * @return the java file for output
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public JavaFileObject getJavaFileForOutput(Location location,
            String className, Kind kind, FileObject sibling) throws IOException {
        jclassObject = new JavaClassObject(className, kind);
        return jclassObject;
    }
}
