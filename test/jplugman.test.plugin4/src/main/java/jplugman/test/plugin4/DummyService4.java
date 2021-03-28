package jplugman.test.plugin4;

import jplugman.annotation.Extension;
import jplugman.test.core.DummyService;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Extension(point = DummyService.class, version = "4.1.0")
@RequiredArgsConstructor
public class DummyService4 implements DummyService {

    private final @NonNull VersionGetter versionGetter;

    @Override
    public @NonNull String getSomething() {
        return "PLUGIN4 "+versionGetter.getFullVersion();
    }
}
