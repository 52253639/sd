/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.schedule.client.ScheduleClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellOrderInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 * modify by qinyouzhen,售楼优化 V7.0.3
 * @date 20110613
 */
public class RoomModelTypeEditUI extends AbstractRoomModelTypeEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomModelTypeEditUI.class);
	  SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	/**
	 * output class constructor
	 */
	public RoomModelTypeEditUI() throws Exception {
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
		RoomModelTypeInfo roomModelType = new RoomModelTypeInfo();
		roomModelType.setIsEnabled(true);
		return roomModelType;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomModelTypeFactory.getRemoteInstance();
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
//					new FilterItemInfo("buildingProperty.id", selectID));
//			if (BuildingFactory.getRemoteInstance().exists(filter)) {
//				MsgBox.showInfo("已经被楼栋使用,不能删除!");
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