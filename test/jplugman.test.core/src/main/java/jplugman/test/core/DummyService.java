package jplugman.test.core;

import jplugman.api.PluginVersion;
import lombok.NonNull;

@PluginVersion("1.0.0")
public interface DummyService extends Runnable {

    @NonNull String getSomething();
}
