package hps.nyu.fa14;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GreedyTraveler {

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
		// Take the first city
		// Find the closest city, add it, then iterate
		
		Set<Integer> usedCities = new HashSet<Integer>();
		Tour currentTour = new Tour(cities);
		
		List<City> cityList = new ArrayList<City>();
		for(City c: cities){
			cityList.add(c);
		}
		double[][] distances = calculateAllDistances(cityList);
		int currentIndex = 0;
		usedCities.add(currentIndex);
		
		while(usedCities.size() < cities.size()){
			currentTour.addCity(cityList.get(currentIndex).ID);
			int nextCity = findClosestCity(currentIndex, distances, usedCities);
			currentIndex = nextCity;
			usedCities.add(currentIndex);
		}
		currentTour.addCity(cityList.get(currentIndex).ID);
		
		currentTour.saveTour(new FileOutputStream(new File(args[1])));
	}
	
	private static double[][] calculateAllDistances(List<City> cities){
		double[][] distances = new double[cities.size()][cities.size()];
		for(int i = 0; i < cities.size(); i++){
			City from = cities.get(i);
			for(int j = 0; j < cities.size(); j++){
				distances[i][j] = from.distance(cities.get(j));
			}
		}
		return distances;
	}
	
	private static int findClosestCity(int cityIndex, double[][] distances, Set<Integer> excluded){
		double minDistance = Double.MAX_VALUE;
		int closestCity = -1;
		for(int i = 0; i < distances.length; i++){
			if(!excluded.contains(i)){
				if(distances[cityIndex][i] < minDistance){
					minDistance = distances[cityIndex][i];
					closestCity = i;
				}
			}
		}
		if(closestCity == -1){
			throw new RuntimeException("No closest city");
		}
		return closestCity;
	}
	
	private static void usage(){
		// How to use it
		System.out.println("java -jar GreedyTraveler <input> <output>");
	}

}
