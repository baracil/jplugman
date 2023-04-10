import jplugman.api.Plugin;
import jplugman.test.plugin1a.Plugin1a;

module jplugman.test.plugin1a {
    requires static lombok;

    requires jplugman.api;
    requires jplugman.test.core;

    provides Plugin with Plugin1a;
}
