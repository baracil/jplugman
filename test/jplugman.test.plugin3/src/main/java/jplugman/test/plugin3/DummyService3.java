package jplugman.test.plugin3;

import jplugman.annotation.Extension;
import jplugman.test.core.DummyService;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Extension(point = DummyService.class, version = "4.0.0")
@RequiredArgsConstructor
public class DummyService3 implements DummyService {

    private final @NonNull VersionGetter versionGetter;

    @Override
    public @NonNull String getSomething() {
        return "PLUGIN3 "+versionGetter.getFullVersion();
    }
}
