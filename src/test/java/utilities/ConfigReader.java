package utilities;


import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        String path = "configuration.properties";
        try {
            FileInputStream file=new FileInputStream(path);
            properties=new Properties();
            properties.load(file);
            file.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    public static String getProperty(String key){

        System.out.println("This is get property method: ");
        return properties.getProperty(key);
    }
    public static void setProperty(String key,String value){
        properties.setProperty(key, value);
    }

}
