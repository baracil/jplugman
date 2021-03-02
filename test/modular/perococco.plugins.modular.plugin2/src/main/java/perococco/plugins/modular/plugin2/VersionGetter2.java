package Bastien Aracil.plugins.modular.plugin2;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.modular.core.VersionGetter;
import Bastien Aracil.vp.VersionProvider;

import java.util.ServiceLoader;

@RequiredArgsConstructor
public class VersionGetter2 implements VersionGetter {

    private final @NonNull VersionProvider versionProvider;

    @Override
    public @NonNull String getFullVersion() {
        return versionProvider.getName() + " " + versionProvider.getVersion();
    }
}
