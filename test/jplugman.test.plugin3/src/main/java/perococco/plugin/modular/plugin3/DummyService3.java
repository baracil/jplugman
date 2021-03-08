package Bastien Aracil.plugin.modular.plugin3;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugin.modular.core.DummyService;
import Bastien Aracil.plugin.modular.core.VersionGetter;

@RequiredArgsConstructor
public class DummyService3 implements DummyService {

    private final @NonNull VersionGetter versionGetter;

    @Override
    public @NonNull String getSomething() {
        return "PLUGIN3 "+versionGetter.getFullVersion();
    }
}
