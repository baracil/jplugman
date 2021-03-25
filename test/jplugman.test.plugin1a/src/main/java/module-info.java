import jplugman.api.Plugin;
import jplugman.test.plugin1.Plugin1;

module jplugman.test.plugin1a {
    requires static lombok;

    requires jplugman.api;
    requires jplugman.test.core;
    requires com.google.common;

    provides Plugin with Plugin1;
}
