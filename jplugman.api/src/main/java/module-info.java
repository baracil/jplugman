
module jplugman.api {
    requires static lombok;

    requires transitive jplugman.annotation;
    requires com.google.common;


    exports jplugman.api;

}
