package Bastien Aracil.plugins.manager.impl;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.api.*;
import Bastien Aracil.plugins.manager.impl.state.InstalledState;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@RequiredArgsConstructor
public class PluginData {

    public static @NonNull PluginData installed(@NonNull Path pluginLocation, @NonNull ModuleLayer moduleLayer, @NonNull Plugin plugin) {
        return new PluginData(ID_GENERATOR.getAndIncrement(),pluginLocation,moduleLayer,plugin,new InstalledState());
    }

    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    private final long id;
    private final @NonNull Path pluginLocation;
    private final @NonNull ModuleLayer moduleLayer;
    private final @NonNull Plugin plugin;
    private @NonNull PluginState state;

    public @NonNull VersionedServiceType<?> getProvidedServiceType() {
        return plugin.getProvidedServiceType();
    }

    public @NonNull ImmutableSet<VersionedServiceType<?>> getPluginRequirements() {
        return plugin.getRequirements();
    }

    public void operate(@NonNull Operator operator) {
        this.state = operator.operate(this);
    }

    public boolean isInstalled() {
        return state.isInstalled();
    }

    public @NonNull VersionedService loadService(@NonNull ServiceProvider serviceProvider) {
        return this.plugin.loadService(moduleLayer,serviceProvider);
    }

    public boolean isFailed() {
        return state.isFailed();
    }

    public boolean isResolved() {
        return state.isResolved();
    }

    public boolean isPlugged() {
        return state.isPlugged();
    }
}
