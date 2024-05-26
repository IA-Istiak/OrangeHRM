package Demo;

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
		//  memory alocate of driver object 
		driver = new ChromeDriver();
		//maximize window
		driver.manage().window().maximize();
		//open url
		driver.get(baseUrl);
		//implicite wait for 60s
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
	}
	
	@Test(priority = 2)
	public void validcredentialsLoginTest() {
		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");
		driver.findElement(By.xpath("//button[@type = 'submit']")).submit();
		
		//verify using title name
		
		String pageTitle = driver.getTitle();
	/*	if(pageTitle.equals("OrangeHRM")) {
			System.out.println("Login Pass");
		}
		else {
			System.out.println("login Failed");
		}
		*/
		Assert.assertEquals("OrangeHRM", pageTitle);
	}
	
	@Test(priority = 1)
	public void invalidcredentialsLoginTest() throws InterruptedException {
		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin32");
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin12323");
		driver.findElement(By.xpath("//button[@type = 'submit']")).submit();
		String expected_message = "Invalid credentials";
		String actual_message = driver.findElement(By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")).getText();
		Assert.assertTrue(actual_message.contains(expected_message));

				//Assert.assertEquals(message_expected, message_actual);

				Thread.sleep(1500);
	}
	
	public void logout() throws InterruptedException {
		driver.findElement(By.xpath("//p[@class='oxd-userdropdown-name']")).click();
		//driver.findElement(By.xpath("//a[normalize-space()='Logout']")).click();
		List<WebElement> elementList = driver.findElements(By.xpath("//a[@class='oxd-userdropdown-link']"));
		/**/for (int i=0; i<elementList.size(); i++)
		{
			Thread.sleep(1000);
			System.out.println(i + ":" + elementList.get(i).getText());

		}
		elementList.get(3).click();
		
		
	}
	
	@AfterTest
	public void tearDown() throws InterruptedException {
		logout();
		Thread.sleep(5000);
		driver.close();
		driver.quit();
	}

}
