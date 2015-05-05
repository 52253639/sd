package com.kingdee.eas.fdc.basedata;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.db.TempTablePool;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.CostSplitInfo;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 生成拆分汇总数据，从明细工程项目的明细科目一直到伤及科目上级项目上级组织
 *  by sxhong 2008-10-29 17:01:18
 *  
 *  为了保证性能，数据在组织上只汇总到实体财务组织即工程项目的fullOrgunit，若上级组织要去数据请从公司汇总
 *  by sxhong 2009-07-02 22:14:28
 * @author xiaohong_shi
 *
 */
public class FDCCostSplitAppHelper {
	public static void collectCostSplit(Context ctx,CostSplitBillTypeEnum costSplitBillType, Set splitIds) throws BOSException, EASBizException {
		//存储共用的表名，父ID等
		Map map=new HashMap();
//		Connection conn=null;
		try {
//			conn=EJBFactory.getConnection(ctx);
			TempTablePool pool=TempTablePool.getInstance(ctx);
			map.put("TempTablePool", pool);
//			map.put("Connection", conn);
			map.put("ParentIdMap", new HashMap());
			map.put("TempTableMap", new HashMap());
			map.put("CostSplitBillTypeEnum", costSplitBillType);
			map.put("SplitIds", splitIds);
			initTmpTable(ctx,map);
			createCostSplit(ctx,map);
			createCostSplitEntry(ctx,map);

		}catch(Exception e){
			throw new BOSException(e);
		}finally{
			try {
				releaseTableData(map);
			} catch (SQLException e) {
				new BOSException(e);
			}
//			SQLUtils.cleanup(conn);
		}
	}
	
	private static void initTmpTable(Context ctx,Map map) throws Exception {
		TempTablePool pool=getPool(map);
		Map tempTableMap=getTempTableMap(map);
		Map paretMap=getParentIdMap(map);
//		Connection cn=getConnection(map);
		Set splitIds=(Set)map.get("SplitIds");
		CostSplitBillTypeEnum costSplitBillType=(CostSplitBillTypeEnum)map.get("CostSplitBillTypeEnum");
		
		String t_my2_costsplitentry="Create Table T_My2_CostSplitEntry (FID VARCHAR(44) ,FParentID VARCHAR(44),FCostAccountID VARCHAR(44),FIsProduct INT,FProductID VARCHAR(44),FAmount NUMERIC(28,10) DEFAULT 0,FProdAmount NUMERIC(28,10) DEFAULT 0,FObjectId VARCHAR(44) ,FPaidAmount NUMERIC(19,4) DEFAULT 0,FProdPaidAmount NUMERIC(19,4) DEFAULT 0,fprjId varchar(44),forgId varchar(44),fprjNumber nvarchar(100),facctNumber nvarchar(100),forgNumber nvarchar(300))";
		String t_my2_prj_costsplitentry="Create Table T_My2_prj_CostSplitEntry ( FID VARCHAR(44) ,FParentID VARCHAR(44),FCostAccountID VARCHAR(44),FIsProduct INT,FProductID VARCHAR(44),FAmount NUMERIC(28,10) DEFAULT 0,FProdAmount NUMERIC(28,10) DEFAULT 0,FObjectId VARCHAR(44) ,FPaidAmount NUMERIC(19,4) DEFAULT 0,FProdPaidAmount NUMERIC(19,4) DEFAULT 0,fprjId varchar(44),forgId varchar(44),fprjNumber nvarchar(100),facctNumber nvarchar(100),forgNumber nvarchar(300))";
		String t_my2_org_costsplitentry="Create Table T_My2_org_CostSplitEntry ( FID VARCHAR(44) ,FParentID VARCHAR(44),FCostAccountID VARCHAR(44),FIsProduct INT,FProductID VARCHAR(44),FAmount NUMERIC(28,10) DEFAULT 0,FProdAmount NUMERIC(28,10) DEFAULT 0,FObjectId VARCHAR(44) ,FPaidAmount NUMERIC(19,4) DEFAULT 0,FProdPaidAmount NUMERIC(19,4) DEFAULT 0,fprjId varchar(44),forgId varchar(44),fprjNumber nvarchar(100),facctNumber nvarchar(100),forgNumber nvarchar(300))";
		String t_my2_parentprj="Create Table T_My2_ParentPrj ( FID VARCHAR(44) ,FParentID VARCHAR(44),FFullOrgUnit Varchar(44) ,FNumber VARCHAR(80),FLongNumber varchar(100))";
		String t_my2_parentorg="Create Table T_My2_ParentOrg ( FID VARCHAR(44) ,FParentID VARCHAR(44),FNumber VARCHAR(80),FLongNumber varchar(300))";
		t_my2_costsplitentry=pool.createTempTable(t_my2_costsplitentry);
		t_my2_prj_costsplitentry=pool.createTempTable(t_my2_prj_costsplitentry);
		t_my2_org_costsplitentry=pool.createTempTable(t_my2_org_costsplitentry);
		t_my2_parentprj=pool.createTempTable(t_my2_parentprj);
		t_my2_parentorg=pool.createTempTable(t_my2_parentorg);
		
		tempTableMap.put("t_my2_costsplitentry", t_my2_costsplitentry);
		tempTableMap.put("t_my2_prj_costsplitentry", t_my2_prj_costsplitentry);
		tempTableMap.put("t_my2_org_costsplitentry", t_my2_org_costsplitentry);
		tempTableMap.put("t_my2_parentprj", t_my2_parentprj);
		tempTableMap.put("t_my2_parentorg", t_my2_parentorg);
//		if(true){
//			tempTableMap.put("t_my2_costsplitentry", "t_my2_costsplitentry");
//			tempTableMap.put("t_my2_prj_costsplitentry", "t_my2_prj_costsplitentry");
//			tempTableMap.put("t_my2_org_costsplitentry", "t_my2_org_costsplitentry");
//			tempTableMap.put("t_my2_parentprj", "t_my2_parentprj");
//			tempTableMap.put("t_my2_parentorg", "t_my2_parentorg");
//		}
		
		for(Iterator iter=splitIds.iterator();iter.hasNext();){
			String splitId=(String)iter.next();
			paretMap.put(splitId, createCostSplitId());
		}
		
		String splitEntryTable=null;
		if(costSplitBillType==CostSplitBillTypeEnum.CONTRACTSPLIT){
			splitEntryTable="T_Con_ContractCostSplitEntry";
		}else if(costSplitBillType==CostSplitBillTypeEnum.CNTRCHANGESPLIT){
			splitEntryTable="T_CON_ConChangeSplitEntry";
		}else if(costSplitBillType==CostSplitBillTypeEnum.SETTLEMENTSPLIT){
			splitEntryTable="T_CON_SettlementCostSplitEntry";
		}else if(costSplitBillType==CostSplitBillTypeEnum.NOTEXTCONSPLIT){
			splitEntryTable="T_FNC_PaymentSplitEntry";
		}else if(costSplitBillType==CostSplitBillTypeEnum.PAYMENTSPLIT){
			splitEntryTable="T_FNC_PaymentSplitEntry";
		}else{
			throw new NullPointerException("costSplitBillType error");
		}
		
		map.put("splitEntryTable", splitEntryTable);
	}
	

	private  static  void createCostSplit(Context ctx,Map map) throws BOSException,SQLException{
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		Map paretMap=getParentIdMap(map);
//		Connection cn=getConnection(map);
		Set splitIds=(Set)map.get("SplitIds");
		String []splits=new String[splitIds.size()];
		splitIds.toArray(splits);
		CostSplitBillTypeEnum costSplitBillType=(CostSplitBillTypeEnum)map.get("CostSplitBillTypeEnum");
		builder.appendSql("");
		String selectSql="";
		if(costSplitBillType==CostSplitBillTypeEnum.CONTRACTSPLIT){
			selectSql="select fid,fcontrolunitid,fcontractbillid as fcostbillid from T_Con_ContractCostSplit where ";
		}else if(costSplitBillType==CostSplitBillTypeEnum.CNTRCHANGESPLIT){
			selectSql="select fid,fcontrolunitid,fcontractchangeid as fcostbillid from T_CON_ConChangeSplit where ";
		}else if(costSplitBillType==CostSplitBillTypeEnum.SETTLEMENTSPLIT){
			selectSql="select fid,fcontrolunitid,fsettlementbillid as fcostbillid from t_Con_Settlementcostsplit where ";
		}else if(costSplitBillType==CostSplitBillTypeEnum.NOTEXTCONSPLIT){
			selectSql="select fid,fcontrolunitid,fpaymentbillid as fcostbillid from t_Fnc_Paymentsplit where ";
		}else if(costSplitBillType==CostSplitBillTypeEnum.PAYMENTSPLIT){
			selectSql="select fid,fcontrolunitid,case when fisworkloadbill=1 then fworkloadbillid else fpaymentbillid end as fcostbillid from t_Fnc_Paymentsplit where ";
		}
		builder.appendSql(selectSql);
		builder.appendParam("fid", splits);
		IRowSet rowSet=builder.executeQuery();
		List sqlParam=new ArrayList();
		while(rowSet.next()){
			String fid=rowSet.getString("fid");
			String fcontrolunitid=rowSet.getString("fcontrolunitid");
			String fcostbillid=rowSet.getString("fcostbillid");
			sqlParam.add(Arrays.asList(new Object[]{paretMap.get(fid),costSplitBillType.getValue(),fcostbillid,fid,Boolean.FALSE,fcontrolunitid}));
		}
		String t_aim_costsplit="t_aim_costsplit";
//		t_aim_costsplit="t_temp_costsplit";//debug table
		String sql="insert into "+t_aim_costsplit+" (fid,fcostbilltype,fcostbillid,fsplitbillid,fisinvalid,fcontrolunitid) values(?,?,?,?,?,?)";
		builder.executeBatch(sql, sqlParam);
	}
	/**
	 * @param ctx
	 * @param map
	 * @throws BOSException
	 */
	private static void createCostSplitEntry(Context ctx,Map map) throws BOSException {
		List sqlList=new ArrayList();
		//从拆分取得基础数据
		createDetailData(ctx,map);
		//生成上级科目数据
		List tmpList=createAcctData(ctx,map);
		sqlList.addAll(tmpList);
		//补全数据及准备基础数据
		tmpList=prepareBaseData(ctx, map);
		sqlList.addAll(tmpList);
		
		//生成上级项目数据
		tmpList=createPrjData(ctx, map);
		sqlList.addAll(tmpList);
		
		//生成上级组织数据
		tmpList=createOrgData(ctx, map);
		sqlList.addAll(tmpList);
		
		//数据合并后插入到costSplitEntry
		tmpList=mergeData(ctx, map);
		sqlList.addAll(tmpList);
		
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
//		if(true){
//			int i=0;
//			for(Iterator iter=sqlList.iterator();iter.hasNext();i++){
//				System.out.println("sql"+i+"\n"+iter.next());
//			}
//		}
		builder.executeBatch(sqlList);
		
	}
	
	private static void createDetailData(Context ctx,Map map) throws BOSException {
		CostSplitBillTypeEnum costSplitBillType=(CostSplitBillTypeEnum)map.get("CostSplitBillTypeEnum");
		String t_my2_costsplitentry=(String)getTempTableMap(map).get("t_my2_costsplitentry");
		Set splitIds=(Set)map.get("SplitIds");
		String []splits=new String[splitIds.size()];
		splitIds.toArray(splits);
		String splitEntryTable=(String)map.get("splitEntryTable");
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		
		if(costSplitBillType==CostSplitBillTypeEnum.NOTEXTCONSPLIT||costSplitBillType==CostSplitBillTypeEnum.PAYMENTSPLIT){
			//合同、变更、结算
			//所有没有产品的明细
			builder.appendSql("insert into "+ t_my2_costsplitentry +" (fid,fparentid,fcostaccountid,fisproduct,fproductid,famount,fprodamount,fobjectid,fpaidamount,fprodpaidamount)  \n");
			builder.appendSql("( \n");
			builder.appendSql("select '' fid,fparentid,fcostaccountid,0 as fisproduct,null as fproductid,sum(famount) as famount,0 as fprodamount,null as fobjectid,sum(fpayedAmt) fpaidamount,0 fprodpaidamount  \n");
			builder.appendSql("from "+splitEntryTable+" entry where \n");
			builder.appendParam("fparentid", splits);
			builder.appendSql(" and ((entry.fisleaf=1 and fproductid is null)   \n");
			builder.appendSql("or (entry.fisleaf=0 and fproductid is null and fsplitType='PRODSPLIT')  \n");
			builder.appendSql("or(entry.fisleaf=1 and fproductid is not null and (fsplitType<>'PRODSPLIT' or fsplittype is null)) \n");
			builder.appendSql(")  \n");
			builder.appendSql("group by fparentid,fcostaccountid) \n");
			builder.execute();
			//产品数据
			builder.clear();
			builder.appendSql("insert into "+ t_my2_costsplitentry +" (fid,fparentid,fcostaccountid,fisproduct,fproductid,famount,fprodamount,fobjectid,fpaidamount,fprodpaidamount)  \n");
			builder.appendSql("(select '' fid,fparentid,fcostaccountid,1 fisproduct,fproductid,0 famount,sum(famount) fprodamount,null fobjectid,0 fpaidamount,sum(fpayedAmt) fprodpaidamount  \n");
			builder.appendSql("from "+splitEntryTable+" entry where \n");
			builder.appendParam(" fparentid", splits);
			builder.appendSql("and ((entry.fisleaf=1 and fproductid is not null)  \n");
			builder.appendSql("or(entry.fisleaf=1 and fproductid is not null)) \n");
			builder.appendSql("group by fparentid,fcostaccountid,fproductid) \n");
			builder.execute();
			
		}else{
			//合同、变更、结算
			//所有没有产品的明细
			builder.appendSql("insert into "+ t_my2_costsplitentry +" (fid,fparentid,fcostaccountid,fisproduct,fproductid,famount,fprodamount,fobjectid,fpaidamount,fprodpaidamount)  \n");
			builder.appendSql("( \n");
			builder.appendSql("select '' fid,fparentid,fcostaccountid,0 as fisproduct,null as fproductid,sum(famount) as famount,0 as fprodamount,null as fobjectid,0 fpaidamount,0 fprodpaidamount  \n");
			builder.appendSql("from "+splitEntryTable+" entry where \n");
			builder.appendParam("fparentid", splits);
			builder.appendSql(" and ((entry.fisleaf=1 and fproductid is null)   \n");
			builder.appendSql("or (entry.fisleaf=0 and fproductid is null and fsplitType='PRODSPLIT')  \n");
			builder.appendSql("or(entry.fisleaf=1 and fproductid is not null and (fsplitType<>'PRODSPLIT' or fsplittype is null)) \n");
			builder.appendSql(")  \n");
			builder.appendSql("group by fparentid,fcostaccountid) \n");
			builder.execute();
			//产品数据
			builder.clear();
			builder.appendSql("insert into "+ t_my2_costsplitentry +" (fid,fparentid,fcostaccountid,fisproduct,fproductid,famount,fprodamount,fobjectid,fpaidamount,fprodpaidamount)  \n");
			builder.appendSql("(select '' fid,fparentid,fcostaccountid,1 fisproduct,fproductid,0 famount,sum(famount) fprodamount,null fobjectid,0 fpaidamount,0 fprodpaidamount  \n");
			builder.appendSql("from "+splitEntryTable+" entry where \n");
			builder.appendParam("fparentid", splits);
			builder.appendSql(" and ((entry.fisleaf=1 and fproductid is not null)  \n");
			builder.appendSql("or(entry.fisleaf=1 and fproductid is not null)) \n");
			builder.appendSql("group by fparentid,fcostaccountid,fproductid) \n");
			builder.execute();
		}

	}
	
	/**
	 * 生成明细项目的上级科目数据
	 * @param ctx
	 * @param map
	 * @return 返回sql集合
	 */
	private static List createAcctData(Context ctx,Map map) {
		Map tempTableMap=getTempTableMap(map);
		List sqlList=new ArrayList();
		StringBuffer sb=new StringBuffer();
		
		sb.append("update t_my2_costsplitentry set (facctNumber,fobjectId)=(select flongnumber,fcurProject from T_FDC_CostAccount acct where acct.fid=t_my2_costsplitentry.fcostaccountid)");
		String str=sb.toString();
		str=replaceTable(str, map);
		sqlList.add(str);
		sb.setLength(0);
		
		//
		//生成明细项目的上级科目数据
		sb.append("insert into t_my2_costsplitentry  (fid,fparentid,fcostaccountid,fisproduct,fproductid,famount,fprodamount,fobjectid,fpaidamount,fprodpaidamount,facctNumber)  \n");
		sb.append("(select '' as fid, entry.fparentid as fparentid,acct.fid as fcostaccountid,entry.fisproduct as fisproduct,entry.fproductid as fproductid, \n");
		sb.append("sum(entry.famount) as famount,sum(entry.fprodamount) as fprodamount,entry.fobjectid as fobjectid,sum(entry.fpaidamount) as fpaidamount, \n");
		sb.append("sum(entry.fprodpaidamount) as fprodpaidamount ,acct.flongnumber as facctNumber  \n");
		sb.append("from t_my2_costsplitentry  entry  \n");
		sb.append("inner join T_FDC_CostAccount acct on acct.fcurproject=entry.fobjectid \n");
		sb.append("where charindex(acct.flongnumber||'!',entry.facctNumber)=1  \n");
		sb.append(" group by entry.fparentid,entry.fobjectid,acct.fid,acct.flongnumber,entry.fproductid,entry.fisproduct \n");
		sb.append(")   \n");
		
		str=sb.toString();
		str=replaceTable(str, map);
		sqlList.add(str);
		return sqlList;
	}
	
	/**
	 * 基础数据准备,补全等
	 * @param ctx
	 * @param map
	 * @return
	 */
	private static List prepareBaseData(Context ctx,Map map) {
		Map tempTableMap=getTempTableMap(map);
		Map parentMap=getParentIdMap(map);
		String t_my2_costsplitentry=(String)tempTableMap.get("t_my2_costsplitentry");
		String t_my2_parentprj=(String)tempTableMap.get("t_my2_parentprj");
		String t_my2_parentorg=(String)tempTableMap.get("t_my2_parentorg");
		List sqlList=new ArrayList();
		sqlList.add("update "+ t_my2_costsplitentry +" set fprjNumber=(select flongnumber from T_FDC_CurProject where fid="+t_my2_costsplitentry+".fobjectid)");
		sqlList.add("update "+ t_my2_costsplitentry +" set forgNumber=(select org.flongnumber from T_org_baseunit org inner join T_FDC_CurProject prj on prj.ffullorgunit=org.fid where prj.fid="+t_my2_costsplitentry+".fobjectid)");
		sqlList.add("update "+ t_my2_costsplitentry +" set fprjId=fobjectid");
		sqlList.add("update "+ t_my2_costsplitentry +" set forgId=(select org.fid from T_org_baseunit org inner join T_FDC_CurProject prj on prj.ffullorgunit=org.fid where prj.fid="+t_my2_costsplitentry+".fobjectid)");
		for(Iterator iter=parentMap.keySet().iterator();iter.hasNext();){
			String key=(String)iter.next();
			sqlList.add("update "+ t_my2_costsplitentry +" set fparentid='"+parentMap.get(key)+"' where fparentid='"+key+"'");
		}
		StringBuffer sb=new StringBuffer();
		//生成上级工程项目表
		sb.append("insert into "+t_my2_parentprj+" (FID,FParentID,FFullOrgUnit,FNumber,FLongNumber) \n"); 
		sb.append("(select distinct prj.fid,prj.fparentid,prj.ffullorgunit,prj.fnumber,prj.flongnumber from T_FDC_CurProject prj ,T_FDC_CurProject prj2 \n");
		sb.append("where prj.ffullorgunit=prj2.ffullorgunit and charindex(prj.flongnumber||'!',prj2.flongnumber)=1 \n");
		sb.append("and prj2.fid in (select distinct prj.fid from "+ t_my2_costsplitentry +" entry \n");
		sb.append("inner join T_FDC_CurProject prj on prj.fid=entry.fobjectid)) \n");
		sqlList.add(sb.toString());
		sb.setLength(0);
		//生成组织表,所有的上级组织//TODO 太细了，其实到工程项目的财务组织或者CU就好了
		sb.append("insert into "+t_my2_parentorg+" (FID,FParentID,FNumber,FLongNumber) \n"); 
		sb.append("(select distinct  org1.fid,org1.fparentId,org1.fnumber,org1.flongnumber  from T_Org_baseUnit org1 ,T_Org_baseUnit org2 \n"); 

//		为了保证性能，数据在组织上只汇总到实体财务组织即工程项目的fullOrgunit，若上级组织要去数据请从公司汇总  by sxhong 2009-07-02 22:15:21
//		sb.append("where (charindex(org1.flongnumber||'!',org2.flongnumber)=1 or org1.fid=org2.fid) \n");
		sb.append("where (org1.fid=org2.fid) \n");
		//因为下级工程项目的ffullorgunit 与上级同，所以直接用下级过滤就可以了,避免只有一级工程项目的时候导致组织丢失  by sxhong 2009-07-02 22:08:07
		sb.append("and org2.fid in (select distinct ffullorgunit from "+ t_my2_costsplitentry +" entry inner join T_FDC_CurProject prj on prj.fid=entry.fobjectid)) \n"); 
		
		

		sqlList.add(sb.toString());
		
		
		return sqlList;
	}
	
	/**
	 * 生成上级项目数据
	 * @param ctx
	 * @param map
	 * @return 返回sql集合
	 */
	private static List createPrjData(Context ctx,Map map) {
		List sqlList=new ArrayList();
		StringBuffer sb=new StringBuffer();
		sb.append("insert into t_my2_prj_costsplitentry (fid,fparentid,fcostaccountid,fisproduct,fproductid,famount,fprodamount,fobjectid,fpaidamount,fprodpaidamount,fprjnumber,facctnumber) \n");
		sb.append(" (select '' as fid, entry.fparentid as fparentid,'' as fcostaccountid,entry.fisproduct as fisproduct,entry.fproductid as fproductid, \n");
		sb.append("sum(entry.famount) as famount,sum(entry.fprodamount) as fprodamount,prj.fid as fobjectid,sum(entry.fpaidamount) as fpaidamount, \n");
		sb.append("sum(entry.fprodpaidamount) as fprodpaidamount,prj.flongnumber as fprjnumber,entry.facctnumber  \n");
		sb.append(" from t_my2_costsplitentry entry inner join t_my2_parentprj prj on charindex(prj.flongnumber||'!',entry.fprjnumber)=1 \n");
		sb.append(" group by entry.fparentid,prj.fid,prj.flongnumber,entry.facctnumber,entry.fproductid,entry.fisproduct) \n");
		String str=sb.toString();
		str=replaceTable(str, map);
		sqlList.add(str);

		//update sql
		sb.setLength(0);
		sb.append("update t_my2_prj_costsplitentry set fcostaccountid=(select fid from t_fdc_costaccount where fcurproject=t_my2_prj_costsplitentry.fobjectid and flongnumber=t_my2_prj_costsplitentry.facctnumber)");
		str=sb.toString();
		str=replaceTable(str, map);
		sqlList.add(str);
		
		return sqlList;
	}
	
	/**
	 * 生成上级组织数据
	 * @param ctx
	 * @param map
	 * @return 返回sql集合
	 */
	private static List createOrgData(Context ctx,Map map) {
		List sqlList=new ArrayList();
		
		StringBuffer sb=new StringBuffer();
		sb.append("insert into  t_my2_org_costsplitentry (fid,fparentid,fcostaccountid,fisproduct,fproductid,famount,fprodamount,fobjectid,fpaidamount,fprodpaidamount,fprjnumber,facctnumber) \n");
		sb.append(" (select '' as fid, entry.fparentid  as fparentid,'' as fcostaccountid,entry.fisproduct as fisproduct,entry.fproductid as fproductid, \n");
		sb.append("sum(entry.famount) as famount,sum(entry.fprodamount) as fprodamount,org.fid as fobjectid,sum(entry.fpaidamount) as fpaidamount, \n");
		sb.append("sum(entry.fprodpaidamount) as fprodpaidamount,org.flongnumber as forgnumber,entry.facctnumber  \n");
		sb.append(" from t_my2_costsplitentry entry inner join t_my2_parentorg org on (charindex(org.flongnumber||'!',entry.forgnumber)=1 or org.fid=entry.forgid) \n");
		sb.append(" group by entry.fparentid,org.fid,org.flongnumber,entry.facctnumber,entry.fproductid,entry.fisproduct) \n");
		String str=sb.toString();
		str=replaceTable(str, map);
		sqlList.add(str);
		
		//更新科目ID字
		sb.setLength(0);
		sb.append("update t_my2_org_costsplitentry set fcostaccountid=(select fid from t_fdc_costaccount where ffullorgunit=t_my2_org_costsplitentry.fobjectid and flongnumber=t_my2_org_costsplitentry.facctnumber and fcurproject is null); \n");
		str=sb.toString();
		str=replaceTable(str, map);
		sqlList.add(str);
		return sqlList;
	}
	
	private static List mergeData(Context ctx,Map map) {
		List sqlList=new ArrayList();
		StringBuffer sb=new StringBuffer();
		//合并明细数据
		sb.append(" insert into t_aim_costsplitentry (fid,fparentid,fcostaccountid,fisproduct,fproductid,famount,fprodamount,fobjectid,fpaidamount,fprodpaidamount) \n"); 
		sb.append(" (select newbosid('F70E723C') as fid,fparentid,fcostaccountid,fisproduct,fproductid,famount,fprodamount,fobjectid,fpaidamount,fprodpaidamount from t_my2_costsplitentry) \n");
		String str=sb.toString();
		str=replaceTable(str, map);
		sqlList.add(str);
		
		//合并上级项目数据
		sb.setLength(0);
		sb.append("insert into t_aim_costsplitentry (fid,fparentid,fcostaccountid,fisproduct,fproductid,famount,fprodamount,fobjectid,fpaidamount,fprodpaidamount)  \n");
		sb.append("(select newbosid('F70E723C') as fid ,fparentid,fcostaccountid,fisproduct,fproductid,famount,fprodamount,fobjectid,fpaidamount,fprodpaidamount from t_my2_prj_costsplitentry) \n");
		str=sb.toString();
		str=replaceTable(str, map);
		sqlList.add(str);
		//合并上级组织数据
		sb.setLength(0);
		sb.append("insert into t_aim_costsplitentry (fid,fparentid,fcostaccountid,fisproduct,fproductid,famount,fprodamount,fobjectid,fpaidamount,fprodpaidamount)  \n");
		sb.append("(select newbosid('F70E723C') as fid,fparentid,fcostaccountid,fisproduct,fproductid,famount,fprodamount,fobjectid,fpaidamount,fprodpaidamount from t_my2_org_costsplitentry) \n");
		str=sb.toString();
		str=replaceTable(str, map);
		sqlList.add(str);
		return sqlList;
	}
	
	/**
	 * 将临时表替换成真正的表
	 * @param sql
	 * @param map
	 * @return
	 */
	private static String replaceTable(String sql,Map map){
		Map tempTableMap=getTempTableMap(map);
		String t_my2_costsplitentry=(String)tempTableMap.get("t_my2_costsplitentry");
		String t_my2_parentprj=(String)tempTableMap.get("t_my2_parentprj");
		String t_my2_parentorg=(String)tempTableMap.get("t_my2_parentorg");
		String t_my2_prj_costsplitentry=(String)tempTableMap.get("t_my2_prj_costsplitentry");
		String t_my2_org_costsplitentry=(String)tempTableMap.get("t_my2_org_costsplitentry");
		String str=sql;
		str=str.replaceAll("t_my2_costsplitentry", t_my2_costsplitentry);
		str=str.replaceAll("t_my2_parentprj", t_my2_parentprj);
		str=str.replaceAll("t_my2_parentorg", t_my2_parentorg);
		str=str.replaceAll("t_my2_prj_costsplitentry", t_my2_prj_costsplitentry);
		str=str.replaceAll("t_my2_org_costsplitentry", t_my2_org_costsplitentry);
		
		//dedug model
//		str=str.replaceAll("t_aim_costsplitentry", "t_temp_costsplitentry");
		return str;
	}
	private static void releaseTableData(Map map) throws SQLException {
		TempTablePool pool=getPool(map);
		Map tempTableMap=getTempTableMap(map);
//		Connection conn=getConnection(map);
		if(pool==null){
			return;
		}
		for(Iterator iter=tempTableMap.values().iterator();iter.hasNext();){
			pool.releaseTable((String)iter.next());
		}
		map.clear();
	}
	
	private static TempTablePool getPool(Map map){
		TempTablePool pool=(TempTablePool)map.get("TempTablePool");
		return pool;
	}

	private static Connection getConnection(Map map){
		Connection conn=(Connection)map.get("Connection");
		return conn;
	}
	
	private static Map getTempTableMap(Map map){
		Map tempTableMap=(Map)map.get("TempTableMap");
		if(tempTableMap==null){
			tempTableMap=new HashMap();
		}
		return tempTableMap;
	}
	
	private static Map getParentIdMap(Map map){
		Map parentIdMap=(Map)map.get("ParentIdMap");
		if(parentIdMap==null){
			parentIdMap=new HashMap();
		}
		return parentIdMap;
	}
	
	private static BOSObjectType costSplitBosType=(new CostSplitInfo().getBOSType());
	private static String createCostSplitId(){
		return BOSUuid.create(costSplitBosType).toString();
	}
}
