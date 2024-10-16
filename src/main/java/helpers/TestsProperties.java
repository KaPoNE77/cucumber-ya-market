package helpers;

import org.aeonbits.owner.Config;

/**
 * Интерфейс TestsProperties содержит ключи для проведения тестов
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties",
        "system:env",
        "file:src/main/resources/tests.properties"})
public interface TestsProperties extends Config {

    @Key("testdata.json.path")
    String testDataJsonPath();

    @Key("marketyandex.url")
    String marketyandexUrl();

    @Config.Key("default.timeout")
    int defaultTimeout();
}
