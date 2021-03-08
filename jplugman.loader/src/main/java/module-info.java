import Bastien Aracil.plugin.api.Plugin;
import Bastien Aracil.plugin.api.PluginLoader;
import Bastien Aracil.plugin.modular.ModularPluginLoader;

module Bastien Aracil.plugins.modular {

    requires static lombok;

    requires com.google.common;

    requires Bastien Aracil.plugins.api;

    requires org.apache.logging.log4j;

    provides PluginLoader with ModularPluginLoader;

    uses Plugin;

    exports Bastien Aracil.plugin.modular to Bastien Aracil.plugins.test.core;
}

