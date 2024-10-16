package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * @author Сергей Канаев
 * Class GoogleBeforeSearch описывает работу по вводу искомого слова в строке поиска сайта Google.ru
 * */
public class GoogleBeforeSearch {

    /**
     * Переменная inputSearch, отвечающая за поле ввода поисковой строки
     */
    private WebElement inputSearch;

    /**
     * Переменная buttonSearch, отвечающая за поле кнопки поиск
     */
    private WebElement buttonSearch;

    /**
     * Переменная driver, которая отвечает за работу драйвера
     */
    private WebDriver driver;

    /**
     * Переменная wait, которая отвечает за ожидание загрузки элементов на странице сайта
     */
    private WebDriverWait wait;

    /**
     * Конструктор класса страницы поиска
     */
    public GoogleBeforeSearch(WebDriver driver) {
        this.driver = driver;
        this.wait=new WebDriverWait(driver, 30);
        wait.until(visibilityOfElementLocated(By.xpath("//textarea[@title='Поиск']")));
        this.inputSearch = driver.findElement(By.xpath("//textarea[@title='Поиск']"));
        this.buttonSearch = driver.findElement(By.xpath("//div[not (@jsname)]/center/*[@value='Поиск в Google']"));
    }

    /**
     * Метод поиска по заданному слову
     */
    @Step("Поиск в Google по слову {text}")
    public void find(String text){
        inputSearch.sendKeys(text);
        driver.findElement(By.xpath("//img[@alt='Google']")).click();
        buttonSearch.click();
    }
}
