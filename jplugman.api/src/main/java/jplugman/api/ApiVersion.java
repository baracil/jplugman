package jplugman.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiVersion {
    /**
     * @return the current version of the api of the interface marked
     * with this annotation
     */
    int version();

    /**
     * If the current version is compatible with an older version (for instance because
     * the new version just add a method), this method list all old versions that are
     * retro compatible
     *
     * @return the version numbers the current version is retro compatible with.
     */
    int[] retroVersions() default {};
}
