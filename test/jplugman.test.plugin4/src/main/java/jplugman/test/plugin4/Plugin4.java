package jplugman.test.plugin4;

import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceProvider;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;

import java.util.Set;

public class Plugin4 implements Plugin {

    @Override
    public @NonNull Set<Requirement<?>> getRequirements() {
        return Set.of(new Requirement<>(VersionGetter.class, 2));
    }

    @Override
    public @NonNull Class<?> getServiceClass() {
        return DummyService4.class;
    }

    public @NonNull DummyService4 loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        final var versionGetter = serviceProvider.getSingleService(VersionGetter.class);
        return new DummyService4(versionGetter);
    }
}
