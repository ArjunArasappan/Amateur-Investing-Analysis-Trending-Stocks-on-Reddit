import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class extractTickers {

	public static String[] arrayTickers_nyse;
	public static String[] arrayTickers_nasdaq;
	
	public static nyseListing[] nyseListings;
	public static nasdaqListing[] nasdaqListings;
	
	private static ArrayList<String> listTickers_nyse = new ArrayList<String>();
	private static ArrayList<String> listTickers_nasdaq = new ArrayList<String>();
	
	public static void addListings() {
		
		addNASDAQ("src/files/nasdaqListings.txt");
		addNYSE("src/files/nyseListings.txt");
		convert();
		//printArray(nyseListings);
		
		
	}
	
	public static void addNYSE(String filename) {
		try  {
            Scanner sc = new Scanner(new File(filename));
            int i = 0;
            while(sc.hasNext()) {
            	//System.out.println("NYSE		" + ((i * 100.0) / 3118));
            	listTickers_nyse.add(sc.next());
            	i++;
            }
           
        } 
        
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}

	public static void addNASDAQ(String filename) {
		try  {
			int i = 0;
            Scanner sc = new Scanner(new File(filename));
            while(sc.hasNext()) {
            	//System.out.println("NASDAQ		" + ((i * 100.0) / 4236));
            	i++;
            	listTickers_nasdaq.add(sc.next());
            }
           
        } 
        
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}
	
	public static void convert() {
		arrayTickers_nyse = new String[listTickers_nyse.size()];
		nyseListings = new nyseListing[listTickers_nyse.size()];
		arrayTickers_nasdaq = new String[listTickers_nasdaq.size()];
		nasdaqListings = new nasdaqListing[listTickers_nasdaq.size()];
		
		
		int pace = 0;
		for(String str : listTickers_nyse) {
			arrayTickers_nyse[pace] = str;
			nyseListings[pace] = new nyseListing(0, str);
			
			pace++;
		}
		pace = 0;
		for(String str : listTickers_nasdaq) {
			arrayTickers_nasdaq[pace] = str;
			nasdaqListings[pace] = new nasdaqListing(0, str);
			pace++;
		}
		
		
		
	}
	
	public static void printArray(Object[] arr) {
		for(Object obj : arr) {
			System.out.println(obj + ", ");
		}
		
	}

}
