package me.helpeachother.springsecurity.util;

import lombok.NoArgsConstructor;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.Key;
import java.util.Base64;

/**
 * <pre>
 * Class Name : CryptoAES.java
 * Description : AES 암호화 복호화 모듈
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 *  -----------------------------------------------
 *  2021. 3. 24.		swkim	신규작성
 * </pre>
 *
 * @author swklim
 * @since 2021. 3. 24.
 */
@NoArgsConstructor
public class CryptoAES { 

	/**
	 * GCM 모드에서 256비트 키를 갖는 AES
	 */
	private static final String transformation = "AES/GCM/NoPadding";

	/**
	 * SecretKeySpec key defualt 
	 */
	private Key key = new SecretKeySpec(
							"qeiuwhrsdkjfbxcbviuhqeorghfcv=-0"
								.getBytes(Charset.forName("UTF-8"))
							, "AES");
	/**
	 * Use custom key.
	 */
	CryptoAES(byte[] key) {
		if (key.length != 32) throw new IllegalArgumentException("Wrong key length. True key length is 32 byte");
		this.key = new SecretKeySpec(key, "AES");
	}

	/**
	 * String to encrypt
	 * AES/GCM/NoPadding 암호화 후 Base64 로 인코딩 하여 처리
	 * @param src
	 * @return String Encrypt Message
	 * @throws Exception
	 */
	public String encrypt(String src) throws Exception {
		return new String(Base64.getEncoder().encode(encrypt(src.getBytes(Charset.forName("UTF-8")))));
	}

	private byte[] encrypt(byte[] src) throws Exception {
		Cipher cipher = Cipher.getInstance(transformation);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] iv = cipher.getIV(); // See question #1
		assert iv.length == 12; // See question #2
		byte[] cipherText = cipher.doFinal(src);
		assert cipherText.length == src.length + 16; // See question #3
		byte[] message = new byte[12 + src.length + 16]; // See question #4
		System.arraycopy(iv, 0, message, 0, 12);
		System.arraycopy(cipherText, 0, message, 12, cipherText.length);
		return message;
	}

	/**
	 * String to decrypt
	 * Base64 로 디코딩 하여 처리 AES/GCM/NoPadding 복호화
	 * @param src
	 * @return String Decrypt Message
	 * @throws Exception
	 */
	public String decrypt(String src) throws Exception {
		return new String(decrypt(Base64.getDecoder().decode(src)));
	}

	private byte[] decrypt(byte[] message) throws Exception {
		if (message.length < 12 + 16) throw new IllegalArgumentException();
		Cipher cipher = Cipher.getInstance(transformation);
		GCMParameterSpec params = new GCMParameterSpec(128, message, 0, 12);
		cipher.init(Cipher.DECRYPT_MODE, key, params);
		return cipher.doFinal(message, 12, message.length - 12);
	  }
}