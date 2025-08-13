package logindetails;

import java.time.Duration;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;

    @Test
    public void EmployeeDetails() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Login
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button.orangehrm-login-button")).click();

        // Go to PIM
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='PIM']"))).click();

        // Add Employees
        addEmployee("Alex", "S", "John");
        addEmployee("Hari", "B", "Babu");
        addEmployee("Priya", "Selva", "Kumar");
        // Search Employees in Employee List
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Employee List']"))).click();

        List<String> namesToFind = new ArrayList<>(Arrays.asList("Priya Selva", "Hari B", "Alex S"));
        boolean nextPageExists = true;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        while (nextPageExists) {
            // Wait for table to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.oxd-table.orangehrm-employee-list")));

            // Get all 3rd column cells
            List<WebElement> thirdColumnCells = driver.findElements(By.xpath(
                "//div[@class='oxd-table orangehrm-employee-list']//div[@role='row']/div[3]"));

            for (WebElement cell : thirdColumnCells) {

                String name = cell.getText().trim();
                if (namesToFind.contains(name)) {
                    System.out.println("Found: " + name);
                    namesToFind.remove(name);
                }
            }

//            if (namesToFind.isEmpty()) {
//                System.out.println("✅ All names found!");
//                break;
//            }

            // Move to next page if available
            try {
//            	if(!isEmployeeNamePresent) {
            		
            		WebElement nextBtn = driver.findElement(By.xpath("(//button[contains(@class, 'oxd-pagination-page-item oxd-pagination-page-item--previous-next')])[last()]"));
            		//nextBtn.click();
                        if (nextBtn.isDisplayed()) {
                            nextBtn.click();
                            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                                By.cssSelector("div.oxd-table.orangehrm-employee-list + div.oxd-loading-spinner")));
                        } else {
                            nextPageExists = false;
                        }
//            	}
            } catch (NoSuchElementException e) {
                nextPageExists = false;
            }
        }

        if (!namesToFind.isEmpty()) {
            System.out.println("❌ Not found: " + namesToFind);
        }

        driver.quit();
    }

    // Reusable method to add employee
    private void addEmployee(String first, String middle, String last) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(.,'Add Employee')]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstName"))).sendKeys(first);
        driver.findElement(By.name("middleName")).sendKeys(middle);
        driver.findElement(By.name("lastName")).sendKeys(last);

        // Wait for loader to disappear before clicking submit
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.oxd-form-loader")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']"))).click();

        // Wait until confirmation or employee details page loads
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.oxd-form-loader")));

    }
}
