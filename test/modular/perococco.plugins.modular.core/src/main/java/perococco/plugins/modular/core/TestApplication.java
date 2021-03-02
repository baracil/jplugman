package Bastien Aracil.plugins.modular.core;

import lombok.NonNull;
import Bastien Aracil.plugins.api.MutableVersionedServiceProvider;
import Bastien Aracil.plugins.api.VersionedService;
import Bastien Aracil.plugins.api.VersionedServiceProvider;
import Bastien Aracil.plugins.manager.Application;
import Bastien Aracil.plugins.manager.PluginManager;

import java.nio.file.Path;

public class TestApplication implements Application {

    private final MutableVersionedServiceProvider serviceProvider = new MutableVersionedServiceProvider();

    @Override
    public int getVersion() {
        return 1;
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

    public static void main(String[] args) {
        final Application application = new TestApplication();
        final var pluginManager = PluginManager.create(application);

        pluginManager.addPlugin(Path.of("./test/build/plugin2.zip"));
        pluginManager.addPlugin(Path.of("./test/build/plugin1.zip"));
    }
}
