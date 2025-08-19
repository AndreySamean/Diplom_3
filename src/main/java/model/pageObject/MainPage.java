package model.pageObject;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.ClickOptions.usingJavaScript;
import static com.codeborne.selenide.Condition.interactable;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    private final SelenideElement loginToAccountButton =
            $x("//button[text()='Войти в аккаунт']");
    private final SelenideElement placeAnOrderButton =
            $x("//button[text()='Оформить заказ']");
    private final SelenideElement bunButton =
            $x("//span[text()='Булки']");
    private final SelenideElement sauceButton =
            $x("//span[text()='Соусы']");
    private final SelenideElement fillingButton =
            $x("//span[text()='Начинки']");
    private final SelenideElement bunText =
            $x("//h2[text()='Булки']");
    private final SelenideElement sauceText =
            $x("//h2[text()='Начинки']");
    private final SelenideElement fillingText =
            $x("//h2[text()='Соусы']");

    @Step("Нажать кнопку Войти")
    public void clickLoginToAccountButton(){
        loginToAccountButton.click();
    }

    @Step("Нажать кнопку Булки")
    public void clickBunButton(){
        bunButton.shouldBe(interactable)
                .click(usingJavaScript());
    }

    @Step("Нажать кнопку Соусы")
    public void clickSauceButton(){
        sauceButton.shouldBe(interactable)
                .click(usingJavaScript());
    }

    @Step("Нажать кнопку Начинки")
    public void clickFillingButton(){
        fillingButton.shouldBe(interactable)
                .click(usingJavaScript());
    }

    @Step("Поле Булки отображается")
    public boolean isBunTextDisplayed(){
        return bunText.isDisplayed();
    }

    @Step("Поле Соусы отображается")
    public boolean isSauceTextDisplayed(){
        return sauceText.isDisplayed();
    }

    @Step("Поле Начинки отображается")
    public boolean isFillingButtonDisplayed(){
        return fillingText.isDisplayed();
    }

    @Step("Кнопка Оформить заказ отображается")
    public boolean isPlaceAnOrderButtonEnabled(){
        return placeAnOrderButton.isEnabled();
    }

}
