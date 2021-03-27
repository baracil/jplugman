package jplugman.test.test;

import com.google.common.collect.ImmutableList;
import jplugman.api.Application;
import jplugman.api.Extension;
import jplugman.api.ServiceProvider;
import lombok.Getter;
import lombok.NonNull;
import lombok.Synchronized;

public class TestApplication implements Application {

    @Getter
    private ImmutableList<Extension> attachedExtensions = ImmutableList.of();

    @Override
    public @NonNull ServiceProvider getServiceProvider(@NonNull Class<?> versionedServiceClass) {
        return ServiceProvider.EMPTY;
    }

    @Override
    @Synchronized
    public void plugExtension(@NonNull Extension extension) {
        attachedExtensions = ImmutableList.<Extension>builder()
                                        .addAll(this.attachedExtensions)
                                        .add(extension)
                                        .build();
    }

    @Override
    @Synchronized
    public void unplugExtension(@NonNull Extension versionedExtension) {
        var builder = ImmutableList.<Extension>builder();
        boolean removed = false;
        for (Extension attachedExtension : attachedExtensions) {
            if (removed || attachedExtension != versionedExtension) {
                builder.add(attachedExtension);
            } else {
                removed = true;
            }
        }
        this.attachedExtensions = builder.build();
    }

}
