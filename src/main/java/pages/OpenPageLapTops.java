package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static helpers.CustomWaits.waitInvisibleIfLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * @author Сергей Канаев
 * Класс, описывающий работу в разделе Ноутбуки
 */
public class OpenPageLapTops {
    /**
     * @param String loadPageLapTop - селектор xpath ожидания прогрузки всей страницы раздела Ноутбуки
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
     * @param String pageLapTop - селектор xpath всей страницы раздела Ноутбуки
     */
    public String pageLapTop = "//div[@class='page']";

    public String pageLaTopsearchLoading = "//div[@class='page']//div[@id='/content/page/fancyPage/searchSerpStatic']//div[@data-zone-name='searchPage']";

    /**
     * @param String minPrice - селектор xpath поля ввода минимальной цены
     */
    private String minPrice = "//div[@data-grabber='SearchFilters']//div[@data-filter-id='glprice']//span[@data-auto='filter-range-min']//input[@type='text']";

    /**
     * @param String maxPrice - селектор xpath поля ввода максимальной цены
     */
    private String maxPrice = "//div[@data-grabber='SearchFilters']//div[@data-filter-id='glprice']//span[@data-auto='filter-range-max']//input[@type='text']";

    /**
     * @param String buttonLenovo3 - селектор xpath выбора бренда Lenovo
     */
    private String buttonLenovo3 = "//div[@data-filter-type='enum']//div[contains(@data-zone-data,'Lenovo')]//span[text()='";

    /**
     * @param String buttonHP3 - селектор xpath выбора бренда HP
     */
    private String buttonHP3 = "//div[@data-filter-type='enum']//div[contains(@data-zone-data,'HP')]//span[text()='";

    /**
     * @param String endOfXpath - селектор xpath окончания селекта. Необходим для метода по выбору модели ноутбука
     */
    private String endOfXpath = "']";

    /**
     * @param String setBrands - селектор xpath для настройки выбора ноутбуков по брендам
     */
    private String setBrands = "//div[@data-grabber='SearchFilters']//div[@data-filter-type='enum']//div[@data-zone-name='Filter']//h4[text()='Производитель']";


    /**
     * @param String titlePartCatalog - селектор xpath для поднятия в верх страницы с результатами
     */
    private String titlePartCatalog = "//div[@id='/content/page/fancyPage/searchTitleWithBreadcrumbs']";

    /**
     * Конструктор класса OpenPageLapTops - описывает работу драйвера на странице
     */
    public OpenPageLapTops(WebDriver chromeDriver) {
        this.driver = chromeDriver;
        this.wait = new WebDriverWait(driver, 30);
        wait.until(visibilityOfElementLocated(By.xpath(pageLapTop)));
        this.driver.findElement(By.xpath(pageLapTop));
    }

    /**
     * Метод setFilterPrice - устанавливает минимальную и максимальную цены в фильтре выбора ноутбуков
     * @param minPriceValue - минимальное значение цены для установки фильтра
     * @param maxPriceValue - максимальное значение цены для установки фильтра
     */
    @Step("Установка в фильтре минимальной м максимальной цен")
    public void setFilterPrice(String minPriceValue, String maxPriceValue) {
        waitInvisibleIfLocated(driver, loadPageLaptop, 3,6);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(minPrice)));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(maxPrice)));
        WebElement minPriceField = driver.findElement(By.xpath(minPrice));
        WebElement maxPriceField = driver.findElement(By.xpath(maxPrice));
        minPriceField.click();
        minPriceField.clear();
        minPriceField.sendKeys(minPriceValue);
        waitInvisibleIfLocated(driver, loadPageLaptop, 4,7);
        new WebDriverWait(driver, 10).until(webDriver ->
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        maxPriceField.click();
        maxPriceField.clear();
        maxPriceField.sendKeys(maxPriceValue);
        waitInvisibleIfLocated(driver, loadPageLaptop, 2,5);
        new WebDriverWait(driver, 10).until(webDriver ->
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Метод setFilterModels - выбирает интересующие нас модели в фильтре выбора ноутбуков
     * @param model1 - название модели для установки фильтра
     * @param model2 - название модели для установки фильтра
     */
    @Step("Установка в фильтре интересующих моделей ноутбуков по словам {model1, model 2}")
    public void setFilterModels(String model1, String model2) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement position = (new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(setBrands))));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", position);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(setBrands)));
        WebElement buttonModelHP = driver.findElement(By.xpath(buttonHP3 + model1 + endOfXpath));
        buttonModelHP.click();
        waitInvisibleIfLocated(driver, loadPageLaptop, 3,6);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pageLaTopsearchLoading)));
        new WebDriverWait(driver, 10).until(webDriver ->
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(setBrands)));
        WebElement buttonModelLenovo = driver.findElement(By.xpath(buttonLenovo3 + model2 + endOfXpath));
        buttonModelLenovo.click();
        waitInvisibleIfLocated(driver, loadPageLaptop, 3,6);
        //JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement position1 = (new WebDriverWait(driver, 20)
                .until(visibilityOfElementLocated(By.xpath(titlePartCatalog))));
        js.executeScript("arguments[0].scrollIntoView(true);", position1);
    }
}
