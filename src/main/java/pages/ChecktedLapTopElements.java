package pages;

import helpers.LoadingHelper;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static helpers.CustomWaits.waitInvisibleIfLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * @author Сергей Канаев
 * класс ChektedLapTopElements описывает:
 * 1. порядок проверки количество элементов на странице,
 * 2. проверку соответствия предложений заданным фильтрам
 * 3. поиск первого элемента через вставку его в поле поиска, с дальнейшей проверкой на наличие данного
 * элемента в результатах поиска
 */
public class ChecktedLapTopElements {


    /**
     * @param String loadPageLapTop - селектор xpath элемента прогрузки изменений
     */
    private static final String loadPageLaptop = "//div[@data-auto='preloader']";

    /**
     * Переменная String elementOfLapTop с селектом на интересующий элемент
     */
    private String elementOfLapTop = "//div[@data-apiary-widget-name='@light/Organic']";

    /**
     * Переменная String titleOfLapTop с селектом для названия интересующего элемента
     */
    private String titleOfLapTop = "//div[@data-baobab-name='title']//span[@role='link']";

    /**
     * Переменная String priceOfLapTop с селектом для цены интересующего элемента
     */
    private String priceOfLapTop = "//div[@data-baobab-name='main']//div[@data-auto-themename='listDetailed']//div[@data-baobab-name='price']//div[@data-auto='snippet-price-current']/div";

    /**
     * Переменная String endOfPageWithElements с селектом, указывающим на окончание страниц интересующих элементов
     */
    private String endOfPageWithElements = "//div[@data-grabber='SearchLegalInfo']";

    /**
     * @param String viewMore - селектор xpath кнопки вперёд внизу страницы
     */
    private String viewMore = "//div[@data-auto='infifinityButton']/button[@type='button']";

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
     * Конструктор класса ChecktedLapTopElements - описывает работу драйвера на странице
     */
    public ChecktedLapTopElements(WebDriver chromeDriver) {
        this.driver = chromeDriver;
        this.wait = new WebDriverWait(driver, 30);
        //wait.until(visibilityOfElementLocated(By.xpath(pageLapTop)));
        this.loadingHelper = new LoadingHelper(driver, wait);
    }

    /**
     * Метод checkQuantityElementsOnFirstPage - описывает алгоритм проверки количества элементов на первой странице каталога
     * @param minQuantityElementsOnPage - минимально количество элементов на первой странице, которое должно быть по условию
     */
    @Step("Проверка количества элементов на первой странице")
    public void checkQuantityElementsOnFirstPage(int minQuantityElementsOnPage) {

        waitInvisibleIfLocated(driver, loadPageLaptop, 2,5);

        List<WebElement> elements = driver.findElements(By.xpath(elementOfLapTop));
        System.out.println(elements.size());
        System.out.println(elements);
        //elements.forEach(x-> System.out.println(x.getText()));
        Assertions.assertTrue(elements.size() >= minQuantityElementsOnPage,
                "Количество элементов товара на странице меньше " + minQuantityElementsOnPage);
    }

    /**
     * Метод checkTitleElementsOnPages - описывает алгоритм проверки названия элементов по заданным условиям
     * @param model1 - проверка модели устройства на странице
     * @param model2 - проверка модели устройства на странице
     */
    @Step("Проверка названия элементов по заданным условиям на странице")
    public void checkTitleElementsOnPages(String model1, String model2) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement position = (new WebDriverWait(driver, 10)
                .until(visibilityOfElementLocated(By.xpath(endOfPageWithElements))));
        js.executeScript("arguments[0].scrollIntoView(true);", position);

        List<WebElement> titleOfElements = driver.findElements(By.xpath(titleOfLapTop));
        for(WebElement titleElement : titleOfElements) {
            String title1 = titleElement.getAttribute("title");
            boolean containsModel1 = model1.contains(title1);
            Assertions.assertTrue(containsModel1,
                    "Название модели не соответствует ожидаемому значению " + model1);

            String title2 = titleElement.getAttribute("title");
            boolean containsModel2 = model1.contains(title2);
            Assertions.assertTrue(containsModel2,
                    "Название модели не соответствует ожидаемому значению " + model2);
        }
        System.out.println(titleOfElements.size());
        System.out.println(titleOfElements);
    }

    /**
     * Метод checkPricesElementsOnPages - описывает алгоритм проверки цен элементов по заданным условиям
     * @param  minPrice - минимальная цена устройств на странице для проверки
     * @param  maxPrice - максимальная цена устройств на странице для проверки
     */
    @Step("Проверка соответствия минимальных и максимальных цен на странице")
    public void checkPricesElementsOnPages(String minPrice, String maxPrice) {
        // пробный вариант
        int minPriceWr = Integer.parseInt(minPrice);
        int maxPriceWr = Integer.parseInt(maxPrice);
        List<WebElement> priceOfElements = driver.findElements(By.xpath(priceOfLapTop));
        for (WebElement priceElement : priceOfElements) {
            String priceText = priceElement.getText().replaceAll(" ","");
            int price = Integer.parseInt(priceText);
            Assertions.assertTrue(price >= minPriceWr && price <= maxPriceWr,
                    "Цена на ноутбук " + price + " не соответствует заданным условиям от " + minPriceWr+ " до " + maxPriceWr);
        }
    }

    /**
     * Метод stepOnNextPage - описывает алгоритм перехода на следующую страницу раздела, при условии, что она доступна
     * Метод возвращает true - если переход на следующую страницу юыл успешным и false - если нет
     */
    @Step("Переход на следующую страницу")
    public boolean stepOnNextPage() {
        while (!driver.findElements(By.xpath(viewMore)).isEmpty()) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement position = (new WebDriverWait(driver, 20)
                    .until(visibilityOfElementLocated(By.xpath(viewMore))));
            js.executeScript("arguments[0].scrollIntoView(true);", position);
            //waitInvisibleIfLocated(driver, loadPageLaptop, 2, 5);
            driver.findElement(By.xpath(viewMore));
            waitInvisibleIfLocated(driver, loadPageLaptop, 2, 5);

        }
        return true;
    }

    /**
     * Метод checkConditionsOnPage - описывает алгоритм проверки заданных условий на странице
     * @param model1 - модель ноутбука 1 по условию теста
     * @param model2 - модель ноутбука 2 по условию теста
     * @param minPrice - минимальная цена по условию теста
     * @param maxPrice - максимальная цена по условию теста
     */
    @Step("Проверка условий на странице")
    public void checkConditionsOnPage(String model1, String model2, String minPrice, String maxPrice) {
        checkTitleElementsOnPages(model1, model2);
        checkPricesElementsOnPages(minPrice, maxPrice);
    }

    /**
     * Метод checkConditionsOnAllPage - описывает алгоритм проверки заданных условий на всех страницах
     * @param model1 - модель ноутбука 1 по условию теста
     * @param model2 - модель ноутбука 2 по условию теста
     * @param minPrice - минимальная цена по условию теста
     * @param maxPrice - максимальная цена по условию теста
     */
    public void checkConditionsOnAllPage(String model1, String model2, String minPrice, String maxPrice) {

        boolean hasNextPage = true;

        while (hasNextPage) {
            stepOnNextPage();
            checkConditionsOnPage(model1, model2, minPrice, maxPrice);
            hasNextPage = !stepOnNextPage();
        }
    }
}
