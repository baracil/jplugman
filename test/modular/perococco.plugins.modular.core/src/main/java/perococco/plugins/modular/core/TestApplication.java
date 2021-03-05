package Bastien Aracil.plugins.modular.core;

import lombok.NonNull;
import Bastien Aracil.plugins.api.MutableVersionedServiceProvider;
import Bastien Aracil.plugins.api.Version;
import Bastien Aracil.plugins.api.VersionedService;
import Bastien Aracil.plugins.api.VersionedServiceProvider;
import Bastien Aracil.plugins.manager.Application;
import Bastien Aracil.plugins.manager.PluginManager;

import java.nio.file.Path;

public class TestApplication implements Application {

    private final MutableVersionedServiceProvider serviceProvider = new MutableVersionedServiceProvider();

    @Override
    public Version getVersion() {
        return Version.with(1,0,0);
    }

    @Override
    public @NonNull VersionedServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    @Override
    public void attachService(@NonNull VersionedService versionedService) {
        serviceProvider.addVersionedService(versionedService);

        System.out.println("Attach service");
        versionedService.getServiceAs(VersionGetter.class)
                        .map(VersionGetter::getFullVersion)
                        .ifPresent(System.out::println);
    }

    @Override
    public void detachService(@NonNull VersionedService versionedService) {
        serviceProvider.removeVersionedService(versionedService);
    }

    public static void main(String[] args) {
        final Application application = new TestApplication();
        final var pluginManager = PluginManager.create(application);

        pluginManager.addPluginBundle(Path.of("./test/build/plugin2.zip"));
        pluginManager.addPluginBundle(Path.of("./test/build/plugin1.zip"));
    }
}
