package Bastien Aracil.plugins.modular.plugin5;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import Bastien Aracil.plugins.api.*;
import Bastien Aracil.plugins.modular.core.DummyService;
import Bastien Aracil.plugins.modular.core.VersionGetter;

public class Plugin5 implements Plugin {

    public static final Version VERSION = Version.with(3,1,0);

    @Override
    public @NonNull Version getApplicationVersion() {
        return Version.with(1,0,0);
    }

    @Override
    public @NonNull ImmutableSet<VersionedServiceClass<?>> getRequirements() {
        return ImmutableSet.of();
    }

    @Override
    public @NonNull VersionedServiceClass<?> getProvidedService() {
        return new VersionedServiceClass<>(DummyService.class, VERSION);
    }

    public @NonNull VersionedService loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        return new VersionedService(new DummyService5(), VERSION);
    }
}
