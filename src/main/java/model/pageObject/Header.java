package model.pageObject;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class Header {

    private final SelenideElement profileButton =
            $x("//p[text()='Личный Кабинет']");
    private final SelenideElement stellarBurgersLogo =
            $x("//div[@class='AppHeader_header__logo__2D0X2']");
    private final SelenideElement constructorButton =
            $x("//p[text()='Конструктор']");


    @Step("Нажать на кнопку Личный кабинет")
    public void clickProfileButton(){
        profileButton.click();
    }

    @Step("Нажать на логотип Stellar Burgers")
    public void clickLogo(){
        stellarBurgersLogo.click();
    }

    @Step("Нажать на кнопку «Конструктор»")
    public void clickConstructorButton(){
        constructorButton.click();
    }
}
