package hr.fer.oprpp1.hw04.db;

/**
 * Class {@code StudentRecord} represents record of student's 
 * JMBAG, name, surname and final grade.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class StudentRecord {
	private String jmbag;
	private String firstName;
	private String lastName;
	private String finalGrade;
	
	/**
	 * Constructor with all parameters
	 * 
	 * @param jmbag
	 * @param firstName
	 * @param lastName
	 * @param finalGrade
	 */
	public StudentRecord(String jmbag, String firstName, String lastName, String finalGrade) {
		this.finalGrade = finalGrade;
		this.firstName = firstName;
		this.jmbag = jmbag;
		this.lastName = lastName;
	}
	
	/**
	 * Default constructor.
	 */
	public StudentRecord() {
		this("", "", "", "");
	}
	
	@Override
	public boolean equals(Object obj) {
		StudentRecord tmp = (StudentRecord)obj;
		return this.jmbag == tmp.getJmbag();
	}
	
	@Override
	public int hashCode() {
		return this.jmbag.hashCode();
	}
	
	/**
	 * Default setter for student's JMBAG.
	 * 
	 * @param jmbag
	 */
	public void setJmbag(String jmbag) {
		this.jmbag = jmbag;
	}
	
	/**
	 * Default setter for student's first name.
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Default setter for student's last name.
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Default setter for student's final grade.
	 * 
	 * @param finalGrade
	 */
	public void setFinalGrade(String finalGrade) {
		this.finalGrade = finalGrade;
	}
	
	/**
	 * Method returns student's JMBAG.
	 * 
	 * @return student's JMBAG
	 */
	public String getJmbag() {
		return this.jmbag;
	}
	
	/**
	 * Method returns student's first name.
	 * 
	 * @return student's first name
	 */
	public String getFirstName() {
		return this.firstName;
	}
	
	/**
	 * Method returns student's last name.
	 * 
	 * @return student's last name
	 */
	public String getLastName() {
		return this.lastName;
	}
	
	/**
	 * Method returns student's final grade.
	 * 
	 * @return student's final grade
	 */
	public String getFinalGrade() {
		return this.finalGrade;
	}
}
