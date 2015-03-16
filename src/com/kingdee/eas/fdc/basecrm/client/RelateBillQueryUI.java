/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.RelatBizType;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.ITranCustomerEntry;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TranCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.CoreBaseCollection;

/**
 * output class name
 */
public class RelateBillQueryUI extends AbstractRelateBillQueryUI
{
    private static final Logger logger = CoreUIObject.getLogger(RelateBillQueryUI.class);    
      
    private Map transactionIdMap = new HashMap();  //业务id(String) 和 交易主线id(BOSID)的映射关系
    
    public RelateBillQueryUI() throws Exception
    {
        super();
    }

    protected void btnSeach_actionPerformed(ActionEvent e) throws Exception {
    	String filterText = this.kDTextField1.getText();
    	if(filterText==null) filterText = "";
    	else filterText = filterText.trim();
    	if(filterText.equals("")){
    		FDCMsgBox.showWarning("查询条件必须录入！");
    		return;
    	}
    	
    	String searchType = "CustomerName";
    	String searchTypeTxt = (String)this.comboSeachType.getSelectedItem();
    	if(searchTypeTxt.equals("房间"))
    		searchType = "RoomNumber";
    		
    	this.kDTable1.getStyleAttributes().setLocked(true);
    	this.kDTable1.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	this.kDTable1.removeRows();
    	
//    	UserInfo thisUserInfo = SysContext.getSysContext().getCurrentUserInfo();
    	String permitSpIdSql = "'"+((SellProjectInfo)((NewSHERevBillEditUI)this.getUIContext().get("ParentObject")).prmtsellProject.getValue()).getId().toString()+"'";
    	
    	if(this.radioPreOrder.isSelected()){
    		fileTableByPreOrderData(searchType,filterText,permitSpIdSql);
    	}else if(this.radioPrePur.isSelected()){
    		fileTableByPrePurData(searchType,filterText,permitSpIdSql);
    	}else if(this.radioPurchase.isSelected()){
    		fileTableByPurchaseData(searchType,filterText,permitSpIdSql);
    	}else if(this.radioSign.isSelected()){
    		fileTableBySignData(searchType,filterText,permitSpIdSql);
    	}
    	
    }
    
    /**查询交易关联的业务id的字符串，以便查询具体的业务单据*/
    private String getTransactionBizIdFilterStr(String searchType,String filterStr,String permitSpIdSql) throws BOSException{
    	//transactionIdMap = new HashMap();
    	String sqlFilter = " where customerNames like '%"+filterStr+"%' ";
    	if(searchType.equals("RoomNumber")) sqlFilter = " where room.name like '%"+filterStr+"%' ";
    	
    	String filterIds = "";
    	TransactionCollection transColl = TransactionFactory.getRemoteInstance()
    								.getTransactionCollection("select id,billId " + sqlFilter + //" and isValid != 1 " +
    										" and room.building.sellProject.id in ("+permitSpIdSql+")");
    	for (int i = 0; i < transColl.size(); i++) {
    		TransactionInfo transInfo = transColl.get(i); 
			if(transInfo.getBillId()!=null) {
				//transactionIdMap.put(transInfo.getBillId().toString(), transInfo.getId());
				filterIds += ",'"+transInfo.getBillId().toString()+"'";
			}
		}
    	if(filterIds.length()>0) filterIds = filterIds.substring(1);
    		
    	return filterIds;
    }
    
    
    private void fileTableByPurchaseData(String searchType,String filterText,String permitSpIdSql) throws BOSException{
    	String viewString = "";
    	RevBillTypeEnum revBillType = (RevBillTypeEnum)((NewSHERevBillEditUI)this.getUIContext().get("ParentObject")).revBillType.getSelectedItem();
    	if(revBillType!=null && revBillType.equals(RevBillTypeEnum.gathering)){	//收款时，必须是当前交易对应的业务
        	//查询符合条件的交易信息，获取交易上的业务id,然后再查询具体的业务单据
        	String filterIdsStr = getTransactionBizIdFilterStr(searchType,filterText,permitSpIdSql);
        	if(filterIdsStr.equals("")) return;    	
        	
        	viewString = "select customer.name,customer.number,head.*,head.room.name,head.saleManNames " +
							"where head.id in ("+ filterIdsStr +") " ;  		
        	viewString += " and (head.bizState = '"+TransactionStateEnum.PURAPPLE_VALUE+"' or head.bizState = '"+TransactionStateEnum.PURAUDIT_VALUE+"') ";
    	}else{
        	String sqlFilter = " where customer.name like '%"+filterText+"%' ";
        	if(searchType.equals("RoomNumber")) sqlFilter = " where head.room.name like '%"+filterText+"%' ";
        	viewString = "select customer.name,customer.number,head.*,head.room.name,head.saleManNames " + sqlFilter;
        	viewString += " and head.sellProject.id in ("+permitSpIdSql+") ";
    	}

    	CoreBaseCollection sinEntryColl = PurCustomerEntryFactory.getRemoteInstance().getCollection(viewString);	//
    	Map sinPurMap = new HashMap();
    	for(int i=0;i<sinEntryColl.size();i++) {
    		PurCustomerEntryInfo entryInfo = (PurCustomerEntryInfo)sinEntryColl.get(i);
    		PurchaseManageInfo sinInfo = entryInfo.getHead();
    		if(sinInfo==null || sinInfo.getId()==null) continue;
    		String sinIdStr = sinInfo.getId().toString();
    		if(sinPurMap.get(sinIdStr)==null){
    			sinInfo.put("customerNames", entryInfo.getCustomer()==null?"":entryInfo.getCustomer().getName());
    			sinPurMap.put(sinIdStr, entryInfo.getHead());  			
    		}else{
    			sinInfo = (PurchaseManageInfo)sinPurMap.get(sinIdStr);
    			if(entryInfo.getCustomer()==null || entryInfo.getCustomer().getName()==null) continue;
    			String customerNames = (String)sinInfo.get("customerNames");
    			customerNames += ";"+ entryInfo.getCustomer().getName();
    			sinInfo.put("customerNames", customerNames);  			
    		}
    	}
    	
    	Iterator iterator = sinPurMap.keySet().iterator();
    	while(iterator.hasNext()){
    		String sinIdStr = (String)iterator.next();
    		PurchaseManageInfo sinInfo = (PurchaseManageInfo)sinPurMap.get(sinIdStr);
    		
    		IRow addRow = this.kDTable1.addRow();
    		addRow.getCell("ID").setValue(sinInfo.getId());
    		addRow.getCell("number").setValue(sinInfo.getNumber());
    		addRow.getCell("state").setValue(sinInfo.getBizState());
    		addRow.getCell("signDate").setValue(sinInfo.getBookedDate());
    		addRow.getCell("room").setValue(sinInfo.getRoom());
    		addRow.getCell("customer").setValue(sinInfo.get("customerNames"));
    		addRow.getCell("saler").setValue(sinInfo.getSaleManNames());
    		addRow.setUserObject(sinInfo);
    	}
    }
    
    
    private void fileTableBySignData(String searchType,String filterText,String permitSpIdSql) throws BOSException{
    	String viewString = "";
    	RevBillTypeEnum revBillType = (RevBillTypeEnum)((NewSHERevBillEditUI)this.getUIContext().get("ParentObject")).revBillType.getSelectedItem();
    	if(revBillType!=null && revBillType.equals(RevBillTypeEnum.gathering)){	//收款时，必须是当前交易对应的业务
        	//查询符合条件的交易信息，获取交易上的业务id,然后再查询具体的业务单据
        	String filterIdsStr = getTransactionBizIdFilterStr(searchType,filterText,permitSpIdSql);
        	if(filterIdsStr.equals("")) return;    	
        	
        	viewString = "select customer.name,head.*,head.room.name,head.saleManNames " +
							"where head.id in ("+ filterIdsStr +") " ;  		
        	viewString += " and (head.bizState = '"+TransactionStateEnum.SIGNAPPLE_VALUE+"' or head.bizState = '"+TransactionStateEnum.SIGNAUDIT_VALUE+"') ";
    	}else{
        	String sqlFilter = " where customer.name like '%"+filterText+"%' ";
        	if(searchType.equals("RoomNumber")) sqlFilter = " where head.room.name like '%"+filterText+"%' ";
        	viewString = "select customer.name,head.*,head.room.name,head.saleManNames " + sqlFilter; 
        	viewString += " and head.sellProject.id in ("+permitSpIdSql+") ";
    	}    	
 	
    	SignCustomerEntryCollection sinEntryColl = SignCustomerEntryFactory.getRemoteInstance().getSignCustomerEntryCollection(viewString);	//
    	Map sinPurMap = new HashMap();
    	for(int i=0;i<sinEntryColl.size();i++) {
    		SignCustomerEntryInfo entryInfo = sinEntryColl.get(i);
    		SignManageInfo sinInfo = entryInfo.getHead();
    		if(sinInfo==null || sinInfo.getId()==null) continue;
    		String sinIdStr = sinInfo.getId().toString();
    		if(sinPurMap.get(sinIdStr)==null){
    			sinInfo.put("customerNames", entryInfo.getCustomer()==null?"":entryInfo.getCustomer().getName());
    			sinPurMap.put(sinIdStr, entryInfo.getHead());
    		}else{
    			sinInfo = (SignManageInfo)sinPurMap.get(sinIdStr);
    			if(entryInfo.getCustomer()==null || entryInfo.getCustomer().getName()==null) continue;
    			String customerNames = (String)sinInfo.get("customerNames");
    			customerNames += ";"+ entryInfo.getCustomer().getName();
    			sinInfo.put("customerNames", customerNames);	
    		}
    	}
    	
    	Iterator iterator = sinPurMap.keySet().iterator();
    	while(iterator.hasNext()){
    		String sinIdStr = (String)iterator.next();
    		SignManageInfo sinInfo = (SignManageInfo)sinPurMap.get(sinIdStr);
    		
    		IRow addRow = this.kDTable1.addRow();
    		addRow.getCell("ID").setValue(sinInfo.getId());
    		addRow.getCell("number").setValue(sinInfo.getNumber());
    		addRow.getCell("state").setValue(sinInfo.getBizState());
    		addRow.getCell("signDate").setValue(sinInfo.getBookedDate());
    		addRow.getCell("room").setValue(sinInfo.getRoom());
    		addRow.getCell("customer").setValue(sinInfo.get("customerNames"));
    		addRow.getCell("saler").setValue(sinInfo.getSaleManNames());
    		addRow.setUserObject(sinInfo);
    	}
    }
    
    
    private void fileTableByPrePurData(String searchType,String filterStr,String permitSpIdSql) throws BOSException{
    	String viewString = "";
    	RevBillTypeEnum revBillType = (RevBillTypeEnum)((NewSHERevBillEditUI)this.getUIContext().get("ParentObject")).revBillType.getSelectedItem();
    	if(revBillType!=null && revBillType.equals(RevBillTypeEnum.gathering)){	//收款时，必须是当前交易对应的业务
        	//查询符合条件的交易信息，获取交易上的业务id,然后再查询具体的业务单据
        	String filterIdsStr = getTransactionBizIdFilterStr(searchType,filterStr,permitSpIdSql);
        	if(filterIdsStr.equals("")) return;    	
        	
        	viewString = "select customer.name,head.* ,head.room.name , head.room.number,head.saleManNames " +
							"where head.id in ("+ filterIdsStr +") " ;  		
        	viewString += " and (head.bizState = '"+TransactionStateEnum.PREAPPLE_VALUE+"' or head.bizState = '"+TransactionStateEnum.PREAUDIT_VALUE+"') ";
    	}else{
        	String sqlFilter = " where customer.name like '%"+filterStr+"%' ";
        	if(searchType.equals("RoomNumber")) sqlFilter = " where head.room.name like '%"+filterStr+"%' ";
        	viewString = "select customer.name,head.* ,head.room.name , head.room.number,head.saleManNames " + sqlFilter; 
        	viewString += " and head.sellProject.id in ("+permitSpIdSql+") ";
    	}

    	CoreBaseCollection sinEntryColl = PrePurchaseCustomerEntryFactory.getRemoteInstance().getCollection(viewString);	//
    	Map sinPurMap = new HashMap();
    	for(int i=0;i<sinEntryColl.size();i++) {
    		PrePurchaseCustomerEntryInfo entryInfo = (PrePurchaseCustomerEntryInfo)sinEntryColl.get(i);
    		PrePurchaseManageInfo sinInfo = entryInfo.getHead();
    		if(sinInfo==null || sinInfo.getId()==null) continue;
    		String sinIdStr = sinInfo.getId().toString();
    		if(sinPurMap.get(sinIdStr)==null){
    			sinInfo.put("customerNames", entryInfo.getCustomer()==null?"":entryInfo.getCustomer().getName());
    			sinPurMap.put(sinIdStr, entryInfo.getHead());
    		}else{
    			sinInfo = (PrePurchaseManageInfo)sinPurMap.get(sinIdStr);
    			if(entryInfo.getCustomer()==null || entryInfo.getCustomer().getName()==null) continue;
    			String customerNames = (String)sinInfo.get("customerNames");
    			customerNames += ";"+ entryInfo.getCustomer().getName();
    			sinInfo.put("customerNames", customerNames);	
    		}
    	}
    	
    	Iterator iterator = sinPurMap.keySet().iterator();
    	while(iterator.hasNext()){
    		String sinIdStr = (String)iterator.next();
    		PrePurchaseManageInfo sinInfo = (PrePurchaseManageInfo)sinPurMap.get(sinIdStr);
    		
    		IRow addRow = this.kDTable1.addRow();
    		addRow.getCell("ID").setValue(sinInfo.getId());
    		addRow.getCell("number").setValue(sinInfo.getNumber());
    		addRow.getCell("state").setValue(sinInfo.getBizState());
    		addRow.getCell("signDate").setValue(sinInfo.getBookedDate());
    		addRow.getCell("room").setValue(sinInfo.getRoom());
    		addRow.getCell("customer").setValue(sinInfo.get("customerNames"));
    		addRow.getCell("saler").setValue(sinInfo.getSaleManNames());
    		addRow.setUserObject(sinInfo);
    	}
    }
    
    private void fileTableByPreOrderData(String searchType,String filterStr,String permitSpIdSql) throws BOSException{
    	String viewString = "";
    	RevBillTypeEnum revBillType = (RevBillTypeEnum)((NewSHERevBillEditUI)this.getUIContext().get("ParentObject")).revBillType.getSelectedItem();
    	if(revBillType!=null && revBillType.equals(RevBillTypeEnum.gathering)){	//收款时，必须是当前交易对应的业务
        	//查询符合条件的交易信息，获取交易上的业务id,然后再查询具体的业务单据
        	String filterIdsStr = getTransactionBizIdFilterStr(searchType,filterStr,permitSpIdSql);
        	if(filterIdsStr.equals("")) return;    	
        	
        	viewString = "select customer.name, head.* ,head.room.name , head.room.number,head.saleManStr " +
							"  where head.id in ("+ filterIdsStr +")  ";     		
    			viewString += " and head.bizState = '"+TransactionStateEnum.BAYNUMBER_VALUE+"'  ";
    	}else{
        	String sqlFilter = " where customer.name like '%"+filterStr+"%' ";
        	if(searchType.equals("RoomNumber")) sqlFilter = " where head.room.name like '%"+filterStr+"%' ";
        	viewString = "select customer.name, head.* ,head.room.name , head.room.number,head.saleManStr " + sqlFilter;   
        	viewString += " and head.sellProject.id in ("+permitSpIdSql+") ";
    	}
    	SincerityPurchaseCustomerEntryCollection sinEntryColl = SincerityPurchaseCustomerEntryFactory.getRemoteInstance()
    								.getSincerityPurchaseCustomerEntryCollection(viewString);	//
    	Map sinPurMap = new HashMap();
    	for(int i=0;i<sinEntryColl.size();i++) {
    		SincerityPurchaseCustomerEntryInfo entryInfo = sinEntryColl.get(i);
    		SincerityPurchaseInfo sinInfo = entryInfo.getHead();
    		if(sinInfo==null || sinInfo.getId()==null) continue;
    		String sinIdStr = sinInfo.getId().toString();
    		if(sinPurMap.get(sinIdStr)==null){
    			sinInfo.put("customerNames", entryInfo.getCustomer()==null?"":entryInfo.getCustomer().getName());
    			sinPurMap.put(sinIdStr, entryInfo.getHead());
    		}else{
    			sinInfo = (SincerityPurchaseInfo)sinPurMap.get(sinIdStr);
    			if(entryInfo.getCustomer()==null || entryInfo.getCustomer().getName()==null) continue;
    			String customerNames = (String)sinInfo.get("customerNames");
    			customerNames += ";"+ entryInfo.getCustomer().getName();
    			sinInfo.put("customerNames", customerNames);
    		}
    	}
    	
    	Iterator iterator = sinPurMap.keySet().iterator();
    	while(iterator.hasNext()){
    		String sinIdStr = (String)iterator.next();
    		SincerityPurchaseInfo sinInfo = (SincerityPurchaseInfo)sinPurMap.get(sinIdStr);
    		if(sinInfo!=null) {
	    		IRow addRow = this.kDTable1.addRow();
	    		addRow.getCell("ID").setValue(sinInfo.getId());
	    		addRow.getCell("number").setValue(sinInfo.getNumber());
	    		addRow.getCell("state").setValue(sinInfo.getBizState());
	    		addRow.getCell("signDate").setValue(sinInfo.getBizDate()); //.getBookDate()
	    		addRow.getCell("room").setValue(sinInfo.getRoom());
	    		addRow.getCell("customer").setValue(sinInfo.get("customerNames"));
	    		addRow.getCell("saler").setValue(sinInfo.getSaleManStr());
	    		addRow.setUserObject(sinInfo);
    		}
    	}
    	
    	/**调试**/
/*    	SincerityPurchaseCollection sinColl = SincerityPurchaseFactory.getRemoteInstance()
    		.getSincerityPurchaseCollection("select id,name,number,state,signDate,room");
		for(int i=0;i<sinColl.size();i++) {
    	SincerityPurchaseInfo sinInfo = sinColl.get(i);
		
		IRow addRow = this.kDTable1.addRow();
		addRow.getCell("ID").setValue(sinInfo.getId());
		addRow.getCell("number").setValue(sinInfo.getNumber());
		addRow.getCell("state").setValue(sinInfo.getState());
		addRow.getCell("signDate").setValue(sinInfo.getBookDate());
		addRow.getCell("room").setValue(sinInfo.getRoom());
		addRow.getCell("customer").setValue(sinInfo.get("customerNames"));
		addRow.getCell("saler").setValue(sinInfo.getSalesman());
		addRow.setUserObject(sinInfo);    
		}
	*/
    	
    }
    
    private void setReturnObjectInfo(Object obj){
    	BaseTransactionInfo retInfo = (BaseTransactionInfo)obj;
    	
    	RelatBizType bizType = null;
    	if(this.radioPreOrder.isSelected()){
    		bizType = RelatBizType.PreOrder;
    	}else if(this.radioPrePur.isSelected()){
    		bizType = RelatBizType.PrePur;
    	}else if(this.radioPurchase.isSelected()){
    		bizType = RelatBizType.Purchase;
    	}else if(this.radioSign.isSelected()){
    		bizType = RelatBizType.Sign;
    	}
    	
    	SHECustomerCollection sheCustColl = new SHECustomerCollection();
		try {
			ITranCustomerEntry custFacFactory = CRMHelper.getCustmerEntryInterFace(null,retInfo.getId());
			TranCustomerEntryCollection custEntryColl = custFacFactory.getTranCustomerEntryCollection(
							"select customer.id,customer.name,customer.number where head.id='"+retInfo.getId()+"' ");
			for (int i = 0; i < custEntryColl.size(); i++) {
				if(custEntryColl.get(i).getCustomer()!=null)
					sheCustColl.add(custEntryColl.get(i).getCustomer());
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
    	
    	retInfo.put("SHECustomerColl", sheCustColl);
    	
    	
    	retInfo.put("RelateBizType", bizType);
    	retInfo.put("TransactionId", retInfo.getTransactionID());
    	retInfo.setNumber(bizType.getAlias() + " " + retInfo.getNumber() );
    	this.getUIContext().put("RetObj", retInfo);

    	
    }
    
    protected void btnSure_actionPerformed(ActionEvent e) throws Exception {
    	IRow selectRow = KDTableUtil.getSelectedRow(this.kDTable1);
    	if(selectRow==null) {
    		FDCMsgBox.showWarning("必须选择一行数据！");
    		return;
    	}	
    	
    	setReturnObjectInfo(selectRow.getUserObject());    	
    	this.getUIWindow().close();
    }
    
    
    protected void btnCancel_actionPerformed(ActionEvent e) throws Exception {
    	this.getUIWindow().close();
    }
    
    protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
    	if(e.getType()==1 && e.getClickCount()==2 && e.getButton()==1) {
    		IRow selectRow = KDTableUtil.getSelectedRow(this.kDTable1);
    		if(selectRow!=null){
    			setReturnObjectInfo(selectRow.getUserObject());
    			this.getUIWindow().close();
    		}
    	}
    }


    

	
	
	
}