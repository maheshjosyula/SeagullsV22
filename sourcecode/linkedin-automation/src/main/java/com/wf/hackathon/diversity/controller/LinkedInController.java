package com.wf.hackathon.diversity.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.wf.hackathon.diversity.pages.LinkedInLogin;
import com.wf.hackathon.diversity.util.WebDriverUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

@RestController
@RequestMapping("/selenium")
public class LinkedInController {

	public static final String USERNAME = "";
	public static final String PASSWORD = "";
	public static final String URL = "https://www.linkedin.com/";

	@GetMapping
	public void getLinkedinDetails(@RequestParam("name") String name, @RequestParam("company") String company)
			throws InterruptedException, IOException {
		WebDriver driver = WebDriverUtil.getWebDriver();
		driver.get(URL);
		driver.manage().window().maximize();
		driver.findElement(LinkedInLogin.signInButton).click();

		WebElement email = driver.findElement(LinkedInLogin.userName);
		WebElement password = driver.findElement(LinkedInLogin.password);

		email.sendKeys(USERNAME);
		password.sendKeys(PASSWORD);

		WebElement submitButton = driver.findElement(LinkedInLogin.submitButton);
		submitButton.click();

		WebElement searchInput = driver.findElement(LinkedInLogin.searchInput);
		searchInput.sendKeys(name);
		searchInput.sendKeys(Keys.ENTER);

		WebElement seeAllResults = (new WebDriverWait(driver, 5))
				.until(ExpectedConditions.elementToBeClickable(LinkedInLogin.seeAllResultsButton));
		seeAllResults.click();

		Thread.sleep(10000);
		List<WebElement> userProfiles = driver.findElements(LinkedInLogin.mathcedUserResults);
		for (int i = 0; i < userProfiles.size(); i++) {
			String userData = userProfiles.get(i).getText();
			if (userData.toLowerCase().contains(company)) {
				WebElement userProfile = (new WebDriverWait(driver, 5))
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main\"]//ul/li[" + i + 1
								+ "]/div/div/div[2]/div[1]/div[1]/div/span[1]/span/a")));
				userProfile.click();
				break;
			}
		}
		boolean exists = driver.findElements(By.xpath("//*[@id='main']/div/div/div[1]/div/a/div/div[2]/div[2]/a"))
				.size() != 0;
		if (exists) {
			WebElement viewFullProfile = (new WebDriverWait(driver, 5))
					.until(ExpectedConditions.elementToBeClickable(LinkedInLogin.viewFullProfile));
			viewFullProfile.click();
		}

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,250)", "");

		String experienceSection = "/html/body/div[6]/div[3]/div/div/div[2]/div/div/main/section";
		Thread.sleep(10000);
		List<WebElement> userExperience = driver.findElements(By.xpath(experienceSection));
		String experience = "";
		int counter = 1;
		for (WebElement webElement : userExperience) {
			boolean isExists = driver
					.findElements(By.xpath(experienceSection + "[" + counter + "]" + "/div[@id='experience']"))
					.size() != 0;
			if (isExists) {
				experience = driver.findElement(By.xpath(experienceSection + "[" + counter + "]")).getText();
				break;
			}
			counter++;
		}
		counter = 1;
		String about = "";
		for (WebElement webElement : userExperience) {
			boolean isExists = driver
					.findElements(By.xpath(experienceSection + "[" + counter + "]" + "/div[@id='about']")).size() != 0;
			if (isExists) {
				about = driver.findElement(By.xpath(experienceSection + "[" + counter + "]")).getText();
				break;
			}
			counter++;
		}
		driver.close();
		String output= "{"
				+ "    \"about\":"+about+","
				+ "    \"experience\":"+experience+","
				+ "    \"dataset\":\"linkendin\"\r\n"
				+ "}";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("about", about);
		jsonObject.put("experience", experience);
		jsonObject.put("source", "linkedin");
		
		FileWriter file = new FileWriter("C:\\Users\\dell\\Desktop\\content.json");
		file.write(jsonObject.toJSONString());
		file.close();
		
		InputStream targetStream = new ByteArrayInputStream(output.getBytes());
		saveToBlobStorage(targetStream);
	}

	public void saveToBlobStorage(InputStream targetStream) throws IOException {
		BlobContainerClient container = new BlobContainerClientBuilder().connectionString(
				"DefaultEndpointsProtocol=https;AccountName=seagullsstorage;AccountKey=1W2VlvlzxmzzQcRUULCSjFuhhTpGluxPBymj2Et4SCfTOpwPLZqquLW3qKl46+sMohn+SdvQkKsI+AStmcLbHQ==;EndpointSuffix=core.windows.net")
				.containerName("linkedin").buildClient();
		BlobClient blobClient = container.getBlobClient("content.json");
		blobClient.uploadFromFile("C:\\Users\\dell\\Desktop\\content.json",true);
	}
}
