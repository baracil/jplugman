package jplugman.test.plugin1a;

import jplugman.api.PluginVersion;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@PluginVersion(value = VersionGetter.class, version = "1.0.0")
@RequiredArgsConstructor
public class VersionGetter1a implements VersionGetter {

    @Override
    public @NonNull String getFullVersion() {
        return "Plugin 1a";
    }

}
