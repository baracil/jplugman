package jplugman.base;

import jplugman.manager.PluginManager;
import jplugman.tools.FolderListener;
import lombok.NonNull;
import lombok.experimental.Delegate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FolderListenerForManager implements FolderListener {

    @Delegate
    private final @NonNull FolderListener delegate;

    public FolderListenerForManager(@NonNull PluginManager pluginManager) {
        final var listener = new PluginFolderListener(pluginManager);
        final MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        this.delegate = new ShowErrorFolderListener(new FilteringFolderListener(algorithm, listener));
    }
}
