package jplugman.test.test;

import jplugman.manager.PluginManager;
import jplugman.test.core.DummyService;
import lombok.NonNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TestLoadPlugin5 extends TestLoadPluginBase {

    @Override
    protected void setUp(@NonNull PluginManager pluginManager) {
        pluginManager.addPluginBundle(getPluginPath("plugin5"));
    }

    public static Stream<ExpectedServices> expectedServices() {
        return Stream.of(ExpectedServices.builder()
                                         .version(DummyService.class, "4.2.0")
                                         .build());
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
