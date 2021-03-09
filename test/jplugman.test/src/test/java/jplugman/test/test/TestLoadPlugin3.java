package jplugman.test.test;

import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import jplugman.manager.PluginManager;

public class TestLoadPlugin3 extends TestLoadPluginBase {

    @Override
    protected void setUp(@NonNull PluginManager pluginManager) {
        pluginManager.addPluginBundle(getPluginPath("plugin3"));
    }

    @Test
    public void shouldHaveNoServiceAttached() {
        Assertions.assertEquals(0,attachedServices.size());
    }

}
