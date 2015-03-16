/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 * modify by qinyouzhen,售楼优化 V7.0.3
 * @date 20110613
 */
public class RoomModelTypeListUI extends AbstractRoomModelTypeListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomModelTypeListUI.class);
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	/**
	 * output class constructor
	 */
	public RoomModelTypeListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)){
			actionAddNew.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
		}
	}

	/**
	 * 监听每一笔记录是否可用,从而设置按钮是否可用
	 */
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row != null) {
			Boolean isEnabled = (Boolean) row.getCell("isEnabled").getValue();
			if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
				actionAddNew.setEnabled(false);
				actionEdit.setEnabled(false);
				actionRemove.setEnabled(false);
				actionCancel.setEnabled(false);
				actionCancelCancel.setEnabled(false);
			} else {
				if (isEnabled.booleanValue()) {// 如果是启用,禁用按钮可用,修改按钮不可用
					actionCancel.setEnabled(true);
					actionCancelCancel.setEnabled(false);
				} else {
					actionCancel.setEnabled(false);
					actionCancelCancel.setEnabled(true);
				}
			}
		}
	}

//	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
//		String id = this.getSelectedKeyValue();
//		if (id != null) {
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(
//					new FilterItemInfo("shareRoomModels.id", id));
//			if (SellProjectFactory.getRemoteInstance().exists(filter)) {
//				MsgBox.showInfo("已经被项目引用,不能删除!");
//				return;
//			}
//		}
//		super.actionRemove_actionPerformed(e);
//	}
	protected FDCDataBaseInfo getBaseDataInfo() {
		return new RoomModelTypeInfo();
	}
	
	protected String getEditUIName() {
		return RoomModelTypeEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomModelTypeFactory.getRemoteInstance();
	}
	
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
}