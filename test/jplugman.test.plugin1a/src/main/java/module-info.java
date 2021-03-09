import jplugman.api.Plugin;
import jplugman.test.plugin1.Plugin1;
import Bastien Aracil.vp.VersionProvider;

module jplugman.test.plugin1a {
    requires static lombok;

    requires version.provider;
    requires jplugman.api;
    requires jplugman.test.core;
    requires com.google.common;

    uses VersionProvider;

    provides Plugin with Plugin1;
}
