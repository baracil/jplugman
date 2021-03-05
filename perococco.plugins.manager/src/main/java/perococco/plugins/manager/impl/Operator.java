package Bastien Aracil.plugins.manager.impl;

import lombok.NonNull;

public interface Operator {

    /**
     * operate on the {@link PluginData} and return the new state
     */
    @NonNull PluginState operate(@NonNull PluginData pluginData);

}
