package Bastien Aracil.plugins.modular.test;


import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Bastien Aracil.plugins.api.Version;
import Bastien Aracil.plugins.manager.PluginManager;
import Bastien Aracil.plugins.modular.core.VersionGetter;

public class TestLoadPlugins1A extends TestLoadPluginBase {

    @Override
    protected void setUp(@NonNull PluginManager pluginManager) {
        pluginManager.addPluginBundle(getPluginPath("plugin1a"));
    }

    @Test
    public void shouldHaveOneServiceAttached() {
        Assertions.assertEquals(1, attachedServices.size());
    }

    @Test
    public void shouldHaveVersion100() {
        final var version = attachedServices.get(0).getVersion();
        Assertions.assertEquals(Version.with(1,0,0),version);
    }

    @Test
    public void shouldHaveServiceVersionGetter() {
        final var service = attachedServices.get(0).getServiceAs(VersionGetter.class);
        Assertions.assertTrue(service.isPresent());

    }
}
