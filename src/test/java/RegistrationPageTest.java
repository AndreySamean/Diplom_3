import api.client.UserClient;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import api.User;
import model.pageObject.RegistrationPage;
import model.settings.BrowserConfig;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.url;
import static model.constants.UserCredentials.*;
import static model.pageObject.constants.StartURLs.*;
import static model.settings.Browser.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты страницы регистрации")
public class RegistrationPageTest {
    private static UserClient client;
    private RegistrationPage registrationPage;
    private static User user = User.builder().email(USER_EMAIL)
            .password(USER_PASSWORD).name(USER_NAME).build();

    @BeforeAll
    public static void creatingUser(){
        client = new UserClient();
        BrowserConfig.setupBrowser(CHROME);
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }

    @AfterAll
    public static void deleteUser(){
        Response response = client.loginUser(user);
        String bearerToken = client.getBearerToken(response);
        try {
            client.deleteUser(bearerToken);
        } catch (IllegalArgumentException e) {
            System.out.println("Такого пользователя не существует");
        }
    }

    @Test
    @DisplayName("Регистрация пользователя - успех")
    public void registration_validCredentials_success(){
        registrationPage = open(REGISTRATION_PAGE, RegistrationPage.class);
        registrationPage.registerUser(user.getName(), user.getEmail(), user.getPassword());

        new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(1))
                .until(ExpectedConditions.urlContains(LOGIN_PAGE));

        Allure.step("Произошел переход на страницу входа",
                () -> assertEquals(LOGIN_PAGE, url()));
    }


    @Test
    @DisplayName("Регистрация - некорректный пароль - ошибка")
    public void registration_incorrectPassword_expectError(){
        registrationPage = open(REGISTRATION_PAGE, RegistrationPage.class);
        registrationPage.registerUser(user.getName(), user.getEmail(), INCORRECT_PASSWORD);

        assertTrue(registrationPage.isErrorDisplayed());
    }
}
