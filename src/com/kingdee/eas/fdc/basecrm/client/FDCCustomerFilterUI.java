/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basecrm.CreateWayEnum;
import com.kingdee.eas.fdc.basecrm.CustomerTypeEnum;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class FDCCustomerFilterUI extends AbstractFDCCustomerFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCCustomerFilterUI.class);
    
    /**
     * output class constructor
     */
    public FDCCustomerFilterUI() throws Exception
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
    	chkIsEnabled.setSelected(true);
    }
    public FilterInfo getFilterInfo(){ //设置过滤条件  //add by shilei
    	super.getFilterInfo();
    	FilterInfo  filter = new FilterInfo();
    	//客户类型FDCOrgCustomer
		 if(this.comboCustomerType.getSelectedItem() instanceof CustomerTypeEnum){
   		 filter.getFilterItems().add(new FilterItemInfo("FDCOrgCustomer.customerType",((CustomerTypeEnum)this.comboCustomerType.getSelectedItem()).getValue(),CompareType.EQUALS));
        }
		 //建立方式
		 if(this.EstablishWay.getSelectedItem() instanceof CreateWayEnum){
			 filter.getFilterItems().add(new FilterItemInfo("createWay",((CreateWayEnum)this.EstablishWay.getSelectedItem()).getValue(),CompareType.EQUALS));
		 }
		 //创建日期
		 if(this.pkCreateTime.getValue()!=null ){
			 getDateFilter(filter,pkCreateTime,"createTime",CompareType.LESS_EQUALS);
			 getDateFilter(filter,pkCreateTime,"createTime",CompareType.GREATER_EQUALS);
		 }

		 //最后修改时间
		 if(this.pkLastUpdateTime.getValue()!=null){
			 getDateFilter(filter,pkLastUpdateTime,"lastUpdateTime",CompareType.LESS_EQUALS);
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

	public void onInit(RptParams rptparams) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void setCustomCondition(RptParams rptparams) {
		// TODO Auto-generated method stub
		
	}

}