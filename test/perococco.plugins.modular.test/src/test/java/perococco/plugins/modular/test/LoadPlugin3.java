package Bastien Aracil.plugins.modular.test;

import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Bastien Aracil.plugins.manager.PluginManager;

public class LoadPlugin3 extends PluginTestBase {

    @Override
    protected void setUp(@NonNull PluginManager pluginManager) {
        pluginManager.addPluginBundle(getPluginPath("plugin3"));
    }

    @Test
    public void shouldHaveNoServiceAttached() {
        Assertions.assertEquals(0,attachedServices.size());
    }

}
