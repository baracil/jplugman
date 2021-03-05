package Bastien Aracil.plugins.manager.impl.state;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.api.VersionedService;
import Bastien Aracil.plugins.manager.impl.PluginState;

@RequiredArgsConstructor
public class PluggedState implements PluginState {

    @Getter
    private final @NonNull VersionedService versionedService;

    @Override
    public boolean isPlugged() {
        return true;
    }
}
