package jplugman.test.plugin3;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceProvider;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;

public class Plugin3 implements Plugin {

    @Override
    public @NonNull ImmutableSet<Requirement<?>> getRequirements() {
        return ImmutableSet.of(new Requirement<>(VersionGetter.class, 1));
    }

    @Override
    public @NonNull Class<DummyService3> getServiceClass() {
        return DummyService3.class;
    }

    public @NonNull Object loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        final var versionGetter = serviceProvider.getAnyService(VersionGetter.class);
        return new DummyService3(versionGetter);
    }
}
