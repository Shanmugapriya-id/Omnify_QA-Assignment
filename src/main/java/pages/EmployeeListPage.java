package pages;

import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmployeeListPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By table = By.cssSelector("div.oxd-table.orangehrm-employee-list");
    private By nameCells = By.xpath("//div[@class='oxd-table orangehrm-employee-list']//div[@role='row']/div[3]");
    private By nextBtn = By.xpath("(//button[contains(@class, 'oxd-pagination-page-item--previous-next')])[last()]");
    private By loadingSpinner = By.cssSelector("div.oxd-table.orangehrm-employee-list + div.oxd-loading-spinner");

    public EmployeeListPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean searchNamesInAllPages(List<String> namesToFind) {
        boolean nextPageExists = true;

        while (nextPageExists) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(table));

            List<WebElement> thirdColumnCells = driver.findElements(nameCells);
            for (WebElement cell : thirdColumnCells) {
                String name = cell.getText().trim();
                if (namesToFind.contains(name)) {
                    System.out.println("Found: " + name);
                    namesToFind.remove(name);                    
                }
                if(namesToFind.isEmpty()) {
                	break;
                }
            }

            try {
                WebElement next = driver.findElement(nextBtn);
                if (next.isDisplayed() && next.isEnabled() && !namesToFind.isEmpty()) {
                    next.click();
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
                } else {
                	//System.out.println("name are removing successfully");
                    nextPageExists = false;
                }
            } catch (NoSuchElementException e) {
                nextPageExists = false;
            }
        }
        return namesToFind.isEmpty();
    }
}

