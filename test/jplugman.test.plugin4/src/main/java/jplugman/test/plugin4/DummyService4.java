package jplugman.test.plugin4;

import jplugman.api.PluginVersion;
import jplugman.test.core.DummyService;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@PluginVersion(value = DummyService.class, version = "1.1.0")
@RequiredArgsConstructor
public class DummyService4 implements DummyService {

    private final @NonNull VersionGetter versionGetter;

    @Override
    public @NonNull String getSomething() {
        return "PLUGIN4 "+versionGetter.getFullVersion();
    }
}
