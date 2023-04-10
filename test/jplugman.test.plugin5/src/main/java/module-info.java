import jplugman.api.Plugin;
import jplugman.test.plugin5.Plugin5;

module jplugman.test.plugin5 {
    requires static lombok;

    requires jplugman.api;
    requires jplugman.test.core;


    provides Plugin with Plugin5;
}
