package jplugman.test.plugin4;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceRegistry;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;

public class Plugin4 implements Plugin<DummyService4> {

    @Override
    public @NonNull ImmutableSet<Requirement<?>> getRequirements() {
        return ImmutableSet.of(new Requirement<>(VersionGetter.class, 2));
    }

    @Override
    public @NonNull Class<DummyService4> getExtensionClass() {
        return DummyService4.class;
    }

    public @NonNull DummyService4 loadExtension(@NonNull ModuleLayer pluginLayer, @NonNull ServiceRegistry serviceRegistry) {
        final var versionGetter = serviceRegistry.getAnyService(VersionGetter.class);
        return new DummyService4(versionGetter);
    }
}
