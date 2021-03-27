package jplugman.test.plugin1c;

import jplugman.annotation.Extension;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Extension(point = VersionGetter.class,version = "1.0.1")
@RequiredArgsConstructor
public class VersionGetter1c implements VersionGetter {

    @Override
    public @NonNull String getFullVersion() {
        return "Plugin 1c";
    }

}
