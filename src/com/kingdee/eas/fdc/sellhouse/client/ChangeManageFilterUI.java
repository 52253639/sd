/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.swing.ButtonModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.commonquery.app.CommonQueryUtil;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeReasonCollection;
import com.kingdee.eas.fdc.sellhouse.ChangeReasonFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeReasonInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelCollection;
import com.kingdee.eas.fdc.sellhouse.RoomModelFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.fi.arap.client.util.ArApBillUIUtil;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class ChangeManageFilterUI extends AbstractChangeManageFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChangeManageFilterUI.class);
    public ChangeManageFilterUI() throws Exception
    {
        super();
    }
    public void onLoad()throws Exception
    {
    	super.onLoad();
    	this.pkBizAdscriptionDateFrom.setValue(null);
    	this.pkBizAdscriptionDateTo.setValue(null);
    	this.pkChangeDateFrom.setValue(null);
    	this.pkChangeDateTo.setValue(null);
    	
    	
		this.comboBizState.addItem(TransactionStateEnum.PREAUDIT);
		this.comboBizState.addItem(TransactionStateEnum.PURAUDIT);
		this.comboBizState.addItem(TransactionStateEnum.SIGNAUDIT);
		
		this.comboBizState.setSelectedItem(null);
		
		this.comboBizType.addItem(ChangeBizTypeEnum.CHANGENAME);
		this.comboBizType.addItem(ChangeBizTypeEnum.QUITROOM);
		this.comboBizType.addItem(ChangeBizTypeEnum.CHANGEROOM);
		
		this.comboBizType.setSelectedItem(null);
		
		this.prmtChangeReson.setValue(null);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo().getId()));	
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",new Boolean(true)));	
		view.setFilter(filter);
		this.prmtChangeReson.setEntityViewInfo(view);
    }

	public FilterInfo getFilterInfo()
	{
	    FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
	   
	    String room=para.getString("room");
	    String customer=para.getString("customer");
	    String phone=para.getString("phone");
	    
	    String bizState=para.getString("bizState");
	    String bizType=para.getString("bizType");
	    
	    Date bizAdscriptionDateFrom=para.getDate("bizAdscriptionDateFrom");
	    Date bizAdscriptionDateTo=para.getDate("bizAdscriptionDateTo");
	    Date changeDateFrom=para.getDate("changeDateFrom");
	    Date changeDateTo=para.getDate("changeDateTo");
	    
	    String changeReason=para.getString("changeReason");
	    
	    FilterInfo filter = new FilterInfo();
	    if(room!=null) filter.getFilterItems().add(new FilterItemInfo("srcRoom.name", "%"+room+"%",CompareType.LIKE));
	    if(customer!=null) filter.getFilterItems().add(new FilterItemInfo("srcCustomerNames", "%"+customer+"%",CompareType.LIKE));
	    if(phone!=null) filter.getFilterItems().add(new FilterItemInfo("srcCustomerPhone", "%"+phone+"%",CompareType.LIKE));
	    
	    if(bizState!=null) filter.getFilterItems().add(new FilterItemInfo("bizState", bizState));
	    if(bizType!=null) filter.getFilterItems().add(new FilterItemInfo("bizType", bizType));
	    
	    if(bizAdscriptionDateFrom!=null) filter.getFilterItems().add(new FilterItemInfo("busAdscriptionDate", FDCDateHelper.getSQLBegin(bizAdscriptionDateFrom),CompareType.GREATER_EQUALS));
	    if(bizAdscriptionDateTo!=null) filter.getFilterItems().add(new FilterItemInfo("busAdscriptionDate", FDCDateHelper.getSQLEnd(bizAdscriptionDateTo),CompareType.LESS_EQUALS));
	    
	    if(changeDateFrom!=null) filter.getFilterItems().add(new FilterItemInfo("changeDate", FDCDateHelper.getSQLBegin(changeDateFrom),CompareType.GREATER_EQUALS));
	    if(changeDateTo!=null) filter.getFilterItems().add(new FilterItemInfo("changeDate", FDCDateHelper.getSQLEnd(changeDateTo),CompareType.LESS_EQUALS));
	    
	    if(changeReason!=null) filter.getFilterItems().add(new FilterItemInfo("changeReason.id", changeReason,CompareType.INNER));
		   
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
	    if(this.comboBizState.getSelectedItem()!=null){
	    	param.add("bizState", ((TransactionStateEnum)this.comboBizState.getSelectedItem()).getValue());
	    }
	    if(this.comboBizType.getSelectedItem()!=null){
	    	param.add("bizType", ((ChangeBizTypeEnum)this.comboBizType.getSelectedItem()).getValue());
	    }
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
	    if(this.prmtChangeReson.getValue() != null){
	    	Object[] changeReason = (Object[])prmtChangeReson.getValue();
	    	String changeReasonString="";
	    	for(int i=0;i<changeReason.length;i++){
	    		if(changeReason[i]==null) continue;
	    		if(i==0)
	    			changeReasonString+="'"+((ChangeReasonInfo)changeReason[i]).getId().toString()+"'";
	    		else
	    			changeReasonString+=",'"+((ChangeReasonInfo)changeReason[i]).getId().toString()+"'";
	    	}
	    	if(!changeReasonString.equals("")) param.add("changeReason", changeReasonString);
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
	   
	    String bizState=para.getString("bizState");
	    String bizType=para.getString("bizType");
	    
	    Date bizAdscriptionDateFrom=para.getDate("bizAdscriptionDateFrom");
	    Date bizAdscriptionDateTo=para.getDate("bizAdscriptionDateTo");
	    Date changeDateFrom=para.getDate("changeDateFrom");
	    Date changeDateTo=para.getDate("changeDateTo");
	    
	    
	    this.txtRoom.setText(room);
	    this.txtCustomer.setText(customer);
	    this.txtPhone.setText(phone);
	    
	    if(bizState!=null)	this.comboBizState.setSelectedItem(TransactionStateEnum.getEnum(bizState));
	    if(bizType!=null)	this.comboBizState.setSelectedItem(ChangeBizTypeEnum.getEnum(bizType));
	    
	    this.pkBizAdscriptionDateFrom.setValue(bizAdscriptionDateFrom);
	    this.pkBizAdscriptionDateTo.setValue(bizAdscriptionDateTo);
	    this.pkChangeDateFrom.setValue(changeDateFrom);
	    this.pkChangeDateTo.setValue(changeDateTo);
	    
	    String changeReason=para.getString("changeReason");
	    if(changeReason != null){
			try {
				ChangeReasonCollection col = ChangeReasonFactory.getRemoteInstance().getChangeReasonCollection("select * from where id in("+changeReason+")");
				this.prmtChangeReson.setValue(col.toArray());
			} catch (BOSException e) {
				e.printStackTrace();
			}
	    }
	    super.setCustomerParams(para.getCustomerParams());
	}
}