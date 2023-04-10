package jplugman.test.plugin1a;

import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceProvider;
import lombok.NonNull;

import java.util.Set;

public class Plugin1a implements Plugin {

    @Override
    public @NonNull Set<Requirement<?>> getRequirements() {
        return Set.of();
    }

    @Override
    public @NonNull Class<?> getServiceClass() {
        return VersionGetter1a.class;
    }

    public @NonNull VersionGetter1a loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        return new VersionGetter1a();
    }
}
