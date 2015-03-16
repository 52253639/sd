/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class FunctionSettingUI extends AbstractFunctionSettingUI {
	private static final Logger logger = CoreUIObject
			.getLogger(FunctionSettingUI.class);

	SHEFunctionSetting setting = new SHEFunctionSetting();

	/**
	 * output class constructor
	 */
	public FunctionSettingUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.chkIsRefToNote.setSelected(setting.isRefToNote());
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected void btnNo_actionPerformed(ActionEvent e) throws Exception {
		super.btnNo_actionPerformed(e);
		this.destroyWindow();
	}

	protected void btnYes_actionPerformed(ActionEvent e) throws Exception {
		super.btnYes_actionPerformed(e);
		setting.setRefToNote(this.chkIsRefToNote.isSelected());
		setting.save();
		this.destroyWindow();
	}

}