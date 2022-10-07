import jplugman.api.Plugin;
import jplugman.loader.PluginLoader;
import jplugman.loader.impl.ModularPluginLoader;

module jplugman.loader {

    requires static lombok;

    requires jplugman.api;

    requires org.slf4j;

    provides PluginLoader with ModularPluginLoader;

    uses Plugin;
    uses PluginLoader;

    exports jplugman.loader;

    exports jplugman.loader.impl to jplugman.test.test;
}

