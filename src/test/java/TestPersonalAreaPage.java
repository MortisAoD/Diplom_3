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

import ui.pages.*;

import static api.functions.Utilities.fromJsonString;
import static api.functions.FunctionsUserRequest.getUserDelete;

@RunWith(Parameterized.class)
public class TestPersonalAreaPage extends Utilities {

    private final String name;
    private final String email;
    private final String password;
    private UserResponseModel response;
    MainPage mainPage;
    AuthorizationPage authorization;
    RegistrationPage registration;
    RestorePage restorePage;
    PersonalAreaPage personalArea;

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
        personalArea = new PersonalAreaPage(driver);
    }

    @Test
    @DisplayName("Переход в личный кабинет по клику на «Личный кабинет»")
    public void shouldPersonalAreaForm() {
        mainPage.waitLoadMainPages();
        mainPage.clickToPersonalAreaBtn();
        authorization.getAuthorization(email, password);
        mainPage.waitLoadMainPages();
        mainPage.clickToPersonalAreaBtn();
        Assert.assertEquals("Профиль",  personalArea.personalAreaProfileBtn());
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор")
    public void shouldPersonalAreaFromConstructor() {
        mainPage.waitLoadMainPages();
        mainPage.clickToPersonalAreaBtn();
        authorization.getAuthorization(email, password);
        mainPage.clickToPersonalAreaBtn();
        personalArea.clickToConstructorBtn();
        Assert.assertEquals("Соберите бургер", mainPage.getTextHeaderConstructor());
    }

    @Test
    @DisplayName("Переход из личного кабинет по клику на логотип")
    public void shouldPersonalAreaFormLogo() {
        mainPage.waitLoadMainPages();
        mainPage.clickToPersonalAreaBtn();
        authorization.getAuthorization(email, password);
        mainPage.clickToPersonalAreaBtn();
        personalArea.clickToLogoBtn();
        Assert.assertEquals("Соберите бургер", mainPage.getTextHeaderConstructor());
    }

    @Test
    @DisplayName("Переход в личный кабинет - по клику на кнопку Выход")
    public void shouldPersonalAreaSignOut() {
        mainPage.waitLoadMainPages();
        mainPage.clickToPersonalAreaBtn();
        authorization.getAuthorization(email, password);
        mainPage.clickToPersonalAreaBtn();
        personalArea.clickToSignOut();
        Assert.assertEquals("Войти", authorization.getTextAuthLoginBtn());
    }

    @After
    public void teardown() {
        driver.quit();
        getUserDelete(response.getAccessToken());
    }
}