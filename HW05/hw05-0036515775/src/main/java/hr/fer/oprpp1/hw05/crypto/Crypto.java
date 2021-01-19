package hr.fer.oprpp1.hw05.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import hr.fer.oprpp1.hw05.util.Util;

/**
 * Class {@code Crypto} provides work with encrypted files using AES algorithm.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class Crypto {
	
	/**
	 * Method starts {@code Crypto} program.
	 * 
	 * @param args arguments from command line
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Scanner sc = new Scanner(System.in);
		
		if (args[0].equals("checksha")) {
			checksha(args[1], sc);
		} else if (args[0].equals("encrypt") || args[0].equals("decrypt")) {
			crypt(args[1], sc, args[0], args [2]);
		} else {
			System.out.println("Invalid method called.");
		}
		
		sc.close();
		
	}
	
	/**
	 * Method checks if file was modified depending on calculation of SHA-256 key
	 * and writes a proper output.
	 * 
	 * @param file file which is checked
	 * @param sc scanner for reading user inputs
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static void checksha(String file, Scanner sc) throws NoSuchAlgorithmException, IOException {
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file), 4096)) {
			System.out.println("Please provide expected sha-256 digest for " + file + " :");
			System.out.print("> ");
			
			String dig = sc.nextLine();

			MessageDigest md = MessageDigest.getInstance("SHA-256");

			byte[] buff = new byte[4096];
			int k = bis.read(buff);
			while (k != -1) {
				md.update(buff, 0, k);
				k = bis.read(buff);
			}
			byte[] arr = md.digest();

			String s = Util.bytetohex(arr);

			if (s.equals(dig)) {
				System.out.print("Digesting completed. Digest of " + file + " matches expected digest.");
			} else {
				System.out.print("Digesting completed. Digest of " + file + " does not match the expected digest. Digest was: " + s);
			}
			
			bis.close();
		}
	}
	
	/**
	 * Method encrypts or decrypts data depending on type argument using AES algorithm.
	 * 
	 * @param file file that is encoded/decoded
	 * @param sc scanner for reading user inputs
	 * @param type decides whether algorithm is encrypting or decrypting
	 * @param output output file
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static void crypt(String file, Scanner sc, String type, String output) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file), 4096);
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		System.out.print("> ");
		
		String keyText = sc.nextLine();
		
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		System.out.print("> ");
		
		String ivText = sc.nextLine();
		
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(type.equals("encrypt") ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		
		byte[] buff = new byte[4096];
		int k = bis.read(buff);
		
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(output));
		byte[] arr; 
		
		while (k != -1) {
			arr = cipher.update(buff, 0, k);
			k = bis.read(buff);
			bos.write(arr);
		}
		arr = cipher.doFinal();
		bos.write(arr);
		
		bos.flush();
		bis.close();
		bos.close();
		
		if (type.equals("encrypt")) {
			System.out.print("Encryption ");
		} else {
			System.out.print("Decryption ");
		}
		
		System.out.println("completed. Generated file " + output + " based on file " + file + ".");
	}
}
