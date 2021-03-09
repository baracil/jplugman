package jplugman.test.plugin5;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import jplugman.test.core.DummyService;

@RequiredArgsConstructor
public class DummyService5 implements DummyService {

    @Override
    public @NonNull String getSomething() {
        return "PLUGIN5";
    }
}
