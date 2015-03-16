package com.kingdee.eas.fdc.sellhouse.report;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
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

import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.sellhouse.MarketingUnit;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

import java.util.Date;
import java.util.Set;

public class SaleManReportFacadeControllerBean extends AbstractSaleManReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.SaleManReportFacadeControllerBean");
    protected IRowSet _getPrintData(Context ctx, Set idSet) throws BOSException {
		return null;
	}
    protected RptParams _init(Context ctx, RptParams params)throws BOSException, EASBizException
	{
	    RptParams pp = new RptParams();
	    return pp;
	}
    private void initColoum(RptTableHeader header,RptTableColumn col,String name,int width,boolean isHide){
    	col= new RptTableColumn(name);
    	col.setWidth(width);
	    col.setHided(isHide);
	    header.addColumn(col);
    }
    protected RptParams _createTempTable(Context ctx, RptParams params)    throws BOSException, EASBizException
	{
	    RptTableHeader header = new RptTableHeader();
	    RptTableColumn col = null;
	    initColoum(header,col,"id",100,true);
	    initColoum(header,col,"sellProjectId",100,true);
	    initColoum(header,col,"name",100,false);
	    initColoum(header,col,"number",100,false);
	    initColoum(header,col,"tel",100,false);
	    initColoum(header,col,"visit",100,false);
	    initColoum(header,col,"sellProjectName",100,false);
	    initColoum(header,col,"purAmount",100,false);
	    initColoum(header,col,"purBuildArea",100,false);
	    initColoum(header,col,"purRoomArea",100,true);
	    initColoum(header,col,"purAccount",100,false);
	    initColoum(header,col,"amount",100,false);
	    initColoum(header,col,"buildArea",100,false);
	    initColoum(header,col,"roomArea",100,true);
	    initColoum(header,col,"account",100,false);
	    initColoum(header,col,"buildPrice",100,false);
	    initColoum(header,col,"roomPrice",100,true);
	    initColoum(header,col,"revAccount",100,false);
	    initColoum(header,col,"revAccountRate",100,false);
	    header.setLabels(new Object[][]{ 
	    		{
	    			"id","sellProjectId","置业顾问","用户名","来电数","来访数","项目名称","认购套数","认购建筑面积","认购套内面积","认购总价","签约套数","签约建筑面积","签约套内面积","签约总价","签约建筑均价","签约套内均价","回款金额","回款率%"
	    		}
	    		,
	    		{
	    			"id","sellProjectId","置业顾问","用户名","来电数","来访数","项目名称","认购套数","认购建筑面积","认购套内面积","认购总价","签约套数","签约建筑面积","签约套内面积","签约总价","签约建筑均价","签约套内均价","回款金额","回款率%"
				}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
	    
		RptRowSet rowSet = executeQuery(getSql(ctx,params), null, from, len, ctx);
		params.setObject("rowset", rowSet);
		return params;
    }
    protected String getSql(Context ctx,RptParams params){
    	String sellProject = (String) params.getObject("sellProject");
    	Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	
    	StringBuffer user =null;
	    if(params.getObject("user")!=null){
	    	user=new StringBuffer();
	    	Object[] userObject = (Object[])params.getObject("user");
        	for(int i=0;i<userObject.length;i++){
            	if(i==0){
            		user.append("'"+((UserInfo)userObject[i]).getId().toString()+"'");
            	}else{
            		user.append(",'"+((UserInfo)userObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    StringBuffer marketUnit = null;
	    if(params.getObject("marketUnit")!=null){
	    	marketUnit=new StringBuffer();
	    	Object[] marketUnitObject = (Object[])params.getObject("marketUnit");
        	for(int i=0;i<marketUnitObject.length;i++){
            	if(i==0){
            		marketUnit.append("'"+((MarketingUnitInfo)marketUnitObject[i]).getId().toString()+"'");
            	}else{
            		marketUnit.append(",'"+((MarketingUnitInfo)marketUnitObject[i]).getId().toString()+"'");
            	}
            }
	    } 
	    String signPurDate="";
    	if(fromDate!=null){
    		signPurDate=signPurDate+" and fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}";
		}
		if(toDate!=null){
			signPurDate=signPurDate+" and fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}";
		}
		String unSignPurDate="";
    	if(fromDate!=null&&toDate!=null){
    		unSignPurDate=unSignPurDate+"and (fbusAdscriptionDate<{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'} or fbusAdscriptionDate>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'})";
		}else if(fromDate!=null&&toDate==null){
			unSignPurDate=unSignPurDate+"and fbusAdscriptionDate<{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}";
		}else if(fromDate==null&&toDate!=null){
			unSignPurDate=unSignPurDate+"and fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}";
		}
    	
    	String sellProjectWhere="";
		if(sellProject!=null){
			sellProjectWhere=" and fsellProjectId in ("+sellProject+")";
		}else{
			sellProjectWhere=" and fsellProjectId in ('null')";
		}
		
	    Integer fromAmount = (Integer)params.getObject("fromAmount");
    	Integer toAmount =   (Integer)params.getObject("toAmount");
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select * from (select saleMan.fid id,(case when t.sellProjectId is not null then t.sellProjectId when t2.sellProjectId is not null then t2.sellProjectId when tel.sellProjectId is not null then tel.sellProjectId when visit.sellProjectId is not null then visit.sellProjectId else null end) sellProjectId,saleMan.fname_l2 name,saleMan.fnumber number,isnull(tel.amount,0) tel,isnull(visit.amount,0) visit,(case when t.sellProjectName is not null then t.sellProjectName when t2.sellProjectName is not null then t2.sellProjectName when tel.sellProjectName is not null then tel.sellProjectName when visit.sellProjectName is not null then visit.sellProjectName else null end) sellProjectName,isnull(t2.amount,0) purAmount,isnull(t2.buildArea,0) purBuildArea,isnull(t2.roomArea,0) purRoomArea,isnull(t2.account,0) purAccount,isnull(t.amount,0) amount,isnull(t.buildArea,0) buildArea,isnull(t.roomArea,0) roomArea,isnull(t.account,0) account,(case when t.buildArea is null or t.buildArea=0 then 0 else isnull(t.account/t.buildArea,0) end) buildPrice,(case when t.roomArea is null or t.roomArea=0 then 0 else isnull(t.account/t.roomArea,0) end) roomPrice,isnull(t.revAccount,0) revAccount,(case when t.account is null or t.account=0 then 0 else isnull(t.revAccount/t.account,0) end) revAccountRate");
    	sb.append(" from t_pm_user saleMan left join (");
    	sb.append(" select t1.sellProjectId,t1.sellProjectName,t1.userId,sum(t1.revAccount) revAccount,(max(case t1.name when 'sign' then round(t1.amount,2) else 0 end)-max(case t1.name when 'quit' then round(t1.amount,2) else 0 end)) amount,");
    	
    	sb.append(" (max(case t1.name when 'sign' then round(t1.account,2) else 0 end)-max(case t1.name when 'quit' then round(t1.account,2) else 0 end)) account,(max(case t1.name when 'sign' then round(t1.buildArea,2) else 0 end)-max(case t1.name when 'quit' then round(t1.buildArea,2) else 0 end)) buildArea,(max(case t1.name when 'sign' then round(t1.roomArea,2) else 0 end)-max(case t1.name when 'quit' then round(t1.roomArea,2) else 0 end)) roomArea from (");
    	sb.append(" select t.name,t.sellProjectId,t.sellProjectName,t.userId,sum(t.revAccount) revAccount,sum(t.amount) amount,sum(t.account) account,sum(t.buildArea) buildArea,sum(t.roomArea) roomArea from(");
    	sb.append(" select 'sign' name,(case when psp.fid is null then sp.fid else psp.fid end) sellProjectId,(case when psp.fname_l2 is null then sp.fname_l2 else psp.fname_l2 end) sellProjectName,sign.FSalesmanID userId,sum(rev.revAccount) revAccount,count(*) amount,sum(sign.fcontractTotalAmount) account,sum(case sign.fsellType when 'PlanningSell' then sign.fstrdPlanBuildingArea when 'PreSell' then sign.fbulidingArea else sign.fstrdActualBuildingArea end) buildArea,sum(case sign.fsellType when 'PlanningSell' then sign.fstrdPlanRoomArea when 'PreSell' then sign.froomArea else sign.fstrdActualRoomArea end) roomArea from t_she_signManage sign left join t_she_sellProject sp on sp.fid=sign.fsellProjectid left join t_she_sellProject psp on psp.fid=sp.fparentid");
    	sb.append(" left join (select base.fbillid,sum(tbov.factRevAmount) revAccount from t_she_transaction base left join t_she_tranBusinessOverView tbov on tbov.fheadid=base.fid left join t_she_moneyDefine md on md.fid=tbov.fmoneyDefineId where tbov.ftype='Pay' and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') and tbov.fBusinessName!='违约金' group by base.fbillid) rev on rev.fbillid=sign.fid ");
    	sb.append(" where sign.fbizState in('SignApple','SignAudit','QRNullify')");
		if(sellProject!=null){
    		sb.append(" and (case when psp.fid is null then sp.fid else psp.fid end) in("+sellProject+")");
    	}
    	if(fromDate!=null){
			sb.append(" and sign.fbusAdscriptionDate>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		sb.append(" group by (case when psp.fid is null then sp.fid else psp.fid end),(case when psp.fname_l2 is null then sp.fname_l2 else psp.fname_l2 end),sign.FSalesmanID");
		
		sb.append(" union all select 'quit' name,(case when psp.fid is null then sp.fid else psp.fid end) sellProjectId,(case when psp.fname_l2 is null then sp.fname_l2 else psp.fname_l2 end) sellProjectName,sign.FSalesmanID userId,0 revAccount,count(*) amount,sum(sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0)) account,sum(case sign.fsellType when 'PlanningSell' then sign.fstrdPlanBuildingArea when 'PreSell' then sign.fbulidingArea else sign.fstrdActualBuildingArea end) buildArea,sum(case sign.fsellType when 'PlanningSell' then sign.fstrdPlanRoomArea when 'PreSell' then sign.froomArea else sign.fstrdActualRoomArea end) roomArea from t_she_signManage sign right join t_she_changeManage change on change.fsrcid=sign.fid left join t_she_sellProject sp on sp.fid=sign.fsellProjectid left join t_she_sellProject psp on psp.fid=sp.fparentid");
//    	sb.append(" left join (select base.fbillid,sum(tbov.factRevAmount) revAccount from t_she_transaction base left join t_she_tranBusinessOverView tbov on tbov.fheadid=base.fid left join t_she_moneyDefine md on md.fid=tbov.fmoneyDefineId where tbov.ftype='Pay' and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') and tbov.fBusinessName!='违约金' group by base.fbillid) rev on rev.fbillid=sign.fid ");
    	sb.append(" where change.fstate in('2SUBMITTED','4AUDITTED') and change.fbizType='quitRoom'");
		if(sellProject!=null){
    		sb.append(" and (case when psp.fid is null then sp.fid else psp.fid end) in("+sellProject+")");
    	}
    	if(fromDate!=null){
			sb.append(" and change.fchangeDate>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and change.fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		sb.append(" group by (case when psp.fid is null then sp.fid else psp.fid end),(case when psp.fname_l2 is null then sp.fname_l2 else psp.fname_l2 end),sign.FSalesmanID");
		
		sb.append("	)t group by t.name,t.sellProjectId,t.sellProjectName,t.userId )t1 group by t1.sellProjectId,t1.sellProjectName,t1.userId)t on saleMan.fid=t.userId");
		
    	sb.append(" left join (select t1.sellProjectId,t1.sellProjectName,t1.userId,max(case t1.name when 'signPre' then round(t1.amount,2) else 0 end)-max(case t1.name when 'quitSignPre' then round(t1.amount,2) else 0 end)+");
    	sb.append(" max(case t1.name when 'unSignPur' then round(t1.amount,2) else 0 end)-max(case t1.name when 'quitUnSignPur' then round(t1.amount,2) else 0 end)+");
    	sb.append(" max(case t1.name when 'signPur' then round(t1.amount,2) else 0 end)-max(case t1.name when 'quitSignPur' then round(t1.amount,2) else 0 end)+");
    	sb.append(" max(case t1.name when 'directSign' then round(t1.amount,2) else 0 end)-max(case t1.name when 'quitDirectSign' then round(t1.amount,2) else 0 end) amount,");
    	
    	sb.append(" max(case t1.name when 'signPre' then round(t1.account,2) else 0 end)-max(case t1.name when 'quitUnSignPur' then round(t1.account,2) else 0 end)+");
    	sb.append(" max(case t1.name when 'unSignPur' then round(t1.account,2) else 0 end)-max(case t1.name when 'quitUnSignPur' then round(t1.account,2) else 0 end)+");
    	sb.append(" max(case t1.name when 'signPur' then round(t1.account,2) else 0 end)-max(case t1.name when 'quitSignPur' then round(t1.account,2) else 0 end)+");
    	sb.append(" max(case t1.name when 'directSign' then round(t1.account,2) else 0 end)-max(case t1.name when 'quitDirectSign' then round(t1.account,2) else 0 end) account,");
    	
    	sb.append(" max(case t1.name when 'signPre' then round(t1.buildArea,2) else 0 end)-max(case t1.name when 'quitUnSignPur' then round(t1.buildArea,2) else 0 end)+");
    	sb.append(" max(case t1.name when 'unSignPur' then round(t1.buildArea,2) else 0 end)-max(case t1.name when 'quitUnSignPur' then round(t1.buildArea,2) else 0 end)+");
    	sb.append(" max(case t1.name when 'signPur' then round(t1.buildArea,2) else 0 end)-max(case t1.name when 'quitSignPur' then round(t1.buildArea,2) else 0 end)+");
    	sb.append(" max(case t1.name when 'directSign' then round(t1.buildArea,2) else 0 end)-max(case t1.name when 'quitDirectSign' then round(t1.buildArea,2) else 0 end) buildArea,");
    	
    	sb.append(" max(case t1.name when 'signPre' then round(t1.roomArea,2) else 0 end)-max(case t1.name when 'quitUnSignPur' then round(t1.roomArea,2) else 0 end)+");
    	sb.append(" max(case t1.name when 'unSignPur' then round(t1.roomArea,2) else 0 end)-max(case t1.name when 'quitUnSignPur' then round(t1.roomArea,2) else 0 end)+");
    	sb.append(" max(case t1.name when 'signPur' then round(t1.roomArea,2) else 0 end)-max(case t1.name when 'quitSignPur' then round(t1.roomArea,2) else 0 end)+");
    	sb.append(" max(case t1.name when 'directSign' then round(t1.roomArea,2) else 0 end)-max(case t1.name when 'quitDirectSign' then round(t1.roomArea,2) else 0 end) roomArea from(");
   
    	sb.append(" select t.name,t.sellProjectId,t.sellProjectName,t.userId,sum(t.amount) amount,sum(t.account) account,sum(t.buildArea) buildArea,sum(t.roomArea) roomArea from (");
    	
    	getBaseTransaction(sb,"'signPre'","t_she_prePurchaseManage","T_SHE_PrePurchaseSaleManEntry","'ToSign'","(EXISTS (select fsrcId from t_she_signManage t where fbizState in('SignApple','SignAudit','QRNullify') "+signPurDate+sellProjectWhere+" and "+getLockChangeSrcId(fromDate,toDate,sellProject)+" and sign.fid=t.fsrcId))",fromDate,toDate,sellProject);
    	sb.append(" union all");
    	getBaseTransaction(sb,"'unSignPur'","t_she_purchaseManage","T_SHE_PurSaleManEntry","'PurApple','PurAudit','QRNullify'",null,fromDate,toDate,sellProject);
    	sb.append(" union all");
    	getBaseTransaction(sb,"'signPur'","t_she_purchaseManage","T_SHE_PurSaleManEntry","'ToSign'","(EXISTS (select fsrcId from t_she_signManage t where fbizState in('SignApple','SignAudit','QRNullify') "+signPurDate+sellProjectWhere+" and "+getLockChangeSrcId(fromDate,toDate,sellProject)+" and sign.fid=t.fsrcId))",fromDate,toDate,sellProject);
    	sb.append(" union all");
    	getBaseTransaction(sb,"'directSign'","t_she_signManage",null,"'SignApple','SignAudit','QRNullify'","(sign.fsrcId is null or NOT EXISTS (select fid from t_she_prePurchaseManage t where 1=1 and sign.fsrcId=t.fid "+sellProjectWhere+" union select fid from t_she_purchaseManage t where 1=1 and sign.fsrcId=t.fid "+sellProjectWhere+"))",fromDate,toDate,sellProject);
    	sb.append(" union all");
    	getChange(sb,"'quitSignPre'","t_she_prePurchaseManage","T_SHE_PrePurchaseSaleManEntry","'2SUBMITTED','4AUDITTED'","(EXISTS (select fsrcId from t_she_signManage t where fbizState in('QRNullify') "+signPurDate+sellProjectWhere+" and sign.fid=t.fsrcId))",fromDate,toDate,sellProject);
    	sb.append(" union all");
    	getChange(sb,"'quitUnSignPur'","t_she_purchaseManage","T_SHE_PurSaleManEntry","'2SUBMITTED','4AUDITTED'","(EXISTS (select fid from t_she_purchaseManage t where fbizState in('QRNullify') and sign.fid=t.fId))",fromDate,toDate,sellProject);
    	if(!unSignPurDate.equals("")){
    		sb.append(" union all");
			getBaseTransaction(sb,"'unSignPur'","t_she_purchaseManage","T_SHE_PurSaleManEntry","'ToSign'","(EXISTS (select fsrcId from t_she_signManage t where fbizState in('SignApple','SignAudit','QRNullify') "+unSignPurDate+sellProjectWhere+" and "+getLockChangeSrcId(fromDate,toDate,sellProject)+" and sign.fid=t.fsrcId))",fromDate,toDate,sellProject);
			sb.append(" union all");
    		getChange(sb,"'quitUnSignPur'","t_she_purchaseManage","T_SHE_PurSaleManEntry","'2SUBMITTED','4AUDITTED'","(EXISTS (select fsrcId from t_she_signManage t where fbizState in('QRNullify') "+unSignPurDate+sellProjectWhere+" and sign.fid=t.fsrcId))",fromDate,toDate,sellProject);
    	}
    	sb.append(" union all");
    	getChange(sb,"'quitSignPur'","t_she_purchaseManage","T_SHE_PurSaleManEntry","'2SUBMITTED','4AUDITTED'","(EXISTS (select fsrcId from t_she_signManage t where fbizState in('QRNullify') "+signPurDate+sellProjectWhere+" and sign.fid=t.fsrcId))",fromDate,toDate,sellProject);
    	sb.append(" union all");
    	getChange(sb,"'quitDirectSign'","t_she_signManage",null,"'2SUBMITTED','4AUDITTED'","(sign.fsrcId is null or NOT EXISTS (select fid from t_she_prePurchaseManage t where 1=1 and sign.fsrcId=t.fid"+sellProjectWhere+" union select fid from t_she_purchaseManage t where 1=1 and sign.fsrcId=t.fid "+sellProjectWhere+"))",fromDate,toDate,sellProject);
    	
    	sb.append(" )t group by t.name,t.sellProjectId,t.sellProjectName,t.userId )t1 group by t1.sellProjectId,t1.sellProjectName,t1.userId)t2 on t2.userId=saleMan.fid and t2.sellProjectId=t.sellProjectId");
		
		getTelSql(sb,fromDate,toDate,sellProject);
    	sb.append(" tel on tel.saleManId=saleMan.fid and tel.sellProjectId=t.sellProjectId");
    	getVisitSql(sb,fromDate,toDate,sellProject);
    	sb.append(" visit on visit.saleManId=saleMan.fid and visit.sellProjectId=t.sellProjectId");
    	
//    	sb.append(" left join t_pm_user saleMan on tel.saleManId=saleMan.fid");
    	
    	sb.append(" where 1=1");
    	if(user!=null&&marketUnit!=null){
    		sb.append(" and (saleMan.fid in("+user.toString()+")");
    		sb.append(" or saleMan.fid in(select member.fmemberId from t_she_marketingUnitMember member left join t_she_marketingUnit mu on mu.fid=member.fheadid where mu.fid in("+marketUnit.toString()+")))");
    	}else if(marketUnit!=null){
    		sb.append(" and saleMan.fid in(select member.fmemberId from t_she_marketingUnitMember member left join t_she_marketingUnit mu on mu.fid=member.fheadid where mu.fid in("+marketUnit.toString()+"))");
    	}else if(user!=null){
    		sb.append(" and saleMan.fid in("+user.toString()+")");
    	}else{
    		sb.append(" and (tel.saleManId is not null or visit.saleManId is not null or t2.userId is not null)");
    	}
    	if(fromAmount!=null){
			sb.append(" and t.amount>="+fromAmount.intValue());
		}
		if(toAmount!=null){
			sb.append(" and t.amount<="+toAmount.intValue());
		}
    	sb.append(" )t order by t.name,t.sellProjectId");
		return sb.toString();
    }
    private void getVisitSql(StringBuffer sb,Date fromDate,Date toDate,String sellProject){
    	StringBuilder where = new StringBuilder();
    	where.append(" having 1=1");
    	if(fromDate!=null){
			where.append(" and min(cct.ftrackDate)>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			where.append(" and min(cct.ftrackDate)<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
    	sb.append(" left join (select t.sellProjectId,t.sellProjectName,t.saleManId,count(*) amount from (");
    	sb.append(" select cc.FSalemanID saleManId,sp.fid sellProjectId,sp.fname_l2 sellProjectName from t_she_commerceChanceTrack cct left join t_she_sellProject sp on sp.fid=cct.fsellProjectid left join t_she_commerceChance cc on cc.fid=cct.fcommerceChanceid where cct.finteractionType='INTERVIEW'");
    	sb.append(" and cc.fid is not null ");
    	if(sellProject!=null){
    		sb.append(" and sp.fid in("+sellProject+")");
    	}
		sb.append(" group by sp.fid,sp.fname_l2,cct.fcommerceChanceid,cc.FSalemanID");
		sb.append(where);
		sb.append(" union all");
		sb.append(" select cc.FSalemanID saleManId,sp.fid sellProjectId,sp.fname_l2 sellProjectName from t_she_commerceChanceTrack cct left join t_she_sellProject sp on sp.fparentid=cct.fsellProjectid left join t_she_commerceChance cc on cc.fid=cct.fcommerceChanceid where cct.finteractionType='INTERVIEW'");
		sb.append(" and cc.fid is not null ");
		if(sellProject!=null){
    		sb.append(" and sp.fid in("+sellProject+")");
    	}
		sb.append(" group by sp.fid,sp.fname_l2,cct.fcommerceChanceid,cc.FSalemanID");
		sb.append(where);
		sb.append(")t group by t.saleManId,t.sellProjectId,t.sellProjectName)");
    }
    private void getTelSql(StringBuffer sb,Date fromDate,Date toDate,String sellProject){
    	StringBuilder where = new StringBuilder();
    	where.append(" having 1=1");
    	if(fromDate!=null){
			where.append(" and min(cct.ftrackDate)>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			where.append(" and min(cct.ftrackDate)<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
    	sb.append(" left join (select t.sellProjectId,t.sellProjectName,t.saleManId,count(*) amount from (");
		sb.append(" select cc.FSalemanID saleManId,sp.fid sellProjectId,sp.fname_l2 sellProjectName from t_she_commerceChanceTrack cct left join t_she_sellProject sp on sp.fid=cct.fsellProjectid left join (select min(cct.ftrackDate) ftrackDate ,cct.fcommerceChanceid from t_she_commerceChanceTrack cct where cct.finteractionType='INTERVIEW' group by cct.fcommerceChanceid) cct1");
		sb.append(" on cct.fcommerceChanceid=cct1.fcommerceChanceid left join t_she_commerceChance cc on cc.fid=cct.fcommerceChanceid where cct.finteractionType='TELEPHONE' and (cct.ftrackDate<cct1.ftrackDate or cct1.ftrackDate is null)");
		sb.append(" and cc.fid is not null ");
		if(sellProject!=null){
    		sb.append(" and sp.fid in("+sellProject+")");
    	}
		sb.append(" group by sp.fid,sp.fname_l2,cct.fcommerceChanceid,cc.FSalemanID");
		sb.append(where);
		sb.append(" union all");
		sb.append(" select cc.FSalemanID saleManId,sp.fid sellProjectId,sp.fname_l2 sellProjectName from t_she_commerceChanceTrack cct left join t_she_sellProject sp on sp.fparentid=cct.fsellProjectid left join (select min(cct.ftrackDate) ftrackDate ,cct.fcommerceChanceid from t_she_commerceChanceTrack cct where cct.finteractionType='INTERVIEW' group by cct.fcommerceChanceid) cct1");
		sb.append(" on cct.fcommerceChanceid=cct1.fcommerceChanceid left join t_she_commerceChance cc on cc.fid=cct.fcommerceChanceid where cct.finteractionType='TELEPHONE' and (cct.ftrackDate<cct1.ftrackDate or cct1.ftrackDate is null)");
		sb.append(" and cc.fid is not null ");
		if(sellProject!=null){
    		sb.append(" and sp.fid in("+sellProject+")");
    	}
		sb.append(" group by sp.fid,sp.fname_l2,cct.fcommerceChanceid,cc.FSalemanID");
		sb.append(where);
		sb.append(")t group by t.saleManId,t.sellProjectId,t.sellProjectName)");
    }
    private void getBaseTransaction(StringBuffer sb,String name,String table,String entryTable,String state,String where,Date fromDate,Date toDate,String sellProject){
    	if(name.equals("'directSign'")){
    		sb.append(" select "+name+" name,(case when psp.fid is null then sp.fid else psp.fid end) sellProjectId,(case when psp.fname_l2 is null then sp.fname_l2 else psp.fname_l2 end) sellProjectName,sign.FSalesmanID userId,count(*) amount,sum(sign.fcontractTotalAmount) account,sum(case sign.fsellType when 'PlanningSell' then sign.fstrdPlanBuildingArea when 'PreSell' then sign.fbulidingArea else sign.fstrdActualBuildingArea end) buildArea,sum(case sign.fsellType when 'PlanningSell' then sign.fstrdPlanRoomArea when 'PreSell' then sign.froomArea else sign.fstrdActualRoomArea end) roomArea from "+table+" sign left join t_she_sellProject sp on sp.fid=sign.fsellProjectid left join t_she_sellProject psp on psp.fid=sp.fparentid");
        	sb.append(" where sign.fbizState in("+state+")");
    		if(sellProject!=null){
        		sb.append(" and (case when psp.fid is null then sp.fid else psp.fid end) in("+sellProject+")");
        	}
        	if(fromDate!=null){
    			sb.append(" and sign.fbusAdscriptionDate>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    		if(where!=null)
        		sb.append(" and "+where);
    		sb.append(" group by (case when psp.fid is null then sp.fid else psp.fid end),(case when psp.fname_l2 is null then sp.fname_l2 else psp.fname_l2 end),sign.FSalesmanID ");
    	}else{
    		sb.append(" select "+name+" name,(case when psp.fid is null then sp.fid else psp.fid end) sellProjectId,(case when psp.fname_l2 is null then sp.fname_l2 else psp.fname_l2 end) sellProjectName,signEntry.fuserid userId,count(*) amount,sum(sign.fcontractTotalAmount) account,sum(case sign.fsellType when 'PlanningSell' then sign.fstrdPlanBuildingArea when 'PreSell' then sign.fbulidingArea else sign.fstrdActualBuildingArea end) buildArea,sum(case sign.fsellType when 'PlanningSell' then sign.fstrdPlanRoomArea when 'PreSell' then sign.froomArea else sign.fstrdActualRoomArea end) roomArea from "+table+" sign left join t_she_sellProject sp on sp.fid=sign.fsellProjectid left join t_she_sellProject psp on psp.fid=sp.fparentid left join "+entryTable+" signEntry on signEntry.fheadId=sign.fid");
        	sb.append(" where sign.fbizState in("+state+")");
    		if(sellProject!=null){
        		sb.append(" and (case when psp.fid is null then sp.fid else psp.fid end) in("+sellProject+")");
        	}
        	if(fromDate!=null){
    			sb.append(" and sign.fbusAdscriptionDate>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    		if(where!=null)
        		sb.append(" and "+where);
    		sb.append(" group by (case when psp.fid is null then sp.fid else psp.fid end),(case when psp.fname_l2 is null then sp.fname_l2 else psp.fname_l2 end),signEntry.fuserid ");
    	}
    }
    private void getChange(StringBuffer sb,String name,String table,String entryTable,String state,String where,Date fromDate,Date toDate,String sellProject){
    	if(name.equals("'quitDirectSign'")){
    		sb.append(" select "+name+" name,(case when psp.fid is null then sp.fid else psp.fid end) sellProjectId,(case when psp.fname_l2 is null then sp.fname_l2 else psp.fname_l2 end) sellProjectName,sign.FSalesmanID userId,count(*) amount,sum(sign.fcontractTotalAmount) account,sum(case sign.fsellType when 'PlanningSell' then sign.fstrdPlanBuildingArea when 'PreSell' then sign.fbulidingArea else sign.fstrdActualBuildingArea end) buildArea,sum(case sign.fsellType when 'PlanningSell' then sign.fstrdPlanRoomArea when 'PreSell' then sign.froomArea else sign.fstrdActualRoomArea end) roomArea from "+table+" sign right join t_she_changeManage change on change.fsrcid=sign.fid left join t_she_sellProject sp on sp.fid=sign.fsellProjectid left join t_she_sellProject psp on psp.fid=sp.fparentid");
        	sb.append(" where change.fstate in("+state+")");
    		if(sellProject!=null){
        		sb.append(" and (case when psp.fid is null then sp.fid else psp.fid end) in("+sellProject+")");
        	}
    		if(fromDate!=null){
    			sb.append(" and change.fchangeDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and change.fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    		if(where!=null)
        		sb.append(" and "+where);
    		sb.append(" group by (case when psp.fid is null then sp.fid else psp.fid end),(case when psp.fname_l2 is null then sp.fname_l2 else psp.fname_l2 end),sign.FSalesmanID ");
    	}else{
    		sb.append(" select "+name+" name,(case when psp.fid is null then sp.fid else psp.fid end) sellProjectId,(case when psp.fname_l2 is null then sp.fname_l2 else psp.fname_l2 end) sellProjectName,signEntry.fuserid userId,count(*) amount,sum(sign.fcontractTotalAmount) account,sum(case sign.fsellType when 'PlanningSell' then sign.fstrdPlanBuildingArea when 'PreSell' then sign.fbulidingArea else sign.fstrdActualBuildingArea end) buildArea,sum(case sign.fsellType when 'PlanningSell' then sign.fstrdPlanRoomArea when 'PreSell' then sign.froomArea else sign.fstrdActualRoomArea end) roomArea from "+table+" sign right join t_she_changeManage change on change.fsrcid=sign.fid left join t_she_sellProject sp on sp.fid=sign.fsellProjectid left join t_she_sellProject psp on psp.fid=sp.fparentid left join "+entryTable+" signEntry on signEntry.fheadId=sign.fid");
        	sb.append(" where change.fstate in("+state+")");
    		if(sellProject!=null){
        		sb.append(" and (case when psp.fid is null then sp.fid else psp.fid end) in("+sellProject+")");
        	}
    		if(fromDate!=null){
    			sb.append(" and change.fchangeDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and change.fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    		if(where!=null)
        		sb.append(" and "+where);
    		sb.append(" group by (case when psp.fid is null then sp.fid else psp.fid end),(case when psp.fname_l2 is null then sp.fname_l2 else psp.fname_l2 end),signEntry.fuserid ");
    	}
    }
    private String getLockChangeSrcId(Date fromDate,Date toDate,String sellProject){
    	String change="(NOT EXISTS (select fsrcId from t_she_changeManage tt where fstate in('2SUBMITTED','4AUDITTED') and fbizType='quitRoom' and tt.fsrcId=t.fid ";
		if(fromDate!=null){
			change=change+" and fchangeDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}";
		}
		if(toDate!=null){
			change=change+" and fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}";
		}
    	if(sellProject!=null){
    		change=change+" and fsellProjectId in ("+sellProject+")";
		}
    	change=change+"))";
    	return change;
    }
}