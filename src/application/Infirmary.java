package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Classe respons�vel por pegar os dados referentes � Enfermaria da classe Reader e realizar os c�lculos necess�rios
 */

public class Infirmary {
	
	public Infirmary() {
	}
	
	/**
	 * M�todo que pega as informa��es necess�rios referentes a enfermaria;
	 * Quando o gr�fico de linha � instanciado m�todo percentageOccupation � utilizado para calcular porcentagem de ocupa��o dos leitos;
	 * Quando o gr�fico de pizza for o instanciado, � utilizado o m�todo percentageOccupationTotal para calcular a porcentagem de internados;
	 * @param f
	 * @param chart
	 * @return
	 */
	public static Map<String, List<Integer>> parseListInfirmary (File f, String chart) {
		Map<String, List<Integer>> dictionary = new LinkedHashMap<>();
		Scanner sc2;

		try {
			sc2 = new Scanner(f);
		
			while(sc2.hasNext()) {
				String line = sc2.nextLine();
				try {
					Reader i = new Reader(line);
					if(dictionary.containsKey(i.date.toUpperCase())) {
						if(chart == "line") {
							dictionary.get(i.date.toUpperCase()).add(percentageOccupation(i.infirmaryBeds, i.infirmarySus, i.infirmaryPrivate));
						} else if (chart == "pie") {
							dictionary.get(i.date.toUpperCase()).add(percentageOccupationTotal(i.icuBeds, i.infirmaryBeds, i.infirmarySus, i.infirmaryPrivate));
						}
						
					} else {
						dictionary.put(i.date.toUpperCase(), new ArrayList<Integer>());
						if(chart == "line") {
							dictionary.get(i.date.toUpperCase()).add(percentageOccupation(i.infirmaryBeds, i.infirmarySus, i.infirmaryPrivate));
						} else if (chart == "pie") {
							dictionary.get(i.date.toUpperCase()).add(percentageOccupationTotal(i.icuBeds, i.infirmaryBeds, i.infirmarySus, i.infirmaryPrivate));
						}
					}
				} 
				catch(Exception e) {
				}
			}	
			sc2.close();
		} catch(FileNotFoundException e) {
			System.out.println("Arquivo n�o encontrado!");
		} 
		
		return dictionary;
	}
	
	/**
	 * Calcula a porcentagem de ocupa��o em rela��o aos leitos da Enfermaria
	 * @param infirmaryBeds
	 * @param infirmarySus
	 * @param privateInfirmary
	 * @return
	 */
	public static Integer percentageOccupation(Integer infirmaryBeds, Integer infirmarySus, Integer privateInfirmary) {
		Integer value = (int) (Math.round(((double) (infirmarySus + privateInfirmary)/ infirmaryBeds) * 100));
		return value;
	}
	
	/**
	 * M�todo respons�vel por calcular a ocupa��o das enfermarias em rela��o ao total de leitos
	 * @param icuBeds
	 * @param infirmaryBeds
	 * @param infirmarySus
	 * @param infirmaryPrivate
	 * @return
	 */
	public static Integer percentageOccupationTotal(Integer icuBeds, Integer infirmaryBeds, Integer infirmarySus, Integer infirmaryPrivate) {
		Integer totalBeds = icuBeds + infirmaryBeds;
		Integer value = (int) Math.round(((double) (infirmarySus + infirmaryPrivate)/totalBeds) * 100);
		return value;
	}
	
	
}
