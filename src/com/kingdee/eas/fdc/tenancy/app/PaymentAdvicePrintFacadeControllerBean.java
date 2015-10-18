package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;
import javax.ejb.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.engine.difftool.RS;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.ctrl.common.util.DBUtil;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fm.common.DateHelper;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PaymentAdvicePrintFacadeControllerBean extends AbstractPaymentAdvicePrintFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.PaymentAdvicePrintFacadeControllerBean");
    protected Map _getValue(Context ctx, Map param)throws BOSException
    {
    	Map result = new HashMap();
    	//预编译？代替集合
    	
    	//处理客户问题
    	FDCSQLBuilder zfsqlBuilder = new FDCSQLBuilder(ctx);
    	zfsqlBuilder.appendSql(" select  tenancyBill.fid as billID,tenancyCustomerEntry.ffdccustomerid as customerID ,fdccustomer.fname_l2 as customer ")
    	.appendSql("  from T_TEN_TenancyCustomerEntry tenancyCustomerEntry  ")
    	.appendSql("  join T_TEN_TenancyBill tenancyBill        on tenancyBill.fid = tenancyCustomerEntry.ftenancybillid ")
    	.appendSql("  left join T_SHE_FDCCustomer fdccustomer   on fdccustomer.fid = tenancyCustomerEntry.ffdccustomerid ")
    	.appendSql(" left join T_SHE_SellProject SellProject on  tenancyBill.FSellProjectID =SellProject.Fid ")
    	.appendSql(" where tenancyBill.forgunitid='"+ContextUtil.getCurrentOrgUnit(ctx).getId().toString()+"' and 1=1 ");
    	if(param.get("sellProject")!=null){
    		zfsqlBuilder.appendSql("  and SellProject.fname_l2 =? ");
    		zfsqlBuilder.addParam(param.get("sellProject"));
		}
    	if(param.get("sellProjectId")!=null){
			Set sellProject=(Set) param.get("sellProjectId");
			zfsqlBuilder.appendSql("  and SellProject.fid in "+FMHelper.setTran2String(sellProject));
//			sqlBuilder.addParam(param.get("sellProject"));
		}
    	if(param.containsKey("isAudit")&&param.get("isAudit").equals(Boolean.TRUE)){
    		zfsqlBuilder.appendSql("  and tenancyBill.FtenancyState in( 'Auditing','Audited','Executing','Submitted'," +
							"'PartExecuted','ContinueTenancying','RejiggerTenancying','ChangeNaming'," +
							"'TenancyChanging','QuitTenancying','Expiration')");
		}else{
			zfsqlBuilder.appendSql(" and tenancyBill.FtenancyState in('Audited','Executing'," +
							"'PartExecuted','ContinueTenancying','RejiggerTenancying','ChangeNaming'," +
							"'TenancyChanging','QuitTenancying','Expiration')");
		}
		logger.debug(zfsqlBuilder.getTestSql());	
		IRowSet zfrs =zfsqlBuilder.executeQuery();
		//合同与客户对应的关系集合
		List ccList = new ArrayList();
		try {
			Map cc=null; ;
			while(zfrs.next()){
				cc = new HashMap();
				String billId =zfrs.getString("billId");
				String customername =zfrs.getString("customer");
				cc.put("billId", billId);
				cc.put("customername", customername);
				ccList.add(cc);
			}
		} catch (SQLException e) {
			logger.debug(e);
		}
		
		
    	// 查询语句
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
    	sqlBuilder.appendSql(" select t0.id,t0.startdate,t0.enddate,t0.appdate,t0.appamount,t0.actamount,t0.tamount,t0.ramount,t0.aamount, ")
		.appendSql(" t0.contractname,t0.contractid,t0.roomname,t0.state,t0.projectname,t0.moneyname,t0.moneyid,t0.tarea,t0.spid ")
		//add by hh     t0.number,
		.appendSql(" ,isnull(t0.rate,0) as rate, ")
		.appendSql(" isnull(t0.appLiquidated,0) as appLiquidated, ")
		.appendSql(" isnull(t0.actLiquidated,0) as actLiquidated, ")
		.appendSql(" isnull(t0.reliefLiquidated,0) as reliefLiquidated, ")
		.appendSql(" isnull(t0.unPayLiquidated,0) as unPayLiquidated ,")
		.appendSql("t0.number as number")//合同编号
		
		.appendSql("  from( ")
		.appendSql(" 	select 'acterPay'||t1.fid  id,t1.FStartdate startdate,t1.fenddate enddate,t1.fappdate appdate,t1.fappamount appamount,t1.fActrevamount ")
		.appendSql(" 	actamount,t1.FHasTransferredAmount tamount,t1.FHasRefundmentAmount ramount,t1.FHasAdjustedAmount aamount, " )
		//add by hh
		.appendSql(" t4.fnumber as number,")//合同编号
		.appendSql(" lm.frate as rate, ")
		.appendSql(" lm.famount as appLiquidated, ")
		.appendSql(" tbop.factrevamount as actLiquidated, ")
		.appendSql(" lm.freliefamount as reliefLiquidated, ")
		.appendSql(" isnull(tbop.fappamount,0)-isnull(tbop.factrevamount,0) as unPayLiquidated, t5.fid as spid,")
		
		.appendSql(" t4.fname contractname,t4.fid contractid,t3.fname_l2 roomname,t4.FtenancyState state,t5.fname_l2 projectname ")
		.appendSql(" 	,t8.fname_l2 as moneyname,t8.fid as moneyid,t3.FTenancyArea tarea ")
		.appendSql(" 			from  T_TEN_TenancyRoomPayListEntry t1   ")
		.appendSql(" 				left join T_TEN_TenancyRoomEntry t2 on t1.ftenroomID =t2.FID ")
		.appendSql("				left join T_SHE_Room t3 on t2.froomid = t3.FID ")
		.appendSql("			     left join (	 ")
		.appendSql(" 				select tenancyBill.fnumber,tenancyBill.FID,tenancyBill.FSellProjectID, tenancyBill.fname ,tenancyBill.FtenancyState ")
		.appendSql(" 			 from T_TEN_TenancyBill tenancyBill  ")
		.appendSql("			 where tenancyBill.forgunitid='"+ContextUtil.getCurrentOrgUnit(ctx).getId().toString()+"' and tenancyBill.fid in (select  tenancyCustomerEntry.ftenancybillid ")
		.appendSql(" 			 from T_TEN_TenancyCustomerEntry tenancyCustomerEntry  ")
		.appendSql(" 			 where 1=1  ");
		//提交用户的过滤条件
		if(param.containsKey("customer")){
			if(param.get("customer") instanceof List){
				List customerList =(List)param.get("customer");
				sqlBuilder.appendParam(" and tenancyCustomerEntry.ffdccustomerid ", customerList.toArray());
			}
		}
		sqlBuilder.appendSql(" 	) ")
		.appendSql(" 		) t4 on t2.FTenancyID=t4.FID ")
		
		//add by hh
		.appendSql(" left join T_TEN_LiquidatedManage as lm ")
    	.appendSql(" on t1.FId = lm.FTenBillOtherPayID ")
    	.appendSql(" left join T_TEN_TenBillOtherPay as tbop ")
		.appendSql(" on tbop.FId = lm.FGenOtherPayID ")
		
		.appendSql(" 		left join T_SHE_SellProject t5 on t4.FSellProjectID =t5.FID ")
		.appendSql("				left join T_SHE_MoneyDefine t8 on t8.fid=t1.FMoneyDefineID ")
		.appendSql("	union all  ")
		.appendSql("		select 'otherPay'||t1.fid  id,t1.FStartdate startdate,t1.fenddate enddate,t1.fappdate appdate,t1.fappamount appamount,t1.fActrevamount ")
		.appendSql("			actamount,t1.FHasTransferredAmount tamount,t1.FHasRefundmentAmount ramount, t1.FHasAdjustedAmount aamount, " )
				//add by hh
		.appendSql(" t2.fnumber as number,")//合同编号
		.appendSql(" lm.frate as rate, ")
		.appendSql(" lm.famount as appLiquidated, ")
		.appendSql(" tbop.factrevamount as actLiquidated, ")
		.appendSql(" lm.freliefamount as reliefLiquidated, ")
		.appendSql(" isnull(tbop.fappamount,0)-isnull(tbop.factrevamount,0) as unPayLiquidated ,t2.FSellProjectID as spid,")
		
		.appendSql(" t2.fname  contractname,t2.fid contractid, t2.fname  roomname ")
		.appendSql("			,t2.FtenancyState state,t5.fname_l2 projectname,t8.fname_l2  moneyname,t8.fid as moneyid,-1 tarea ")
		
				
		.appendSql("				from T_TEN_TenBillOtherPay t1  ")
		.appendSql("				left join (  ")
		.appendSql("				select tenancyBill.fnumber,tenancyBill.FID,tenancyBill.FSellProjectID, tenancyBill.ftenRoomsDes fname ,tenancyBill.fstate,tenancyBill.FtenancyState  ")
		.appendSql("				 from T_TEN_TenancyBill tenancyBill  ")
		.appendSql("					where tenancyBill.forgunitid='"+ContextUtil.getCurrentOrgUnit(ctx).getId().toString()+"' and tenancyBill.fid in (select  tenancyCustomerEntry.ftenancybillid  ")
		.appendSql("				 from T_TEN_TenancyCustomerEntry tenancyCustomerEntry   ")
		
		.appendSql(" 			 where 1=1  ");
		//提交用户的过滤条件
		if(param.containsKey("customer")){
			if(param.get("customer") instanceof List){
				
				List customerList =(List)param.get("customer");
				sqlBuilder.appendParam(" and tenancyCustomerEntry.ffdccustomerid ", customerList.toArray());
			}
		}
		sqlBuilder.appendSql(" 	) ")
		.appendSql("				) t2 on t1.FHeadID=t2.FID   ")
		
				//add by hh
		.appendSql(" left join T_TEN_LiquidatedManage as lm ")
		.appendSql(" on t1.FId = lm.FTenBillOtherPayID ")
		.appendSql(" left join T_TEN_TenBillOtherPay as tbop ")
		.appendSql(" on tbop.FId = lm.FGenOtherPayID ")
		
		.appendSql("				left join T_SHE_SellProject t5 on t2.FSellProjectID =t5.FID   ")
		.appendSql("				left join T_SHE_MoneyDefine t8 on t8.fid=t1.FMoneyDefineID   ")
		.appendSql(") t0 ");
		
			if(param!=null){
				//依照过滤条件添加where子句
				sqlBuilder.appendSql(" where 1=1 ");
				if(param.get("sellProjectId")!=null){
					Set sellProject=(Set) param.get("sellProjectId");
					sqlBuilder.appendSql("  and t0.spid in "+FMHelper.setTran2String(sellProject));
//					sqlBuilder.addParam(param.get("sellProject"));
				}
				  if(param.get("sellProject")!=null){
						sqlBuilder.appendSql("  and t0.projectname =? ");
						sqlBuilder.addParam(param.get("sellProject"));
					}
				if(param.get("contract")!=null){
					sqlBuilder.appendSql("  and t0.contractname = ? " );
					sqlBuilder.addParam(param.get("contract"));
				}
				if(param.containsKey("dateFrom")&&param.get("dateFrom")!=null&&param.containsKey("dateTo")&&param.get("dateFrom")!=null){
					 
					 Date date1 = (Date)param.get("dateFrom");
					 Date date2 = (Date)param.get("dateTo");
					 String date1Str = "{ts '"+FDCDateHelper.DateToString(date1)+"'}" ;
					 String date2Str = "{ts '"+FDCDateHelper.DateToString(date2)+"'}" ; 
					 sqlBuilder.appendSql(" and appdate>=" +date1Str+" and appdate<= "+ date2Str);
					
					
				}
				//add by hh
				if(param.containsKey("isLack")&&param.get("isLack")!=null&&((Boolean)param.get("isLack")).booleanValue()){
					sqlBuilder.appendSql(" and isnull(t0.appamount,0) - (isnull(t0.actamount,0)- isnull(t0.tamount,0)-isnull(t0.ramount,0)-isnull(t0.aamount,0))> 0 ");
				}
				
				if(param.containsKey("arrearageDay")&&param.get("arrearageDay")!=null){
					 
					// 逾期天数
					Integer arrearageDay = (Integer) param.get("arrearageDay");
					if (arrearageDay != null && arrearageDay.intValue() != 0) {
						Calendar cal = Calendar.getInstance();
						cal.add(Calendar.DATE, -arrearageDay.intValue());
						sqlBuilder.appendSql(" and t0.appdate = {ts '"
								+ DateHelper.getSQLBegin(cal.getTime()).toString()
										.trim() + "'} ");
					}
				}
				
				if(param.containsKey("moneyDefine")&&param.get("moneyDefine")!=null){
					if(param.get("moneyDefine") instanceof List){
						List moneyDefineList =(List)param.get("moneyDefine");
						sqlBuilder.appendParam(" and t0.moneyid ", moneyDefineList.toArray());
					}
				}
				if(param.containsKey("isAudit")&&param.get("isAudit").equals(Boolean.TRUE)){
					sqlBuilder.appendSql("  and t0.state in( 'Auditing','Audited','Executing','Submitted'," +
							"'PartExecuted','ContinueTenancying','RejiggerTenancying','ChangeNaming'," +
							"'TenancyChanging','QuitTenancying','Expiration') ");
				}else{
					sqlBuilder.appendSql("  and t0.state in( 'Audited','Executing'," +
							"'PartExecuted','ContinueTenancying','RejiggerTenancying','ChangeNaming'," +
							"'TenancyChanging','QuitTenancying','Expiration') ");
				}
				sqlBuilder.appendSql(" order by t0.projectname,t0.contractname,t0.roomname,t0.startdate,t0.moneyname ");
				
			}
			logger.debug(sqlBuilder.getTestSql());	
			IRowSet rs =sqlBuilder.executeQuery();
			//付款单集合
			List paymentAdviceList =new ArrayList();
			try{
				while(rs.next()){
					BigDecimal appamount = rs.getBigDecimal("appamount");
					
					if(appamount.intValue()==0){
						continue;
					}
					String id = rs.getString("id");
					Date startdate = rs.getDate("startdate");
					Date enddate = rs.getDate("enddate");
					Date appdate = rs.getDate("appdate");
					String contractname = rs.getString("contractname");
					String roomname ="";
//					if(rs.getString("contractname")!=null){
//						if(rs.getString("contractname").equals(rs.getString("roomname"))){
//							roomname="";
//						}else{
//							roomname= rs.getString("roomname");
//						}
//					}else{
						roomname= rs.getString("roomname");
//					}
					
					String state = rs.getString("state");
					String projectname = rs.getString("projectname");
					
					//处理客户显示问题
					String customer = "";
					Map cc =null; 
					String contractid =rs.getString("contractid");
					int i=0;
					for(Iterator iter = ccList.iterator();iter.hasNext();){
						cc=(HashMap)iter.next();
						if(cc.containsKey("billId")&&cc.get("billId")!=null){
							String billId=cc.get("billId").toString();
							if(billId.equals(contractid)){
								if(i==0){
									customer=customer+cc.get("customername").toString();
									i=1;
								}else{
									customer=customer+","+cc.get("customername").toString();
								}
							}
						}
					}
					
					String moneyname = rs.getString("moneyname");
					BigDecimal actamount=null;
					if(rs.getBigDecimal("actamount")==null){
						actamount=new BigDecimal("0");
					}else{
						actamount = rs.getBigDecimal("actamount");
					}
					BigDecimal tamount=null;
					if(rs.getBigDecimal("tamount")==null){
						tamount=new BigDecimal("0");
					}else{
						tamount = rs.getBigDecimal("tamount");
					}
					BigDecimal ramount=null;
					if(rs.getBigDecimal("ramount")==null){
						ramount=new BigDecimal("0");
					}else{
						ramount = rs.getBigDecimal("ramount");
					}
					BigDecimal aamount=null;
					if(rs.getBigDecimal("aamount")==null){
						aamount=new BigDecimal("0");
					}else{
						aamount = rs.getBigDecimal("aamount");
					}
					//实收金额=已收金额-已退-已转-已调
					BigDecimal finalAmount =actamount.subtract(tamount).subtract(ramount).subtract(aamount);
					BigDecimal tarea=null;
					if(rs.getBigDecimal("tarea")!=null){
						if(rs.getBigDecimal("tarea").compareTo(new BigDecimal("-1"))!=0){
							tarea=rs.getBigDecimal("tarea");
						}
					}
					//add by hehe 
					BigDecimal rate =rs.getBigDecimal("rate");
					BigDecimal appLiquidated =rs.getBigDecimal("appLiquidated");
					BigDecimal actLiquidated =rs.getBigDecimal("actLiquidated");
					BigDecimal reliefLiquidated =rs.getBigDecimal("reliefLiquidated");
					BigDecimal unPayLiquidated =rs.getBigDecimal("unPayLiquidated");
					String 	   tenancyBillnumber=rs.getString("number");
					
					//一条付款单
					Map paymentAdvice = null;
					paymentAdvice = new HashMap();
					paymentAdvice.put("id", id);
					paymentAdvice.put("startdate", startdate);
					paymentAdvice.put("enddate", enddate);
					paymentAdvice.put("appdate", appdate);
					paymentAdvice.put("appamount", appamount);
					paymentAdvice.put("contractname", contractname);
					paymentAdvice.put("roomname", roomname);
					paymentAdvice.put("state", state);
					paymentAdvice.put("projectname", projectname);
					paymentAdvice.put("customer", customer);
					paymentAdvice.put("moneyname", moneyname);
					paymentAdvice.put("actamount", finalAmount);
					paymentAdvice.put("tarea", tarea);
					
					//add by hh
					paymentAdvice.put("rate", rate);
					paymentAdvice.put("appLiquidated", appLiquidated);
					paymentAdvice.put("actLiquidated", actLiquidated);
					paymentAdvice.put("reliefLiquidated", reliefLiquidated);
					paymentAdvice.put("unPayLiquidated", unPayLiquidated);
					paymentAdvice.put("tenancyBill", tenancyBillnumber);
					
					Date now = DateHelper.getDayBegin();
					paymentAdvice.put("arrearageDay", new Integer(DateHelper
							.getDiffDays(appdate, now) -1));
					
					paymentAdviceList.add(paymentAdvice);
					
					
				}
				}catch(SQLException e){
				logger.debug(e);
			}
			result.put("result", paymentAdviceList);
    	return result;
    }
	
	protected IRowSet _getPrintData(Context ctx, Set idSet) throws BOSException {
		// TODO Auto-generated method stub
		// 查询语句
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
    	sqlBuilder.appendSql(" select t0.id,t0.startdate,t0.enddate,t0.appdate,t0.appamount,t0.actamount,t0.tamount,t0.ramount,t0.aamount, ")
		.appendSql(" 	t0.contractname,t0.contractid,t0.roomname,t0.state,t0.projectname,t0.moneyname,t0.moneyid,t0.tarea ")
		.appendSql("  from( ")
		.appendSql(" 	select 'acterPay'||t1.fid  id,t1.FStartdate startdate,t1.fenddate enddate,t1.fappdate appdate,t1.fappamount appamount,t1.fActrevamount ")
		.appendSql(" 	actamount,t1.FHasTransferredAmount tamount,t1.FHasRefundmentAmount ramount,t1.FHasAdjustedAmount aamount, " )
		.appendSql(" t4.fname contractname,t4.fid contractid,t3.fname_l2  roomname,t4.FtenancyState state,t5.fname_l2 projectname ")
		.appendSql(" 	,t8.fname_l2 as moneyname,t8.fid as moneyid,t3.FTenancyArea tarea ")
		.appendSql(" 			from  T_TEN_TenancyRoomPayListEntry t1   ")
		.appendSql(" 				left join T_TEN_TenancyRoomEntry t2 on t1.ftenroomID =t2.FID ")
		.appendSql("				left join T_SHE_Room t3 on t2.froomid = t3.FID ")
		.appendSql("			     left join (	 ")
		.appendSql(" 				select tenancyBill.FID,tenancyBill.FSellProjectID, tenancyBill.fname ,tenancyBill.FtenancyState ")
		.appendSql(" 			 from T_TEN_TenancyBill tenancyBill  ")
		.appendSql("			 where tenancyBill.forgunitid='"+ContextUtil.getCurrentOrgUnit(ctx).getId().toString()+"' and tenancyBill.fid in (select  tenancyCustomerEntry.ftenancybillid ")
		.appendSql(" 			 from T_TEN_TenancyCustomerEntry tenancyCustomerEntry  ");
//		.appendSql(" 			 where 1=1  ");
//		//提交用户的过滤条件
//		if(param.containsKey("customer")){
//			if(param.get("customer") instanceof List){
//				
//				List customerList =(List)param.get("customer");
//				sqlBuilder.appendParam(" and tenancyCustomerEntry.ffdccustomerid ", customerList.toArray());
//			}
//		}
		sqlBuilder.appendSql(" 	) ")
		.appendSql(" 		) t4 on t2.FTenancyID=t4.FID ")
		.appendSql(" 		left join T_SHE_SellProject t5 on t4.FSellProjectID =t5.FID ")
		.appendSql("				left join T_SHE_MoneyDefine t8 on t8.fid=t1.FMoneyDefineID ")
		.appendSql("	union all  ")
		.appendSql("		select 'otherPay'||t1.fid  id,t1.FStartdate startdate,t1.fenddate enddate,t1.fappdate appdate,t1.fappamount appamount,t1.fActrevamount ")
		.appendSql("			actamount,t1.FHasTransferredAmount tamount,t1.FHasRefundmentAmount ramount, t1.FHasAdjustedAmount aamount,t2.fname  contractname,t2.fid contractid, t2.fname  roomname ")
		.appendSql("			,t2.FtenancyState state,t5.fname_l2 projectname,t8.fname_l2  moneyname,t8.fid as moneyid,-1 tarea ")
		.appendSql("				from T_TEN_TenBillOtherPay t1  ")
		.appendSql("				left join (  ")
		.appendSql("				select tenancyBill.FID,tenancyBill.FSellProjectID, tenancyBill.ftenRoomsDes fname ,tenancyBill.fstate,tenancyBill.FtenancyState  ")
		.appendSql("				 from T_TEN_TenancyBill tenancyBill  ")
		.appendSql("					where tenancyBill.forgunitid='"+ContextUtil.getCurrentOrgUnit(ctx).getId().toString()+"' and tenancyBill.fid in (select  tenancyCustomerEntry.ftenancybillid  ")
		.appendSql("				 from T_TEN_TenancyCustomerEntry tenancyCustomerEntry   ")
		.appendSql(" 			 where 1=1  ");
//		//提交用户的过滤条件
//		if(idSet!=null){
//				sqlBuilder.appendParam(" and tenancyCustomerEntry.ffdccustomerid ", idSet.toArray());
//		}
		sqlBuilder.appendSql(" 	) ")
		.appendSql("				) t2 on t1.FHeadID=t2.FID   ")
		.appendSql("				left join T_SHE_SellProject t5 on t2.FSellProjectID =t5.FID   ")
		.appendSql("				left join T_SHE_MoneyDefine t8 on t8.fid=t1.FMoneyDefineID   ")
		.appendSql(") t0 ")
		.appendSql(" where 1=1 ");
		sqlBuilder.appendParam(" and t0.id ", idSet.toArray());
		logger.debug(sqlBuilder.getTestSql());
		return sqlBuilder.executeQuery();
	}
	protected IRowSet _getComment(Context ctx) throws BOSException {
//		// TODO Auto-generated method stub
//		StringBuffer sql = new StringBuffer();
//		sql.append("select fcomment as comment from T_TEN_PaymentAdviceComment");
//		
//		return DbUtil.executeQuery(ctx, sql.toString());
		return null;
	}
}