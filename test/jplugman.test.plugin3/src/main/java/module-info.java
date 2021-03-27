import jplugman.api.Plugin;
import jplugman.test.plugin3.Plugin3;

module jplugman.test.plugin3 {
    requires static lombok;

    requires jplugman.test.core;
    requires com.google.common;
    requires jplugman.api;

    provides Plugin with Plugin3;
}
