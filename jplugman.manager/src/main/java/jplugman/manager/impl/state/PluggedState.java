package jplugman.manager.impl.state;

import jplugman.api.Disposable;
import jplugman.api.Extension;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PluggedState implements PluginState {

    @Getter
    private final @NonNull Extension extension;

    public PluggedState(@NonNull Extension extension) {
        this.extension = extension;
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
    public @NonNull PluginState unload(@NonNull PluginContext pluginContext) {
        pluginContext.unplugService(extension);
        extension.getInstanceAs(Disposable.class).ifPresent(this::tryToDispose);
        return new InstalledState();
    }

    private void tryToDispose(@NonNull Disposable disposable) {
        try {
            disposable.dispose();
        } catch (Throwable t) {
            LOG.warn("Error while disposing the service '{}' : {}", extension, t.getMessage());
            LOG.debug(t);
        }
    }

}
