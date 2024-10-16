package helpers;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static helpers.Properties.testsProperties;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * Класс CustomWaits - описывает алгоритмы ожидания прогрузки страницы и появления элементов
 */
public class CustomWaits {
//    /**
//     * Переменная testsProperties
//     */
//    private static TestsProperties testsProperties;
    /**
     * Переменная implicitlyWait - ожидание
     */
    private static int implicitlyWait = testsProperties.defaultTimeout();
    /**
     * Переменная pageLoadTimeout - ожидание прогрузки страницы
     */
    private static int pageLoadTimeout = testsProperties.defaultTimeout();
    /**
     * Переменная pageLoadTimeout - ожидание прогрузки скрипта
     */
    private static int setScriptTimeout = testsProperties.defaultTimeout();

    /**
     * @param String loadPageLapTop - селектор xpath всей страницы раздела Ноутбуки
     */
    private static final String loadPageLaptop = "//div[@data-auto='preloader']";

    /**
     * Переменная driver, которая отвечает за работу драйвера
     */
    private WebDriver driver;

    /**
     * Переменная wait, которая отвечает за ожидание загрузки элементов на странице сайта
     */
    private WebDriverWait wait;

    /**
     * Конструктор класса
     * @param driver необходим для управления браузером
     * @param wait необходим для управления ожиданием элементов
     * конструктор CusomWaits и метод waitLoading - один из вариантов описания ожидания исчезнования прелоада
     */

    /**
     * Конструктор CustomWaits
     * @param driver - драйвер браузера
     * @param wait - ожидание
     */
    public CustomWaits(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void waitLoading() {
        WebElement loadingPage = driver.findElement(By.xpath(loadPageLaptop));
        wait.until(ExpectedConditions.stalenessOf(loadingPage));
    }

    public static void implicitlyWait (WebDriver driver, int defaultTimeout){
        driver.manage().timeouts().implicitlyWait(defaultTimeout, TimeUnit.SECONDS);
        implicitlyWait=defaultTimeout;
    }

    public static void pageLoadTimeout (WebDriver driver, int defaultTimeout){
        driver.manage().timeouts().pageLoadTimeout(defaultTimeout, TimeUnit.SECONDS);
        pageLoadTimeout=defaultTimeout;
    }

    public static void setScriptTimeout (WebDriver driver, int defaultTimeout){
        driver.manage().timeouts().setScriptTimeout(defaultTimeout, TimeUnit.SECONDS);
        setScriptTimeout=defaultTimeout;
    }

    private static void sleep(int sec){
        try {
            Thread.sleep(sec* 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @author Kirill Filenkov
     * Метод waitInvisibleIfLocated - описывает ожидание появления загрузочного элемента при прогрузке страницы
     * @param driver - драйвер браузера
     * @param elementXpath - Xpath - селектор элемента загрузки
     * @param timeWaitLocated - время ожидания появления элемента
     * @param timeWaitInvisible - время ожидания исчезновения элемента
     */
    public static void waitInvisibleIfLocated(WebDriver driver, String elementXpath , int timeWaitLocated, int timeWaitInvisible){ //

        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        for(int i=0;i<timeWaitLocated;++i){
            if(driver.findElements(By.xpath(elementXpath)).size()!=0){
                for(int j=0;;++j){
                    if(driver.findElements(By.xpath(elementXpath)).size()==0)
                        break;
                    if(j+1==timeWaitInvisible)
                        Assertions.fail("Элемент "+elementXpath+" не исчез за "+ timeWaitInvisible + " секунд!");
                    sleep(1);
                }
            }
            sleep(1);
        }
        implicitlyWait(driver,implicitlyWait);
    }

    public static FluentWait<WebDriver> getFluentWait(WebDriver driver, int timeWaite, int frequency){
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeWaite))
                .pollingEvery(Duration.ofMillis(frequency))
                .ignoreAll(List.of(
                        NoSuchElementException.class,
                        WebDriverException.class,
                        StaleElementReferenceException.class,
                        ElementClickInterceptedException.class,
                        TimeoutException.class)
                );
    }

    public static void fluentWaitInvisibleIfLocated(WebDriver driver, String elementXpath, int timeWaitLocated, int timeWaitInvisible){

        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        getFluentWait(driver,timeWaitLocated, 100).until(visibilityOfElementLocated(By.xpath(elementXpath)));
        getFluentWait(driver,timeWaitInvisible, 100).until(invisibilityOfElementLocated(By.xpath(elementXpath)));

        implicitlyWait(driver,implicitlyWait);
    }
}
