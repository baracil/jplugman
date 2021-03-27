import jplugman.api.Plugin;
import jplugman.test.plugin4.Plugin4;

module jplugman.test.plugin4 {
    requires static lombok;

    requires jplugman.test.core;
    requires com.google.common;
    requires jplugman.api;

    provides Plugin with Plugin4;
}
