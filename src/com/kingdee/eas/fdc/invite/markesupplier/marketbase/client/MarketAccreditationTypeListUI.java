/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.marketbase.client;

import java.awt.event.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketAccreditationTypeFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketAccreditationTypeInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MarketAccreditationTypeListUI extends AbstractMarketAccreditationTypeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketAccreditationTypeListUI.class);
    
    /**
     * output class constructor
     */
    public MarketAccreditationTypeListUI() throws Exception
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
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.actionCancel.setVisible(true);
    	this.actionCancelCancel.setVisible(true);
    	/*
		 * 锁定表格，仅选择一行
		 */
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		/*
		 * 锁向下方向键
		 */
		KDTableHelper.downArrowAutoAddRow(tblMain, false, null);
		/*
		 * 锁回车
		 */
		KDTableHelper.updateEnterWithTab(tblMain, false);
    }
    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }


    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
//        super.actionCancel_actionPerformed(e);
    	if(MsgBox.showConfirm2("确认禁用所选数据？")==2){
    		return;
    	}
        ArrayList list = this.getSelectedIdValues();
        for (int i = 0; i < list.size(); i++) {
			String id = list.get(i).toString();
			MarketAccreditationTypeInfo info = MarketAccreditationTypeFactory.getRemoteInstance().getMarketAccreditationTypeInfo(new ObjectUuidPK(id));
			info.setIsEnable(false);
			MarketAccreditationTypeFactory.getRemoteInstance().update(new ObjectUuidPK(info.getId()), info);
		}
        this.refresh(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
//        super.actionCancelCancel_actionPerformed(e);
    	if(MsgBox.showConfirm2("确认启用所选数据？")==2){
    		return;
    	}
        ArrayList list = this.getSelectedIdValues();
        for (int i = 0; i < list.size(); i++) {
			String id = list.get(i).toString();
			MarketAccreditationTypeInfo info = MarketAccreditationTypeFactory.getRemoteInstance().getMarketAccreditationTypeInfo(new ObjectUuidPK(id));
			info.setIsEnable(true);
			MarketAccreditationTypeFactory.getRemoteInstance().update(new ObjectUuidPK(info.getId()), info);
		}
        this.refresh(e);
    }
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionEdit_actionPerformed(e);
    }
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	super.actionRemove_actionPerformed(e);
    }
    
    protected void tblMain_tableSelectChanged(KDTSelectEvent e)
    		throws Exception {
    	super.tblMain_tableSelectChanged(e);
    	checkSelected();
    	this.actionCancel.setEnabled(false);
		this.actionCancelCancel.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionEdit.setEnabled(false);
    	String id = this.getSelectedKeyValue();
    	MarketAccreditationTypeInfo info = MarketAccreditationTypeFactory.getRemoteInstance().getMarketAccreditationTypeInfo(new ObjectUuidPK(id));
    	if(info.isIsEnable()){
    		this.actionCancel.setEnabled(true);
    	}else{
    		this.actionCancelCancel.setEnabled(true);
    		this.actionRemove.setEnabled(true);
    		this.actionEdit.setEnabled(true);
    	}
    }
    
    
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketAccreditationTypeFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketAccreditationTypeInfo objectValue = new com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketAccreditationTypeInfo();
		
        return objectValue;
    }

}