package Bastien Aracil.plugins.manager.impl;

public enum PluginState {
    /**
     * The plugin has been loaded but not verification has been made yet (like compatibility
     * with the application version or the availability of the requirements
     */
    LOADED,
    /**
     * The plugin is not compatible with the application (invalid version)
     */
    REJECTED,
    /**
     * The plugin is compatible with the application (valid session)
     */
    ACCEPTED,
    /**
     * The plugin has all its requirements fulfill
     */
    RESOLVED,
    /**
     * The attempt to plug the plugin failed
     */
    FAILED,
    /**
     * The service provided by the plugin has been loaded and plugged into the application
     */
    PLUGGED,
    ;

}
