# JPlugman

[![Build Status](https://github.com/Bastien Aracil/plugin-manager/actions/workflows/maven.yml/badge.svg)](https://github.com/Bastien Aracil/plugin-manager/actions)
[![GitHub](https://img.shields.io/github/license/Bastien Aracil/plugin-manager.svg)](https://github.com/Bastien Aracil/plugin-manager/blob/develop/LICENSE)

JPlugman is a plugin manager that can be used in modular Java projects. Its main features are :

* hot-swapping : a plugin can be added/removed/updated while the application is running.
* dependency management : service provided by a plugin can be used by the main application but also by other plugins.
* simple packaging : plugins are zip files.

This module is released under MIT license.

## Components

* **Plugin** : this is the main interface for all plugins. The plugins are loaded in their own [ModuleLayer](https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/lang/ModuleLayer.html)
* **Application** : The main application to which the plugins will be plugged to
* **PluginManager** : Used to load and unload plugins. It takes care of dependencies between plugins as well as compatibility with the application
* **Version** : semantic versioning used to determine the compatibility of a plugin with the application and other plugins (compatibility is only based on the major value of a version)

## How to use

First create your application by implementing the **Application** interface:

```java
public interface Application {

    /**
     * The application version the plugins will be plugged to. This
     * is checked against {@link Plugin#getApplicationVersion()}.
     */
    @NonNull Version getVersion();

    /**
     * @param pluginServiceType the type of the service provided by the plugin requesting the application service provider
     * @return the service provided by the application filtered for the provided <code>pluginServiceType</code>
     */
    @NonNull VersionedServiceProvider getServiceProvider(@NonNull VersionedServiceClass<?> pluginServiceType);

    /**
     * Plug a service to the application. This must
     * not affect the service provider of the application.
     * @param versionedService the service to plug
     */
    void plugService(@NonNull VersionedService versionedService);

    /**
     * Unplug a service from the application.
     * not affect the service provider of the application.
     * @param versionedService the service to unplug
     */
    void unplugService(@NonNull VersionedService versionedService);
}
```
You then create a **PluginManager** with

```java
 public static void main(String[]args) {
        final var myApplication = createMyApplication();
        final var pluginManager = PluginManager.create(myApplication);
        
        //Add some plugins
        pluginManager.addPluginBundle(Path.of("path_to_my_plugin_bundle1"));
        pluginManager.addPluginBundle(Path.of("path_to_my_plugin_bundle2"));
        
        //do things
        
        //remove a plugin
        pluginManager.removePluginBundle(Path.of("path_to_a_plugin_to_remove"));
        
        //...
  }
```

Now create a *Plugin* by implementing the **Plugin** interface :

```java
public interface Plugin {
    /**
     * @return the version of the application this plugin has been compiled for
     */
    @NonNull Version getApplicationVersion();

    /**
     * @return the set of services this plugin needs to load the service it provides
     */
    @NonNull ImmutableSet<VersionedServiceClass<?>> getRequirements();

    /**
     * @return the class and the version of the service this plugin provides
     */
    @NonNull VersionedServiceClass<?> getProvidedService();

    /**
     * @return load the service provided by this plugin
     */
    @NonNull VersionedService loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider);
}
```



## Dependencies and obsolescence

When adding or removing a bundle (which might contains several plugins) the plugin manager might call the methods **Application#plugService** and **Application#unplugService** several times.

This is because the plugin manager takes care of dependencies between plugins as well as obsolete plugins.

*TO COMPLETE*






