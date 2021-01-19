package hr.fer.oprpp1.custom.scripting;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.lexer.*;

public class LexerTest {
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
		SmartScriptLexer l = new SmartScriptLexer(tmp);
		assertEquals(SmartScriptTokenType.TEXT, l.nextToken().getType());
	}
	
	@Test
	public void testSecondExample() {
		String tmp = readExample(2);
		SmartScriptLexer l = new SmartScriptLexer(tmp);
		assertEquals(SmartScriptTokenType.TEXT, l.nextToken().getType());
	}
	
	@Test
	public void testThirdExample() {
		String tmp = readExample(3);
		SmartScriptLexer l = new SmartScriptLexer(tmp);
		assertEquals(SmartScriptTokenType.TEXT, l.nextToken().getType());
	}
	
	@Test
	public void testFourthExample() {
		String tmp = readExample(4);
		assertThrows(SmartScriptLexerException.class, () -> new SmartScriptLexer(tmp).nextToken());
	}
	
	@Test
	public void testFifthExample() {
		String tmp = readExample(5);
		assertThrows(SmartScriptLexerException.class, () -> new SmartScriptLexer(tmp).nextToken());
	}
	
	@Test
	public void testSixthExample() {
		String tmp = readExample(6);
		SmartScriptLexer l = new SmartScriptLexer(tmp);
		assertEquals(SmartScriptTokenType.TEXT, l.nextToken().getType());
		assertEquals(SmartScriptTokenType.TAGOPEN, l.nextToken().getType());
	}
	
	@Test
	public void testSeventhExample() {
		String tmp = readExample(7);
		SmartScriptLexer l = new SmartScriptLexer(tmp);
		assertEquals(SmartScriptTokenType.TEXT, l.nextToken().getType());
		assertEquals(SmartScriptTokenType.TAGOPEN, l.nextToken().getType());
	}
	
	@Test
	public void testEightExample() {
		String tmp = readExample(8);
		SmartScriptLexer l = new SmartScriptLexer(tmp);
		assertEquals(SmartScriptTokenType.TEXT, l.nextToken().getType());
		assertEquals(SmartScriptTokenType.TAGOPEN, l.nextToken().getType());
		assertEquals(SmartScriptTokenType.TEXT, l.nextToken().getType());
		assertEquals(SmartScriptTokenType.EOF, l.nextToken().getType());
		assertThrows(SmartScriptLexerException.class, () -> l.nextToken());;
	}
	
	@Test
	public void testNinthExample() {
		String tmp = readExample(9);
		SmartScriptLexer l = new SmartScriptLexer(tmp);
		assertEquals(SmartScriptTokenType.TEXT, l.nextToken().getType());
		assertEquals(SmartScriptTokenType.TAGOPEN, l.nextToken().getType());
		assertThrows(SmartScriptLexerException.class, () -> l.nextToken());
	}
}
