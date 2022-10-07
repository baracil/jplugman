package baracil.jplugman.manager.state;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ResolvedState implements PluginState {

    private final @NonNull PluginsStateAction pluginsStateAction;

    @Override
    public @NonNull PluginState load() {
        try {
            final var pluginService = pluginsStateAction.loadAndPlugService();
            return new PluggedState(pluginsStateAction, pluginService);
        } catch (Throwable e) {
            if (LOG.isDebugEnabled()) {
                LOG.warn("Could not load plugin {}", pluginsStateAction.getPluginInfo(),e);
            } else {
                LOG.warn("Could not load plugin {} : {}", pluginsStateAction.getPluginInfo(), e.getMessage());
            }
            return new FailedState();
        }
    }

    @Override
    public @NonNull PluginState unload() {
        return new InstalledState(pluginsStateAction);
    }

    @Override
    public boolean isInInstalledState() {
        return false;
    }

    @Override
    public boolean isInPluggedState() {
        return false;
    }

    @Override
    public boolean isInFailedState() {
        return false;
    }
}
