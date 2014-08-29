package com.app.utils;

/**
 * <p>
 * <code>Utils</code> includes various functionalities
 * </p>
 * */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

enum EncryptionType {
	MD2("MD2"), MD5("MD5"), SHA1("SHA-1"), SHA256("SHA-256"), SHA384("SHA-384"), SHA512(
			"SHA-512");

	EncryptionType(String type) {
		this.type = type;
	}

	private String type;

	public String getType() {
		return type;
	}

}

public class Utils {

	public static boolean isEmtpy(String value) {
		return value == null || "".equals(value.trim()) || value.length() < 1;
	}

	/***
	 * Convierte un arreglo de bytes a String usando valores hexadecimales
	 * 
	 * @param digest
	 *            arreglo de bytes a convertir
	 * @return String creado a partir de <code>digest</code>
	 */
	private static String toHexadecimal(byte[] digest) {
		String hash = "";
		for (byte aux : digest) {
			int b = aux & 0xff;
			if (Integer.toHexString(b).length() == 1)
				hash += "0";
			hash += Integer.toHexString(b);
		}
		return hash;
	}

	/***
	 * Encripta un mensaje de texto mediante algoritmo de resumen de mensaje.
	 * 
	 * @param message
	 *            texto a encriptar
	 * @param algorithm
	 *            algoritmo de encriptacion, puede ser: MD2, MD5, SHA-1,
	 *            SHA-256, SHA-384, SHA-512
	 * @return mensaje encriptado
	 */
	public static String getStringMessageDigest(String message,
			EncryptionType type) throws NoSuchAlgorithmException {
		byte[] digest = null;
		byte[] buffer = message.getBytes();
		MessageDigest messageDigest = MessageDigest.getInstance(type.getType());
		messageDigest.reset();
		messageDigest.update(buffer);
		digest = messageDigest.digest();
		return toHexadecimal(digest);
	}
}
