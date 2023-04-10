package baracil.jplugman.manager;

import jplugman.api.Requirement;
import jplugman.api.ServiceProvider;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PluginSpecificServiceProvider implements ServiceProvider {

  private final @NonNull Map<Class<?>, Requirement<?>> pluginRequirements;
  private final @NonNull VersionedServiceProvider versionedServiceProvider;

  @Override
  public <T> @NonNull List<T> getAllServices(@NonNull Class<T> serviceClass) {
    return getRequirementFromServiceType(serviceClass)
        .stream()
        .flatMap(versionedServiceProvider::findService)
        .collect(Collectors.toList());
  }

  @SuppressWarnings("unchecked")
  private <T> @NonNull Optional<Requirement<T>> getRequirementFromServiceType(@NonNull Class<T> serviceType) {
    final var requirement = pluginRequirements.get(serviceType);
    return Optional.ofNullable((Requirement<T>) requirement);
  }



  /**
   * @param pluginRequirements       the requirements of the plugins
   * @param versionedServiceProvider the provider of services
   * @return a service provider that will provide only the services required by the plugin
   */
  public static @NonNull ServiceProvider create(
      @NonNull Set<Requirement<?>> pluginRequirements,
      @NonNull VersionedServiceProvider versionedServiceProvider) {
    return new PluginSpecificServiceProvider(
        pluginRequirements.stream()
            .collect(Collectors.toMap(Requirement::getServiceType, Function.identity())),
        versionedServiceProvider
    );
  }



}
