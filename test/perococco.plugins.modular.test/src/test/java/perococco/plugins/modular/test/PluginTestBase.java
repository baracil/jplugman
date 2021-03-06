package Bastien Aracil.plugins.modular.test;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import Bastien Aracil.plugins.api.VersionedService;
import Bastien Aracil.plugins.manager.PluginManager;

import java.nio.file.Path;

public abstract class PluginTestBase {

    public @NonNull Path getPluginPath(@NonNull String pluginName) {
        return Path.of("..").resolve("build").resolve(pluginName+".zip");
    }

    protected ImmutableList<VersionedService> attachedServices;

    @BeforeEach
    void setUp() {
        final var application = new TestApplication();
        final var pluginManager = PluginManager.create(application);

        this.setUp(pluginManager);
        this.attachedServices = application.getAttachedServices();
    }
    protected abstract void setUp(@NonNull PluginManager pluginManager);
}
