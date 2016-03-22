package tests;

import helpers.ScreenShot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by HP on 22.03.2016.
 */
public class TestRunner {

    private ScreenShot screenShot;
    private WebDriver driver = null;
    Connection con = null;
    Statement stnt = null;

    private String url = "http://192.168.0.183:5000/showSignUp";
    private String dbUrl = "jdbc:mysql://192.168.0.183:3306/BucketList";

    private String userEmail = "example111122@example.com";
    private String testUserName = "TestUser";
    private String userPass = "123123";
    private String pathToScreenShots = "D://";

    @BeforeMethod
    public void setUp(){
        driver = new FirefoxDriver();
        screenShot = new ScreenShot(driver, pathToScreenShots);
    }

    @AfterMethod
    public void tearDown(ITestResult iTestResult){

        if(iTestResult.getStatus() == ITestResult.FAILURE){
            System.out.println("Test Failure ....");
            screenShot.takeScreenShot();
        }

        driver.quit();

    }

    @Test
    public void insertTestData(){
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"btnSignUp\"]"));
        WebElement inputName = driver.findElement(By.id("inputName"));
        WebElement inputPass = driver.findElement(By.id("inputPassword"));
        WebElement inputEmail = driver.findElement(By.id("inputEmail"));

        inputName.sendKeys(testUserName);
        inputEmail.sendKeys(userEmail);
        inputPass.sendKeys(userPass);

        submit.click();

    }

    @Test
    public void dataBaseTest() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(dbUrl,"root","12345");
        stnt = con.createStatement();

        String query = "SELECT * FROM tbl_user;";
        String clearTestData = "DELETE FROM tbl_user WHERE user_id >='2';";

        ResultSet result = stnt.executeQuery(query);
        while (result.next()){
            int id = result.getInt("user_id");
            String userName = result.getString("user_name");
            String userEmail = result.getString("user_username");

            System.out.println("id: " + id);
            System.out.println("User Name: " + userName);
            System.out.println("User Email: " + userEmail);
            System.out.println("/n");

            if (id>1){
                Assert.assertEquals(userName, testUserName);

            }

        }
        stnt.executeUpdate(clearTestData);
        result.close();
    }

}
