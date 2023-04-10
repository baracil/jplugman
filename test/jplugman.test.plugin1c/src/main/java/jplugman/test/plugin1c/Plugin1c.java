package jplugman.test.plugin1c;

import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceProvider;
import lombok.NonNull;

import java.util.Set;

public class Plugin1c implements Plugin {

    @Override
    public @NonNull Set<Requirement<?>> getRequirements() {
        return Set.of();
    }

    @Override
    public @NonNull Class<?> getServiceClass() {
        return VersionGetter1c.class;
    }

    public @NonNull VersionGetter1c loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        return new VersionGetter1c();
    }
}
