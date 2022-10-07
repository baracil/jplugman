package jplugman.test.plugin8;

import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceProvider;
import lombok.NonNull;

import java.util.Set;

public class Plugin8 implements Plugin {

    @Override
    public @NonNull Set<Requirement<?>> getRequirements() {
        return Set.of();
    }

    @Override
    public @NonNull Class<?> getServiceClass() {
        return SorterLoader.class;
    }

    public @NonNull SorterLoader loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        return new SorterLoader(pluginLayer);
    }

}
