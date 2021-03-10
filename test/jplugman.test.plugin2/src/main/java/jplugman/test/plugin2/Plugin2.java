package jplugman.test.plugin2;

import com.google.common.collect.ImmutableSet;
import jplugman.api.*;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;
import jplugman.api.*;
import Bastien Aracil.vp.VersionProvider;

import java.util.ServiceLoader;

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
        final var versionProvider = ServiceLoader.load(pluginLayer, VersionProvider.class).findFirst().get();
        return new VersionGetter2(versionProvider);
    }
}
