package jplugman.test.plugin7;

import jplugman.api.PluginVersion;
import jplugman.test.core.DummyService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@PluginVersion(value = DummyService.class, version = "4.1.0")
@RequiredArgsConstructor
public class DummyService7 implements DummyService {

    @Override
    public @NonNull String getSomething() {
        return "PLUGIN5";
    }
}
