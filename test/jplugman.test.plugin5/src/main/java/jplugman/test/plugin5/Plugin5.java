package jplugman.test.plugin5;

import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceProvider;
import lombok.NonNull;

import java.util.Set;

public class Plugin5 implements Plugin {

    @Override
    public @NonNull Set<Requirement<?>> getRequirements() {
        return Set.of();
    }

    @Override
    public @NonNull Class<?> getServiceClass() {
        return DummyService5.class;
    }

    public @NonNull DummyService5 loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        return new DummyService5();
    }
}
