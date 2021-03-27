package jplugman.test.plugin5;

import jplugman.annotation.Extension;
import jplugman.test.core.DummyService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Extension(point = DummyService.class, version = "3.0.0")
@RequiredArgsConstructor
public class DummyService5 implements DummyService {

    @Override
    public @NonNull String getSomething() {
        return "PLUGIN5";
    }
}
