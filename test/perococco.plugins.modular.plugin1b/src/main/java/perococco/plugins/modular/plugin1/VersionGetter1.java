package Bastien Aracil.plugins.modular.plugin1;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.modular.core.VersionGetter;
import Bastien Aracil.vp.VersionProvider;

@RequiredArgsConstructor
public class VersionGetter1 implements VersionGetter {

    private final @NonNull VersionProvider versionProvider;

    @Override
    public @NonNull String getFullVersion() {
        return versionProvider.getVersion();
    }

}
