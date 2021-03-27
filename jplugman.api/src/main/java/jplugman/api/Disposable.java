package jplugman.api;

/**
 * If the instance of the extension implements this interface,
 * the method {@link #dispose()} must be called by the plugin manager
 * when the extension is unloaded (i.e. after the extension has been
 * unplugged from the application by calling {@link Application#unplugService(PluginService)})
 */
public interface Disposable {

    void dispose();
}
