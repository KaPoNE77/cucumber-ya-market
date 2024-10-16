package helpers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * @author Сергей Канаев
 * Класс DataProvider содержит в себе параметры для проведения теста и его параметризации
 */
public class DataProvider {

    public static Stream<Arguments> providerCheckingLapTops() {
        return Stream.of(
                Arguments.of("Яндекс Маркет",
                        "Ноутбуки",
                        "10000",
                        "30000",
                        "HP",
                        "Lenovo",
                        12)

        );
    }
}
