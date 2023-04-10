import jplugman.api.Plugin;
import jplugman.test.plugin8.Plugin8;

module jplugman.test.plugin8 {
    requires static lombok;

    requires jplugman.api;
    requires jplugman.test.core;
    requires jplugman.annotation;
    requires jplugman.test.lib;


    provides Plugin with Plugin8;
}
