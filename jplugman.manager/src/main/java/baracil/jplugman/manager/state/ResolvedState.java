package baracil.jplugman.manager.state;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class ResolvedState implements PluginState {

    private final @NonNull PluginsStateAction pluginsStateAction;

    @Override
    public @NonNull PluginState load() {
        try {
            final var pluginService = pluginsStateAction.load();
            pluginsStateAction.plugService(pluginService);
            return new PluggedState(pluginsStateAction, pluginService);
        } catch (Throwable e) {
            LOG.warn("Could not load plugin {} : {}", pluginsStateAction.getPluginInfo(), e.getMessage());
            LOG.debug(e);
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
