package com.wf.hackathon.diversity.pages;

import org.openqa.selenium.By;

public class LinkedInLogin {
	public static By signInButton = By.xpath("//html/body/nav/div/a[2]");
	public static By userName = By.id("username");
	public static By password = By.id("password");
	public static By submitButton = By.xpath("//*[@id=\"organic-div\"]/form/div[3]/button");
	public static By searchInput = By.xpath("//input");
	public static By seeAllResultsButton = By.linkText("See all people results");
	public static By mathcedUserResults = By.xpath("//*[@id='main']//ul/li");
	public static By viewFullProfile = By.xpath("//*[@id='main']/div/div/div[1]/div/a/div/div[2]/div[2]/a");
}
