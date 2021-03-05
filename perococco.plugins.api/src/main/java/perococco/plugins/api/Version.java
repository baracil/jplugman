package Bastien Aracil.plugins.api;

import lombok.NonNull;
import lombok.Value;

import java.util.Comparator;

@Value
public class Version implements Comparable<Version> {

    public static Comparator<Version> VERSION_COMPARATOR = Comparator.comparingInt(Version::getMajor)
                                                                     .thenComparing(Version::getMinor)
                                                                     .thenComparing(Version::getPatch);

    public static @NonNull Version with(int major, int minor, int patch) {
        return new Version(major,minor,patch);
    }

    int major;
    int minor;
    int patch;

    public boolean isCompatible(@NonNull Version version) {
        return version.major == this.major;
    }

    @Override
    public int compareTo(Version that) {
        return VERSION_COMPARATOR.compare(this,that);
    }
}
