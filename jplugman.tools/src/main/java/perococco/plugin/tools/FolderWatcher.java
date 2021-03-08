package Bastien Aracil.plugin.tools;

import lombok.NonNull;
import Bastien Aracil.plugin.tools.impl.PeroFolderWatcher;

import java.nio.file.Path;
import java.util.ServiceLoader;

public interface FolderWatcher {

    static @NonNull FolderWatcher create(@NonNull Path watchedFolder) {
        return ServiceLoader.load(FolderWatcher.Factory.class)
                            .findFirst()
                            .orElseGet(() -> PeroFolderWatcher::new)
                            .create(watchedFolder);
    }

    void start();

    void stop() throws InterruptedException;

    boolean isRunning();

    @NonNull Subscription addListener(@NonNull FolderListener listener);


    interface Factory {

        @NonNull FolderWatcher create(@NonNull Path watcherFolder);

    }

}
