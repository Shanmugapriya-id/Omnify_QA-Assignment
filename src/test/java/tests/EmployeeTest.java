package tests;

import base.BaseTest;
import pages.LoginPage;
import pages.PIMPage;
import pages.EmployeeListPage;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.List;

public class EmployeeTest extends BaseTest {

    @Test
    public void verifyEmployeesInList() {
        LoginPage loginPage = new LoginPage(driver, wait);
        PIMPage pimPage = new PIMPage(driver, wait);
        EmployeeListPage listPage = new EmployeeListPage(driver, wait);

        loginPage.openLoginPage("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        loginPage.login("Admin", "admin123");

        pimPage.goToPIM();
        pimPage.addEmployee("Alex", "S", "John");
        pimPage.addEmployee("Hari", "B", "Babu");
        pimPage.addEmployee("Priya", "Selva", "Kumar");

        pimPage.goToEmployeeList();

        List<String> namesToFind = Arrays.asList("Priya Selva", "Hari B", "Alex S");
        boolean allFound = listPage.searchNamesInAllPages(new java.util.ArrayList<>(namesToFind));

        if (!allFound) {
            System.out.println("❌ Not all employees found in the list!");
        } else {
            System.out.println("✅ All employees found!");
        }
    }
}
