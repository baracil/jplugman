package jplugman.test.test;


import jplugman.api.Version;
import jplugman.manager.PluginManager;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestLoadPlugin1BThen1A extends TestLoadPluginBase {

    @Override
    protected void setUp(@NonNull PluginManager pluginManager) {
        pluginManager.addPluginBundle(getPluginPath("plugin1b"));
        pluginManager.addPluginBundle(getPluginPath("plugin1a"));
    }

    @Test
    public void shouldHaveOneServiceAttached() {
        Assertions.assertEquals(1, attachedVersionedServiceData.size());
    }

    @Test
    public void shouldHaveVersion110() {
        final var version = attachedVersionedServiceData.get(0).getVersion();
        Assertions.assertTrue(version.isPresent());
        Assertions.assertEquals(Version.with(1,1,0),version.get());
    }

    @Test
    public void shouldHaveServiceVersionGetter() {
        final var service = attachedVersionedServiceData.get(0).getServiceAs(VersionGetter.class);
        Assertions.assertTrue(service.isPresent());

    }
}
