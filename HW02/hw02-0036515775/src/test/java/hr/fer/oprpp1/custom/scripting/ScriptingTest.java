package hr.fer.oprpp1.custom.scripting;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.nodes.*;
import hr.fer.oprpp1.custom.scripting.parser.*;

public class ScriptingTest {
	private String readExample(int n) {
		  try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
		    if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
		    byte[] data = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt").readAllBytes();
		    String text = new String(data, StandardCharsets.UTF_8);
		    return text;
		  } catch(IOException ex) {
		    throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		  }
		}
	
	@Test
	public void testFirstExample() {
		String tmp = readExample(1);
		SmartScriptParser p = new SmartScriptParser(tmp);
		assertTrue(p.getDocumentNode().getChild(0) instanceof TextNode);
		assertEquals(1, p.getDocumentNode().numberOfChildren());
	}
	
	@Test
	public void testSecondExample() {
		String tmp = readExample(2);
		SmartScriptParser p = new SmartScriptParser(tmp);
		assertTrue(p.getDocumentNode().getChild(0) instanceof TextNode);
		assertEquals(1, p.getDocumentNode().numberOfChildren());
	}
	
	@Test
	public void testThirdExample() {
		String tmp = readExample(3);
		SmartScriptParser p = new SmartScriptParser(tmp);
		assertTrue(p.getDocumentNode().getChild(0) instanceof TextNode);
		assertEquals(1, p.getDocumentNode().numberOfChildren());
	}
	
	@Test
	public void testFourthExample() {
		String tmp = readExample(4);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(tmp));
	}
	
	@Test
	public void testFifthExample() {
		String tmp = readExample(5);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(tmp));
	}
	
	@Test
	public void testSixthExample() {
		String tmp = readExample(6);
		SmartScriptParser p = new SmartScriptParser(tmp);
		assertTrue(p.getDocumentNode().getChild(0) instanceof TextNode);
		assertTrue(p.getDocumentNode().getChild(1) instanceof EchoNode);
		assertEquals(3, p.getDocumentNode().numberOfChildren());
	}
	
	@Test
	public void testSeventhExample() {
		String tmp = readExample(7);
		SmartScriptParser p = new SmartScriptParser(tmp);
		assertTrue(p.getDocumentNode().getChild(0) instanceof TextNode);
		assertTrue(p.getDocumentNode().getChild(1) instanceof EchoNode);
		assertEquals(3, p.getDocumentNode().numberOfChildren());
	}
	
	@Test
	public void testEightExample() {
		String tmp = readExample(8);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(tmp));
	}
	
	@Test
	public void testNinthExample() {
		String tmp = readExample(9);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(tmp));
	}


}
