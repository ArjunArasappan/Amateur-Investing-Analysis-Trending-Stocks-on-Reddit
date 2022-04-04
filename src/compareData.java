import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class compareData {
	
	public static String[] dateRange = new String[2];
	
	private static String[][] titleData;
	//private static String fileName = "dummy.txt";
	private static String fileName = "data_8.28.tsv";
	//private static String fileName = "timeAndPost.txt";
	
	public static nyseListing[] nyseRanks = extractTickers.nyseListings.clone();
	public static nasdaqListing[] nasdaqRanks= extractTickers.nasdaqListings.clone();


	private static ArrayList<String> titleData_list = new ArrayList<String>();
	private static ArrayList<String> titleTime_list = new ArrayList<String>();


    
    public static void rankTickers(int initialM, int finalM) {
    	String stringy;
		int cap = 0;
		

    	
		try  {
            Scanner sc = new Scanner(new File("src/files/" + fileName));
            int i = 0;
            while(sc.hasNextLine()) {
 
            	stringy = sc.nextLine();
         
    			int month = Integer.parseInt(stringy.substring(3, 5));
    			if(month <= finalM && month >= initialM) {
    				if(cap != 0 && i >= cap) 
    					break;
                	titleTime_list.add(stringy.substring(0, 10));
                	titleData_list.add(stringy.substring(11));
    			}
    			
    			System.out.println( (i+1) + ";" + month + ";" + stringy.substring(0, 10) + ";" + stringy.substring(11));
    			i++;
            }
		}

        
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		
		titleData = new String[titleData_list.size()][2];
		


		
		int l = 0;
		for(String str : titleData_list) {
			titleData[l][1] = str;
			titleData[l][0] = titleTime_list.get(l);

			l++;
		}


		int num = 0;
		String altTicker;
		String alt = "";
		int increment = 1;
		int lastNum = 0;
		
		for(String[] strs : titleData) {
			if(num == 0)
				dateRange[1] = strs[0].substring(3, 6) + strs[0].substring(0, 3) + strs[0].substring(6);
			else if(num == titleData.length - 1)
				dateRange[0] = strs[0].substring(3, 6) + strs[0].substring(0, 3) + strs[0].substring(6);
			
		
			
			
			
			
			for(String str : strs[1].split(" ")) {
				

				if(str.length() >= 1) {
					if(str.substring(0, 1).equals("$"))
						alt = str.substring(1);
					
					for(int i = 0; i < extractTickers.nyseListings.length; i++){
						nyseListing listing = extractTickers.nyseListings[i];
						altTicker = listing.getTicker().substring(0, 1) + listing.getTicker().substring(1);
						if(str.equals(listing.getTicker()) || alt.equals(listing.getTicker()) || str.equals(altTicker) || alt.equals(altTicker)){
							listing.incrementMentions();
						}
				
					}
					
					for(int i = 0; i < extractTickers.nasdaqListings.length; i++){
						nasdaqListing listing = extractTickers.nasdaqListings[i];
						altTicker = listing.getTicker().substring(0, 1) + listing.getTicker().substring(1);
						if(str.equals(listing.getTicker()) || alt.equals(listing.getTicker()) || str.equals(altTicker) || alt.equals(altTicker)) {
							listing.incrementMentions();
						}
					
					}
				}
			}
				
				
				
			
			
			
			num++;
			if((num * 100.0 / titleData.length) >= (lastNum * 100.0 / titleData.length) + increment) {
				System.out.print("Scanning WSB Post Titles... [");
				System.out.print((double)(num * 100.0 / titleData.length));
				System.out.println("%]");
				lastNum = num;
			}
			

		}


		System.out.println(dateRange[0] + ";" +  dateRange[1]);
		System.out.println(nyseRanks.length + ";" + nasdaqRanks.length);

		
		quickSort(nyseRanks);
		quickSort(nasdaqRanks);
		

		
		
		
    }
		

	public static void quickSort(redditStockListing[] ranks) {

		quickSort(ranks, 0, ranks.length - 1);
		
	}
	
    //best case and average case O(n logn) worst case is O(n^2)
    private static void quickSort(redditStockListing[] ranks, int i, int j)
    {	
        //base cases
        if(i >= j) //> means list length 0, = means list length 1
            return;
        else if(i == j - 1) //2 elements
        {
            if (ranks[i].getMentions() > ranks[j].getMentions())
                swap(ranks, i, j);
            return;
        }



        int startI = i;
        int startJ = j;
        //partition
        //pick the leftmost element to be the pivot -
        //try picking pivot with a different method?
        int pivot = ranks[i].getMentions();
        int pivotIndex = i;
        while(i != j)
        {
            //start with j and move left
            while (ranks[j].getMentions() > pivot && i != j)
                j--;
            //i and move right right
            while (ranks[i].getMentions() <= pivot && i != j)
                i++;
            if(i != j)
                swap(ranks, i, j);
            else //if they are equal swap 1/j with pivot
                swap(ranks, i, pivotIndex);
        }

        quickSort(ranks, startI, i-1);
        quickSort(ranks, i+1, startJ);
    }
    
	private static void swap(redditStockListing[] ranks, int i, int j) {
		redditStockListing temp = ranks[i];
		ranks[i] = ranks[j];
		ranks[j] = temp;
	}
    

}