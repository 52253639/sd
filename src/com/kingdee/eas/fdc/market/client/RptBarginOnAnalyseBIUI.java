/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.analysis.bicomponent.DataCellItem;
import com.kingdee.bos.ctrl.analysis.bicomponent.MemberCellItem;
import com.kingdee.bos.ctrl.analysis.olap.Member;
import com.kingdee.bos.ctrl.kdf.headfootdesigner.HeadFootModel;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.IndustryInfo;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.market.RptBarginOnAnalyseFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerGradeInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerOriginInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.HabitationAreaInfo;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo;
import com.kingdee.eas.fdc.sellhouse.ProductDetialInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFormInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SexEnum;
import com.kingdee.eas.fdc.sellhouse.SightRequirementInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.bireport.IBireportBaseFacade;
import com.kingdee.eas.framework.bireport.client.BireportBaseFilterUI;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class RptBarginOnAnalyseBIUI extends AbstractRptBarginOnAnalyseBIUI
{
    private static final Logger logger = CoreUIObject.getLogger(RptBarginOnAnalyseBIUI.class);
    
    /**
     * output class constructor
     */
    public RptBarginOnAnalyseBIUI() throws Exception
    {
        super();
    }

    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
    	handlePermissionForItemAction(actionExport);
        super.actionExport_actionPerformed(e);
    }

    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
    	handlePermissionForItemAction(actionExportSelected);
        super.actionExportSelected_actionPerformed(e);
    }
    
    
	protected RptParams getParamsForInit() {
		return null;
	}

	protected RptParams getParamsForRequest() {
		return this.params;
	}

	protected IBireportBaseFacade getRemoteInstance() throws BOSException {
		return RptBarginOnAnalyseFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return this.tblMain;
	}

	protected void onAfterQuery() throws Exception {
		
	}

	protected void onBeforeQuery() throws Exception {
		
	}

	protected void preparePrintPageHeader(HeadFootModel header) {
		
	}

	protected Map preparePrintVariantMap() {
		return null;
	}

    protected BireportBaseFilterUI getQueryDialogUserPanel() throws Exception {
    	return new RptBarginOnAnalyseFilterUI();
    }
    
     protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
    	 if (e.getButton() == 1 && e.getClickCount() == 2) {
			if(e.getType()!=1) return;		
		
/*			boolean radioRoomNoDimension = ((Boolean)params.getObjectElement("radioRoomNoDimension.value")).booleanValue();
			boolean radioCustNoDimension = ((Boolean)params.getObjectElement("radioCustNoDimension.value")).booleanValue();
			//只对有房间维度且有客户维度的才能查看认购单	   
			if(radioRoomNoDimension || radioCustNoDimension) return;*/
			
			Object cellObject = this.tblMain.getCell(e.getRowIndex(), e.getColIndex()).getValue();
			//必须是数据单元
			if(!(cellObject instanceof DataCellItem)) return;
			//如果没有数值，双击无效
			DataCellItem dataCell = (DataCellItem)cellObject;
			if(dataCell.getValue() instanceof BigDecimal){ 
				if((BigDecimal)dataCell.getValue()==null || ((BigDecimal)dataCell.getValue()).compareTo(new BigDecimal(0))<=0)  return;
			}else if(dataCell.getValue() instanceof Integer){
				if((Integer)dataCell.getValue()==null || ((Integer)dataCell.getValue()).compareTo(new Integer(0))<=0)  return;
			}else{
				return;
			}
			
			
			Member[] members = this.getSelectedDataCellItemInfo();
			
			FilterInfo roomFilter = getRoomFilterInfo(this.getRoomDimensionValue(members));
			Set roomStatSet = new HashSet();
			roomStatSet.add(RoomSellStateEnum.PURCHASE_VALUE);
			roomStatSet.add(RoomSellStateEnum.SIGN_VALUE);
			roomFilter.getFilterItems().add(new FilterItemInfo("room.SellState",roomStatSet, CompareType.INCLUDE));			
			roomFilter.getFilterItems().add(new FilterItemInfo("head.state",FDCBillStateEnum.AUDITTED_VALUE));
			roomFilter.getFilterItems().add(new FilterItemInfo("room.isForSHE",new Integer(1)));			
			FilterInfo customerFilter = getCustomerFilterInfo(this.getCustomerDimensionValue(members));
			
			roomFilter.mergeFilter(customerFilter, "AND");
			
			UIContext uiContext = new UIContext(this); 
			uiContext.put("PurchaseFilter", roomFilter);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(RptBarginOnPurchaseListUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
			
		}

    }
     

     
     private String getCustomerDimensionValue(Member[] members) {
    	 String dimenSionName = "";
     	boolean radioCustomerType = ((Boolean)params.getObjectElement("radioCustomerType.value")).booleanValue();
    	boolean radioCustomerDegree = ((Boolean)params.getObjectElement("radioCustomerDegree.value")).booleanValue();
    	boolean cadioCustomerOrigin = ((Boolean)params.getObjectElement("cadioCustomerOrigin.value")).booleanValue();
    	boolean radioCustOriType = ((Boolean)params.getObjectElement("radioCustOriType.value")).booleanValue();
    	boolean radioCreator = ((Boolean)params.getObjectElement("radioCreator.value")).booleanValue();
    	boolean radioSaleMan = ((Boolean)params.getObjectElement("radioSaleMan.value")).booleanValue();
    	boolean radioTradeTimes = ((Boolean)params.getObjectElement("radioTradeTimes.value")).booleanValue();
    	boolean radioProvince = ((Boolean)params.getObjectElement("radioProvince.value")).booleanValue();
    	boolean radioHabitationArea = ((Boolean)params.getObjectElement("radioHabitationArea.value")).booleanValue();
    	boolean radioSex = ((Boolean)params.getObjectElement("radioSex.value")).booleanValue();
    	boolean radioFamilyEarning = ((Boolean)params.getObjectElement("radioFamilyEarning.value")).booleanValue();
    	boolean radioFirstReception = ((Boolean)params.getObjectElement("radioFirstReception.value")).booleanValue();
    	boolean radioEnterpriceSize = ((Boolean)params.getObjectElement("radioEnterpriceSize.value")).booleanValue();
    	boolean radioIndustry = ((Boolean)params.getObjectElement("radioIndustry.value")).booleanValue();
    	 
      	if(radioCustomerType)	dimenSionName = "[CustomerType]";
    	else if(radioCustomerDegree)	dimenSionName = "[CustomerGrade]";
    	else if(cadioCustomerOrigin)	dimenSionName = "[CustomerOrigin]";
    	else if(radioCustOriType)	dimenSionName = "[CustOriType]";
    	else if(radioCreator)	dimenSionName = "[Creator]";
    	else if(radioSaleMan)	dimenSionName = "[Salesman]";
    	else if(radioTradeTimes)	dimenSionName = "[TradeTime]";
    	else if(radioProvince)	dimenSionName = "[Province]";
    	else if(radioHabitationArea)	dimenSionName = "[HabitationArea]";
    	else if(radioSex)	dimenSionName = "[Sex]";
    	else if(radioFamilyEarning)	dimenSionName = "[FamillyEarning]";
    	else if(radioFirstReception)	dimenSionName = "[ReceptionType]";
    	else if(radioEnterpriceSize)	dimenSionName = "[EnterpriceSize]";
    	else if(radioIndustry)	dimenSionName = "[ReceptionType]";
    	else if(radioFirstReception)	dimenSionName = "[Industry]";
    	 
      	for(int i=0;i<members.length;i++) {
      		Member member = members[i];
      		String compStr = member.getUniqueName().substring(0,member.getUniqueName().indexOf(".[")); 
      		if(compStr.equals(dimenSionName)) return (String)member.getKey();
      	}
    	 return "";
     }
     
     
     private String getRoomDimensionValue(Member[] members) {
    	 String dimenSionName = "";
    	 
     	boolean radioBuilding = ((Boolean)params.getObjectElement("radioBuilding.value")).booleanValue();
    	boolean radioFloor = ((Boolean)params.getObjectElement("radioFloor.value")).booleanValue();
    	boolean radioHouseProperty = ((Boolean)params.getObjectElement("radioHouseProperty.value")).booleanValue();
    	boolean radioRoomModelType = ((Boolean)params.getObjectElement("radioRoomModelType.value")).booleanValue();
    	boolean radioRoomModel = ((Boolean)params.getObjectElement("radioRoomModel.value")).booleanValue();
    	boolean radioProductDetail = ((Boolean)params.getObjectElement("radioProductDetail.value")).booleanValue();
    	boolean radioProductType = ((Boolean)params.getObjectElement("radioProductType.value")).booleanValue();
    	boolean radioBuildingProperty = ((Boolean)params.getObjectElement("radioBuildingProperty.value")).booleanValue();
    	boolean radioRoomForm = ((Boolean)params.getObjectElement("radioRoomForm.value")).booleanValue();
    	boolean radioSight = ((Boolean)params.getObjectElement("radioSight.value")).booleanValue();
    	boolean radioDirection = ((Boolean)params.getObjectElement("radioDirection.value")).booleanValue();   
    	
  	    if(radioBuilding)	dimenSionName = "[Building]";
  	    else if(radioFloor)	dimenSionName = "[Floor]";	
  	    else if(radioHouseProperty)	dimenSionName = "[HouseProperty]";
  	    else if(radioRoomModelType)	dimenSionName = "[RoomModelType]";
  	    else if(radioRoomModel)	dimenSionName = "[RoomModel]";
  	    else if(radioProductDetail)	dimenSionName = "[ProductDetial]";
  	    else if(radioProductType)	dimenSionName = "[ProductType]";
  	    else if(radioBuildingProperty)	dimenSionName = "[BuildingProperty]";
  	    else if(radioRoomForm)	dimenSionName = "[RoomForm]";
  	    else if(radioSight)	dimenSionName = "[SightRequirement]";
  	    else if(radioDirection)	dimenSionName = "[HopedDirection]";
    	
      	for(int i=0;i<members.length;i++) {
      		Member member = members[i];
      		String compStr = member.getUniqueName().substring(0,member.getUniqueName().indexOf(".[")); 
      		if(compStr.equals(dimenSionName)) {
      			if(member.getKey() instanceof String)
      				return (String)member.getKey();
      			else if(member.getKey() instanceof BigDecimal)
      				return ((BigDecimal)member.getKey()).toString();
      			else if(member.getKey() instanceof Integer)
      				return ((Integer)member.getKey()).toString();
      		}
      	}
    	 return "";
     }
     
     
     
     
     private FilterInfo getRoomFilterInfo(String rowNameValue) {
    	 FilterInfo filter = new FilterInfo();
     	//房间纬度的过滤条件
     	SellProjectInfo sellProInfo = (SellProjectInfo)params.getObjectElement("promptSellProject.value");
     	BuildingInfo buildInfo = (BuildingInfo)params.getObjectElement("prmtBuilding.value");
     	RoomModelInfo roomModelInfo = (RoomModelInfo)params.getObjectElement("prmtRoomModel.value");
     	RoomModelTypeInfo roomModelTypeInfo = (RoomModelTypeInfo)params.getObjectElement("prmtRoomModelType.value");
     	ProductDetialInfo productDetailInfo = (ProductDetialInfo)params.getObjectElement("prmtProductDetail.value");
     	ProductTypeInfo productTypeInfo = (ProductTypeInfo)params.getObjectElement("prmtProductType.value");
     	BuildingPropertyInfo buildingPropertyInfo = (BuildingPropertyInfo)params.getObjectElement("prmtBuildingProperty.value");
     	RoomFormInfo roomFormInfo = (RoomFormInfo)params.getObjectElement("prmtRoomForm.value");
     	SightRequirementInfo sightInfo = (SightRequirementInfo)params.getObjectElement("prmtSight.value");
     	HopedDirectionInfo directionInfo = (HopedDirectionInfo)params.getObjectElement("prmtDirection.value");
     	String minAreaCount = (String)params.getObjectElement("txtMinAreaCount.value");
	     	if(minAreaCount!=null && !minAreaCount.trim().equals("")) minAreaCount = minAreaCount.replaceAll(",", "");
	     	else minAreaCount = null;
     	String maxAreaCount = (String)params.getObjectElement("txtMaxAreaCount.value");
	     	if(maxAreaCount!=null && !maxAreaCount.trim().equals("")) maxAreaCount = maxAreaCount.replaceAll(",", "");
	     	else maxAreaCount = null;
     	boolean radioBuildingArea = ((Boolean)params.getObjectElement("radioBuildingArea.value")).booleanValue();
     	boolean radioRoomArea = ((Boolean)params.getObjectElement("radioRoomArea.value")).booleanValue();
     	String minPriceCount = (String)params.getObjectElement("txtMinPriceCount.value");
	     	if(minPriceCount!=null && !minPriceCount.trim().equals("")) minPriceCount = minPriceCount.replaceAll(",", "");
	     	else minPriceCount = null;
     	String maxPriceCount = (String)params.getObjectElement("txtMaxPriceCount.value");
	     	if(maxPriceCount!=null && !maxPriceCount.trim().equals("")) maxPriceCount = maxPriceCount.replaceAll(",", "");
	     	else maxPriceCount = null;
     	boolean radioBuildPerPrice = ((Boolean)params.getObjectElement("radioBuildPerPrice.value")).booleanValue();
     	boolean radioRoomPerPrice = ((Boolean)params.getObjectElement("radioRoomPerPrice.value")).booleanValue();
     	boolean radioDealPerPrice = ((Boolean)params.getObjectElement("radioDealPerPrice.value")).booleanValue();
     	boolean radioStandardTotalPrice = ((Boolean)params.getObjectElement("radioStandardTotalPrice.value")).booleanValue();
     	boolean radioDealTotalPrice = ((Boolean)params.getObjectElement("radioDealTotalPrice.value")).booleanValue();
     	boolean radioContactTotalPrice = ((Boolean)params.getObjectElement("radioContactTotalPrice.value")).booleanValue();    
    	
    	if(sellProInfo!=null) 
    		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProInfo.getId().toString()));
    	else{  //默认只能看到当前用户能看到的项目
    		try {
				Set permitProIds = CommerceHelper.getPermitProjectIds();
				permitProIds.add("null");
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id",permitProIds,CompareType.INCLUDE));
			} catch (BOSException e) {
				e.printStackTrace();
			}
    	}
    	if(buildInfo!=null) filter.getFilterItems().add(new FilterItemInfo("building.id",buildInfo.getId().toString()));
    	if(roomModelInfo!=null) filter.getFilterItems().add(new FilterItemInfo("roomModel.id",roomModelInfo.getId().toString()));
    	if(roomModelTypeInfo!=null) filter.getFilterItems().add(new FilterItemInfo("roomModelType.id",roomModelTypeInfo.getId().toString()));
    	if(productDetailInfo!=null) filter.getFilterItems().add(new FilterItemInfo("productDetail.id",productDetailInfo.getId().toString()));
    	if(productTypeInfo!=null) filter.getFilterItems().add(new FilterItemInfo("productType.id",productTypeInfo.getId().toString()));
    	if(buildingPropertyInfo!=null) filter.getFilterItems().add(new FilterItemInfo("buildingProperty.id",buildingPropertyInfo.getId().toString()));
    	if(roomFormInfo!=null) filter.getFilterItems().add(new FilterItemInfo("roomForm.id",roomFormInfo.getId().toString()));
    	if(sightInfo!=null) filter.getFilterItems().add(new FilterItemInfo("sight.id",sightInfo.getId().toString()));
    	if(directionInfo!=null) filter.getFilterItems().add(new FilterItemInfo("direction.id",directionInfo.getId().toString()));
    	if(minAreaCount!=null || maxAreaCount!=null) {
    		String compareExp = "";
    		if(radioBuildingArea) compareExp = "room.buildingArea";
    		else if(radioRoomArea)  compareExp = "room.roomArea";
    		if(!compareExp.equals("")) {
    			if(minAreaCount!=null)
    				filter.getFilterItems().add(new FilterItemInfo(compareExp,minAreaCount,CompareType.GREATER_EQUALS));
    			else	
    				filter.getFilterItems().add(new FilterItemInfo(compareExp,maxAreaCount,CompareType.LESS_EQUALS));
    		}
    	}    	
    	if(minPriceCount!=null || maxPriceCount!=null) {
    		String compareExp = "";
    		if(radioBuildPerPrice)	compareExp = "room.buildPrice";
    		else if(radioRoomPerPrice)	compareExp = "room.roomPrice";
    		else if(radioDealPerPrice)	compareExp = "room.dealPrice";
    		else if(radioStandardTotalPrice)	compareExp = "room.standardTotalAmount";
    		else if(radioDealTotalPrice)	compareExp = "room.dealTotalAmount";
    		else if(radioContactTotalPrice)	compareExp = "head.contractTotalAmount";
    		if(!compareExp.equals("")) {
    			if(minPriceCount!=null)
    				filter.getFilterItems().add(new FilterItemInfo(compareExp,minAreaCount,CompareType.GREATER_EQUALS));
    			else
    				filter.getFilterItems().add(new FilterItemInfo(compareExp,minAreaCount,CompareType.LESS_EQUALS));
    		}
    	}
    	
    	//维度
    	boolean radioBuilding = ((Boolean)params.getObjectElement("radioBuilding.value")).booleanValue();
    	boolean radioFloor = ((Boolean)params.getObjectElement("radioFloor.value")).booleanValue();
    	boolean radioHouseProperty = ((Boolean)params.getObjectElement("radioHouseProperty.value")).booleanValue();
    	boolean radioRoomModelType = ((Boolean)params.getObjectElement("radioRoomModelType.value")).booleanValue();
    	boolean radioRoomModel = ((Boolean)params.getObjectElement("radioRoomModel.value")).booleanValue();
    	boolean radioProductDetail = ((Boolean)params.getObjectElement("radioProductDetail.value")).booleanValue();
    	boolean radioProductType = ((Boolean)params.getObjectElement("radioProductType.value")).booleanValue();
    	boolean radioBuildingProperty = ((Boolean)params.getObjectElement("radioBuildingProperty.value")).booleanValue();
    	boolean radioRoomForm = ((Boolean)params.getObjectElement("radioRoomForm.value")).booleanValue();
    	boolean radioSight = ((Boolean)params.getObjectElement("radioSight.value")).booleanValue();
    	boolean radioDirection = ((Boolean)params.getObjectElement("radioDirection.value")).booleanValue();    	
    	String compareExp = "";
    	if(radioBuilding)	compareExp = "building.id";
    	else if(radioFloor)	compareExp = "room.floor";
    	else if(radioHouseProperty)	compareExp = "room.houseProperty";
    	else if(radioRoomModelType)	compareExp = "roomModelType.id";
    	else if(radioRoomModel)	compareExp = "roomModel.id";
    	else if(radioProductDetail)	compareExp = "productDetail.id";
    	else if(radioProductType)	compareExp = "productType.id";
    	else if(radioBuildingProperty)	compareExp = "buildingProperty.longNumber";  //树形结构
    	else if(radioRoomForm)	compareExp = "roomForm.id";
    	else if(radioSight)	compareExp = "sight.id";
    	else if(radioDirection)	compareExp = "direction.id";
    	if(!compareExp.equals("")) {
    		if(rowNameValue!=null && !rowNameValue.equals("")) {
    			if(compareExp.equals("buildingProperty.longNumber")) {
    				filter.getFilterItems().add(new FilterItemInfo(compareExp,rowNameValue+"%",CompareType.LIKE));
    			}else{
    				filter.getFilterItems().add(new FilterItemInfo(compareExp,rowNameValue));
    			}
    		}else
    			filter.getFilterItems().add(new FilterItemInfo(compareExp,null,CompareType.NOTEQUALS));
    	}
    	 return filter;
     }
     
     
     
     private FilterInfo getCustomerFilterInfo(String colNameValue) {
    	 FilterInfo filter = new FilterInfo();
     	//客户纬度的过滤条件
     	CustomerTypeEnum customerTypeEnum = (CustomerTypeEnum)params.getObjectElement("comboCustomerType.SelectedItem");
     	SexEnum sexEnum = (SexEnum)params.getObjectElement("comboSex.SelectedItem");
     	IndustryInfo industryInfo = (IndustryInfo)params.getObjectElement("prmtIndustry.value");
     	CustomerGradeInfo customerGradeInfo = (CustomerGradeInfo)params.getObjectElement("promptCustomerDegree.value");
     	CustomerOriginInfo customerOriginInfo = (CustomerOriginInfo)params.getObjectElement("prmtCustomerOrigin.value");
     	ProvinceInfo provinceInfo = (ProvinceInfo)params.getObjectElement("prmtProvince.value");
     	HabitationAreaInfo habitationAreaInfo = (HabitationAreaInfo)params.getObjectElement("prmtHabitation.value");
     	UserInfo saleManInfo = (UserInfo)params.getObjectElement("prmtSaleMan.value");
     	UserInfo creatorInfo = (UserInfo)params.getObjectElement("prmtCreator.value");
     	String tradeTimes = (String)params.getObjectElement("txtTradeTimes.value");  //单指售楼销售次数
	     	if(tradeTimes!=null && !tradeTimes.trim().equals("")) tradeTimes = tradeTimes.replaceAll(",", "");
	     	else tradeTimes = null;
     	Date minCustDate = (Date)params.getObjectElement("minCustDate.value");
     	Date maxCustDate = (Date)params.getObjectElement("maxCustDate.value");
     	boolean checkOnlyMainCustomer = ((Boolean)params.getObjectElement("checkOnlyMainCustomer.value")).booleanValue();
     	
     	if(customerTypeEnum!=null) filter.getFilterItems().add(new FilterItemInfo("customer.customerType",customerTypeEnum.getValue()));
     	if(sexEnum!=null) filter.getFilterItems().add(new FilterItemInfo("customer.sex",sexEnum.getValue()));
     	if(industryInfo!=null) filter.getFilterItems().add(new FilterItemInfo("industry.id",industryInfo.getId().toString()));
     	if(customerGradeInfo!=null) filter.getFilterItems().add(new FilterItemInfo("customerGrade.id",customerGradeInfo.getId().toString()));
     	if(customerOriginInfo!=null) filter.getFilterItems().add(new FilterItemInfo("customerOrigin.id",customerOriginInfo.getId().toString()));
     	if(provinceInfo!=null) filter.getFilterItems().add(new FilterItemInfo("province.id",provinceInfo.getId().toString()));
     	if(habitationAreaInfo!=null) filter.getFilterItems().add(new FilterItemInfo("habitationArea.id",habitationAreaInfo.getId().toString()));
     	if(saleManInfo!=null) filter.getFilterItems().add(new FilterItemInfo("salesman.id",saleManInfo.getId().toString()));
     	if(creatorInfo!=null) filter.getFilterItems().add(new FilterItemInfo("creator.id",creatorInfo.getId().toString()));
     	if(tradeTimes!=null) filter.getFilterItems().add(new FilterItemInfo("customer.tradeTime",tradeTimes));
     	if(minCustDate!=null)	filter.getFilterItems().add(new FilterItemInfo("customer.CreateTime",minCustDate,CompareType.GREATER_EQUALS));
     	if(maxCustDate!=null)	filter.getFilterItems().add(new FilterItemInfo("customer.CreateTime",maxCustDate,CompareType.LESS_EQUALS));
     	if(checkOnlyMainCustomer) filter.getFilterItems().add(new FilterItemInfo("seq",new Integer(0)));
     	
     	//维度
    	boolean radioCustomerType = ((Boolean)params.getObjectElement("radioCustomerType.value")).booleanValue();
    	boolean radioCustomerDegree = ((Boolean)params.getObjectElement("radioCustomerDegree.value")).booleanValue();
    	boolean cadioCustomerOrigin = ((Boolean)params.getObjectElement("cadioCustomerOrigin.value")).booleanValue();
    	boolean radioCustOriType = ((Boolean)params.getObjectElement("radioCustOriType.value")).booleanValue();
    	boolean radioCreator = ((Boolean)params.getObjectElement("radioCreator.value")).booleanValue();
    	boolean radioSaleMan = ((Boolean)params.getObjectElement("radioSaleMan.value")).booleanValue();
    	boolean radioTradeTimes = ((Boolean)params.getObjectElement("radioTradeTimes.value")).booleanValue();
    	boolean radioProvince = ((Boolean)params.getObjectElement("radioProvince.value")).booleanValue();
    	boolean radioHabitationArea = ((Boolean)params.getObjectElement("radioHabitationArea.value")).booleanValue();
    	boolean radioSex = ((Boolean)params.getObjectElement("radioSex.value")).booleanValue();
    	boolean radioFamilyEarning = ((Boolean)params.getObjectElement("radioFamilyEarning.value")).booleanValue();
    	boolean radioFirstReception = ((Boolean)params.getObjectElement("radioFirstReception.value")).booleanValue();
    	boolean radioEnterpriceSize = ((Boolean)params.getObjectElement("radioEnterpriceSize.value")).booleanValue();
    	boolean radioIndustry = ((Boolean)params.getObjectElement("radioIndustry.value")).booleanValue();
     	String compareExp = "";
     	if(radioCustomerType)	compareExp = "customer.customerType";
     	else if(radioCustomerDegree)	compareExp = "customerGrade.id";
     	else if(cadioCustomerOrigin)	compareExp = "customerOrigin.id";
     	else if(radioCustOriType)	compareExp = "group.id";
     	else if(radioCreator)	compareExp = "creator.id";
     	else if(radioSaleMan)	compareExp = "salesman.id";
     	else if(radioTradeTimes)	compareExp = "customer.tradeTime";
     	else if(radioProvince)	compareExp = "province.id";
     	else if(radioHabitationArea)	compareExp = "habitationArea.longNumber";	//树形结构
     	else if(radioSex)	compareExp = "customer.sex";
     	else if(radioFamilyEarning)	compareExp = "famillyEarning.id";
     	else if(radioFirstReception)	compareExp = "receptionType.id";
     	else if(radioEnterpriceSize)	compareExp = "customer.enterpriceSize";
     	else if(radioIndustry)	compareExp = "industry.id";
     	if(!compareExp.equals("")){
     		if(colNameValue!=null && !colNameValue.equals("")) {
     			if(compareExp.equals("habitationArea.longNumber")) {
     				filter.getFilterItems().add(new FilterItemInfo(compareExp,colNameValue+"%",CompareType.LIKE));
     			}else{
     				filter.getFilterItems().add(new FilterItemInfo(compareExp,colNameValue));
     			}
     		}else
     			filter.getFilterItems().add(new FilterItemInfo(compareExp,null,CompareType.NOTEQUALS));
     	}
    	 return filter;
     }
     
    
    
    
    
    

}