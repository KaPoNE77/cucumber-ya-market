package ru.market.yandex;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import static helpers.CustomWaits.implicitlyWait;
import static helpers.Properties.testsProperties;

/**
 * @author Сергей Канаев
 * Класс базовый, содержит общие настройки драйвера браузера,
 * а также, алгоритм открытия-закрытия драйвера перед и после каждого теста
 * */
public class BaseTest {
    /**
     * WebDriver chromeDriver отвечает за работу драйвера
     * */
    protected WebDriver chromeDriver;

    /**
     * метод before() - описывает алгоритм работы браузера перед каждым тестом
     * В данном методе ожидание загрузки страницы отключено
     * */
    @BeforeEach
    public void before() {
        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "none");
        chromeDriver = new ChromeDriver(capabilities);
        chromeDriver.manage().window().maximize();

        implicitlyWait(chromeDriver,testsProperties.defaultTimeout());
    }

    /**
     * Метод after() закрывает браузер после окончания теста, независимо от результатов выполнения
     * */
    @AfterEach
    public void after(){
        chromeDriver.quit();
    }

}
