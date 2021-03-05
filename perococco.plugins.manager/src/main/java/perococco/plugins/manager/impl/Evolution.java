package Bastien Aracil.plugins.manager.impl;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
@Getter
public class Evolution {

    public static @NonNull Evolution withoutChange(@NonNull PluginData p) {
        return new Evolution(p,p);
    }

    public static <P extends PluginData> @NonNull Evolution withUpdater(@NonNull P currentValue, @NonNull Function<? super P, ? extends PluginData> updater) {
        return new Evolution(currentValue,updater.apply(currentValue));
    }

    private final @NonNull PluginData oldValue;

    private final @NonNull PluginData newValue;


    public boolean isDifferenteState() {
        return oldValue.getState() != newValue.getState();
    }
}
