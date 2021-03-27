package jplugman.test.plugin7;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceProvider;
import lombok.NonNull;

public class Plugin7 implements Plugin<DummyService7> {

    @Override
    public @NonNull ImmutableSet<Requirement<?>> getRequirements() {
        return ImmutableSet.of();
    }

    @Override
    public @NonNull Class<DummyService7> getServiceClass() {
        return DummyService7.class;
    }

    public @NonNull DummyService7 loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        return new DummyService7();
    }
}
