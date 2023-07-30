import ui.pages.MainPage;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import io.qameta.allure.junit4.DisplayName;
import org.junit.runners.Parameterized.Parameters;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;

@RunWith(Parameterized.class)
public class TestMainPage extends Utilities {

    private final String buns;
    private final String sauce;
    private final String filling;

    public TestMainPage(String sauce, String filling, String buns) {
        this.buns = buns;
        this.sauce = sauce;
        this.filling = filling;
    }

    @Parameters(name = "Тестовые данные: {0}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"Соусы", "Начинки", "Булки"},
        };
    }

    @Before
    public void setConfig() {
        getStarted("ya");
    }

    @Test
    @DisplayName("«Конструктор» - переход в раздел «Булки»")
    public void shouldConstructor() {
        MainPage mainPage = new MainPage(driver);
        mainPage.waitLoadMainPages();
        mainPage.selectCategories(filling);
        mainPage.selectCategories(sauce);
        mainPage.selectCategories(buns);
        Assert.assertEquals(buns, mainPage.getTextNoActiveCategories());
    }

    @Test
    @DisplayName("«Конструктор» - переход в раздел «Соусы»")
    public void shouldConstructorSauce(){
        MainPage mainPage = new MainPage(driver);
        mainPage.waitLoadMainPages();
        mainPage.selectCategories(filling);
        mainPage.selectCategories(buns);
        mainPage.selectCategories(sauce);
        Assert.assertEquals(sauce, mainPage.getTextNoActiveCategories());
    }

    @Test
    @DisplayName("«Конструктор» - переход в раздел «Начинки»")
    public void shouldConstructorBuns(){
        MainPage mainPage = new MainPage(driver);
        mainPage.waitLoadMainPages();
        mainPage.selectCategories(sauce);
        mainPage.selectCategories(buns);
        mainPage.selectCategories(filling);
        Assert.assertEquals(filling, mainPage.getTextNoActiveCategories());
    }

    @After
    public void teardown() {
        driver.quit();
    }
}