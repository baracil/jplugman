package jplugman.test.plugin1b;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceProvider;
import lombok.NonNull;

public class Plugin1b implements Plugin<VersionGetter1b> {

    @Override
    public @NonNull ImmutableSet<Requirement<?>> getRequirements() {
        return ImmutableSet.of();
    }

    @Override
    public @NonNull Class<VersionGetter1b> getServiceClass() {
        return VersionGetter1b.class;
    }

    public @NonNull VersionGetter1b loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        return new VersionGetter1b();
    }
}
