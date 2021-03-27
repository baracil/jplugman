package jplugman.test.plugin4;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceProvider;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;

public class Plugin4 implements Plugin<DummyService4> {

    @Override
    public @NonNull ImmutableSet<Requirement<?>> getRequirements() {
        return ImmutableSet.of(new Requirement<>(VersionGetter.class, 2));
    }

    @Override
    public @NonNull Class<DummyService4> getServiceClass() {
        return DummyService4.class;
    }

    public @NonNull DummyService4 loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        final var versionGetter = serviceProvider.getAnyService(VersionGetter.class);
        return new DummyService4(versionGetter);
    }
}
