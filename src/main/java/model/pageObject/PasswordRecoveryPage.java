package model.pageObject;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class PasswordRecoveryPage {

    private final SelenideElement loginButton = $x("//a[text()='Войти']");

    @Step("Нажать кнопку Войти")
    public void clickLoginButton(){
        loginButton.click();
    }
}
