package jplugman.test.plugin1c;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceRegistry;
import lombok.NonNull;

public class Plugin1c implements Plugin<VersionGetter1c> {

    @Override
    public @NonNull ImmutableSet<Requirement<?>> getRequirements() {
        return ImmutableSet.of();
    }

    @Override
    public @NonNull Class<VersionGetter1c> getExtensionClass() {
        return VersionGetter1c.class;
    }

    public @NonNull VersionGetter1c loadExtension(@NonNull ModuleLayer pluginLayer, @NonNull ServiceRegistry serviceRegistry) {
        return new VersionGetter1c();
    }
}
