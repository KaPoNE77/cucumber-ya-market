package ru.market.yandex;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.*;

import static steps.StepsAll.*;

/**
 *  @autor Сергей Канаев
 *  Данный класс Tests включает тесты сайта Яндекс Маркет на работу по поиску необходимых,
 *  по условиям фильтров,  элементов.
 */
public class Tests extends BaseTest {
    @Test
    public void firstTest() {
        chromeDriver.get("https://market.yandex.ru/");
        String title = chromeDriver.getTitle();
        System.out.println(title);
        Assertions.assertTrue(title.contains("Яндекс Маркет"), "Тайтл " + title + " на сайте не содержит Яндекс Маркет");
    }

    @Test
    public void testFind() {
        chromeDriver.get("https://market.yandex.ru/");
        WebElement searchButtonCatalog = chromeDriver.findElement(By.xpath("//button[contains(@class, 'button-focus')]//*[@id='hamburger']"));
        WebElement searchButtonElectronics = chromeDriver.findElement(By.xpath("//div[@role='dialog']//div[@class='_3zga_']//ul[@role='tablist']//a[@class='_3yHCR' and @href='https://market.yandex.ru/special/electronics_dep']"));

        searchButtonCatalog.click();
        searchButtonElectronics.click();

    }

    @Feature("Проверка сайта Яндекс Маркет")
    @DisplayName("Проверка сайта Яндекс Маркет - базовая")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#providerCheckingLapTops")
    public void OpenList(String searchQuery, String title, String minPrice, String maxPrice, String model1, String model2, int minQuantityElementsOnPage) {
        chromeDriver.get("https://www.google.ru/");
        GoogleBeforeSearch googleBeforeSearch = new GoogleBeforeSearch(chromeDriver);
        googleBeforeSearch.find(searchQuery);
        GoogleAfterSearch googleAfterSearch = new GoogleAfterSearch(chromeDriver);
        googleAfterSearch.goPageByLinkName();
        OpenMainPage openPage = new OpenMainPage(chromeDriver);
        openPage.goCatalogElectronics();
        openPage.goPartition(title);
        OpenPageLapTops openPageLapTops = new OpenPageLapTops(chromeDriver);
        openPageLapTops.setFilterPrice(minPrice, maxPrice);
        openPageLapTops.setFilterModels(model1, model2);
        ChecktedLapTopElements checktedLapTopElements = new ChecktedLapTopElements(chromeDriver);
        checktedLapTopElements.checkQuantityElementsOnFirstPage(minQuantityElementsOnPage);
        checktedLapTopElements.checkTitleElementsOnPages(model1, model2);
        checktedLapTopElements.checkPricesElementsOnPages(minPrice, maxPrice);
        checktedLapTopElements.checkConditionsOnAllPage(model1, model2, minPrice, maxPrice);
        CheckedElementsOnFirstPage checkedElementsOnFirstPage = new CheckedElementsOnFirstPage(chromeDriver);
        checkedElementsOnFirstPage.finalCompareElements();

    }

    @Feature("Проверка сайта Яндекс Маркет")
    @DisplayName("Проверка сайта Яндекс Маркет - всё в степах")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#providerCheckingLapTops")
    public void testYndexMarketStepsAll(String searchQuery, String title, String minPrice, String maxPrice, String model1, String model2, int minQuantityElementsOnPage){
        openSite("https://www.google.ru/","Google",chromeDriver);
        searchInGoogle(searchQuery);
        goSiteByLink();
        stepOnMainPage(title);
        setFilters(minPrice, maxPrice, model1, model2);
        checkSetFilters(minPrice, maxPrice, model1, model2, minQuantityElementsOnPage);
        repeateSearchCompare();
    }
}
