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

@DisplayName("Тесты страницы личного кабинета")
public class ProfilePageTest {

    private static UserClient client;
    private static Response response;
    private static User user = User.builder().email(USER_EMAIL)
            .password(USER_PASSWORD).name(USER_NAME).build();

    private static String bearerToken;

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
        bearerToken = client.getBearerToken(response);
        try {
            client.deleteUser(bearerToken);
        } catch (IllegalArgumentException e) {
            System.out.println("Такого пользователя не существует");
        }
    }

    @Test
    @DisplayName("Переход по клику на «Личный кабинет»")
    public void goToPersonalAccountPage_APILogin_success(){
        response = client.loginUser(user);
        bearerToken = client.getBearerToken(response);
        open(REGISTRATION_PAGE);
        Selenide.executeJavaScript(
                "localStorage.setItem('accessToken', '" + bearerToken + "');"
        );
        open(MAIN_PAGE);
        Header header = new Header();
        header.clickProfileButton();

        ProfilePage profilePage = new ProfilePage();
        assertTrue(profilePage.isQuitButtonEnabled());
    }

    @Test
    @DisplayName("Выход по кнопке «Выйти» в личном кабинете")
    public void profileLogOut_UILogin_success(){
        open(LOGIN_PAGE);

        LoginPage loginPage = new LoginPage();
        loginPage.loginUser(user.getEmail(), user.getPassword());

        Header header = new Header();
        header.clickProfileButton();

        ProfilePage profilePage = new ProfilePage();
        profilePage.clickQuitButton();

        assertTrue(loginPage.loginButtonIsEnabled());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("dataProvider")
    @DisplayName("Переход по клику на «Конструктор» и на логотип Stellar Burgers")
    public void goToMainPage_buttonParameterUILogin_success(String description, Runnable action){
        open(LOGIN_PAGE);

        LoginPage loginPage = new LoginPage();
        loginPage.loginUser(user.getEmail(), user.getPassword());

        Header header = new Header();
        header.clickProfileButton();

        action.run();

        MainPage mainPage = new MainPage();
        assertTrue(mainPage.isPlaceAnOrderButtonEnabled());
    }

    @Test
    @DisplayName("Выход по кнопке «Выйти» в личном кабинете")
    @Disabled("PROFILE_PAGE: страница не открывается по прямой ссылке даже вручную")
    public void profileLogOut_APILogin_success(){
        response = client.loginUser(user);
        bearerToken = client.getBearerToken(response);
        open(REGISTRATION_PAGE);
        Selenide.executeJavaScript(
                "localStorage.setItem('accessToken', '" + bearerToken + "');"
        );
        open(PROFILE_PAGE);

        ProfilePage profilePage = new ProfilePage();
        profilePage.clickQuitButton();

        LoginPage loginPage = new LoginPage();
        assertTrue(loginPage.loginButtonIsEnabled());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("dataProvider")
    @DisplayName("Переход по клику на «Конструктор» и на логотип Stellar Burgers")
    @Disabled("PROFILE_PAGE: страница не открывается по прямой ссылке даже вручную")
    public void goToMainPage_buttonParameterAPILogin_success(String description, Runnable action){
        open(PROFILE_PAGE);
        action.run();

        MainPage mainPage = new MainPage();
        assertTrue(mainPage.isPlaceAnOrderButtonEnabled());
    }

    static Stream<Arguments> dataProvider(){
        return Stream.of(
                Arguments.of("Переход по клику на логотип Stellar Burgers",
                        (Runnable) () -> new Header().clickLogo()),
                Arguments.of("Переход по клику на «Конструктор»",
                        (Runnable) () -> new Header().clickConstructorButton())
        );
    }
}
