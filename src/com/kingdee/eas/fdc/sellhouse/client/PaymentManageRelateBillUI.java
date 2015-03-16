/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDComboBoxMultiColumnItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.eas.fdc.basecrm.RelatBizType;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
/**
 * output class name
 */
public class PaymentManageRelateBillUI extends AbstractPaymentManageRelateBillUI
{
    private static final Logger logger = CoreUIObject.getLogger(PaymentManageRelateBillUI.class);
    private static final String ID = "id";
    private static final String TRANID = "tranId";
    private static final String NUMBER = "number";
    private static final String STATE= "state";
    private static final String SIGNDATE = "signDate";
    private static final String ROOM = "room";
    private static final String CUSTOMER = "customer";
    private static final String SALER = "saler";
    
    private String customer=null;
    private String room=null;
    private SellProjectInfo sellProject=null;
    private boolean isNew=true;
    private Map info=null;
    public PaymentManageRelateBillUI() throws Exception
    {
        super();
    }

   
    public void onLoad() throws Exception {
		this.tblSellBill.checkParsed();
		this.tblSellBill.getStyleAttributes().setLocked(true);
		this.tblSellBill.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		String[] fields=new String[this.tblSellBill.getColumnCount()];
		for(int i=0;i<this.tblSellBill.getColumnCount();i++){
			fields[i]=this.tblSellBill.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(this.tblSellBill,fields);
		
		super.onLoad();
		sellProject=(SellProjectInfo) this.getUIContext().get("sellProject");
		customer=(String)this.getUIContext().get("customer");
		room=(String)this.getUIContext().get("room");
		isNew=((Boolean)this.getUIContext().get("isNew")).booleanValue();
		info=((Map)this.getUIContext().get("info"));
		
		this.comboFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { "房间" }));
		this.comboFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { "客户" }));
		
		
		if(room!=null){
			this.txtFilterValue.setText(room);
			this.comboFilter.setSelectedIndex(0);
		}
		if(customer!=null){
			this.txtFilterValue.setText(customer);
			this.comboFilter.setSelectedIndex(1);
		}
		
		this.fillTable(sellProject,room,customer,isNew);
	}
    protected String getTextValue(KDTextField text) {
		if (text.getText().trim().equals("")) {
			return null;
		} else {
			return text.getText();
		}
	}
    protected void btnSelect_actionPerformed(ActionEvent e) throws Exception {
    	String room=null;
    	String customer=null;
    	if (comboFilter.getSelectedItem().toString().equals("房间")) {
			room = getTextValue(txtFilterValue);
		}
		if (comboFilter.getSelectedItem().toString().equals("客户")) {
			customer = getTextValue(txtFilterValue);
		}
		fillTable(sellProject,room,customer,isNew);
	}
	private void toPaymentManage() throws BOSException{
    	int activeRowIndex = this.tblSellBill.getSelectManager().getActiveRowIndex();
		if(activeRowIndex!=-1) {
			RelatBizType bizType=null;
			IRow selectRow=this.tblSellBill.getRow(activeRowIndex);
			String selectid=selectRow.getCell(ID).getValue().toString();
			String tranId=selectRow.getCell(TRANID).getValue().toString();
			ObjectUuidPK pk=new ObjectUuidPK(selectid);
			IObjectValue objectValue=DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk,SHEManageHelper.getBizSelectors(pk.getObjectType()));
			
			Object[] customerID=null;
			if(objectValue instanceof PrePurchaseManageInfo){
				customerID=new Object[((PrePurchaseManageInfo)objectValue).getPrePurchaseCustomerEntry().size()];
				for(int i=0;i<((PrePurchaseManageInfo)objectValue).getPrePurchaseCustomerEntry().size();i++){
					customerID[i]=(((PrePurchaseManageInfo)objectValue).getPrePurchaseCustomerEntry().get(i).getCustomer());
				}
				bizType = RelatBizType.PrePur;
			}
			if(objectValue instanceof PurchaseManageInfo){
				customerID=new Object[((PurchaseManageInfo)objectValue).getPurCustomerEntry().size()];
				for(int i=0;i<((PurchaseManageInfo)objectValue).getPurCustomerEntry().size();i++){
					customerID[i]=(((PurchaseManageInfo)objectValue).getPurCustomerEntry().get(i).getCustomer());
				}
				bizType = RelatBizType.Purchase;
			}
			if(objectValue instanceof SignManageInfo){
				customerID=new Object[((SignManageInfo)objectValue).getSignCustomerEntry().size()];
				for(int i=0;i<((SignManageInfo)objectValue).getSignCustomerEntry().size();i++){
					customerID[i]=(((SignManageInfo)objectValue).getSignCustomerEntry().get(i).getCustomer());
				}
				bizType = RelatBizType.Sign;
			}
			info.put("id", selectid);
			info.put("tranId", tranId);
			info.put("bizType", bizType);
			info.put("room",((BaseTransactionInfo)objectValue).getRoom());
			info.put("customer", customerID);
			info.put("comboFilter", comboFilter.getSelectedItem().toString());
			info.put("txtFilterValue", txtFilterValue.getText());
			this.getUIWindow().close();
		}
    }

	public void actionSure_actionPerformed(ActionEvent e) throws Exception{
		SHEManageHelper.checkSelected(this, this.tblSellBill);
		toPaymentManage();
    }

   
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	this.getUIWindow().close();
    }
	protected void tblSellBill_tableClicked(KDTMouseEvent e) throws Exception {
		if(e.getType()==1 && e.getClickCount()==2 && e.getButton()==1) {
			toPaymentManage();
		}
	}
	private void fillTable(SellProjectInfo sellProject,String room,String customer,boolean isNew){
		this.tblSellBill.removeRows();
		
//		if(room==null&&customer==null) return;
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		
		sqlBuilder.appendSql("select * from ( ");
		sqlBuilder.appendSql("select bill.fsellProjectId SELLPROJECT ,bill.ftransactionID TRANID,bill.fid ID,bill.fnumber NUMBER,bill.fbizState STATE,bill.fbizDate SIGNDATE,room.fname_l2 ROOM,bill.fcustomerNames CUSTOMER, ");
		sqlBuilder.appendSql("bill.fsaleManNames SALER from t_she_prePurchaseManage bill ");
		sqlBuilder.appendSql("left join t_she_room room on room.fid=bill.froomid ");
		sqlBuilder.appendSql("union ");
		sqlBuilder.appendSql("select bill.fsellProjectId SELLPROJECT ,bill.ftransactionID TRANID,bill.fid ID,bill.fnumber NUMBER,bill.fbizState STATE,bill.fbizDate SIGNDATE,room.fname_l2 ROOM,bill.fcustomerNames CUSTOMER, ");
		sqlBuilder.appendSql("bill.fsaleManNames SALER from t_she_purchaseManage bill ");
		sqlBuilder.appendSql("left join t_she_room room on room.fid=bill.froomid ");
		sqlBuilder.appendSql("union ");
		sqlBuilder.appendSql("select bill.fsellProjectId SELLPROJECT ,bill.ftransactionID TRANID,bill.fid ID,bill.fnumber NUMBER,bill.fbizState STATE,bill.fbizDate SIGNDATE,room.fname_l2 ROOM,bill.fcustomerNames CUSTOMER, ");
		sqlBuilder.appendSql("bill.fsaleManNames SALER from t_she_signManage bill ");
		sqlBuilder.appendSql("left join t_she_room room on room.fid=bill.froomid ");
		sqlBuilder.appendSql(") t where 1=1 ");
		
		if(sellProject!=null){
			sqlBuilder.appendSql("and SELLPROJECT =? ");
			sqlBuilder.addParam(sellProject.getId().toString());
		}else{
			sqlBuilder.appendSql("and SELLPROJECT =? ");
			sqlBuilder.addParam("null");
		}
		if(room!=null){
			sqlBuilder.appendSql("and ROOM like ? ");
			sqlBuilder.addParam("%"+room+"%");
		}
		if(customer!=null){
			sqlBuilder.appendSql("and CUSTOMER like ? ");
			sqlBuilder.addParam("%"+customer+"%");
		}
		if(isNew){
			sqlBuilder.appendSql("and STATE in('PreApple','PreAudit','PurApple','PurAudit','SignApple','SignAudit') ");
		}else{
			sqlBuilder.appendSql("and STATE in('SignNullify','PreNullify','PurNullify','PCNullify','CRNullify','QRNullify','CNNullify','ToPur','ToSign') ");
		}
		try {
			IRowSet rs = sqlBuilder.executeQuery();
			while(rs.next()){
				IRow row=this.tblSellBill.addRow();
				row.getCell(ID).setValue(rs.getString(ID));
				row.getCell(TRANID).setValue(rs.getString(TRANID));
				row.getCell(NUMBER).setValue(rs.getString(NUMBER));
				row.getCell(STATE).setValue(TransactionStateEnum.getEnum(rs.getString(STATE)));
				row.getCell(SIGNDATE).setValue(rs.getDate(SIGNDATE));
				row.getCell(ROOM).setValue(rs.getString(ROOM));
				row.getCell(CUSTOMER).setValue(rs.getString(CUSTOMER));
				row.getCell(SALER).setValue(rs.getString(SALER));
			}
		}catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}