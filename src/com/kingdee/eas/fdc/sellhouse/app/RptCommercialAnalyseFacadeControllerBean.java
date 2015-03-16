package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
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

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.CommerceIntentionEnum;
import com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SexEnum;
import com.kingdee.eas.framework.bireport.app.BireportBaseFacadeControllerBean;
import com.kingdee.eas.framework.bireport.util.SchemaSource;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.SqlParams;

public class RptCommercialAnalyseFacadeControllerBean extends AbstractRptCommercialAnalyseFacadeControllerBean
{
	private static final long serialVersionUID = 1412547200670395681L;
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.RptCommercialAnalyseFacadeControllerBean");
	
	protected SchemaSource readySchemaSource(RptParams params, Context ctx) throws BOSException, EASBizException
	{
 		SchemaSource ss = new SchemaSource();

		boolean isLevel = ((Boolean) params.getObjectElement("level.value")).booleanValue();
		
		boolean isState = ((Boolean) params.getObjectElement("state.value")).booleanValue();
		
		boolean isBuildingPropery = ((Boolean) params.getObjectElement("buildingPropery.value")).booleanValue();
		
		boolean isProductType = ((Boolean) params.getObjectElement("productType.value")).booleanValue();
		
		boolean isDirection = ((Boolean) params.getObjectElement("direction.value")).booleanValue();
		
		boolean isAreaRequired = ((Boolean) params.getObjectElement("areaRequired.value")).booleanValue();
		
		boolean isViewRequired = ((Boolean) params.getObjectElement("viewRequired.value")).booleanValue();
		
		boolean isRoomType= ((Boolean) params.getObjectElement("roomType.value")).booleanValue();
		
		boolean isProductDetail = ((Boolean) params.getObjectElement("productDetail.value")).booleanValue();
		
		boolean isPrice = ((Boolean) params.getObjectElement("price.value")).booleanValue();
		
		boolean isTotalPrice = ((Boolean) params.getObjectElement("totalPrice.value")).booleanValue();
		
		boolean isReason = ((Boolean) params.getObjectElement("reason.value")).booleanValue();
		
		boolean isIntension = ((Boolean) params.getObjectElement("intension.value")).booleanValue();
		
		boolean isForm = ((Boolean) params.getObjectElement("form.value")).booleanValue();
		
		boolean isFloor = ((Boolean) params.getObjectElement("floor.value")).booleanValue();
		
		boolean isProportion = ((Boolean) params.getObjectElement("proportion.value")).booleanValue();
		
		boolean isAim = ((Boolean) params.getObjectElement("aim.value")).booleanValue();
		
	    
	    String loc = this.getLoc(ctx);
		String configFile = null;
		StringBuffer factSql = new StringBuffer();
		StringBuffer itemSql = new StringBuffer();
		
	
		
		SqlParams sqlParams = new SqlParams();
		StringBuffer mdx = new StringBuffer();
		
		

		//商机级别
		if (isLevel)
		{
    		configFile = "commercial_level.xml";
    		factSql.append(" select c.FCommerceLevelID as FLevelID,1 as FCounter ");
    		factSql.append(" from T_SHE_CommerceChance as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer levelSql = new StringBuffer();
    		levelSql.append(" select c.FID, c.FName_"+loc+" as FName ");
    		levelSql.append(" from T_SHE_CommerceLevel as c ");
    		
    		ss.setDataItem("Level",levelSql.toString(),null);
    		
    		mdx.append(" with member [Level].[Totals] as 'sum([Level].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[Level].members,[Level].[Totals]}on rows from Fact");
    	}
		//商机状态
		if(isState)
		{
    		configFile = "commercial_state.xml";
    		factSql.append(" select c.FCommerceStatus,1 as FCounter  ");
    		factSql.append(" from T_SHE_CommerceChance as c");
    		
//    		加过滤条件
    		factSql.append(itemSql);
 
			ss.setDataItem("Fact", factSql.toString(), sqlParams);			
			StringBuffer sexSql = new StringBuffer();
			List list = CommerceStatusEnum.getEnumList();
			for(int i = 0;i < list.size();i ++){
				CommerceStatusEnum stateEnum = (CommerceStatusEnum) list.get(i);
				sexSql.append(" select '");
				sexSql.append(stateEnum.getValue());
				sexSql.append("' as FID,'");
				sexSql.append(stateEnum.getAlias());
				sexSql.append("' as FCommerceStatus ");
				sexSql.append(" union all");
			}
			sexSql.delete(sexSql.lastIndexOf("u") - 1, sexSql.lastIndexOf("l") + 1);
			ss.setDataItem("State",sexSql.toString(),null);
			
			mdx.append(" with member [State].[Totals] as 'sum([State].members)',caption='合计' ");
			mdx.append(" select {[Measures].Members} on columns,{[State].members,[State].[Totals]}on rows from Fact");
    	
		}
		//意向建筑性质
		else if(isBuildingPropery)
		{
    		configFile = "commercial_buildingpropery.xml";
    		factSql.append(" select c.FBuildingPropertyID,1 as FCounter ");
    		factSql.append(" from T_SHE_CommerceChance as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer sql = new StringBuffer();
    		sql.append(" select b.FID, b.FName_"+loc+" as FName ");
    		sql.append(" from T_SHE_BuildingProperty as b ");
    		
    		ss.setDataItem("BuildingPropery",sql.toString(),null);
    		
    		mdx.append(" with member [BuildingPropery].[Totals] as 'sum([BuildingPropery].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[BuildingPropery].members,[BuildingPropery].[Totals]}on rows from Fact");
    	
		}
		//意向产品类型
		else if(isProductType)
		{
    		configFile = "commercial_producttype.xml";
    		factSql.append(" select c.FProductTypeID,1 as FCounter ");
    		factSql.append(" from T_SHE_CommerceChance as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer sql = new StringBuffer();
    		sql.append(" select p.FID, p.FName_"+loc+" as FName ");
    		sql.append(" from T_FDC_ProductType as p ");
    		
    		ss.setDataItem("ProductType",sql.toString(),null);
    		
    		mdx.append(" with member [ProductType].[Totals] as 'sum([ProductType].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[ProductType].members,[ProductType].[Totals]}on rows from Fact");
		}
		//意向朝向
		else if(isDirection)
		{
    		configFile = "commercial_direction.xml";
    		factSql.append(" select c.FDirectionID,1 as FCounter ");
    		factSql.append(" from T_SHE_CommerceChance as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer sql = new StringBuffer();
    		sql.append(" select h.FID, h.FName_"+loc+" as FName ");
    		sql.append(" from T_SHE_HopedDirection as h ");
    		
    		ss.setDataItem("Direction",sql.toString(),null);
    		
    		mdx.append(" with member [Direction].[Totals] as 'sum([Direction].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[Direction].members,[Direction].[Totals]}on rows from Fact");
		}
		//面积需求
		else if(isAreaRequired)
		{

    		configFile = "commercial_arearequired.xml";
    		factSql.append(" select c.FAreaRequirementID,1 as FCounter ");
    		factSql.append(" from T_SHE_CommerceChance as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer sql = new StringBuffer();
    		sql.append(" select a.FID, a.FName_"+loc+" as FName ");
    		sql.append(" from T_SHE_AreaRequirement as a ");
    		
    		ss.setDataItem("AreaRequired",sql.toString(),null);
    		
    		mdx.append(" with member [AreaRequired].[Totals] as 'sum([AreaRequired].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[AreaRequired].members,[AreaRequired].[Totals]}on rows from Fact");
		
		}
		//景观需求
		else if(isViewRequired)
		{
    		configFile = "commercial_viewrequired.xml";
    		factSql.append(" select c.FSightRequirementID,1 as FCounter ");
    		factSql.append(" from T_SHE_CommerceChance as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer sql = new StringBuffer();
    		sql.append(" select s.FID, s.FName_"+loc+" as FName ");
    		sql.append(" from T_SHE_SightRequirement as s ");
    		
    		ss.setDataItem("ViewRequired",sql.toString(),null);
    		
    		mdx.append(" with member [ViewRequired].[Totals] as 'sum([ViewRequired].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[ViewRequired].members,[ViewRequired].[Totals]}on rows from Fact");
		}
		//户型需求
		else if(isRoomType)
		{
    		configFile = "commercial_roommodel.xml";
    		factSql.append(" select c.FRoomModelTypeID,1 as FCounter ");
    		factSql.append(" from T_SHE_CommerceChance as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer sql = new StringBuffer();
    		sql.append(" select r.FID, r.FName_"+loc+" as FName ");
    		sql.append(" from T_SHE_RoomModelType as r ");
    		
    		ss.setDataItem("RoomModel",sql.toString(),null);
    		
    		mdx.append(" with member [RoomModel].[Totals] as 'sum([RoomModel].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[RoomModel].members,[RoomModel].[Totals]}on rows from Fact");
		}
		//产品描述
		else if(isProductDetail)
		{
    		configFile = "commercial_productdetail.xml";
    		factSql.append(" select c.FProductDetailID,1 as FCounter ");
    		factSql.append(" from T_SHE_CommerceChance as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer sql = new StringBuffer();
    		sql.append(" select p.FID, p.FName_"+loc+" as FName ");
    		sql.append(" from T_SHE_ProductDetial as p ");
    		
    		ss.setDataItem("ProductDetail",sql.toString(),null);
    		
    		mdx.append(" with member [ProductDetail].[Totals] as 'sum([ProductDetail].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[ProductDetail].members,[ProductDetail].[Totals]}on rows from Fact");
		}
		
//		意向单价
		else if(isPrice)
		{
    		configFile = "commercial_unitprice.xml";
    		factSql.append(" select c.FHopedUnitPriceID,1 as FCounter ");
    		factSql.append(" from T_SHE_CommerceChance as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer sql = new StringBuffer();
    		sql.append(" select h.FID, h.FName_"+loc+" as FName ");
    		sql.append(" from T_SHE_HopedUnitPrice as h ");
    		
    		ss.setDataItem("UnitPrice",sql.toString(),null);
    		
    		mdx.append(" with member [UnitPrice].[Totals] as 'sum([UnitPrice].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[UnitPrice].members,[UnitPrice].[Totals]}on rows from Fact");
		}
//		意向总价
		else if(isTotalPrice)
		{
    		configFile = "commercial_totalprice.xml";
    		factSql.append(" select c.FHopedTotalPricesID,1 as FCounter ");
    		factSql.append(" from T_SHE_CommerceChance as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer sql = new StringBuffer();
    		sql.append(" select h.FID, h.FName_"+loc+" as FName ");
    		sql.append(" from T_SHE_HopedTotalPrices as h ");
    		
    		ss.setDataItem("TotalPrice",sql.toString(),null);
    		
    		mdx.append(" with member [TotalPrice].[Totals] as 'sum([TotalPrice].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[TotalPrice].members,[TotalPrice].[Totals]}on rows from Fact");
		}
//		商机原因
		else if(isReason)
		{
    		configFile = "commercial_reason.xml";
    		factSql.append(" select c.FBuyHouseReasonID,1 as FCounter ");
    		factSql.append(" from T_SHE_CommerceChance as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer sql = new StringBuffer();
    		sql.append(" select b.FID, b.FName_"+loc+" as FName ");
    		sql.append(" from T_SHE_BuyHouseReason as b ");
    		
    		ss.setDataItem("Reason",sql.toString(),null);
    		
    		mdx.append(" with member [Reason].[Totals] as 'sum([Reason].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[Reason].members,[Reason].[Totals]}on rows from Fact");
		}
		//意向强度
		else if(isIntension)
		{

    		configFile = "commercial_pitch.xml";
    		factSql.append(" select c.FHopedPitchID,1 as FCounter ");
    		factSql.append(" from T_SHE_CommerceChance as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer sql = new StringBuffer();
    		sql.append(" select h.FID, h.FName_"+loc+" as FName ");
    		sql.append(" from T_SHE_HopePitch as h ");
    		
    		ss.setDataItem("Pitch",sql.toString(),null);
    		
    		mdx.append(" with member [Pitch].[Totals] as 'sum([Pitch].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[Pitch].members,[Pitch].[Totals]}on rows from Fact");
		
		}
		//房屋形式
		else if(isForm)
		{
    		configFile = "commercial_form.xml";
    		factSql.append(" select c.FRoomFormID,1 as FCounter ");
    		factSql.append(" from T_SHE_CommerceChance as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer sql = new StringBuffer();
    		sql.append(" select r.FID, r.FName_"+loc+" as FName ");
    		sql.append(" from T_SHE_RoomForm as r ");
    		
    		ss.setDataItem("Form",sql.toString(),null);
    		
    		mdx.append(" with member [Form].[Totals] as 'sum([Form].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[Form].members,[Form].[Totals]}on rows from Fact");
		
		}
		//意向楼层
		else if(isFloor)
		{
    		configFile = "commercial_floor.xml";
    		factSql.append(" select c.FHopedFloorID,1 as FCounter ");
    		factSql.append(" from T_SHE_CommerceChance as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer sql = new StringBuffer();
    		sql.append(" select h.FID, h.FName_"+loc+" as FName ");
    		sql.append(" from T_SHE_HopedFloor as h ");
    		
    		ss.setDataItem("Floor",sql.toString(),null);
    		
    		mdx.append(" with member [Floor].[Totals] as 'sum([Floor].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[Floor].members,[Floor].[Totals]}on rows from Fact");
		
		}
//		首付比例
		else if(isProportion)
		{
    		configFile = "commercial_proportion.xml";
    		factSql.append(" select c.FFirstPayProportionID,1 as FCounter ");
    		factSql.append(" from T_SHE_CommerceChance as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer sql = new StringBuffer();
    		sql.append(" select f.FID, f.FName_"+loc+" as FName ");
    		sql.append(" from T_SHE_FirstPayProportion as f ");
    		
    		ss.setDataItem("Proportion",sql.toString(),null);
    		
    		mdx.append(" with member [Proportion].[Totals] as 'sum([Proportion].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[Proportion].members,[Proportion].[Totals]}on rows from Fact");
		}
//		置业目的
		if(isAim)
		{
    		configFile = "commercial_aim.xml";
    		factSql.append(" select c.FCommerceIntention,1 as FCounter  ");
    		factSql.append(" from T_SHE_CommerceChance as c");
    		
//    		加过滤条件
    		factSql.append(itemSql);
 
			ss.setDataItem("Fact", factSql.toString(), sqlParams);			
			StringBuffer sexSql = new StringBuffer();
			List list = CommerceIntentionEnum.getEnumList();
			for(int i = 0;i < list.size();i ++){
				CommerceIntentionEnum stateEnum = (CommerceIntentionEnum) list.get(i);
				sexSql.append(" select '");
				sexSql.append(stateEnum.getValue());
				sexSql.append("' as FID,'");
				sexSql.append(stateEnum.getAlias());
				sexSql.append("' as FCommerceIntention ");
				sexSql.append(" union all");
			}
			sexSql.delete(sexSql.lastIndexOf("u") - 1, sexSql.lastIndexOf("l") + 1);
			ss.setDataItem("Aim",sexSql.toString(),null);
			
			mdx.append(" with member [Aim].[Totals] as 'sum([Aim].members)',caption='合计' ");
			mdx.append(" select {[Measures].Members} on columns,{[Aim].members,[Aim].[Totals]}on rows from Fact");
    	
		}
		
		
		ss.setCaller(this.getClass());
		ss.setFilename(configFile);
		ss.setMdx(mdx.toString());

		return ss;
	}
}