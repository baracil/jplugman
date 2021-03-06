import Bastien Aracil.plugins.api.Plugin;
import Bastien Aracil.plugins.modular.plugin4.Plugin4;
import Bastien Aracil.vp.VersionProvider;

module Bastien Aracil.plugins.test.plugin4 {
    requires static lombok;

    requires Bastien Aracil.plugins.test.core;
    requires version.provider;
    requires com.google.common;
    requires Bastien Aracil.plugins.api;

    uses VersionProvider;

    provides Plugin with Plugin4;
}
