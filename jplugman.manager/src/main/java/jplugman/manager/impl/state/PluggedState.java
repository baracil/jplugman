package jplugman.manager.impl.state;

import jplugman.api.Disposable;
import lombok.Getter;
import lombok.NonNull;
import jplugman.api.VersionedService;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Log4j2
public class PluggedState extends PluginStateBase {

    @Getter
    private final @NonNull VersionedService versionedService;

    public PluggedState(@NonNull PluginContext context, @NonNull VersionedService versionedService) {
        super(context);
        this.versionedService = versionedService;
    }

    @Override
    public boolean isInInstalledState() {
        return false;
    }

    @Override
    public boolean isInPluggedState() {
        return true;
    }

    @Override
    public boolean isInFailedState() {
        return false;
    }

    @Override
    public @NonNull PluginState unload() {
        getPluginContext().unplugService(versionedService);
        versionedService.getServiceAs(Disposable.class).ifPresent(this::tryToDispose);
        return new InstalledState(getPluginContext());
    }

    private void tryToDispose(@NonNull Disposable disposable) {
        try {
            disposable.dispose();
        } catch (Throwable t) {
            LOG.warn("Error while disposing the service '{}' : {}",versionedService,t.getMessage());
            LOG.debug(t);
        }
    }

}
