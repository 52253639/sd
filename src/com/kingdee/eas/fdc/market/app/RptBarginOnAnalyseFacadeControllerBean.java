package com.kingdee.eas.fdc.market.app;

import org.apache.log4j.Logger;
import javax.ejb.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.IndustryInfo;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerGradeInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerOriginInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.HabitationAreaInfo;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.ProductDetialInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFormInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SexEnum;
import com.kingdee.eas.fdc.sellhouse.SightRequirementInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.eas.framework.bireport.util.SchemaSource;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.SqlParams;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.enums.StringEnum;


public class RptBarginOnAnalyseFacadeControllerBean extends AbstractRptBarginOnAnalyseFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.market.app.RptBarginOnAnalyseFacadeControllerBean");


    protected SchemaSource readySchemaSource(RptParams params, Context ctx) throws BOSException, EASBizException
    {    	
    	//当前用户能看到的项目id串,当过滤条件项目为空时，默认为当前客户有权限看到的项目
    	String permitProIdStr = "'null'";
    	Set permitProIds = MarketingUnitFactory.getLocalInstance(ctx).getPermitSellProjectIdSet(ContextUtil.getCurrentUserInfo(ctx));
    	Iterator proIter = permitProIds.iterator();
    	while(proIter.hasNext()){
    		permitProIdStr += ",'" + (String)proIter.next() + "'";
    	}    	
    	
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
    	String maxAreaCount = (String)params.getObjectElement("txtMaxAreaCount.value");
    	boolean radioBuildingArea = ((Boolean)params.getObjectElement("radioBuildingArea.value")).booleanValue();
    	boolean radioRoomArea = ((Boolean)params.getObjectElement("radioRoomArea.value")).booleanValue();
    	String minPriceCount = (String)params.getObjectElement("txtMinPriceCount.value");
    	String maxPriceCount = (String)params.getObjectElement("txtMaxPriceCount.value");
    	boolean radioBuildPerPrice = ((Boolean)params.getObjectElement("radioBuildPerPrice.value")).booleanValue();
    	boolean radioRoomPerPrice = ((Boolean)params.getObjectElement("radioRoomPerPrice.value")).booleanValue();
    	boolean radioDealPerPrice = ((Boolean)params.getObjectElement("radioDealPerPrice.value")).booleanValue();
    	boolean radioStandardTotalPrice = ((Boolean)params.getObjectElement("radioStandardTotalPrice.value")).booleanValue();
    	boolean radioDealTotalPrice = ((Boolean)params.getObjectElement("radioDealTotalPrice.value")).booleanValue();
    	boolean radioContactTotalPrice = ((Boolean)params.getObjectElement("radioContactTotalPrice.value")).booleanValue();    	
    	String roomFilterString = "";
    	String onlyRoomFilterString = "";
    	if(buildInfo!=null)	   roomFilterString += " and Room.FBuildingID = '"+buildInfo.getId().toString()+"'";
    	if(roomModelInfo!=null) roomFilterString += " and Room.FRoomModelID = '"+roomModelInfo.getId().toString()+"'";
    	if(productDetailInfo!=null) roomFilterString += " and Room.FProductDetailID = '"+productDetailInfo.getId().toString()+"'";
    	if(productTypeInfo!=null) roomFilterString += " and Room.FProductTypeID = '"+productTypeInfo.getId().toString()+"'";
    	if(roomFormInfo!=null) roomFilterString += " and Room.FRoomFormID = '"+roomFormInfo.getId().toString()+"'";
    	if(sightInfo!=null) roomFilterString += " and Room.FSightID = '"+sightInfo.getId().toString()+"'";
    	if(directionInfo!=null) roomFilterString += " and Room.FDirectionID = '"+directionInfo.getId().toString()+"'";
    	if(radioBuildingArea || radioRoomArea){
    		if(minAreaCount!=null && !minAreaCount.trim().equals("")){
    			minAreaCount = minAreaCount.replaceAll(",", "");
    			if(radioBuildingArea) roomFilterString += " and Room.FBuildingArea >= "+minAreaCount;
    			else roomFilterString += " and Room.FRoomArea >= "+minAreaCount;
    		}
    		if(maxAreaCount!=null && !maxAreaCount.trim().equals("")){
    			maxAreaCount = maxAreaCount.replaceAll(",", "");
    			if(radioBuildingArea) roomFilterString += " and Room.FBuildingArea <= "+maxAreaCount;
    			else roomFilterString += " and Room.FRoomArea <= "+maxAreaCount;
    		}
    	}
    	if(radioBuildPerPrice||radioRoomPerPrice||radioDealPerPrice||radioStandardTotalPrice||radioDealTotalPrice) {
    		if(minPriceCount!=null && !minPriceCount.trim().equals("")) {
    			minPriceCount = minPriceCount.replaceAll(",", "");
    			if(radioBuildPerPrice)	roomFilterString += " and Room.FBuildPrice >= "+minPriceCount;
    			else if(radioRoomPerPrice) roomFilterString += " and Room.FRoomPrice >= "+minPriceCount;
    			else if(radioDealPerPrice) roomFilterString += " and Room.FDealPrice >= "+minPriceCount;
    			else if(radioStandardTotalPrice) roomFilterString += " and Room.FStandardTotalAmount >= "+minPriceCount;
    			else if(radioDealTotalPrice) roomFilterString += " and Room.FDealTotalAmount >= "+minPriceCount;
    		}
    		if(maxPriceCount!=null && !maxPriceCount.trim().equals("")){
    			maxPriceCount = maxPriceCount.replaceAll(",", "");
    			if(radioBuildPerPrice)	roomFilterString += " and Room.FBuildPrice >= "+maxPriceCount;
    			else if(radioRoomPerPrice) roomFilterString += " and Room.FRoomPrice >= "+maxPriceCount;
    			else if(radioDealPerPrice) roomFilterString += " and Room.FDealPrice >= "+maxPriceCount;
    			else if(radioStandardTotalPrice) roomFilterString += " and Room.FStandardTotalAmount >= "+maxPriceCount;
    			else if(radioDealTotalPrice) roomFilterString += " and Room.FDealTotalAmount >= "+maxPriceCount;
    			 
    		}
    	}  
    	onlyRoomFilterString = roomFilterString;
		if(radioContactTotalPrice) {
			if(minPriceCount!=null && !minPriceCount.trim().equals("")) {
				minPriceCount = minPriceCount.replaceAll(",", "");
				roomFilterString += " and Pruchase.FContractTotalAmount >= "+minPriceCount;
			}				
			if(maxPriceCount!=null && !maxPriceCount.trim().equals("")) {
				maxPriceCount = maxPriceCount.replaceAll(",", "");
				roomFilterString += " and Pruchase.FContractTotalAmount >= "+maxPriceCount;
			}
		}
		if(roomModelTypeInfo!=null) roomFilterString += " and RoomModelType.fid = '"+roomModelTypeInfo.getId().toString()+"'";
    	if(sellProInfo!=null)  
    		roomFilterString += " and Building.FSellProjectID = '"+sellProInfo.getId().toString()+"'";
    	else{  //默认只能看到当前用户能看到的项目  
    		roomFilterString += " and Building.FSellProjectID in ("+ permitProIdStr +")";
    	}
    	
    	//房间纬度的纬度选择
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
        String roomRowsString = "";
  	    if(radioBuilding)	roomRowsString = " [Building].Members";
  	    else if(radioFloor)	roomRowsString = " [Floor].Members";	
  	    else if(radioHouseProperty)	roomRowsString = " [HouseProperty].Members";
  	    else if(radioRoomModelType)	roomRowsString = " [RoomModelType].Members";
  	    else if(radioRoomModel)	roomRowsString = " [RoomModel].Members";
  	    else if(radioProductDetail)	roomRowsString = " [ProductDetial].Members";
  	    else if(radioProductType)	roomRowsString = " [ProductType].Members";
  	    else if(radioBuildingProperty)	roomRowsString = " [BuildingProperty].Members";
  	    else if(radioRoomForm)	roomRowsString = " [RoomForm].Members";
  	    else if(radioSight)	roomRowsString = " [SightRequirement].Members";
  	    else if(radioDirection)	roomRowsString = " [HopedDirection].Members";

    	
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
    	Date minCustDate = (Date)params.getObjectElement("minCustDate.value");
    	Date maxCustDate = (Date)params.getObjectElement("maxCustDate.value");   
    	boolean checkOnlyMainCustomer = ((Boolean)params.getObjectElement("checkOnlyMainCustomer.value")).booleanValue();
    	String customerFilterString = "";
    	if(customerTypeEnum!=null)  customerFilterString += " and Customer.FCustomerType = '"+customerTypeEnum.getValue()+"'";
    	if(sexEnum!=null)	customerFilterString += " and Customer.FSex = '"+sexEnum.getValue()+"'";
    	if(industryInfo!=null) customerFilterString += " and Customer.FIndustryID = '"+industryInfo.getId().toString()+"'";
    	if(customerGradeInfo!=null)	customerFilterString += " and Customer.FCustomerGradeID = '"+customerGradeInfo.getId().toString()+"'";
    	if(customerOriginInfo!=null)	customerFilterString += " and Customer.FCustomerGradeID = '"+customerOriginInfo.getId().toString()+"'";
    	if(provinceInfo!=null)	customerFilterString += " and Customer.FProvinceID = '"+provinceInfo.getId().toString()+"'";
    	if(saleManInfo!=null)	customerFilterString += " and Customer.FSalesmanID = '"+saleManInfo.getId().toString()+"'";
    	if(creatorInfo!=null)	customerFilterString += " and Customer.FCreatorID = '"+creatorInfo.getId().toString()+"'";
    	if(tradeTimes!=null && !tradeTimes.trim().equals(""))	{
    		tradeTimes = tradeTimes.replaceAll(",", "");
    		customerFilterString += " and Customer.FTradeTime = "+ tradeTimes;
    	}
    	SqlParams sqlParams = new SqlParams();
    	if(minCustDate!=null)	{
    		customerFilterString += " and Customer.FCreateTime >= ?";
    		sqlParams.addDate(FDCDateHelper.getSQLBegin(minCustDate));
    	}
    	if(maxCustDate!=null)	{
    		customerFilterString += " and Customer.FCreateTime <= ?";
    		sqlParams.addDate(FDCDateHelper.getSQLEnd(maxCustDate));
    	}
    	
    	
    	//客户纬度的纬度选择  radioCustomerType
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
    	
    	String customerColumnsString = "";
      	if(radioCustomerType)	customerColumnsString = " [CustomerType].Members";
    	else if(radioCustomerDegree)	customerColumnsString = " [CustomerGrade].Members";
    	else if(cadioCustomerOrigin)	customerColumnsString = " [CustomerOrigin].Members";
    	else if(radioCustOriType)	customerColumnsString = " [CustOriType].Members";
    	else if(radioCreator)	customerColumnsString = " [Creator].Members";
    	else if(radioSaleMan)	customerColumnsString = " [Salesman].Members";
    	else if(radioTradeTimes)	customerColumnsString = " [TradeTime].Members";
    	else if(radioProvince)	customerColumnsString = " [Province].Members";
    	else if(radioHabitationArea)	customerColumnsString = "  [HabitationArea].Members";
    	else if(radioSex)	customerColumnsString = " [Sex].Members";
    	else if(radioFamilyEarning)	customerColumnsString = " [FamillyEarning].Members";
    	else if(radioFirstReception)	customerColumnsString = " [ReceptionType].Members";
    	else if(radioEnterpriceSize)	customerColumnsString = " [EnterpriceSize].Members";
    	else if(radioIndustry)	customerColumnsString = " [ReceptionType].Members";
    	else if(radioFirstReception)	customerColumnsString = " [Industry].Members";
    	
    	
    	//房间的查询sql     //默认无维度
//    	String roomSql = "select '房间',FBuildingID,FFloor,FHouseProperty,'modeltype待链接'," +
//    			"FRoomModelID,FProductDetailID,FProductTypeID,FBuildingPropertyID,FRoomFormID,FSightID,FDirectionID  from T_SHE_Room";
    	String buildingSql = "select fid,fname_"+this.getLoc(ctx)+" from T_SHE_Building ";
    	if(sellProInfo!=null)  buildingSql += " where FSellProjectID = '"+sellProInfo.getId().toString()+"'";
    	else buildingSql += " where FSellProjectID in ("+ permitProIdStr +")";
    	if(buildInfo!=null)   buildingSql += " and FID = '"+buildInfo.getId().toString()+"'";//
    	
    	String roomModelTypeSql = "select fid,fname_"+this.getLoc(ctx)+" from T_SHE_RoomModelType";
    	String roomModelSql = "select fid,fname_"+this.getLoc(ctx)+" from T_SHE_RoomModel ";
    	if(buildInfo!=null)   roomModelSql += " where FBuildingID = '"+buildInfo.getId().toString()+"'";//	
    	
    	String productDetailSql = "select fid,fname_"+this.getLoc(ctx)+" from T_SHE_ProductDetial";
    	String productTypeSql = "select fid,fname_"+this.getLoc(ctx)+" from T_FDC_ProductType";
    	String buildingPropertySql = "select * from T_SHE_BuildingProperty BuildingProperty";
    	String buildingPropertyFillter = "";
/*    	if(buildingPropertyInfo!=null) {
    		buildingPropertySql += " where BuildingProperty.flongNumber like '"+buildingPropertyInfo.getLongNumber()+"%'";
    		buildingPropertyFillter += " and BuildingProperty.flongNumber like '"+buildingPropertyInfo.getLongNumber()+"%'";
    	}*/
    	
    	String roomFormSql = "select fid,fname_"+this.getLoc(ctx)+" from T_SHE_RoomForm";
    	String sightSql = "select fid,fname_"+this.getLoc(ctx)+" from T_SHE_SightRequirement";
    	String directionSql = "select fid,fname_"+this.getLoc(ctx)+" from T_SHE_HopedDirection";
    	//客户的查询sql   //默认无维度
//    	String customerSql = "select '客户',FCustomerType,FCustomerGradeID,FCustomerOriginID,FCreatorID,FSalesmanID,"
//    		+"FTradeTime,FProvinceID,FHabitationAreaID,FSex,FFamillyEarningID,FReceptionTypeID,FEnterpriceSize,FIndustryID from T_SHE_FDCCustomer";
    	String customerGradeSql = "select fid,fname_"+this.getLoc(ctx)+" from T_SHE_CustomerGrade";
    	String customerOriginSql = "select fid,fname_"+this.getLoc(ctx)+" from T_SHE_CustomerOrigin";
    	String creatorSql = "select fid,fname_"+this.getLoc(ctx)+" from T_PM_User";
    	//String saleManSql = "select fid,fname_"+this.getLoc(ctx)+",fnumber from T_PM_User";
    	String provinceSql = "select fid,fname_"+this.getLoc(ctx)+" from T_BD_Province";
    	String habitationAreaFilter = "";
    	String habitationAreaSql = "select * from T_SHE_HabitationArea HabitationArea";	//
    	if(habitationAreaInfo!=null)	{
    		habitationAreaSql += " where HabitationArea.flongNumber like '"+habitationAreaInfo.getLongNumber()+"%'";
    		habitationAreaFilter += " and HabitationArea.flongNumber like '"+habitationAreaInfo.getLongNumber()+"%'";
    	}
    	
    	String famillyEarningSql = "select fid,fname_"+this.getLoc(ctx)+" from T_SHE_FamillyEarning";
    	String receptionTypeSql = "select fid,fname_"+this.getLoc(ctx)+" from T_SHE_ReceptionType";
    	String industrySql = "select fid,fname_"+this.getLoc(ctx)+" from T_BD_Industry";    	
    	
    	String cubeTableSql = "select  1 CountNum, Pruchase.fid , Room.FBuildingID,Room.FFloor,Room.FHouseProperty, RoomModelType.fid roomModelTypeID," +
		"Room.FRoomModelID,Room.FProductDetailID,Room.FProductTypeID,Room.FBuildingPropertyID,Room.FRoomFormID,Room.FSightID,Room.FDirectionID," +
		" Customer.FCustomerType,Customer.FCustomerGradeID,Customer.FCustomerOriginID,CustOriType.fid custOriTypeID,Customer.FCreatorID,Customer.FSalesmanID,"+
    	"Customer.FTradeTime,Customer.FProvinceID,Customer.FHabitationAreaID,Customer.FSex,Customer.FFamillyEarningID,Customer.FReceptionTypeID,Customer.FEnterpriceSize,Customer.FIndustryID " +
		"from T_SHE_PurchaseCustomerInfo PurCust " +
		"inner join T_SHE_Purchase Pruchase on PurCust.FHeadID = Pruchase.fid " +    //一定要存在认购单
		"left outer join T_SHE_FDCCustomer Customer on PurCust.FCustomerID = Customer.fid " +
		"left outer join T_SHE_Room Room on Pruchase.FRoomID = Room.fid  " +  
		"left outer join T_SHE_CustomerOrigin CustomerOrigin on Customer.FCustomerOriginID = CustomerOrigin.fid  " +	//增加客户来源的关联
		"left outer join T_SHE_CustomerOriginGroup CustOriType on CustomerOrigin.FGroupID = CustOriType.fid  " +	//增加客户来源分类的关联
		"left outer join T_SHE_RoomModel RoomModel on Room.FRoomModelID = RoomModel.fid " +
		"left outer join T_SHE_RoomModelType RoomModelType on RoomModel.FRoomModelTypeID = RoomModelType.fid " +
		"left outer join T_SHE_Building Building on Room.FBuildingID = Building.fid " +
		"left outer join T_SHE_BuildingProperty BuildingProperty on Room.FBuildingPropertyID = BuildingProperty.fid " +
		"left outer join T_SHE_HabitationArea HabitationArea on Customer.FHabitationAreaID = HabitationArea.fid " +
		"left outer join T_SHE_SellProject SellProject on Building.FSellProjectID = SellProject.fid " +
		"where Room.FSellState in ('"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.SIGN_VALUE+"')" +  //对应的房间必须是认购或签约的状态
    	" and Pruchase.FState = '4AUDITTED' ";
    	if(checkOnlyMainCustomer) cubeTableSql += " and PurCust.FSeq = 0 ";   //只显示主业务客户的
    	if(!roomFilterString.equals(""))  cubeTableSql += roomFilterString;
    	if(!customerFilterString.equals(""))  cubeTableSql += customerFilterString;  
    	if(!buildingPropertyFillter.equals("")) cubeTableSql += buildingPropertyFillter;
    	if(!habitationAreaFilter.equals(""))	cubeTableSql += habitationAreaFilter;
   	
    	SchemaSource scCo = new SchemaSource();
    		scCo.setDataItem("T_SHE_Building", buildingSql, null);
    		scCo.setDataItem("T_SHE_RoomModelType", roomModelTypeSql, null);
    		scCo.setDataItem("T_SHE_RoomModel", roomModelSql, null);
    		scCo.setDataItem("T_SHE_ProductDetial", productDetailSql, null);
    		scCo.setDataItem("T_FDC_ProductType", productTypeSql, null);
    		scCo.setDataItem("T_SHE_BuildingProperty", buildingPropertySql, null);
    		scCo.setDataItem("T_SHE_RoomForm", roomFormSql, null);
    		scCo.setDataItem("T_SHE_SightRequirement", sightSql, null);
    		scCo.setDataItem("T_SHE_HopedDirection", directionSql, null);
    		
    		scCo.setDataItem("T_SHE_CustomerGrade", customerGradeSql, null);
    		scCo.setDataItem("T_SHE_CustomerOrigin", customerOriginSql, null);
    		scCo.setDataItem("T_PM_User", creatorSql, null);
    		scCo.setDataItem("T_BD_Province", provinceSql, null);    		
    		scCo.setDataItem("T_SHE_HabitationArea", habitationAreaSql, null);
    		scCo.setDataItem("T_SHE_FamillyEarning", famillyEarningSql, null);
    		scCo.setDataItem("T_SHE_ReceptionType", receptionTypeSql, null);
    		scCo.setDataItem("T_BD_Industry", industrySql, null); 
    		
    		scCo.setDataItem("T_Temp_Floor", "select distinct(FFloor) FFloor from T_SHE_Room Room where 1=1 "+onlyRoomFilterString, null);
    		scCo.setDataItem("T_Temp_HouseProperty", convertHousePropertyEnumToTableSql(), null);
    		scCo.setDataItem("T_Temp_CustomerType", convertCustomerTypeEnumToTableSql(), sqlParams);
    		scCo.setDataItem("T_Temp_TradeTime", "select distinct(case when FTradeTime is null then 0 else FTradeTime end) FTradeTime from T_SHE_FDCCustomer Customer where 1=1 "+customerFilterString, sqlParams);
    		scCo.setDataItem("T_Temp_Sex", convertSexEnumToTableSql(), sqlParams);
    		scCo.setDataItem("T_Temp_EnterpriceSize", "select distinct(FEnterpriceSize) FEnterpriceSize from T_SHE_FDCCustomer Customer where FEnterpriceSize is not null "+customerFilterString, sqlParams);
    		
    		scCo.setDataItem("T_CubeTable", cubeTableSql, sqlParams); 
    	
    	StringBuffer mdx = new StringBuffer(); //房间行 ， 客户列
    		boolean hasTotalCount = true;
    		if(roomRowsString.equals("")) {
				mdx.append("with member [Measures].[RoomNoDimsion] as 'Measures.members.count',caption='无维度'");
				roomRowsString = "[Measures].[RoomNoDimsion]";
				hasTotalCount = false;
    		}
    		if(!customerColumnsString.equals("")) {
    			customerColumnsString = "[Measures].[PurchaseCount] * " + customerColumnsString;
    		}else{
    			customerColumnsString = "[Measures].[PurchaseCount]";
    		}    		
    		
    		//行若无维度，则无合计行
    		String totalString = "";
    		if(hasTotalCount) {
	    		if(mdx.indexOf("with")<0) mdx.append("with ");
	    		totalString = roomRowsString.substring(0,roomRowsString.indexOf(".")) + ".[TotalCount]";;
	    		mdx.append(" member "+ totalString +" as 'sum("+roomRowsString+")',caption='合计'");
	    		totalString = "," + totalString;
    		}
    		mdx.append("select {"+customerColumnsString+"} on columns,{"+ roomRowsString +totalString+"} on rows from CubeTable");
    	
    		scCo.setCaller(this.getClass());
    		scCo.setFilename("BarginOnAnalyseBI.xml");
    		scCo.setMdx(mdx.toString());
		
    	return scCo;
    }



    private String convertSexEnumToTableSql() {
    	String sqlTable = "";
		List enumlist = SexEnum.getEnumList();
		for(int j=0;j<enumlist.size();j++) {
			if(j!=0) sqlTable += " union "; 
			SexEnum enumT = (SexEnum)enumlist.get(j);
			sqlTable += "select '"+enumT.getValue()+ "' fid,'"+enumT.getAlias() + "' fname";
		}
		return sqlTable;
    }

    private String convertHousePropertyEnumToTableSql() {
    	String sqlTable = "";
		List enumlist = HousePropertyEnum.getEnumList();
		for(int j=0;j<enumlist.size();j++) {
			if(j!=0) sqlTable += " union "; 
			HousePropertyEnum enumT = (HousePropertyEnum)enumlist.get(j);
			sqlTable += "select '"+enumT.getValue()+ "' fid,'"+enumT.getAlias() + "' fname";
		}
		return sqlTable;
    }

    private String convertCustomerTypeEnumToTableSql() {
    	String sqlTable = "";
		List enumlist = CustomerTypeEnum.getEnumList();
		for(int j=0;j<enumlist.size();j++) {
			if(j!=0) sqlTable += " union "; 
			CustomerTypeEnum enumT = (CustomerTypeEnum)enumlist.get(j);
			sqlTable += "select '"+enumT.getValue()+ "' fid,'"+enumT.getAlias() + "' fname";
		}
		return sqlTable;
    }



	protected String _getPurchaseQuerySql(Context ctx) throws BOSException {
		// TODO Auto-generated method stub
		return null;
	}







}