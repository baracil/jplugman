package jplugman.test.plugin3;

import com.google.common.collect.ImmutableSet;
import jplugman.api.*;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;
import jplugman.api.*;
import jplugman.test.core.DummyService;

public class Plugin3 implements Plugin {

    public static final Version VERSION = Version.with(1, 0, 0);

    @Override
    public @NonNull Version getApplicationVersion() {
        return Version.with(1,0,0);
    }

    @Override
    public @NonNull ImmutableSet<VersionedServiceClass<?>> getRequirements() {
        return ImmutableSet.of(new VersionedServiceClass<>(VersionGetter.class, Version.with(1, 0, 0)));
    }

    @Override
    public @NonNull VersionedServiceClass<?> getProvidedService() {
        return new VersionedServiceClass<>(DummyService.class, VERSION);
    }

    public @NonNull VersionedService loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        final var versionGetter = serviceProvider.getService(VersionGetter.class);
        return new VersionedService(new DummyService3(versionGetter), VERSION);
    }
}