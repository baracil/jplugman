package Bastien Aracil.plugins.manager.impl.state;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.api.Nil;
import Bastien Aracil.plugins.api.VersionedServiceProvider;
import Bastien Aracil.plugins.api.VersionedServiceClass;

import java.nio.file.Path;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PluginData implements StateOperation<Nil>{

    public static @NonNull PluginData create(@NonNull InstalledState state) {
        return new PluginData(state);
    }

    private @NonNull PluginState state;

    @Override
    public long getId() {
        return state.getId();
    }

    @Override
    public boolean isInInstalledState() {
        return state.isInInstalledState();
    }

    @Override
    public boolean isInPluggedState() {
        return state.isInPluggedState();
    }

    @Override
    public @NonNull Nil unload() {
        this.state = state.unload();
        return Nil.NIL;
    }

    @Override
    public @NonNull Nil load() {
        this.state = state.load();
        return Nil.NIL;
    }

    @Override
    public @NonNull Nil markResolved() {
        this.state = state.markResolved();
        return Nil.NIL;
    }

    @Override
    public @NonNull Nil unInstall() {
        this.state = state.unInstall();
        return Nil.NIL;
    }

    @Override
    public @NonNull Nil setToInstalledState() {
        this.state = state.setToInstalledState();
        return Nil.NIL;
    }

    @Override
    public @NonNull PluginContext getPluginContext() {
        return state.getPluginContext();
    }

    public boolean isInFailedState() {
        return this.state.isInFailedState();
    }

    public boolean isServiceAvailable(VersionedServiceClass<?> requirement) {
        return this.state.isServiceAvailable(requirement);
    }

    @Override
    public boolean isFromBundle(@NonNull Path pluginBundleLocation) {
        return this.state.isFromBundle(pluginBundleLocation);
    }

    @Override
    public String toString() {
        return "PluginData{" + state.getClass().getSimpleName()+" - "+state.getPluginContext()+"}";
    }

    public @NonNull VersionedServiceProvider getApplicationServiceProvider() {
        return getPluginContext().getApplicationServiceProvider();
    }
}
