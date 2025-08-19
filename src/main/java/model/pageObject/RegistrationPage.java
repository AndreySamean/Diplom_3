package model.pageObject;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;


public class RegistrationPage {

    private final SelenideElement nameField =
            $x("//div[label[text()='Имя']]/input[@name='name']");
    private final SelenideElement emailField =
            $x("//div[label[text()='Email']]/input[@name='name']");
    private final SelenideElement passwordField =
            $x("//div[label[text()='Пароль']]/input[@name='Пароль']");
    private final SelenideElement registrationButton =
            $x("//button[text()='Зарегистрироваться']");
    private final SelenideElement errorMessage =
            $x("//p[contains(text(),'Некорректный')]");
    private final SelenideElement loginButton =
            $x("//a[text()='Войти']");

    @Step("Ввести имя пользователя")
    public void setName(String username){
        nameField.setValue(username);
    }

    @Step("Ввести email")
    public void setEmail(String email){
        emailField.setValue(email);
    }

    @Step("Ввести пароль")
    public void setPassword(String password){
        passwordField.setValue(password);
    }

    @Step("Нажать кнопку зарегистрироваться")
    public void clickRegistrationButton(){
        registrationButton.click();
    }

    @Step("Зарегистрировать пользователя")
    public void registerUser(String name, String email, String password){
        setName(name);
        setEmail(email);
        setPassword(password);
        clickRegistrationButton();
    }

    @Step("Нажать кнопку Войти")
    public void clickLoginButton(){
        loginButton.click();
    }

    @Step("Сообщение об ошибке отображается")
    public boolean isErrorDisplayed(){
        return errorMessage.isDisplayed();
    }
}
