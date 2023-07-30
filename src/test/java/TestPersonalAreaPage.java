import api.request.Request;
import api.functions.FunctionsUserCreate;
import api.models.response.UserResponseModel;

import io.qameta.allure.junit4.DisplayName;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ui.pages.MainPage;
import ui.pages.PersonalAreaPage;
import ui.pages.AuthorizationPage;

import static api.functions.Utilities.deserialize;
import static api.functions.FunctionsUserDelete.getUserDelete;

@RunWith(Parameterized.class)
public class TestPersonalAreaPage extends Utilities {

    @Before
    public void setConfig() {
        getStarted("ya");
        Request request = new Request();
        request.apiEndPoint();
        getCreateUser();
    }

    private final String name;
    private final String email;
    private final String password;

    public TestPersonalAreaPage(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Parameters(name = "Тестовые данные: {0},{1},{2}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"TestQAUser", "TestsQAUser@yandex.ru", "12345P@sw0rd!"}
        };
    }

    private UserResponseModel response;

    public void getCreateUser() {
        FunctionsUserCreate userCreate = new FunctionsUserCreate();
        response = deserialize(userCreate.getUserCreate(name, email, password, 200), UserResponseModel.class);
    }

    @Test
    @DisplayName("Переход в личный кабинет по клику на «Личный кабинет»")
    public void shouldPersonalAreaForm() {
        MainPage mainPage = new MainPage(driver);
        mainPage.waitLoadMainPages();
        mainPage.clickToPersonalAreaBtn();

        AuthorizationPage authorization = new AuthorizationPage(driver);
        authorization.getAuthorization(email, password);
        mainPage.waitLoadMainPages();
        mainPage.clickToPersonalAreaBtn();

        PersonalAreaPage personalArea = new PersonalAreaPage(driver);
        Assert.assertEquals("Профиль",  personalArea.personalAreaProfileBtn());
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор")
    public void shouldPersonalAreaFromConstructor() {
        MainPage mainPage = new MainPage(driver);
        mainPage.waitLoadMainPages();
        mainPage.clickToPersonalAreaBtn();

        AuthorizationPage authorization = new AuthorizationPage(driver);
        authorization.getAuthorization(email, password);
        mainPage.clickToPersonalAreaBtn();

        PersonalAreaPage personalArea = new PersonalAreaPage(driver);
        personalArea.clickToConstructorBtn();

        Assert.assertEquals("Соберите бургер", mainPage.getTextHeaderConstructor());
    }

    @Test
    @DisplayName("Переход из личного кабинет по клику на логотип")
    public void shouldPersonalAreaFormLogo() {
        MainPage mainPage = new MainPage(driver);
        mainPage.waitLoadMainPages();
        mainPage.clickToPersonalAreaBtn();

        AuthorizationPage authorization = new AuthorizationPage(driver);
        authorization.getAuthorization(email, password);
        mainPage.clickToPersonalAreaBtn();

        PersonalAreaPage personalArea = new PersonalAreaPage(driver);
        personalArea.clickToLogoBtn();

        Assert.assertEquals("Соберите бургер", mainPage.getTextHeaderConstructor());
    }

    @Test
    @DisplayName("Переход в личный кабинет - по клику на кнопку Выход")
    public void shouldPersonalAreaSignOut() {
        MainPage mainPage = new MainPage(driver);
        mainPage.waitLoadMainPages();
        mainPage.clickToPersonalAreaBtn();

        AuthorizationPage authorization = new AuthorizationPage(driver);
        authorization.getAuthorization(email, password);
        mainPage.clickToPersonalAreaBtn();

        PersonalAreaPage personalArea = new PersonalAreaPage(driver);
        personalArea.clickToSignOut();

        Assert.assertEquals("Войти", authorization.getTextAuthLoginBtn());
    }

    @After
    public void teardown() {
        driver.quit();
        getUserDelete(response.getAccessToken());
    }
}