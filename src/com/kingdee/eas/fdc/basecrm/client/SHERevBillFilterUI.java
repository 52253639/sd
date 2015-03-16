/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.event.*;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.ListUI;

/**
 * output class name
 */
public class SHERevBillFilterUI extends AbstractSHERevBillFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(SHERevBillFilterUI.class);
    protected ItemAction actionListOnLoad;

	protected ListUI listUI;

	private boolean isLoaded;
    /**
     * output class constructor
     */
    public SHERevBillFilterUI(ListUI listUI, ItemAction actionListOnLoad) throws Exception
    {
    	super();
		this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;
    }

    public void onLoad() throws Exception {	
		super.onLoad();
		this.moneyDefine.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");		
		this.moneyDefine.setEnabledMultiSelection(true);
        this.moneyDefine.setDisplayFormat("$name$");		
        this.moneyDefine.setEditFormat("$number$");		
        this.moneyDefine.setCommitFormat("$number$");		
	        
		if (!isLoaded) {
			this.clear();
		}
		isLoaded = true;
		
		this.kDLabelContainer5.setVisible(false);
		this.moneyDefine.setVisible(false);
    }
    public FilterInfo getFilterInfo() {
    	CustomerParams para = this.getCustomerParams();
		FilterInfo filter = new FilterInfo();
		
		if(!this.isSubmit.isSelected()){
			filter.getFilterItems().add(
					new FilterItemInfo("state",
							FDCBillStateEnum.SUBMITTED_VALUE,CompareType.NOTEQUALS));
		}
		if(!this.isAudit.isSelected()){
			filter.getFilterItems().add(
					new FilterItemInfo("state",
							FDCBillStateEnum.AUDITTED_VALUE,CompareType.NOTEQUALS));
		}
		if(!this.isGenerate.isSelected()){
			filter.getFilterItems().add(
					new FilterItemInfo("isGathered",
							new Boolean(true),CompareType.NOTEQUALS));
		}
		if(!this.unGenerate.isSelected()){
			filter.getFilterItems().add(
					new FilterItemInfo("isGathered",
							new Boolean(false),CompareType.NOTEQUALS));
		}
		if(!this.gathering.isSelected()){
			filter.getFilterItems().add(
					new FilterItemInfo("revBillType",
							RevBillTypeEnum.GATHERING_VALUE,CompareType.NOTEQUALS));
		}
		if(!this.refundment.isSelected()){
			filter.getFilterItems().add(
					new FilterItemInfo("revBillType",
							RevBillTypeEnum.REFUNDMENT_VALUE,CompareType.NOTEQUALS));
		}
		if(!this.transfer.isSelected()){
			filter.getFilterItems().add(
					new FilterItemInfo("revBillType",
							RevBillTypeEnum.TRANSFER_VALUE,CompareType.NOTEQUALS));
		}
		if (para.getCustomerParamsHashMap().containsKey("bizDateFrom")) {
			filter.getFilterItems().add(
					new FilterItemInfo("bizDate",
							FDCDateHelper.getSQLBegin(para.getDate("bizDateFrom")),CompareType.GREATER_EQUALS));
		}
		if (para.getCustomerParamsHashMap().containsKey("bizDateTo")) {
			filter.getFilterItems().add(
					new FilterItemInfo("bizDate",
							FDCDateHelper.getSQLEnd(para.getDate("bizDateTo")),CompareType.LESS_EQUALS));
		}
		
		if(para.getCustomerParam("room")!=null) filter.getFilterItems().add(new FilterItemInfo("room.name", "%"+para.getCustomerParam("room")+"%",CompareType.LIKE));
	    if(para.getCustomerParam("customer")!=null) filter.getFilterItems().add(new FilterItemInfo("customerNames", "%"+para.getCustomerParam("customer")+"%",CompareType.LIKE));
		return filter;
    }
    public void clear() {
    	isSubmit.setSelected(true);
    	isAudit.setSelected(true);
    	isGenerate.setSelected(false);
    	unGenerate.setSelected(true);
    	gathering.setSelected(true);
    	refundment.setSelected(true);
    	transfer.setSelected(true);
    	moneyDefine.setValue(null);
    	bizDateFrom.setValue(FDCHelper.getCurrentDate());
    	bizDateTo.setValue(FDCHelper.getCurrentDate());
    }
    public CustomerParams getCustomerParams() {
		CustomerParams para = new CustomerParams();
		
		para.putBoolean("isSubmit", this.isSubmit.isSelected());
		para.putBoolean("isAudit", this.isAudit.isSelected());
		para.putBoolean("isGenerate", this.isGenerate.isSelected());
		para.putBoolean("unGenerate", this.unGenerate.isSelected());
		para.putBoolean("gathering", this.gathering.isSelected());
		para.putBoolean("refundment", this.refundment.isSelected());
		para.putBoolean("transfer", this.transfer.isSelected());
		if(bizDateFrom.getValue()!=null){
			para.putDate("bizDateFrom", (Date)bizDateFrom.getValue());
		}
		if(bizDateTo.getValue()!=null){
			para.putDate("bizDateTo", (Date)bizDateTo.getValue());
		}
//		if(moneyDefine.getValue()!=null){
//			Object value[]=(Object[]) moneyDefine.getValue();
//			para.addCustomerParam("moneyDefine", value);
//		}
		
		if(this.txtRoom.getText()!=null&&!"".equals(this.txtRoom.getText())){
			para.addCustomerParam("room", this.txtRoom.getText().trim());
	    }
	    if(this.txtCustomer.getText()!=null&&!"".equals(this.txtCustomer.getText())){
	    	para.addCustomerParam("customer", this.txtCustomer.getText().trim());
	    }
		return para;
    }
    public void setCustomerParams(CustomerParams para) {
    	if (para == null)
			return;
    	
    	this.isSubmit.setSelected(para.getBoolean("isSubmit"));
    	this.isAudit.setSelected(para.getBoolean("isAudit"));
    	this.isGenerate.setSelected(para.getBoolean("isGenerate"));
    	this.unGenerate.setSelected(para.getBoolean("unGenerate"));
    	this.gathering.setSelected(para.getBoolean("gathering"));
    	this.refundment.setSelected(para.getBoolean("refundment"));
    	this.transfer.setSelected(para.getBoolean("transfer"));
    	
    	if (para.getCustomerParamsHashMap().containsKey("bizDateFrom")) {
    		this.bizDateFrom.setValue(para.getDate("bizDateFrom"));
    	}
    	if (para.getCustomerParamsHashMap().containsKey("bizDateTo")) {
    		this.bizDateTo.setValue(para.getDate("bizDateTo"));
    	}
    	
    	this.txtRoom.setText(para.getCustomerParam("room"));
    	this.txtCustomer.setText(para.getCustomerParam("customer"));
    }
}