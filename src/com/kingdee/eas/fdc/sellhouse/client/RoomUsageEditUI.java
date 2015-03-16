/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.PossessionStandardInfo;
import com.kingdee.eas.fdc.sellhouse.ProductDetialFactory;
import com.kingdee.eas.fdc.sellhouse.ProductDetialInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDesFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomUsageFactory;
import com.kingdee.eas.fdc.sellhouse.RoomUsageInfo;
import com.kingdee.eas.fdc.sellhouse.SellProject;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomUsageEditUI extends AbstractRoomUsageEditUI {
	private static final Logger logger = CoreUIObject.getLogger(RoomUsageEditUI.class);
	private ItemAction[] hiddenAction = { this.actionCancel, this.actionCancelCancel, this.actionSave, this.actionCopy, this.actionPrint, this.actionPrintPreview };

	/**
	 * output class constructor
	 */
	public RoomUsageEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		RoomUsageInfo ru = this.editData;
		ru.setDescription(this.txtDesription.getText());
		super.storeFields();
	}

	protected IObjectValue createNewData() {
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		RoomUsageInfo ru = new RoomUsageInfo();
		ru.setSellProject(sellProject);
		ru.setDescription("");
		this.txtDesription.setText("");
		return ru;
	}

	protected void loadData() throws Exception {

		super.loadData();
		RoomUsageInfo ru = this.editData;
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		this.sellProjectNum.setText(sellProject.getNumber());
		this.sellProjectName.setText(sellProject.getName());
		this.txtDesription.setText(ru.getDescription());

	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selItemColl = super.getSelectors();
		selItemColl.add("description");

		return selItemColl;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		FDCClientHelper.setActionVisible(this.hiddenAction, false);
		this.initOldData(this.editData);
		this.sellProjectNum.setEnabled(false);
		this.sellProjectName.setEnabled(false);
		this.txtNumber.setMaxLength(80);
		this.txtName.setMaxLength(80);
		this.txtDesription.setMaxLength(80);
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
	}

	
	protected ICoreBase getBizInterface() throws Exception {
		return RoomUsageFactory.getRemoteInstance();
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		RoomUsageInfo info = (RoomUsageInfo) this.editData;
		if (info == null) {
			return;
		}
		String id = info.getId().toString();
		if (id != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("noise.id", id));
			if (RoomDesFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已被房间定义引用，不能删除！");
				SysUtil.abort();
			}
			if (RoomFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已被房间引用，不能删除！");
				SysUtil.abort();
			}
			super.actionRemove_actionPerformed(e);
		}
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception{
		super.actionEdit_actionPerformed(e);
		this.sellProjectNum.setEnabled(false);
		this.sellProjectName.setEnabled(false);		
	}

}