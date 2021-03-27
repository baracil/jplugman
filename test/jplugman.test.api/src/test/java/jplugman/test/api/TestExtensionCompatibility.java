package jplugman.test.api;

import jplugman.annotation.ExtensionPoint;
import jplugman.api.CompatibilityChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TestExtensionCompatibility {

    @ExtensionPoint(version = 4, retroVersions = {2,3})
    private static class E4_2_3 {}
    @ExtensionPoint(version = 2, retroVersions = {1})
    private static class E2_1 {}
    @ExtensionPoint(version = 10, retroVersions = {3,1})
    private static class E10_3_1 {}

    public static Stream<Arguments> compatibleVersions() {
        return Stream.of(
                Arguments.of(E4_2_3.class, 4),
                Arguments.of(E4_2_3.class, 2),
                Arguments.of(E4_2_3.class, 3),
                Arguments.of(E2_1.class, 2),
                Arguments.of(E2_1.class, 1),
                Arguments.of(E10_3_1.class, 10),
                Arguments.of(E10_3_1.class, 3),
                Arguments.of(E10_3_1.class, 1)
        );
    }
    public static Stream<Arguments> incompatibleVersions() {
        return Stream.of(
                Arguments.of(E4_2_3.class, 1),
                Arguments.of(E4_2_3.class, 10),
                Arguments.of(E4_2_3.class, 5),
                Arguments.of(E4_2_3.class, -1),
                Arguments.of(E4_2_3.class, 0),
                Arguments.of(E4_2_3.class, -4),
                Arguments.of(E2_1.class, 0),
                Arguments.of(E2_1.class, 10),
                Arguments.of(E2_1.class, 11),
                Arguments.of(E10_3_1.class, 9),
                Arguments.of(E10_3_1.class, 8),
                Arguments.of(E10_3_1.class, 7),
                Arguments.of(E10_3_1.class, 6),
                Arguments.of(E10_3_1.class, 5),
                Arguments.of(E10_3_1.class, 4),
                Arguments.of(E10_3_1.class, 2),
                Arguments.of(E10_3_1.class, 0)
        );
    }


    @ParameterizedTest
    @MethodSource("compatibleVersions")
    public void shouldBeCompatible(Class<?> extensionPoint, int majorVersion) {
        final var compatible = CompatibilityChecker.isCompatible(extensionPoint.getAnnotation(ExtensionPoint.class),majorVersion);
        Assertions.assertTrue(compatible);
    }

    @ParameterizedTest
    @MethodSource("incompatibleVersions")
    public void shouldBeIncompatible(Class<?> extensionPoint, int majorVersion) {
        final var compatible = CompatibilityChecker.isCompatible(extensionPoint.getAnnotation(ExtensionPoint.class),majorVersion);
        Assertions.assertFalse(compatible);
    }
}
