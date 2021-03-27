package jplugman.test.plugin1c;

import jplugman.api.PluginVersion;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@PluginVersion(value = VersionGetter.class, version = "1.0.1")
@RequiredArgsConstructor
public class VersionGetter1c implements VersionGetter {

    @Override
    public @NonNull String getFullVersion() {
        return "Plugin 1c";
    }

}
