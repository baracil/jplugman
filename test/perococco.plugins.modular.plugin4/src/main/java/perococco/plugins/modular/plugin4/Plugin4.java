package Bastien Aracil.plugins.modular.plugin4;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import Bastien Aracil.plugins.api.*;
import Bastien Aracil.plugins.modular.core.DummyService;
import Bastien Aracil.plugins.modular.core.VersionGetter;

public class Plugin4 implements Plugin {

    public static final Version VERSION = Version.with(1,1,0);

    @Override
    public @NonNull Version getApplicationVersion() {
        return Version.with(1,0,0);
    }

    @Override
    public @NonNull ImmutableSet<VersionedServiceType<?>> getRequirements() {
        return ImmutableSet.of(new VersionedServiceType<>(VersionGetter.class, Version.with(2,1,0)));
    }

    @Override
    public @NonNull VersionedServiceType<?> getProvidedService() {
        return new VersionedServiceType<>(DummyService.class, VERSION);
    }

    public @NonNull VersionedService loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        final var versionGetter = serviceProvider.getService(VersionGetter.class);
        return new VersionedService(new DummyService4(versionGetter), VERSION);
    }
}
