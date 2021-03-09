package jplugman.test.test;


import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import jplugman.api.Version;
import jplugman.manager.PluginManager;
import jplugman.test.core.VersionGetter;

public class TestLoadPlugin1BThen1A extends TestLoadPluginBase {

    @Override
    protected void setUp(@NonNull PluginManager pluginManager) {
        pluginManager.addPluginBundle(getPluginPath("plugin1b"));
        pluginManager.addPluginBundle(getPluginPath("plugin1a"));
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
        final var service = attachedServices.get(0).getServiceAs(VersionGetter.class);
        Assertions.assertTrue(service.isPresent());

    }
}
