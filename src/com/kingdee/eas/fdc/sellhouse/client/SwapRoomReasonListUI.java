/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientCtrler;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataEditUI;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.RenameRoomReasonFactory;
import com.kingdee.eas.fdc.sellhouse.SwapRoomReasonFactory;
import com.kingdee.eas.fdc.sellhouse.SwapRoomReasonInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class SwapRoomReasonListUI extends AbstractSwapRoomReasonListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SwapRoomReasonListUI.class);
    
    /**
     * output class constructor
     */
    public SwapRoomReasonListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	//by tim_gao 2010-10-26
    	String selectID = this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(),"id").getValue().toString();
    	String warnings= "修改";
    	if(outPutWarningSentanceAndVerifyCancelorCancelCancelByID(warnings,selectID)){//判断是否启用禁用
    		this.abort();
    	}
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	//by tim_gao 2010-10-26
    	String selectID = this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(),"id").getValue().toString();
    	String warnings= "删除";
    	if(outPutWarningSentanceAndVerifyCancelorCancelCancelByID(warnings,selectID)){//判断是否启用禁用
    		this.abort();
    	}
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        // super.actionCancel_actionPerformed(e);

		if (!isOrgPermission()) {
			FDCMsgBox.showWarning("当前组织下，不能禁用该基础资料！");
			SysUtil.abort();
		}
		setIsEnabled(false);
    }
    
    /**
	 * 判断是否是集团组织
	 * 
	 * @return
	 */
	public static boolean isOrgPermission() {
		OrgUnitInfo currOrgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		String cuid = OrgConstants.DEF_CU_ID;
		if (currOrgUnit != null && cuid.equalsIgnoreCase(currOrgUnit.getCU().getId().toString())) {
			return true;
		}
		return false;
	}

	protected void setIsEnabled(boolean flag) throws Exception {
		//getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_MODIFY);
    
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0)
			return;
	
		String id = tblMain.getRow(activeRowIndex).getCell("id").getValue().toString().trim();
		FDCDataBaseInfo info = getBaseDataInfo();
		info.setId(BOSUuid.read(id));
		info.setIsEnabled(flag);
		//便于启用、禁用时得到编码及名称
		String number = tblMain.getRow(activeRowIndex).getCell("number").getValue().toString().trim();
		String name = tblMain.getRow(activeRowIndex).getCell("name").getValue().toString().trim();
		info.setNumber(number);
		info.setName(name);
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("isEnabled"));
		String message = null;
		if (flag) {
			getBizInterface().updatePartial(info, sic);
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK");
		}
		// ContractCostPropertyFactory.getRemoteInstance().enabled(new
		// ObjectUuidPK(id), info);
		else {
			getBizInterface().updatePartial(info, sic);
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK");
		}
		// ContractCostPropertyFactory.getRemoteInstance().disEnabled(new
		// ObjectUuidPK(id), info);

		setMessageText(message);
		showMessage();
		tblMain.refresh();
		loadFields();
		actionCancel.setEnabled(false);
		actionCancelCancel.setEnabled(false);
	}


    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        //super.actionCancelCancel_actionPerformed(e);
    	if (!isOrgPermission()) {
			FDCMsgBox.showWarning("当前组织下，不能启用该基础资料！");
			SysUtil.abort();
		}
		setIsEnabled(true);
    }

    protected FDCDataBaseInfo getBaseDataInfo() {
    	SwapRoomReasonInfo reasonInfo = new SwapRoomReasonInfo();
    	reasonInfo.setIsEnabled(true);
		return reasonInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SwapRoomReasonFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return SwapRoomReasonEditUI.class.getName();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}
	 /**
     * @author tim_gao
     * @date 2010-10-26
     * @throws BOSException 
     * @throws EASBizException 
     * @description 输入ID和提醒语句判断是否启用
     */
    public boolean outPutWarningSentanceAndVerifyCancelorCancelCancelByID(String words,String selectID) throws EASBizException, BOSException{
    	boolean flag=false;
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("id",selectID));
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));//判断是否启用
    	if(SwapRoomReasonFactory.getRemoteInstance().exists(filter)){//判断记录是否存在
    		MsgBox.showWarning("本记录已经启用，不能"+words+"!");
    		flag=true;
    	}
		return flag;
    }

}