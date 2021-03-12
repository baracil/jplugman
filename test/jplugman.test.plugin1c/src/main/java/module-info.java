import jplugman.api.Plugin;
import jplugman.test.plugin1.Plugin1;
import Bastien Aracil.vp.VersionProvider;

module jplugman.test.plugin1b {
    requires static lombok;

    requires version.provider;
    requires jplugman.test.core;
    requires jplugman.api;
    requires com.google.common;

    uses VersionProvider;

    provides Plugin with Plugin1;
}
