package Bastien Aracil.plugins.modular.core;

import Bastien Aracil.plugins.api.Plugin;
import Bastien Aracil.plugins.api.ServiceProvider;

import java.nio.file.Path;

public class LoadPluginBase {

    public static VersionGetter getVersionGetter(Path pluginPath) {
        final var loadingResult = Plugin.loadBundle(pluginPath);

        return  loadingResult.loadService(0,ServiceProvider.empty())
                                  .getServiceAs(VersionGetter.class)
                                  .orElseThrow();
    }

}
