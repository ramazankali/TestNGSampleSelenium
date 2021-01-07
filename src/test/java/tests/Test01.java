package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.OurPartners;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ExcelUtil;
import utilities.TestBase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Test01 extends TestBase {

    @Test(dataProvider="partners")
    public void getPartnersInfo(String name){
        //This signatures added just to increase the test case number to visualize to be able to run with xml suite for parallel testing
        System.out.printf("This test is : %s\n",name);
        driver.get(ConfigReader.getProperty("main_url"));

        OurPartners ourPartners=new OurPartners(driver);
        HomePage homePage=new HomePage(driver);

        homePage.hamburgerMenu.click();

        //clicks to hamburger menu
        Driver.clickWithJS(homePage.ourPartners);
        Driver.waitForPageToLoad(3);

        //Just to be sure page loaded !

        /* This method gets All partners data header, logo as a picture in a map and partner info as a text
         * "header"="text" , "logo"=object (logo.png , "info"="partner info"        */
          ourPartners.
                getListOfPartnersData(driver).
                  stream().
                  map(partner->
                  String.format("Header: %s, Logo: %s, Info: %s",
                          partner.get("header"),
                          partner.get("logo").toString(),
                          partner.get("info"))).
                forEach(System.out::println);

        /* This method gets All partners web element data as ListOfWebElements
         * This records data as WebElement
         * "header"=headerWebelement, "logo=logoWebElement , "info"= infoWebElement         */
        ourPartners.
                getListOfPartners(driver).
                stream().
                map(partner->
                        String.format("Header: %s, Logo: %s, Info: %s",
                                partner.get("header").getText(),
                                partner.get("logo").getAttribute("src"),
                                partner.get("info").getText())).
                forEach(System.out::println);

    }

    @DataProvider(name="partners")
    public Object[] getData() throws IOException {
        // This had been added just to apply paralllel testing
        Object[] obj = new Object[3];
        obj[0]="test1";
        obj[1]="test2";
        obj[2]="test3";

        return obj;

    }

}
