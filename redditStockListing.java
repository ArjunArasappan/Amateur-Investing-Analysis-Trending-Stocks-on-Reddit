
public abstract class redditStockListing {
	private int mentions;
	private String ticker;
	private String stockExchange;
	
	public redditStockListing(int mentions, String ticker, String stockExchange){
		this.mentions = 0;
		this.ticker = ticker;
		this.stockExchange = stockExchange;
		
	}
	
	public int getMentions() {
		return mentions;
	}
	public String getTicker() {
		return ticker;
	}
	public String getStockExchange() {
		return stockExchange;
	}
	
	public void incrementMentions() {
		mentions ++;
	}
	
	public void incrementMentions(int num) {
		mentions += num;
	}
	
	
	@Override
	public String toString() {
		return "Counts: " + mentions + "\n" + "Ticker: " + ticker + "\n" + "Exchange: " + stockExchange + "\n" ;
		
	}

}
