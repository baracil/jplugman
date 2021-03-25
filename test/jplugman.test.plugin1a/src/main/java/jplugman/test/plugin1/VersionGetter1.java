package jplugman.test.plugin1;

import jplugman.test.core.VersionGetter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VersionGetter1 implements VersionGetter {

    @Override
    public @NonNull String getFullVersion() {
        return "Plugin 1a";
    }

}
