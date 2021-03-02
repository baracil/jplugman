package Bastien Aracil.plugins.manager.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import Bastien Aracil.plugins.manager.Application;
import Bastien Aracil.plugins.manager.impl.state.AcceptedPlugin;
import Bastien Aracil.plugins.manager.impl.state.ResolvedPlugin;

@RequiredArgsConstructor
@Log4j2
public class PluginLinker {

    public static void linkPlugins(@NonNull PluginRegistry registry, @NonNull Application application) {
        new PluginLinker(registry, application).linkPlugins();
    }

    private final @NonNull PluginRegistry registry;
    private final @NonNull Application application;


    private void linkPlugins() {
        while (performOnePass());
    }

    private boolean performOnePass() {
        final boolean someResolved = registry.pluginPaths()
                                             .stream()
                                             .map(p -> registry.updatePluginInfo(p, AcceptedPlugin.class,
                                                                                 this::tryToResolvedPlugin))
                                             .reduce(false, Boolean::logicalOr);

        if (someResolved) {
            registry.pluginPaths()
                    .forEach(p -> registry.updatePluginInfo(p, ResolvedPlugin.class, this::tryToLoadPlugin));
        }
        return someResolved;
    }

    private @NonNull PluginInfo tryToLoadPlugin(@NonNull ResolvedPlugin p) {
        try {
            final var versionedService = p.loadService(application.getServiceProvider());
            application.attachService(versionedService);
            return p.plugged();
        } catch (Exception e) {
            LOG.warn("Could not log plugin {} : {}",p.getPlugin(), e.getMessage());
            LOG.debug(e);
            return p.failed();
        }

    }

    private @NonNull PluginInfo tryToResolvedPlugin(@NonNull AcceptedPlugin pluginInfo) {
        if (!pluginInfo.areRequirementsFulFilled(application.getServiceProvider())) {
            return pluginInfo;
        }
        return pluginInfo.resolved();
    }
}
