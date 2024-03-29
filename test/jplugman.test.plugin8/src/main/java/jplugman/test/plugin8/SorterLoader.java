package jplugman.test.plugin8;

import jplugman.annotation.Extension;
import jplugman.test.core.DummyService;
import jplugman.test.lib.CollectionSorter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Extension(point = DummyService.class, version = "4.1.0")
@RequiredArgsConstructor
public class SorterLoader implements DummyService {

    private final @NonNull ModuleLayer moduleLayer;

    @Override
    public @NonNull String getSomething() {

        final var sorter = CollectionSorter.create(moduleLayer);

        final var sorted = sorter.sort(List.of(1,7,2,3));

        return sorted.toString();
    }


}
