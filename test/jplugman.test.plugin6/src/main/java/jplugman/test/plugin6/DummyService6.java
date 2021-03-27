package jplugman.test.plugin6;

import jplugman.annotation.Extension;
import jplugman.test.core.DummyService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Extension(point = DummyService.class, version = "3.1.0")
@RequiredArgsConstructor
public class DummyService6 implements DummyService {

    @Override
    public @NonNull String getSomething() {
        return "PLUGIN5";
    }
}
