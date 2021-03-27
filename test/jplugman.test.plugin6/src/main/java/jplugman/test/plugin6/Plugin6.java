package jplugman.test.plugin6;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceRegistry;
import lombok.NonNull;

public class Plugin6 implements Plugin<DummyService6> {

    @Override
    public @NonNull ImmutableSet<Requirement<?>> getRequirements() {
        return ImmutableSet.of();
    }


    @Override
    public @NonNull Class<DummyService6> getExtensionClass() {
        return DummyService6.class;
    }

    public @NonNull DummyService6 loadExtension(@NonNull ModuleLayer pluginLayer, @NonNull ServiceRegistry serviceRegistry) {
        return new DummyService6();
    }
}
