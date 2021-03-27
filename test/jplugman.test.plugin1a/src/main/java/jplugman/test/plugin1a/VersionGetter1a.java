package jplugman.test.plugin1a;

import jplugman.annotation.Extension;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Extension(point = VersionGetter.class, version = "1.0.0")
@RequiredArgsConstructor
public class VersionGetter1a implements VersionGetter {

    @Override
    public @NonNull String getFullVersion() {
        return "Plugin 1a";
    }

}
