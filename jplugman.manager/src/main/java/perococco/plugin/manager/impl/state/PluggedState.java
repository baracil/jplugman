package Bastien Aracil.plugin.manager.impl.state;

import lombok.Getter;
import lombok.NonNull;
import Bastien Aracil.plugin.api.VersionedService;

public class PluggedState extends PluginStateBase {

    @Getter
    private final @NonNull VersionedService versionedService;

    public PluggedState(@NonNull PluginContext context, @NonNull VersionedService versionedService) {
        super(context);
        this.versionedService = versionedService;
    }

    @Override
    public boolean isInInstalledState() {
        return false;
    }

    @Override
    public boolean isInPluggedState() {
        return true;
    }

    @Override
    public boolean isInFailedState() {
        return false;
    }

    @Override
    public @NonNull PluginState unload() {
        getPluginContext().unplugService(versionedService);
        return new InstalledState(getPluginContext());
    }

}
