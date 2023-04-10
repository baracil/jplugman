import jplugman.api.Plugin;
import jplugman.test.plugin1c.Plugin1c;

module jplugman.test.plugin1c {
    requires static lombok;

    requires jplugman.test.core;
    requires jplugman.api;

    provides Plugin with Plugin1c;
}
