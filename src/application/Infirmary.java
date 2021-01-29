package application;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
//import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Infirmary {
	String data;
	Integer infirmaryBeds;
	Integer privateInfirmary;
	Integer infirmarySUS;
	
	Infirmary(String line) throws Exception {
		Scanner sc = new Scanner(line);
		sc.useDelimiter("\\s{2,}");
		String[] dataParts = sc.next().replaceAll(" +", "").split("-");
		data = dataParts[1];
		sc.next();
		sc.next();
		sc.next();
		sc.next();
		sc.next();
		sc.next();
		sc.next();
		sc.next();
		sc.next();
		sc.next();
		infirmaryBeds = sc.nextInt();
		sc.next();
		sc.next();
		infirmarySUS = sc.nextInt();
		sc.next();
		privateInfirmary = sc.nextInt();
		sc.close();
	}
	
	public static Map<String, List<Integer>> parseListInfirmary (File f) {
		Map<String, List<Integer>> dictionary = new LinkedHashMap<>();
		Scanner sc2;
		try {
			sc2 = new Scanner(f);
			while(sc2.hasNext()) {
				String line = sc2.nextLine();
				try {
					Infirmary i = new Infirmary(line);
					int iBeds = i.infirmaryBeds;
					int iSUS = i.infirmarySUS; 
					int iPrivate = i.privateInfirmary;
					if(dictionary.containsKey(i.data.toUpperCase())) {
						dictionary.get(i.data.toUpperCase()).add(percentageOccupation(iBeds, iSUS, iPrivate));
					} else {
						dictionary.put(i.data.toUpperCase(), new ArrayList<Integer>());
						dictionary.get(i.data.toUpperCase()).add(percentageOccupation(iBeds, iSUS, iPrivate));
					}
				} 
				catch(Exception e) {
				}
			}	
			sc2.close();
		} catch(FileNotFoundException e) {
			System.out.println("Arquivo não encontrado!");
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
