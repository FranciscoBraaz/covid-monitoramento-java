package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Classe responsável por pegar os dados referentes à Enfermaria da classe Reader e realizar os cálculos necessários
 */

public class Infirmary {
	
	public Infirmary() {
	}
	
	/**
	 * Método que pega as informações necessários referentes a enfermaria;
	 * Quando o gráfico de linha é instanciado método percentageOccupation é utilizado para calcular porcentagem de ocupação dos leitos;
	 * Quando o gráfico de pizza for o instanciado, é utilizado o método percentageOccupationTotal para calcular a porcentagem de internados;
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
			System.out.println("Arquivo não encontrado!");
		} 
		
		return dictionary;
	}
	
	/**
	 * Calcula a porcentagem de ocupação em relação aos leitos da Enfermaria
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
	 * Método responsável por calcular a ocupação das enfermarias em relação ao total de leitos
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
