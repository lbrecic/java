package hr.fer.oprpp1.lsystem.impl;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.lsystems.impl.LSystemBuilderImpl;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateTest {

	@Test	
	public void testGenerate1() {
		LSystemBuilderImpl imp = new LSystemBuilderImpl();
		imp.registerProduction('F', "F+F--F+F");
		imp.setAxiom("F");
		assertEquals("F", imp.build().generate(0));
		assertEquals("F+F--F+F", imp.build().generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", imp.build().generate(2));
	}
		
}
