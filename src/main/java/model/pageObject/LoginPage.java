package model.pageObject;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {

    private final SelenideElement emailField = $x("//input[@name='name']");
    private final SelenideElement passwordField = $x("//input[@name='Пароль']");
    private final SelenideElement loginButton = $x("//button[text()='Войти']");


    @Step("Ввести email")
    public void setEmail(String email){
        emailField.setValue(email);
    }

    @Step("Ввести пароль")
    public void setPassword(String password){
        passwordField.setValue(password);
    }

    @Step("Нажать на кнопку Войти")
    public void clickLoginButton(){
        loginButton.click();
    }

    @Step("Авторизировать пользователя")
    public void loginUser(String email, String password){
        setEmail(email);
        setPassword(password);
        clickLoginButton();
    }

    @Step("Кнопка Войти доступна")
    public boolean loginButtonIsEnabled(){
        return loginButton.isEnabled();
    }
}
