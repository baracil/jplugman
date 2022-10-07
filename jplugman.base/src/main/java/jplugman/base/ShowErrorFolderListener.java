package jplugman.base;

import com.google.common.collect.ImmutableSet;
import jplugman.tools.FolderListener;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class ShowErrorFolderListener implements FolderListener {

    private final @NonNull FolderListener delegate;

    @Override
    public void onFileCreated(@NonNull Path path) {
        displayError(delegate::onFileCreated,path);
    }

    @Override
    public void onFileModified(@NonNull Path path) {
        displayError(delegate::onFileModified,path);
    }

    @Override
    public void onFileDeleted(@NonNull Path path) {
        displayError(delegate::onFileDeleted,path);
    }

    @Override
    public void onStart(ImmutableSet<Path> paths) {
        displayError(delegate::onStart,paths);
    }

    private <T> void displayError(@NonNull Consumer<? super T> action, @NonNull T parameter) {
        try {
            action.accept(parameter);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw throwable;
        }
    }
}
