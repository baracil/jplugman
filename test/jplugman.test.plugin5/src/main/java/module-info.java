import Bastien Aracil.plugin.api.Plugin;
import Bastien Aracil.plugin.modular.plugin5.Plugin5;
import Bastien Aracil.vp.VersionProvider;

module Bastien Aracil.plugins.test.plugin5 {
    requires static lombok;

    requires Bastien Aracil.plugins.test.core;
    requires version.provider;
    requires com.google.common;
    requires Bastien Aracil.plugins.api;

    uses VersionProvider;

    provides Plugin with Plugin5;
}
