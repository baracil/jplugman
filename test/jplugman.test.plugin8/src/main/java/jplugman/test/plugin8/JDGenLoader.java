package jplugman.test.plugin8;

import jplugman.annotation.Extension;
import jplugman.test.core.DummyService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.gen.generator.DungeonGenerator;
import Bastien Aracil.jdgen.api.JDGenConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

@Extension(point = DummyService.class, version = "4.1.0")
@RequiredArgsConstructor
public class JDGenLoader implements DummyService {

    private final @NonNull ModuleLayer moduleLayer;

    @Override
    public @NonNull String getSomething() {
        final var configuration = new JDGenConfiguration(1, 20, 4, 7, 0.5);

        final var dungeonGenerator = DungeonGenerator.create(moduleLayer);

        final var cells = dungeonGenerator.generate(configuration);

        return cells.getSize().toString();
    }


}
