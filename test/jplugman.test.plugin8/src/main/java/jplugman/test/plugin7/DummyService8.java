package jplugman.test.plugin7;

import com.google.common.collect.ImmutableList;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import jplugman.test.core.DummyService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DummyService8 implements VersionGetter {

    private final ImmutableList<DummyService> dummyServices;

    @Override
    public @NonNull String getFullVersion() {
        return dummyServices.stream()
                            .map(DummyService::getSomething)
                            .collect(Collectors.joining(", "));
    }
}
