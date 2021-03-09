import jplugman.api.PluginLoader;

module jplugman.api {
    requires static lombok;
    requires com.google.common;

    exports jplugman.api;

    uses PluginLoader;
}
