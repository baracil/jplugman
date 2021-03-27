package jplugman.api;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PluginVersion {

    /**
     * @return the service the annotated service provides. This must be an
     * interface annotated with the {@link ApiVersion} annotation provided by
     * the main application.
     * Also, the {@link #version()} method must return a version with a major
     * that is compatible with a version listed in the {@link ApiVersion}.
     */
    Class<?> value();

    /**
     * @return the semantic version of the provided service.
     */
    String version();
}
