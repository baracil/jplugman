package Bastien Aracil.plugins.manager.impl;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
@Getter
public class Evolution {

    public static @NonNull Evolution withoutChange(@NonNull PluginInfo p) {
        return new Evolution(p,p);
    }

    public static <P extends PluginInfo> @NonNull Evolution withUpdater(@NonNull P currentValue, @NonNull Function<? super P, ? extends PluginInfo> updater) {
        return new Evolution(currentValue,updater.apply(currentValue));
    }

    private final @NonNull PluginInfo oldValue;

    private final @NonNull PluginInfo newValue;


    public boolean isDifferenteState() {
        return oldValue.getState() != newValue.getState();
    }
}
