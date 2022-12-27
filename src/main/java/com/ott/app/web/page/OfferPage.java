package com.ott.app.web.page;
import com.ott.app.web.annotation.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Page
public class OfferPage extends BasePage {

    @FindBy(id = "email-mobile")
    private WebElement email;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "sub_form")
    private WebElement offerBtn;


    public void setEmail(String Email){
        email.sendKeys(Email);
    }

    public void setPassword(String Password){
        password.sendKeys(Password);
    }

    public void tapOfferbtn(){
        offerBtn.click();
    }

    @Override
    public boolean isAt() {
        return false;
    }
}
