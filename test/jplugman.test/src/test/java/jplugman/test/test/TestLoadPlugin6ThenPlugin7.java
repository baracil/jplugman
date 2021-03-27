package jplugman.test.test;

import jplugman.api.Version;
import jplugman.manager.PluginManager;
import jplugman.test.core.DummyService;
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
                Arguments.of(0,Version.with(4,1,0))
        );
    }
    public static Stream<Arguments> types() {
        return Stream.of(
                Arguments.of(0, DummyService.class)
        );
    }


    @Test
    public void shouldHaveOneServiceAttached() {
        Assertions.assertEquals(1, attachedVersionedServiceData.size());
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
    public void shouldHaveRightServiceType(int index, @NonNull Class<?> serviceType) {
        final var service = attachedVersionedServiceData.get(index).getServiceAs(serviceType);
        Assertions.assertTrue(service.isPresent());
    }



}
