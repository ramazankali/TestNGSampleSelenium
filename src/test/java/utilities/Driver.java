package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Driver {
    private Driver() {
    }

    private static WebDriver driver;

    public static WebDriver getDriver(String browser) {

        if (driver == null) {
            browser= browser ==null ? ConfigReader.getProperty("browser"):browser;
            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver=new FirefoxDriver();
                    break;
                case "ie":
                    WebDriverManager.iedriver().setup();
                    driver=new InternetExplorerDriver();
                    break;
                case "safari":
                    WebDriverManager.getInstance(SafariDriver.class).setup();
                    driver=new SafariDriver();
                    break;
                case "chrome-headless":
                    WebDriverManager.chromedriver().setup();
                    driver=new ChromeDriver(new ChromeOptions().setHeadless(true));
                    break;
            }
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
         return driver;
    }


    public static WebDriver getDriver() {
        return getDriver(null);
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
//            driver.close();

        }
    }

    public static void wait(int secs) {
        try {
            Thread.sleep((long)(1000 * secs));
        } catch (InterruptedException var2) {
            var2.printStackTrace();
        }

    }

    public static void switchToWindow(String targetTitle) {
        String origin = driver.getWindowHandle();
        Iterator var2 = driver.getWindowHandles().iterator();

        do {
            if (!var2.hasNext()) {
                driver.switchTo().window(origin);
                return;
            }

            String handle = (String)var2.next();
            driver.switchTo().window(handle);
        } while(!driver.getTitle().equals(targetTitle));

    }

    public static void hover(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public static List<String> getElementsText(List<WebElement> list) {
        List<String> elemTexts = new ArrayList();
        Iterator var2 = list.iterator();

        while(var2.hasNext()) {
            WebElement el = (WebElement)var2.next();
            elemTexts.add(el.getText());
        }

        return elemTexts;
    }

    public static List<String> getElementsText(By locator) {
        List<WebElement> elems = driver.findElements(locator);
        List<String> elemTexts = new ArrayList();
        Iterator var3 = elems.iterator();

        while(var3.hasNext()) {
            WebElement el = (WebElement)var3.next();
            elemTexts.add(el.getText());
        }

        return elemTexts;
    }

    public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(driver, (long)timeToWaitInSec);
        return (WebElement)wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, (long)timeout);
        return (WebElement)wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static Boolean waitForInVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, (long)timeout);
        return (Boolean)wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickablility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, (long)timeout);
        return (WebElement)wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForClickablility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, (long)timeout);
        return (WebElement)wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForPageToLoad(long timeOutInSeconds) {
        ExpectedCondition expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor)driver).executeScript("return document.readyState", new Object[0]).equals("complete");
            }
        };

        try {
            System.out.println("Waiting for page to load...");
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.until(expectation);
        } catch (Exception var4) {
            System.out.println("Timeout waiting for Page Load Request to complete after " + timeOutInSeconds + " seconds");
            var4.printStackTrace();
        }

    }

    public static WebElement fluentWait(final WebElement webElement, int timeinsec) {
        FluentWait<WebDriver> wait = (new FluentWait(driver)).withTimeout(Duration.ofSeconds((long)timeinsec)).pollingEvery(Duration.ofMillis(500L)).ignoring(NoSuchElementException.class);
        WebElement element = (WebElement)wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return webElement;
            }
        });
        return element;
    }

    public static void verifyElementDisplayed(By by) {
        try {
            Assert.assertTrue("Element not visible: " + by, driver.findElement(by).isDisplayed());
        } catch (NoSuchElementException var2) {
            Assert.fail("Element not found: " + by);
        }

    }

    public static void verifyElementNotDisplayed(By by) {
        try {
            Assert.assertFalse("Element should not be visible: " + by, driver.findElement(by).isDisplayed());
        } catch (NoSuchElementException var2) {
            var2.printStackTrace();
        }

    }

    public static void verifyElementDisplayed(WebElement element) {
        try {
            Assert.assertTrue("Element not visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException var2) {
            Assert.fail("Element not found: " + element);
        }

    }

    public void waitForStaleElement(WebElement element) {
        int y = 0;

        while(y <= 15) {
            if (y == 1) {
                try {
                    element.isDisplayed();
                    break;
                } catch (StaleElementReferenceException var7) {
                    ++y;

                    try {
                        Thread.sleep(300L);
                    } catch (InterruptedException var6) {
                        var6.printStackTrace();
                    }
                } catch (WebDriverException var8) {
                    ++y;

                    try {
                        Thread.sleep(300L);
                    } catch (InterruptedException var5) {
                        var5.printStackTrace();
                    }
                }
            }
        }

    }

    public static WebElement selectRandomTextFromDropdown(Select select) {
        Random random = new Random();
        List<WebElement> weblist = select.getOptions();
        int optionIndex = 1 + random.nextInt(weblist.size() - 1);
        select.selectByIndex(optionIndex);
        return select.getFirstSelectedOption();
    }

    public static boolean selectByVisibleText(WebElement element, String selection) {
        Select select = new Select(element);

        try {
            select.selectByVisibleText(selection);
            return true;
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    public static List<String> getSelectDropDownOptions(WebElement element) {
        Select select = new Select(element);
        return (List)select.getOptions().stream().map((t) -> {
            return t.getText();
        }).collect(Collectors.toList());
    }

    public static String getFirstSelectedOption(WebElement element) {
        Select select = new Select(element);
        return select.getFirstSelectedOption().getText();
    }

    public static void clickWithJS(WebElement element) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", new Object[]{element});
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", new Object[]{element});
    }

    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", new Object[]{element});
    }

    public static void doubleClick(WebElement element) {
        (new Actions(driver)).doubleClick(element).build().perform();
    }

    public static void setAttribute(WebElement element, String attributeName, String attributeValue) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", new Object[]{element, attributeName, attributeValue});
    }

    public static void selectCheckBox(WebElement element, boolean check) {
        if (check) {
            if (!element.isSelected()) {
                element.click();
            }
        } else if (element.isSelected()) {
            element.click();
        }

    }

    public static void clickWithTimeOut(WebElement element, int timeout) {
        int i = 0;

        while(i < timeout) {
            try {
                element.click();
                return;
            } catch (WebDriverException var4) {
                wait(1);
                ++i;
            }
        }

    }

    public static void executeJScommand(WebElement element, String command) {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript(command, new Object[]{element});
    }

    public static void executeJScommand(String command) {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript(command, new Object[0]);
    }

    public static void openNewTab() {
        executeJScommand("window.open()");
    }

    public static String getScreenshot(String name) throws IOException {
        String date = (new SimpleDateFormat("yyyyMMddhhmmss")).format(new Date());
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = (File)ts.getScreenshotAs(OutputType.FILE);
        String target = System.getProperty("user.dir") + "/test-output/Screenshots/" + name + date + ".png";
        File finalDestination = new File(target);
        FileUtils.copyFile(source, finalDestination);
        return target;
    }

/***************************************************************************************************************************/
    //This method gets an image from an URL and saves it to the images
    public static BufferedImage  getTheImage(String url) throws InterruptedException {
        String mainHandle=driver.getWindowHandle();
        Actions actions=new Actions(driver);
        //opens a new tab to get the image

        executeJScommand("window.open('"+url+"', '_blank')");

        try {
            //This part captures the image and saves in the new tab
            URL imageURL = new URL(url);
            BufferedImage saveImage = ImageIO.read(imageURL);

            //this part forms the name of the logo to be saved
            StringBuilder logoname=new StringBuilder();
            logoname.append("src/test/resources/images/").                               //file location
                    append(System.currentTimeMillis()).                                   //to prevent file exists errors while saving
                    append(url.substring(url.lastIndexOf("/")+1,url.length()));      //getting logonamefrom original link

            ImageIO.write(saveImage, "png", new File(logoname.toString()));

            return saveImage;

        } catch (Exception e) {
            System.out.println("Image Buffering did not succeed!Image Url might not be found!");
            e.printStackTrace();
            return null;
        } finally {

            //This closes the new tab
            Set<String> windowhandles= driver.getWindowHandles();
            for (String handle:windowhandles){
                driver.switchTo().window(handle);
               if(!handle.equals(mainHandle)){
                   driver.close();
               }
            }
            //driver is switched to main url page for the next image
            driver.switchTo().window(mainHandle);
        }


    }
}
