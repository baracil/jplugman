package jplugman.base;

import com.google.common.collect.ImmutableSet;
import jplugman.manager.PluginManager;
import jplugman.tools.FolderListener;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;

@RequiredArgsConstructor
@Slf4j
public class PluginFolderListener implements FolderListener {


    private final @NonNull PluginManager pluginManager;

    @Override
    public void onFileCreated(@NonNull Path path) {
        this.pluginManager.addPluginBundle(path);
    }

    @Override
    public void onFileModified(@NonNull Path path) {
        this.pluginManager.removePluginBundle(path);
        this.pluginManager.addPluginBundle(path);
    }

    @Override
    public void onFileDeleted(@NonNull Path path) {
        this.pluginManager.removePluginBundle(path);
    }

    @Override
    public void onStart(ImmutableSet<Path> paths) {
        for (Path path : paths) {
            try {
                pluginManager.addPluginBundle(path);
            } catch (Throwable t) {
                LOG.warn("Could not load plugin '{}' : {}", path.getFileName(), t.getMessage());
            }
        }
    }
}
