package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PIMPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By pimMenu = By.xpath("//span[text()='PIM']");
    private By addEmployeeLink = By.xpath("//a[contains(.,'Add Employee')]");
    private By firstNameField = By.name("firstName");
    private By middleNameField = By.name("middleName");
    private By lastNameField = By.name("lastName");
    private By submitBtn = By.xpath("//button[@type='submit']");
    private By loader = By.cssSelector("div.oxd-form-loader");
    private By employeeListLink = By.xpath("//a[text()='Employee List']");

    public PIMPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void goToPIM() {
        wait.until(ExpectedConditions.elementToBeClickable(pimMenu)).click();
    }

    public void addEmployee(String first, String middle, String last) {
        wait.until(ExpectedConditions.elementToBeClickable(addEmployeeLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField)).sendKeys(first);
        driver.findElement(middleNameField).sendKeys(middle);
        driver.findElement(lastNameField).sendKeys(last);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
    }

    public void goToEmployeeList() {
        wait.until(ExpectedConditions.elementToBeClickable(employeeListLink)).click();
    }
}
