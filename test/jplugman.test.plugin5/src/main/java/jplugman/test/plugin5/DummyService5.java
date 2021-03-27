package jplugman.test.plugin5;

import jplugman.api.PluginVersion;
import jplugman.test.core.DummyService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@PluginVersion(value = DummyService.class, version = "3.0.0")
@RequiredArgsConstructor
public class DummyService5 implements DummyService {

    @Override
    public @NonNull String getSomething() {
        return "PLUGIN5";
    }
}
