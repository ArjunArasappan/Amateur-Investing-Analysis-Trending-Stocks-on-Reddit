

public class outputData {
	
	public static nyseListing[] nyseRanks ;
	public static nasdaqListing[] nasdaqRanks;
	public static String[][] stockPrices;

	
	public static void getData(nyseListing[] nyseRankings, nasdaqListing[] nasdaqRankings, String[][] prices) {
		
		nyseRanks = nyseRankings;
		nasdaqRanks = nasdaqRankings;
		stockPrices = prices;

		
	}
	
	public static void printNYSE(int number, String[] arr) {
		System.out.println("TOP MENTIONED NYSE TICKERS from " + arr[0] + " - " + arr[1] + ":" );
		
		for(int i = nyseRanks.length - 1; i > nyseRanks.length - 1 - number; i--) {
			System.out.println("      " + (nyseRanks.length - i) + ") " + nyseRanks[i].getTicker() + ": " +
								nyseRanks[i].getMentions() + ", " + 
								stockPrices[0][number - 1 - (stockPrices[0].length - 1 - (nyseRanks.length - i - 1))] + 
								", Year Range"  + stockPriceData.yearRange[0][number - 1 - (stockPrices[0].length - 1 - (nyseRanks.length - i - 1))] +
								", Market Cap:"  + stockPriceData.marketCap[0][number - 1 - (stockPrices[0].length - 1 - (nyseRanks.length - i - 1))] +
								", P/E Ratio"  + stockPriceData.PEratios[0][number - 1 - (stockPrices[0].length - 1 - (nyseRanks.length - i - 1))]);
;
		}
	}
	
	public static void printNASDAQ(int number, String[] arr) {
		System.out.println("TOP MENTIONED NASDAQ TICKERS from " + arr[0] + " - " + arr[1] + ":" );
		
		for(int i = nasdaqRanks.length - 1; i > nasdaqRanks.length - 1 - number; i--) { 
			System.out.println("      " + (nasdaqRanks.length - i) + ") " + nasdaqRanks[i].getTicker() + ": " + 
								nasdaqRanks[i].getMentions()  + ", " + 
								stockPrices[1][number - 1 - (stockPrices[1].length - 1 - (nasdaqRanks.length - i - 1))] + 
								", Year Range: "  + stockPriceData.yearRange[1][number - 1 - (stockPrices[1].length - 1 - (nasdaqRanks.length - i - 1))] +
								", Market Cap: "  + stockPriceData.marketCap[1][number - 1 - (stockPrices[1].length - 1 - (nasdaqRanks.length - i - 1))] +
								", P/E Ratio: "  + stockPriceData.PEratios[1][number - 1 - (stockPrices[1].length - 1 - (nasdaqRanks.length - i - 1))]);
		}
	}
}
																																																						