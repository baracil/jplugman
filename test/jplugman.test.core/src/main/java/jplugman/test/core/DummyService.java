package jplugman.test.core;

import jplugman.api.ApiVersion;
import lombok.NonNull;

@ApiVersion(version = 4,retroVersions = {3,1})
public interface DummyService {

    @NonNull String getSomething();
}
