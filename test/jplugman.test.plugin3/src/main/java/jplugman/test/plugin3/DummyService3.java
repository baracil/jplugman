package jplugman.test.plugin3;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import jplugman.test.core.DummyService;
import jplugman.test.core.VersionGetter;

@RequiredArgsConstructor
public class DummyService3 implements DummyService {

    private final @NonNull VersionGetter versionGetter;

    @Override
    public @NonNull String getSomething() {
        return "PLUGIN3 "+versionGetter.getFullVersion();
    }
}