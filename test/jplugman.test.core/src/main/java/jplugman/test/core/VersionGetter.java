package jplugman.test.core;

import lombok.NonNull;

public interface VersionGetter {

    @NonNull String getFullVersion();
}
