package jplugman.test.plugin2;

import jplugman.api.PluginVersion;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@PluginVersion(value = VersionGetter.class, version = "2.0.0")
@RequiredArgsConstructor
public class VersionGetter2 implements VersionGetter {

    @Override
    public @NonNull String getFullVersion() {
        return "Plugin version 2";
    }
}
