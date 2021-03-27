package jplugman.test.plugin2;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceRegistry;
import lombok.NonNull;

public class Plugin2 implements Plugin<VersionGetter2> {

    @Override
    public @NonNull ImmutableSet<Requirement<?>> getRequirements() {
        return ImmutableSet.of();
    }

    @Override
    public @NonNull Class<VersionGetter2> getExtensionClass() {
        return VersionGetter2.class;
    }

    public @NonNull VersionGetter2 loadExtension(@NonNull ModuleLayer pluginLayer, @NonNull ServiceRegistry serviceRegistry) {
        return new VersionGetter2();
    }
}
