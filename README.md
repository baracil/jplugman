# JPlugman

[![Build Status](https://github.com/baracil/jplugman/actions/workflows/maven.yml/badge.svg)](https://github.com/baracil/jplugman/actions)
[![GitHub](https://img.shields.io/github/license/baracil/jplugman.svg)](https://github.com/baracil/jplugman/blob/develop/LICENSE)

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

*TODO*

## Dependencies and obsolescence

When adding or removing a bundle (which might contain several plugins) the plugin manager might call the methods **Application#plugService** and **Application#unplugService** several times.

This is because the plugin manager takes care of dependencies between plugins as well as obsolete plugins.

*TO COMPLETE*






