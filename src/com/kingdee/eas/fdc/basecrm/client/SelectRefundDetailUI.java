/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class SelectRefundDetailUI extends AbstractSelectRefundDetailUI
{
    private static final Logger logger = CoreUIObject.getLogger(SelectRefundDetailUI.class);
    
    /**
     * output class constructor
     */
    public SelectRefundDetailUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
        super.onLoad();
        
    	this.kDTable1.checkParsed();
    	this.kDTable1.getStyleAttributes().setLocked(true);
    	
    	Object[] custObjs = (Object[])this.getUIContext().get("CustomerObjs");
    	FDCBillInfo relateBillInfo = (FDCBillInfo)this.getUIContext().get("RelateBillInfo");
    	SHERevBillInfo currRevBillInfo = (SHERevBillInfo)this.getUIContext().get("CurrRevBillInfo");
    	
    	String custIds = "";
    	CRMHelper.sortCollection(custObjs, "name", true);
    	if(custObjs!=null && custObjs.length>0){
    		for (int i = 0; i < custObjs.length; i++) {
    			SHECustomerInfo custInfo = (SHECustomerInfo)custObjs[i];
    			if(custInfo!=null)
    				custIds += "," + custInfo.getId().toString();
			}
    	}
    	if(custIds.equals("")) {
    		FDCMsgBox.showWarning("客户必须选择！");
    		this.abort();
    	}
    	custIds = custIds.substring(1);
    	
		String filterBillSql = " where ";
		if(relateBillInfo!=null) filterBillSql += " parent.relateBizBillId = '"+relateBillInfo.getId().toString()+"'";
		if(relateBillInfo!=null && !custIds.equals(""))  filterBillSql += " and "; 
		if(!custIds.equals(""))	{
			filterBillSql += " parent.id in (select fid from t_bdc_sheRevBill where charindex('"+custIds+"',fcustomerIds)>0) ";
		}
		
		//必须是收款单，且非保存状态
		filterBillSql += " and parent.revBillType = '"+RevBillTypeEnum.GATHERING_VALUE+"' and parent.state != '"+FDCBillStateEnum.SAVED_VALUE+"' ";
		//且对象不能是本单据 （防止直接修改收款单为退款单，此时看到的收款明细是自己的	）
		if(currRevBillInfo!=null && currRevBillInfo.getId()!=null)
			filterBillSql += " and parent.id != '"+currRevBillInfo.getId().toString()+"' ";
		
		SHERevBillEntryCollection revEntryColl = SHERevBillEntryFactory.getRemoteInstance()
					.getSHERevBillEntryCollection("select *,moneyDefine.name,moneyDefine.moneyType,parent.bizDate  " + filterBillSql);  //
		for(int i=0;i<revEntryColl.size();i++){
			SHERevBillEntryInfo revEntryInfo = revEntryColl.get(i);
			IRow addRow = this.kDTable1.addRow();
			addRow.setUserObject(revEntryInfo);
			addRow.getCell("id").setValue(revEntryInfo.getId().toString());
			addRow.getCell("select").setValue(new Boolean(false));
    		addRow.getCell("moneyDefine").setValue(revEntryInfo.getMoneyDefine());
    		addRow.getCell("revAmount").setValue(revEntryInfo.getRevAmount());
    		addRow.getCell("hasTransfAmount").setValue(revEntryInfo.getHasTransferAmount());
    		addRow.getCell("hasRefundAmount").setValue(revEntryInfo.getHasRefundmentAmount());
    		addRow.getCell("bizDate").setValue(revEntryInfo.getParent().getBizDate());
    		if(revEntryInfo.getRemainAmount().compareTo(new BigDecimal("0"))<=0)
    			addRow.getStyleAttributes().setBackground(new Color(0xFCFBDF));
		}
    }
    
    
    protected void btnSure_actionPerformed(ActionEvent e) throws Exception {
    	SHERevBillEntryCollection retObjColl = new SHERevBillEntryCollection();
    	for (int i = 0; i < this.kDTable1.getRowCount(); i++) {
			IRow currRow = this.kDTable1.getRow(i);
			Boolean isSelected = (Boolean)currRow.getCell("select").getValue();
			if(isSelected==null) isSelected = new Boolean(false);
			if(isSelected.booleanValue()){
				SHERevBillEntryInfo revBillInfo = (SHERevBillEntryInfo)currRow.getUserObject();
				revBillInfo.setRevAmount(revBillInfo.getRemainAmount().negate());
				revBillInfo.setTransferFromEntryId(revBillInfo.getId().toString());
				revBillInfo.setId(null);
				retObjColl.add(revBillInfo);
			}
		}
    	
    	this.getUIContext().put("RetObjColl", retObjColl);    	
    	this.getUIWindow().close();
    }
    
    public void btnCancel_actionPerformed(java.awt.event.ActionEvent e){
    	this.getUIWindow().close();
    }
    
    protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
    	if(e.getType()!=1) return;
    	if(e.getRowIndex()<0) return;
    	
    	int selectColumnIndex = this.kDTable1.getColumnIndex("select");
    	IRow selectRow = this.kDTable1.getRow(e.getRowIndex());
    	if(e.getColIndex()==selectColumnIndex){
    		Boolean isSelected = (Boolean)selectRow.getCell("select").getValue();
			if(isSelected==null) isSelected = new Boolean(false);
			selectRow.getCell("select").setValue(new Boolean(!isSelected.booleanValue()));
    	}
    }
   
    protected void kDTable1_editStopped(KDTEditEvent e) throws Exception {     	
    	super.kDTable1_editStopped(e);
    }

}