package cn.ichano.common.util;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件助手类 标题、简要说明. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2014年12月11日 下午9:07:53
 * <p>
 * Company: 南京云恩通讯科技有限公司
 * <p>
 * 
 * @author wenjianping
 * @version 1.0.0
 */

public class FileUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

	public static List<String> readLines(File file, String encode) {

		List<String> lineList = new ArrayList<String>();
		if (!file.exists()) {
			return lineList;
		}

		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			isr = new InputStreamReader(new FileInputStream(file), encode);
			br = new BufferedReader(isr);

			String temp = null;
			while ((temp = br.readLine()) != null) {
				lineList.add(temp);
			}
		} catch (Exception e) {
			LOGGER.error("readFileError,fileName:{}", file.getName(), e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return lineList;
	}

	public static String getFileContent(File file, String encode) {
		if (!file.exists()) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			isr = new InputStreamReader(new FileInputStream(file), encode);
			br = new BufferedReader(isr);

			String temp = null;
			while ((temp = br.readLine()) != null) {
				sb = sb.append(temp);
			}
		}
		catch (Exception e) {
			LOGGER.error("readFileError,fileName:{}", file.getName(), e);
		}
		finally {
			if (br != null) {
				try {
					br.close();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (isr != null) {
				try {
					isr.close();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}

	/**
	 * 将字符内容写入文件
	 * 
	 * @param content
	 * @param file
	 * @return
	 */
	public static boolean saveFile(String content, String file, boolean isappend) {

		File f = new File(file);
		OutputStreamWriter out = null;
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件


			if (!f.exists()) {
				f.createNewFile();
			}
			out = new OutputStreamWriter(new FileOutputStream(f, true), "UTF-8");
			out.write(content);
			out.flush();


		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return true;

	}

	public static boolean saveFile(String content, String file) {
		return saveFile(content, file, true);
	}

	public static boolean save(String fileName, byte[] content) {
		File f = new File(fileName);
		FileOutputStream out = null;
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			out = new FileOutputStream(f, true);
			out.write(content);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {

			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	public static byte[] read(String fileName) {

		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		File f = null;
		FileInputStream in = null;
		try {
			f = new File(fileName);

			if (!f.exists()) {
				return null;
			}
			in = new FileInputStream(f);
			int c;
			byte buffer[] = new byte[1024];
			while ((c = in.read(buffer)) != -1) {
				for (int i = 0; i < c; i++)
					bytes.write(buffer[i]);
			}
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return bytes.toByteArray();
	}

	public static String getFileContent(String string, String encode) {
		return getFileContent(new File(string), encode);
	}
}
