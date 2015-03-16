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
import com.kingdee.eas.fdc.sellhouse.HopedDirectionFactory;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 * modify by qinyouzhen,售楼优化 V7.0.3
 * @date 20110613
 */
public class HopedDirectionListUI extends AbstractHopedDirectionListUI
{
    private static final Logger logger = CoreUIObject.getLogger(HopedDirectionListUI.class);
    SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    /**
     * output class constructor
     */
    public HopedDirectionListUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
	//	actionCancel.setEnabled(false);
	//	actionCancelCancel.setEnabled(false);
	//	actionEdit.setEnabled(false);
		
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
			}else{
				if (isEnabled.booleanValue()) {// 如果是启用,禁用按钮可用,修改按钮不可用
				actionCancel.setEnabled(true);
				actionCancelCancel.setEnabled(false);
				//actionEdit.setEnabled(false);
			    } else {
//			    	this.actionAddNew.setEnabled(true);
//					this.actionEdit.setEnabled(true);
//					this.actionRemove.setEnabled(true);
					actionCancel.setEnabled(false);
					actionCancelCancel.setEnabled(true);
					}
				}
			}
		}
	
	protected FDCDataBaseInfo getBaseDataInfo() {
		return new HopedDirectionInfo();
	}
	
	protected String getEditUIName() {
		return HopedDirectionEditUI.class.getName();
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return HopedDirectionFactory.getRemoteInstance();
	}
	
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
	
}