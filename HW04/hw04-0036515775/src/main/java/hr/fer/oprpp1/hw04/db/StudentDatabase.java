package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class {@code StudentDatabase} represents database of student records.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class StudentDatabase {
	private List<StudentRecord> elements;
	private int index;
	private Map<String, Integer> indexMap;
	
	/**
	 * Default constructor that creates database.
	 * 
	 * @param list
	 */
	public StudentDatabase(List<String> list) {
		elements = new ArrayList<>();
		index = 0;
		indexMap = new HashMap<>();

		String[] s;
		for (String el : list) {
			s = el.split("\\s+");
			StudentRecord tmp = null;
			
			if (s.length == 4) {
				tmp = new StudentRecord(s[0], s[2], s[1], s[3]);
			}
			
			if (s.length == 5) {
				tmp = new StudentRecord(s[0], s[3], s[1] + " " + s[2], s[4]);
			}
			
			
			if (Integer.parseInt(tmp.getFinalGrade()) >= 1 && Integer.parseInt(tmp.getFinalGrade()) <= 5 && !indexMap.containsKey(tmp.getJmbag())) {
				elements.add(tmp);
				indexMap.put(s[0], index);
				index++;
			} else {
				System.err.println("Postoje duplikati - program nije valjan!");
				System.exit(-1);
			}
		}
	}
	
	/**
	 * Method returns student record for given JMBAG.
	 * 
	 * @param jmbag
	 * @return student record with given JMBAG.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		if (!indexMap.containsKey(jmbag)) return null;
		else return elements.get(indexMap.get(jmbag));
	}
	
	/**
	 * Method filters database and returns list of valid student records.
	 * 
	 * @param filter 
	 * @return list of valid student records.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> tmpRec = new ArrayList<>();
		
		for (StudentRecord rec : elements) {
			if (filter.accepts(rec)) {
				tmpRec.add(rec);
			}
		}
		
		return tmpRec;
	}
	
	/**
	 * Method returns list of student records.
	 * 
	 * @return list of student records.
	 */
	public List<StudentRecord> getElements() {
		return this.elements;
	}
}
