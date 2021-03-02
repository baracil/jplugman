open module Bastien Aracil.plugins.test.core {
    requires static lombok;

    requires com.google.common;


    requires Bastien Aracil.plugins.modular;
    requires Bastien Aracil.plugins.api;

    exports Bastien Aracil.plugins.modular.core;
}
