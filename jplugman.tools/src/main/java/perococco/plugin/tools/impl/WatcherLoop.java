package Bastien Aracil.plugin.tools.impl;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugin.tools.FolderListener;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class WatcherLoop implements Runnable {

    private final @NonNull Path watchedFolder;

    private final @NonNull FolderListener folderListener;

    private WatchService watchService;

    private WatchKey watchKey;

    @Override
    public void run() {
        try {
            doRun();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void doRun() throws IOException {
        this.beforeStarting();
        try {
            this.loop();
        } finally {
            this.afterEnding();
        }

    }

    private void beforeStarting() throws IOException {
        folderListener.onStart(Files.list(watchedFolder).collect(ImmutableSet.toImmutableSet()));
        watchService = FileSystems.getDefault().newWatchService();
        watchKey = watchedFolder.register(watchService,
                                          StandardWatchEventKinds.ENTRY_CREATE,
                                          StandardWatchEventKinds.ENTRY_MODIFY,
                                          StandardWatchEventKinds.ENTRY_DELETE);
    }

    private void loop() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                var key = watchService.take();

                // process events
                for (WatchEvent<?> event : key.pollEvents()) {
                    final var path = watchedFolder.resolve((Path) event.context());
                    final Consumer<Path> action = switch (event.kind().name()) {
                        case "ENTRY_CREATE" -> folderListener::onFileCreated;
                        case "ENTRY_MODIFY" -> folderListener::onFileModified;
                        case "ENTRY_DELETE" -> folderListener::onFileDeleted;
                        default -> p -> {};
                    };
                    action.accept(path);
                }

                // reset the key
                boolean valid = key.reset();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void afterEnding() throws IOException {
        watchKey.cancel();
        watchService.close();
    }
}