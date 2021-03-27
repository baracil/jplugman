package jplugman.test.test;


import jplugman.api.Version;
import jplugman.manager.PluginManager;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestLoadPlugin1AThen1B extends TestLoadPluginBase {

    @Override
    protected void setUp(@NonNull PluginManager pluginManager) {
        pluginManager.addPluginBundle(getPluginPath("plugin1a"));
        pluginManager.addPluginBundle(getPluginPath("plugin1b"));
    }

    @Test
    public void shouldHaveOneServiceAttached() {
        Assertions.assertEquals(1, attachedServices.size());
    }

    @Test
    public void shouldHaveVersion110() {
        final var version = attachedServices.get(0).getVersion();
        Assertions.assertEquals(Version.with(1,1,0),version);
    }

    @Test
    public void shouldHaveServiceVersionGetter() {
        final var service = attachedServices.get(0).getInstanceAs(VersionGetter.class);
        Assertions.assertTrue(service.isPresent());

    }
}
