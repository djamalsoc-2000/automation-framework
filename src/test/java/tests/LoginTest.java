package tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.ProductsPage;
import utilities.ConfigReader;
import utilities.WaitUtils;

@Listeners(listeners.TestListener.class)
public class LoginTest extends BaseTest {

    @Test
    public void testInvalidLogin() {
        driver.get(ConfigReader.get("url"));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(ConfigReader.get("invalidUsername"));
        loginPage.enterPassword(ConfigReader.get("invalidPassword"));
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed for invalid login");
    }

    @Test
    public void testValidLogin() {
        driver.get(ConfigReader.get("url"));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(ConfigReader.get("validUsername"));
        loginPage.enterPassword(ConfigReader.get("validPassword"));
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isOnProductsPage(), "User should land on products page after valid login");
    }

    @Test(dataProvider = "loginData", dataProviderClass = utilities.DataProviderUtil.class)
    public void testLoginWithMultipleUsers(String username, String password, String expectedResult) {
        driver.get(ConfigReader.get("url"));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        if (expectedResult.equals("success")) {
            Assert.assertTrue(loginPage.isOnProductsPage(), "Expected successful login for user: " + username);
        } else {
            Assert.assertTrue(loginPage.isErrorDisplayed(), "Expected error message for user: " + username);
        }
    }

    @Test
    public void testAddToCartWithWaits() {
        driver.get(ConfigReader.get("url"));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(ConfigReader.get("validUsername"));
        loginPage.enterPassword(ConfigReader.get("validPassword"));
        loginPage.clickLogin();

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addFirstItemToCart();

        Assert.assertTrue(
            WaitUtils.explicitWaitForVisibility(driver, productsPage.getCartBadgeLocator(), 5).isDisplayed(),
            "Cart badge should appear after adding item (explicit wait)"
        );
    }

    @Test
    public void testAddToCartWithFluentWait() {
        driver.get(ConfigReader.get("url"));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(ConfigReader.get("validUsername"));
        loginPage.enterPassword(ConfigReader.get("validPassword"));
        loginPage.clickLogin();

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addFirstItemToCart();

        Assert.assertTrue(
            WaitUtils.fluentWaitForVisibility(driver, productsPage.getCartBadgeLocator(), 5, 500).isDisplayed(),
            "Cart badge should appear after adding item (fluent wait)"
        );
    }
}