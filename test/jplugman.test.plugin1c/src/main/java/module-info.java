import jplugman.api.Plugin;
import jplugman.test.plugin1.Plugin1;

module jplugman.test.plugin1b {
    requires static lombok;

    requires version.provider;
    requires jplugman.test.core;
    requires jplugman.api;
    requires com.google.common;


    provides Plugin with Plugin1;
}
