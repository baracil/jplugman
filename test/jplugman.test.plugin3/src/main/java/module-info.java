import jplugman.api.Plugin;
import jplugman.test.plugin3.Plugin3;

module jplugman.test.plugin3 {
    requires static lombok;

    requires jplugman.test.core;
    requires jplugman.api;

    provides Plugin with Plugin3;
}
