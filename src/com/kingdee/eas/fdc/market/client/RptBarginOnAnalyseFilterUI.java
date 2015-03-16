/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SexEnum;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptConditionManager;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RptBarginOnAnalyseFilterUI extends AbstractRptBarginOnAnalyseFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(RptBarginOnAnalyseFilterUI.class);
    
    /**
     * output class constructor
     */
    public RptBarginOnAnalyseFilterUI() throws Exception
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
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	
    	this.promptSellProject.setEntityViewInfo(CommerceHelper.getPermitProjectView());  //可看到的项目
    	
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("sellProject.id","null"));
    	view.setFilter(filter);
    	this.prmtBuilding.setEntityViewInfo(view);
    	this.prmtProductDetail.setEntityViewInfo(view);    
    	
    	
    	this.comboCustomerType.addItem("");
    	this.comboCustomerType.setSelectedItem(null);
    	this.comboSex.addItem("");
    	
    	this.minCustDate.setValue(null);;
    	this.maxCustDate.setValue(null);
    	
		this.radioSex.setVisible(false);
		this.radioIndustry.setVisible(false);
		this.radioFamilyEarning.setVisible(false);
		this.radioEnterpriceSize.setVisible(false);
    }
    
   
    
    //房间维度  销售项目 变化控制楼栋和产品描述的变化
    protected void promptSellProject_dataChanged(DataChangeEvent e)
    		throws Exception {
    	String sellProId = "null";
    	SellProjectInfo sellProInfo = (SellProjectInfo)this.promptSellProject.getValue();
    	if(sellProInfo!=null) sellProId = sellProInfo.getId().toString();
    	
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProId));
    	view.setFilter(filter);
    	this.prmtBuilding.setEntityViewInfo(view);
    	
    	view = new EntityViewInfo();
    	filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProId));
    	view.setFilter(filter);
    	this.prmtProductDetail.setEntityViewInfo(view);    	
    }
    
    //房间维度  楼栋 变化控制
    protected void prmtBuilding_dataChanged(DataChangeEvent e) throws Exception {
    	String buildId = "null";
    	BuildingInfo buildInfo = (BuildingInfo)this.prmtBuilding.getValue();
    	if(buildInfo!=null) buildId = buildInfo.getId().toString();
    	
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("building.id",buildId));
    	view.setFilter(filter);
    	this.prmtRoomModel.setEntityViewInfo(view);
    }
    
    //客户纬度  客户类型 变化控制
    protected void boxCustomerType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    	this.comboSex.setSelectedItem(null);
    	this.contSex.setVisible(false);
    	this.prmtIndustry.setValue(null);
    	this.radioSex.setSelected(false);
    	this.radioIndustry.setSelected(false);
    	this.radioFamilyEarning.setSelected(false);
    	this.radioEnterpriceSize.setSelected(false);
		//this.comboSex.setVisible(false);
		//this.prmtIndustry.setVisible(false);
		this.contIndustry.setVisible(false);
		this.radioSex.setVisible(false);
		this.radioIndustry.setVisible(false);
		this.radioFamilyEarning.setVisible(false);
		this.radioEnterpriceSize.setVisible(false);
		
		Object custTypeObject = this.comboCustomerType.getSelectedItem();
    	CustomerTypeEnum custType = null;
    	if(custTypeObject!=null && custTypeObject instanceof CustomerTypeEnum)
    		custType = (CustomerTypeEnum)custTypeObject;
    	if(custType==null) return;
    	if(custType.equals(CustomerTypeEnum.EnterpriceCustomer)) {
    		//this.prmtIndustry.setVisible(true);
    		this.contIndustry.setVisible(true);
    		this.radioIndustry.setVisible(true);
    		this.radioEnterpriceSize.setVisible(true);
    	}else if(custType.equals(CustomerTypeEnum.PersonalCustomer)) {
    		//this.comboSex.setVisible(true);
    		this.contSex.setVisible(true);
    		this.radioSex.setVisible(true);
    		this.radioFamilyEarning.setVisible(true);
    	}
    }

    
	public void onInit(RptParams initParams) throws Exception {
	}

    
    public RptParams getCustomCondition() {
    	RptConditionManager rcm=new RptConditionManager(); 
    	rcm.recordAllStatus(this); //保存控件值 
    	
    	return rcm.toRptParams();
	}

	public void setCustomCondition(RptParams params) {
		RptConditionManager rcm=new RptConditionManager(params); 
		rcm.restoreAllStatus(this); //设置控件值
	}

	public boolean verify() {
		String waringString = "";
		BigDecimal minArea = (BigDecimal)this.txtMinAreaCount.getNumberValue();
		BigDecimal maxArea = (BigDecimal)this.txtMaxAreaCount.getNumberValue();
		if(minArea!=null && maxArea!=null) {
			if(minArea.compareTo(maxArea)>0)  {
				waringString += "面积范围不符合逻辑！\r\n";
			}
		}
		BigDecimal minPrice = (BigDecimal)this.txtMinPriceCount.getNumberValue();
		BigDecimal maxPrice = (BigDecimal)this.txtMaxPriceCount.getNumberValue();
		if(minPrice!=null && maxPrice!=null) {
			if(minPrice.compareTo(maxPrice)>0) { 
				waringString += "价格范围不符合逻辑！\r\n";
			}
		}
		Date minCustDate = (Date)this.minCustDate.getValue();
		Date maxCustDate = (Date)this.maxCustDate.getValue();			  
		if(minCustDate!=null && maxCustDate!=null) {
			maxCustDate = FDCDateHelper.getNextDay(maxCustDate);
			if(minCustDate.after(maxCustDate)) {
				waringString += "登记时间范围不符合逻辑！";
			}
		}
		
		if(!waringString.equals("")) {
			MsgBox.showWarning(waringString);
			return false;
		}
		
		return true;
	}

}