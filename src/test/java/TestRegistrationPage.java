import api.request.Request;
import api.functions.FunctionsUserLogin;
import api.models.response.UserResponseModel;

import io.qameta.allure.junit4.DisplayName;

import org.junit.Test;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import ui.pages.MainPage;
import ui.pages.RegistrationPage;
import ui.pages.AuthorizationPage;

import static api.functions.Utilities.deserialize;
import static api.functions.FunctionsUserDelete.getUserDelete;

@RunWith(Parameterized.class)
public class TestRegistrationPage extends Utilities {

    @Before
    public void setConfig() {
        getStarted("ya");
        Request request = new Request();
        request.apiEndPoint();
    }
    private final String name;
    private final String email;
    private final String password;


    public TestRegistrationPage(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Parameters(name = "Тестовые данные: {0},{1},{2}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"TestQAUser", "TestsQAUser123@yandex.ru", "12345P@sw0rd!"}
        };
    }

    @Test
    @DisplayName("Регистрация пользователя - 200 ОК")
    public void shouldRegistration() {
        MainPage mainPage = new MainPage(driver);
        mainPage.waitLoadMainPages();
        mainPage.clickToLoginBtn();

        AuthorizationPage authorization = new AuthorizationPage(driver);
        authorization.clickToRegistrationBtn();

        RegistrationPage registration = new RegistrationPage(driver);
        registration.getRegistration(name, email, password);

        FunctionsUserLogin userLogin = new FunctionsUserLogin();
        UserResponseModel responseLogin = deserialize(userLogin.getUserLogin(email, password, 200),
                UserResponseModel.class);

        getUserDelete(responseLogin.getAccessToken());

        Assert.assertEquals("Войти", authorization.getTextAuthLoginBtn());
    }

    @Test
    @DisplayName("Регистрация пользователя - некорректный пароль")
    public void shouldUnValidRegistration() {
        MainPage mainPage = new MainPage(driver);
        mainPage.waitLoadMainPages();
        mainPage.clickToLoginBtn();

        AuthorizationPage authorization = new AuthorizationPage(driver);
        authorization.clickToRegistrationBtn();

        RegistrationPage registration = new RegistrationPage(driver);
        registration.getRegistration(name, email, password.replace("12345P@sw0rd!", "123"));

        Assert.assertEquals("Некорректный пароль", registration.getErrorTextRegistrationForm());
    }

    @After
    public void teardown() {
        driver.quit();
    }
}