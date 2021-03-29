package Bastien Aracil.jplugman.manager;

import com.google.common.collect.ImmutableSet;
import jplugman.annotation.Extension;
import jplugman.api.ExtensionData;
import jplugman.api.InvalidPluginVersion;
import jplugman.api.Version;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
public class ProvidedServiceExtractor {

    /**
     * @param serviceType the type of the service provided by the plugin
     * @return the extension information if the service type is an extension
     * @throws InvalidPluginVersion if the {@link Extension} provided by the plugin
     * if not compatible with the {@link jplugman.annotation.ExtensionPoint} of the service it implements
     */
    public static @NonNull Optional<ExtensionData> extract(@NonNull Class<?> serviceType) {
        return new ProvidedServiceExtractor(serviceType).extract();
    }

    /**
     * The extension type (the input)
     */
    private final @NonNull Class<?> serviceType;

    /**
     * The annotation found on the service type if any
     */
    private Extension extension;

    /**
     * All the interfaces annotated with {@link jplugman.annotation.ExtensionPoint}
     */
    private ImmutableSet<ExtensionPointData> extensionPoints;

    private ExtensionData extensionData;

    private Optional<ExtensionData> extract() {
        this.extractExtensionAnnotation();
        this.findExtensionPoints();
        this.formProvidedVersions();
        return Optional.ofNullable(extensionData);
    }

    private void extractExtensionAnnotation() {
        extension = serviceType.getAnnotation(Extension.class);
    }

    private void findExtensionPoints() {
        extensionPoints = ClassHierarchy.streamHierarchy(serviceType)
                                        .map(ExtensionPointData::create)
                                        .flatMap(Optional::stream)
                                        .collect(ImmutableSet.toImmutableSet());
    }

    private void formProvidedVersions() {
        if (extension == null) {
            LOG.info("No @PluginVersion on extension {}", serviceType);
            extensionData = null;
            return;
        }

        final var extensionVersion = Version.with(extension);
        final var extensionPoint = extensionPoints.stream()
                                                  .filter(ep -> extension.point().equals(ep.type))
                                                  .findFirst()
                                                  .orElse(null);

        if (extensionPoint == null) {
            throw new InvalidPluginVersion("The Plugin '"+ serviceType +"' does not implement the extensionPoint set in @Extension : '"+ extension.point()+"'",
                                           serviceType);
        }

        if (extensionPoint.getExtensionPointVersion() != extensionVersion.getMajor()) {
            throw new InvalidPluginVersion("The plugin '"+ serviceType +"' does not provides " +
                                                   "a compatible version of '"+extensionPoint.getExtensionPointName()+"'",
                                           serviceType);
        }

        extensionData = extensionPoint.createExtensionData(extensionVersion);
    }


    @Value
    public static class ExtensionPointData {

        @NonNull Class<?> type;

        @NonNull jplugman.annotation.ExtensionPoint extensionPoint;

        public String getExtensionPointName() {
            return type.getName();
        }

        public int getExtensionPointVersion() {
            return extensionPoint.version();
        }

        @Override
        public String toString() {
            return type + "(v"+ extensionPoint +")";
        }

        public ExtensionData createExtensionData(@NonNull Version extensionVersion) {
            return ExtensionData.create(extensionPoint,type,extensionVersion);
        }

        public static @NonNull Optional<ExtensionPointData> create(@NonNull Class<?> clazz) {
            final var information = clazz.getAnnotation(jplugman.annotation.ExtensionPoint.class);
            if (clazz.isInterface() && information != null) {
                return Optional.of(new ExtensionPointData(clazz, information));
            }
            return Optional.empty();
        }

    }
}
