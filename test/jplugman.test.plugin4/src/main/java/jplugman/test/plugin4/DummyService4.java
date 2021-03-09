package jplugman.test.plugin4;

import jplugman.test.core.VersionGetter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import jplugman.test.core.DummyService;

@RequiredArgsConstructor
public class DummyService4 implements DummyService {

    private final @NonNull VersionGetter versionGetter;

    @Override
    public @NonNull String getSomething() {
        return "PLUGIN4 "+versionGetter.getFullVersion();
    }
}
