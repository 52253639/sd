package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.IndustryInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SexEnum;
import com.kingdee.eas.fdc.sellhouse.TrackPhaseEnum;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.eas.fdc.tenancy.app.MarketingUnitControllerBean;
import com.kingdee.eas.framework.bireport.app.BireportBaseFacadeControllerBean;
import com.kingdee.eas.framework.bireport.util.SchemaSource;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.SqlParams;

public class RptCustomerFacadeControllerBean extends AbstractRptCustomerFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.RptCustomerFacadeControllerBean");
    
    protected SchemaSource readySchemaSource(RptParams params, Context ctx) throws BOSException, EASBizException
    {
    	
     	SchemaSource ss = new SchemaSource();
    	
    	boolean isCustomerOrigin = ((Boolean)params.getObjectElement("jrbCustomerOrigin.value")).booleanValue();
    	boolean isReceptionType = ((Boolean)params.getObjectElement("jrbReceptionType.value")).booleanValue();
    	boolean isCustomerGrade = ((Boolean)params.getObjectElement("jrbCustomerGrade.value")).booleanValue();
    	boolean isFamillyEarning = ((Boolean)params.getObjectElement("jrbFamillyEarning.value")).booleanValue();
    	boolean isHabitationArea = ((Boolean)params.getObjectElement("jrbHabitationArea.value")).booleanValue();
    	boolean isTradeTime = ((Boolean)params.getObjectElement("jrbTradeTime.value")).booleanValue();
    	boolean isOwner = ((Boolean)params.getObjectElement("jrbOwner.value")).booleanValue();
    	boolean isBooker = ((Boolean)params.getObjectElement("jrbBooker.value")).booleanValue();
    	boolean isSex = ((Boolean)params.getObjectElement("jrbSex.value")).booleanValue();
    	boolean isProvince = ((Boolean)params.getObjectElement("jrbProvince.value")).booleanValue();
    	
    	boolean isIndustry = ((Boolean)params.getObjectElement("rbIndustry.value")).booleanValue();
    	
    	boolean isEnterpriseSize = ((Boolean)params.getObjectElement("rbEnterpriceSize.value")).booleanValue();
    	
    	
		Date bookedDateFrom = null;
		Date bookedDateTo = null;

		if(params.getObjectElement("bookedDateFrom.value")!=null)
		{
			bookedDateFrom = (Date)params.getObjectElement("bookedDateFrom.value");
		}
		if(params.getObjectElement("bookedDateTo.value")!=null)
		{
			bookedDateTo = (Date)params.getObjectElement("bookedDateTo.value");
		}
    	
		//交易次数
		Long tradeTime =  params.getObjectElement("tradeTime.value")==null||params.getObjectElement("tradeTime.value").toString().trim().length()<1? null:this.tempConvertToLong(params.getObjectElement("tradeTime.value").toString());
		
		
		//取得用户过滤数据
		Set personIds = (Set) params.getObject("personIds");
		//当前用户
		UserInfo currentUserInfo =(UserInfo) params.getObject("currentUserInfo");
		
		
		SexEnum sexE = null;
		//性别
		if(!(params.getObjectElement("comboxSex.value") instanceof String))
		{
		   sexE =(SexEnum)params.getObjectElement("comboxSex.value");
		}
		
		//行业
		IndustryInfo industryInfo = (IndustryInfo) params.getObjectElement("f7Industry.value");
		
    	String loc = this.getLoc(ctx);
 		String configFile = null;
 		StringBuffer factSql = new StringBuffer();
 		StringBuffer itemSql = new StringBuffer();
 		
 		SqlParams sqlParams = new SqlParams();
 		StringBuffer mdx = new StringBuffer();
 		
 		itemSql.append(" where 1=1 ");
 		
 		if(bookedDateFrom!=null)
 		{
 			//itemSql.append(" and to_date(to_char(c.FBookedDate,'yyyy-mm-dd'))>=? ");
 			itemSql.append(" and c.FCreateTime >=? ");
 			sqlParams.addDate(FDCDateHelper.getSQLBegin(bookedDateFrom));
 		}
 		if(bookedDateTo!=null)
 		{
 			//itemSql.append(" and to_date(to_char(c.FBookedDate,'yyyy-mm-dd'))<=? ");
 			itemSql.append(" and c.FCreateTime <=? ");
 			sqlParams.addDate(FDCDateHelper.getSQLEnd(bookedDateTo));
 		}

 		/*---------------------------end------------------------------*/

 		//判断交易次数
 		if(tradeTime != null)
 		{
 			itemSql.append(" and c.FTradeTime=? ");
 			sqlParams.addBigDecimal(new BigDecimal(tradeTime.longValue()));
 		}
 	
 		//判断性别
 		if(!(params.getObjectElement("comboxSex.value") instanceof String))
 		{
 			itemSql.append(" and c.FSex=? ");
 			sqlParams.addString(sexE.getValue());
 		}
 		//判断行业
 		if(industryInfo != null)
 		{
 			itemSql.append(" and c.FIndustryID = ? ");
 			sqlParams.addString(industryInfo.getId().toString());
 		}
 		

 		String permitIdSql = MarketingUnitFactory.getLocalInstance(ctx).getPermitFdcCustomerIdSql(currentUserInfo,false);
 		itemSql.append(" and c.fid in ("+permitIdSql+") ");
 		
 		
    	//客户来源
    	if(isCustomerOrigin)
    	{
    		configFile = "customer_origin.xml";
    		factSql.append(" select c.FCustomerOriginID,1 as FCounter ");
    		factSql.append(" from T_SHE_FDCCustomer as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer customerOriginSql = new StringBuffer();
    		customerOriginSql.append(" select co.FID, co.FName_"+loc+" as FName ");
    		customerOriginSql.append(" from T_SHE_CustomerOrigin as co ");
    		ss.setDataItem("CustomerOrigin",customerOriginSql.toString(),null);
    		//mdx.append(" select {[Measures].Members} on columns,{[CustomerOrigin].members}on rows from Fact");
    		
    		mdx.append(" with member [CustomerOrigin].[Totals] as 'sum([CustomerOrigin].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[CustomerOrigin].members,[CustomerOrigin].[Totals]}on rows from Fact");
    	}
    	//接待方式
    	else if(isReceptionType)
    	{
    		configFile = "customer_receptiontype.xml";
    		factSql.append(" select c.FReceptionTypeID,1 as FCounter ");
    		factSql.append(" from T_SHE_FDCCustomer as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer receptionTypeSql = new StringBuffer();
    		receptionTypeSql.append(" select rt.FID, rt.FName_"+loc+" as FName ");
    		receptionTypeSql.append(" from T_SHE_ReceptionType as rt ");
    		
    		ss.setDataItem("ReceptionType",receptionTypeSql.toString(),null);
    		
    		//mdx.append(" select {[Measures].Members} on columns,{[ReceptionType].members}on rows from Fact");
    		mdx.append(" with member [ReceptionType].[Totals] as 'sum([ReceptionType].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[ReceptionType].members,[ReceptionType].[Totals]}on rows from Fact");
    	}
    	//客户分级
    	else if(isCustomerGrade)
    	{
    		configFile = "customer_grade.xml";
    		factSql.append(" select c.FCustomerGradeID,1 as FCounter ");
    		factSql.append(" from T_SHE_FDCCustomer as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer customerGradeSql = new StringBuffer();
    		customerGradeSql.append(" select cg.FID, cg.FName_"+loc+" as FName ");
    		customerGradeSql.append(" from T_SHE_CustomerGrade as cg ");
    		
    		ss.setDataItem("CustomerGrade",customerGradeSql.toString(),null);
    		
    		//mdx.append(" select {[Measures].Members} on columns,{[CustomerGrade].members}on rows from Fact");
    	    mdx.append(" with member [CustomerGrade].[Totals] as 'sum([CustomerGrade].members)',caption='合计' ");
    	    mdx.append(" select {[Measures].Members} on columns,{[CustomerGrade].members,[CustomerGrade].[Totals]}on rows from Fact");
    	}
    	//家庭收入
    	else if(isFamillyEarning)
    	{
    		configFile = "customer_famillyearning.xml";
    		factSql.append(" select c.FFamillyEarningID,1 as FCounter ");
    		factSql.append(" from T_SHE_FDCCustomer as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer famillyEarningSql = new StringBuffer();
    		famillyEarningSql.append(" select fe.FID, fe.FName_"+loc+" as FName ");
    		famillyEarningSql.append(" from T_SHE_FamillyEarning as fe ");
    		
    		ss.setDataItem("FamillyEarning",famillyEarningSql.toString(),null);
    		
    		mdx.append(" with member [FamillyEarning].[Totals] as 'sum([FamillyEarning].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[FamillyEarning].members,[FamillyEarning].[Totals]}on rows from Fact");
    	}
    	//居住区域
    	else if(isHabitationArea)
    	{
    		configFile = "customer_habitationarea.xml";
    		factSql.append(" select c.FHabitationAreaID,1 as FCounter ");
    		factSql.append(" from T_SHE_FDCCustomer as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer habitationAreaSql = new StringBuffer();
    		habitationAreaSql.append(" select fe.FID, fe.FName_"+loc+" as FName ");
    		habitationAreaSql.append(" from T_SHE_HabitationArea as fe ");
    		
    		ss.setDataItem("HabitationArea",habitationAreaSql.toString(),null);
    		
    		mdx.append(" with member [HabitationArea].[Totals] as 'sum([HabitationArea].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[HabitationArea].members,[HabitationArea].[Totals]}on rows from Fact");
    	}
    	//交易次数???????
    	else if(isTradeTime)
    	{
    		configFile = "customer_tradetime.xml";
    		factSql.append(" select (case when c.FTradeTime is null then 0 else c.FTradeTime end) as FTradeTimeID,1 as FCounter ");
    		//factSql.append(" select c.FTradeTime as FTradeTimeID,1 as FCounter ");
    		factSql.append(" from T_SHE_FDCCustomer as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer tradeTimeSql = new StringBuffer();
    		tradeTimeSql.append(" select distinct (case when c.FTradeTime is null then 0 else c.FTradeTime end) as FID,(case when c.FTradeTime is null then 0 else c.FTradeTime end) as FTradeTime ");
    		//tradeTimeSql.append(" select distinct c.FTradeTime as FID, c.FTradeTime as FTradeTime ");
    		tradeTimeSql.append(" from T_SHE_FDCCustomer as c ");
    		
    		ss.setDataItem("TradeTime",tradeTimeSql.toString(),null);
    		
    		mdx.append(" with member [TradeTime].[Totals] as 'sum([TradeTime].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[TradeTime].members,[TradeTime].[Totals]}on rows from Fact");
    	}
    	//所有者
    	else if(isOwner)
    	{
    		configFile = "customer_owner.xml";
    		//factSql.append(" select (case when c.FOwner is null then N' ' else c.FOwner end) as FOwnerID,1 as FCounter ");
    		factSql.append(" select  c.FSalesmanID as FOwnerID,1 as FCounter ");
    		factSql.append(" from T_SHE_FDCCustomer as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer ownerSql = new StringBuffer();
    		//ownerSql.append(" select distinct (case when c.FOwner is null then N' ' else c.FOwner end) as FID,(case when c.FOwner is null then N' ' else c.FOwner end) as FOwner ");
    		
    		
    		
    		ownerSql.append(" select  u.FID, u.FName_"+loc+" as FOwner ");
    		ownerSql.append(" from T_PM_User as u ");
    		//过滤掉系统用户，已删除，代理用户
    		ownerSql.append(" WHERE u.FType <> 10 AND u.FIsDelete = 0 AND u.FAgentUser = 0");
    		
    		//ownerSql.append(" from T_SHE_FDCCustomer as c ");
    		
    		ss.setDataItem("Owner",ownerSql.toString(),null);
    		
    		mdx.append(" with member [Owner].[Totals] as 'sum([Owner].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[Owner].members,[Owner].[Totals]}on rows from Fact");
    	}
    	//登记人
    	else if(isBooker)
    	{
    		configFile = "customer_booker.xml";
    		factSql.append(" select (case when c.FBooker is null then N' ' else c.FBooker end) as FBookerID,1 as FCounter ");
    		factSql.append(" from T_SHE_FDCCustomer as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer bookerSql = new StringBuffer();
    		bookerSql.append(" select distinct (case when c.FBooker is null then N' ' else c.FBooker end) as FID,(case when c.FBooker is null then N' ' else c.FBooker end) as FBooker ");
    		bookerSql.append(" from T_SHE_FDCCustomer as c ");
    		
    		ss.setDataItem("Booker",bookerSql.toString(),null);
    		
    		mdx.append(" with member [Booker].[Totals] as 'sum([Booker].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[Booker].members,[Booker].[Totals]}on rows from Fact");
    	}
    	//性别
    	else if(isSex)
    	{
    		configFile = "customer_sex.xml";
    		factSql.append(" select c.FSex as FSexID,1 as FCounter  ");
    		factSql.append(" from T_SHE_FDCCustomer as c");
    		
//    		加过滤条件
    		factSql.append(itemSql);
 
			ss.setDataItem("Fact", factSql.toString(), sqlParams);			
			StringBuffer sexSql = new StringBuffer();
			List list = SexEnum.getEnumList();
			for(int i = 0;i < list.size();i ++){
				SexEnum sexEnum = (SexEnum) list.get(i);
				sexSql.append(" select '");
				sexSql.append(sexEnum.getValue());
				sexSql.append("' as FID,'");
				sexSql.append(sexEnum.getAlias());
				sexSql.append("' as FSex ");
				sexSql.append(" union all");
			}
			sexSql.delete(sexSql.lastIndexOf("u") - 1, sexSql.lastIndexOf("l") + 1);
			ss.setDataItem("Sex",sexSql.toString(),null);
			
			mdx.append(" with member [Sex].[Totals] as 'sum([Sex].members)',caption='合计' ");
			mdx.append(" select {[Measures].Members} on columns,{[Sex].members,[Sex].[Totals]}on rows from Fact");
    	}
    	//省市
    	else if(isProvince)
    	{
    		configFile = "customer_province.xml";
    		factSql.append(" select c.FProvinceID,1 as FCounter ");
    		factSql.append(" from T_SHE_FDCCustomer as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer provinceSql = new StringBuffer();
    		provinceSql.append(" select p.FID, p.FName_"+loc+" as FName ");
    		provinceSql.append(" from T_BD_Province as p ");
    		
    		ss.setDataItem("Province",provinceSql.toString(),null);
    		
    		mdx.append(" with member [Province].[Totals] as 'sum([Province].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[Province].members,[Province].[Totals]}on rows from Fact");
    	}  
    	//行业
    	else if(isIndustry)
    	{
    		configFile = "customer_industry.xml";
    		factSql.append(" select c.FIndustryID,1 as FCounter ");
    		factSql.append(" from T_SHE_FDCCustomer as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer provinceSql = new StringBuffer();
    		provinceSql.append(" select p.FID, p.FName_"+loc+" as FName ");
    		provinceSql.append(" from T_BD_Industry as p ");
    		
    		ss.setDataItem("Industry",provinceSql.toString(),null);
    		
    		mdx.append(" with member [Industry].[Totals] as 'sum([Industry].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[Industry].members,[Industry].[Totals]}on rows from Fact");
    	}
    	else if(isEnterpriseSize)
    	{

    		configFile = "customer_enterprise.xml";
    		factSql.append(" select (case when c.FEnterpriceSize is null then N' ' else c.FEnterpriceSize end) as FSizeID,1 as FCounter ");
    		factSql.append(" from T_SHE_FDCCustomer as c ");
    		
//    		加过滤条件
    		factSql.append(itemSql);
    		
    		ss.setDataItem("Fact",factSql.toString(),sqlParams);
    		
    		StringBuffer bookerSql = new StringBuffer();
    		bookerSql.append(" select distinct (case when c.FEnterpriceSize is null then N' ' else c.FEnterpriceSize end) as FID,(case when c.FEnterpriceSize is null then N' ' else c.FEnterpriceSize end) as FSize ");
    		bookerSql.append(" from T_SHE_FDCCustomer as c ");
    		
    		ss.setDataItem("EnterpriseSize",bookerSql.toString(),null);
    		
    		mdx.append(" with member [EnterpriseSize].[Totals] as 'sum([EnterpriseSize].members)',caption='合计' ");
    		mdx.append(" select {[Measures].Members} on columns,{[EnterpriseSize].members,[EnterpriseSize].[Totals]}on rows from Fact");
    	
    	}
    	    	
    	
    	ss.setCaller(this.getClass());
		ss.setFilename(configFile);
		ss.setMdx(mdx.toString());

		return ss;
    }
	public Integer tempConvert(String temp)
	{
		
		
		StringBuffer result = new StringBuffer();
		for(int i=0;i<temp.length();i++)
		{
			if(temp.charAt(i)!=',')
			{
				result.append(temp.charAt(i));
			}
		}
		
		return new Integer(result.toString());
	}
	public Long tempConvertToLong(String temp)
	{
		
		
		StringBuffer result = new StringBuffer();
		for(int i=0;i<temp.length();i++)
		{
			if(temp.charAt(i)!=',')
			{
				result.append(temp.charAt(i));
			}
		}
		
		return new Long(result.toString());
	}
}