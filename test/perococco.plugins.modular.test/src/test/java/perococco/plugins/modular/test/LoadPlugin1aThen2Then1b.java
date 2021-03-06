package Bastien Aracil.plugins.modular.test;


import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import Bastien Aracil.plugins.api.Version;
import Bastien Aracil.plugins.manager.PluginManager;
import Bastien Aracil.plugins.modular.core.VersionGetter;

import java.util.stream.Stream;

public class LoadPlugin1aThen2Then1b extends PluginTestBase {

    @Override
    protected void setUp(@NonNull PluginManager pluginManager) {
        pluginManager.addPluginBundle(getPluginPath("plugin1a"));
        pluginManager.addPluginBundle(getPluginPath("plugin2"));
        pluginManager.addPluginBundle(getPluginPath("plugin1b"));
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
                Arguments.of(1,VersionGetter.class)
        );
    }



    @Test
    public void shouldHaveTwoServicesAttached() {
        Assertions.assertEquals(2, attachedServices.size());
    }

    @ParameterizedTest
    @MethodSource("versions")
    public void shouldHaveRightVersion(int index, @NonNull Version expected) {
        final var actual = attachedServices.get(index).getVersion();
        Assertions.assertEquals(expected,actual);
    }

    @ParameterizedTest
    @MethodSource("types")
    public void shouldHaveRightServiceType(int index, @NonNull Class<?> expectedType) {
        final var service = attachedServices.get( index).getServiceAs(expectedType);
        Assertions.assertTrue(service.isPresent());

    }
}
