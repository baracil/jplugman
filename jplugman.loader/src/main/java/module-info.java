import jplugman.api.Plugin;
import jplugman.api.PluginLoader;
import jplugman.test.ModularPluginLoader;

module jplugman.modular {

    requires static lombok;

    requires com.google.common;

    requires jplugman.api;

    requires org.apache.logging.log4j;

    provides PluginLoader with ModularPluginLoader;

    uses Plugin;

    exports jplugman.test to Bastien Aracil.plugins.test.core;
}

