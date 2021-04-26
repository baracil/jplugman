import jplugman.api.Plugin;
import jplugman.test.plugin8.Plugin8;
import Bastien Aracil.gen.generator.DungeonGenerator;

module jplugman.test.plugin8 {
    requires static lombok;

    requires jdgen.generator;

    requires com.google.common;
    requires jplugman.api;
    requires jplugman.test.core;
    requires jplugman.annotation;
    requires jdgen.api;


    uses DungeonGenerator;

    provides Plugin with Plugin8;
}
