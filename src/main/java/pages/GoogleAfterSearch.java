package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * @autor Сергей Канаев
 * класс GoogleAfterSearch - описывает работу сайта google.ru после нахождения результатов поиска по заданному слову,
 * а также описывает переход на страницу сайта Яндекс Маркет из поля найденных поиском результатов
 */
public class GoogleAfterSearch {
    /**
     * Переменная driver, которая отвечает за работу драйвера
     */
    private WebDriver driver;

    /**
     * Переменная wait, которая отвечает за ожидание загрузки элементов на странице сайта
     */
    private WebDriverWait wait;

    /**
     * Метод getWebDriver() для работы со степами теста
     */
    public WebDriver getWebDriver() { return driver; }

    /**
     * Конструктор класса для управления браузером
     */
    public GoogleAfterSearch(WebDriver driver) {
        this.driver = driver;
        this.wait=new WebDriverWait(driver, 10);
    }

    /**
     * Конструктор класса для управления браузером, переопределённый
     * @param searchQuery - искомое слово
     */
    public GoogleAfterSearch(WebDriver driver, String searchQuery) {
        this.driver = driver;
        this.wait=new WebDriverWait(driver, 10);
        driver.get("https://www.google.ru/search?q="+searchQuery);
    }

    public void checkingTitleByText(String title){
        wait.until(visibilityOfElementLocated(By.xpath("//div[@id='rcnt']//div[@class='g']//a[@href='https://market.yandex.ru/']")));
        Assertions.assertFalse( driver.findElements(By.xpath("//div[@id='rcnt']//div[@class='g']//a[@href='https://market.yandex.ru/']")).size()==0,
                "Не найдено тайтла с текстом: '");
    }

    /**
     * Метод перехода на сайт Яндекс Маркет из результатов поиска в google.ru
     */
    public void goPageByLinkName(){
        wait.until(visibilityOfElementLocated(By.xpath("//div[@id='rcnt']//div[@class='g']//a[@href='https://market.yandex.ru/']")));
        driver.findElement(By.xpath("//div[@id='rcnt']//div[@class='g']//a[@href='https://market.yandex.ru/']")).click();

        Set<String> tabs = driver.getWindowHandles();
        for(String tab:tabs)
            driver.switchTo().window(tab);
    }
}

