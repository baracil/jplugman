package jplugman.manager.impl.state;

import lombok.NonNull;

public interface StateOperation<S> {

    boolean isInInstalledState();

    boolean isInPluggedState();

    boolean isInFailedState();

    @NonNull S setToInstalledState(@NonNull PluginContext context);

    @NonNull S unload(@NonNull PluginContext context);

    @NonNull S load(@NonNull PluginContext context);

    @NonNull S markResolved(@NonNull PluginContext context);

    @NonNull S unInstall(@NonNull PluginContext context);
}
