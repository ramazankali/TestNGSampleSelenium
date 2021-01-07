package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePage {

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "button[@id='onetrust-accept-btn-handler']")
    public WebElement cookieAcceptBtn;


    @FindBy(xpath = "//*[@id=\"onetrust-close-btn-container\"]/button")
    public WebElement cookieXButton;
    //div/div/div[6]/button
//    @FindBy(xpath = "//div/div/div[6]/button[@class='navbar-toggler collapsed']")
    @FindBy(xpath = "/html/body/nav/div/div/div[6]/button")
    public WebElement hamburgerMenu;



    //*[@id='header-nav']/nav/div/div[2]/div[2]/div[1]/ul/li[3]/a
    @FindBy(xpath = "//*[@id='header-nav']/nav/div/div[2]/div[2]/div[1]/ul/li[3]/a")
    public WebElement ourPartners;

    @FindBy(xpath = "//ul//li[.text()='Our Partners']")
    public WebElement ourPartners2;

    @FindBy(xpath = "//iframe[@id='MktoForms2XDIFrame']")
    public WebElement iframeCookie;

    public List<Map<String,List<WebElement>>> listOfPartners;

    public void getListOfPartners(){

        String parentXpath="//div[@class='header-offset-content']/div[@class='container'][2]";
        int numberOfPartners=0;
        Map<String,WebElement> partnerData=new HashMap<>();
        boolean doesExist=true;

        while (doesExist){
            String element=parentXpath+"";
            try {
                Driver.waitForVisibility(By.xpath(element),3);
            }catch (NoSuchElementException e)
            {
                System.out.println("The number of Total Partners are :"+numberOfPartners);
            }
        }



    }


}
