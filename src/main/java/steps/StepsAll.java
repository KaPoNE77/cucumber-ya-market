package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;

import java.time.Duration;

/**
 * @author Сергей Канаев
 * Данный класс описывает процедуру выполнения тестов по шагам
 */
public class StepsAll {
    /**
     * Переменная driver, которая отвечает за работу драйвера
     */
    private static WebDriver driver;

    /**
     * Переменная wait, которая отвечает за ожидание загрузки элементов на странице сайта
     */
    private static WebDriverWait wait;

    /**
     * Шаг в тестировании: открытие браузера Chrome
     */
    @Step("Открытие браузера и переход на сайт: {url}")
    public static void openSite (String url, String title, WebDriver currentDriver) {
        driver = currentDriver;
        driver.get(url);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.titleIs(title));
    }

    /**
     * Шаг в тестировании: проверяем наличие тайтла: {title} в результатах поиска Google
     * @param title - поисковый запрос "Яндекс Маркет"
     */
    @Step("Проверяем наличие тайтла: {title} в результатах поиска Google")
    public static void validateSearchInGoogle(String title) {
        GoogleAfterSearch googleAfterSearch = new GoogleAfterSearch(driver);
        googleAfterSearch.checkingTitleByText(title);
    }

    /**
     * Шаг в тестировании: ищем в поисковике по ключевой фразе: {searchQuery}
     * @param searchQuery - поисковый завпрос"Яндекс Маркет"
     */
    @Step("Ищем в поисковике по ключевой фразе: {searchQuery}")
    public static void searchInGoogle(String searchQuery){
        GoogleBeforeSearch googleBeforeSearch = new GoogleBeforeSearch(driver);
        googleBeforeSearch.find(searchQuery);
    }
    /**
     * Шаг в тестировании: переходим на сайт по заголовку: {title}
     */
    @Step("Переходим на сайт по ссылке")
    public static void goSiteByLink(){
        GoogleAfterSearch googleAfterSearch = new GoogleAfterSearch(driver);
        googleAfterSearch.goPageByLinkName();
    }

    /**
     * Шаг в тестировании: переходим на сайт и переход в раздел электроника, а затем в подраздел ноутбуки
     * @param title - название подраздела "Ноутбуки"
     */
    @Step("Открытие каталога, кликаем на раздел, а затем на подраздел")
    public static void stepOnMainPage(String title) {
        OpenMainPage openPage = new OpenMainPage(driver);
        openPage.goCatalogElectronics();
        openPage.goPartition(title);
    }

    /**
     * Шаг в тестировании: в подраздел ноутбуки выставляем необходимые фильтры
     * @param  minPrice - минимальная цена для фильтра
     * @param  maxPrice - максимальная цена для фильтра
     * @param model1 - выбор модели устройства для фильтра
     * @param model2 - выбор модели устройства для фильтра
     *
     */
    @Step("Устанавливаем необходимые фильтры")
    public static void setFilters(String minPrice, String maxPrice, String model1, String model2) {
        OpenPageLapTops openPageLapTops = new OpenPageLapTops(driver);
        openPageLapTops.setFilterPrice(minPrice, maxPrice);
        openPageLapTops.setFilterModels(model1, model2);
    }
    /**
     * Шаг в тестировании: проверяем условия действия установленных фильтров
     * @param  minPrice - минимальная цена устройств на странице для проверки
     * @param  maxPrice - максимальная цена устройств на странице для проверки
     * @param model1 - проверка модели устройства на странице
     * @param model2 - проверка модели устройства на странице
     * @param minQuantityElementsOnPage - проверка минимального количества устройств на странице
     */
    @Step("Проверка фильтров по названию ноутбуков, установленной разнице в цене, минимального количества устройтсв на странице ")
    public static void checkSetFilters(String minPrice, String maxPrice, String model1, String model2, int minQuantityElementsOnPage) {
        ChecktedLapTopElements checktedLapTopElements = new ChecktedLapTopElements(driver);
        checktedLapTopElements.checkQuantityElementsOnFirstPage(minQuantityElementsOnPage);
        checktedLapTopElements.checkTitleElementsOnPages(model1, model2);
        checktedLapTopElements.checkPricesElementsOnPages(minPrice, maxPrice);
        checktedLapTopElements.checkConditionsOnAllPage(model1, model2, minPrice, maxPrice);
    }

    /**
     * Шаг в тестировании: возвращение на первую страницу, поиск первого элемента в строке поиска сайта и сравнение его
     * с полученными результатами
     */
    @Step("Возвращение на первую страницу, поиск элемента на первой станице и сравнение его с результатами повторного поиска")
    public static void repeateSearchCompare() {
        CheckedElementsOnFirstPage checkedElementsOnFirstPage = new CheckedElementsOnFirstPage(driver);
        checkedElementsOnFirstPage.finalCompareElements();
    }
}
