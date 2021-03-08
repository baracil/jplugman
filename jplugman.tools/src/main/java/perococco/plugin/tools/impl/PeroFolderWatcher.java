package Bastien Aracil.plugin.tools.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import Bastien Aracil.plugin.tools.FolderListener;
import Bastien Aracil.plugin.tools.FolderWatcher;
import Bastien Aracil.plugin.tools.Subscription;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Log4j2
public class PeroFolderWatcher implements FolderWatcher {

    private final @NonNull Path watchedFolder;

    private ImmutableList<FolderListener> listeners = ImmutableList.of();

    public PeroFolderWatcher(@NonNull Path watchedFolder) {
        if (!Files.isDirectory(watchedFolder)) {
            throw new IllegalArgumentException("The provided path is not a directory : '" + watchedFolder + "'");
        }
        this.watchedFolder = watchedFolder;
    }

    @Override
    @Synchronized
    public @NonNull Subscription addListener(@NonNull FolderListener listener) {
        this.listeners = ListTools.addFirst(this.listeners, listener);
        return () -> removeListener(listener);
    }

    @Synchronized
    private void removeListener(@NonNull FolderListener listener) {
        this.listeners = ListTools.removeFirst(this.listeners, listener);
    }

    private Thread loopThread = null;

    @Override
    @Synchronized
    public void start() {
        if (loopThread != null) {
            return;
        }
        loopThread = new Thread(new WatcherLoop(watchedFolder, new Listener()), "Watcher Loop");
        loopThread.setDaemon(true);
        loopThread.start();
    }

    @Override
    @Synchronized
    public void stop() throws InterruptedException {
        if (loopThread == null) {
            return;
        }
        loopThread.interrupt();
        loopThread.join();
        loopThread = null;
    }

    @Override
    public boolean isRunning() {
        return loopThread != null;
    }


    private class Listener implements FolderListener {
        @Override
        public void onFileCreated(@NonNull Path path) {
            warnListener(FolderListener::onFileCreated, path);
        }

        @Override
        public void onFileModified(@NonNull Path path) {
            warnListener(FolderListener::onFileModified, path);
        }

        @Override
        public void onFileDeleted(@NonNull Path path) {
            warnListener(FolderListener::onFileDeleted, path);
        }

        @Override
        public void onStart(ImmutableSet<Path> paths) {
            warnListener(FolderListener::onStart, paths);
        }

        private <T> void warnListener(@NonNull BiConsumer<FolderListener,T> action,@NonNull T parameter) {
            final Consumer<FolderListener> safeAction = listener -> {
                try {
                    action.accept(listener, parameter);
                } catch (Throwable t) {
                    LOG.warn("Error while warning listener '"+listener+"'");
                }
            };
            listeners.forEach(safeAction);
        }

    }
}
