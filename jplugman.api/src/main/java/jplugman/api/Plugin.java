package jplugman.api;

import lombok.NonNull;

import java.util.Set;

public interface Plugin {

  @NonNull Class<?> getServiceClass();

  /**
   * load and return the service provided by this plugin as an opaque object.
   * The plugin should plug the service within this method call as no other call to it will be made
   * expect when unplugged it. </p>
   * <p>
   * If the returned object implements {@link Disposable},
   * the method {@link Disposable#dispose()} will
   * be called after the plugin has been unplugged from the application
   *
   * @return load the service provided by this plugin
   */
  @NonNull Object loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider);

    /**
     * @return the set of services this plugin needs to load the service it provides
     */
    @NonNull Set<Requirement<?>> getRequirements();

  /**
   * Callback when the plugin is unplugged, this is called before the {@link Disposable#dispose()}
   * method of the loaded service
   */
  default void unloadService() {
  }

}
