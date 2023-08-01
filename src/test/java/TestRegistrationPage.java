import api.functions.FunctionsUserRequest;
import api.request.Request;
import api.models.response.UserResponseModel;

import io.qameta.allure.junit4.DisplayName;

import org.junit.Test;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import ui.pages.*;

import static api.functions.FunctionsUserRequest.getUserDelete;
import static api.functions.Utilities.fromJsonString;

@RunWith(Parameterized.class)
public class TestRegistrationPage extends Utilities {

    private final String name;
    private final String email;
    private final String password;
    MainPage mainPage;
    AuthorizationPage authorization;
    RegistrationPage registration;
    RestorePage restorePage;
    PersonalAreaPage personalArea;
    FunctionsUserRequest userLogin;

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

    @Before
    public void setConfig() {
        getStarted("ya");
        Request request = new Request();
        request.apiEndPoint();
        mainPage = new MainPage(driver);
        authorization = new AuthorizationPage(driver);
        registration = new RegistrationPage(driver);
        restorePage = new RestorePage(driver);
        personalArea = new PersonalAreaPage(driver);
        userLogin = new FunctionsUserRequest();
    }

    @Test
    @DisplayName("Регистрация пользователя - 200 ОК")
    public void shouldRegistration() {
        mainPage.waitLoadMainPages();
        mainPage.clickToLoginBtn();
        authorization.clickToRegistrationBtn();
        registration.getRegistration(name, email, password);
        UserResponseModel responseLogin = fromJsonString(userLogin.getUserLogin(email, password, 200),
                UserResponseModel.class);
        getUserDelete(responseLogin.getAccessToken());
        Assert.assertEquals("Войти", authorization.getTextAuthLoginBtn());
    }

    @Test
    @DisplayName("Регистрация пользователя - некорректный пароль")
    public void shouldUnValidRegistration() {
        mainPage.waitLoadMainPages();
        mainPage.clickToLoginBtn();
        authorization.clickToRegistrationBtn();
        registration.getRegistration(name, email, password.replace("12345P@sw0rd!", "123"));
        Assert.assertEquals("Некорректный пароль", registration.getErrorTextRegistrationForm());
    }

    @After
    public void teardown() {
        driver.quit();
    }
}