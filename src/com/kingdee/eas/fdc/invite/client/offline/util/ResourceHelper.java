package com.kingdee.eas.fdc.invite.client.offline.util;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.kingdee.eas.fdc.invite.client.offline.Main;

/**
 * 2007-9-10
 * 
 * @author jianxing_zhou
 * @version 1.0
 */
public class ResourceHelper {

	// 用于保存文件资源,主要是图片
	private static Map resources = new HashMap(18);

	private static Class mainClass = Main.class;

	private static String resFolder = "resource/";

	public static Icon getIcon(String iconName) {
		if (ObjectUtil.isNullString(iconName))
			return null;
		Object obj = resources.get(iconName);
		if (obj != null)
			return (Icon) obj;
		else
			obj = new ImageIcon(mainClass.getResource(resFolder + iconName));
		resources.put(iconName, obj);
		return (Icon) obj;
	}

	public static Image getImage(String imageName) {
		if (ObjectUtil.isNullString(imageName))
			return null;
		Object obj = resources.get(imageName);
		if (obj != null)
			return (Image) obj;
		else
			obj = new ImageIcon(mainClass.getResource(resFolder + imageName)).getImage();
		resources.put(imageName, obj);
		return (Image) obj;
	}

}
