package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Icu {
	
	public Icu() {
	}
	
	public static Map<String, List<Integer>> parseListIcu (File f) {
		Map<String, List<Integer>> dictionary = new LinkedHashMap<>();
		Scanner sc;
		try {
			sc = new Scanner(f);
			while(sc.hasNext()) {
				String line = sc.nextLine();
				try {
					Reader i = new Reader(line);
					if(dictionary.containsKey(i.date.toUpperCase())) {
						dictionary.get(i.date.toUpperCase()).add(percentageOccupation(i.icuBeds, i.icuSUS, i.icuPrivate));
					} else {
						dictionary.put(i.date.toUpperCase(), new ArrayList<Integer>());
						dictionary.get(i.date.toUpperCase()).add(percentageOccupation(i.icuBeds, i.icuSUS, i.icuPrivate));
					}
				} 
				catch(Exception e) {
				}
			}	
			sc.close();
		} catch(FileNotFoundException e) {
			System.out.println("Arquivo não encontrado!");
		} 
	
		return dictionary;
	}
	
	public static Integer percentageOccupation(Integer infirmaryBeds, Integer infirmarySUS, Integer privateInfirmary) {
		Integer value = (int) (((double) (infirmarySUS + privateInfirmary)/ infirmaryBeds ) * 100);
		
		return value;
	}

}
