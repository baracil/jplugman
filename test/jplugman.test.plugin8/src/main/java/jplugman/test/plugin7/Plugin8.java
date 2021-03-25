package jplugman.test.plugin7;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Plugin;
import jplugman.api.ServiceProvider;
import jplugman.api.Version;
import jplugman.api.VersionedServiceClass;
import jplugman.test.core.DummyService;
import lombok.NonNull;

public class Plugin8 implements Plugin {

    public static final Version VERSION = Version.with(4, 1, 0);

    @Override
    public @NonNull Version getApplicationVersion() {
        return Version.with(1,0,0);
    }

    @Override
    public @NonNull ImmutableSet<VersionedServiceClass<?>> getRequirements() {
        return ImmutableSet.of(DummyService.class);
    }


    @Override
    public @NonNull VersionedServiceClass<?> getProvidedServiceClass() {
        return new VersionedServiceClass<>(DummyService.class, VERSION);
    }

    public @NonNull Object loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        return new DummyService8();
    }
}
