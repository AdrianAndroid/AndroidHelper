package cn.kuwo.networker.crypt;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {	
	 
	private static MessageDigest md5 = null;
	private synchronized static MessageDigest getMessageDigest() {
		if (md5 == null) {
			try {
				md5 = MessageDigest.getInstance("md5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		return md5;
	}
	
	public static byte[] encode(String origin) {
		if (origin == null || origin.length() == 0) {
			return null;
		}
		
		MessageDigest md = getMessageDigest();
		if (md == null) {
			throw new IllegalAccessError("no md5 algorithm");
		}
		
		byte[] bytes = md.digest(origin.getBytes());
		return bytes;
	}
	
	public static byte[] encode16(String origin) {
		if (origin == null || origin.length() == 0) {
			return null;
		}
		
		MessageDigest md = getMessageDigest();
		if (md == null) {
			throw new IllegalAccessError("no md5 algorithm");
		}
		byte[] bytes = md.digest(origin.getBytes());
		byte[] dstBytes = new byte[8];
		System.arraycopy(bytes, 4, dstBytes, 0, 8);
		bytes = null;
		return dstBytes;
	}
	
	
	public static byte[] encode(String origin, String enc) throws UnsupportedEncodingException {
		if (origin == null || origin.length() == 0) {
			return null;
		}
		
		MessageDigest md = getMessageDigest();
		if (md == null) {
			throw new IllegalAccessError("no md5 algorithm");
		}
		
		byte[] bytes = md.digest(origin.getBytes(enc));
		return bytes;
	}
	
	public static byte[] encode16(String origin, String enc) throws UnsupportedEncodingException {
		if (origin == null || origin.length() == 0) {
			return null;
		}
		
		MessageDigest md = getMessageDigest();
		if (md == null) {
			throw new IllegalAccessError("no md5 algorithm");
		}
		
		byte[] bytes = md.digest(origin.getBytes(enc));
		byte[] dstBytes = new byte[8];
        System.arraycopy(bytes, 4, dstBytes, 0, 8);
        bytes = null;
        return dstBytes;
	}
	
	public static byte[] encode(byte[] bytes) {
		if(bytes == null || bytes.length == 0) {
			return null;
		}
		
		MessageDigest md = getMessageDigest();
		if (md == null) {
			throw new IllegalAccessError("no md5 algorithm");
		}
		
		byte[] resultbytes = md.digest(bytes);
		return resultbytes;
	}
	
	public static byte[] encode16(byte[] bytes) {
		if(bytes == null || bytes.length == 0) {
			return null;
		}
		
		MessageDigest md = getMessageDigest();
		if (md == null) {
			throw new IllegalAccessError("no md5 algorithm");
		}
		
		byte[] resultbytes = md.digest(bytes);
		byte[] dstBytes = new byte[8];
        System.arraycopy(resultbytes, 4, dstBytes, 0, 8);
        bytes = null;
        return dstBytes;
	}
	
	/**
	 * MD5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
			byte[] byteArray = messageDigest.digest();
			StringBuffer md5StrBuff = new StringBuffer();
			for (int i = 0; i < byteArray.length; i++) {
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
					md5StrBuff.append("0").append(
							Integer.toHexString(0xFF & byteArray[i]));
				else
					md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
			return md5StrBuff.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String fileMD5(String inputFile) throws IOException {
		// 缓冲区大小（这个可以抽出一个参数）
		int bufferSize = 256 * 1024;
		FileInputStream fileInputStream = null;
		DigestInputStream digestInputStream = null;
		try {
			// 拿到一个MD5转换器（同样，这里可以换成SHA1）
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			// 使用DigestInputStream
			fileInputStream = new FileInputStream(inputFile);
			digestInputStream = new DigestInputStream(fileInputStream,messageDigest);
			// read的过程中进行MD5处理，直到读完文件
			byte[] buffer =new byte[bufferSize];
			while (digestInputStream.read(buffer) > 0);
			// 获取最终的MessageDigest
			messageDigest= digestInputStream.getMessageDigest();
			// 拿到结果，也是字节数组，包含16个元素
			byte[] resultByteArray = messageDigest.digest();
			// 同样，把字节数组转换成字符串
			return byteArrayToHex(resultByteArray);
		} catch (NoSuchAlgorithmException e) {
			return null;
		} finally {
			IOUtils.closeIoStream(digestInputStream);
			IOUtils.closeIoStream(fileInputStream);
		}
	}

	public static String byteArrayToHex(byte[] byteArray) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < byteArray.length; n++) {
			stmp = (Integer.toHexString(byteArray[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
			if (n < byteArray.length - 1) {
				hs = hs + "";
			}
		}
		return hs;
	}
}
