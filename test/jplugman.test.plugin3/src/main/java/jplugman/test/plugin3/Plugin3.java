package jplugman.test.plugin3;

import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceProvider;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;

import java.util.Set;

public class Plugin3 implements Plugin {

    @Override
    public @NonNull Set<Requirement<?>> getRequirements() {
        return Set.of(new Requirement<>(VersionGetter.class, 1));
    }

    @Override
    public @NonNull Class<?> getServiceClass() {
        return DummyService3.class;
    }

    public @NonNull Object loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        final var versionGetter = serviceProvider.getSingleService(VersionGetter.class);
        return new DummyService3(versionGetter);
    }
}
