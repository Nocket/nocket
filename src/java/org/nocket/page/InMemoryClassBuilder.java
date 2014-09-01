package org.nocket.page;

import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import org.apache.commons.lang.ClassUtils;
import org.nocket.rtcompile.CharSequenceJavaFileObject;
import org.nocket.rtcompile.ClassFileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class allows two create generic Page classes at runtime using the
 * in-memory runtime compiler from package org.nocket.rtcompile. The structure of a
 * fully generic page class is absolutely canonic and therefore can completely
 * be derived from the domain class to display.
 * 
 * @author less02
 */
abstract public class InMemoryClassBuilder {
    private static final Logger log = LoggerFactory.getLogger(InMemoryClassBuilder.class);

    protected final String packageName;
    protected final Class<?> domainClass;

    public InMemoryClassBuilder(Class<?> domainClass) {
        //TODO JL: Konfiguration aus gengui lesen:
        //  - Soll die dynamische Erzeugung überhaupt passieren? 
        //  - Soll sie auch passieren, wenn noch nicht mal das HTML existiert?
        //TODO L: Prüfung auf Serializable der domainClass
        this.domainClass = domainClass;
        this.packageName = ClassUtils.getPackageName(domainClass);
    }

    public Class<?> buildClassInMemory() throws ClassNotFoundException {
        String fullyQualifiedClassName = fullyQualifiedClassName();
        String sourcecode = constructSourceCode();
        log.debug("Compiling source code in memory: " + sourcecode);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        JavaFileManager fileManager =
                new ClassFileManager(compiler.getStandardFileManager(null, null, null));
        List<JavaFileObject> jfiles = new ArrayList<JavaFileObject>();
        jfiles.add(new CharSequenceJavaFileObject(fullyQualifiedClassName, new StringBuilder(sourcecode)));
        compiler.getTask(null, fileManager, null, null, null, jfiles).call();
        Class<?> clazz = fileManager.getClassLoader(null).loadClass(fullyQualifiedClassName);
        log.debug("Compilation successful: " + clazz);
        return clazz;
    }

    protected abstract String fullyQualifiedClassName();

    protected abstract String constructSourceCode();
}
