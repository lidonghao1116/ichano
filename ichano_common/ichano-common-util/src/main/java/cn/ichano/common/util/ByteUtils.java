package cn.ichano.common.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * byte自己操作类
 * @author wenjp
 *
 */
public class ByteUtils {
	final static int BUFFER_SIZE = 4096; 

	/**
	 *  合并两个byte数组
	 * @param byte_1
	 * @param byte_2
	 * @return
	 */
		public static byte[] byteMerger(byte[] byte_1, byte[] byte_2){
			byte[] byte_3 = new byte[byte_1.length+byte_2.length];
			System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
			System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
			return byte_3;
		}
		
		/**
		 * 字节追加N个相同的byte
		 * @param src
		 * @param data
		 * @param repeat
		 * @return
		 */
		public static byte[] append(byte[] src,byte data,int repeat){
			byte[] byteDesc = new byte[src.length+repeat];
			System.arraycopy(src, 0, byteDesc, 0, src.length);
			for(int i=src.length;i<byteDesc.length;i++){
				byteDesc[i] = data;
			}
			return byteDesc;
		}
		
		public static String InputStreamTOString(InputStream in) throws Exception{  
	          
	        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
	        byte[] data = new byte[BUFFER_SIZE];  
	        int count = -1;  
	        while((count = in.read(data,0,BUFFER_SIZE)) != -1)  
	            outStream.write(data, 0, count);  
	        
	        data = null;  
	        return new String(outStream.toByteArray(),"UTF-8");  
	    }
		
		public static byte[] InputStreamToBtye(InputStream in) throws Exception{  
	          
	        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
	        byte[] data = new byte[BUFFER_SIZE];  
	        int count = -1;  
	        while((count = in.read(data,0,BUFFER_SIZE)) != -1)  
	            outStream.write(data, 0, count);  
	        
	        data = null;  
	        return outStream.toByteArray();  
	    }
		
		public static void main(String[] args){
			String testa = "12341234";
			byte[] bytes = testa.getBytes();
			byte[] result = append(bytes,(byte)0x20,5);
			System.out.println(new String(result) + "!!");
		}
}
