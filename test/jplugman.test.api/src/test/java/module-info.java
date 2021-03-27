module jplugman.test.api {
    requires static lombok;



    requires jplugman.annotation;
    requires jplugman.api;

    requires org.junit.jupiter.api;
    requires org.junit.jupiter.params;
    requires org.junit.jupiter.engine;

    exports jplugman.test.api to org.junit.platform.commons;
}
