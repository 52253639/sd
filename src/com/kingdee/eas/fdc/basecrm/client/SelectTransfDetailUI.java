/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.RelatBizType;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BusTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ITranPayListEntry;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.TranPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.TranPayListEntryInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;

/**
 * output class name
 */
public class SelectTransfDetailUI extends AbstractSelectTransfDetailUI
{
    private static final Logger logger = CoreUIObject.getLogger(SelectTransfDetailUI.class);
    
    private Map revBillEntryMap;	//为了确认时校验，转款金额超出了可转金额
    private final String payListEntry = "payListEntry";
    private final String revListEntry = "revListEntry";
    
    public SelectTransfDetailUI() throws Exception
    {
        super();
    }

    
    public void onLoad() throws Exception {

    	super.onLoad();
    	this.kDTable1.checkParsed();
    	this.kDTable1.getStyleAttributes().setLocked(true);
    	this.kDTable1.getColumn("id").getStyleAttributes().setHided(true);
    	this.kDTable1.getColumn("type").getStyleAttributes().setHided(true);
    	this.kDTable1.getColumn("select").getStyleAttributes().setLocked(true);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor formatTextEditor = new KDTDefaultCellEditor(formattedTextField);    	
    	this.kDTable1.getColumn("transfAmount").setEditor(formatTextEditor);
    	
    	
    	BaseTransactionInfo relateBizInfo = (BaseTransactionInfo)this.getUIContext().get("RelateBizBillInfo");
/*    	if(relateBizInfo==null){
    		FDCMsgBox.showWarning("关联单据必须选择！请检查");
    		this.abort();
    	}*/
    	if(relateBizInfo!=null) {
	    	//RelatBizType relateBizType = (RelatBizType)relateBizInfo.get("RelateBizType");
	    	ITranPayListEntry payListEntryFactory = CRMHelper.getPayListEntryInterFace(null, null);  //直接从交易明细上查询
	    	
	    	TranPayListEntryCollection payListEntryColl = payListEntryFactory
	    					.getTranPayListEntryCollection("select *,moneyDefine.name where head.id = '"+relateBizInfo.getTransactionID()+"' order by seq");
	    	for(int i=0;i<payListEntryColl.size();i++){
	    		TranPayListEntryInfo payListEntryInfo = payListEntryColl.get(i);
	    		if(payListEntryInfo.get("type")!=null && BusTypeEnum.PAY_VALUE.equals(payListEntryInfo.get("type"))) {
		    		payListEntryInfo.put("typeFlag", payListEntry);	//交易中的应收明细
		    		IRow addRow = this.kDTable1.addRow(); 
		    		addRow.getCell("id").setValue(payListEntryInfo.getId().toString());
		    		addRow.getCell("type").setValue(payListEntryInfo.get("typeFlag")); 
		    		addRow.getCell("select").setValue(new Boolean(false));
		    		addRow.getCell("moneyDefine").setValue(payListEntryInfo.getMoneyDefine());
		    		addRow.getCell("appRevAmount").setValue(payListEntryInfo.getAppAmount());
		    		addRow.getCell("actRevAmount").setValue(payListEntryInfo.getActRevAmount());
		    		addRow.getCell("appRevDate").setValue(payListEntryInfo.getAppDate());
		    		addRow.setUserObject(payListEntryInfo); 
	    		}
	    	}
    	}else{
    		MoneyDefineCollection monDefColl = MoneyDefineFactory.getRemoteInstance()
    						.getMoneyDefineCollection("select * where sysType = '"+MoneySysTypeEnum.SALEHOUSESYS_VALUE+"'  ");
	    	for(int i=0;i<monDefColl.size();i++){
	    		TranPayListEntryInfo payListEntryInfo = new TranPayListEntryInfo();
	    		payListEntryInfo.setMoneyDefine(monDefColl.get(i));
	    		payListEntryInfo.put("typeFlag", payListEntry);	//交易中的应收明细
	    		IRow addRow = this.kDTable1.addRow(); 
	    		addRow.getCell("id").setValue("");
	    		addRow.getCell("type").setValue(payListEntryInfo.get("typeFlag")); 
	    		addRow.getCell("select").setValue(new Boolean(false));
	    		addRow.getCell("moneyDefine").setValue(payListEntryInfo.getMoneyDefine());
	    		addRow.getCell("appRevAmount").setValue(new BigDecimal("0"));
	    		addRow.getCell("actRevAmount").setValue(new BigDecimal("0"));
	    		addRow.getCell("appRevDate").setValue(null);
	    		addRow.setUserObject(payListEntryInfo); 
	    	}
    		
    	}
    	
    	
    }


    protected void btnSure_actionPerformed(ActionEvent e) throws Exception {
    	//转换成收款单的明细返回
    	SHERevBillEntryCollection sheRevEntryColl = new SHERevBillEntryCollection(); 

    	//校验转款金额是否超出可转范围
    	Iterator iterTemp = revBillEntryMap.keySet().iterator();
    	while(iterTemp.hasNext()){
    		SHERevBillEntryInfo revEntryInfo = (SHERevBillEntryInfo)revBillEntryMap.get(iterTemp.next());
    		revEntryInfo.put("transfAmount", new BigDecimal("0"));
    	} 
    	for (int i = 0; i < this.kDTable1.getRowCount(); i++) {
    		IRow currRow = this.kDTable1.getRow(i);    		
    		String typeStr = (String)currRow.getCell("type").getValue();
    		if(typeStr!=null && typeStr.equals(revListEntry)){	
    			SHERevBillEntryInfo revEntryInfo = (SHERevBillEntryInfo)currRow.getUserObject();
    			SHERevBillEntryInfo mapInfo = (SHERevBillEntryInfo)revBillEntryMap.get(revEntryInfo.getId().toString());
    			BigDecimal currAmount = (BigDecimal)currRow.getCell("transfAmount").getValue();
    			if(currAmount==null) currAmount = new BigDecimal("0");
    			revEntryInfo.put("transfAmount", currAmount);
    			if(mapInfo!=null){
    	   				BigDecimal tansfAmount = (BigDecimal)mapInfo.get("transfAmount");
        				if(tansfAmount!=null) {
        					mapInfo.put("transfAmount", tansfAmount.add(currAmount));
        				}
    			}
    			
    			if(currAmount!=null && currAmount.compareTo(new BigDecimal("0"))>0){
					SHERevBillEntryInfo sheRevEntryInfo = new SHERevBillEntryInfo(); 
	    			sheRevEntryInfo.setRevAmount(currAmount.negate());
	    			sheRevEntryInfo.setMoneyDefine(revEntryInfo.getMoneyDefine());
	    			sheRevEntryInfo.setTransferFromEntryId(revEntryInfo.getId().toString());    
	    			sheRevEntryColl.add(sheRevEntryInfo);
    			}
    		}else{
    			if(typeStr!=null && typeStr.equals(payListEntry)){
    				Boolean select = (Boolean)currRow.getCell("select").getValue();
    				if(select.booleanValue()){
    					TranPayListEntryInfo payEntryInfo = (TranPayListEntryInfo)currRow.getUserObject();
    					BigDecimal currAmount = (BigDecimal)currRow.getCell("transfAmount").getValue();
    					payEntryInfo.put("transfAmount", currAmount);
    					if(currAmount!=null && currAmount.compareTo(new BigDecimal("0"))>0){
    	    				SHERevBillEntryInfo sheRevEntryInfo = new SHERevBillEntryInfo(); 
    	        			sheRevEntryInfo.setRevAmount(currAmount);
    	        			sheRevEntryInfo.setMoneyDefine(payEntryInfo.getMoneyDefine());    
    	        			sheRevEntryColl.add(sheRevEntryInfo);
    					}
    				}
    			}
    		}
		}
    	Iterator iterator = revBillEntryMap.keySet().iterator();
    	while(iterator.hasNext()){
    		SHERevBillEntryInfo revEntryInfo = (SHERevBillEntryInfo)revBillEntryMap.get(iterator.next());
    		BigDecimal thisTransAmount = (BigDecimal)revEntryInfo.get("transfAmount");
    		if(thisTransAmount==null) thisTransAmount = new BigDecimal("0");
    		if(revEntryInfo.getRemainAmount().compareTo(thisTransAmount)<0){
    			FDCMsgBox.showWarning("转款明细‘"+revEntryInfo.getMoneyDefine().getName()+"’的转款金额合计超出了可转范围，请检查");
    			return;
    		}
    	}    	
    	
    	this.getUIContext().put("RetObjColl", sheRevEntryColl);    	
    	this.getUIWindow().close();
    }
    
    public void btnCancel_actionPerformed(java.awt.event.ActionEvent e){
    	this.getUIWindow().close();
    }
   
    protected void kDTable1_editStopped(KDTEditEvent e) throws Exception {
    	if(e.getType()!=1) return;
    	if(e.getRowIndex()<0) return;
    	
    	int selectColumnIndex = this.kDTable1.getColumnIndex("transfAmount");
    	IRow selectRow = this.kDTable1.getRow(e.getRowIndex());
    	if(e.getColIndex()==selectColumnIndex){
    		String typeStr = (String)selectRow.getCell("type").getValue();
    		if(typeStr!=null && typeStr.equals(revListEntry)){	
    			BigDecimal transfAmount = (BigDecimal)selectRow.getCell("transfAmount").getValue();
    			if(transfAmount==null) transfAmount = new BigDecimal("0");
    			SHERevBillEntryInfo revEntryInfo = (SHERevBillEntryInfo)selectRow.getUserObject();
    			revEntryInfo.put("transfAmount", transfAmount);
    		}
    		
    		
    		//刷新应收明细的转款金额（合计）
        	for (int i = 0; i < this.kDTable1.getRowCount(); i++) {
        		IRow currRow = this.kDTable1.getRow(i);    		
        		String currStr = (String)currRow.getCell("type").getValue();
        		if(currStr!=null && currStr.equals(revListEntry)){	
        			SHERevBillEntryInfo revEntryInfo = (SHERevBillEntryInfo)currRow.getUserObject();
        			BigDecimal currAmount = (BigDecimal)revEntryInfo.get("transfAmount");
        			if(currAmount==null) currAmount = new BigDecimal("0");

        			IRow payEntryRow = (IRow)revEntryInfo.get("OriginRow");
        			BigDecimal totalAmount =(BigDecimal)payEntryRow.getCell("transfAmount").getValue();
        			if(totalAmount==null) totalAmount = new BigDecimal("0");
        			if(totalAmount.add(currAmount).compareTo(new BigDecimal("0"))!=0)
        				payEntryRow.getCell("transfAmount").setValue(totalAmount.add(currAmount));
        			else
        				payEntryRow.getCell("transfAmount").setValue(null);
        		}else{	//先经过汇总行，先置为0
        			if(currStr!=null && currStr.equals(payListEntry)){
        				Boolean select = (Boolean)currRow.getCell("select").getValue();
        				if(select.booleanValue()){
        					currRow.getCell("transfAmount").setValue(null);
        				}
        			}
        		}
    		}
    	}
     }
    
    
    
	protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
    	if(e.getType()!=1) return;
    	if(e.getRowIndex()<0) return;
    	
    	int selectColumnIndex = this.kDTable1.getColumnIndex("select");
    	IRow selectRow = this.kDTable1.getRow(e.getRowIndex());
    	if(e.getColIndex()==selectColumnIndex){
    		String typeStr = (String)selectRow.getCell("type").getValue();
    		if(typeStr!=null && typeStr.equals(payListEntry)){
    			Boolean isSelected = (Boolean)selectRow.getCell("select").getValue();
    			if(isSelected==null) isSelected = new Boolean(false);
    			selectRow.getCell("select").setValue(new Boolean(!isSelected.booleanValue()));
    			
    			if(isSelected.booleanValue()){ //取消时，删除下方的实收明细
    				int rowCount = this.kDTable1.getRowCount();
    				for(int i=e.getRowIndex()+1;i<rowCount;i++){
    					IRow nextRow = this.kDTable1.getRow(i);
    					String nextRowStr = (String)nextRow.getCell("type").getValue();
    					if(nextRowStr!=null && nextRowStr.equals(payListEntry)){
    						break;
    					}else{
    						this.kDTable1.removeRow(i);
    						rowCount--;
    						i--;
    					}
    				}
    			}else{//勾选时，显示下方的实收明细
    				if(revBillEntryMap==null) {
    					revBillEntryMap = new HashMap();
	    				FDCBillInfo relateFromBizInfo = (FDCBillInfo)this.getUIContext().get("RelateFromBizBillInfo");
	    				SHECustomerInfo custFromInfo = (SHECustomerInfo)this.getUIContext().get("RelateFromCustInfo");
	
	    				String filterBillSql = " where parent.revBillType = '"+RevBillTypeEnum.GATHERING_VALUE+"' ";
	    				if(relateFromBizInfo!=null) 
	    					filterBillSql += " and parent.relateBizBillId = '"+relateFromBizInfo.getId().toString()+"'";
	    				if(custFromInfo!=null)	
	    					filterBillSql += " and parent.id in (select fid from t_bdc_sheRevBill where charindex('"+custFromInfo.getId().toString()+"',fcustomerIds)>0) ";
	    				SHERevBillEntryCollection revEntryColl = SHERevBillEntryFactory.getRemoteInstance()
	    							.getSHERevBillEntryCollection("select *,moneyDefine.*  " + filterBillSql);  //
	    				for(int i=0;i<revEntryColl.size();i++){
	    					SHERevBillEntryInfo revEntryInfo = revEntryColl.get(i);
	    					revEntryInfo.put("typeFlag", revListEntry);
	    					IRow addRow = this.kDTable1.addRow(e.getRowIndex()+1);
	    					addRow.setUserObject(revEntryInfo);
	    					addRow.getCell("id").setValue(revEntryInfo.getId().toString());
	    		    		addRow.getCell("type").setValue(revEntryInfo.get("typeFlag")); 
	    		    		addRow.getCell("moneyDefine").setValue(revEntryInfo.getMoneyDefine());
	    		    		addRow.getCell("actRevAmount").setValue(revEntryInfo.getRevAmount());
	    		    		addRow.getCell("canTransfAmount").setValue(revEntryInfo.getRemainAmount());
	    		    		addRow.getCell("transfAmount").getStyleAttributes().setLocked(false);
	    		    		addRow.getStyleAttributes().setBackground(new Color(0xFCFBDF));
	    		    		revBillEntryMap.put(revEntryInfo.getId().toString(), revEntryInfo.clone());
	    		    		revEntryInfo.put("OriginRow", selectRow);	//为了把转款金额汇总到应收明细上用的
	    				}
    				}else{
    					Iterator iterator = revBillEntryMap.keySet().iterator();
    					while(iterator.hasNext()){
    						SHERevBillEntryInfo revEntryInfo = (SHERevBillEntryInfo)revBillEntryMap.get(iterator.next());
    						revEntryInfo = (SHERevBillEntryInfo)revEntryInfo.clone();
	    					IRow addRow = this.kDTable1.addRow(e.getRowIndex()+1);
	    					addRow.setUserObject(revEntryInfo);
	    					addRow.getCell("id").setValue(revEntryInfo.getId().toString());
	    		    		addRow.getCell("type").setValue(revEntryInfo.get("typeFlag")); 
	    		    		addRow.getCell("moneyDefine").setValue(revEntryInfo.getMoneyDefine());
	    		    		addRow.getCell("actRevAmount").setValue(revEntryInfo.getRevAmount());
	    		    		addRow.getCell("canTransfAmount").setValue(revEntryInfo.getRemainAmount());
	    		    		addRow.getCell("transfAmount").getStyleAttributes().setLocked(false);
	    		    		addRow.getStyleAttributes().setBackground(new Color(0xFCFBDF));
	    		    		revEntryInfo.put("OriginRow", selectRow);	//为了把转款金额汇总到应收明细上用的
    					}
    				}
    			}
    		}
    	}   
	}    
    
    
    
    
}