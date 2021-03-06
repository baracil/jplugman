package Bastien Aracil.plugins.modular.test;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import Bastien Aracil.plugins.api.Version;
import Bastien Aracil.plugins.api.VersionedService;
import Bastien Aracil.plugins.api.VersionedServiceType;
import Bastien Aracil.plugins.manager.PluginManager;
import Bastien Aracil.plugins.modular.core.DummyService;
import Bastien Aracil.plugins.modular.core.VersionGetter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

public class LoadPlugin3Then1bThen4Then2 extends PluginTestBase {

    @Override
    protected void setUp(@NonNull PluginManager pluginManager) {
        pluginManager.addPluginBundle(getPluginPath("plugin3"));
        pluginManager.addPluginBundle(getPluginPath("plugin1b"));
        pluginManager.addPluginBundle(getPluginPath("plugin4"));
        pluginManager.addPluginBundle(getPluginPath("plugin2"));
    }


    public static Stream<Arguments> versions() {
        return Stream.of(
                Arguments.of(0,Version.with(1,1,0)),
                Arguments.of(1,Version.with(2,0,0)),
                Arguments.of(2,Version.with(1,1,0))
        );
    }
    public static Stream<Arguments> types() {
        return Stream.of(
                Arguments.of(0,VersionGetter.class),
                Arguments.of(1,VersionGetter.class),
                Arguments.of(2,DummyService.class)
        );
    }


    @Test
    public void shouldHaveFourServicesAttached() {
        Assertions.assertEquals(3,attachedServices.size());
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
        Assertions.assertTrue(service.isPresent(),"expected="+expectedType+"  actual="+attachedServices.get(index).getService());
    }

    public void shouldHaveAllServices(@NonNull ImmutableList<VersionedServiceType<?>> expectedServices) {
        Assertions.assertEquals(expectedServices.size(), attachedServices.size());

        final Set<VersionedService> actualServices = new HashSet<>(attachedServices);

        for (VersionedServiceType<?> expectedService : expectedServices) {
            final Iterator<VersionedService> iter = actualServices.iterator();
            while (iter.hasNext()) {
                var actualService = iter.next();
                if (actualService.getVersion().equals(expectedService.getVersion()) &&
                        expectedService.getServiceType().isInstance(actualService.getService())) {
                    iter.remove();
                    break;
                }
            }
        }

        Assertions.assertTrue(actualServices.isEmpty());
    }

}
