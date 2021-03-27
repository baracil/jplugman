package jplugman.api;

import jplugman.annotation.ExtensionPoint;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.stream.IntStream;

@RequiredArgsConstructor
public class CompatibilityChecker {

    public static boolean isCompatible(@NonNull ExtensionPoint extensionPoint, int majorVersion) {
        return new CompatibilityChecker(extensionPoint, majorVersion).isCompatible();
    }

    private final @NonNull ExtensionPoint extensionPoint;
    private final int majorVersion;

    private boolean isCompatible() {
        return IntStream.concat(
                IntStream.of(extensionPoint.version()),
                IntStream.of(extensionPoint.retroVersions()))
                        .anyMatch(i -> i == majorVersion);
    }
}
