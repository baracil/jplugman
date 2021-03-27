package jplugman.manager.impl;

import com.google.common.collect.ImmutableSet;
import jplugman.api.InvalidPluginVersion;
import jplugman.api.PluginVersion;
import jplugman.api.Version;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
public class ProvidedServiceExtractor {

    /**
     * @param providedService the type service provided by the plugin
     * @return a map of the implemented services with the version provided by the plugin.
     */
    public static @NonNull Optional<VersionedServiceClass> extract(@NonNull Class<?> providedService) {
        return new ProvidedServiceExtractor(providedService).extract();
    }

    private final @NonNull Class<?> providedExtension;

    private PluginVersion pluginVersion;

    private ImmutableSet<ServiceClass> serviceClasses;

    private VersionedServiceClass providedService;

    private Optional<VersionedServiceClass> extract() {
        this.extractPluginVersions();
        this.findApiServices();
        this.formProvidedVersions();
        return Optional.ofNullable(providedService);
    }

    private void extractPluginVersions() {
        pluginVersion = providedExtension.getAnnotation(PluginVersion.class);
    }

    private void findApiServices() {
        serviceClasses = ClassHierarchy.streamHierarchy(providedExtension)
                                       .map(ServiceClass::create)
                                       .flatMap(Optional::stream)
                                       .collect(ImmutableSet.toImmutableSet());
    }

    private void formProvidedVersions() {
        if (pluginVersion == null) {
            LOG.warn("No @PluginVersion on extension {}", providedExtension);
            providedService = null;
            return;
        }

        final var extensionName = pluginVersion.value().getName();
        final var version = Version.with(pluginVersion.version());

        final var serviceType = serviceClasses.stream()
                                              .filter(a -> a.getServiceName().equals(extensionName))
                                              .findFirst()
                                              .orElse(null);

        if (serviceType == null) {
            throw new InvalidPluginVersion("The Plugin '"+providedExtension+"' does not implement the service set in @PluginVersion : '"+pluginVersion.value()+"'",providedExtension);
        }

        if (!serviceType.isCompatible(version.getMajor())) {
            throw new InvalidPluginVersion("The plugin '"+providedExtension+"' does not provides " +
                                                   "a compatible version of '"+serviceType.getServiceName()+"'",providedExtension);
        }

        providedService = new VersionedServiceClass(serviceType, version);
    }


}
