package tsum;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SerenityRunner.class)
public class TsumTestCases extends PageObject {

    private String passwordRegFormXpath = "/html/body/app-root/div/full-layout/div/div/auth-layout/div/div[4]/auth-register/form/div[2]/input";
    private String loginRegFormXpath = "/html/body/app-root/div/full-layout/div/div/auth-layout/div/div[4]/auth-register/form/div[1]/input";
    private String registrationRegButtonXpath = "/html/body/app-root/div/full-layout/div/div/auth-layout/div/div[4]/auth-register/form/div[4]/button";
    private String loginRevealXpath = "/html/body/app-root/div/full-layout/div/header/div/div[1]/div[2]/div/div/a";
    private String revealIncorrectEmailXpath = "/html/body/app-root/div/full-layout/div/div/auth-layout/div/div[1]/notices/div/notice/div/span";
    private String invalidMail = "Пользователь с таким email уже существует";

    private String loginAuthFormXpath = "/html/body/app-root/div/full-layout/div/div/auth-layout/div/div[4]/auth-login/form/div[1]/input";
    private String passwordLoginFormXpath = "/html/body/app-root/div/full-layout/div/div/auth-layout/div/div[4]/auth-login/form/div[2]/input";
    private String loginAuthButtonXpath = "/html/body/app-root/div/full-layout/div/div/auth-layout/div/div[4]/auth-login/form/div[3]/button";
    private String revealInvalidLoginXpath = "/html/body/app-root/div/full-layout/div/div/auth-layout/div/div[1]/notices/div/notice/div/span";
    private String invalidPassword = "Неверный логин или пароль";


    private String testEmail = "test@tisting.ru";
    private String testPassword = "1234567890";

    @Managed()
    WebDriver driver;

    @Test
    public void TestRegistration() {
        driver.get("https://www.tsum.ru/registration");
        String testRegMail = GetDate()+testEmail;
        driver.findElement(By.xpath(loginRegFormXpath)).sendKeys(testRegMail);
        driver.findElement(By.xpath(passwordRegFormXpath)).sendKeys(testPassword);
        clickOn(driver.findElement(By.xpath(registrationRegButtonXpath)));
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.get("https://www.tsum.ru/");
        driver.manage().window().maximize();
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(driver.findElement(By.xpath(loginRevealXpath)).getText().contains(testRegMail));
    }

    @Test
    public void TestLogin() {
        driver.get("https://www.tsum.ru/login");
        driver.findElement(By.xpath(loginAuthFormXpath)).sendKeys(testEmail);
        driver.findElement(By.xpath(passwordLoginFormXpath)).sendKeys(testPassword);
        clickOn(driver.findElement(By.xpath(loginAuthButtonXpath)));
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.get("https://www.tsum.ru/");
        driver.manage().window().maximize();
        try {
            sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(driver.findElement(By.xpath(loginRevealXpath)).getText()).contains(testEmail);

    }

    @Test
    public void TestLoginWithNonExistingEmail() {
        driver.get("https://www.tsum.ru/login");
        String testRegMail = GetDate()+testEmail;
        driver.findElement(By.xpath(loginAuthFormXpath)).sendKeys(testRegMail);
        driver.findElement(By.xpath(passwordLoginFormXpath)).sendKeys(testPassword);
        clickOn(driver.findElement(By.xpath(loginAuthButtonXpath)));
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(driver.findElement(By.xpath(revealInvalidLoginXpath)).getText()).contains(invalidPassword);
    }

    @Test
    public void TestExistigMailRegistration(){
        driver.get("https://www.tsum.ru/registration");
        driver.findElement(By.xpath(loginRegFormXpath)).sendKeys(testEmail);
        driver.findElement(By.xpath(passwordRegFormXpath)).sendKeys(testPassword);
        clickOn(driver.findElement(By.xpath(registrationRegButtonXpath)));
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(driver.findElement(By.xpath(revealIncorrectEmailXpath)).getText()).contains(invalidMail);

    }

    public String GetDate (){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

}