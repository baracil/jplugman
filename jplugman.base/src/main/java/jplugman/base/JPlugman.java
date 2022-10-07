package jplugman.base;

import jplugman.api.Application;
import jplugman.manager.PluginManager;
import jplugman.tools.FolderListener;
import jplugman.tools.FolderWatcher;
import jplugman.tools.Subscription;
import lombok.NonNull;

import java.nio.file.Path;

public class JPlugman {

    private final @NonNull FolderWatcher folderWatcher;

    public JPlugman(@NonNull Application application, @NonNull Path pluginDir) {
        this.folderWatcher = FolderWatcher.create(pluginDir);
        @NonNull PluginManager pluginManager = PluginManager.create(application);
        this.folderWatcher.addListener(new FolderListenerForManager(pluginManager));
    }

    public void start() {
        folderWatcher.start();
    }

    public void stop() throws InterruptedException {
        folderWatcher.stop();
    }

    public boolean isRunning() {
        return folderWatcher.isRunning();
    }

    public @NonNull Subscription addListener(@NonNull FolderListener listener) {
        return this.folderWatcher.addListener(listener);
    }
}
