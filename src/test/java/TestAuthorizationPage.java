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
import ui.pages.RestorePage;
import ui.pages.AuthorizationPage;
import ui.pages.RegistrationPage;

import static api.functions.Utilities.deserialize;
import static api.functions.FunctionsUserDelete.getUserDelete;

@RunWith(Parameterized.class)
public class TestAuthorizationPage extends Utilities {

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

    public TestAuthorizationPage(String name, String email, String password) {
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
    @DisplayName("Авторизация пользователя - на главной странице")
    public void shouldLoginFromMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.waitLoadMainPages();
        mainPage.clickToLoginBtn();

        AuthorizationPage authorization = new AuthorizationPage(driver);
        authorization.getAuthorization(email, password);

        Assert.assertEquals("Оформить заказ", mainPage.getTextAuthCheckoutBtn());
    }

    @Test
    @DisplayName("Авторизация пользователя - через «Личный кабинет»")
    public void shouldLoginFromPersonalAreaPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.waitLoadMainPages();
        mainPage.clickToPersonalAreaBtn();

        AuthorizationPage authorization = new AuthorizationPage(driver);
        authorization.getAuthorization(email, password);

        Assert.assertEquals("Оформить заказ", mainPage.getTextAuthCheckoutBtn());
    }

    @Test
    @DisplayName("Авторизация пользователя - через форму регистрации")
    public void shouldLoginFromRegistrationForm() {
        MainPage mainPage = new MainPage(driver);
        mainPage.waitLoadMainPages();
        mainPage.clickToLoginBtn();

        AuthorizationPage authorization = new AuthorizationPage(driver);
        authorization.clickToRegistrationBtn();

        RegistrationPage registration = new RegistrationPage(driver);
        registration.clickToLoginBtn();

        authorization.getAuthorization(email, password);

        Assert.assertEquals("Оформить заказ", mainPage.getTextAuthCheckoutBtn());
    }

    @Test
    @DisplayName("Авторизация пользователя - через форму восстановления пароля.")
    public void shouldLoginFromRestoreForm() {
        MainPage mainPage = new MainPage(driver);
        mainPage.waitLoadMainPages();
        mainPage.clickToLoginBtn();

        AuthorizationPage authorization = new AuthorizationPage(driver);
        authorization.clickToRestoreBtn();

        RestorePage restorePage = new RestorePage(driver);
        restorePage.clickToLoginBtn();

        authorization.getAuthorization(email, password);

        Assert.assertEquals("Оформить заказ", mainPage.getTextAuthCheckoutBtn());
    }

    @After
    public void teardown() {
        driver.quit();
        getUserDelete(response.getAccessToken());
    }
}