package Demo;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import junit.framework.Assert;

public class OrangeHRM {

	public String baseUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
	public WebDriver driver;

	@BeforeTest
	public void setUp() {
		System.out.println("Before test execution");
		// memory alocate of driver object
		driver = new ChromeDriver();
		// maximize window

		driver.manage().window().maximize();
		// open url
		driver.get(baseUrl);
		// implicite wait for 60s
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

	}

	@Test(priority = 2, enabled = false)
	public void validcredentialsLoginTest() throws InterruptedException {
		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");
		driver.findElement(By.xpath("//button[@type = 'submit']")).submit();

		// verify using title name

		String pageTitle = driver.getTitle();
		/*
		 * if(pageTitle.equals("OrangeHRM")) { System.out.println("Login Pass"); } else
		 * { System.out.println("login Failed"); }
		 */
		Assert.assertEquals("OrangeHRM", pageTitle);

		logout();
		Thread.sleep(1500);
	}

	@Test(priority = 1, enabled = false)
	public void invalidcredentialsLoginTest() throws InterruptedException {
		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin32");
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin12323");
		driver.findElement(By.xpath("//button[@type = 'submit']")).submit();
		String expected_message = "Invalid credentials";
		String actual_message = driver
				.findElement(By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")).getText();
		Assert.assertTrue(actual_message.contains(expected_message));

		// Assert.assertEquals(message_expected, message_actual);

		Thread.sleep(1500);
	}

	@Test(priority = 3, enabled = false)
	public void addEmployee() throws InterruptedException {
		logIn();
		// Navigate to the "PIM" menu and click PIM

		driver.findElement(By.xpath("//span[normalize-space()='PIM']")).click();
		// click on Add Employee
		driver.findElement(By.xpath("//a[text()='Add Employee']")).click();
		driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys("Istiak");
		driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys("Ahamed");
		driver.findElement(By.xpath("//button[@type = 'submit']")).click();
		Thread.sleep(1500);
		// Verify if the employee is successfully added by checking the employee list
		// personal details
		String confirmationMessage = driver.findElement(By.xpath("//h6[normalize-space()='Personal Details']"))
				.getText();

		/*
		 * WebElement userid = driver.findElements(By.tagName("input")).get(5); String
		 * id = userid.getAttribute("value"); System.out.println(id +"AFSDF");
		 * Thread.sleep(1500);
		 */

		if (confirmationMessage.contains("Personal Details")) {
			System.out.println("Employee added successfully!");
		} else {
			System.out.println("Failed to add employee!");
		}
		Assert.assertEquals("Personal Details", confirmationMessage);

		logout();

	}

	@Test(priority = 4, enabled = false)
	public void searchEmployeebyName() throws InterruptedException {
		logIn();
		// Navigate to the "PIM" menu and click PIM

		driver.findElement(By.xpath("//span[normalize-space()='PIM']")).click();

		// select "Employee List".

		driver.findElement(By.xpath("//a[normalize-space()='Employee List']")).click();
		// Enter search criteria (e.g., employee name)
		driver.findElement(By.xpath("(//input[@placeholder='Type for hints...'])[1]")).sendKeys("Istiak");

		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();
		// //div[@role='rowgroup']

		List<WebElement> element1 = driver.findElements(By.xpath("//span[@class='oxd-text oxd-text--span']"));
		/*
		 * for(int i = 0; i<=element1.size();i++) { System.out.println(i+
		 * element1.get(i).getText()); }
		 * 
		 */
		List<WebElement> element2 = driver.findElements(By.xpath("//div[@class='oxd-table-card']"));
		System.out.println(element2.size());
		int value = element2.size();
		String valuetostr = Integer.toString(value);
		String expectedMessage = "(" + valuetostr + ") Records Found";

		String actualMessage = element1.get(0).getText();

		Assert.assertEquals(expectedMessage, actualMessage);

		logout();

	}

	@Test(priority = 5, enabled = true)
	public void fileUploadTest() throws InterruptedException, IOException {
		logIn();
		// Navigate to the "PIM" menu and click PIM

		driver.findElement(By.xpath("//span[normalize-space()='PIM']")).click();

		// click on configuration button
		driver.findElement(By.xpath("//span[@class='oxd-topbar-body-nav-tab-item']")).click();

		// click on Data import
		driver.findElement(By.partialLinkText("Data ")).click();
		// click on browse button
		driver.findElement(By.xpath("//div[@class='oxd-file-button']")).click();

		Thread.sleep(5000);// pause of 5 seconds

		Runtime.getRuntime()
				.exec("D:\\ProgramFiles\\JAVA_automation\\OrangeHRM\\FileUploadOrangeHRM\\fileupload1.exe");

		Thread.sleep(5000);

		// click on upload button
		driver.findElement(By.xpath("//button[@type='submit']")).submit();
		
		logout();

	}

	public void logout() throws InterruptedException {
		driver.findElement(By.xpath("//p[@class='oxd-userdropdown-name']")).click();
		// driver.findElement(By.xpath("//a[normalize-space()='Logout']")).click();
		List<WebElement> elementList = driver.findElements(By.xpath("//a[@class='oxd-userdropdown-link']"));
		/*
		 * for (int i = 0; i < elementList.size(); i++) { Thread.sleep(1000);
		 * System.out.println(i + ":" + elementList.get(i).getText());
		 * 
		 * }
		 */
		elementList.get(3).click();

	}

	public void logIn() {
		// find username and enter username "Admin"
		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");

		// find password and enter password admin123
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");

		// login button click
		driver.findElement(By.xpath("//button[@type='submit']")).submit();

	}

	@AfterTest
	public void tearDown() throws InterruptedException {

		Thread.sleep(15000);
		driver.close();
		driver.quit();
	}

}
