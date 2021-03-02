package Bastien Aracil.plugins.modular.plugin2;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import Bastien Aracil.plugins.api.Plugin;
import Bastien Aracil.plugins.api.Requirement;
import Bastien Aracil.plugins.api.VersionedService;
import Bastien Aracil.plugins.api.ServiceProvider;
import Bastien Aracil.vp.VersionProvider;

import java.nio.charset.StandardCharsets;
import java.util.ServiceLoader;
import java.util.UUID;

public class Plugin2 implements Plugin {

    public static final int VERSION = 1;
    public static final UUID ID = UUID.nameUUIDFromBytes("plugin2".getBytes(StandardCharsets.UTF_8));

    @Override
    public @NonNull UUID getId() {
        return ID;
    }

    @Override
    public int getVersionCompatibility() {
        return 1;
    }

    @Override
    public @NonNull ImmutableSet<Requirement> getRequirements() {
        return ImmutableSet.of();
    }

    public @NonNull VersionedService loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        final var versionProvider = ServiceLoader.load(pluginLayer, VersionProvider.class).findFirst().get();
        return new VersionedService(new VersionGetter2(versionProvider), VERSION);
    }
}
