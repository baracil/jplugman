package jplugman.test.plugin6;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import jplugman.test.core.DummyService;

@RequiredArgsConstructor
public class DummyService6 implements DummyService {

    @Override
    public @NonNull String getSomething() {
        return "PLUGIN5";
    }
}
