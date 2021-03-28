package jplugman.test.test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import jplugman.api.Application;
import jplugman.api.PluginService;
import jplugman.api.VersionedService;
import lombok.Getter;
import lombok.NonNull;
import lombok.Synchronized;

public class TestApplication implements Application {

    @Getter
    private ImmutableList<PluginService<?>> attachedPluginServices = ImmutableList.of();

    @Override
    public @NonNull ImmutableSet<VersionedService> getApplicationServices(@NonNull Class<?> versionedServiceClass) {
        return ImmutableSet.of();
    }

    @Override
    @Synchronized
    public void plugService(@NonNull PluginService<?> pluginService) {
        attachedPluginServices = ImmutableList.<PluginService<?>>builder()
                                        .addAll(this.attachedPluginServices)
                                        .add(pluginService)
                                        .build();
    }

    @Override
    @Synchronized
    public void unplugService(@NonNull PluginService<?> pluginService) {
        var builder = ImmutableList.<PluginService<?>>builder();
        boolean removed = false;
        for (PluginService<?> attachedPluginServices : attachedPluginServices) {
            if (removed || attachedPluginServices != pluginService) {
                builder.add(attachedPluginServices);
            } else {
                removed = true;
            }
        }
        this.attachedPluginServices = builder.build();
    }

}
