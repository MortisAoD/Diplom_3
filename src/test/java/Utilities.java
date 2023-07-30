import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Utilities {

    public WebDriver driver;
    private final String URL = "https://stellarburgers.nomoreparties.site";

    public void getStarted(String browserName) {
        if(browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if(browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if(browserName.equalsIgnoreCase("ie")) {
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
        } else if (browserName.equalsIgnoreCase("ya")) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/yandexdriver.exe");
            driver = new ChromeDriver();
        } else {
            throw new IllegalArgumentException("Invalid browser name: " + browserName);
        }
        driver.get(URL);
    }
}
