import jplugman.api.Plugin;
import jplugman.test.plugin2.Plugin2;

module jplugman.test.plugin2 {
    requires static lombok;

    requires jplugman.test.core;
    requires jplugman.api;


    provides Plugin with Plugin2;
}
