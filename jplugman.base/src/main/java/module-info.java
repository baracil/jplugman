module jplugman.base {
    requires static lombok;
    requires java.desktop;

    requires transitive jplugman.api;
    requires transitive jplugman.tools;
    requires jplugman.manager;

    requires org.slf4j;


    exports jplugman.base;
}