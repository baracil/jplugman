import jplugman.api.Plugin;
import jplugman.api.PluginLoader;
import jplugman.loader.ModularPluginLoader;

module jplugman.loader {

    requires static lombok;

    requires com.google.common;

    requires jplugman.api;

    requires org.apache.logging.log4j;

    provides PluginLoader with ModularPluginLoader;

    uses Plugin;

    exports jplugman.loader to jplugman.test.test;
}

