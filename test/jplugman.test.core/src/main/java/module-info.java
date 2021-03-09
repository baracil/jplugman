open module jplugman.test.core {
    requires static lombok;

    requires com.google.common;


    requires jplugman.api;
    requires jplugman.manager;

    exports jplugman.test.core;
}
