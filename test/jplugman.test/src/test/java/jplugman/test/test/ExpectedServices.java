package jplugman.test.test;

import jplugman.api.Version;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

import java.util.Iterator;
import java.util.List;

@Builder(builderClassName = "Builder")
public class ExpectedServices implements Iterable<ServiceTypeWithVersion> {

    @Singular("oneVersion")
    private final @NonNull List<ServiceTypeWithVersion> versions;

    public int nbServices() {
        return versions.size();
    }

    @NonNull
    @Override
    public Iterator<ServiceTypeWithVersion> iterator() {
        return versions.iterator();
    }

    public static class Builder {

        public Builder version(@NonNull Class<?> serviceType, @NonNull String version) {
            return oneVersion(new ServiceTypeWithVersion(serviceType, Version.with(version)));
        }

    }

}
