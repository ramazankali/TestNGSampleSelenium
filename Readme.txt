- Framework is a TestNG framework assumed that your current framework is a testNG framework by estimation of your codes i received
- You can run the test by running "./XmlRunnerSuites/CrossBrowsertest01.xml" file
- I added a method end of Driver class to save the images from an url which is collected from your partners webelements
- test registers data of partners in 2 ways
    a- 1st method
        a.1- List<Map<String,Object>> listOfAllPartners --> each index is a single partner info
        a.2- Map<String,Object>> map of a single partner K={"header","logo","info"} where header and info is String and "logo" value is an Object --> real png image
    b- 2nd method
        b.1- List<Map<String,WebElement>> listOfAllPartners --> each index is a single partner's webelements
        b.2- Map<String,WebElement>> map of a single partner K={"header","logo","info"} all K--> Values (WebElement)

- I prepared the framework for cross browser testing by entering parameter to xml file
It works fine in chrome but i guess some locators had to be located in a different manner so it can work for all browsers like firefox.
    
  I did not have time for this.
  Hope it works...
