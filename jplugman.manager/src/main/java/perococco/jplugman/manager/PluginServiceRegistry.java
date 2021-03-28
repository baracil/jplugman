package Bastien Aracil.jplugman.manager;

import jplugman.api.CompatibilityChecker;
import jplugman.api.PluginService;
import jplugman.api.Requirement;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

public class PluginServiceRegistry implements MutableVersionedServiceProvider {

    private final Set<PluginService<?>> extensions = new HashSet<>();

    @Override
    public @NonNull <T> Stream<T> findService(@NonNull Requirement<T> requirement) {
        return extensions.stream()
                         .map(castIfCompatible(requirement))
                         .flatMap(Optional::stream);
    }

    public <T> Function<PluginService<?>, Optional<T>> castIfCompatible(@NonNull Requirement<T> requirement) {
        return ps -> {
            final var data = ps.getExtensionData().orElse(null);
            if (data == null) {
                return Optional.empty();
            }
            if (!CompatibilityChecker.isCompatible(data.getExtensionPoint(),requirement.getMajorVersion())) {
                return Optional.empty();
            }
            return ps.getServiceAs(requirement.getServiceType());
        };
    }

    public void addPluginService(@NonNull PluginService<?> pluginService) {
        this.extensions.add(pluginService);
    }


    public void removePluginService(@NonNull PluginService<?> pluginService) {
        this.extensions.remove(pluginService);
    }
}
