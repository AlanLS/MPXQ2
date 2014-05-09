package io;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

public class Crypt
{
	/**
	 * This function encrypts and decrypts the input parameter string
	 * 
	 * @param textToCrypting
	 *            - string that will be encrypted and decrypted
	 */
	static synchronized public String cryptingDecrypting(String textToCrypting, String _keyword)
	{
		// Object of this class provides the functionality for
		// encryption and decryption.
		Cipher cipher;
		try
		{
			// parameter "DES" specifies type of cipher we want to create
			// through the factory method. It includes algorithm, mode and
			// padding. You can define only algorithm and in that case default
			// values will be used for mode and padding.
			cipher = Cipher.getInstance("DES");
		}
		catch (final NoSuchAlgorithmException ex)
		{
			// requested cryptographic algorithm is not available
			System.out.println(ex.toString());
			return textToCrypting;
		}
		catch (final NoSuchPaddingException ex)
		{
			// requested padding mechanism is not available in the environment.
			System.out.println(ex.toString());
			return textToCrypting;
		}
		// The lenght of keyString is 8. It's important characteristic
		// for encryption
		// String keyString = _keyword;
		final byte[] keyData = _keyword.getBytes();
		// key object specifies a secret key
		// it uses to construct the secret key for our cipher object from a byte
		// array
		final SecretKeySpec key = new SecretKeySpec(keyData, 0, keyData.length, "DES");
		try
		{
			// initializing with setting up the type of operation it's to be
			// used for. In this case - for encryption.
			cipher.init(Cipher.ENCRYPT_MODE, key);
		}
		catch (final InvalidKeyException ex)
		{
			// given key object is inappropriate for initializing this cipher
			System.out.println(ex.toString());
			return textToCrypting;
		}
		int cypheredBytes = 0;
		System.out.println("Source string: " + textToCrypting);
		byte[] inputBytes = textToCrypting.getBytes();
		final byte[] outputBytes = new byte[256];
		try
		{
			// doFinal method encrypts data to outputBytes array.
			// for unknown or big counts of data update method more recomended
			// counts of crypted bytes saved inside cypheredBytes variable
			cypheredBytes = cipher.doFinal(inputBytes, 0, inputBytes.length, outputBytes, 0);
		}
		catch (final IllegalStateException ex)
		{
			System.out.println(ex.toString());
			return textToCrypting;
		}
		catch (final ShortBufferException ex)
		{
			System.out.println(ex.toString());
			return textToCrypting;
		}
		catch (final IllegalBlockSizeException ex)
		{
			System.out.println(ex.toString());
			return textToCrypting;
		}
		catch (final BadPaddingException ex)
		{
			System.out.println(ex.toString());
			return textToCrypting;
		}
		String str = new String(outputBytes, 0, cypheredBytes);
		inputBytes = str.getBytes();
		System.out.println("Crypted string:" + str);
		try
		{
			// change state of the cipher object for decrypting
			cipher.init(Cipher.DECRYPT_MODE, key);
		}
		catch (final InvalidKeyException ex)
		{
			System.out.println(ex.toString());
			return textToCrypting;
		}
		try
		{
			// decrypts data
			cypheredBytes = cipher.doFinal(inputBytes, 0, inputBytes.length, outputBytes, 0);
		}
		catch (final IllegalStateException ex)
		{
			System.out.println(ex.toString());
			return textToCrypting;
		}
		catch (final ShortBufferException ex)
		{
			System.out.println(ex.toString());
			return textToCrypting;
		}
		catch (final IllegalBlockSizeException ex)
		{
			System.out.println(ex.toString());
			return textToCrypting;
		}
		catch (final BadPaddingException ex)
		{
			System.out.println(ex.toString());
			return textToCrypting;
		}
		str = new String(outputBytes, 0, cypheredBytes);
		System.out.println("Decrypted string:" + str);
		System.out.println(" Ok!   " + str);
		return str;
	}

	/**
	 * This function encrypts the input parameter string
	 * 
	 * @param textToCrypting
	 *            - string that will be encrypted
	 */
	static synchronized public String encrypting(String textToCrypting, String _keyword)
	{
		// Object of this class provides the functionality for
		// encryption and decryption.
		Cipher cipher;
		try
		{
			// parameter "DES" specifies type of cipher we want to create
			// through the factory method. It includes algorithm, mode and
			// padding. You can define only algorithm and in that case default
			// values will be used for mode and padding.
			cipher = Cipher.getInstance("DES");
		}
		catch (final NoSuchAlgorithmException ex)
		{
			// requested cryptographic algorithm is not available
			System.out.println(ex.toString());
			return null;
		}
		catch (final NoSuchPaddingException ex)
		{
			// requested padding mechanism is not available in the environment.
			System.out.println(ex.toString());
			return null;
		}
		// The length of keyString is 8. It's important characteristic for
		// encryption
		final byte[] keyData = _keyword.getBytes();
		// key object specifies a secret key
		// it uses to construct the secret key for our cipher object from a byte
		// array
		final SecretKeySpec key = new SecretKeySpec(keyData, 0, keyData.length, "DES");
		try
		{
			// initializing with setting up the type of operation it's to be
			// used for. In this case - for encryption.
			cipher.init(Cipher.ENCRYPT_MODE, key);
		}
		catch (final InvalidKeyException ex)
		{
			// given key object is inappropriate for initializing this cipher
			System.out.println(ex.toString());
			return null;
		}
		int cypheredBytes = 0;
		System.out.println("Source string: " + textToCrypting);
		byte[] inputBytes = textToCrypting.getBytes();
		final byte[] outputBytes = new byte[256];
		for (int i = 0; i < outputBytes.length; ++i)
		{
			outputBytes[i] = 0;
		}
		try
		{
			// doFinal method encrypts data to outputBytes array.
			// for unknown or big counts of data update method more recomended
			// counts of crypted bytes saved inside cypheredBytes variable
			cypheredBytes = cipher.doFinal(inputBytes, 0, inputBytes.length, outputBytes, 0);
		}
		catch (final IllegalStateException ex)
		{
			System.out.println(ex.toString());
			return null;
		}
		catch (final ShortBufferException ex)
		{
			System.out.println(ex.toString());
			return null;
		}
		catch (final IllegalBlockSizeException ex)
		{
			System.out.println(ex.toString());
			return null;
		}
		catch (final BadPaddingException ex)
		{
			System.out.println(ex.toString());
			return null;
		}
		final String str = new String(outputBytes, 0, cypheredBytes);
		// try
		{
			inputBytes = str.getBytes(/* "UTF-8" */);
		}
		// catch (UnsupportedEncodingException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		System.out.println("Crypted string:" + str);
		return str;
	}
}
