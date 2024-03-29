package jplugman.test.test;

import jplugman.manager.PluginManager;
import lombok.NonNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TestLoadPlugin3 extends TestLoadPluginBase {

    @Override
    protected void setUp(@NonNull PluginManager pluginManager) {
        pluginManager.addPluginBundle(getPluginPath("plugin3"));
    }

    public static Stream<ExpectedServices> expectedServices() {
        return Stream.of(ExpectedServices.builder().build());
    }


    @ParameterizedTest
    @MethodSource("expectedServices")
    public void shouldHaveOneServiceAttached(@NonNull ExpectedServices versions) {
        checkNbServices(versions);
    }

    @ParameterizedTest
    @MethodSource("expectedServices")
    public void shouldHaveRightServices(@NonNull ExpectedServices versions) {
        checkServices(versions);
    }

}
