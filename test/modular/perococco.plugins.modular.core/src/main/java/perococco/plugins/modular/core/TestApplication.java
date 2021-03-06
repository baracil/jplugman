package Bastien Aracil.plugins.modular.core;

import lombok.NonNull;
import Bastien Aracil.plugins.api.Version;
import Bastien Aracil.plugins.api.VersionedService;
import Bastien Aracil.plugins.api.VersionedServiceProvider;
import Bastien Aracil.plugins.api.VersionedServiceType;
import Bastien Aracil.plugins.manager.Application;
import Bastien Aracil.plugins.manager.PluginManager;

import java.nio.file.Path;

public class TestApplication implements Application {

    private final VersionedServiceProvider serviceProvider = VersionedServiceProvider.of();

    @Override
    public @NonNull Version getVersion() {
        return Version.with(1,0,0);
    }

    @Override
    public @NonNull VersionedServiceProvider getServiceProvider(@NonNull VersionedServiceType<?> versionedServiceType) {
        return serviceProvider;
    }

    @Override
    public void plugService(@NonNull VersionedService versionedService) {
        System.out.println("Attach service");
        versionedService.getServiceAs(VersionGetter.class)
                        .map(VersionGetter::getFullVersion)
                        .ifPresent(System.out::println);
    }

    @Override
    public void unplugService(@NonNull VersionedService versionedService) {
        System.out.println("Detach service : "+versionedService);
    }

    public static void main(String[] args) {
        final Application application = new TestApplication();
        final var pluginManager = PluginManager.create(application);

        pluginManager.addPluginBundle(Path.of("./test/build/plugin2.zip"));
        pluginManager.addPluginBundle(Path.of("./test/build/plugin1.zip"));
    }
}
