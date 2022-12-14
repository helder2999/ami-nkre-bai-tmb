package nosi.core.webapp.security;
/**
 * @author: Emanuel Pereira
 * 17 Nov 2017
 */

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import nosi.core.webapp.Igrp;

public class EncrypDecrypt {

	private static final String ALGO = "AES/ECB/PKCS5Padding";// "AES/ECB/PKCS5PADDING"
	private static final String CHARTSET = "UTF-8";
	private static final String SECRET_KEY_SPEC = "AES";
	private static final String SECRET_KEY_ALGO = "SHA-1";
	public static final String SECRET_KEY_ENCRYPT_DB = "igrp.conf.db";
	private static final String SECRET_KEY_PUBLIC_PAGE = "&igrp.public.page.encrypt/";
	
	public static String encrypt(String content) {
		return encryptURL(content, getSecretKey()).replace(" ", "+");
	}

	public static String encryptPublicPage(String content) {
		return encryptURL(content, SECRET_KEY_PUBLIC_PAGE).replace(" ", "+");
	}
	
	
	public static String encryptURL(String content, String secretKey) {
		try {
			Cipher cipher = Cipher.getInstance(ALGO);
			cipher.init(Cipher.ENCRYPT_MODE, generateSecretKey(secretKey));			
			return new String(Base64.getUrlEncoder().encode(cipher.doFinal(content.getBytes(CHARTSET))),CHARTSET);
		} catch (Exception e) {

		}
		return content;
	}
	
	public static String encrypt(String content, String secretKey) {
		try {
			Cipher cipher = Cipher.getInstance(ALGO);
			cipher.init(Cipher.ENCRYPT_MODE, generateSecretKey(secretKey));			
			return new String(Base64.getEncoder().encode(cipher.doFinal(content.getBytes(CHARTSET))),CHARTSET);
		} catch (Exception e) {

		}
		return content;
	}
//	-------------------------------DECRYPTS-----------------------------------#
	
	public static String decrypt(String content) {
		String customHeader = Igrp.getInstance() != null ? Igrp.getInstance().getRequest().getHeader("X-IGRP-REMOTE")
				: null;
		if (customHeader != null && customHeader.equals("1") && content.split("/").length==3 )
			return content;
		final String replace = content.replace(" ", "+");	
		return decryptURL(replace, getSecretKey());
	}

	public static String decryptPublicPage(String content) {
		String customHeader = Igrp.getInstance() != null ? Igrp.getInstance().getRequest().getHeader("X-IGRP-REMOTE")
				: null;
		if (customHeader != null && customHeader.equals("1") && content.split("/").length==3)
			return content;
		final String replace = content.replace(" ", "+");	
		return decryptURL(replace, SECRET_KEY_PUBLIC_PAGE);
	}
	
	public static String decryptURL(String content, String secretKey) {
		try {			
			Cipher cipher = Cipher.getInstance(ALGO);
			cipher.init(Cipher.DECRYPT_MODE, generateSecretKey(secretKey));			
			return new String(cipher.doFinal(Base64.getUrlDecoder().decode(content.getBytes(StandardCharsets.UTF_8))),CHARTSET);
		} catch (Exception e) {

		}
		return null;
	}
	
	public static String decrypt(String content, String secretKey) {
		try {			
			Cipher cipher = Cipher.getInstance(ALGO);
			cipher.init(Cipher.DECRYPT_MODE, generateSecretKey(secretKey));			
			return new String(cipher.doFinal(Base64.getDecoder().decode(content.getBytes(StandardCharsets.UTF_8))),CHARTSET);
		} catch (Exception e) {

		}
		return null;
	}
//	-----------------------------------------------------------------------------
	private static String getSecretKey() {
		return Igrp.getInstance().getRequest().getSession().getId();
	}

	public static SecretKeySpec generateSecretKey(String key) {
		try {
			byte[] byteKey = key.getBytes(CHARTSET);
			MessageDigest sha = MessageDigest.getInstance(SECRET_KEY_ALGO);
			byteKey = sha.digest(byteKey);
			byteKey = Arrays.copyOf(byteKey, 16);
			return new SecretKeySpec(byteKey, SECRET_KEY_SPEC);
		} catch (Exception e) {

		}
		return null;
	}

}
