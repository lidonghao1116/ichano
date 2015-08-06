package cn.ichano.common.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import cn.ichano.common.config.Env;

public class EncryptUtil {

	
//	private static  Blowfish blowfish = new Blowfish(Env.getConfig().getValue(
//			"common.filter.encrypt.key","dayton9780"));
	private static  Blowfish blowfish = new Blowfish(Env.getConfig().getValue(
			"common.filter.encrypt.key","dayton9780"));
	
	/**
	 * bluefis加密
	 * @param value
	 * @return
	 */
	public static byte[] getBluefishEncryptValue(String value){

		byte[] data = value.getBytes(Charset.forName("utf-8"));
		byte[] decodeContent = getBluefishEncrypt(data);

		return decodeContent;
	}

	public static byte[] getBluefishEncrypt(byte[] data) {
		byte[] decodeContent = blowfish.encrypt(data);
		return decodeContent;
	}
	
	public static String getBluefishDencryptValue(byte[] decodeContent){
		
		byte[] result = decrypt(decodeContent);
		return new String(result,Charset.forName("utf-8"));
	}

	/**
	 * 解密字符串
	 * @param decodeContent
	 * @return
	 */
	public static byte[] decrypt(byte[] decodeContent) {
		byte[] result = blowfish.decrypt(decodeContent);
		return result;
	}
	
	
	public static String encodeBase64String(byte[] data){
		String base64 = new String(Base64.encodeBase64(data));
		return base64;
	}
	
	public static byte[] encodeBase64(byte[] data){
		return Base64.encodeBase64(data);
	}
	
	public static byte[] decodeBase64(byte[] data){
		return Base64.decodeBase64(data);
	}
	
	public static String bin2hex(String inputStr){
		Blowfish cipher = new Blowfish();
		cipher.setKey("dayton9780");
		if (inputStr.length() % 8 != 0) {
			int num = 8 - inputStr.length() % 8;
			StringBuffer s = new StringBuffer(inputStr);
			for (int i = 0; i < num; i++) {
				s = s.append(" ");
			}
			inputStr = s.toString();
		}
		byte[] enc_in = inputStr.getBytes();
		byte[] enc_out = new byte[enc_in.length];
		cipher.encrypt(enc_in, 0, enc_out, 0, inputStr.length());
		return byte2hex(enc_out);
	}
	public static String bin2String(String s){
		byte[] b=  hex2byte(s.getBytes());
		return declassified(b ,b.length).trim();
	}
	public static String byte2hex(byte[] b) {
		String hs = "";
		String tmp = "";
		for (int n = 0; n < b.length; n++) {
			tmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (tmp.length() == 1) {
				hs = hs + "0" + tmp;
			} else {
				hs = hs + tmp;
			}
		}
		tmp = null;
		return hs.toUpperCase();
	}
	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0) {
			throw new IllegalArgumentException("Hex2Byte error.");
		}
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		b = null;
		return b2;
	}
	public static String declassified(byte[] declassifiedStr, int contentLength) {
		Blowfish cipher = new Blowfish();
		cipher.setKey("dayton9780");
		byte[] dec_out = new byte[declassifiedStr.length];
		cipher.decrypt(declassifiedStr, 0, dec_out, 0, contentLength);
		String decStr = new String(dec_out);
		return decStr;
	}
	
	
	public static String SHA1(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
 
    public static String SHA(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
 
    public static String MD5(String input) {
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(input.getBytes());
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < md.length; i++) {
                String shaHex = Integer.toHexString(md[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
 
    /**
     * 加密
     *
     * @param content
     *            需要加密的内容
     * @param password
     *            加密密码
     * @return
     */
    public static byte[] encryptAES(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
 
    /**
     * 解密
     *
     * @param content
     *            待解密内容
     * @param password
     *            解密密钥
     * @return
     */
    public static byte[] decryptAES(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
