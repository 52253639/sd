/*
 * @(#)ClassLoaderUtil.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Properties;

import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class ClassLoaderUtil {
	public static final String RE_KEY = "re";
	public static final String C_KEY = "c";
	private static final String BEGIN_IDENTIFIER = "begin.identifier";
	public static boolean isNeedRestart = false;

	/**
	 * 目前发现建筑的/lib/industry/eas-gantt-client.jar与房地产的lib/industry/fdc_schedule-client.jar有冲突。
	 * 当使用相应模块时，需要将冲突的包加上后缀bak，保证冲突的包不会被加载进来。
	 * @param isRE
	 *            若为房地产菜单调用，则为true，否则为false
	 */
	public static void modifyClassLoader(boolean isRE) {
		if (isNeedRestart) {
			MsgBox.showInfo("配置修改完成，请重启客户端以便正常使用当前进度模块。如果重启后仍不能正常使用，请删除客户端目录下的/classloader/pkmap.lst文件，重新开始当前操作即可。");
			SysUtil.abort();
		}
		try {
			Properties config = getCRDProperties();
			String[] beginIds = config.getProperty(BEGIN_IDENTIFIER).split(";");
			String c_jarName = config.getProperty(C_KEY);
			String re_jarName = config.getProperty(RE_KEY);
			
			File file = getClassLoaderConfig();
			System.out.println(file + "; exists:" + file.exists());
			boolean isConflict = isRE ? checkUseAll(file, beginIds, c_jarName) : checkUseAll(file, beginIds, re_jarName);

			if (isConflict) {
				if (MsgBox.showConfirm2("系统检测到您当前使用的进度模块（即同时使用了房地产与建筑的进度模块）存在冲突,您需要修改系统配置以便正常使用当前进度模块。 点击确定系统将自动修改配置。") == MsgBox.CANCEL) {
					SysUtil.abort();
					return;
				}
				File bakFile = new File(file.getParent() + File.separator + file.getName() + ".bak");
				if (bakFile.exists())
					bakFile.delete();
				renameTo(file, bakFile);
				
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile)));
				String line = null;
				while ((line = br.readLine()) != null) {
					if (isRE) {
						/*
						 * 若使用房地产进度，将建筑的包加后缀bak，同时将房地产的bak去掉
						 */
						if (isBegin(line, beginIds)) {
							line = line.replace(c_jarName, c_jarName + "bak");
							line = line.replace(re_jarName + "bak", re_jarName);
						}
					} else {
						/*
						 * 若使用建筑进度，将房地产的包加后缀bak，同时将建筑的bak去掉
						 */
						if (isBegin(line, beginIds)) {
							// com/kingdee/eas/fdc/schedule/client= 不能给包加bak，否则界面都打不开，找不到第三方类
							line = line.replace(re_jarName, re_jarName + "bak");
							line = line.replace(c_jarName + "bak", c_jarName);
						}
					}
					if (line.split("=").length > 1) {
						bw.write(line);
						bw.write("\r\n");
					}
				}
				bw.close();
				br.close();
				isNeedRestart = true;
				MsgBox.showInfo("配置修改完成，请重启客户端以便正常使用当前进度模块。如果重启后仍不能正常使用，请删除客户端目录下的/classloader/pkmap.lst文件，重新开始当前操作即可。");
				SysUtil.abort();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void renameTo(File src, File dest) throws IOException {
		if (!src.renameTo(dest)) {
			System.out.println(src.getName() + " rename to " + dest.getName() + " failure.");
			FileInputStream in = new FileInputStream(src);
			FileOutputStream out = new FileOutputStream(dest);
			try {
				byte[] buffer = new byte[1024 * 128];
				int length = 0;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
			} finally {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			}
			System.out.println(src.getName() + " delete :" + src.delete());
		}
	}

	/**
	 * 检查是否有冲突包
	 * @param clFile
	 * @param beginIds 检查哪个包名开头的，如net/sourceforge/ganttproject;org/
	 * @param jarNames 冲突包的包名
	 * @return true 有冲突；false 无冲突
	 * @throws IOException
	 */
	private static boolean checkUseAll2(File clFile, String jarName) throws IOException {
		if (!clFile.exists()) {
			return false;
		}
		System.out.println(jarName);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(clFile)));
		String line = null;
		while ((line = br.readLine()) != null) {
			// "com/kingdee/eas/fdc/schedule/client=" 开头的不算有冲突
			if (line.contains(jarName) && !line.contains(jarName + "bak") && !line.startsWith("com/kingdee/eas/fdc/schedule/client=")) {
				System.out.println("has conflict");
				return true;
			}
		}
		System.out.println(" no conflict ");
		return false;
	}

	/**
	 * 检查是否有冲突包
	 * @param clFile
	 * @param beginIds 检查哪个包名开头的，如net/sourceforge/ganttproject;org/
	 * @param jarNames 冲突包的包名
	 * @return true 有冲突；false 无冲突
	 * @throws IOException
	 */
	private static boolean checkUseAll(File clFile, String[] beginIds, String jarName) throws IOException {
		if (!clFile.exists()) {
			return false;
		}
		System.out.println(jarName);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(clFile)));
		String line = null;
		while ((line = br.readLine()) != null) {
			// 只检查beginIds开头的行
			if (isBegin(line, beginIds) && line.contains(jarName) && !line.contains(jarName + "bak")) {
				System.out.println("has conflict");
				return true;
			}
		}
		System.out.println(" no conflict ");
		return false;
	}

	private static File getClassLoaderConfig() {
		String easClientRoot = System.getProperty("easclient.root");
		String easClient = easClientRoot.substring(0, easClientRoot.indexOf("/deploy/client"));
		return new File(easClient + "/classloader/pkmap.lst");
	}

	/**
	 * 行是否以标识字符串开头
	 * @param line 待检查的行
	 * @param beginIds 标识字符串 
	 * @return 若line以beginIds中的字符串开头，返回true
	 */
	private static boolean isBegin(String line, String[] beginIds) {
		for (String beginId : beginIds) {
			if (line.startsWith(beginId)) {
				return true;
			}
		}
		return false;
	}

	private static Properties getCRDProperties() throws IOException {
		Class clazz = ClassLoaderUtil.class;
		String path = clazz.getPackage().getName().replace('.', '/');
		Properties prop = new Properties();
		URL url = clazz.getClassLoader().getResource(path + "/crd.classloader.properties");
		System.out.println(url);
		prop.load(url.openStream());
		prop.list(System.out);
		return prop;
	}
	
	public static void main(String[] args) {
		modifyClassLoader(false);
	}
}
