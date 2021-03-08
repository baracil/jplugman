package Bastien Aracil.plugin.api;

import lombok.NonNull;
import lombok.Value;

import java.util.Comparator;
import java.util.regex.Pattern;

@Value
public class Version implements Comparable<Version> {

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

    @Override
    public String toString() {
        return "v" + major + "."+ minor + "."+patch;
    }


    public static Comparator<Version> VERSION_COMPARATOR = Comparator.comparingInt(Version::getMajor)
                                                                     .thenComparing(Version::getMinor)
                                                                     .thenComparing(Version::getPatch);

    private static final Pattern VERSION_PATTERN = Pattern.compile("v?(\\d+)\\.(\\d+)\\.(\\d+)");

    public static @NonNull Version with(@NonNull String versionAsString) {
        final var matcher = VERSION_PATTERN.matcher(versionAsString.trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid version '"+versionAsString+"'");
        }
        return with(Integer.parseInt(matcher.group(1)),Integer.parseInt(matcher.group(2)),Integer.parseInt(matcher.group(3)));
    }

    public static @NonNull Version with(int major, int minor, int patch) {
        return new Version(major,minor,patch);
    }


}
