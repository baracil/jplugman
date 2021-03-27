package jplugman.manager.impl.state;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ResolvedState implements PluginState {

    @Override
    public @NonNull PluginState load(@NonNull PluginContext pluginContext) {
        try {
            return pluginContext.load();
        } catch (Throwable e) {
            LOG.warn("Could not load plugin {} : {}", pluginContext, e.getMessage());
            LOG.debug(e);
            return new FailedState();
        }
    }

    @Override
    public @NonNull PluginState unload(@NonNull PluginContext pluginContext) {
        return new InstalledState();
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
