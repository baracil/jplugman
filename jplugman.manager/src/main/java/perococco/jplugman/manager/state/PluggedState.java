package Bastien Aracil.jplugman.manager.state;

import jplugman.api.Disposable;
import jplugman.api.PluginService;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class PluggedState implements PluginState {

    private final @NonNull PluginsStateAction pluginsStateAction;

    @Getter
    private final @NonNull PluginService<?> pluginService;


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
        pluginsStateAction.unplugService(pluginService);
        pluginService.getServiceAs(Disposable.class).ifPresent(this::tryToDispose);
        return new InstalledState(pluginsStateAction);
    }

    private void tryToDispose(@NonNull Disposable disposable) {
        try {
            disposable.dispose();
        } catch (Throwable t) {
            LOG.warn("Error while disposing the service '{}' : {}", pluginService, t.getMessage());
            LOG.debug(t);
        }
    }

}
