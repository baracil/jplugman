package Bastien Aracil.plugin.modular.plugin4;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugin.modular.core.DummyService;
import Bastien Aracil.plugin.modular.core.VersionGetter;

@RequiredArgsConstructor
public class DummyService4 implements DummyService {

    private final @NonNull VersionGetter versionGetter;

    @Override
    public @NonNull String getSomething() {
        return "PLUGIN4 "+versionGetter.getFullVersion();
    }
}
