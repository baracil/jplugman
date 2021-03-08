import Bastien Aracil.plugin.api.Plugin;
import Bastien Aracil.plugin.modular.plugin1.Plugin1;
import Bastien Aracil.vp.VersionProvider;

module Bastien Aracil.plugins.test.plugin1b {
    requires static lombok;

    requires version.provider;
    requires Bastien Aracil.plugins.test.core;
    requires Bastien Aracil.plugins.api;
    requires com.google.common;

    uses VersionProvider;

    provides Plugin with Plugin1;
}
