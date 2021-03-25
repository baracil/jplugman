import jplugman.api.Plugin;
import jplugman.test.plugin7.Plugin8;

module jplugman.test.plugin8 {
    requires static lombok;

    requires jplugman.api;
    requires com.google.common;
    requires jplugman.test.core;


    provides Plugin with Plugin8;
}
