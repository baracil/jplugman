package jplugman.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An extension is a type provided by the plugin
 * that implements an {@link ExtensionPoint}.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Extension {

    /**
     * @return the extension point the annotated service provides. This must be an
     * interface annotated with the {@link ExtensionPoint} annotation provided by
     * the main application.
     * Also, the {@link #version()} ()} method must return a version
     * that is compatible with a version listed in the {@link ExtensionPoint}
     * for the plugin to be loaded.
     */
    Class<?> point();

    /**
     * @return the semantic version of the extension. The major version
     * must be compatible with the version of the extension point otherwise
     * the plugin will not be loaded
     */
    String version();

}
