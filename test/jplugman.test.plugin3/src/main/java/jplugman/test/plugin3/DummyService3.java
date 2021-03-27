package jplugman.test.plugin3;

import jplugman.api.PluginVersion;
import jplugman.test.core.DummyService;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@PluginVersion(value = DummyService.class, version = "1.0.0")
@RequiredArgsConstructor
public class DummyService3 implements DummyService {

    private final @NonNull VersionGetter versionGetter;

    @Override
    public @NonNull String getSomething() {
        return "PLUGIN3 "+versionGetter.getFullVersion();
    }
}
