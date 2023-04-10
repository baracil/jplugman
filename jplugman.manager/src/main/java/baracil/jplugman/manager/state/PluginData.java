package baracil.jplugman.manager.state;

import baracil.jplugman.manager.VersionedServiceProvider;
import jplugman.api.ExtensionData;
import jplugman.api.Requirement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PluginData {

    public static @NonNull PluginData createInstalled(@NonNull PluginContext pluginContext) {
        return new PluginData(pluginContext,new InstalledState(pluginContext));
    }

    @Getter
    private final @NonNull PluginContext pluginContext;

    private @NonNull PluginState state;

    public long getId() {
        return pluginContext.getId();
    }

    public boolean isInInstalledState() {
        return state.isInInstalledState();
    }

    public boolean isInPluggedState() {
        return state.isInPluggedState();
    }

    public void unload() {
        this.state = state.unload();
    }

    public void load() {
        this.state = state.load();
    }

    public void markResolved() {
        this.state = state.markResolved();
    }

    public void unInstall() {
        this.state = state.unInstall();
    }

    public void setToInstalledState() {
        this.state = state.setToInstalledState();
    }

    public boolean isInFailedState() {
        return this.state.isInFailedState();
    }

    @Override
    public String toString() {
        return "PluginData{" + state.getClass().getSimpleName()+" - "+pluginContext+"}";
    }

    public @NonNull VersionedServiceProvider getApplicationServiceProvider() {
        return pluginContext.getApplicationServiceProvider();
    }

    public boolean pluginProvides(Requirement<?> requirement) {
        return pluginContext.pluginProvides(requirement);
    }

    public boolean isFromBundle(@NonNull Path bundleLocation) {
        return pluginContext.getPluginLocation().equals(bundleLocation);
    }

    public @NonNull Set<Requirement<?>> getPluginRequirements() {
        return pluginContext.getPluginRequirements();
    }

    public @NonNull Optional<? extends ExtensionData> getExtensionData() {
        return pluginContext.getExtensionData();
    }
}
