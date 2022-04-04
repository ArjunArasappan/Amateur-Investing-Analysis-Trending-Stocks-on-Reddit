import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import processing.core.PApplet;
import processing.core.PFont;

public class graphicsMain extends PApplet {
	
	
	boolean isStarted = false;
	boolean isFinished = false;
	
	String[] fontList = PFont.list();
	PFont font, fontBig, fontSmall, fontSlightlySmall, fontTitle;
	
	ArrayList<String> NASDAQ = new ArrayList<String>();
	ArrayList<String> NYSE = new ArrayList<String>();
	
	int ranks = 10;
	
	boolean isNYSEPrinted = true;
	boolean isNASDAQPrinted = true;
	
	int initialMonth = 1;
	int finalMonth = 12;
	int rankedNum = 10;

	
	public static void main(String[] args) {
		PApplet.main("graphicsMain");
	}
	
	public void settings() {
		size(700, 760);

	}
	
	public void setup() {
		background(177, 227, 252);   	

	}
	
	public void draw() {
		clear();
		background(177, 227, 252);
		drawMenu();
		
		if(isStarted) {
		  	updateProgress("Extracting NASDAQ Tickers...", 0);
			extractTickers.addNASDAQ("src/files/nasdaqListings.txt");
			updateProgress("Extracting NYSE Tickers...", 1);
			extractTickers.addNYSE("src/files/nyseListings.txt");
			
			extractTickers.convert();
			
			updateProgress("Cross-Checking WSB Data...", 2);
	    	compareData.rankTickers(initialMonth, finalMonth);
	    	
	    	
	    	updateProgress("Gathering Stock Data...", 76);
	    	stockPriceData.inputRanks(compareData.nyseRanks, compareData.nasdaqRanks, ranks);
	    	stockPriceData.gatherData();
	    	
	    	updateProgress("Displaying Data...", 99.9);
	    	outputData.getData(compareData.nyseRanks, compareData.nasdaqRanks, stockPriceData.lastPrices);
	    	
	    	outputData.getData(compareData.nyseRanks, compareData.nasdaqRanks, stockPriceData.lastPrices);
	    	System.out.println();
	    	outputData.printNYSE(rankedNum, compareData.dateRange);
	    	System.out.println();
	    	outputData.printNASDAQ(rankedNum, compareData.dateRange);
	    	
	    	updateProgress("Done...", 100);
	    	isStarted = false;
	    	isFinished = true;
		}
		
		if(isFinished) {
			updateProgress("Done...", 100);
			stroke(0,0,0);
			fill(0,0,0);
			appendData();
		}
	}
	
	int menuSpacing = -18;
	int progress = 0; //progress percent
	int yOffset = 35;
	int sqOffset = -18; // button squares
	int listingsOffset = 14;
	
	public void drawMenu() {
		stroke(0,0,0);
		fill(0,0,0);
		fontBig = createFont("Georgia", 40);
		font = createFont("Georgia", 20);
		fontSmall = createFont(fontList[5], 10);
		fontSlightlySmall = createFont("Georgia", 15);
		fontTitle = createFont("Georgia", 30);

    	textFont(fontTitle);
		text("Amateur Investing: Trending Stocks on Reddit", 30, 40);
		
		textFont(font);
		
		text("Number of Ranks?", 60, 50 + yOffset);
		text(ranks, 250, 50 + yOffset);
		
		fill(255, 255, 255);
//		rect(225, 50 + yOffset + sqOffset, 20, 20);
//		rect(280, 50 + yOffset + sqOffset, 20, 20);
		fill(194, 0, 0);
		noStroke();
		triangle(225 + 20, 50 + yOffset + sqOffset, 225 + 20, 50 + yOffset + sqOffset + 20, 225 + 2, 50 + yOffset + sqOffset + 10);
		triangle(280, 50 + yOffset + sqOffset, 280, 50 + yOffset + sqOffset + 20, 280 + 20 - 2, 50 + yOffset + sqOffset + 10);
		fill(0,0,0);
		stroke(0,0,0);
		
		
		
		text("Nasdaq Exchange?", 60, 50+2*yOffset);
		
		fill(255, 255, 255);
		rect(230, 50+2*yOffset+sqOffset, 20, 20);
		fill(0,0,0);
		stroke(0,0,0);
		
		if (isNASDAQPrinted) {
			fill(0, 0, 0);
			rect(233, 50+2*yOffset+sqOffset+3, 14, 14);
		}
		
		
		text("NYSE Exchange?", 60, 50 + 3 * yOffset);
		
		fill(255, 255, 255);
		rect(215, 50+3*yOffset+sqOffset, 20, 20);
		fill(0,0,0);
		stroke(0,0,0);
		
		if (isNYSEPrinted) {
			fill(0, 0, 0);
			rect(218, 50+3*yOffset+sqOffset+3, 14, 14);
		}

		
		//progress bar
		
		rect(60, 50+5*yOffset, 520, 20);
		fill(255,255,255);
		stroke(255,255,255);
		rect(62, 50+5*yOffset+2, 516, 16);

		
		//start button
		stroke(0,0,0);
		textFont(fontBig);
		fill(3, 252, 7);
		rect(400, 76, 150, 80);
		fill(0,0,0);
		text("Start", 430, 130);
		

	

	}
	
	public void appendData() {
		//Rank
		textFont(font);
		text("Ranks: " + compareData.dateRange[0] + " - " + compareData.dateRange[1], 60, 50+6*yOffset+15);
		
		nyseListing[] nyseMentions = outputData.nyseRanks ;
		nasdaqListing[] nasdaqMentions = outputData.nasdaqRanks ;
		
		String[] nyseRanks = stockPriceData.nyseRanks;
		String[] nasdaqRanks = stockPriceData.nasdaqRanks;
		String[][] lastPrices = stockPriceData.lastPrices;
		String[][] yearRange = stockPriceData.yearRange;
		String[][] marketCap = stockPriceData.marketCap;
		String[][] PEratios = stockPriceData.PEratios;
		String[][] company = stockPriceData.company;
		String[][] tickerURL = stockPriceData.tickerURL;
		
		
		
		
		//NYSE
		if (isNYSEPrinted) {
			//categories
			textFont(fontSlightlySmall);
			text("Mentions", 200, 50+7*yOffset+15);
			text("Share Price", 280, 50+7*yOffset+15);
			text("1-Year Range", 370, 50+7*yOffset+15);
			text("Market Cap", 470 + 10 + 20, 50+7*yOffset+15);
			text("P/E", 570 + 20, 50+7*yOffset+15);			
			//listings		
			textFont(fontSlightlySmall);
			text("NYSE", 70, 50+7*yOffset+15);
			textFont(fontSmall);
			for (int i = 0; i < ranks; i++) {
				text(i+1 + ". " +  nyseRanks[i], 75, 50+7*yOffset+15 + 10 + (i+1)*listingsOffset);
				text(nyseMentions[nyseMentions.length - 1 - i].getMentions(), 220, 50+7*yOffset+15 + 10 + (i+1)*listingsOffset);
				text(lastPrices[0][i], 305, 50+7*yOffset+15 + 10 + (i+1)*listingsOffset);
				text(yearRange[0][i], 400 - 30, 50+7*yOffset+15 + 10 + (i+1)*listingsOffset);
				text(marketCap[0][i], 495 - 10 + 20, 50+7*yOffset+15 + 10 + (i+1)*listingsOffset);
				text(PEratios[0][i], 573 + 20, 50+7*yOffset+15 + 10 + (i+1)*listingsOffset);
			}
		}
		
		//NASDAQ
		if (isNASDAQPrinted) {
			//categories
			textFont(fontSlightlySmall);
			text("Mentions", 200, 50+13*yOffset+15);
			text("Share Price", 280, 50+13*yOffset+15);
			text("1-Year Range", 370, 50+13*yOffset+15);
			text("Market Cap", 470 + 10 + 20, 50+13*yOffset+15);
			text("P/E", 570 + 20, 50+13*yOffset+15);	
			//listings
			textFont(fontSlightlySmall);
			text("NASDAQ", 70, 50+13*yOffset+15);
			textFont(fontSmall);
			for (int i = 0; i < ranks; i++) {
				text(i+1 + ". " + nasdaqRanks[i], 75, 50+13*yOffset+15 + 10 + (i+1)*listingsOffset);
				text(nasdaqMentions[nasdaqMentions.length - 1 - i].getMentions(), 220, 50+13*yOffset+15 + 10 + (i+1)*listingsOffset);
				text(lastPrices[1][i], 305, 50+13*yOffset+15 + 10 + (i+1)*listingsOffset);
				text(yearRange[1][i], 400 - 30, 50+13*yOffset+15 + 10 + (i+1)*listingsOffset);
				text(marketCap[1][i], 495 - 10 + 20, 50+13*yOffset+15 + 10 + (i+1)*listingsOffset);
				text(PEratios[1][i], 573 + 20, 50+13*yOffset+15 + 10 + (i+1)*listingsOffset);
			}
		}
	}
	
	public void mouseClicked() {
		//Rankings
		//left

		
		if (mouseX >= 225 && mouseX <= 245)
			if (mouseY >= 50+yOffset+sqOffset && mouseY <= 50+yOffset+sqOffset+20)
				if (ranks > 1) {
					ranks--;
				}
		//right
		if (mouseX >= 280 && mouseX <= 300)
			if (mouseY >= 50+yOffset+sqOffset && mouseY <= 50+yOffset+sqOffset+20)
				if (ranks < 10) {
					ranks++;
				}
		
		//isNYSE
		
		
		if (mouseX >=215 && mouseX <=235)
			if (mouseY >= 50+3*yOffset+sqOffset && mouseY <= 50+3*yOffset+sqOffset+20)
				isNYSEPrinted = !isNYSEPrinted;
		
		//isNASDAQ

		
		if (mouseX >=230 && mouseX <=250)
			if (mouseY >= 50+2*yOffset+sqOffset && mouseY <= 50+2*yOffset+sqOffset+20)
				isNASDAQPrinted = !isNASDAQPrinted;
		
		//ifStart
		
		if (mouseX >=400 && mouseX <=500)
			if (mouseY >= 76 && mouseY <= 156)
				isStarted = true;
		
	}
	
	public void updateProgress(String prog, double percent) {
		clear();
		textFont(font);
		text("Progress: "+ percent + "%    " + prog, 60, 50+4*yOffset+20);
		background(177, 227, 252);
		drawMenu();
		

		//progress bar green
		fill(3, 252, 82);
		stroke(3, 252, 82);
		rect(62, 50+5*yOffset+2, (int)(516 * percent / 100), 16);
	
	}
	
	
	
	
//	//_______________________//
//	
//	public static String[] dateRange = new String[2];
//	
//	private static String[][] titleData;
//	//private static String fileName = "dummy.txt";
//	private static String fileName = "data_8.28.tsv";
//	//private static String fileName = "timeAndPost.txt";
//	
//	public static nyseListing[] nyseRanks;
//	public static nasdaqListing[] nasdaqRanks;
//
//
//	private static ArrayList<String> titleData_list = new ArrayList<String>();
//	private static ArrayList<String> titleTime_list = new ArrayList<String>();
//
//
//    
//    public void rankTickers(int initialM, int finalM) {
//    	nyseListing[] nyseRanks = extractTickers.nyseListings.clone();
//    	nasdaqListing[] nasdaqRanks= extractTickers.nasdaqListings.clone();
//    			
//    	String stringy;
//		int cap = 0;
//		
//
//    	
//		try  {
//            Scanner sc = new Scanner(new File("src/files/" + fileName));
//            int i = 0;
//            while(sc.hasNextLine()) {
// 
//            	stringy = sc.nextLine();
//         
//    			int month = Integer.parseInt(stringy.substring(3, 5));
//    			if(month <= finalM && month >= initialM) {
//    				if(cap != 0 && i >= cap) 
//    					break;
//                	titleTime_list.add(stringy.substring(0, 10));
//                	titleData_list.add(stringy.substring(11));
//    			}
//    			
//    			System.out.println( (i+1) + ";" + month + ";" + stringy.substring(0, 10) + ";" + stringy.substring(11));
//    			i++;
//            }
//		}
//
//        
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//		
//		titleData = new String[titleData_list.size()][2];
//		
//
//
//		
//		int l = 0;
//		for(String str : titleData_list) {
//			titleData[l][1] = str;
//			titleData[l][0] = titleTime_list.get(l);
//
//			l++;
//		}
//
//
//		int num = 0;
//		String altTicker;
//		String alt = "";
//		int increment = 1;
//		int lastNum = 0;
//		
//		for(String[] strs : titleData) {
//			if(num == 0)
//				dateRange[1] = strs[0].substring(3, 6) + strs[0].substring(0, 3) + strs[0].substring(6);
//			else if(num == titleData.length - 1)
//				dateRange[0] = strs[0].substring(3, 6) + strs[0].substring(0, 3) + strs[0].substring(6);
//			
//		
//			
//			
//			
//			
//			for(String str : strs[1].split(" ")) {
//				
//
//				if(str.length() >= 1) {
//					if(str.substring(0, 1).equals("$"))
//						alt = str.substring(1);
//					
//					for(int i = 0; i < extractTickers.nyseListings.length; i++){
//						nyseListing listing = extractTickers.nyseListings[i];
//						altTicker = listing.getTicker().substring(0, 1) + listing.getTicker().substring(1);
//						if(str.equals(listing.getTicker()) || alt.equals(listing.getTicker()) || str.equals(altTicker) || alt.equals(altTicker)){
//							listing.incrementMentions();
//						}
//				
//					}
//					
//					for(int i = 0; i < extractTickers.nasdaqListings.length; i++){
//						nasdaqListing listing = extractTickers.nasdaqListings[i];
//						altTicker = listing.getTicker().substring(0, 1) + listing.getTicker().substring(1);
//						if(str.equals(listing.getTicker()) || alt.equals(listing.getTicker()) || str.equals(altTicker) || alt.equals(altTicker)) {
//							listing.incrementMentions();
//						}
//					
//					}
//				}
//			}
//				
//				
//				
//			
//			
//			
//			num++;
//			if((num * 100.0 / titleData.length) >= (lastNum * 100.0 / titleData.length) + increment) {
//				System.out.print("Scanning WSB Post Titles... [");
//				
//				System.out.print((double)(num * 100.0 / titleData.length));
//				System.out.println("%]");
//				updateProgress("Cross-Checking WSB Post Titles...", (double) num / (double )titleData.length);
//				lastNum = num;
//			}
//			
//
//		}
//
//
//		System.out.println(dateRange[0] + ";" +  dateRange[1]);
//		System.out.println(nyseRanks.length + ";" + nasdaqRanks.length);
//
//		
//		compareData.quickSort(nyseRanks);
//		compareData.quickSort(nasdaqRanks);
//		
//    }

}
	


