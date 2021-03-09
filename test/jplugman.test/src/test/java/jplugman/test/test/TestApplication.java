package jplugman.test.test;

import com.google.common.collect.ImmutableList;
import jplugman.api.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Synchronized;
import jplugman.api.*;
import jplugman.manager.Application;

public class TestApplication implements Application {

    private final VersionedServiceProvider serviceProvider = ImmutableVersionedServiceProvider.of();

    @Getter
    private ImmutableList<VersionedService> attachedServices = ImmutableList.of();

    @Override
    public @NonNull Version getVersion() {
        return Version.with(1,0,0);
    }

    @Override
    public @NonNull VersionedServiceProvider getServiceProvider(@NonNull VersionedServiceClass<?> versionedServiceClass) {
        return serviceProvider;
    }

    @Override
    @Synchronized
    public void plugService(@NonNull VersionedService versionedService) {
        attachedServices = ImmutableList.<VersionedService>builder()
                                        .addAll(this.attachedServices)
                                        .add(versionedService)
                                        .build();
    }

    @Override
    @Synchronized
    public void unplugService(@NonNull VersionedService versionedService) {
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
