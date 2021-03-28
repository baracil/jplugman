package jplugman.test.plugin7;

import jplugman.annotation.Extension;
import jplugman.test.core.DummyService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Extension(point = DummyService.class, version = "4.1.0")
@RequiredArgsConstructor
public class DummyService7 implements DummyService {

    @Override
    public @NonNull String getSomething() {
        return "PLUGIN7";
    }
}
