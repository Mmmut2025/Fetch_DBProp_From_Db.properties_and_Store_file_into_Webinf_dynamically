package digest.message;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class GenerateMAC_and_DIGEST {
	public static String generateMAC(byte[] fileData , String key , String algoName) throws NoSuchAlgorithmException, InvalidKeyException {
		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), algoName);
		
		Mac Hmac = Mac.getInstance(algoName);
		Hmac.init(secretKey);
		
		byte[] data= Hmac.doFinal(fileData);
		
		return toHashString(data);
	}
	
	
	public static String toHashString(byte[] fileData) {
		StringBuffer sb = new StringBuffer();
		for(byte b : fileData) {
			sb.append(Integer.toHexString(0xFF & b));
		}
		return sb.toString();
	}
	
	
	public static String generateDigest(byte[] fileData , String algoName) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(algoName);
		byte[] digest = md.digest(fileData);

		return toHashString(digest);
	}
}
