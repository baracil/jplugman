package jplugman.test.plugin2;

import jplugman.test.core.VersionGetter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VersionGetter2 implements VersionGetter {

    @Override
    public @NonNull String getFullVersion() {
        return "Plugin version 2";
    }
}
