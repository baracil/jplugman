# JPlugman

[![Build Status](https://github.com/Bastien Aracil/plugin-manager/actions/workflows/maven.yml/badge.svg)](https://github.com/Bastien Aracil/plugin-manager/actions)
[![GitHub](https://img.shields.io/github/license/Bastien Aracil/plugin-manager.svg)](https://github.com/Bastien Aracil/plugin-manager/blob/develop/LICENSE)

JPlugman is a plugin manager that can be used in modular Java projects. Its main features are :

* hot-swapping : a plugin can be added/removed/updated while the application is running.
* dependency management : requirement provided by a plugin can be used by the main application but also by other plugins.
* simple packaging : plugins are zip files.

This module is released under MIT license.

## Components

* **Plugin** : this is the main interface for all plugins. The plugins are loaded in their own [ModuleLayer](https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/lang/ModuleLayer.html)
* **Application** : The main application to which the plugins will be plugged to
* **PluginManager** : Used to load and unload plugins. It takes care of dependencies between plugins as well as compatibility with the application

## How to use

First create your application by implementing the **Application** interface:

```java
public interface Application {

    /**
     * @param pluginServiceType the type of the service provided by the plugin requesting the application service provider
     * @return the service provided by the application filtered for the provided <code>pluginServiceType</code>
     */
    @NonNull ServiceProvider getServiceProvider(@NonNull Class<?> pluginServiceType);

    /**
     * Plug an extension to the application. This must
     * not affect the service provider of the application.
     * @param extension the service to plug
     */
    void plugExtension(@NonNull Extension extension);

    /**
     * Unplug an extension from the application. This must
     * not affect the service provider of the application.
     * @param extension the service to unplug
     */
    void unplugExtension(@NonNull Extension extension);
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
public interface Plugin<T> {
    /**
     * @return the set of services this plugin needs to load the service it provides
     */
    @NonNull ImmutableSet<Requirement<?>> getRequirements();

    /**
     * @return the class of the extension this plugin provides
     */
    @NonNull Class<T> getExtensionClass();

    /**
     * @return load the extension provided by this plugin
     */
    @NonNull T loadExtension(@NonNull ModuleLayer pluginLayer, @NonNull ServiceRegistry serviceRegistry);
}
```



## Dependencies and obsolescence

When adding or removing a bundle (which might contain several plugins) the plugin manager might call the methods **Application#plugService** and **Application#unplugService** several times.

This is because the plugin manager takes care of dependencies between plugins as well as obsolete plugins.

*TO COMPLETE*






