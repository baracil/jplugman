package Bastien Aracil.plugin.modular.test;

import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Bastien Aracil.plugin.manager.PluginManager;

public class TestLoadPlugin5 extends TestLoadPluginBase {

    @Override
    protected void setUp(@NonNull PluginManager pluginManager) {
        pluginManager.addPluginBundle(getPluginPath("plugin5"));
    }

    @Test
    public void shouldHaveOneServiceAttached() {
        Assertions.assertEquals(1,attachedServices.size());
    }

}
