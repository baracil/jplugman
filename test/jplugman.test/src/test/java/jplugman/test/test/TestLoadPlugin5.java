package jplugman.test.test;

import jplugman.api.Version;
import jplugman.manager.PluginManager;
import jplugman.test.core.DummyService;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestLoadPlugin5 extends TestLoadPluginBase {

    @Override
    protected void setUp(@NonNull PluginManager pluginManager) {
        pluginManager.addPluginBundle(getPluginPath("plugin5"));
    }

    @Test
    public void shouldHaveOneServiceAttached() {
        Assertions.assertEquals(1, attachedVersionedServiceData.size());
    }

    @Test
    public void shouldHaveVersion300() {
        final var version = attachedVersionedServiceData.get(0).getVersion();
        Assertions.assertTrue(version.isPresent());
        Assertions.assertEquals(Version.with(3, 0, 0), version.get());
    }

    @Test
    public void shouldHaveServiceDummyService() {
        final var service = attachedVersionedServiceData.get(0).getServiceAs(DummyService.class);
        Assertions.assertTrue(service.isPresent());

    }
}
