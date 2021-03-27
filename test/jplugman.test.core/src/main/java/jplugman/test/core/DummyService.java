package jplugman.test.core;

import jplugman.annotation.ExtensionPoint;
import lombok.NonNull;

@ExtensionPoint(version = 4,retroVersions = {3,1})
public interface DummyService {

    @NonNull String getSomething();
}
