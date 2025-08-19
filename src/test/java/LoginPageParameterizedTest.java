import api.User;
import api.client.UserClient;
import com.codeborne.selenide.Selenide;
import io.restassured.response.Response;
import model.pageObject.*;
import model.settings.BrowserConfig;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.open;
import static model.constants.UserCredentials.*;
import static model.pageObject.constants.StartURLs.*;
import static model.settings.Browser.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты на вход в аккаунт")
public class LoginPageParameterizedTest {

    private static UserClient client;
    private static Response response;
    private static User user = User.builder().email(USER_EMAIL)
            .password(USER_PASSWORD).name(USER_NAME).build();

    @BeforeAll
    public static void creatingUser(){
        client = new UserClient();
        BrowserConfig.setupBrowser(CHROME);
        response = client.createUser(user);
        if (response.statusCode() != 200){
            throw new IllegalStateException("Пользователь не создан. Код: " + response.statusCode());
        }
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }

    @AfterAll
    public static void deleteUser(){
        String bearerToken = client.getBearerToken(response);
        try {
            client.deleteUser(bearerToken);
        } catch (IllegalArgumentException e) {
            System.out.println("Такого пользователя не существует");
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("dataProvider")
    @DisplayName("Тест на вход через разные точки входа")
    public void loginUser_entryPointParameters_success(String description, String entryPoint, Runnable goToLoginPage){

        open(entryPoint);

        goToLoginPage.run();

        LoginPage loginPage = new LoginPage();
        loginPage.loginUser(user.getEmail(), user.getPassword());

        MainPage mainPage = new MainPage();
        assertTrue(mainPage.isPlaceAnOrderButtonEnabled());
    }

    static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of("Вход через кнопку в форме регистрации", REGISTRATION_PAGE, (Runnable) () -> {
                    RegistrationPage registrationPage = new RegistrationPage();
                    registrationPage.clickLoginButton();
                }),
                Arguments.of("Вход по кнопке «Войти в аккаунт» на главной", MAIN_PAGE, (Runnable) () -> {
                    MainPage mainPage = new MainPage();
                    mainPage.clickLoginToAccountButton();
                }),
                Arguments.of("Вход через кнопку «Личный кабинет»", MAIN_PAGE, (Runnable) () -> {
                    Header header = new Header();
                    header.clickProfileButton();
                }),
                Arguments.of("Вход через кнопку в форме восстановления пароля", PASSWORD_RECOVERY_PAGE, (Runnable) () -> {
                    PasswordRecoveryPage passwordRecoveryPage = new PasswordRecoveryPage();
                    passwordRecoveryPage.clickLoginButton();
                })
        );
    }
}
