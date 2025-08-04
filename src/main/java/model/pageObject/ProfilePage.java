package model.pageObject;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class ProfilePage {

    private final SelenideElement quitButton = $x("//button[text()='Выход']");

    @Step("Нажать кнопку Выход")
    public void clickQuitButton(){
        quitButton.click();
    }

    @Step("Кнопка Выход отображается")
    public boolean isQuitButtonEnabled(){
        return quitButton.isEnabled();
    }
}
