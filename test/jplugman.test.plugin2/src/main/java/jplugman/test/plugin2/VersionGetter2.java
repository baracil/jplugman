package jplugman.test.plugin2;

import jplugman.test.core.VersionGetter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.vp.VersionProvider;

@RequiredArgsConstructor
public class VersionGetter2 implements VersionGetter {

    private final @NonNull VersionProvider versionProvider;

    @Override
    public @NonNull String getFullVersion() {
        return versionProvider.getName() + " " + versionProvider.getVersion();
    }
}
