import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import java.awt.Desktop;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.*;

public class stockPriceData {
	static String[] nyseRanks;
	static String[] nasdaqRanks;
	static String[][] lastPrices;
	static String[][] yearRange;
	static String[][] marketCap;
	static String[][] PEratios;
	static String[][] company;
	static String[][] tickerURL;
	
	static String driverPath = "/Users/arjunarasappan/Documents/chromedriver v100";
	
	static String baseLink = "https://www.google.com/search?q=AMC+stock+price&safe=strict&rlz=1C5CHFA_enUS890US890&ei=ooa_YOjuEracwbkPzfuBsAw&oq=AMC+stock+price&gs_lcp=Cgdnd3Mtd2l6EAMyCggAELEDEIMBEEMyCAgAELEDEIMBMggIABCxAxCDATIICAAQsQMQgwEyCAgAELEDEIMBMggIABCxAxCDATIICAAQsQMQgwEyCAgAELEDEIMBMggIABCxAxCDATIICAAQsQMQgwE6BggAEAcQHjoFCAAQkQI6BQgAEMQCUK0RWIUUYOYVaABwAngAgAGLAogBggSSAQUzLjAuMZgBAKABAaoBB2d3cy13aXrAAQE&sclient=gws-wiz&ved=0ahUKEwjox-igp4jxAhU2TjABHc19AMYQ4dUDCA4&uact=5";
	static String baseFinanceLink = "https://www.google.com/finance/quote/";
	
	static String xpath = "//*[@id=\"yDmH0d\"]/c-wiz/div/div[4]/div/div/main/div[2]/c-wiz/div/div[1]/div/div[1]/div/div[1]/div/span/div/div";
	static String financeXpathFull = "/html/body/c-wiz/div/div[4]/div/div/main/div[2]/div[1]/c-wiz/div/div[1]/div/div[1]/div/div[1]/div/span/div/div";
	static String financeXpath = "//*[@id=\"yDmH0d\"]/c-wiz/div/div[4]/div/div/main/div[2]/div[1]/c-wiz/div/div[1]/div/div[1]/div/div[1]/div/span/div/div";
	static String financeClass = "YMlKec fxKbKc";
	
	
	static String cssFinanceSelector = "#yDmH0d > c-wiz > div > div.e1AOyf > div > div > main > div.Gfxi4 > div.yWOrNb > div.VfPpkd-WsjYwc.VfPpkd-WsjYwc-OWXEXe-INsAgc.KC1dQ.Usd1Ac.AaN0Dd.QZMA8b > c-wiz > div > div:nth-child(1) > div > div.rPF6Lc > div > div:nth-child(1) > div > span > div > div";
	static String cssYearRange = "#yDmH0d > c-wiz > div > div.e1AOyf > div > div > main > div.Gfxi4 > div.HKO5Mb > div > div.eYanAe > div:nth-child(4) > div";
	static String cssMarketCap = "#yDmH0d > c-wiz > div > div.e1AOyf > div > div > main > div.Gfxi4 > div.HKO5Mb > div > div.eYanAe > div:nth-child(5) > div";
	static String cssPEratios = "#yDmH0d > c-wiz > div > div.e1AOyf > div > div > main > div.Gfxi4 > div.HKO5Mb > div > div.eYanAe > div:nth-child(7) > div";
	static String cssCompany = "#yDmH0d > c-wiz > div > div.e1AOyf > div > div > main > div.Q2JMWd > div.xJwwl > div.zzDege";
			
	
	static int num;
	
	static boolean isHeadless  = false;
	
	
	public static void inputRanks(nyseListing[] nyse, nasdaqListing[] nasdaq, int ranksNum) {

		nyseRanks = new String[ranksNum];
		nasdaqRanks = new String[ranksNum];
		num = ranksNum;
		
		for(int i = nyse.length - 1; i >= nyse.length - num; i--) {
			nyseRanks[nyse.length - i - 1] = nyse[i].getTicker();
			
		}

		
		for(int i = nasdaq.length - 1; i >= nasdaq.length - num; i--) {
			nasdaqRanks[nasdaq.length - i - 1] = nasdaq[i].getTicker();
		}
			
		num = ranksNum;
		lastPrices = new String[2][ranksNum];
		yearRange = new String[2][ranksNum];
		marketCap = new String[2][ranksNum];
		PEratios = new String[2][ranksNum];
		company = new String[2][ranksNum];
		
		tickerURL = new String[ranksNum * 2][2];
	}
	
	
	
	public static void gatherData() {
		String str, url;
		
		ChromeOptions options = new ChromeOptions();
		if(isHeadless) {
			options.addArguments("--headless");
		}
		
		System.setProperty("webdriver.chrome.driver", driverPath);
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		int index = 0;
		for(String stock : nyseRanks) {
			 //url = baseLink.substring(0).replace("AMC", stock);
			 url = baseFinanceLink + stock + ":NYSE";
			 driver.get(url);
			 
			 //lastPrices[0][index] = driver.findElement(By.xpath(xpath)).getText();
			 lastPrices[0][index] = driver.findElement(By.cssSelector(cssFinanceSelector)).getText();
			 yearRange[0][index] = driver.findElement(By.cssSelector(cssYearRange)).getText();
			 marketCap[0][index] = driver.findElement(By.cssSelector(cssMarketCap)).getText();
			 PEratios[0][index] = driver.findElement(By.cssSelector(cssPEratios)).getText();
			 company[0][index] = driver.findElement(By.cssSelector(cssCompany)).getText();
			 //lastPrices[0][index] = driver.findElement(By.xpath(financeClass)).getText();
			 
			 
			 
			 
			 
			 System.out.println("Gathering NYSE Stock Price Data... [" + stock + ": " + lastPrices[0][index] + ", " + yearRange[0][index] +  ", " + marketCap[0][index] + ", " + PEratios[0][index] + "]");
			 tickerURL[index][0] = stock;
			 tickerURL[index][1] = url;
			 
			 index++;
	
		}
		
		index = 0;
		for(String stock : nasdaqRanks) {

			 //url = baseLink.substring(0).replace("AMC", stock);
			 url = baseFinanceLink + stock + ":NASDAQ";

			 driver.get(url);
			 
			 //lastPrices[0][index] = driver.findElement(By.xpath(xpath)).getText();
			 
			 lastPrices[1][index] = driver.findElement(By.cssSelector(cssFinanceSelector)).getText();
			 yearRange[1][index] = driver.findElement(By.cssSelector(cssYearRange)).getText();
			 marketCap[1][index] = driver.findElement(By.cssSelector(cssMarketCap)).getText();
			 PEratios[1][index] = driver.findElement(By.cssSelector(cssPEratios)).getText();
			 company[1][index] = driver.findElement(By.cssSelector(cssCompany)).getText();
			 
			 System.out.println("Gathering NASDAQ Stock Price Data... [" + company[1][index] + ": " + lastPrices[1][index] + "]");
			 tickerURL[index + num][0] = stock;
			 tickerURL[index + num][1] = url;
			 
			 index++;

		}
	}
	
}
