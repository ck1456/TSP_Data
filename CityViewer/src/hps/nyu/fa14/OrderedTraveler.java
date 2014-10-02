package hps.nyu.fa14;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderedTraveler {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		// Parse the first argument as the input file and the second as the output file
		if(args.length != 2){
			usage();
			System.exit(1);
		}
		CitySet cities = CitySet.LoadFromUrl(new File(args[0]).toURI().toString());

		Tour newTour = new Tour(cities);
		List<Integer> cityIDs = new ArrayList<Integer>();
		for(City c : cities){
			cityIDs.add(c.ID);
		}
		Collections.sort(cityIDs);
		for(Integer id : cityIDs){
			newTour.addCity(id);
		}
		
		newTour.saveTour(new FileOutputStream(new File(args[1])));
	}
	
	private static void usage(){
		// How to use it
		System.out.println("java -jar OrderedTraveler <input> <output>");
	}

}
