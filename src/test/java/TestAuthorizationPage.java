import api.request.Request;
import api.functions.FunctionsUserRequest;
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

import static api.functions.Utilities.fromJsonString;
import static api.functions.FunctionsUserRequest.getUserDelete;

@RunWith(Parameterized.class)
public class TestAuthorizationPage extends Utilities {

    private final String name;
    private final String email;
    private final String password;
    private UserResponseModel response;
    MainPage mainPage;
    AuthorizationPage authorization;
    RegistrationPage registration;
    RestorePage restorePage;

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

    private void getCreateUser() {
        FunctionsUserRequest userCreate = new FunctionsUserRequest();
        response = fromJsonString(userCreate.getUserCreate(name, email, password, 200), UserResponseModel.class);
    }

    @Before
    public void setConfig() {
        getStarted("ya");
        Request request = new Request();
        request.apiEndPoint();
        getCreateUser();
        mainPage = new MainPage(driver);
        authorization = new AuthorizationPage(driver);
        registration = new RegistrationPage(driver);
        restorePage = new RestorePage(driver);
    }

    @Test
    @DisplayName("Авторизация пользователя - на главной странице")
    public void shouldLoginFromMainPage() {
        mainPage.waitLoadMainPages();
        mainPage.clickToLoginBtn();
        authorization.getAuthorization(email, password);
        Assert.assertEquals("Оформить заказ", mainPage.getTextAuthCheckoutBtn());
    }

    @Test
    @DisplayName("Авторизация пользователя - через «Личный кабинет»")
    public void shouldLoginFromPersonalAreaPage() {
        mainPage.waitLoadMainPages();
        mainPage.clickToPersonalAreaBtn();
        authorization.getAuthorization(email, password);
        Assert.assertEquals("Оформить заказ", mainPage.getTextAuthCheckoutBtn());
    }

    @Test
    @DisplayName("Авторизация пользователя - через форму регистрации")
    public void shouldLoginFromRegistrationForm() {
        mainPage.waitLoadMainPages();
        mainPage.clickToLoginBtn();
        authorization.clickToRegistrationBtn();
        registration.clickToLoginBtn();
        authorization.getAuthorization(email, password);
        Assert.assertEquals("Оформить заказ", mainPage.getTextAuthCheckoutBtn());
    }

    @Test
    @DisplayName("Авторизация пользователя - через форму восстановления пароля.")
    public void shouldLoginFromRestoreForm() {
        mainPage.waitLoadMainPages();
        mainPage.clickToLoginBtn();
        authorization.clickToRestoreBtn();
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