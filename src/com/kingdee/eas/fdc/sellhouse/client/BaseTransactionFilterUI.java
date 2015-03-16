/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.KDComboBoxMultiColumnItem;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelCollection;
import com.kingdee.eas.fdc.sellhouse.RoomModelFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataCollection;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataFactory;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.fdc.sellhouse.formula.SellHouseHelper;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class BaseTransactionFilterUI extends AbstractBaseTransactionFilterUI
{
	String UI=null;
	String psql=NewCommerceHelper.getPermitProjectIdSql(null);
    public BaseTransactionFilterUI() throws Exception {
		super();
	}
    public BaseTransactionFilterUI(String UI) throws Exception {
		super();
		this.UI=UI;
	}
	private static final Logger logger = CoreUIObject.getLogger(BaseTransactionFilterUI.class);
    public void onLoad()throws Exception
    {
    	super.onLoad();
    	this.prmtProductType.setValue(null);
    	this.prmtRoomModel.setValue(null);
    	this.prmtSeller.setValue(null);
    	this.pkBizAdscriptionDateFrom.setValue(null);
    	this.pkBizAdscriptionDateTo.setValue(null);
    	this.pkChangeDateFrom.setValue(null);
    	this.pkChangeDateTo.setValue(null);
    	
    	this.txtBuildingAreaFrom.setNegatived(false);
	    this.txtBuildingAreaTo.setNegatived(false);
	    
    	
    	FilterInfo filter = new FilterInfo();
		EntityViewInfo evi = new EntityViewInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", psql,CompareType.INNER));
		evi.setFilter(filter);
		this.prmtRoomModel.setEntityViewInfo(evi);
		
		Set set = NewCommerceHelper.getPermitSaleManIdSet(null,null);
		evi = new EntityViewInfo();
		filter= new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", set, CompareType.INCLUDE));
		evi.setFilter(filter);
		this.prmtSeller.setEntityViewInfo(evi);
		
		if(BaseTransactionListUI.IAMPURCHASE.equals(UI)){
			this.rbToPur.setVisible(false);
			this.rbToSign.setBounds(this.rbToPur.getX(), this.rbToPur.getY(), this.rbToPur.getWidth(), this.rbToPur.getHeight());
		}else if(BaseTransactionListUI.IAMSIGN.equals(UI)){
			this.rbToPur.setVisible(false);
			this.rbToSign.setVisible(false);
		}
    }

	public FilterInfo getFilterInfo()
	{
	    FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
	   
	    String room=para.getString("room");
	    String customer=para.getString("customer");
	    String phone=para.getString("phone");
	    String buildingAreaFrom=para.getString("buildingAreaFrom");
	    String buildingAreaTo=para.getString("buildingAreaTo");
	    String bizState=para.getString("bizState");
	    
	    Date bizAdscriptionDateFrom=para.getDate("bizAdscriptionDateFrom");
	    Date bizAdscriptionDateTo=para.getDate("bizAdscriptionDateTo");
	    Date changeDateFrom=para.getDate("changeDateFrom");
	    Date changeDateTo=para.getDate("changeDateTo");
	    
	    String productType=para.getString("productType");
	    String roomModel=para.getString("roomModel");
	    String seller=para.getString("seller");
	    
	    FilterInfo filter = new FilterInfo();
	    if(room!=null) filter.getFilterItems().add(new FilterItemInfo("room.name", "%"+room+"%",CompareType.LIKE));
	    if(customer!=null) filter.getFilterItems().add(new FilterItemInfo("customerNames", "%"+customer+"%",CompareType.LIKE));
	    if(phone!=null) filter.getFilterItems().add(new FilterItemInfo("customerPhone", "%"+phone+"%",CompareType.LIKE));
	    
	    if(productType!=null) filter.getFilterItems().add(new FilterItemInfo("room.productType.id", productType,CompareType.INNER));
	    if(roomModel!=null) filter.getFilterItems().add(new FilterItemInfo("room.roomModel.id", roomModel,CompareType.INNER));
	    
	    if(buildingAreaFrom!=null) filter.getFilterItems().add(new FilterItemInfo("bulidingArea", buildingAreaFrom,CompareType.GREATER_EQUALS));
	    if(buildingAreaTo!=null) filter.getFilterItems().add(new FilterItemInfo("bulidingArea", buildingAreaTo,CompareType.LESS_EQUALS));
	    
	    if(bizState!=null) filter.getFilterItems().add(new FilterItemInfo("bizState", bizState,CompareType.INNER));
	    
	    if(bizAdscriptionDateFrom!=null) filter.getFilterItems().add(new FilterItemInfo("busAdscriptionDate", FDCDateHelper.getSQLBegin(bizAdscriptionDateFrom),CompareType.GREATER_EQUALS));
	    if(bizAdscriptionDateTo!=null) filter.getFilterItems().add(new FilterItemInfo("busAdscriptionDate", FDCDateHelper.getSQLEnd(bizAdscriptionDateTo),CompareType.LESS_EQUALS));
	    
	    if(changeDateFrom!=null) filter.getFilterItems().add(new FilterItemInfo("changeDate", FDCDateHelper.getSQLBegin(changeDateFrom),CompareType.GREATER_EQUALS));
	    if(changeDateTo!=null) filter.getFilterItems().add(new FilterItemInfo("changeDate", FDCDateHelper.getSQLEnd(changeDateTo),CompareType.LESS_EQUALS));
	    
	    if(BaseTransactionListUI.IAMPREPURCHASE.equals(UI)){
	    	 if(seller!=null) filter.getFilterItems().add(new FilterItemInfo("prePurchaseSaleManEntry.user.id", seller,CompareType.INNER));
		}else if(BaseTransactionListUI.IAMPURCHASE.equals(UI)){
			 if(seller!=null) filter.getFilterItems().add(new FilterItemInfo("purSaleManEntry.user.id", seller,CompareType.INNER));
		}else if(BaseTransactionListUI.IAMSIGN.equals(UI)){
			 if(seller!=null) filter.getFilterItems().add(new FilterItemInfo("signSaleManEntry.user.id", seller,CompareType.INNER));
		}
	    return filter;
	}
	public CustomerParams getCustomerParams()
	{
	    FDCCustomerParams param = new FDCCustomerParams();
	    
	    if(this.txtRoom.getText()!=null&&!"".equals(this.txtRoom.getText())){
	    	param.add("room", this.txtRoom.getText().trim());
	    }
	    if(this.txtCustomer.getText()!=null&&!"".equals(this.txtCustomer.getText())){
	    	param.add("customer", this.txtCustomer.getText().trim());
	    }
	    if(this.txtPhone.getText()!=null&&!"".equals(this.txtPhone.getText())){
	    	param.add("phone", this.txtPhone.getText().trim());
	    }
	    if(this.txtBuildingAreaFrom.getBigDecimalValue()!=null){
	    	param.add("buildingAreaFrom", this.txtBuildingAreaFrom.getBigDecimalValue().toString());
	    }
	    if(this.txtBuildingAreaTo.getBigDecimalValue()!=null){
	    	param.add("buildingAreaTo", this.txtBuildingAreaTo.getBigDecimalValue().toString());
	    }
	    List bizState=new ArrayList();
	    if(this.rbSaved.isSelected()){
	    	bizState.add(TransactionStateEnum.PRESAVED_VALUE);
	    	bizState.add(TransactionStateEnum.PURSAVED_VALUE);
	    	bizState.add(TransactionStateEnum.SIGNSAVED_VALUE);
	    }
	    if(this.rbSubmitted.isSelected()){
	    	bizState.add(TransactionStateEnum.PREAPPLE_VALUE);
	    	bizState.add(TransactionStateEnum.PURAPPLE_VALUE);
	    	bizState.add(TransactionStateEnum.SIGNAPPLE_VALUE);
	    }
	    if(this.rbAuditted.isSelected()){
	    	bizState.add(TransactionStateEnum.PREAUDIT_VALUE);
	    	bizState.add(TransactionStateEnum.PURAUDIT_VALUE);
	    	bizState.add(TransactionStateEnum.SIGNAUDIT_VALUE);
	    }
	    if(this.rbAuditting.isSelected()){
	    	bizState.add(TransactionStateEnum.PREAUDITING_VALUE);
	    	bizState.add(TransactionStateEnum.PURAUDITING_VALUE);
	    	bizState.add(TransactionStateEnum.SIGNAUDITING_VALUE);
	    }
	    if(this.rbNullify.isSelected()){
	    	bizState.add(TransactionStateEnum.PRENULLIFY_VALUE);
	    	bizState.add(TransactionStateEnum.PURNULLIFY_VALUE);
	    	bizState.add(TransactionStateEnum.SIGNNULLIFY_VALUE);
	    }
	    if(this.rbCNAuditting.isSelected()){
	    	bizState.add(TransactionStateEnum.CHANGENAMEAUDITING_VALUE);
	    }
	    if(this.rbCNNullify.isSelected()){
	    	bizState.add(TransactionStateEnum.CNNULLIFY_VALUE);
	    }
	    if(this.rbQRAuditting.isSelected()){
	    	bizState.add(TransactionStateEnum.QUITROOMAUDITING_VALUE);
	    }
	    if(this.rbQRNullify.isSelected()){
	    	bizState.add(TransactionStateEnum.QRNULLIFY_VALUE);
	    }
	    if(this.rbCRAuditting.isSelected()){
	    	bizState.add(TransactionStateEnum.CHANGEROOMAUDITING_VALUE);
	    }
	    if(this.rbCRNullify.isSelected()){
	    	bizState.add(TransactionStateEnum.CRNULLIFY_VALUE);
	    }
	    if(this.rbToPur.isSelected()){
	    	bizState.add(TransactionStateEnum.TOPUR_VALUE);
	    }
	    if(this.rbToSign.isSelected()){
	    	bizState.add(TransactionStateEnum.TOSIGN_VALUE);
	    }
	    String bizStateString="";
    	for(int i=0;i<bizState.size();i++){
    		if(i==0)
    			bizStateString+="'"+bizState.get(i).toString()+"'";
    		else
    			bizStateString+=",'"+bizState.get(i).toString()+"'";
    	}
    	if(!bizStateString.equals("")) param.add("bizState", bizStateString);
    	
	    if(this.pkBizAdscriptionDateFrom.getValue()!=null){
	    	param.add("bizAdscriptionDateFrom",(Date)this.pkBizAdscriptionDateFrom.getValue());
	    }
	    if(this.pkBizAdscriptionDateTo.getValue()!=null){
	    	param.add("bizAdscriptionDateTo",(Date)this.pkBizAdscriptionDateTo.getValue());
	    }
	    if(this.pkChangeDateFrom.getValue()!=null){
	    	param.add("changeDateFrom",(Date)this.pkChangeDateFrom.getValue());
	    }
	    if(this.pkChangeDateTo.getValue()!=null){
	    	param.add("changeDateTo",(Date)this.pkChangeDateTo.getValue());
	    }
	    if(this.prmtProductType.getValue() != null){
	    	Object[] productType = (Object[])prmtProductType.getValue();
	    	String productTypeString="";
	    	for(int i=0;i<productType.length;i++){
	    		if(productType[i]==null) continue;
	    		if(i==0)
	    			productTypeString+="'"+((ProductTypeInfo)productType[i]).getId().toString()+"'";
	    		else
	    			productTypeString+=",'"+((ProductTypeInfo)productType[i]).getId().toString()+"'";
	    	}
	    	if(!productTypeString.equals("")) param.add("productType", productTypeString);
	    }
	    if(this.prmtRoomModel.getValue() != null){
	    	Object[] roomModel = (Object[])prmtRoomModel.getValue();
	    	String roomModelString="";
	    	for(int i=0;i<roomModel.length;i++){
	    		if(roomModel[i]==null) continue;
	    		if(i==0)
	    			roomModelString+="'"+((RoomModelInfo)roomModel[i]).getId().toString()+"'";
	    		else
	    			roomModelString+=",'"+((RoomModelInfo)roomModel[i]).getId().toString()+"'";
	    	}
	    	if(!roomModelString.equals("")) param.add("roomModel", roomModelString);
	    }
	    if(this.prmtSeller.getValue() != null){
	    	Object[] seller = (Object[])prmtSeller.getValue();
	    	String sellerString="";
	    	for(int i=0;i<seller.length;i++){
	    		if(seller[i]==null) continue;
	    		if(i==0)
	    			sellerString+="'"+((UserInfo)seller[i]).getId().toString()+"'";
	    		else
	    			sellerString+=",'"+((UserInfo)seller[i]).getId().toString()+"'";
	    	}
	    	if(!sellerString.equals("")) param.add("seller", sellerString);
	    }
	    return param.getCustomerParams();
	}
	
	public void setCustomerParams(CustomerParams cp)
	{
	    if(cp == null)
	        return;
	    FDCCustomerParams para = new FDCCustomerParams(cp);
	    String room=para.getString("room");
	    String customer=para.getString("customer");
	    String phone=para.getString("phone");
	    String buildingAreaFrom=para.getString("buildingAreaFrom");
	    String buildingAreaTo=para.getString("buildingAreaTo");
	    String bizState=para.getString("bizState");
	    
	    Date bizAdscriptionDateFrom=para.getDate("bizAdscriptionDateFrom");
	    Date bizAdscriptionDateTo=para.getDate("bizAdscriptionDateTo");
	    Date changeDateFrom=para.getDate("changeDateFrom");
	    Date changeDateTo=para.getDate("changeDateTo");
	    
	    String productType=para.getString("productType");
	    String roomModel=para.getString("roomModel");
	    String seller=para.getString("seller");
	    
	    this.txtRoom.setText(room);
	    this.txtCustomer.setText(customer);
	    this.txtPhone.setText(phone);
	    if(buildingAreaFrom!=null)  this.txtBuildingAreaFrom.setValue(new BigDecimal(buildingAreaFrom));
	    if(buildingAreaTo!=null)  this.txtBuildingAreaTo.setValue(new BigDecimal(buildingAreaTo));
	    
	    if(bizState!=null){
	    	if(bizState.indexOf(TransactionStateEnum.PRESAVED_VALUE)>0||bizState.indexOf(TransactionStateEnum.PURSAVED_VALUE)>0||bizState.indexOf(TransactionStateEnum.SIGNSAVED_VALUE)>0){
		    	this.rbSaved.setSelected(true);
		    }
	    	if(bizState.indexOf(TransactionStateEnum.PREAPPLE_VALUE)>0||bizState.indexOf(TransactionStateEnum.PURAPPLE_VALUE)>0||bizState.indexOf(TransactionStateEnum.SIGNAPPLE_VALUE)>0){
		    	this.rbSubmitted.setSelected(true);
		    }
	    	if(bizState.indexOf(TransactionStateEnum.PREAUDIT_VALUE)>0||bizState.indexOf(TransactionStateEnum.PURAUDIT_VALUE)>0||bizState.indexOf(TransactionStateEnum.SIGNAUDIT_VALUE)>0){
		    	this.rbAuditted.setSelected(true);
		    }
	    	if(bizState.indexOf(TransactionStateEnum.PREAUDITING_VALUE)>0||bizState.indexOf(TransactionStateEnum.PURAUDITING_VALUE)>0||bizState.indexOf(TransactionStateEnum.SIGNAUDITING_VALUE)>0){
		    	this.rbAuditting.setSelected(true);
		    }
	    	if(bizState.indexOf(TransactionStateEnum.PRENULLIFY_VALUE)>0||bizState.indexOf(TransactionStateEnum.PURNULLIFY_VALUE)>0||bizState.indexOf(TransactionStateEnum.SIGNNULLIFY_VALUE)>0){
		    	this.rbNullify.setSelected(true);
		    }
	    	if(bizState.indexOf(TransactionStateEnum.CHANGENAMEAUDITING_VALUE)>0){
		    	this.rbCNAuditting.setSelected(true);
		    }
	    	if(bizState.indexOf(TransactionStateEnum.CNNULLIFY_VALUE)>0){
		    	this.rbCNNullify.setSelected(true);
		    }
	    	if(bizState.indexOf(TransactionStateEnum.QUITROOMAUDITING_VALUE)>0){
		    	this.rbQRAuditting.setSelected(true);
		    }
	    	if(bizState.indexOf(TransactionStateEnum.QRNULLIFY_VALUE)>0){
		    	this.rbQRNullify.setSelected(true);
		    }
	    	if(bizState.indexOf(TransactionStateEnum.CHANGEROOMAUDITING_VALUE)>0){
		    	this.rbCRAuditting.setSelected(true);
		    }
	    	if(bizState.indexOf(TransactionStateEnum.CRNULLIFY_VALUE)>0){
		    	this.rbCRNullify.setSelected(true);
		    }
	    	if(bizState.indexOf(TransactionStateEnum.TOPUR_VALUE)>0){
		    	this.rbToPur.setSelected(true);
		    }
	    	if(bizState.indexOf(TransactionStateEnum.TOSIGN_VALUE)>0){
		    	this.rbToSign.setSelected(true);
		    }
	    }
	    
	    this.pkBizAdscriptionDateFrom.setValue(bizAdscriptionDateFrom);
	    this.pkBizAdscriptionDateTo.setValue(bizAdscriptionDateTo);
	    this.pkChangeDateFrom.setValue(changeDateFrom);
	    this.pkChangeDateTo.setValue(changeDateTo);
	    
	    if(productType != null){
			try {
				ProductTypeCollection col = ProductTypeFactory.getRemoteInstance().getProductTypeCollection("select * from where id in("+productType+")");
				this.prmtProductType.setValue(col.toArray());
			} catch (BOSException e) {
				e.printStackTrace();
			}
	    }
	    if(roomModel != null){
			try {
				RoomModelCollection col = RoomModelFactory.getRemoteInstance().getRoomModelCollection("select * from where id in("+roomModel+")");
				this.prmtRoomModel.setValue(col.toArray());
			} catch (BOSException e) {
				e.printStackTrace();
			}
	    }
	    if(seller != null){
			try {
				UserCollection col = UserFactory.getRemoteInstance().getUserCollection("select * from where id in("+seller+")");
				this.prmtSeller.setValue(col.toArray());
			} catch (BOSException e) {
				e.printStackTrace();
			}
	    }
	    super.setCustomerParams(para.getCustomerParams());
	}

}