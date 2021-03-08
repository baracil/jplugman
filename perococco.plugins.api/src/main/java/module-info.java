import Bastien Aracil.plugin.api.PluginLoader;

module Bastien Aracil.plugins.api {
    requires static lombok;
    requires com.google.common;

    exports Bastien Aracil.plugin.api;

    uses PluginLoader;
}
