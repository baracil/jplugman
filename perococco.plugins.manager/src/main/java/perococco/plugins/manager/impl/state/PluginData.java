package Bastien Aracil.plugins.manager.impl.state;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.api.Nil;
import Bastien Aracil.plugins.api.VersionedServiceType;

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

    public boolean isServiceAvailable(VersionedServiceType<?> requirement) {
        return this.state.isServiceAvailable(requirement);
    }
}
