package baracil.jplugman.manager.action;

import com.google.common.collect.ImmutableList;
import jplugman.api.Application;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import baracil.jplugman.manager.PluginRegistry;
import baracil.jplugman.manager.state.PluginData;

import java.nio.file.Path;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BundleAdder {


    public static void add(@NonNull Application application, @NonNull PluginRegistry pluginRegistry, @NonNull Path bundleLocation) {
        new BundleAdder(application, pluginRegistry, bundleLocation).add();
    }

    private final @NonNull Application application;

    private final @NonNull PluginRegistry pluginRegistry;

    private final @NonNull Path bundleLocation;

    private ImmutableList<PluginData> pluginDataList;

    private void add() {
        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED ni FAILED
        this.loadBundle();
        this.addLoadedPluginToRegistry();
//        this.unloadObsoletePlugins();
        PluginPlugger.plug(pluginRegistry);
        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED ni FAILED
    }

    private void loadBundle() {
        this.pluginDataList = CompatiblePluginContextCreator.create(application, pluginRegistry, bundleLocation)
                                                            .stream()
                                                            .map(PluginData::createInstalled)
                                                            .collect(ImmutableList.toImmutableList());
    }

    private void addLoadedPluginToRegistry() {
        this.pluginDataList.forEach(pluginRegistry::addPluginData);
    }

}
