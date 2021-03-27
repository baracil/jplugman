package jplugman.manager;

import jplugman.api.Extension;
import jplugman.api.ServiceProvider;
import lombok.NonNull;

public interface MutableServiceProvider extends ServiceProvider {

    void addExtension(@NonNull Extension extension);

    void removeExtension(@NonNull Extension extension);

}
