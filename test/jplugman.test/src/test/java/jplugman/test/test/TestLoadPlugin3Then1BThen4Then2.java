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

public class TestLoadPlugin3Then1BThen4Then2 extends TestLoadPluginBase {

    @Override
    protected void setUp(@NonNull PluginManager pluginManager) {
        pluginManager.addPluginBundle(getPluginPath("plugin3"));
        pluginManager.addPluginBundle(getPluginPath("plugin1b"));
        pluginManager.addPluginBundle(getPluginPath("plugin4"));
        pluginManager.addPluginBundle(getPluginPath("plugin2"));
    }


    public static Stream<Arguments> versions() {
        return Stream.of(
                Arguments.of(0,Version.with(2,0,0)),
                Arguments.of(1,Version.with(1,1,0))
        );
    }
    public static Stream<Arguments> types() {
        return Stream.of(
                Arguments.of(0,VersionGetter.class),
                Arguments.of(1,DummyService.class)
        );
    }


    @Test
    public void shouldHaveTwoServicesAttached() {
        Assertions.assertEquals(2, attachedVersionedServiceData.size());
    }

    @ParameterizedTest
    @MethodSource("versions")
    public void shouldHaveRightVersion(int index, @NonNull Version expected) {
        final var actual = attachedVersionedServiceData.get(index).getVersion();
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(expected,actual.get());
    }

    @ParameterizedTest
    @MethodSource("types")
    public void shouldHaveRightServiceType(int index, @NonNull Class<?> expectedType) {
        final var service = attachedVersionedServiceData.get(index).getServiceAs(expectedType);
        Assertions.assertTrue(service.isPresent(),"expected="+expectedType+"  actual="+ attachedVersionedServiceData.get(index).getServiceClass());
    }

}
