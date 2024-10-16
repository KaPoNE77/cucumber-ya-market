package pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * @author Сергей Канаев
 * Данный класс OpenMainPage описывает алгоритм перехода с главной страницы сайта Яндекс Маркет в раздел Электроника,
 * а затем в подраздел Ноутбуки
 */
public class OpenMainPage {
    /**
     * Переменная searchButtonCatalog, которая содержит xpath-select на кнопку Каталог
     */
    private String searchButtonCatalog = "//noindex/div[@data-apiary-widget-name='@light/NavigationMenu']//ancestor::button";

    /**
     * Переменная searchButtonElectronics, которая содержит xpath-select на кнопку подраздела Электроника
     */
    private String searchButtonElectronics = "//div[@data-zone-name='catalog-content']//span[text()='Электроника']";

    /**
     * Переменная buttonSearchLapTop, которая содержит xpath-select на кнопку подраздела Ноутбуки
     */
    private String buttonSearchLapTop = "//div[contains(@data-apiary-widget-id,'/content/')]//div[contains(@data-zone-data,'--noutbuki')]/a";

    /**
     * Переменная lapTopTitle, которая содержит xpath-select на заголовок подраздела Ноутбука
     */
    private String lapTopTitle = "//div[@class='page']//div[@data-zone-name='searchTitle']//a[@title='Ноутбуки']";

    /**
     * Переменная driver, которая отвечает за работу драйвера
     */
    private WebDriver driver;

    /**
     * Переменная wait, которая отвечает за ожидание загрузки элементов на странице сайта
     */
    private WebDriverWait wait;

    /**
     * Конструктор с параметрами для работы браузера
     * @param driver - драйвер браузера
     */
    public OpenMainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30);
        wait.until(visibilityOfElementLocated(By.xpath(searchButtonCatalog)));
        this.driver.findElement(By.xpath(searchButtonCatalog));
    }
    /**
     * Метод перехода в раздел Электроника с помощью класса Actions Selenium
     * @param section - раздел "Электроника"
     */
    @Step("наведение курсора на {section}")
    private void electronicsSection(String section) {
        WebElement buttonElectronics = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(section)));
        Actions actions = new Actions(driver);
        actions.moveToElement(buttonElectronics).click().perform();
    }

    /**
     * Метод перехода (алгоритм) с главной страницы Яндекс Маркет по пути: Каталог - Электроника
     */
    @Step("Переход из Каталога в раздел Электроника")
    public void goCatalogElectronics() {
        wait.until(visibilityOfElementLocated(By.xpath(searchButtonCatalog)));
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchButtonCatalog)));
        new WebDriverWait(driver, 10).until(webDriver ->
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        driver.findElement(By.xpath(searchButtonCatalog)).click();
        wait.until(visibilityOfElementLocated(By.xpath(searchButtonElectronics)));
        electronicsSection(searchButtonElectronics);
    }

    /**
     * Метод перехода (алгоритм) из раздела Электроника в раздел Ноутбуки и проверка данного перехода
     */
    @Step("Переход из раздела Электроника в раздел Ноутбуки")
    public void goPartition(String lapTopTitle) {
        //wait.until(visibilityOfElementLocated(By.xpath("//div[@class='page']")));
        wait.until(visibilityOfElementLocated(By.xpath(buttonSearchLapTop)));
        driver.findElement(By.xpath(buttonSearchLapTop)).click();
        Assertions.assertTrue(lapTopTitle.contains("Ноутбуки"), "Тайтл " + lapTopTitle + " на сайте не содержит подраздел Ноутбуки");
    }
}
