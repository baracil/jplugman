import Bastien Aracil.plugins.api.Plugin;
import Bastien Aracil.plugins.api.PluginLoader;
import Bastien Aracil.plugins.modular.ModularPluginLoader;

module Bastien Aracil.plugins.modular {

    requires static lombok;

    requires com.google.common;

    requires Bastien Aracil.plugins.api;

    provides PluginLoader with ModularPluginLoader;

    uses Plugin;

    exports Bastien Aracil.plugins.modular to Bastien Aracil.plugins.test.core;
}

