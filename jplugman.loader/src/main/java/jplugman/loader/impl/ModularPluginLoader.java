package jplugman.loader.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import jplugman.api.InvalidPluginLocation;
import jplugman.api.InvalidPluginStructure;
import jplugman.api.Plugin;
import jplugman.loader.PluginLoader;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Log4j2
public class ModularPluginLoader implements PluginLoader {

    @Override
    public @NonNull Result load(@NonNull Path zipFileLocation, @NonNull ClassLoader pluginClassLoader) {
        return Loader.load(zipFileLocation,pluginClassLoader);
    }

    @RequiredArgsConstructor
    private static class Loader {

        public static @NonNull Result load(@NonNull Path zipFileLocation, @NonNull ClassLoader pluginClassLoader) {
            try {
                return new Loader(zipFileLocation,pluginClassLoader).load();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        private final @NonNull Path zipFileLocation;

        private final @NonNull ClassLoader pluginClassLoader;


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
            this.removeModulesProvidedByTheBootLayer();
            this.createModuleFinder();
            this.getAllModuleNames();
            this.createPluginConfiguration();
            this.createModuleLayer();
            this.loadPlugins();
            return new Result(moduleLayer, plugins);
        }

        private void checkInputLocationIsAZipFile() {
            if (zipFileLocation.getFileName().toString().endsWith(".zip")) {
                return;
            }
            throw new InvalidPluginLocation("The plugin location must point to a zip file", zipFileLocation);
        }

        private void createTemporaryDirectory() throws IOException {
            this.temporaryDirectory = Files.createTempDirectory("pero-plugin");
        }

        private void extractZipInTemporaryDirectory() throws IOException {
            //todo put unzip in specific class
            try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFileLocation))) {
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
                                       .orElseThrow(() -> new InvalidPluginStructure(zipFileLocation));
            }
        }


        private void removeModulesProvidedByTheBootLayer() {
            final var moduleFinder = ModuleFinder.of(moduleDirectory);

            final var bootModules = ModuleLayer.boot()
                                               .modules()
                                               .stream()
                                               .map(Module::getName)
                                               .collect(Collectors.toSet());

            final Predicate<ModuleReference> moduleInBootLayer = mr -> bootModules.contains(mr.descriptor().name());

            moduleFinder.findAll()
                        .stream()
                        .filter(moduleInBootLayer)
                        .map(ModuleReference::location)
                        .flatMap(Optional::stream)
                        .map(Path::of)
                        .forEach(this::deleteFile);
        }

        private void deleteFile(@NonNull Path path) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                LOG.warn("Could not remove file {} : {}", path, e.getMessage());
                LOG.debug(e);
                throw new UncheckedIOException(e);
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


        }


        private void createPluginConfiguration() {
            this.pluginsConfiguration = ModuleLayer.boot()
                                                   .configuration()
                                                   .resolveAndBind(moduleFinder,
                                                                   ModuleFinder.of(),
                                                                   pluginNames);
        }

        private void createModuleLayer() {
            this.moduleLayer = ModuleLayer.boot().defineModulesWithManyLoaders(pluginsConfiguration, pluginClassLoader);
        }


        private void loadPlugins() {
            this.plugins = ServiceLoader.load(moduleLayer, Plugin.class)
                                        .stream()
                                        .map(ServiceLoader.Provider::get)
                                        .collect(ImmutableList.toImmutableList());
        }


    }
}
