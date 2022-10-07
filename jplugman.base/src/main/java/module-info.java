module jplugman.base {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;

    requires transitive jplugman.api;
    requires transitive jplugman.tools;
    requires jplugman.manager;


    exports jplugman.base;
}