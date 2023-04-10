import jplugman.api.Plugin;
import jplugman.test.plugin6.Plugin6;

module jplugman.test.plugin7 {
    requires static lombok;

    requires jplugman.api;
    requires jplugman.test.core;


    provides Plugin with Plugin6;
}
