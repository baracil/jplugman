import Bastien Aracil.plugins.api.PluginLoader;

module Bastien Aracil.plugins.api {
    requires static lombok;
    requires com.google.common;

    exports Bastien Aracil.plugins.api;

    uses PluginLoader;
}
