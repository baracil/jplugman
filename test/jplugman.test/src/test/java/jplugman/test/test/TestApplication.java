package jplugman.test.test;

import jplugman.api.Application;
import jplugman.api.PluginService;
import jplugman.api.VersionedService;
import lombok.Getter;
import lombok.NonNull;
import lombok.Synchronized;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestApplication implements Application {

    @Getter
    private List<PluginService> attachedPluginServices = List.of();

    @Override
    public @NonNull Set<VersionedService> getApplicationServices(@NonNull Class<?> versionedServiceClass) {
        return Set.of();
    }

    @Override
    @Synchronized
    public void plugService(@NonNull PluginService pluginService) {
        final List<PluginService> updated = new ArrayList<>(attachedPluginServices.size()+1);
        updated.addAll(attachedPluginServices);
        updated.add(pluginService);
        attachedPluginServices = updated;
    }

    @Override
    @Synchronized
    public void unplugService(@NonNull PluginService pluginService) {
        final List<PluginService> updated = new ArrayList<>(attachedPluginServices.size());
        boolean removed = false;
        for (PluginService attachedPluginServices : attachedPluginServices) {
            if (removed || attachedPluginServices != pluginService) {
                updated.add(attachedPluginServices);
            } else {
                removed = true;
            }
        }
        this.attachedPluginServices = updated;
    }

}
