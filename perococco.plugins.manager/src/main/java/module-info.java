module Bastien Aracil.plugins.manager {
    uses Bastien Aracil.plugins.manager.PluginManager.Factory;
    requires static lombok;
    requires Bastien Aracil.plugins.api;

    requires org.apache.logging.log4j;
    requires com.google.common;

    exports Bastien Aracil.plugins.manager;
}
