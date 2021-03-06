package Bastien Aracil.plugins.modular.test;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.Synchronized;
import Bastien Aracil.plugins.api.Version;
import Bastien Aracil.plugins.api.VersionedService;
import Bastien Aracil.plugins.api.VersionedServiceProvider;
import Bastien Aracil.plugins.api.VersionedServiceType;
import Bastien Aracil.plugins.manager.Application;

public class TestApplication implements Application {

    private final VersionedServiceProvider serviceProvider = VersionedServiceProvider.of();

    @Getter
    private ImmutableList<VersionedService> attachedServices = ImmutableList.of();

    @Override
    public @NonNull Version getVersion() {
        return Version.with(1,0,0);
    }

    @Override
    public @NonNull VersionedServiceProvider getServiceProvider(@NonNull VersionedServiceType<?> versionedServiceType) {
        return serviceProvider;
    }

    @Override
    @Synchronized
    public void plugService(@NonNull VersionedService versionedService) {
        System.out.println("Plug   "+versionedService);
        attachedServices = ImmutableList.<VersionedService>builder()
                                        .addAll(this.attachedServices)
                                        .add(versionedService)
                                        .build();
    }

    @Override
    @Synchronized
    public void unplugService(@NonNull VersionedService versionedService) {
        System.out.println("Unplug "+versionedService);
        var builder = ImmutableList.<VersionedService>builder();
        boolean removed = false;
        for (VersionedService attachedService : attachedServices) {
            if (removed || attachedService != versionedService) {
                builder.add(attachedService);
            } else {
                removed = true;
            }
        }
        this.attachedServices = builder.build();
    }

}
