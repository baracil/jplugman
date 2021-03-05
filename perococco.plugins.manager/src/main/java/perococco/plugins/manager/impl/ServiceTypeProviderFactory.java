package Bastien Aracil.plugins.manager.impl;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ServiceTypeProviderFactory {

    public static @NonNull ServiceTypeProvider create(@NonNull ImmutableList<? extends PluginData> pluginDataList) {
        return new ServiceTypeProviderFactory(pluginDataList).create();
    }

    private @NonNull ImmutableList<? extends PluginData> pluginDataList;

    private @NonNull ServiceTypeProvider create() {
        final Map<Class<?>, Map<Integer, ServiceTypeProvider.ServiceInfo>> services = new HashMap<>();

        for (var pluginData : pluginDataList) {
            final var info = new ServiceTypeProvider.ServiceInfo(pluginData.getId(), pluginData.getProvidedServiceType());
            services.computeIfAbsent(info.getServiceType(), t -> new HashMap<>())
                    .merge(info.getMajorVersion(), info, ServiceTypeProvider.ServiceInfo::max );
        }

        return new ServiceTypeProvider(services);
    }


}
