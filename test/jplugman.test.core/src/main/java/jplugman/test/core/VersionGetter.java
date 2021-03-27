package jplugman.test.core;

import jplugman.annotation.ExtensionPoint;
import lombok.NonNull;

@ExtensionPoint(version = 3,retroVersions = {2,1})
public interface VersionGetter {

    @NonNull String getFullVersion();
}
