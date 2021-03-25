package jplugman.test.test;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import jplugman.api.VersionedService;
import jplugman.manager.PluginManager;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public abstract class TestLoadPluginBase {


    protected ImmutableList<VersionedService> attachedServices;

    @TempDir
    public Path pluginDir;

    public @NonNull Path getPluginPath(@NonNull String pluginName) {
        return pluginDir.resolve(pluginName+".zip");
    }

    @BeforeEach
    void setUp() {
        Stream.of("plugin1a","plugin1b","plugin1c","plugin2","plugin3","plugin4","plugin5", "plugin6","plugin7")
              .forEach(this::copyPluginsToPluginDir);
        final var application = new TestApplication();
        final var pluginManager = PluginManager.create(application);

        this.setUp(pluginManager);
        this.attachedServices = application.getAttachedServices();
    }

    private void copyPluginsToPluginDir(String resourceName) {
        final String pluginFilename = resourceName+".zip";
        final URL plugin = TestLoadPluginBase.class.getResource("/plugins/"+pluginFilename);
        try (var inputStream = plugin.openStream()) {
            Files.copy(inputStream,pluginDir.resolve(pluginFilename));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    protected abstract void setUp(@NonNull PluginManager pluginManager);
}
