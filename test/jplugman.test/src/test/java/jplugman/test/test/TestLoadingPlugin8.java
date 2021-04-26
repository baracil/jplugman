package jplugman.test.test;

import jplugman.manager.PluginManager;
import jplugman.test.core.DummyService;
import lombok.NonNull;
import org.junit.jupiter.api.Test;

public class TestLoadingPlugin8 extends TestLoadPluginBase {

    @Override
    protected void setUp(@NonNull PluginManager pluginManager) {
        pluginManager.addPluginBundle(getPluginPath("plugin8"));
    }

    @Test
    void displayNumberOfCells() {
        final var service = attachedVersionedServiceData.get(0)
                                                        .getServiceAs(DummyService.class);

        final var something  = service.get().getSomething();
        System.out.println(something);
    }
}
