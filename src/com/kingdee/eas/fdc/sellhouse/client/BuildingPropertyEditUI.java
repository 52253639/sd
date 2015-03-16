/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.schedule.client.ScheduleClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDesFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * output class name
 * modify by qinyouzhen,售楼优化 V7.0.3
 * @date 20110613
 */
public class BuildingPropertyEditUI extends AbstractBuildingPropertyEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(BuildingPropertyEditUI.class);
	  SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	/**
	 * output class constructor
	 */
	public BuildingPropertyEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}else{
			if (STATUS_ADDNEW.equals(getOprtState())) {
				actionEdit.setEnabled(false);
				actionRemove.setEnabled(false);
			}else if(STATUS_EDIT.equals(getOprtState())){
				actionEdit.setEnabled(false);
			}else if(STATUS_VIEW.equals(getOprtState())){
				actionSubmit.setEnabled(false);
			}else{
				actionAddNew.setEnabled(true);
				actionEdit.setEnabled(true);
				actionRemove.setEnabled(true);
			}
		}
	}

	protected IObjectValue createNewData() {
		BuildingPropertyInfo property = new BuildingPropertyInfo();
		property.setIsEnabled(true);
		return property;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return BuildingPropertyFactory.getRemoteInstance();
	}
	
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	
	protected FDCDataBaseInfo getEditData() {
		return editData;
	}
	
	protected KDBizMultiLangBox getNameCtrl() {
		return null;
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String selectID = editData.getId().toString();
		if (verifyCancelorCancelCancelByID("删除",
				selectID)) {// 判断是否启用禁用
			return;
//		}else if(selectID != null) {
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(
//					new FilterItemInfo("shareRoomModels.id", selectID));
//			if (SellProjectFactory.getRemoteInstance().exists(filter)) {
//				MsgBox.showInfo("已经被项目引用,不能删除!");
//				return;
//			}
//			
//			filter = new FilterInfo();
//			filter.getFilterItems().add(
//					new FilterItemInfo("roomModel.id", selectID));
//			if (RoomFactory.getRemoteInstance().exists(filter)) {
//				MsgBox.showInfo("已经被房间引用,不能删除!");
//				return;
//			}
		}
		super.actionRemove_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		String selectID = editData.getId().toString();
		if (verifyCancelorCancelCancelByID("修改",
				selectID)) {// 判断是否启用禁用
			return;
		}
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * 判断记录是否启用,修改和删除时调用
	 * 
	 * @param words
	 * @param selectID
	 * @return
	 * @throws Exception
	 */
	public boolean verifyCancelorCancelCancelByID(
			String words, String selectID) throws Exception {
		boolean flag = false;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", selectID));
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.valueOf(true)));// 判断是否启用
		if (getBizInterface().exists(filter)) {// 判断记录是否存在
			MsgBox.showWarning("本记录已经启用，不能" + words + "!");
			flag = true;
		}
		return flag;
	}
}