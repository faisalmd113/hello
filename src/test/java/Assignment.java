import org.testng.annotations.Test;

import io.restassured.response.Response;

import org.testng.annotations.BeforeTest;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class Assignment {
	WebDriver driver = new ChromeDriver();

	@Test
	public void test() throws Exception {
		// Storing the data

		String testdata = "[\n"
				+ "  {\n"
				+ "    \"name\": \"Bob\",\n"
				+ "    \"age\": 20,\n"
				+ "    \"gender\": \"male\"\n"
				+ "  },\n"
				+ "  {\n"
				+ "    \"name\": \"George\",\n"
				+ "    \"age\": 42,\n"
				+ "    \"gender\": \"male\"\n"
				+ "  },\n"
				+ "  {\n"
				+ "    \"name\": \"Sara\",\n"
				+ "    \"age\": 42,\n"
				+ "    \"gender\": \"female\"\n"
				+ "  },\n"
				+ "  {\n"
				+ "    \"name\": \"Conor\",\n"
				+ "    \"age\": 40,\n"
				+ "    \"gender\": \"male\"\n"
				+ "  },\n"
				+ "  {\n"
				+ "    \"name\": \"Jennifer\",\n"
				+ "    \"age\": 42,\n"
				+ "    \"gender\": \"female\"\n"
				+ "  }\n"
				+ "]";

		// Initializing lists to store the data
		List<String> actualNames = new ArrayList<String>();
		List<Integer> actualAges = new ArrayList<Integer>();
		List<String> actualGenders = new ArrayList<String>();

		// Extracting data from json objects and storing in the lists
		JSONArray array = new JSONArray(testdata);
		for (int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			actualNames.add(object.getString("name"));
			actualAges.add(object.getInt("age"));
			actualGenders.add(object.getString("gender"));
		}

		System.out.println(actualNames);
		System.out.println(actualAges);
		System.out.println(actualGenders);

		// Sending the data to UI
		driver.findElement(By.xpath("//summary[text()='Table Data']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//textarea[@id]")).clear();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//textarea[@id]")).sendKeys(testdata);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[@id]")).click();
		Thread.sleep(3000);

		// Initializing lists to store the UI data
		List<String> ExpectedNames = new ArrayList<String>();
		List<Integer> ExpectedAges = new ArrayList<Integer>();
		List<String> ExpectedGenders = new ArrayList<String>();

		// Extracting data from UI and storing in the lists
		List<WebElement> nameElements = driver.findElements(By.xpath("//table/tr/td[1]"));
		List<WebElement> ageElements = driver.findElements(By.xpath("//table/tr/td[2]"));
		List<WebElement> genderElements = driver.findElements(By.xpath("//table/tr/td[3]"));

		for (int i = 0; i < array.length(); i++) {
			ExpectedNames.add(nameElements.get(i).getText());
			ExpectedAges.add(Integer.parseInt(ageElements.get(i).getText()));
			ExpectedGenders.add(genderElements.get(i).getText());
		}

		System.out.println(ExpectedNames);
		System.out.println(ExpectedAges);
		System.out.println(ExpectedGenders);

		// performing assertions
		Assert.assertEquals(actualNames, ExpectedNames, "Names in Json matches names in UI");
		Assert.assertEquals(actualAges, ExpectedAges, "Ages in Json matches ages in UI");
		Assert.assertEquals(actualGenders, ExpectedGenders, "Gender in Json matches gender in UI");

	}

	@BeforeTest
	public void beforeTest() throws Exception {
		System.out.println("Start Execution");
		System.setProperty("webdriver.chrome.driver", "C:\\Assignment\\chromedriver.exe");
		driver.get("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");
		driver.manage().window().maximize();
		Thread.sleep(3000);

	}

	@AfterTest
	public void afterTest() {
		driver.quit();
		System.out.println("Execution completed");
	}

}
