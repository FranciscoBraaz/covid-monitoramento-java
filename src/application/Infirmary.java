package application;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
//import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Infirmary {
	
	public Infirmary() {
	}
	
	public static Map<String, List<Integer>> parseListInfirmary (File f) {
		Map<String, List<Integer>> dictionary = new LinkedHashMap<>();
		Scanner sc2;
		try {
			sc2 = new Scanner(f);
			while(sc2.hasNext()) {
				String line = sc2.nextLine();
				try {
					Reader i = new Reader(line);
					int iBeds = i.infirmaryBeds;
					int iSUS = i.infirmarySUS; 
					int iPrivate = i.infirmaryPrivate;
					if(dictionary.containsKey(i.date.toUpperCase())) {
						dictionary.get(i.date.toUpperCase()).add(percentageOccupation(iBeds, iSUS, iPrivate));
					} else {
						dictionary.put(i.date.toUpperCase(), new ArrayList<Integer>());
						dictionary.get(i.date.toUpperCase()).add(percentageOccupation(iBeds, iSUS, iPrivate));
					}
				} 
				catch(Exception e) {
				}
			}	
			sc2.close();
		} catch(FileNotFoundException e) {
			System.out.println("Arquivo n�o encontrado!");
		} 
		
//		for (Entry<String, List<Integer>> pair : dictionary.entrySet()) {
//		    System.out.println(pair.getKey());
//		    System.out.println(pair.getValue());
//		}
		return dictionary;
	}
	
	public static Integer percentageOccupation(Integer infirmaryBeds, Integer infirmarySUS, Integer privateInfirmary) {
		Integer value = (int) (((double) (infirmarySUS + privateInfirmary)/ infirmaryBeds ) * 100);
		
		return value;
	}
}
