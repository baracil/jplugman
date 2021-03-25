package jplugman.test.plugin7;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import jplugman.test.core.DummyService;

@RequiredArgsConstructor
public class DummyService7 implements DummyService {

    @Override
    public @NonNull String getSomething() {
        return "PLUGIN5";
    }
}
