package jplugman.test.plugin1b;

import jplugman.api.PluginVersion;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@PluginVersion(value = VersionGetter.class, version = "1.1.0")
@RequiredArgsConstructor
public class VersionGetter1b implements VersionGetter {

    @Override
    public @NonNull String getFullVersion() {
        return "Plugin 1b";
    }

}
