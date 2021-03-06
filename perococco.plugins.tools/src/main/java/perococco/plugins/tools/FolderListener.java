package Bastien Aracil.plugins.tools;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

import java.nio.file.Path;
import java.util.Set;

public interface FolderListener {

    void onFileCreated(@NonNull Path path);

    void onFileModified(@NonNull Path path);

    void onFileDeleted(@NonNull Path path);

    void onStart(ImmutableSet<Path> paths);
}
