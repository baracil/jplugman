package jplugman.test.plugin1b;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceRegistry;
import lombok.NonNull;

public class Plugin1b implements Plugin<VersionGetter1b> {

    @Override
    public @NonNull ImmutableSet<Requirement<?>> getRequirements() {
        return ImmutableSet.of();
    }

    @Override
    public @NonNull Class<VersionGetter1b> getExtensionClass() {
        return VersionGetter1b.class;
    }

    public @NonNull VersionGetter1b loadExtension(@NonNull ModuleLayer pluginLayer, @NonNull ServiceRegistry serviceRegistry) {
        return new VersionGetter1b();
    }
}
