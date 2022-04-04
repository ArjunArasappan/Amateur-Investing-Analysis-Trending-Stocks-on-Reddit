import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class analysisMain {


    public static void main(String[] args) {

    	
		extractTickers.addListings();
		
    	int initialMonth = 1;
    	int finalMonth = 12;
    	int rankedNum = 10;
    	compareData.rankTickers(initialMonth, finalMonth);
    	stockPriceData.inputRanks(compareData.nyseRanks, compareData.nasdaqRanks, rankedNum);
    	stockPriceData.gatherData();
    	outputData.getData(compareData.nyseRanks, compareData.nasdaqRanks, stockPriceData.lastPrices);
    	System.out.println();
    	outputData.printNYSE(rankedNum, compareData.dateRange);
    	System.out.println();
    	outputData.printNASDAQ(rankedNum, compareData.dateRange);



    }
   
    

}
