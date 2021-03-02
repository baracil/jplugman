module Bastien Aracil.plugins.manager {
    requires static lombok;
    requires Bastien Aracil.plugins.api;

    requires org.apache.logging.log4j;
    requires com.google.common;

    exports Bastien Aracil.plugins.manager;
}
