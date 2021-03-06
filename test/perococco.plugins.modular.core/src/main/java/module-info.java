open module Bastien Aracil.plugins.test.core {
    requires static lombok;

    requires com.google.common;


    requires Bastien Aracil.plugins.api;
    requires Bastien Aracil.plugins.manager;

    exports Bastien Aracil.plugins.modular.core;
}
