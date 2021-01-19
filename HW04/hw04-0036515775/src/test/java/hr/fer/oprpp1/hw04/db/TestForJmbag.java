package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestForJmbag {
	
	private class AlwaysTrue implements IFilter {

		@Override
		public boolean accepts(StudentRecord record) {
			return true;
		}
		
	}
	
	private class AlwaysFalse implements IFilter{

		@Override
		public boolean accepts(StudentRecord record) {
			return false;
		}
		
	}
	
	@Test
	public void testForJMBAG1() throws IOException {
		List<String> lines = Files.readAllLines(
				Paths.get("/home/luka/Workspaces/eclipse-workspace/HW/HW04/hw04-0036515775/src/test/resources/db"),
				StandardCharsets.UTF_8);
		
		StudentDatabase sdb = new StudentDatabase(lines);
		IFilter filter = new AlwaysTrue();
		
		assertEquals(sdb.getElements(), sdb.filter(filter));
	}
	
	@Test
	public void testForJMBAG2() throws IOException {
		List<String> lines = Files.readAllLines(
				Paths.get("/home/luka/Workspaces/eclipse-workspace/HW/HW04/hw04-0036515775/src/test/resources/db"),
				StandardCharsets.UTF_8);
		
		StudentDatabase sdb = new StudentDatabase(lines);
		IFilter filter = new AlwaysFalse();
		
		assertEquals(new ArrayList<StudentRecord>(), sdb.filter(filter));
	}
}
