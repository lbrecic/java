package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * Class {@code StudentDB} provides work for querying student database.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class StudentDB {

	public static void main(String[] args) throws IOException {
		
		List<String> lines = Files.readAllLines(
				Paths.get("/home/luka/Workspaces/eclipse-workspace/OPRPP1/HW/HW04/hw04-0036515775/src/test/resources/db"),
				StandardCharsets.UTF_8);
		
		StudentDatabase sdb = new StudentDatabase(lines);
		
		System.out.print('>');
		Scanner sc = new Scanner(System.in);
		
		QueryParser parser;
		while (sc.hasNextLine()) {
			String q = sc.nextLine();
			q = q.trim();

			if (q.startsWith("exit")) {
				sc.close();
				System.out.println("Goodbye!");
				System.exit(-1);
			} else if (q.startsWith("query")) {
				q = q.substring("query".length());
			} else {
				System.out.println("Unknown query. Please, try again.");
			}

			parser = new QueryParser(q);

			List<StudentRecord> list = sdb.filter(new QueryFilter(parser.getQuery()));

			if (list.size() != 0) { 

				int maxName = list.get(0).getFirstName().length();
				int maxSurname = list.get(0).getLastName().length();
				int jmbagSize = list.get(0).getJmbag().length();

				for (StudentRecord rec : list) {
					if (rec.getFirstName().length() > maxName) maxName = rec.getFirstName().length();
					if (rec.getLastName().length() > maxSurname) maxSurname = rec.getLastName().length();
				}


				if (parser.isDirectQuery()) System.out.println("Using index for record retrieval.");

				System.out.print("+=");
				for (int i = 0; i < jmbagSize; i++) System.out.print("=");
				System.out.print("=+=");
				for (int i = 0; i < maxSurname; i++) System.out.print("=");
				System.out.print("=+=");
				for (int i = 0; i < maxName; i++) System.out.print("=");
				System.out.print("=+===+");

				System.out.println();

				for (StudentRecord rec : list) {
					System.out.print("| " + rec.getJmbag() + " | " + rec.getLastName());

					for (int i = 0; i < maxSurname - rec.getLastName().length(); i++) {
						System.out.print(" ");
					}

					System.out.print(" | " + rec.getFirstName());

					for (int i = 0; i < maxName - rec.getFirstName().length(); i++) {
						System.out.print(" ");
					}

					System.out.println(" | " + rec.getFinalGrade() + " |");
				}

				System.out.print("+=");
				for (int i = 0; i < jmbagSize; i++) System.out.print("=");
				System.out.print("=+=");
				for (int i = 0; i < maxSurname; i++) System.out.print("=");
				System.out.print("=+=");
				for (int i = 0; i < maxName; i++) System.out.print("=");
				System.out.print("=+===+");

				System.out.println();
				System.out.println("Records selected: " + list.size());
				System.out.println();

			} else {
				continue;
			}

			System.out.print(">");
		} 
	}
}
