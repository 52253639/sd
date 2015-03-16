/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBoxMultiColumnItem;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class TranSaleManSelectUI extends AbstractTranSaleManSelectUI
{
    private static final Logger logger = CoreUIObject.getLogger(TranSaleManSelectUI.class);
    public static final String NUMBER ="用户账户";
    public static final String NAME ="用户实名";
    protected List saleManEntry=null;
    protected String state=null;
    protected BOSUuid billId=null;
    protected SellProjectInfo sellProject;
    public TranSaleManSelectUI() throws Exception
    {
        super();
    }
    protected String getTextValue(KDTextField text) {
		if (text.getText().trim().equals("")) {
			return null;
		} else {
			return text.getText();
		}
	}
    private void setUserTable(String name,String number) throws EASBizException, BOSException{
    	this.tblUser.removeRows();
    	EntityViewInfo view= NewCommerceHelper.getPermitSalemanView(sellProject,true);
    	if(name!=null){
    		view.getFilter().getFilterItems().add(new FilterItemInfo("name","%"+name+"%",CompareType.LIKE));
    	}
    	if(number!=null){
    		view.getFilter().getFilterItems().add(new FilterItemInfo("number","%"+number+"%",CompareType.LIKE));
    	}
    	SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("id");
		sel.add("number");
		sel.add("name");
		sel.add("type");
		sel.add("cu.name");
		sel.add("group.name");
		view.setSelector(sel);
    	UserCollection col=UserFactory.getRemoteInstance().getUserCollection(view);
    	
    	for(int i=0;i<col.size();i++){
    		addUserRow(col.get(i));
    	}
    }
    protected void btnSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	String name=null;
		String number=null;
		if (comboFilter.getSelectedItem().toString().equals(NAME)) {
			name = getTextValue(txtFilterValue);
		}
		if (comboFilter.getSelectedItem().toString().equals(NUMBER)) {
			number = getTextValue(txtFilterValue);
		}
		setUserTable(name,number);
    }

    protected void btnYes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	BigDecimal totalpercent=FDCHelper.ZERO;
    	boolean ishasPercent=false;
    	for(int i=0;i<this.tblSelect.getRowCount();i++){
    		IRow row=this.tblSelect.getRow(i);
    		if(row.getCell("percent").getValue()!=null){
    			ishasPercent=true;
    			totalpercent=totalpercent.add((BigDecimal)this.tblSelect.getRow(i).getCell("percent").getValue());
    		}
    	}
		if(ishasPercent&&totalpercent.compareTo(new BigDecimal(100))!=0){
			FDCMsgBox.showWarning(this,"分摊比例之和不等于100！");
			SysUtil.abort();
		}
		if("VIEW".equals(state)){
			if(this.tblSelect.getRowCount()==0){
				FDCMsgBox.showWarning(this,"置业顾问不能为空！");
				SysUtil.abort();
			}
		}
		saleManEntry.clear();
		for(int i=0;i<this.tblSelect.getRowCount();i++){
			IRow row=this.tblSelect.getRow(i);
			((TranSaleManEntryInfo)row.getUserObject()).setPercent((BigDecimal)this.tblSelect.getRow(i).getCell("percent").getValue());
			saleManEntry.add(row.getUserObject());
		}
		
		if("VIEW".equals(state)){
			if(billId!=null){
				ObjectUuidPK pk=new ObjectUuidPK(billId);
				IObjectValue objectValue=DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk);
				String saleManNames="";
				if(objectValue instanceof PrePurchaseManageInfo){
					PrePurchaseManageInfo info =(PrePurchaseManageInfo)objectValue;
					info.getPrePurchaseSaleManEntry().clear();
				
					for(int i=0;i<this.tblSelect.getRowCount();i++){
						IRow row=this.tblSelect.getRow(i);
						
						if(i==this.tblSelect.getRowCount()-1){
							saleManNames=saleManNames+((TranSaleManEntryInfo)row.getUserObject()).getUser().getName();
						}else{
							saleManNames=saleManNames+((TranSaleManEntryInfo)row.getUserObject()).getUser().getName()+";";
						}
						PrePurchaseSaleManEntryInfo entry=new PrePurchaseSaleManEntryInfo();
						SHEManageHelper.setSaleManEntry(entry,(TranSaleManEntryInfo)row.getUserObject());
						info.getPrePurchaseSaleManEntry().add(entry);
					}
					info.setSaleManNames(saleManNames);
					PrePurchaseManageFactory.getRemoteInstance().update(pk, info);
    			}
    			if(objectValue instanceof PurchaseManageInfo){
    				PurchaseManageInfo info =(PurchaseManageInfo)objectValue;
					info.getPurSaleManEntry().clear();
					
					for(int i=0;i<this.tblSelect.getRowCount();i++){
						IRow row=this.tblSelect.getRow(i);
						
						if(i==this.tblSelect.getRowCount()-1){
							saleManNames=saleManNames+((TranSaleManEntryInfo)row.getUserObject()).getUser().getName();
						}else{
							saleManNames=saleManNames+((TranSaleManEntryInfo)row.getUserObject()).getUser().getName()+";";
						}
						PurSaleManEntryInfo entry=new PurSaleManEntryInfo();
						SHEManageHelper.setSaleManEntry(entry,(TranSaleManEntryInfo)row.getUserObject());
						info.getPurSaleManEntry().add(entry);
					}
					info.setSaleManNames(saleManNames);
					PurchaseManageFactory.getRemoteInstance().update(pk, info);
    			}
    			if(objectValue instanceof SignManageInfo){
    				SignManageInfo info =(SignManageInfo)objectValue;
					info.getSignSaleManEntry().clear();
					
					for(int i=0;i<this.tblSelect.getRowCount();i++){
						IRow row=this.tblSelect.getRow(i);
						
						if(i==this.tblSelect.getRowCount()-1){
							saleManNames=saleManNames+((TranSaleManEntryInfo)row.getUserObject()).getUser().getName();
						}else{
							saleManNames=saleManNames+((TranSaleManEntryInfo)row.getUserObject()).getUser().getName()+";";
						}
						SignSaleManEntryInfo entry=new SignSaleManEntryInfo();
						SHEManageHelper.setSaleManEntry(entry,(TranSaleManEntryInfo)row.getUserObject());
						info.getSignSaleManEntry().add(entry);
					}
					info.setSaleManNames(saleManNames);
					SignManageFactory.getRemoteInstance().update(pk, info);
    			} 
			}
		}
		this.getUIWindow().close();
    }

    protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	this.getUIWindow().close();
    }
    protected void btnDown_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	actionAddSelectSaleMan();
    }
    protected void btnUp_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	SHEManageHelper.checkSelected(this,this.tblSelect);
		int activeRowIndex = this.tblSelect.getSelectManager().getActiveRowIndex();
		this.tblSelect.removeRow(activeRowIndex);
    }
    protected void tblUser_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			actionAddSelectSaleMan();
		}
    }
    private void addUserRow(UserInfo info) throws EASBizException, BOSException{
		if(info==null)return;
		IRow row =this.tblUser.addRow();
		row.setUserObject(info);
		row.getCell("id").setValue(info.getId());
		row.getCell("number").setValue(info.getNumber());
		row.getCell("name").setValue(info.getName());
		row.getCell("type").setValue(info.getType().getAlias());
		row.getCell("cu").setValue(info.getCU().getName());
		if(info.getGroup()!=null){
			row.getCell("group").setValue(info.getGroup().getName());
		}
	}
	private void addSelectSaleManRow(TranSaleManEntryInfo info){
		if(info==null)return;
		IRow row =this.tblSelect.addRow();
		row.setUserObject(info);
		row.getCell("id").setValue(info.getId());
		row.getCell("number").setValue(info.getUser().getNumber());
		row.getCell("name").setValue(info.getUser().getName());
		row.getCell("type").setValue(info.getUser().getType().getAlias());
		row.getCell("cu").setValue(info.getUser().getCU().getName());
		if(info.getUser().getGroup()!=null){
			row.getCell("group").setValue(info.getUser().getGroup().getName());
		}
		row.getCell("percent").setValue(info.getPercent());
	}
	private void initTable(){
		this.tblUser.checkParsed();
		this.tblSelect.checkParsed();
		
		this.tblUser.getStyleAttributes().setLocked(true);
		this.tblSelect.getStyleAttributes().setLocked(true);
		
		this.tblUser.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		this.tblSelect.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		
		this.tblSelect.getColumn("percent").getStyleAttributes().setLocked(false);
		
		KDFormattedTextField indexValue_TextField = new KDFormattedTextField();
		indexValue_TextField.setName("indexValue_TextField");
		indexValue_TextField.setVisible(true);
		indexValue_TextField.setEditable(true);
		indexValue_TextField.setHorizontalAlignment(JTextField.RIGHT);
		indexValue_TextField.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		indexValue_TextField.setMaximumValue(new BigDecimal(100));
		indexValue_TextField.setMinimumValue(FDCHelper.ZERO);
		indexValue_TextField.setSupportedEmpty(true);
		indexValue_TextField.setPrecision(2);
		indexValue_TextField.setNegatived(false);
		
		KDTDefaultCellEditor indexValue_CellEditor = new KDTDefaultCellEditor(
				indexValue_TextField);
		indexValue_CellEditor.setClickCountToStart(1);
		
		this.tblSelect.getColumn("percent").setEditor(indexValue_CellEditor);
		this.tblSelect.getColumn("percent").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblSelect.getColumn("percent").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		String[] fields=new String[this.tblUser.getColumnCount()];
		for(int i=0;i<this.tblUser.getColumnCount();i++){
			fields[i]=this.tblUser.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(this.tblUser,fields);
		
		fields=new String[this.tblSelect.getColumnCount()];
		for(int i=0;i<this.tblSelect.getColumnCount();i++){
			fields[i]=this.tblSelect.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(this.tblSelect,fields);
	}
	public void onLoad() throws Exception {
		initTable();
		super.onLoad();
		saleManEntry=(ArrayList)this.getUIContext().get("saleManEntry");
		state=(String)this.getUIContext().get("state");
		billId=(BOSUuid)this.getUIContext().get("billId");
		sellProject=(SellProjectInfo)this.getUIContext().get("sellProject");
		setSelectTable();
		setUserTable(null,null);
		this.comboFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { NAME }));
		this.comboFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { NUMBER }));
	}
	private void setSelectTable(){
		if(saleManEntry==null) return;
		for(int i = 0 ; i < saleManEntry.size() ; i++){
			addSelectSaleManRow((TranSaleManEntryInfo)saleManEntry.get(i));
		}
	}
    private void actionAddSelectSaleMan(){
		SHEManageHelper.checkSelected(this,this.tblUser);
		
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblUser);
		for(int s=0;s<selectRows.length;s++){
			UserInfo info=(UserInfo)this.tblUser.getRow(selectRows[s]).getUserObject();
			for(int i=0;i<this.tblSelect.getRowCount();i++){
				 if(info.getId().equals(((TranSaleManEntryInfo)this.tblSelect.getRow(i).getUserObject()).getUser().getId())){
					FDCMsgBox.showWarning(this,"所选置业顾问已经存在！");
					SysUtil.abort();
				}
			}
		}
		for (int i = 0; i < selectRows.length; i++) {
			UserInfo info=(UserInfo)this.tblUser.getRow(selectRows[i]).getUserObject();
			TranSaleManEntryInfo tranInfo=new TranSaleManEntryInfo();
			tranInfo.setUser(info);
			addSelectSaleManRow(tranInfo);
		}
	}
}