/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.util.Date;
import java.util.Map;

import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class VersionInputUI extends AbstractVersionInputUI {

	/**
	 * output class constructor
	 */
	public VersionInputUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output btnConfirm_actionPerformed method
	 */
	protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		if (this.getOprtState().equals("Edit")) {
			String text = this.txtVersionName.getText();
			if (text == null || text.equals("")) {
				MsgBox.showWarning(AimCostHandler
						.getResource("versionNameNull"));
				return;
			}
			String objectId = (String) this.getUIContext().get("objectId");
			String sql = "select * where orgOrProId ='" + objectId
					+ "' and versionName='" + text + "'";
			if (this.getUIContext().get("aimId") != null) {
				sql += " and id !='"
						+ (String) this.getUIContext().get("aimId") + "'";
			}
			if (AimCostFactory.getRemoteInstance().exists(sql)) {
				MsgBox.showWarning(AimCostHandler
						.getResource("versionNameRepeat"));
				return;
			}

			this.getUIContext().put("versionName", text);
			this.getUIContext().put("description",
					this.txtDescription.getText());
			this.getUIContext().put("recenseDate",
					this.pkRecenseDate.getValue());
			this.getUIContext().put("isConfirm", Boolean.TRUE);
		}
		this.destroyWindow();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.txtVersionName.setMaxLength(80);
		this.txtDescription.setMaxLength(80);
		if (this.getUIContext().get("recenseDate") == null) {
			this.pkRecenseDate.setValue(null);
		} else {
			this.pkRecenseDate.setValue(this.getUIContext().get("recenseDate"));
		}
		this.txtVersionNumber.setText((String) this.getUIContext().get(
				"versionNum"));
		this.txtVersionName.setText((String) this.getUIContext().get(
				"versionName"));
		this.txtDescription.setText((String) this.getUIContext().get(
				"description"));
		if (this.getOprtState().equals("View")) {
			this.txtVersionName.setEnabled(false);
			this.txtDescription.setEnabled(false);
		}
		if(this.getUIContext().get("measureStage")!=null){
			FDCClientHelper.initComboMeasureStage(comboMeasureStage,false);
			GlUtils.setSelectedItem(comboMeasureStage, (MeasureStageInfo)getUIContext().get("measureStage"));
		}
	}

	/**
	 * output btnCancel_actionPerformed method
	 */
	protected void btnCancel_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		this.destroyWindow();

	}

	public static Map showEditUI(String objectId, String aimId,
			String versionNum, String verisonName, String description,
			Date recenseDate, MeasureStageInfo measureStage) throws UIException {
		UIContext uiContext = new UIContext();
		uiContext.put(uiContext.OWNER, FDCClientHelper.getCurrentActiveWindow());
		uiContext.put("objectId", objectId);
		uiContext.put("aimId", aimId);
		uiContext.put("versionNum", versionNum);
		uiContext.put("versionName", verisonName);
		uiContext.put("description", description);
		uiContext.put("recenseDate", recenseDate);
		uiContext.put("measureStage", measureStage);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory
				.createUIFactory(UIFactoryName.MODEL)
				.create(VersionInputUI.class.getName(), uiContext, null, "Edit");
		uiWindow.show();
		return uiWindow.getUIObject().getUIContext();
	}

	public static void showViewUI(String versionNum, String verisonName,
			String description, Date recenseDate, MeasureStageInfo measureStage) throws UIException {
		UIContext uiContext = new UIContext();
		uiContext.put("versionNum", versionNum);
		uiContext.put("versionName", verisonName);
		uiContext.put("description", description);
		uiContext.put("recenseDate", recenseDate);
		uiContext.put("measureStage", measureStage);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory
				.createUIFactory(UIFactoryName.MODEL)
				.create(VersionInputUI.class.getName(), uiContext, null, "View");
		uiWindow.show();
	}
}