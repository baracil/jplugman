package jplugman.test.plugin6;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceProvider;
import lombok.NonNull;

public class Plugin6 implements Plugin<DummyService6> {

    @Override
    public @NonNull ImmutableSet<Requirement<?>> getRequirements() {
        return ImmutableSet.of();
    }


    @Override
    public @NonNull Class<DummyService6> getServiceClass() {
        return DummyService6.class;
    }

    public @NonNull DummyService6 loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        return new DummyService6();
    }
}
