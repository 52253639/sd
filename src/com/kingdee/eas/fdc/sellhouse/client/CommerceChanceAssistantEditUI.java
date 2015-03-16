/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceDataTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CommerceChanceAssistantEditUI extends
		AbstractCommerceChanceAssistantEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CommerceChanceAssistantEditUI.class);
	private FullOrgUnitInfo orgUnit = SysContext.getSysContext()
			.getCurrentOrgUnit().castToFullOrgUnitInfo();

	/**
	 * output class constructor
	 */
	public CommerceChanceAssistantEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();

		// 开启编辑页面,F7
		this.prmtSellProject
				.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7SellProjectQuery");
		this.prmtType
				.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7CommerceChanceDataTypeQuery");
		this.prmtSellProject.setEnabled(false);
		this.prmtType.setEnabled(false);
		txtNumber.setMaxLength(80);
		txtName.setMaxLength(80);
		txtDescription.setMaxLength(255);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);

		// 重新获取
		if (this.getUIContext().get("typeInfo") == null) {
			getUIContext().put("typeInfo", editData.getType());
		}
		if (this.getUIContext().get("sellProjectInfo") == null) {
			getUIContext().put("sellProjectInfo", editData.getSellProject());
		}
		if (!OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString())) {
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
		}
		
		this.prmtSellProject.setVisible(false);
		this.contSellProject.setVisible(false);
	}

	protected IObjectValue createNewData() {
		CommerceChanceDataTypeInfo typeInfo = (CommerceChanceDataTypeInfo) this
				.getUIContext().get("typeInfo");
		CommerceChanceAssistantInfo info = new CommerceChanceAssistantInfo();
		info.setType(typeInfo);
		info.setIsEnabled(true);// 默认为启用
		SellProjectInfo sellProjectInfo = (SellProjectInfo) this.getUIContext()
				.get("sellProjectInfo");
		info.setSellProject(sellProjectInfo);

		/**
		 * 添加创建组织字段 add by renliang 2011-9-2
		 */
		if (this.orgUnit != null) {
			info.setOrgUnit(this.orgUnit);
		}
		return info;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		String selectID = editData.getId().toString();
		if (VerifyCancelorCancelCancelById("修改", selectID)) {// 判断是否启用
			this.abort();
		}
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String selectID = editData.getId().toString();
		if (VerifyCancelorCancelCancelById("删除", selectID)) {// 判断是否启用
			this.abort();
		}
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * 判断记录是否启用,修改和删除时调用
	 * 
	 * @param words
	 * @param selectID
	 * @return
	 * @throws Exception
	 */
	public boolean VerifyCancelorCancelCancelById(String words, String selectId)
			throws Exception {
		boolean flag = false;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", selectId));
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.valueOf(true)));// 判断是否启用
		if (getBizInterface().exists(filter)) {// 判断记录是否存在
			MsgBox.showWarning("本记录已经启用，不能" + words + "!");
			flag = true;
		}
		return flag;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CommerceChanceAssistantFactory.getRemoteInstance();
	}

	protected FDCDataBaseInfo getEditData() {
		return this.editData;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
}