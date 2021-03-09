package jplugman.manager.impl.state;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import jplugman.api.VersionedServiceClass;

import java.nio.file.Path;

public interface StateOperation<S> {

    boolean isInInstalledState();

    boolean isInPluggedState();

    boolean isInFailedState();

    @NonNull PluginContext getPluginContext();

    @NonNull S setToInstalledState();

    @NonNull S unload();

    @NonNull S load();

    @NonNull S markResolved();

    @NonNull S unInstall();

    default long getId() {
        return getPluginContext().getId();
    }

    default @NonNull VersionedServiceClass<?> getProvidedService() {
        return getPluginContext().getProvidedService();
    }

    default ImmutableSet<VersionedServiceClass<?>> getPluginRequirements() {
        return getPluginContext().getPluginRequirements();
    }

    boolean isServiceAvailable(@NonNull VersionedServiceClass<?> requirement);

    boolean isFromBundle(@NonNull Path pluginBundleLocation);
}
