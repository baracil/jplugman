package Bastien Aracil.plugins.modular;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.api.InvalidPluginLocation;
import Bastien Aracil.plugins.api.InvalidPluginStructure;
import Bastien Aracil.plugins.api.Plugin;
import Bastien Aracil.plugins.api.PluginLoader;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ServiceLoader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ModularPluginLoader implements PluginLoader {

    public static final String FILE_SCHEME = "file";

    @Override
    public @NonNull Result load(@NonNull Path location) {
        return Loader.load(location);
    }

    @RequiredArgsConstructor
    private static class Loader {

        public static @NonNull Result load(@NonNull Path location) {
            try {
                return new Loader(location).load();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        private final @NonNull Path location;

        private Path temporaryDirectory;

        private Path moduleDirectory;

        private ModuleFinder moduleFinder;

        private ImmutableSet<String> pluginNames;

        private Configuration pluginsConfiguration;

        private ModuleLayer moduleLayer;

        private ImmutableList<Plugin> plugins;

        private @NonNull Result load() throws IOException {
            this.checkInputLocationIsAZipFile();
            this.createTemporaryDirectory();
            this.extractZipInTemporaryDirectory();
            this.guessModuleDirectory();
            this.createModuleFinder();
            this.getAllModuleNames();
            this.createPluginConfiguration();
            this.createModuleLayer();
            this.loadPlugins();
            return new Result(moduleLayer, plugins);
        }


        private void checkInputLocationIsAZipFile() {
            if (location.getFileName().toString().endsWith(".zip")) {
                return;
            }
            throw new InvalidPluginLocation("The plugin location must point to a zip file", location);
        }

        private void createTemporaryDirectory() throws IOException {
            this.temporaryDirectory = Files.createTempDirectory("pero-plugin");
        }

        private void extractZipInTemporaryDirectory() throws IOException {
            //todo put unzip in specific class
            try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(location))) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    final var outputFile = temporaryDirectory.resolve(entry.getName());
                    if (entry.isDirectory()) {
                        Files.createDirectory(outputFile);
                    } else {
                        final var parent = outputFile.getParent();
                        if (!Files.exists(parent)) {
                            Files.createDirectories(parent);
                        }
                        Files.copy(zis, outputFile);
                    }
                    zis.closeEntry();
                }
            }
        }

        private void guessModuleDirectory() throws IOException {
            if (Files.list(temporaryDirectory)
                     .anyMatch(p -> p.getFileName().toString().endsWith(".jar"))) {
                moduleDirectory = temporaryDirectory;
            } else {
                moduleDirectory = Files.list(temporaryDirectory)
                                       .filter(Files::isDirectory)
                                       .findFirst()
                                       .orElseThrow(() -> new InvalidPluginStructure(location));
            }
        }


        private void createModuleFinder() {
            this.moduleFinder = ModuleFinder.of(moduleDirectory);
        }

        private void getAllModuleNames() {
            this.pluginNames = moduleFinder.findAll()
                                           .stream()
                                           .map(ModuleReference::descriptor)
                                           .map(ModuleDescriptor::name)
                                           .collect(ImmutableSet.toImmutableSet());
//            System.out.println("CURRENT LAYER");
//            getClass().getModule().getLayer().modules().forEach(m -> System.out.println(m.getName()));
//
//            System.out.println("PLUGIN LAYER");
//            this.pluginNames.forEach(m -> System.out.println(m));
//            System.out.println("-------");
        }


        private void createPluginConfiguration() {
            this.pluginsConfiguration = ModuleLayer.boot()
                                                   .configuration()
                                                   .resolveAndBind(moduleFinder,
                                                                   ModuleFinder.of(),
                                                                   pluginNames);
        }

        private void createModuleLayer() {
            this.moduleLayer = ModuleLayer.boot().defineModulesWithManyLoaders(pluginsConfiguration, null);
        }


        private void loadPlugins() {
            this.plugins = ServiceLoader.load(moduleLayer, Plugin.class)
                                        .stream()
                                        .map(ServiceLoader.Provider::get)
                                        .collect(ImmutableList.toImmutableList());
        }


    }
}
