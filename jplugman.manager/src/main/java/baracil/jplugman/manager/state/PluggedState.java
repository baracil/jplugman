package baracil.jplugman.manager.state;

import jplugman.api.Disposable;
import jplugman.api.PluginService;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PluggedState implements PluginState {

    private final @NonNull PluginsStateAction pluginsStateAction;

    @Getter
    private final @NonNull PluginService pluginService;


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
            if (LOG.isDebugEnabled()) {
                LOG.warn("Error while disposing the service '{}'", pluginService, t);
            } else {
                LOG.warn("Error while disposing the service '{}' : {}", pluginService, t.getMessage());
            }
        }
    }

}
