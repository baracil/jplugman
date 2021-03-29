package jplugman.test.test;

import com.google.common.collect.ImmutableList;
import jplugman.api.PluginService;
import jplugman.manager.PluginManager;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class TestLoadPluginBase {


    protected ImmutableList<PluginService> attachedVersionedServiceData;

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
        this.attachedVersionedServiceData = application.getAttachedPluginServices();
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


    protected void checkNbServices(ExpectedServices services) {
        Assertions.assertEquals(services.nbServices(), attachedVersionedServiceData.size());
    }

    protected void checkServices(ExpectedServices services) {
        final var l = new ArrayList<>(attachedVersionedServiceData);
        final List<ServiceTypeWithVersion> notRemoved = new ArrayList<>();
        for (ServiceTypeWithVersion version : services) {
            if (!l.removeIf(version::matches)) {
                notRemoved.add(version);
            }
        }

        Assertions.assertTrue(l.isEmpty(),"Some service were not expected "+l);
        Assertions.assertTrue(notRemoved.isEmpty(),"Some expected version could not be found "+notRemoved);


    }

}
