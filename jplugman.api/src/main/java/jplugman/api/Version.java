package jplugman.api;

import jplugman.annotation.Extension;
import lombok.NonNull;
import lombok.Value;

import java.util.Comparator;
import java.util.regex.Pattern;

@Value
public class Version implements Comparable<Version> {

    int major;
    int minor;
    int patch;

    public Version(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    @Override
    public int compareTo(@NonNull Version that) {
        return VERSION_COMPARATOR.compare(this, that);
    }

    @Override
    public String toString() {
        return "v" + major + "." + minor + "." + patch;
    }


    public static Comparator<Version> VERSION_COMPARATOR = Comparator.comparingInt(Version::getMajor)
                                                                     .thenComparing(Version::getMinor)
                                                                     .thenComparing(Version::getPatch);

    private static final Pattern VERSION_PATTERN = Pattern.compile("v?(\\d+)\\.(\\d+)\\.(\\d+)");


    public static @NonNull Version with(@NonNull Extension extension) {
        return with(extension.version());
    }

    /**
     * Create a version from a string representation.
     *
     * @param versionAsString the version as string. Must be of the form "X.Y.Z" of "vX.Y.Z" where X,Y and Z must
     *                        be integer
     * @return a {@link Version} initialize with the provided string representation
     */
    public static @NonNull Version with(@NonNull String versionAsString) {
        final var matcher = VERSION_PATTERN.matcher(versionAsString.trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid version '" + versionAsString + "'");
        }
        return with(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3)));
    }

    public static @NonNull Version with(int major, int minor, int patch) {
        return new Version(major, minor, patch);
    }


}
