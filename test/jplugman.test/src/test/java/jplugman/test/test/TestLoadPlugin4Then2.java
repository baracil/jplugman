package jplugman.test.test;

import jplugman.api.Version;
import jplugman.manager.PluginManager;
import jplugman.test.core.DummyService;
import jplugman.test.core.VersionGetter;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TestLoadPlugin4Then2 extends TestLoadPluginBase {

    @Override
    protected void setUp(@NonNull PluginManager pluginManager) {
        pluginManager.addPluginBundle(getPluginPath("plugin4"));
        pluginManager.addPluginBundle(getPluginPath("plugin2"));
    }


    public static Stream<ExpectedServices> expectedServices() {
        return Stream.of(ExpectedServices.builder()
                                         .version(VersionGetter.class, "3.0.0")
                                         .version(DummyService.class, "4.1.0")
                                         .build());
    }


    @ParameterizedTest
    @MethodSource("expectedServices")
    public void shouldHaveOneServiceAttached(@NonNull ExpectedServices versions) {
        checkNbServices(versions);
    }

    @ParameterizedTest
    @MethodSource("expectedServices")
    public void shouldHaveRightServices(@NonNull ExpectedServices versions) {
        checkServices(versions);
    }

}
