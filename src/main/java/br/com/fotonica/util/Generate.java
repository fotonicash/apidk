package br.com.fotonica.util;

import java.util.Random;

public class Generate {
	
	static final Random random = new Random();
	
	public static String numbers(int qtnDigits) {
		String output = "";
		int k = 0;
		while(k++ < qtnDigits) {
			output += random.nextInt(10);
		}
		return output;
	}
	
}