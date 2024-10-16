package pages;

import helpers.LoadingHelper;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static helpers.CustomWaits.waitInvisibleIfLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * @author Сергей Канаев
 * Данный класс class CheckedElementsOnFirstPage описывает алгоритм работы теста, в котором происходит:
 * 1) возвращение на первую страницу;
 * 2) получение в качестве искомого элемента название первого элемента;
 * 3) поиск данного элемента в строке поиска;
 * 4) сравнение полученных результатов с поисковым запросом
 */
public class CheckedElementsOnFirstPage {

    /**
     * Переменная driver, которая отвечает за работу драйвера
     */
    private WebDriver driver;

    /**
     * Переменная wait, которая отвечает за ожидание загрузки элементов на странице сайта
     */
    private WebDriverWait wait;

    /**
     * Объект типа {@code LoadingHelper} для ожидания загрузки элементов на странице.
     */
    private LoadingHelper loadingHelper;

    /**
     * @param String titlePartCatalog - селектор xpath для поднятия в верх страницы с результатами
     */
    private String titlePartCatalog = "//div[@id='/content/page/fancyPage/searchTitleWithBreadcrumbs']";

    /**
     * Переменная String titleOfLapTop с селектом для названия интересующего элемента
     */
    private String titleOfLapTop = "//div[@data-baobab-name='title']//span[@role='link']";

    /**
     * @param String loadPageLapTop - селектор xpath элемента прогрузки изменений
     */
    private static final String loadPageLaptop = "//div[@data-auto='preloader']";
    /**
     * @param String searchFieldOnYM - селектор xpath элемента поля поиска на странице Яндекс Маркет
     */
    private String searchFieldOnYM = "//form[@role='search']//div[@data-zone-name='search-input']/input[@name='text']";
    /**
     * @param String searchButtonOnYM - селектор xpath элемента кнопки поиска на странице Яндекс Маркет
     */
    private String searchButtonOnYM = "//div[@data-zone-name='search_block']//form[@role='search']//button[@type='submit']";

    /**
     * Конструктор CheckedElementsOnFirstPage с параметрами
     * @param chromeDriver - драйвер браузера
     */
    public CheckedElementsOnFirstPage(WebDriver chromeDriver) {
        this.driver = chromeDriver;
        this.wait = new WebDriverWait(driver, 30);
        this.loadingHelper = loadingHelper;
    }

    /**
     * Метод getElementTitleOnFirstPage описывает алгоритм возвращения на первую страницу и получение заголовка
     * названия первого элемента
     */
    @Step("Возвращение на первую страницу и получение заголовка-названия первого элемента")
    public String getElementTitleOnFirstPage(String xpath) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
//        WebElement position = (new WebDriverWait(driver, Duration.ofSeconds(20))
        WebElement position = (new WebDriverWait(driver, 20)
                .until(visibilityOfElementLocated(By.xpath(titlePartCatalog))));
        js.executeScript("arguments[0].scrollIntoView(true);", position);
        System.out.println("Мы вернулись на первую страницу в начало списка элементов.");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        List<WebElement> elementTitle = driver.findElements(By.xpath(titleOfLapTop));
        WebElement firstElement = elementTitle.get(0);
        String firstElementTitle = firstElement.getText();
        System.out.println("Заголовок-название первого элемента: " + firstElementTitle);
        return firstElementTitle;
    }

    /**
     * Метод getElementTitle описывает алгоритм получение заголовка-названия первого элемента
     */
    @Step("Получаем и запоминаем название элемента(ноутбука)")
    // вариант 1:
//    private String getElementTitle(String elementXpath) {
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementXpath)));
//        WebElement elementContainer = driver.findElement(By.xpath(elementXpath));
//        String title = elementContainer.getAttribute("title");
//        System.out.println("Получен заголовок элемента: " + title);
//        return title;
//    }

    // вариант 2:
    private String getElementTitle(String elementXpath) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementXpath)));
        List<WebElement> titleOfElements = driver.findElements(By.xpath(titleOfLapTop));
        WebElement firstElement = titleOfElements.get(0);
        String firstElementTitle = firstElement.getText();
        System.out.println("Заголовок элемента после поиска - " + firstElementTitle);
        return firstElementTitle;
    }
    /**
     * Метод elementInSearchField описывает алгоритм поиска элемента
     */
    @Step("Поиск элемента в строке поиска")
    private void elementInSearchField(String elementTitle) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchFieldOnYM)));
        WebElement searchElement = driver.findElement(By.xpath(searchFieldOnYM));
        searchElement.click();
        searchElement.clear();
        searchElement.sendKeys(elementTitle);
    }

    /**
     * Метод cleckSearchButton кликает по кнопке поиск на сайте Яндекс Маркета
     */
    @Step("Кликаем по кнопке поиск")
    private void cleckSearchButton() {
        WebElement cleckSearchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(searchButtonOnYM)));
        waitInvisibleIfLocated(driver, loadPageLaptop, 2, 5);
    }

    /**
     * Метод compareElements сравнивает названия элементов
     */
    @Step("Сравниваем полученные элементы")
    private void compareElements(String elementsOnFirstPage, String elementAfterSearch) {
        Assertions.assertEquals(elementsOnFirstPage, elementAfterSearch,
                "Элемент на первой странице и элемент после поиска - разные!");
    }

    /**
     * Метод finalCompareElements описывает алгоритм, в котором:
     * 1) получаем название первого элемента на первой странице;
     * 2) вставляем данный элемент в строку поиска;
     * 3) ждём кнопку "Поиск";
     * 4) получаем результат поиска и название первого элемента в строке;
     * 5) сравниваем результат элемента на первой строке и название элемента после поиска.
     */
    @Step("Сравнение названий первого элемента на первой странице и элемента после поиска")
    public void finalCompareElements() {
        String firstElementTitle = getElementTitleOnFirstPage(titleOfLapTop);
        elementInSearchField(firstElementTitle);
        cleckSearchButton();
        String secondElementTitle = getElementTitle(titleOfLapTop);
        compareElements(firstElementTitle, secondElementTitle);
    }
}
