/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelCollection;
import com.kingdee.eas.fdc.sellhouse.RoomModelFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeCollection;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class SignManageFilterUI extends AbstractSignManageFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(SignManageFilterUI.class);
    public SignManageFilterUI() throws Exception
    {
        super();
    }
    public SignManageFilterUI(String UI) throws Exception {
		super();
		this.UI=UI;
	}
    public void onLoad()throws Exception
    {
    	super.onLoad();
    	this.prmtPayType.setValue(null);
    	this.pkPurbusAdscriptionDateFrom.setValue(null);
    	this.pkPurbusAdscriptionDateTo.setValue(null);
    	
    	this.txtActBuildingAreaFrom.setNegatived(false);
	    this.txtActBuildingAreaTo.setNegatived(false);
	    
	    this.txtSellAmountFrom.setNegatived(false);
	    this.txtSellAmountTo.setNegatived(false);
	    
	    this.txtAgioFrom.setNegatived(false);
	    this.txtAgioTo.setNegatived(false);
	    
    	FilterInfo filter = new FilterInfo();
		EntityViewInfo evi = new EntityViewInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("validDate", FDCDateHelper.addDays(FDCCommonServerHelper.getServerTime(), 1), CompareType.LESS_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("invalidDate", FDCCommonServerHelper.getServerTime(), CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("invalidDate", null, CompareType.IS));
		
		filter.getFilterItems().add(new FilterItemInfo("project.id", psql,CompareType.INNER));
		filter.setMaskString("#0 and #1 and (#2 or #3) and #4 ");
		evi.setFilter(filter);
		this.prmtPayType.setEntityViewInfo(evi);
    }

	public FilterInfo getFilterInfo()
	{
	    FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
	   
	    String actBuildingAreaFrom=para.getString("actBuildingAreaFrom");
	    String actBuildingAreaTo=para.getString("actBuildingAreaTo");
	    String sellAmountFrom=para.getString("sellAmountFrom");
	    String sellAmountTo=para.getString("sellAmountTo");
	    
	    String lastAgioFrom=para.getString("lastAgioFrom");
	    String lastAgioTo=para.getString("lastAgioTo");
	    
	    Date purbusAdscriptionDateFrom=para.getDate("purbusAdscriptionDateFrom");
	    Date purbusAdscriptionDateTo=para.getDate("purbusAdscriptionDateTo");
	    
	    
	    String payType=para.getString("payType");
	    
	    FilterInfo filter = super.getFilterInfo();
	    
	    if(actBuildingAreaFrom!=null) filter.getFilterItems().add(new FilterItemInfo("actBulidingArea", actBuildingAreaFrom,CompareType.GREATER_EQUALS));
	    if(actBuildingAreaTo!=null) filter.getFilterItems().add(new FilterItemInfo("actBulidingArea", actBuildingAreaTo,CompareType.LESS_EQUALS));
	    
	    if(sellAmountFrom!=null) filter.getFilterItems().add(new FilterItemInfo("sellAmount", sellAmountFrom,CompareType.GREATER_EQUALS));
	    if(sellAmountTo!=null) filter.getFilterItems().add(new FilterItemInfo("sellAmount", sellAmountTo,CompareType.LESS_EQUALS));
	   
	    if(lastAgioFrom!=null) filter.getFilterItems().add(new FilterItemInfo("lastAgio", lastAgioFrom,CompareType.GREATER_EQUALS));
	    if(lastAgioTo!=null) filter.getFilterItems().add(new FilterItemInfo("lastAgio", lastAgioTo,CompareType.LESS_EQUALS));
	   
	    if(purbusAdscriptionDateFrom!=null) filter.getFilterItems().add(new FilterItemInfo("pur.busAdscriptionDate", FDCDateHelper.getSQLBegin(purbusAdscriptionDateFrom),CompareType.GREATER_EQUALS));
	    if(purbusAdscriptionDateTo!=null) filter.getFilterItems().add(new FilterItemInfo("pur.busAdscriptionDate", FDCDateHelper.getSQLEnd(purbusAdscriptionDateTo),CompareType.LESS_EQUALS));
	    
	    if(payType!=null) filter.getFilterItems().add(new FilterItemInfo("payType.id", payType,CompareType.INNER));
	    
	    return filter;
	}
	public CustomerParams getCustomerParams()
	{
	    FDCCustomerParams param = new FDCCustomerParams();
	    param.setCustomerParams(super.getCustomerParams());
	    
	    if(this.txtActBuildingAreaFrom.getBigDecimalValue()!=null){
	    	param.add("actBuildingAreaFrom", this.txtActBuildingAreaFrom.getBigDecimalValue().toString());
	    }
	    if(this.txtActBuildingAreaTo.getBigDecimalValue()!=null){
	    	param.add("actBuildingAreaTo", this.txtActBuildingAreaTo.getBigDecimalValue().toString());
	    }
	    if(this.txtSellAmountFrom.getBigDecimalValue()!=null){
	    	param.add("sellAmountFrom", this.txtSellAmountFrom.getBigDecimalValue().toString());
	    }
	    if(this.txtSellAmountTo.getBigDecimalValue()!=null){
	    	param.add("sellAmountTo", this.txtSellAmountTo.getBigDecimalValue().toString());
	    }
	    if(this.txtAgioFrom.getBigDecimalValue()!=null){
	    	param.add("lastAgioFrom", this.txtAgioFrom.getBigDecimalValue().toString());
	    }
	    if(this.txtAgioTo.getBigDecimalValue()!=null){
	    	param.add("lastAgioTo", this.txtAgioTo.getBigDecimalValue().toString());
	    }
	    if(this.pkPurbusAdscriptionDateFrom.getValue()!=null){
	    	param.add("purbusAdscriptionDateFrom",(Date)this.pkPurbusAdscriptionDateFrom.getValue());
	    }
	    if(this.pkPurbusAdscriptionDateTo.getValue()!=null){
	    	param.add("purbusAdscriptionDateTo",(Date)this.pkPurbusAdscriptionDateTo.getValue());
	    }
	    if(this.prmtPayType.getValue() != null){
	    	Object[] payType = (Object[])prmtPayType.getValue();
	    	String payTypeString="";
	    	for(int i=0;i<payType.length;i++){
	    		if(payType[i]==null) continue;
	    		if(i==0)
	    			payTypeString+="'"+((SHEPayTypeInfo)payType[i]).getId().toString()+"'";
	    		else
	    			payTypeString+=",'"+((SHEPayTypeInfo)payType[i]).getId().toString()+"'";
	    	}
	    	if(!payTypeString.equals("")) param.add("payType", payTypeString);
	    }
	    return param.getCustomerParams();
	}
	
	public void setCustomerParams(CustomerParams cp)
	{
	    if(cp == null)
	        return;
	    FDCCustomerParams para = new FDCCustomerParams(cp);
	    String actBuildingAreaFrom=para.getString("actBuildingAreaFrom");
	    String actBuildingAreaTo=para.getString("actBuildingAreaTo");
	    String sellAmountFrom=para.getString("sellAmountFrom");
	    String sellAmountTo=para.getString("sellAmountTo");
	    
	    String lastAgioFrom=para.getString("lastAgioFrom");
	    String lastAgioTo=para.getString("lastAgioTo");
	    
	    Date purbusAdscriptionDateFrom=para.getDate("purbusAdscriptionDateFrom");
	    Date purbusAdscriptionDateTo=para.getDate("purbusAdscriptionDateTo");
	    
	    
	    String payType=para.getString("payType");
	    
	    if(actBuildingAreaFrom!=null)  this.txtActBuildingAreaFrom.setValue(new BigDecimal(actBuildingAreaFrom));
	    if(actBuildingAreaTo!=null)  this.txtActBuildingAreaTo.setValue(new BigDecimal(actBuildingAreaFrom));
	    if(sellAmountFrom!=null)  this.txtSellAmountFrom.setValue(new BigDecimal(sellAmountFrom));
	    if(sellAmountTo!=null)  this.txtSellAmountTo.setValue(new BigDecimal(sellAmountTo));
	    if(lastAgioFrom!=null)  this.txtAgioFrom.setValue(new BigDecimal(lastAgioFrom));
	    if(lastAgioTo!=null)  this.txtAgioFrom.setValue(new BigDecimal(lastAgioTo));
	    
	    
	    this.pkPurbusAdscriptionDateFrom.setValue(purbusAdscriptionDateFrom);
	    this.pkPurbusAdscriptionDateFrom.setValue(purbusAdscriptionDateTo);
	    
	    if(payType != null){
			try {
				SHEPayTypeCollection col = SHEPayTypeFactory.getRemoteInstance().getSHEPayTypeCollection("select * from where id in("+payType+")");
				this.prmtProductType.setValue(col.toArray());
			} catch (BOSException e) {
				e.printStackTrace();
			}
	    }
	    super.setCustomerParams(para.getCustomerParams());
	}

}