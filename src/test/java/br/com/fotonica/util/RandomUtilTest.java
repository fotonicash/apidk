package br.com.fotonica.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class RandomUtilTest {

	@Test
	public void testGenerateNumbers() {
		String n1 = Generate.numbers(6);
		String n2 = Generate.numbers(6);
		
		assertEquals(6, n1.split("").length);
		assertEquals(6, n2.split("").length);
		assertNotEquals(n1, n2);
	}
	
}
