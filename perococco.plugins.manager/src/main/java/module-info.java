module Bastien Aracil.plugins.manager {
    uses Bastien Aracil.plugin.manager.PluginManager.Factory;
    requires static lombok;
    requires transitive Bastien Aracil.plugins.api;

    requires org.apache.logging.log4j;
    requires com.google.common;

    exports Bastien Aracil.plugin.manager;
}
