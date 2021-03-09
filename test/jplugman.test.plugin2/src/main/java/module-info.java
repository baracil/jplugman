import jplugman.api.Plugin;
import jplugman.test.plugin2.Plugin2;
import Bastien Aracil.vp.VersionProvider;

module jplugman.test.plugin2 {
    requires static lombok;

    requires jplugman.test.core;
    requires version.provider;
    requires com.google.common;
    requires jplugman.api;

    uses VersionProvider;

    provides Plugin with Plugin2;
}
