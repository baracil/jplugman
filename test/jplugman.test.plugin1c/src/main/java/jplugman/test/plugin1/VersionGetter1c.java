package jplugman.test.plugin1;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import jplugman.test.core.VersionGetter;
import Bastien Aracil.vp.VersionProvider;

@RequiredArgsConstructor
public class VersionGetter1c implements VersionGetter {

    private final @NonNull VersionProvider versionProvider;

    @Override
    public @NonNull String getFullVersion() {
        return versionProvider.getVersion();
    }

}
