package jplugman.test.plugin1;

import com.google.common.collect.ImmutableSet;
import jplugman.api.*;
import lombok.NonNull;
import jplugman.api.*;
import jplugman.test.core.VersionGetter;
import Bastien Aracil.vp.VersionProvider;

import java.util.ServiceLoader;

public class Plugin1 implements Plugin {

    public static final Version VERSION = Version.with(1, 1, 0);


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
        final var versionProvider = ServiceLoader.load(pluginLayer, VersionProvider.class).findFirst().get();
        return new VersionGetter1(versionProvider);
    }
}
