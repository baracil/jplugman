package Bastien Aracil.plugins.modular.plugin1;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import Bastien Aracil.plugins.api.*;
import Bastien Aracil.vp.VersionProvider;

import java.nio.charset.StandardCharsets;
import java.util.ServiceLoader;
import java.util.UUID;

public class Plugin1 implements Plugin {

    public static final Version VERSION = Version.with(1,0,0);


    @Override
    public Version getVersionCompatibility() {
        return Version.with(1,0,0);
    }

    @Override
    public @NonNull ImmutableSet<VersionedServiceType<?>> getRequirements() {
        return ImmutableSet.of();
    }

    @Override
    public @NonNull VersionedServiceType<?> getProvidedServiceType() {
        return new VersionedServiceType<>(VersionGetter1.class, VERSION);
    }

    public @NonNull VersionedService loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        final var versionProvider = ServiceLoader.load(pluginLayer, VersionProvider.class).findFirst().get();
        return new VersionedService(new VersionGetter1(versionProvider), VERSION);
    }
}
