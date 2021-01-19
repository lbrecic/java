package hr.fer.oprpp1.hw02;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.*;

public class SmartScriptTester {
	public static void main(String[] args) throws IOException {
		String docBody = new String(
							Files.readAllBytes(Paths.get("/home/bluka/Workspaces/eclipse-workspace/HW/HW02/hw02-0036515775/src/test/resources/extra/primjer8.txt")),
							StandardCharsets.UTF_8);
		
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			e.printStackTrace();
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch(Exception e) {
			
			e.printStackTrace();
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		System.out.println(originalDocumentBody);							
	}
}
