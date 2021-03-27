package jplugman.test.test;

import jplugman.api.Version;
import jplugman.manager.PluginManager;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TestLoadPlugin6ThenPlugin7 extends TestLoadPluginBase {

    @Override
    protected void setUp(@NonNull PluginManager pluginManager) {
        pluginManager.addPluginBundle(getPluginPath("plugin6"));
        pluginManager.addPluginBundle(getPluginPath("plugin7"));
    }

    public static Stream<Arguments> versions() {
        return Stream.of(
                Arguments.of(0,Version.with(3,1,0)),
                Arguments.of(1,Version.with(4,1,0))
        );
    }
    public static Stream<Arguments> types() {
        return Stream.of(
                Arguments.of(0, "jplugman.test.plugin6.DummyService6"),
                Arguments.of(1, "jplugman.test.plugin7.DummyService7")
        );
    }


    @Test
    public void shouldHaveTwoServicesAttached() {
        Assertions.assertEquals(2,attachedServices.size());
    }

    @ParameterizedTest
    @MethodSource("versions")
    public void shouldHaveRightVersion(int index, @NonNull Version expected) {
        final var actual = attachedServices.get(index).getVersion();
        Assertions.assertEquals(expected,actual);
    }

    @ParameterizedTest
    @MethodSource("types")
    public void shouldHaveRightServiceType(int index, @NonNull String className) {
        final var service = attachedServices.get( index).getInstance();
        Assertions.assertEquals(className, service.getClass().getName());
    }



}
