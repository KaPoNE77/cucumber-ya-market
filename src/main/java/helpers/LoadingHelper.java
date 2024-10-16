package helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Сергей Канаев
 * Класс LoadingHelper описывает алгоритм появления и исчезноваения элемента прогрузки страницы
 */
public class LoadingHelper {
    /**
     * @param String LOADING_PRELOAD - селектор xpath элемента прогрузки изменений на странице
     */
    private static String LOADING_PRELOAD = "//div[@data-auto='preloader']";

    /**
     * Переменная driver, которая отвечает за работу драйвера
     */
    private WebDriver driver;

    /**
     * Переменная wait, которая отвечает за ожидание загрузки элементов на странице сайта
     */
    private WebDriverWait wait;

    /**
     * Конструктор класса.
     *
     * @param driver экземпляр WebDriver для управления браузером.
     * @param wait   экземпляр WebDriverWait для ожидания элементов на странице.
     */
    public LoadingHelper(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    /**
     * Метод {@code loading} ожидает, пока элемент загрузки исчезнет со страницы.
     */
    public void loading() {
        WebElement loadingElement = driver.findElement(By.xpath(LOADING_PRELOAD));
        wait.until(ExpectedConditions.stalenessOf(loadingElement));
    }
}
