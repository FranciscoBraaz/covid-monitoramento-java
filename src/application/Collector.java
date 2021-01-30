package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Collector {
	private Integer beds;
	private Integer patientSus;
	private Integer patientPrivate;
	
	public Collector(Integer beds, Integer patientSus, Integer patientPrivate) {
		this.beds = beds;
		this.patientSus = patientSus;
		this.patientPrivate = patientPrivate;
	}
	
	public Map<String, List<Integer>> parseListInfirmary (File f) {
		Map<String, List<Integer>> dictionary = new LinkedHashMap<>();
		Scanner sc2;
		try {
			sc2 = new Scanner(f);
			while(sc2.hasNext()) {
				String line = sc2.nextLine();
				try {
					Reader i = new Reader(line);
					beds = i.infirmaryBeds;
					patientSus = i.infirmarySUS; 
					patientPrivate = i.infirmaryPrivate;
					if(dictionary.containsKey(i.date.toUpperCase())) {
						dictionary.get(i.date.toUpperCase()).add(percentageOccupation(beds, patientSus, patientPrivate));
					} else {
						dictionary.put(i.date.toUpperCase(), new ArrayList<Integer>());
						dictionary.get(i.date.toUpperCase()).add(percentageOccupation(beds, patientSus, patientPrivate));
					}
				} 
				catch(Exception e) {
				}
			}	
			sc2.close();
		} catch(FileNotFoundException e) {
			System.out.println("Arquivo não encontrado!");
		} 
		
		return dictionary;
	}
	
	public static Integer percentageOccupation(Integer beds, Integer patientSus, Integer patientPrivate) {
		Integer value = (int) (((double) (patientSus + patientPrivate)/ beds ) * 100);
		
		return value;
	}
}
