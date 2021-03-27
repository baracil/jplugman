package jplugman.test.plugin7;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceRegistry;
import lombok.NonNull;

public class Plugin7 implements Plugin<DummyService7> {

    @Override
    public @NonNull ImmutableSet<Requirement<?>> getRequirements() {
        return ImmutableSet.of();
    }

    @Override
    public @NonNull Class<DummyService7> getExtensionClass() {
        return DummyService7.class;
    }

    public @NonNull DummyService7 loadExtension(@NonNull ModuleLayer pluginLayer, @NonNull ServiceRegistry serviceRegistry) {
        return new DummyService7();
    }
}
