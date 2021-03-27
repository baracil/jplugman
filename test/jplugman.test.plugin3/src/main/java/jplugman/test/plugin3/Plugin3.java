package jplugman.test.plugin3;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceRegistry;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;

public class Plugin3 implements Plugin {

    @Override
    public @NonNull ImmutableSet<Requirement<?>> getRequirements() {
        return ImmutableSet.of(new Requirement<>(VersionGetter.class, 1));
    }

    @Override
    public @NonNull Class<DummyService3> getExtensionClass() {
        return DummyService3.class;
    }

    public @NonNull Object loadExtension(@NonNull ModuleLayer pluginLayer, @NonNull ServiceRegistry serviceRegistry) {
        final var versionGetter = serviceRegistry.getAnyService(VersionGetter.class);
        return new DummyService3(versionGetter);
    }
}
