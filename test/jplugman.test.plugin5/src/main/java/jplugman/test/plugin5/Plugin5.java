package jplugman.test.plugin5;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceRegistry;
import lombok.NonNull;

public class Plugin5 implements Plugin<DummyService5> {

    @Override
    public @NonNull ImmutableSet<Requirement<?>> getRequirements() {
        return ImmutableSet.of();
    }


    @Override
    public @NonNull Class<DummyService5> getExtensionClass() {
        return DummyService5.class;
    }

    public @NonNull DummyService5 loadExtension(@NonNull ModuleLayer pluginLayer, @NonNull ServiceRegistry serviceRegistry) {
        return new DummyService5();
    }
}
