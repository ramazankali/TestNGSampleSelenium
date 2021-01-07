package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

public class TestBase {

    public TestBase()  {
    }

    protected WebDriver driver;

    @Parameters("browser")
    @BeforeMethod (alwaysRun = true)
    public void setUp (@Optional String browser) {
        driver= Driver.getDriver(browser);

        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        WebDriverWait wait =new WebDriverWait(driver,10);
        Actions actions=new Actions(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        Driver.closeDriver();
    }
}
