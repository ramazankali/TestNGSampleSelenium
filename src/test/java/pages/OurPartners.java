package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OurPartners {

    public OurPartners(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//main//div[@class='container'][2]")
    public WebElement partnersParentElement;

    @FindBy(xpath = "//main//div[@class='container'][2]/div[@class='row h-100 ']")
    public WebElement testElement;

    @FindBy(xpath = "//main//div[@class='container'][2]//div[@class='row h-100 '][1]")
    public WebElement firstPartner;

    @FindBy(xpath = "//main//div[@class='container'][2]/div[@class='row h-100 '][1]/div[2]/a/h3")
    public WebElement firstPartnerName;

    @FindBy(xpath = "//main//div[@class='container'][2]/div[@class='row h-100 '][1]/div[1]/a/img")
    public WebElement firstPartnerLogo;

    @FindBy(xpath = "//main//div[@class='container'][2]/div[@class='row h-100 '][1]/div[2]/div/p/text()")
    public WebElement firstPartnerInfo;



    /*
     * - Each index of the List is complete data set of a single partner.
     * - Each index has 3 main components keySet--> "header","logo", "info" This method saves "header" and "info" as a text Map<K,V>
     * -  Values are Values -->partner's header(String), logo as an object(BufferedImage) , info (String)
     * - Methods also saves the files as a png file under java/resources/images
     * - You must delete the files before running the test again
    * */
    public List<Map<String, Object>> getListOfPartnersData(WebDriver driver) {

        List<Map<String, Object>> partnersDataList = new ArrayList<>();
        String partnersMainxpath = "//main//div[@class='container'][2]//div[@class='row h-100 ']";

        boolean doesExist = true;
        int partnerNo = 1;

        //starts from 1 to get each partner webelement will return 0 if no elements
        while (doesExist) {

            //try to catch each partner one by one end ends when completed
            try {

                String headerXpath = String.format(
                        "//main//div[@class='container'][2]/div[@class='row h-100 '][%d]/div[2]/a/h3",
                        partnerNo);

                String logoXpath = String.format("//main//div[@class='container'][2]/div[@class='row h-100 '][%d]/div[1]/a/img",
                        partnerNo);

                String infoXpath = String.format(
                        "//main//div[@class='container'][2]/div[@class='row h-100 '][%d]/div[2]/div/p",
                        partnerNo);

                Map<String, Object> dataMap = new HashMap<>();

                //gets the header info into the map
                dataMap.put("header", driver.findElement(By.xpath(headerXpath)).getText());

                //finds the url of the logo
                //Then goes to the url
                // saves the image to images folder + adds it to the map as "logo" keyed object
                dataMap.put("logo", Driver.getTheImage(driver.findElement(By.xpath(logoXpath)).getAttribute("src")));

                //gets and saves the partner info as String
                dataMap.put("info", driver.findElement(By.xpath(infoXpath)).getText());

                //adds the data of the partner to the list as a complete map
                partnersDataList.add(dataMap);

                //increase the partnerNo for the next partner data
                partnerNo++;


            } catch (NoSuchElementException | InterruptedException e) {
                partnerNo--;
                doesExist = false;
                break;
            }

        }

        //Returns and prints the total number of partners in that page

        System.out.println("The total number of partners : "+partnerNo);
        return partnersDataList;
    }

    public List<Map<String, WebElement>> getListOfPartners(WebDriver driver) {

        List<Map<String, WebElement>> partnersDataList = new ArrayList<>();
        String partnersMainxpath = "//main//div[@class='container'][2]//div[@class='row h-100 ']";

        boolean doesExist = true;
        int partnerNo = 1;
        while (doesExist) {

            try {

                String headerXpath = String.format(
                        "%s[%d]/div[2]/a/h3",partnersMainxpath,
                        partnerNo);

                String logoXpath = String.format("%s[%d]/div[1]/a/img",partnersMainxpath,partnerNo);

                String infoXpath = String.format("%s[%d]/div[2]/div/p",partnersMainxpath,partnerNo);

                Map<String, WebElement> elementsMap = new HashMap<>();

                elementsMap.put("header", driver.findElement(By.xpath(headerXpath)));
                elementsMap.put("logo", driver.findElement(By.xpath(logoXpath)));
                elementsMap.put("info", driver.findElement(By.xpath(infoXpath)));

                partnersDataList.add(elementsMap);

                partnerNo++;


            } catch (NoSuchElementException e) {
                partnerNo--;
                doesExist = false;
                break;
            }

        }
        System.out.println(partnersDataList);
        return partnersDataList;
    }
}
