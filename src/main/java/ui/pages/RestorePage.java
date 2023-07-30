package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RestorePage {
    private final WebDriver driver;

    public RestorePage(WebDriver driver) {
        this.driver = driver;
    }
    public void clickToLoginBtn(){
        driver.findElement(restoreLoginBtn).click();
    }
    private final By restoreLoginBtn = By.xpath(".//a[contains(text(),'Войти')]");
}