package application;


import java.util.Scanner;

/**
 * Classe responsável por ler o arquivo separando as informações necessários utilizando um delimitador
 */

public class Reader {
	String date;
	Integer infirmaryBeds;
	Integer infirmaryPrivate;
	Integer infirmarySus;
	Integer icuBeds;
	Integer icuPrivate;
	Integer icuSus;
	Integer totalPatients;

	
	Reader(String line) throws Exception {
		Scanner sc = new Scanner(line);
		sc.useDelimiter("\\;");
		String[] dataParts = sc.next().replaceAll(" +", "").replaceAll("/", "-").split("-");
		date = dataParts[1];
		sc.next();
		sc.next();
		sc.next();
		sc.next();
		sc.next();
		sc.next();
		sc.next();
		sc.next();
		sc.next();
		icuBeds = sc.nextInt();
		infirmaryBeds = sc.nextInt();
		totalPatients = sc.nextInt();
		icuSus = sc.nextInt();
		infirmarySus = sc.nextInt();
		icuPrivate = sc.nextInt();
		infirmaryPrivate = sc.nextInt();
		sc.close();
	}

}
