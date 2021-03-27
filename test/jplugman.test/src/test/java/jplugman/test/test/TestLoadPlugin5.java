package jplugman.test.test;

import jplugman.manager.PluginManager;
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
        Assertions.assertEquals(1,attachedServices.size());
    }

}
