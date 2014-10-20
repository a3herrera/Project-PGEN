package com.app.utils;

/**
 * <p>
 * <code>Utils</code> includes various functionalities
 * </p>
 * */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;



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
	public static String getEncriptyonMessage(String message,
			EncryptionType type) throws NoSuchAlgorithmException {
		byte[] digest = null;
		byte[] buffer = message.getBytes();
		MessageDigest messageDigest = MessageDigest.getInstance(type.getType());
		messageDigest.reset();
		messageDigest.update(buffer);
		digest = messageDigest.digest();
		return toHexadecimal(digest);
	}

	/**
	 * <p>
	 * Valida si una lista no esta inicializada o esta vacia
	 * </p>
	 * 
	 * @param <E>
	 * */
	public static <E> boolean isEmptyList(List<E> list) {
		return (list == null || list.isEmpty());
	}

	public static boolean compareString(String a, String b, boolean IgnoreCase) {
		if (IgnoreCase) {
			return a.equals(b);
		}
		return a.equalsIgnoreCase(b);
	}

	/**
	 * <p>
	 * Convierte un valor de tipo <code>String</code> a un valor de tipo
	 * <code>Integer</code>
	 * </p>
	 * 
	 * @param cast
	 * 
	 *            Valor que se va a castear a <code>Integer</code>
	 * 
	 * @param defaultValue
	 * 
	 *            Valor que se va a estar retornando cuando no se pueda castear
	 *            el valor deseado
	 * 
	 * @return un numero de tipo integer
	 */
	public static int strToInteger(String cast, int defaultValue) {
		if (!isEmtpy(cast)) {
			try {
				return Integer.parseInt(cast);
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}
}
