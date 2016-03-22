package helpers;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

/**
 * Created by HP on 22.03.2016.
 */
public class ScreenShot {

    private WebDriver driver;
    private String pathToDir;
    private int screenShotCounter = 0;

    public ScreenShot(WebDriver driver, String pathToDir) {
        this.driver = driver;
        this.pathToDir = pathToDir;
    }

    public void takeScreenShot(){

        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File(this.pathToDir + "screenshot_" + screenShotCounter + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Screen shot Taken");
        System.out.println(file.getName().toString());
        System.out.println(file.getParent().toString());
        System.out.println(FileUtils.getUserDirectoryPath().toString());


        screenShotCounter++;
    }

}