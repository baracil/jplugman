package Bastien Aracil.plugin.modular.plugin5;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugin.modular.core.DummyService;

@RequiredArgsConstructor
public class DummyService5 implements DummyService {

    @Override
    public @NonNull String getSomething() {
        return "PLUGIN5";
    }
}
