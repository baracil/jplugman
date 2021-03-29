package jplugman.api;

import jplugman.annotation.ExtensionPoint;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ExtensionData {

    /**
     * The extension point the extension implements
     */
    @NonNull ExtensionPoint extensionPoint;

    /**
     * The type of the extension point
     */
    @NonNull Class<?> extensionPointType;

    /**
     * The version of the implementation of the extension point the plugin provides
     */
    @NonNull Version version;

    public boolean provides(@NonNull Requirement<?> requirement) {
        final var sameType = requirement.getServiceType().isAssignableFrom(extensionPointType);
        final var compatibleVersion = CompatibilityChecker.isCompatible(extensionPoint, requirement.getMajorVersion());
        return sameType && compatibleVersion;
    }


    public static ExtensionData create(@NonNull ExtensionPoint extensionPoint,
                                       @NonNull Class<?> type,
                                       @NonNull Version extensionVersion) {
        final var major = extensionVersion.getMajor();
        if (!CompatibilityChecker.isCompatible(extensionPoint,major)) {
            throw new IllegalStateException("Incompatible versions");
        }
        return new ExtensionData(extensionPoint, type, extensionVersion);
    }

}
