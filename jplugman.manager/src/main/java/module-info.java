import jplugman.manager.PluginManager;

module jplugman.manager {
    uses PluginManager.Factory;
    requires static lombok;

    requires jplugman.annotation;
    requires transitive jplugman.api;
    requires jplugman.loader;

    requires org.apache.logging.log4j;

    exports jplugman.manager;

}
