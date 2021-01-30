package application;

import java.util.Scanner;

public class Reader {
	String date;
	Integer infirmaryBeds;
	Integer infirmaryPrivate;
	Integer infirmarySUS;
	Integer icuBeds;
	Integer icuPrivate;
	Integer icuSUS;
	
	
	Reader(String line) throws Exception {
		Scanner sc = new Scanner(line);
		sc.useDelimiter("\\s{2,}");
		String[] dataParts = sc.next().replaceAll(" +", "").split("-");
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
		sc.next();
		icuSUS = sc.nextInt();
		infirmarySUS = sc.nextInt();
		icuPrivate = sc.nextInt();
		infirmaryPrivate = sc.nextInt();
		sc.close();
	}
}
