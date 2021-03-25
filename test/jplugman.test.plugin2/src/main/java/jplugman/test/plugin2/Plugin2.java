package jplugman.test.plugin2;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Plugin;
import jplugman.api.ServiceProvider;
import jplugman.api.Version;
import jplugman.api.VersionedServiceClass;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;

public class Plugin2 implements Plugin {

    public static final Version VERSION = Version.with(2, 0, 0);

    @Override
    public @NonNull Version getApplicationVersion() {
        return Version.with(1,0,0);
    }

    @Override
    public @NonNull ImmutableSet<VersionedServiceClass<?>> getRequirements() {
        return ImmutableSet.of();
    }

    @Override
    public @NonNull VersionedServiceClass<?> getProvidedServiceClass() {
        return new VersionedServiceClass<>(VersionGetter.class, VERSION);
    }

    public @NonNull Object loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        return new VersionGetter2();
    }
}
