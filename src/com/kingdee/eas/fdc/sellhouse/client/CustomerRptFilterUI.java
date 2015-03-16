/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.UserType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CreateWayEnum;
import com.kingdee.eas.fdc.basecrm.CustomerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class CustomerRptFilterUI extends AbstractCustomerRptFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(CustomerRptFilterUI.class);
    
    /**
     * output class constructor
     */
    public CustomerRptFilterUI() throws Exception
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
    public void onLoad() throws Exception{  //add by shilei 
    	super.onLoad();
    	//把客户类型和建立方式初始化为空
    	comboCustomerType.addItem("");
    	comboCustomerType.addItem(CustomerTypeEnum.PERSONALCUSTOMER);
    	comboCustomerType.addItem(CustomerTypeEnum.ENTERPRICECUSTOMER);
    	EstablishWay.addItem("");
    	EstablishWay.addItem(CreateWayEnum.HAND);
    	EstablishWay.addItem(CreateWayEnum.CHILDGENERATE);
    	EstablishWay.addItem(CreateWayEnum.PARENTREFERENCE);
    	EstablishWay.addItem(CreateWayEnum.UNITSHARE);
    	EstablishWay.addItem(CreateWayEnum.SYSTEMIMPORT);
    	pkCreateTime.setValue(null);
    	pkLastUpdateTime.setValue(null);
    	pkToCreateTime.setValue(null);
    	pkToLastUpdateTime.setValue(null);
    	chkIsEnabled.setSelected(true);
    	prmtSaleMan.setValue(null);
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head.isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("head.orgUnit.longnumber", SysContext.getSysContext().getCurrentCtrlUnit().getLongNumber()+"%",CompareType.LIKE));
		view.setFilter(filter);	
		MarketingUnitMemberCollection user=MarketingUnitMemberFactory.getRemoteInstance().getMarketingUnitMemberCollection(view);
		Set userId=new HashSet();
		for(int i=0;i<user.size();i++){
			userId.add(user.get(i).getMember().getId().toString());
		}
		
    	view = new EntityViewInfo();
    	filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("type", new Integer(UserType.PERSON_VALUE),CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("type",new Integer(UserType.OTHER_VALUE),CompareType.EQUALS) );
		filter.getFilterItems().add(new FilterItemInfo("isDelete", Boolean.TRUE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isLocked", Boolean.TRUE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isForbidden", Boolean.TRUE,CompareType.NOTEQUALS));
		if(user.size()>0){
			filter.getFilterItems().add(new FilterItemInfo("id", userId,CompareType.INCLUDE));
			filter.setMaskString("(#0 or #1) and #2 and #3 and #4 and #5");
		}else{
			filter.setMaskString("(#0 or #1) and #2 and #3 and #4");
		}
		view.setFilter(filter);		
		this.prmtSaleMan.setEntityViewInfo(view);
		this.prmtSaleMan.setEditable(false);
    }
    public FilterInfo getFilterInfo(){ //设置过滤条件  //add by shilei
    	super.getFilterInfo();
    	FilterInfo  filter = new FilterInfo();
    	//客户类型FDCOrgCustomer
		 if(this.comboCustomerType.getSelectedItem() instanceof CustomerTypeEnum){
   		 filter.getFilterItems().add(new FilterItemInfo("customerType",((CustomerTypeEnum)this.comboCustomerType.getSelectedItem()).getValue(),CompareType.EQUALS));
        }
		 //建立方式
		 if(this.EstablishWay.getSelectedItem() instanceof CreateWayEnum){
			 filter.getFilterItems().add(new FilterItemInfo("createWay",((CreateWayEnum)this.EstablishWay.getSelectedItem()).getValue(),CompareType.EQUALS));
		 }
		 //创建日期
		 if(this.pkCreateTime.getValue()!=null ){
			 getDateFilter(filter,pkToCreateTime,"createTime",CompareType.LESS_EQUALS);
			 getDateFilter(filter,pkCreateTime,"createTime",CompareType.GREATER_EQUALS);
		 }

		 //最后修改时间
		 if(this.pkLastUpdateTime.getValue()!=null){
			 getDateFilter(filter,pkToLastUpdateTime,"lastUpdateTime",CompareType.LESS_EQUALS);
			 getDateFilter(filter,pkLastUpdateTime,"lastUpdateTime",CompareType.GREATER_EQUALS);
		 }
		 //是否选房客户
		 if(this.chkIsChooseRoom.isSelected()==true){
			 filter.getFilterItems().add(new FilterItemInfo("isChooseRoom","1",CompareType.EQUALS));
		 }
		 //是否启用
		 if(this.chkIsEnabled.isSelected()==true){
			 filter.getFilterItems().add(new FilterItemInfo("isEnabled","1",CompareType.EQUALS));
		 }
		 //是否会员
		 if(this.chkIsInsider.isSelected()==true){
			 filter.getFilterItems().add(new FilterItemInfo("isInsider","1",CompareType.EQUALS));
		 }
		 if(this.txtName.getText()!=null&&!"".equals(this.txtName.getText().trim())){
			 filter.getFilterItems().add(new FilterItemInfo("name","%"+this.txtName.getText().trim()+"%",CompareType.LIKE));
		 }
		 Object[] saleMan = (Object[])this.prmtSaleMan.getValue();
         if(saleMan!=null ){
        	 StringBuffer user=new StringBuffer();
        	 for(int i=0;i<saleMan.length;i++){
        		 if(saleMan[i]==null) continue;
        		 if(i==0){
        			 user.append("'"+((UserInfo)saleMan[i]).getId().toString()+"'");
        		 }else{
             		user.append(",'"+((UserInfo)saleMan[i]).getId().toString()+"'");
             	 }
             }
        	 filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id",user.toString(),CompareType.INNER));
		 }
		 if(this.txtPhone.getText()!=null&&!"".equals(this.txtPhone.getText().trim())){
			 FilterInfo phoneFilter=new  FilterInfo();
			 phoneFilter.getFilterItems().add(new FilterItemInfo("phone","%"+this.txtPhone.getText().trim()+"%",CompareType.LIKE));
			 phoneFilter.getFilterItems().add(new FilterItemInfo("tel","%"+this.txtPhone.getText().trim()+"%",CompareType.LIKE));
			 phoneFilter.setMaskString("#0 or #1");
			 try {
				filter.mergeFilter(phoneFilter, "and");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		 }
		
		return filter;
    	
    }
    /**时间控件查询
     * compareType.less（结束时间） or compareType.GREATER(起始时间)
     * @param compareType  过滤类型 ：可以为空 null
     * @param filterItem 过滤条件 
     * @param filter 
     * */
    public static FilterInfo getDateFilter(FilterInfo filter, KDDatePicker pkEndBizDate, String filterItem, CompareType compareType) {
	    Date applyDate = (Date) pkEndBizDate.getValue();
	    if (applyDate != null ) {
		    Calendar calendar=null; 
		    calendar = Calendar.getInstance();
		    calendar.setTime(applyDate);
		    if(CompareType.GREATER_EQUALS.equals(compareType))
		    	calendar.add(Calendar.DATE, -1);
		    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),23, 59, 59);
		    filter.getFilterItems().add(new FilterItemInfo(filterItem,calendar.getTime(),compareType));
	    }
	    return filter;
    }
	public RptParams getCustomCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	public void onInit(RptParams arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void setCustomCondition(RptParams arg0) {
		// TODO Auto-generated method stub
		
	}

}