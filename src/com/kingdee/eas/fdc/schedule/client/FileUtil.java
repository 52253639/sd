package com.kingdee.eas.fdc.schedule.client;

import java.io.File;
import java.util.Map;

import javax.swing.JFileChooser;

import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fi.gl.ExtensionFileFilter;

public class FileUtil {
	/**
	 * 描述：通过文件对话框得到文件
	 * 
	 * @throws EASBizException
	 */
	public static String getSelectFile(CoreUIObject ui,boolean isSave,String ext) throws Exception {
		// 检查路径是否存在
		KDFileChooser chooser = new KDFileChooser(new File("."));

		// 禁用选择对话框的“保存类型”为"全部"
		chooser.setAcceptAllFileFilterUsed(true);
		chooser.addChoosableFileFilter(new ExtensionFileFilter(ext, "*."+ext));
		//chooser.addChoosableFileFilter(new ExtensionFileFilter(ext, "*.*"));

		int returnVal = 0;
		if(isSave){
			chooser.setDialogTitle("保存为");
			returnVal = chooser.showSaveDialog(ui);
		}else{
			chooser.setDialogTitle("选择文件");
			returnVal = chooser.showOpenDialog(ui);
		}
		String strFullPath = null;
		/*
		 * JFileChooser.APPROVE_OPTION 为 0
		 * 
		 */
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// 获取文件
			File file = chooser.getSelectedFile();
			strFullPath = file.getPath();
			
		}

		if(isSave && strFullPath != null && !strFullPath.endsWith("."+ext)){
			return strFullPath+"."+ext;
		}else{
			return strFullPath;
		}
	}
	
	
	public static File[] getSelectFiles(CoreUIObject ui, boolean isSave, String ext) throws Exception {
		// 检查路径是否存在
		KDFileChooser chooser = new KDFileChooser(new File("."));
		chooser.setMultiSelectionEnabled(true);

		// 禁用选择对话框的“保存类型”为"全部"
		chooser.setAcceptAllFileFilterUsed(true);
		chooser.addChoosableFileFilter(new ExtensionFileFilter(ext, "*." + ext));
		// chooser.addChoosableFileFilter(new ExtensionFileFilter(ext, "*.*"));

		int returnVal = 0;
		if (isSave) {
			chooser.setDialogTitle("保存为");
			returnVal = chooser.showSaveDialog(ui);
		} else {
			chooser.setDialogTitle("选择文件");
			returnVal = chooser.showOpenDialog(ui);
		}
		String strFullPath = null;
		/*
		 * JFileChooser.APPROVE_OPTION 为 0
		 */
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// 获取文件
			File[] files = chooser.getSelectedFiles();
			return files;
		}
		
		return null;

		// if (isSave && strFullPath != null && !strFullPath.endsWith("." +
		// ext)) {
		// return strFullPath + "." + ext;
		// } else {
		// return strFullPath;
		// }
	}
	
	//添加附件管理到UI
	public static KDPanel getAttachmentUI(CoreUIObject owner,Map uiContext,IObjectValue editData,String title){
        uiContext.put("owner",owner);
        uiContext.put("editData",editData);
        
		// 创建UI对象并显示
		IUIWindow uiWindow = null;
		AttachmentUI ui = null;
		try {
			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(AttachmentUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			
			ui =(AttachmentUI)uiWindow.getUIObject();    	
	    	ui.setTitle(title);
		} catch (UIException e) {
			e.printStackTrace();
			owner.handUIException(e);
		}

    	return ui;
	}
}
