package jplugman.test.plugin1;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import jplugman.test.core.VersionGetter;

@RequiredArgsConstructor
public class VersionGetter1c implements VersionGetter {

    @Override
    public @NonNull String getFullVersion() {
        return "Plugin 1c";
    }

}
