package com.kingdee.eas.fdc.invite.client.offline;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.kingdee.eas.fdc.invite.client.offline.util.ObjectUtil;

public class Application {
	private static Logger logger = Logger.getLogger(Application.class);

	public void run() {
		copyDll();
//		setFileAssociation();
	}

	private static void copyDll() {
		String dllPath = System.getProperty("user.dir");
		if (ObjectUtil.isNullString(dllPath))
			dllPath = "c:";
		try {
			File file = new File(dllPath + "\\PrintProxy2.dll");
			System.setProperty("easclient.root", dllPath);
			System.setProperty("jdic", dllPath);
			if (!file.exists()) {
				// URL url = Application.class.getResourceAsStream("resource\\PrintProxy2.dll");
				InputStream in = Application.class.getResourceAsStream("resource\\PrintProxy2.dll");
				FileOutputStream out = new FileOutputStream(file);
				int b = 0;
				byte[] bytes = new byte[1024];
				while ((b = in.read(bytes)) > 0)
					out.write(bytes, 0, b);
				in.close();
				out.close();
			}
//			file = new File(dllPath + "\\jdic.dll");
//			if (!file.exists()) {
//				// URL url = Application.class.getResource("resource\\jdic.dll");
//				InputStream in = Application.class.getResourceAsStream("resource\\jdic.dll");
//				FileOutputStream out = new FileOutputStream(file);
//				int b = 0;
//				byte[] bytes = new byte[1024];
//				while ((b = in.read(bytes)) > 0)
//					out.write(bytes, 0, b);
//				in.close();
//				out.close();
//			}
			// file = new File(dllPath + "\\LanchFile.bat");
			// if (!file.exists()) {
			// FileWriter writer = new FileWriter(file);
			// writer.write("start %1 %2");
			// writer.flush();
			// writer.close();
			// }
		} catch (IOException ioe) {
			logger.error(ioe);
		}

	}

//	private void setFileAssociation() {
//		Preferences preferences = Preferences.userRoot();
//		if (preferences.getBoolean("isFirst", true)) {
//			AssociationService assocService = new AssociationService();
//			Association assoc = new Association();
//			String description = "房地产(fdc)报价文件";
//			assoc.setDescription(description);
//			assoc.setIconFileName("shell32.dll,1");
//			// assoc.setIconFileName("c:\\fdc_icon.gif");
//			assoc.addFileExtension("fdc");
//			// Action oneAction = new Action("", "");
//			// assoc.addAction(oneAction);
//			if (assocService.getFileExtensionAssociation("fdc") != null) {
//				try {
//					assocService.unregisterUserAssociation(assoc);
//				} catch (AssociationNotRegisteredException e) {
//					logger.error(e);
//				} catch (RegisterFailedException e) {
//					logger.error(e);
//				}
//			}
//			try {
//				assocService.registerUserAssociation(assoc);
//			} catch (AssociationAlreadyRegisteredException e) {
//				logger.error(e);
//			} catch (RegisterFailedException e) {
//				logger.error(e);
//			}
//			preferences.putBoolean("isFirst", false);
//		}
//	}
}
