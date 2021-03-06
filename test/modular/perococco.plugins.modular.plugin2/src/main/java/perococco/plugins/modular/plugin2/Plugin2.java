package Bastien Aracil.plugins.modular.plugin2;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import Bastien Aracil.plugins.api.*;
import Bastien Aracil.plugins.modular.core.DummyService;
import Bastien Aracil.vp.VersionProvider;

import java.nio.charset.StandardCharsets;
import java.util.ServiceLoader;
import java.util.UUID;

public class Plugin2 implements Plugin {

    public static final Version VERSION = Version.with(2,0,0);

    @Override
    public Version getApplicationVersion() {
        return Version.with(1,0,0);
    }

    @Override
    public @NonNull ImmutableSet<VersionedServiceType<?>> getRequirements() {
        return ImmutableSet.of(new VersionedServiceType<>(DummyService.class, Version.with(1,0,0)));
    }

    @Override
    public @NonNull VersionedServiceType<?> getProvidedService() {
        return new VersionedServiceType<>(VersionGetter2.class, VERSION);
    }

    public @NonNull VersionedService loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        serviceProvider.getService(DummyService.class).doSomething();
        final var versionProvider = ServiceLoader.load(pluginLayer, VersionProvider.class).findFirst().get();
        return new VersionedService(new VersionGetter2(versionProvider), VERSION);
    }
}
