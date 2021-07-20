package baracil.jplugman.manager;

import jplugman.api.ExtensionData;
import jplugman.api.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import baracil.jplugman.manager.state.PluginContext;

import java.util.Optional;

@AllArgsConstructor
public class PluginIdAndExtensionData {

    public static @NonNull Optional<PluginIdAndExtensionData> createWith(@NonNull PluginContext pluginContext) {
        return pluginContext.getExtensionData()
                            .map(s -> new PluginIdAndExtensionData(pluginContext.getId(), s));
    }

    @Getter
    private final long pluginId;

    @Getter
    private final @NonNull ExtensionData extensionData;


    public @NonNull Class<?> getServiceType() {
        return extensionData.getExtensionPointType();
    }

    public @NonNull Version getVersion() {
        return extensionData.getVersion();
    }

}
