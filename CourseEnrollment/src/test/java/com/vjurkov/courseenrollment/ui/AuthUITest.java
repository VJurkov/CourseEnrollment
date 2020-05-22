package com.vjurkov.courseenrollment.ui;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class AuthUITest extends SeleniumBaseTests {

    @Test
    public void loginTest() {
        this.driver.get("http://localhost:8081/login");
        WebElement username = this.driver.findElement(By.id("username"));
        WebElement password = this.driver.findElement(By.id("password"));
        WebElement login = this.driver.findElement(By.id("login"));
        assertThat(username != null);
        username.sendKeys("MarkoMaric");
        assertThat(password != null);
        password.sendKeys("password");
        assertThat(login != null);
        login.click();
        assertThat(this.driver.getCurrentUrl().contains("home"));
    }
    @Test
    public void loginFailTest() {
        this.driver.get("http://localhost:8081/login");
        WebElement username = this.driver.findElement(By.id("username"));
        WebElement password = this.driver.findElement(By.id("password"));
        WebElement login = this.driver.findElement(By.id("login"));
        assertThat(username != null);
        username.sendKeys("MarkoMaric");
        assertThat(password != null);
        password.sendKeys("passwordWrong");
        assertThat(login != null);
        login.click();

        WebElement errorMessage = this.driver.findElement(By.id("loginError"));
        assertThat(errorMessage != null);
    }
}
