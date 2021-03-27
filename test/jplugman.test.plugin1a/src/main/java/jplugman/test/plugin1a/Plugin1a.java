package jplugman.test.plugin1a;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceRegistry;
import lombok.NonNull;

public class Plugin1a implements Plugin<VersionGetter1a> {

    @Override
    public @NonNull ImmutableSet<Requirement<?>> getRequirements() {
        return ImmutableSet.of();
    }

    @Override
    public @NonNull Class<VersionGetter1a> getExtensionClass() {
        return VersionGetter1a.class;
    }

    public @NonNull VersionGetter1a loadExtension(@NonNull ModuleLayer pluginLayer, @NonNull ServiceRegistry serviceRegistry) {
        return new VersionGetter1a();
    }
}
