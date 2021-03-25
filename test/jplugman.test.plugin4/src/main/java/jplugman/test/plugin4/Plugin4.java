package jplugman.test.plugin4;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Plugin;
import jplugman.api.ServiceProvider;
import jplugman.api.Version;
import jplugman.api.VersionedServiceClass;
import jplugman.test.core.DummyService;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;

public class Plugin4 implements Plugin {

    public static final Version VERSION = Version.with(1, 1, 0);

    @Override
    public @NonNull Version getApplicationVersion() {
        return Version.with(1,0,0);
    }

    @Override
    public @NonNull ImmutableSet<VersionedServiceClass<?>> getRequirements() {
        return ImmutableSet.of(new VersionedServiceClass<>(VersionGetter.class, Version.with(2, 1, 0)));
    }

    @Override
    public @NonNull VersionedServiceClass<?> getProvidedServiceClass() {
        return new VersionedServiceClass<>(DummyService.class, VERSION);
    }

    public @NonNull Object loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        final var versionGetter = serviceProvider.getAnyService(VersionGetter.class);
        return new DummyService4(versionGetter);
    }
}
