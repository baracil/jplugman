import jplugman.api.Plugin;
import jplugman.test.plugin7.Plugin7;

module jplugman.test.plugin7 {
    requires static lombok;

    requires jplugman.api;
    requires jplugman.test.core;
    requires jplugman.annotation;


    provides Plugin with Plugin7;
}
