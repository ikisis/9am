package sbb.seed.util;

import java.util.Random;

public abstract class RandomUtils {

	private static char[] digits = { 
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
			
	};

	private static int digitsSize = digits.length;
	
	
	public static String getString(int length){
				
		char[] base = new char[length];
		
		Random random = new Random(System.nanoTime());
		
		for(int i = 0; i< base.length; i++){
			base[i] = digits[random.nextInt(digitsSize)];
		
		}
		
		return new String(base);
	}
	
}
