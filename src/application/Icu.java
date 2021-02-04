package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Classe respons�vel por pegar os dados referentes � UTI da classe Reader e realizar os c�lculos necess�rios
 */

public class Icu {
	
	public Icu() {
	}
	
	
	/**
	 * M�todo que pega as informa��es necess�rios referentes a UTI;
	 * Quando o gr�fico de linha � instanciado � utilizado o m�todo percentageOccupation;
	 * Quando o gr�fico de pizza for o instanciado, o m�todo percentageOccupationTotal ser� utilizado;
	 * @param f
	 * @param chart
	 * @return
	 */
	public static Map<String, List<Integer>> parseListIcu (File f, String chart) {
		Map<String, List<Integer>> dictionary = new LinkedHashMap<>();
		
		Scanner sc;
		
		try {
			sc = new Scanner(f);
	
			while(sc.hasNext()) {
				String line = sc.nextLine();
				try {
					Reader i = new Reader(line);
					if(dictionary.containsKey(i.date.toUpperCase())) {
						if(chart == "line") {
							dictionary.get(i.date.toUpperCase()).add(percentageOccupation(i.icuBeds, i.icuSus, i.icuPrivate));
						} else if (chart == "pie") {
							dictionary.get(i.date.toUpperCase()).add(percentageOccupationTotal(i.icuBeds, i.infirmaryBeds, i.icuSus, i.icuPrivate));
						}
						
					} else {
						dictionary.put(i.date.toUpperCase(), new ArrayList<Integer>());
						if(chart == "line") {
							dictionary.get(i.date.toUpperCase()).add(percentageOccupation(i.icuBeds, i.icuSus, i.icuPrivate));
						} else if (chart == "pie") {
							dictionary.get(i.date.toUpperCase()).add(percentageOccupationTotal(i.icuBeds, i.infirmaryBeds, i.icuSus, i.icuPrivate));
						}
					}
				} 
				catch(Exception e) {
				}
			}	
			sc.close();
		} catch(FileNotFoundException e) {
			System.out.println("Arquivo n�o encontrado!");
		} 
	
		return dictionary;
	}
	
	/**
	 * Calcula a porcentagem de ocupa��o em rela��o aos leitos da UTI
	 * @param icuBeds
	 * @param icuSus
	 * @param icuPrivate
	 * @return
	 */
	public static Integer percentageOccupation(Integer icuBeds, Integer icuSus, Integer icuPrivate) {
		Integer value = (int) (Math.round(((double) (icuSus + icuPrivate)/ icuBeds ) * 100));
		return value;
	}
	
	/**
	 * M�todo respons�vel por calcular a ocupa��o das UTIs em rela��o ao total de leitos
	 * @param icuBeds
	 * @param infirmaryBeds
	 * @param icuSus
	 * @param privateIcu
	 * @return
	 */
	public static Integer percentageOccupationTotal(Integer icuBeds, Integer infirmaryBeds, Integer icuSus, Integer privateIcu) {
		Integer totalBeds = icuBeds + infirmaryBeds;
		Integer value = (int) Math.round(((double) (icuSus + privateIcu)/totalBeds) * 100);
		return value;
	}

}
