package com.kingdee.eas.fdc.market.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.reportone.data.engine.script.java.IDataProvider;
import com.kingdee.bos.ctrl.reportone.data.invoke.IDataEnvProvider;
import com.kingdee.bos.ctrl.reportone.data.invoke.IDataExecutor;
import com.kingdee.bos.db.TempTablePool;
import com.kingdee.bos.framework.ejb.EJBFactory;
/*
 * 计划实际分析表
 */
public class PlanFactBill implements IDataProvider {

	public Object fetchData(HashMap arg1, IDataEnvProvider arg2,
			IDataExecutor executorRef) throws Exception {
		// bos context

		ResultSet rs = null;
		Context bosCtx = (Context) arg2.get("_ENV_BOS_CONTEXT");
		Connection conn = EJBFactory.getConnection(bosCtx);

		TempTablePool pool = TempTablePool.getInstance(bosCtx);
		// eas report context
		Map rpt_ctx = (Map) arg1.get("CONTEXT");

		String billdatefrom = (String) arg1.get("planType");
		String billdateto = (String) arg1.get("generalizeType");
		String customid = (String) arg1.get("execState");
		String producttype = (String) arg1.get("producttype");
		
		
		
		String prosttype = (String) arg1.get("prosttype");		

		System.out.println("this:::" + billdatefrom + "\r\n");
		System.out.println("this:::" + billdateto + "\r\n");
		System.out.println("last:::" + customid + "\r\n");
		System.out.println("next:::" + producttype + "\r\n");
		System.out.println("end:::" + prosttype + "\r\n");
//		
//		StringBuffer where_condition=new StringBuffer();
//		StringBuffer where_condition_in=new StringBuffer();
//		StringBuffer condition=new StringBuffer();
//		StringBuffer condition_in=new StringBuffer();
//		boolean condition_flag=false;
//		boolean condition_in_flag=false;
//				
//		//时间条件
//		if(billdatefrom!=null && billdateto!=null){
//			where_condition.append(" f.FBIZDATE BETWEEN to_date('").append(billdatefrom).append("') AND to_date('").append(billdateto).append("')");
//			where_condition_in.append(" FF.FBIZDATE BETWEEN to_date('").append(billdatefrom).append("') AND to_date('").append(billdateto).append("')");
//			condition_flag=true;
//			condition_in_flag=true;
//		}else
//		if(billdatefrom!=null){
//			where_condition.append(" f.FBIZDATE >=to_date('").append(billdatefrom).append("')");
//			where_condition_in.append(" FF.FBIZDATE >=to_date('").append(billdatefrom).append("')");
//			condition_flag=true;
//			condition_in_flag=true;
//		}else
//		if(billdateto!=null){
//			where_condition.append(" f.FBIZDATE <=to_date('").append(billdateto).append("')");
//			where_condition_in.append(" FF.FBIZDATE <=to_date('").append(billdateto).append("')");
//			condition_flag=true;
//			condition_in_flag=true;
//		}else{
//			condition_flag=false;
//			condition_in_flag=false;
//		}
//		
//		//客户条件
//		if(customid!=null){
//			if (condition_flag) {
//				where_condition.append(" and c.fnumber in (").append(customid)
//						.append(")");
//				
//			} else {
//				where_condition.append(" c.fnumber in (").append(customid)
//						.append(")");
//				
//			}
//			condition_flag=true;
//		}else{
//			if(!condition_flag)
//				condition_flag=false;
//		}
//		
//		//产品分类
//		if(producttype!=null){
//			if(condition_flag){
//			where_condition.append(" and d.fnumber in (").append(producttype).append(")");
//			}
//			else{
//			where_condition.append(" d.fnumber in (").append(producttype).append(")");
//			}
//			condition_flag=true;
//		}else{
//			if(!condition_flag)
//				condition_flag=false;
//		}
//		
//		//产品规格分类
//		if(prosttype!=null){
//			if(condition_flag){
//			where_condition.append(" and e.fnumber in (").append(prosttype).append(")");
//			}
//			else{
//			where_condition.append(" e.fnumber in (").append(prosttype).append(")");
//			}
//			
//			condition_flag=true;
//		}else{
//			if(!condition_flag)
//				condition_flag=false;
//		}
//		
//		if(condition_flag){
//			condition.append(" where ").append(where_condition);
//		}else{
//			condition.append(" ");
//		}
//		if(condition_in_flag){
//			condition_in.append(" where ").append(where_condition_in);
//		}else{
//			condition_in.append(" ");
//		}
		
		
		

		String show_sql = new StringBuffer()
				.append("select FMarketTypeID,FMarketName,FAmount,FFactPhone,FFactVisit,FFactStrike," +
						"FFactStrike as phoneEffect,FFactStrike as callInEffect,FFactStrike as bargainPer,FFactStrike as bargainEffect " +
						" from T_MAR_MovementManageE3 as e3,T_MAR_MovementManageE2 as e2 where e3.FParentID = e2.FParentID ")
				.toString();

		// 显示SQL
		System.out.println(show_sql);
		PreparedStatement pstmt2 = conn.prepareStatement(show_sql);
		
		rs = pstmt2.executeQuery();
//		conn.commit();
		//DbUtil.executeQuery(bosCtx,show_sql,params.toArray());
		if(conn!=null){
			conn.close();
		}
		return rs;

	}

}