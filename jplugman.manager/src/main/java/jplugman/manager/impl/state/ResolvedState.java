package jplugman.manager.impl.state;

import jplugman.manager.impl.PluginSpecificServiceProvider;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ResolvedState extends PluginStateBase {

    public ResolvedState(@NonNull PluginContext context) {
        super(context);
    }

    @Override
    public @NonNull PluginState load() {
        try {
            final var serviceProvider = PluginSpecificServiceProvider.create(
                    pluginContext.getPluginRequirements(),
                    pluginContext.getApplicationServiceProvider().thenSearch(pluginContext.getPluginServiceProvider()));
            final var service = pluginContext.loadService(serviceProvider);
            pluginContext.plugService(service);
            return new PluggedState(getPluginContext(), service);
        } catch (Throwable e) {
            LOG.warn("Could not load plugin {} : {}", pluginContext, e.getMessage());
            LOG.debug(e);
            return new FailedState(getPluginContext());
        }

    }

    @Override
    public @NonNull PluginState unload() {
        return new InstalledState(getPluginContext());
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
