import model.pageObject.MainPage;
import model.settings.BrowserConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.open;
import static model.pageObject.constants.StartURLs.MAIN_PAGE;
import static model.settings.Browser.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты главной страницы (Конструктор)")
public class MainPageParameterizedTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("actionProvider")
    @DisplayName("Проверка перехода к разделам")
    public void clickToButton_parameterizedButtons_displaysSection_success(
            String description,
            Consumer<MainPage> action,
            Predicate<MainPage> checkResult) {

        BrowserConfig.setupBrowser(CHROME);
        MainPage mainPage = open(MAIN_PAGE, MainPage.class);

        action.accept(mainPage);
        assertTrue(checkResult.test(mainPage));
    }

    static Stream<Arguments> actionProvider() {
        return Stream.of(
                Arguments.of(
                        "Проверка раздела 'Булки'",
                        (Consumer<MainPage>) MainPage::clickBunButton,
                        (Predicate<MainPage>) MainPage::isBunTextDisplayed),
                Arguments.of(
                        "Проверка раздела 'Соусы'",
                        (Consumer<MainPage>) MainPage::clickSauceButton,
                        (Predicate<MainPage>) MainPage::isSauceTextDisplayed),
                Arguments.of("Проверка раздела 'Начинки'",
                        (Consumer<MainPage>) MainPage::clickFillingButton,
                        (Predicate<MainPage>) MainPage::isFillingButtonDisplayed)
        );
    }
}
