import jplugman.api.Plugin;
import jplugman.test.plugin3.Plugin3;
import Bastien Aracil.vp.VersionProvider;

module jplugman.test.plugin3 {
    requires static lombok;

    requires jplugman.test.core;
    requires version.provider;
    requires com.google.common;
    requires jplugman.api;

    uses VersionProvider;

    provides Plugin with Plugin3;
}
