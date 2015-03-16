package com.kingdee.eas.fdc.market.report;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.jdbc.rowset.IRowSet;

public class MediaCostAssessmentReportFacadeControllerBean extends AbstractMediaCostAssessmentReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.market.report.MediaCostAssessmentReportFacadeControllerBean");
    
    private SellProjectInfo sellProject = new SellProjectInfo();
    
    protected Map _getDatas(Context ctx, Map params) throws BOSException, EASBizException 
    {
        return queryDatas(ctx,params);
    }
    
    private Map queryDatas(Context ctx,Map params)
    {
//    	sellProject.setId(BOSUuid.read("sljrmtweQBiW4jhEzvTYyS/75aw="));
    	Map rowSet = new HashMap();
    	IRowSet set = null;
    	IRowSet set_1 = null;
    	FDCSQLBuilder sqlBuild = new FDCSQLBuilder();
    	try {
    		if(((SellProjectInfo)params.get("sellProject")).get("id") != null){
        		sellProject.setId(BOSUuid.read(((SellProjectInfo)params.get("sellProject")).get("id").toString()));
    		}
//    		String sql = "select tt.channelName,tt.treeName,sum(tt.visitCommerceId) visitCommerceId,sum(tt.telCommerceId) telCommerceId,sum(tt.tel2visitCommerceId) tel2visitCommerceId,sum(tt.signManage) signManage,sum(tt.actpaylocamt) actpaylocamt,sum(tt.Contractamount) Contractamount,sum(tt.Cwtamount) Cwtamount from ( ";
//	    	sql = sql + "select t_all_value.channelName,t_all_value.treeName,t_all_value.channelTypeId,t_all_value.visitCommerceId,t_all_value.telCommerceId,t_all_value.tel2visitCommerceId,t_all_value.signManage,t_payment.actpaylocamt,t_contractBill.Contractamount,t_ContractWithoutText.Cwtamount from ( ";
    		String sql = "select tt.channelName,tt.channelTypeId,tt.treeName,sum(tt.visitCommerceId) visitCommerceId,sum(tt.telCommerceId) telCommerceId,sum(tt.tel2visitCommerceId) tel2visitCommerceId,sum(tt.signManage) signManage from ( ";
	    	sql = sql + "select t_all_value.channelName,t_all_value.treeName,t_all_value.channelTypeId,t_all_value.visitCommerceId,t_all_value.telCommerceId,t_all_value.tel2visitCommerceId,t_all_value.signManage from ( ";
	    	sql = sql + starSql();
	    	sql = sql + starSql_1();
	    	sql = sql + telSql(params);
	    	sql = sql + visitSql(params);
	    	sql = sql + telToVisitSql(params);
	    	sql = sql + sign(params);
	    	sql = sql + endSql_1();
	    	sql = sql + endSql();
	    	sql = sql + "where t_all_value.treeName is not null and t_all_value.channelName is not null " +
	    				"group by t_all_value.channelName,t_all_value.treeName,t_all_value.channelTypeId,t_all_value.visitCommerceId,t_all_value.telCommerceId,t_all_value.tel2visitCommerceId,t_all_value.signManage ";
	    	sql = sql + ") tt group by  tt.channelName,tt.treeName,tt.channelTypeId order by tt.treeName asc";
//	    	sql = sql + getContract(params);
//	    	sql = sql + "where t_all_value.treeName is not null and t_all_value.channelName is not null " +
//	    			"group by t_all_value.channelName,t_all_value.treeName,t_all_value.channelTypeId,t_all_value.visitCommerceId,t_all_value.telCommerceId,t_all_value.tel2visitCommerceId,t_all_value.signManage,t_payment.actpaylocamt,"+
//	    			"t_contractBill.Contractamount,t_ContractWithoutText.Cwtamount";
//	    	sql = sql + ") tt group by  tt.channelName,tt.treeName order by tt.treeName asc";
	    	sqlBuild.appendSql("/*dialect*/"+sql);
	    	set = sqlBuild.executeQuery(ctx);	
	    	rowSet.put("rowSet_cus", set);
	    	sqlBuild.clear();
	    	String sql_1 = "select a.MediaNameID MediaNameID,sum(a.actpaylocamt) actpaylocamt,sum(a.contractamount) Contractamount,sum(a.cwtamount) Cwtamount from( ";
	    	sql_1 = sql_1 + getContract(params);
	    	sql_1 = sql_1 + ")) a group by a.MediaNameID";
	    	sqlBuild.appendSql("/*dialect*/"+sql_1);
	    	set_1 = sqlBuild.executeQuery(ctx);	
	    	rowSet.put("rowSet_contract", set_1);
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return rowSet;
    }
    
	//最外层SQL
    private String starSql(){
    	StringBuffer sql = new StringBuffer();
    	sql.append("select t_value_2.channelName channelName,t_value_2.treeName treeName,t_value_2.channelTypeId channelTypeId,count(t_value_2.visitCommerceId) visitCommerceId, ");
    	sql.append("count(t_value_2.telCommerceId) telCommerceId,count(t_value_2.tel2visitCommerceId) tel2visitCommerceId,count(t_value_2.signManage) signManage ");
    	sql.append("from ( ");
    	return sql.toString();
    }

	//最外层SQL
    private String endSql(){
    	StringBuffer sql = new StringBuffer();
    	sql.append(") t_value_2 group by t_value_2.channelName,t_value_2.treeName,t_value_2.channelTypeId )t_all_value ");
    	return sql.toString();
    }
    
    
    
    //外层第二层SQL
    private String starSql_1(){
    	StringBuffer sql = new StringBuffer();
    	sql.append("select t_value_1.channelName,t_value_1.channelTypeId,t_value_1.treeName,t_value_1.visitCommerceId,t_value_1.telCommerceId, t_value_1.tel2visitCommerceId, ");
    	sql.append("t_value_1.signManage from ( ");
    	return sql.toString();
    }



	//最外第二层SQL
    private String endSql_1(){
    	StringBuffer sql = new StringBuffer();
    	sql.append(")t_value_1 ");
//    	sql.append("where t_value_1.visitCommerceId is not null or t_value_1.telCommerceId is not null or t_value_1.tel2visitCommerceId is not null or t_value_1.signManage is not null ");
    	sql.append("group by t_value_1.channelName,t_value_1.channelTypeId,t_value_1.treeName,t_value_1.visitCommerceId,t_value_1.telCommerceId, t_value_1.tel2visitCommerceId,t_value_1.signManage ");
    	return sql.toString();
    }
    
	
          
	//来电
    private String telSql(Map params){
		StringBuilder sql = new StringBuilder();
		sql.append("select T_MAR_ChannelType.Fname_L2 channelName,T_MAR_ChannelType.fid channelTypeId,T_MAR_ChannelTypeTree.fname_l2 treeName,'' visitCommerceId, ");
		sql.append("t_she_tel.telCommerceId telCommerceId,'' tel2visitCommerceId,'' signManage from T_MAR_ChannelTypeTree ");
		sql.append("left outer join T_MAR_ChannelType on T_MAR_ChannelTypeTree.fid = T_MAR_ChannelType.Ftreeid ");
		sql.append("and T_MAR_ChannelType.Fcontrolunitid = '"+params.get("CU")+"' ");
		sql.append("right outer join ( ");
//		sql.append("select cct.fcommerceChanceid telCommerceId,cc.fclassifyid classifyid,cc.fcustomerid telCustomerId from t_she_commerceChanceTrack cct ");
//		sql.append("left outer join t_she_commerceChance cc on cct.Fcommercechanceid = cc.Fid where ");
//		sql.append("cct.fcommerceChanceid is not null and ");
//		sql.append("cct.finteractionType='TELEPHONE' and (cct.ftrackDate <(select min(cct1.ftrackDate) from t_she_commerceChanceTrack cct1 where cct1.finteractionType='INTERVIEW' ");
//		sql.append("and cct.fcommerceChanceid=cct1.fcommerceChanceid group by cct1.fcommerceChanceid ) or (select count(*) from t_she_commerceChanceTrack cct1 where cct1.finteractionType='INTERVIEW' and cct.fcommerceChanceid=cct1.fcommerceChanceid)=0) ");
		//新逻辑，取来电最新的媒体
		sql.append("select cct.fcommerceChanceid telCommerceId,cct.fclassifyid classifyid,cc.fcustomerid telCustomerId from t_she_commerceChanceTrack cct ");
		sql.append("right outer join ( ");
		sql.append("select ccts.fcommerceChanceid fcommerceChanceid,max(ccts.ftrackdate) ftrackdate from t_she_commerceChanceTrack ccts where ccts.finteractionType = 'TELEPHONE' ");
		if(((SellProjectInfo)params.get("sellProject")).get("id")!=null){
			sql.append("and ccts.fsellprojectid = '"+((SellProjectInfo)params.get("sellProject")).get("id")+"' ");
		}
//		sql.append("group by cct.fcommerceChanceid, cc.fclassifyid , cc.fcustomerid having 1=1 ");
		if(params.get("beginDate")!=null){
    		Date beginDate = (Date)params.get("beginDate");
    		sql.append("and ccts.ftrackDate >= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(beginDate))+ "'} ");
		}
		if(params.get("endDate")!=null){
			Date endDate = (Date)params.get("endDate");
			sql.append("and ccts.ftrackDate < {ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'} ");
		}
		sql.append("group by ccts.fcommerceChanceid )asps on cct.Fcommercechanceid = asps.fcommerceChanceid and cct.Ftrackdate = asps.ftrackdate ");
		sql.append("left outer join t_she_commerceChance cc on cct.Fcommercechanceid = cc.Fid ");
		sql.append(")t_she_tel on t_she_tel.classifyid = T_MAR_ChannelType.fid ");
		sql.append("union all ");
		return sql.toString();
	}
    
    //来访
    private String visitSql(Map params){
    	StringBuilder sql = new StringBuilder();
    	sql.append("select T_MAR_ChannelType.Fname_L2 channelName,T_MAR_ChannelType.fid channelTypeId,T_MAR_ChannelTypeTree.fname_l2 treeName, ");
		sql.append("t_she_visit.visitCommerceId visitCommerceId,'' telCommerceId,'' tel2visitCommerceId,'' signManage ");
		sql.append("from T_MAR_ChannelTypeTree left outer join T_MAR_ChannelType on T_MAR_ChannelTypeTree.fid = T_MAR_ChannelType.Ftreeid ");
		sql.append("and T_MAR_ChannelType.Fcontrolunitid = '"+params.get("CU")+"' ");
		sql.append("right outer join ( ");
//		sql.append("select cct.fcommerceChanceid visitCommerceId,cc.fclassifyid classifyid,cc.fcustomerid visitCustomerId from t_she_commerceChanceTrack cct ");
//		sql.append("left outer join t_she_commerceChance cc on cct.Fcommercechanceid = cc.Fid where ");
////		sql.append("cct.fcommerceChanceid is not null and ");
//    	sql.append("cct.finteractionType='INTERVIEW' ");
//    	if(((SellProjectInfo)params.get("sellProject")).get("id")!=null){
//			sql.append("and cct.fsellprojectid = '"+((SellProjectInfo)params.get("sellProject")).get("id")+"' ");
//		}
//		sql.append("group by cct.fcommerceChanceid, cc.fclassifyid , cc.fcustomerid having 1=1 ");
//		if(params.get("beginDate")!=null){
//    		Date beginDate = (Date)params.get("beginDate");
//    		sql.append("and min(cct.ftrackDate)>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(beginDate))+ "'} ");
//		}
//		if(params.get("endDate")!=null){
//			Date endDate = (Date)params.get("endDate");
//			sql.append("and min(cct.ftrackDate)<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'} ");
//		}
		//新逻辑，取来访最新的媒体
		sql.append("select cct.fcommerceChanceid visitCommerceId,cct.fclassifyid classifyid,cc.fcustomerid visitCustomerId from t_she_commerceChanceTrack cct ");
		sql.append("right outer join ( ");
		sql.append("select ccts.fcommerceChanceid fcommerceChanceid,max(ccts.ftrackdate) ftrackdate from t_she_commerceChanceTrack ccts where ccts.finteractionType = 'INTERVIEW' ");
		if(((SellProjectInfo)params.get("sellProject")).get("id")!=null){
			sql.append("and ccts.fsellprojectid = '"+((SellProjectInfo)params.get("sellProject")).get("id")+"' ");
		}
		if(params.get("beginDate")!=null){
    		Date beginDate = (Date)params.get("beginDate");
    		sql.append("and ccts.ftrackDate >= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(beginDate))+ "'} ");
		}
		if(params.get("endDate")!=null){
			Date endDate = (Date)params.get("endDate");
			sql.append("and ccts.ftrackDate < {ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'} ");
		}
		sql.append("group by ccts.fcommerceChanceid )asps on cct.Fcommercechanceid = asps.fcommerceChanceid and cct.Ftrackdate = asps.ftrackdate ");
		sql.append("left outer join t_she_commerceChance cc on cct.Fcommercechanceid = cc.Fid ");
		sql.append(")t_she_visit on t_she_visit.classifyid = T_MAR_ChannelType.fid ");
		sql.append("union all ");
    	return sql.toString();
    }
    
    //来电转来访
    private String telToVisitSql(Map params){
		StringBuilder sql = new StringBuilder();
		sql.append("select T_MAR_ChannelType.Fname_L2 channelName,T_MAR_ChannelType.fid channelTypeId,T_MAR_ChannelTypeTree.fname_l2 treeName, ");
		sql.append("'' visitCommerceId,'' telCommerceId,t_she_tel2visit.tel2visitCommerceId tel2visitCommerceId,'' signManage ");
		sql.append("from T_MAR_ChannelTypeTree left outer join T_MAR_ChannelType on T_MAR_ChannelTypeTree.fid = T_MAR_ChannelType.Ftreeid ");
		sql.append("and T_MAR_ChannelType.Fcontrolunitid = '"+params.get("CU")+"' ");
		sql.append("left outer join t_she_commerceChanceTrack on T_MAR_ChannelType.fid = t_she_commerceChanceTrack.Fclassifyid ");
		sql.append("inner join ( ");
//		sql.append("select cct.fcommerceChanceid tel2visitCommerceId,cc.fclassifyid classifyid from t_she_commerceChanceTrack cct ");
//		sql.append("left outer join t_she_commerceChance cc on cct.Fcommercechanceid = cc.Fid where ");
////		sql.append("cct.fcommerceChanceid is not null and ");
//		sql.append("cct.finteractionType='INTERVIEW' and (select count(*) from t_she_commerceChanceTrack cct1 where cct1.finteractionType='TELEPHONE' and cct.fcommerceChanceid=cct1.fcommerceChanceid and cct.ftrackDate>cct1.ftrackDate)!=0 ");
//		if(((SellProjectInfo)params.get("sellProject")).get("id")!=null){
//			sql.append("and cct.fsellprojectid = '"+((SellProjectInfo)params.get("sellProject")).get("id")+"' ");
//		}
//		sql.append("group by cct.fcommerceChanceid,cc.fclassifyid having 1=1 ");
//		if(params.get("beginDate")!=null){
//    		Date beginDate = (Date)params.get("beginDate");
//    		sql.append("and min(cct.ftrackDate)>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(beginDate))+ "'} ");
//		}
//		if(params.get("endDate")!=null){
//			Date endDate = (Date)params.get("endDate");
//			sql.append("and min(cct.ftrackDate)<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'} ");
//		}
		//新逻辑，取来电最新的媒体
		sql.append("select tel.telCommerceId tel2visitCommerceId,tel.classifyid classifyid from (");
		sql.append("select cct.fcommerceChanceid telCommerceId,cct.fclassifyid classifyid,cc.fcustomerid telCustomerId from t_she_commerceChanceTrack cct ");
		sql.append("right outer join ( ");
		sql.append("select ccts.fcommerceChanceid fcommerceChanceid,max(ccts.ftrackdate) ftrackdate from t_she_commerceChanceTrack ccts where ccts.finteractionType = 'TELEPHONE' ");
		if(((SellProjectInfo)params.get("sellProject")).get("id")!=null){
			sql.append("and ccts.fsellprojectid = '"+((SellProjectInfo)params.get("sellProject")).get("id")+"' ");
		}
		if(params.get("beginDate")!=null){
    		Date beginDate = (Date)params.get("beginDate");
    		sql.append("and ccts.ftrackDate >= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(beginDate))+ "'} ");
		}
		if(params.get("endDate")!=null){
			Date endDate = (Date)params.get("endDate");
			sql.append("and ccts.ftrackDate < {ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'} ");
		}
		sql.append("group by ccts.fcommerceChanceid )asps on cct.Fcommercechanceid = asps.fcommerceChanceid and cct.Ftrackdate = asps.ftrackdate ");
		sql.append("left outer join t_she_commerceChance cc on cct.Fcommercechanceid = cc.Fid)tel ");
		
		sql.append("inner join ");
		
		sql.append("(select cct.fcommerceChanceid visitCommerceId,cct.fclassifyid classifyid,cc.fcustomerid visitCustomerId from t_she_commerceChanceTrack cct ");
		sql.append("right outer join ( ");
		sql.append("select ccts.fcommerceChanceid fcommerceChanceid,max(ccts.ftrackdate) ftrackdate from t_she_commerceChanceTrack ccts where ccts.finteractionType = 'INTERVIEW' ");
		if(((SellProjectInfo)params.get("sellProject")).get("id")!=null){
			sql.append("and ccts.fsellprojectid = '"+((SellProjectInfo)params.get("sellProject")).get("id")+"' ");
		}
		if(params.get("beginDate")!=null){
    		Date beginDate = (Date)params.get("beginDate");
    		sql.append("and ccts.ftrackDate >= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(beginDate))+ "'} ");
		}
		if(params.get("endDate")!=null){
			Date endDate = (Date)params.get("endDate");
			sql.append("and ccts.ftrackDate < {ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'} ");
		}
		sql.append("group by ccts.fcommerceChanceid )asps on cct.Fcommercechanceid = asps.fcommerceChanceid and cct.Ftrackdate = asps.ftrackdate ");
		sql.append("left outer join t_she_commerceChance cc on cct.Fcommercechanceid = cc.Fid)visit ");
		sql.append("on tel.telCommerceId = visit.visitCommerceId and tel.classifyid = visit.classifyid and tel.telCustomerId = visit.visitCustomerId ");
		
		sql.append(")t_she_tel2visit on t_she_tel2visit.classifyid = T_MAR_ChannelType.fid ");
//		sql.append("group by T_MAR_ChannelType.Fname_L2,T_MAR_ChannelType.fid,T_MAR_ChannelTypeTree.fname_l2,'','',t_she_tel2visit.tel2visitCommerceId,'' ");
		sql.append("union all ");
		return sql.toString();
	}     
                           
	//签约
    private String sign(Map params){
    	StringBuilder sql = new StringBuilder();
    	sql.append("select T_MAR_ChannelType.Fname_L2 channelName,T_MAR_ChannelType.fid channelTypeId,T_MAR_ChannelTypeTree.fname_l2 treeName, ");
    	sql.append("'' visitCommerceId,'' telCommerceId,'' tel2visitCommerceId,t_she_signMan.signCustomerid signManage from T_MAR_ChannelTypeTree ");
    	sql.append("left outer join T_MAR_ChannelType on T_MAR_ChannelTypeTree.fid = T_MAR_ChannelType.Ftreeid ");
    	sql.append("and T_MAR_ChannelType.Fcontrolunitid = '"+params.get("CU")+"' ");
    	sql.append("right outer join ( ");
    	//cct.fid signCustomerid
    	sql.append("select cct.fclassifyid classifyid,cc.fid signCommerceId,sc.fid signCustomerid,max(cct.ftrackdate) trackdate  from t_she_commerceChance cc ");
    	sql.append("left outer join t_she_shecustomer sc on cc.fcustomerid = sc.fid ");
    	sql.append("left outer join t_she_commerceChanceTrack cct on cc.fid = cct.fcommercechanceid and cct.FTrackEvent = 'Sign' and cct.fclassifyid is not null  ");
    	if(params.get("beginDate")!=null){
    		Date beginDate = (Date)params.get("beginDate");
    		sql.append("and cct.ftrackdate >={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(beginDate))+ "'} ");
		}
		if(params.get("endDate")!=null){
			Date endDate = (Date)params.get("endDate");
			sql.append("and cct.ftrackdate <{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'} ");
		}
    	
    	sql.append("where cc.fcustomerid in ( ");
    	sql.append("select sce.fcustomerid from t_she_signmanage sm left outer join T_SHE_SignCustomerEntry sce on sm.fid = sce.fheadid ");
    	sql.append("where sce.FIsMain = '1' and sm.fsellprojectid in ( ");
    	if(((SellProjectInfo)params.get("sellProject")).get("id")!=null){
			sql.append("select fid from T_SHE_sellproject where fid = '"+((SellProjectInfo)params.get("sellProject")).get("id")+"' or fparentid = '"+((SellProjectInfo)params.get("sellProject")).get("id")+"' ");
		}
    	//qianyue
    	sql.append(") and sm.fbizstate in ('SignAudit','SignApple') ");
    	if(params.get("beginDate")!=null){
    		Date beginDate = (Date)params.get("beginDate");
    		sql.append("and sm.fbizdate >={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(beginDate))+ "'} ");
		}
		if(params.get("endDate")!=null){
			Date endDate = (Date)params.get("endDate");
			sql.append("and sm.fbizdate <{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'} ");
		}
		//cct.fid
    	sql.append("and cct.fclassifyid is not null) group by cct.fclassifyid ,cc.fid , sc.fid ) t_she_signMan on t_she_signMan.classifyid = T_MAR_ChannelType.fid ");
    	return sql.toString();
    	
    }

    //获取合同ID
    private String getContract(Map params){
    	StringBuffer sql = new StringBuffer();
    	sql.append("select * from ( ");
    	sql.append("(select ese.fcontractnumberid contractnumberid, esp.FMediaNameID MediaNameID from T_MAR_EnterpriseSchemeEntry ese ");
    	sql.append("left outer join T_MAR_EnterpriseScheme es on ese.FParentID = es.fid ");
    	sql.append("left outer join T_MAR_EnterpriseSellPlan esp on ese.fsellplanid = esp.fid  ");
    	sql.append("left outer join T_ORG_BaseUnit bu on bu.fid = es.forgunitid ");
    	if(params.get("longNumber")!=null){
			sql.append("and bu.fnumber like '"+params.get("longNumber")+"%' ");
		}
    	sql.append("group by ese.fcontractnumberid, esp.FMediaNameID ) t_contract ");
    	sql.append("left outer join ");
    	sql.append("( select pb.factpaylocamt actpaylocamt,pb.fcontractbillid contractbillid from T_CAS_PaymentBill pb where pb.fbillStatus = '15' ) ");
    	sql.append("t_payment on t_payment.contractbillid = t_contract.contractnumberid ");
    	//合同金额 有文本
    	sql.append("left outer join (select cb.fid fid,cb.famount contractAmount from T_CON_ContractBill cb ) t_contractBill ");
    	sql.append("on t_contractBill.fid = t_contract.contractnumberid ");
    	//合同金额 无文本
    	sql.append("left outer join (select cwt.fid fid,cwt.famount cwtAmount from T_CON_ContractWithoutText cwt ) t_ContractWithoutText ");
    	sql.append("on t_ContractWithoutText.fid = t_contract.contractnumberid ");
    	return sql.toString();
    }

    /**
    //最外层SQL
    private String starSql(){
    	StringBuffer sql = new StringBuffer();
    	sql.append("select t_all_value.treeName,t_all_value.channelName,t_all_value.channelTypeId,count(t_all_value.visitCommerceId),count(t_all_value.telCommerceId),count(t_all_value.tel2visitCommerceId), ");
    	sql.append("count(t_all_value.signCustomerTime) ");
    	sql.append("from ( ");
    	return sql.toString();
    }
    
	//最外层SQL
    private String endSql(){
    	StringBuffer sql = new StringBuffer();
    	sql.append(") t_all_value group by t_all_value.channelName,t_all_value.treeName,t_all_value.channelTypeId order by t_all_value.treeName,t_all_value.channelName asc");
    	return sql.toString();
    }
    
    //外层第二层SQL
    private String starSql_1(){
    	StringBuffer sql = new StringBuffer();
    	sql.append("select t_value_2.channelName channelName,t_value_2.treeName treeName,t_value_2.channelTypeId channelTypeId,t_value_2.visitCommerceId visitCommerceId,t_value_2.telCommerceId telCommerceId, ");
    	sql.append("t_value_2.tel2visitCommerceId tel2visitCommerceId,t_value_2.visitCustomerId visitCustomerId,t_value_2.telCustomerId telCustomerId,MIN(sm.fcreatetime) signCustomerTime ");
    	sql.append("from ( ");
    	return sql.toString();
    }
    
	//最外第二层SQL
    private String endSql_1(){
    	StringBuffer sql = new StringBuffer();
    	sql.append("where ");
    	sql.append("t_value_2.visitCommerceId is not null or t_value_2.telCommerceId is not null or t_value_2.tel2visitCommerceId is not null or ");
    	sql.append("t_value_2.visitCustomerId is not null or t_value_2.telCustomerId is not null ");
    	sql.append("group by t_value_2.channelName,t_value_2.treeName,t_value_2.channelTypeId,t_value_2.visitCommerceId,t_value_2.telCommerceId,t_value_2.tel2visitCommerceId, ");
    	sql.append("t_value_2.visitCustomerId,t_value_2.telCustomerId,sm.fcreatetime ");
    	return sql.toString();
    }
    
	//外层第三层SQL
    private String starSql_2(){
    	StringBuffer sql = new StringBuffer();
    	sql.append("select t_value_1.channelName,t_value_1.channelTypeId,t_value_1.treeName,t_value_1.visitCommerceId,t_value_1.telCommerceId,t_value_1.tel2visitCommerceId,t_value_1.visitCustomerId,t_value_1.telCustomerId ");
    	sql.append("from ( ");
    	return sql.toString();
    }

	//最外第三层SQL
    private String endSql_2(){
    	StringBuffer sql = new StringBuffer();
    	sql.append(")t_value_2 ");
    	sql.append("left outer join T_SHE_SignCustomerEntry sce_1 on sce_1.Fcustomerid = t_value_2.visitCustomerId or sce_1.Fcustomerid = t_value_2.telCustomerId ");
    	sql.append("left outer join t_she_signmanage sm on sm.fid = sce_1.fheadid and sm.fbizstate = 'SignAudit' ");
    	return sql.toString();
    }

    //获取该项目所有媒体
    private String classifyStarSql(){
    	StringBuffer sql = new StringBuffer();
    	sql.append("select T_MAR_ChannelType.Fname_L2 channelName,T_MAR_ChannelType.fid channelTypeId,T_MAR_ChannelTypeTree.fname_l2 treeName,t_she_visit.visitCommerceId visitCommerceId, ");
    	sql.append("t_she_tel.telCommerceId telCommerceId,t_she_tel2visit.tel2visitCommerceId tel2visitCommerceId,t_she_visit.visitCustomerId visitCustomerId,t_she_tel.telCustomerId telCustomerId ");
    	sql.append("from T_MAR_ChannelTypeTree ");
    	sql.append("left outer join T_MAR_ChannelType on T_MAR_ChannelTypeTree.fid = T_MAR_ChannelType.Ftreeid ");
    	sql.append("left outer join t_she_commerceChanceTrack on T_MAR_ChannelType.fid = t_she_commerceChanceTrack.Fclassifyid ");
    	sql.append("left outer join ( ");
    	return sql.toString();
    }
    
    //来电
    private String telSql(Map params){
		StringBuilder sql = new StringBuilder();
		sql.append("select cct.fcommerceChanceid telCommerceId,cct.fid cctid,cc.fcustomerid telCustomerId from t_she_commerceChanceTrack cct ");
		sql.append("left outer join t_she_commerceChance cc on cct.Fcommercechanceid = cc.Fid where cct.fcommerceChanceid is not null ");
		sql.append(" and cct.finteractionType='TELEPHONE' and (cct.ftrackDate <(select min(cct1.ftrackDate) from t_she_commerceChanceTrack cct1 where cct1.finteractionType='INTERVIEW' ");
		sql.append(" and cct.fcommerceChanceid=cct1.fcommerceChanceid group by cct1.fcommerceChanceid ) or (select count(*) from t_she_commerceChanceTrack cct1 where cct1.finteractionType='INTERVIEW' and cct.fcommerceChanceid=cct1.fcommerceChanceid)=0) ");
		if(((SellProjectInfo)params.get("sellProject")).get("id")!=null){
			sql.append(" and cct.fsellprojectid = '"+((SellProjectInfo)params.get("sellProject")).get("id")+"' ");
		}
		sql.append(" group by cct.fcommerceChanceid, cct.fid , cc.fcustomerid having 1=1 ");
//    	if(params.get("beginDate")!=null){
//    		sql.append(" and min(cct.FTrackDate) >= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin((Date) params.get("beginDate")))+ "'} ");
//		}
//		if(params.get("endDate")!=null){
//			sql.append(" and min(cct.FTrackDate) <{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd((Date) params.get("endDate")))+ "'} ");
//		}
		if(params.get("beginDate")!=null){
    		String beginDate = params.get("beginDate").toString();
    		beginDate = beginDate.replace("-", "");
    		sql.append(" and to_char(min(cct.FTrackDate),'yyyymmdd') >= "+beginDate+" ");
		}
		if(params.get("endDate")!=null){
			String endDate = params.get("endDate").toString();
			endDate = endDate.replace("-", "");
			sql.append(" and to_char(min(cct.FTrackDate),'yyyymmdd') <= "+endDate+" ");
		}
		sql.append(" )t_she_tel on t_she_tel.cctid = t_she_commerceChanceTrack.Fid left outer join ( ");
		return sql.toString();
	}
    
    //来访
    private String visitSql(Map params){
    	StringBuilder sql = new StringBuilder();
		sql.append(" select cct.fcommerceChanceid visitCommerceId,cct.fid cctid,cc.fcustomerid visitCustomerId from t_she_commerceChanceTrack cct ");
		sql.append(" left outer join t_she_commerceChance cc on cct.Fcommercechanceid = cc.Fid where cct.fcommerceChanceid is not null");
    	sql.append(" and cct.finteractionType='INTERVIEW'");
    	if(((SellProjectInfo)params.get("sellProject")).get("id")!=null){
			sql.append(" and cct.fsellprojectid = '"+((SellProjectInfo)params.get("sellProject")).get("id")+"' ");
		}
		sql.append(" group by cct.fcommerceChanceid, cct.fid, cc.fcustomerid having 1=1 ");
//    	if(params.get("beginDate")!=null){
//			sql.append(" and min(cct.FTrackDate) >= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin((Date) params.get("beginDate")))+ "'} ");
//		}
//		if(params.get("endDate")!=null){
//			sql.append(" and min(cct.FTrackDate) <{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd((Date) params.get("endDate")))+ "'}");
//		}
		if(params.get("beginDate")!=null){
    		String beginDate = params.get("beginDate").toString();
    		beginDate = beginDate.replace("-", "");
    		sql.append(" and to_char(min(cct.FTrackDate),'yyyymmdd') >= "+beginDate+" ");
		}
		if(params.get("endDate")!=null){
			String endDate = params.get("endDate").toString();
			endDate = endDate.replace("-", "");
			sql.append(" and to_char(min(cct.FTrackDate),'yyyymmdd') <= "+endDate+" ");
		}
		sql.append(" )t_she_visit on t_she_visit.cctid = t_she_commerceChanceTrack.Fid left outer join ( ");
    	return sql.toString();
    }
    
    private String telToVisitSql(Map params){
		StringBuilder sql = new StringBuilder();
		sql.append(" select cct.fcommerceChanceid tel2visitCommerceId,cct.fid cctid from t_she_commerceChanceTrack cct where cct.fcommerceChanceid is not null");
		sql.append(" and cct.finteractionType='INTERVIEW' and (select count(*) from t_she_commerceChanceTrack cct1 where cct1.finteractionType='TELEPHONE' and cct.fcommerceChanceid=cct1.fcommerceChanceid and cct.ftrackDate>cct1.ftrackDate)!=0");
		if(((SellProjectInfo)params.get("sellProject")).get("id")!=null){
			sql.append(" and cct.fsellprojectid = '"+((SellProjectInfo)params.get("sellProject")).get("id")+"' ");
		}
		sql.append(" group by cct.fcommerceChanceid,cct.fid having 1=1");
    	if(params.get("beginDate")!=null){
    		String beginDate = params.get("beginDate").toString();
    		beginDate = beginDate.replace("-", "");
    		sql.append(" and to_char(min(cct.FTrackDate),'yyyymmdd') >= "+beginDate+" ");
		}
		if(params.get("endDate")!=null){
			String endDate = params.get("endDate").toString();
			endDate = endDate.replace("-", "");
			sql.append(" and to_char(min(cct.FTrackDate),'yyyymmdd') <= "+endDate+" ");
		}
		sql.append(" )t_she_tel2visit on t_she_tel2visit.cctid = t_she_commerceChanceTrack.Fid ");
		return sql.toString();
	}
	
    //获取该项目所有媒体
    private String classifyEndSql(){
    	StringBuffer sql = new StringBuffer();
    	sql.append(")t_value_1 ");
    	sql.append("group by t_value_1.channelName,t_value_1.channelTypeId,t_value_1.treeName,t_value_1.visitCommerceId,t_value_1.telCommerceId,t_value_1.tel2visitCommerceId,t_value_1.visitCustomerId,t_value_1.telCustomerId ");
    	return sql.toString();
    }

    //获取合同ID
    private String getContract(Map params){
    	StringBuffer sql = new StringBuffer();
    	sql.append(") tt left outer join ( ");
    	sql.append("select ese.fcontractnumberid,esp.FMediaNameID from T_MAR_EnterpriseSchemeEntry ese ");
    	sql.append("left outer join T_MAR_EnterpriseScheme es on ese.FParentID = es.fid  ");
    	sql.append("left outer join T_MAR_EnterpriseSellPlan esp on ese.fsellplanid = esp.fid ");
    	if(((SellProjectInfo)params.get("sellProject")).get("id")!=null){
			sql.append("where es.FSellProjectID = '"+((SellProjectInfo)params.get("sellProject")).get("id")+"' ");
		}
    	sql.append("group by ese.fcontractnumberid,esp.FMediaNameID ) t_contract on tt.channelTypeId = t_contract.FMediaNameID ");
    	return sql.toString();
    }
    **/
}