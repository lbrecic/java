package hr.fer.oprpp1.hw05.util;

/**
 * Class {@code Util} provides work with byte arrays and hexadecimal strings.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class Util {	
	
	/**
	 * Method converts byte array to hexadecimal string.
	 * 
	 * @param arr array that is converted
	 * @return hexadecimal string of the given array
	 */
	public static String bytetohex(byte[] arr) {
		StringBuilder sb = new StringBuilder();
		
		for (byte b  : arr) {
			sb.append(String.format("%02X", b));
		}
		
		return sb.toString().toLowerCase();
	}
	
	/**
	 * Method converts hexadecimal string to byte array.
	 * 
	 * @param keyText hex-string that is converted
	 * @return byte array of given hex-string
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText.length() % 2 == 0) {
			String s = keyText.toLowerCase();
			int n = s.length();
			
			byte[] arr = new byte[n/2];
			
			for (int i = 0; i < n; i += 2) {
				arr[i/2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) +
						(Character.digit(s.charAt(i+1), 16)));
			}
			
			return arr;
			
		} else {
			throw new IllegalArgumentException();
		}
	}
}
