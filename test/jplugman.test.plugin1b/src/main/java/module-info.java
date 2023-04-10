import jplugman.api.Plugin;
import jplugman.test.plugin1b.Plugin1b;

module jplugman.test.plugin1b {
    requires static lombok;

    requires jplugman.test.core;
    requires jplugman.api;

    provides Plugin with Plugin1b;
}
