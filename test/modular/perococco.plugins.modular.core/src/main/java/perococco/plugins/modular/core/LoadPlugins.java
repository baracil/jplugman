package Bastien Aracil.plugins.modular.core;

import java.nio.file.Path;

public class LoadPlugins extends LoadPluginBase {

    public static void main(String[] args) {
        {
            final var getter1 = getVersionGetter(Path.of("./test/build/plugin1.zip"));
            System.out.println(getter1.getFullVersion());
        }

        {
            final var getter2 = getVersionGetter(Path.of("./test/build/plugin2.zip"));
            System.out.println(getter2.getFullVersion());
        }
    }
}
