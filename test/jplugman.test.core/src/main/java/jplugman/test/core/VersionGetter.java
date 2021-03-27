package jplugman.test.core;

import jplugman.api.ApiVersion;
import lombok.NonNull;

@ApiVersion(version = 3,retroVersions = {2,1})
public interface VersionGetter {

    @NonNull String getFullVersion();
}
