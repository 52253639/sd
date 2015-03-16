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

public class MonthBudgetBill implements IDataProvider {

	public Object fetchData(HashMap arg1, IDataEnvProvider arg2, IDataExecutor executorRef) throws Exception {
		// TODO 自动生成方法存根
		// bos context

		ResultSet rs = null;
		Context bosCtx = (Context) arg2.get("_ENV_BOS_CONTEXT");
		Connection conn = EJBFactory.getConnection(bosCtx);

		TempTablePool pool = TempTablePool.getInstance(bosCtx);
		// eas report context
		Map rpt_ctx = (Map) arg1.get("CONTEXT");

		Integer planType = (Integer)arg1.get("planType");
		String fromDate = (String) arg1.get("fromDate");
		String endDate = (String) arg1.get("endDate");
		Object[] os = arg1.values().toArray();
		for (int i = 0; os.length > i; i++) {
			Object o = os[i];
			System.out.println("this:::" + o + "\r\n");
		}
		
		System.out.println("this:::" + planType + "\r\n");
		System.out.println("this:::" + fromDate + "\r\n");
		System.out.println("last:::" + endDate + "\r\n");

		StringBuffer where_condition = new StringBuffer();
		// StringBuffer where_condition_in=new StringBuffer();
		// StringBuffer condition=new StringBuffer();
		// StringBuffer condition_in=new StringBuffer();
		boolean condition_flag = false;
		// boolean condition_in_flag=false;
		//				
		// 时间条件
		if (fromDate != null && endDate != null) {
			where_condition.append(" e.FBIZDATE BETWEEN to_date('").append(fromDate).append("') AND to_date('").append(endDate).append("')");
			condition_flag = true;
		} else if (fromDate != null) {
			where_condition.append(" e.FBIZDATE >=to_date('").append(fromDate).append("')");
			condition_flag = true;
		} else if (endDate != null) {
			where_condition.append(" e.FBIZDATE <=to_date('").append(endDate).append("')");
			condition_flag = true;
		} else {
			condition_flag = false;
		}
		//		
		// //客户条件
		// if(customid!=null){
		// if (condition_flag) {
		// where_condition.append(" and c.fnumber in (").append(customid)
		// .append(")");
		//				
		// } else {
		// where_condition.append(" c.fnumber in (").append(customid)
		// .append(")");
		//				
		// }
		// condition_flag=true;
		// }else{
		// if(!condition_flag)
		// condition_flag=false;
		// }

		// 计划类型
		if (planType != null) {
			if (condition_flag) {
				where_condition.append(" and FMarketTypeID = ").append(planType);
			} else {
				where_condition.append(" FMarketTypeID = ").append(planType);
			}
			condition_flag = true;
		} else {
			if (!condition_flag)
				condition_flag = false;
		}

		// //产品规格分类
		// if(prosttype!=null){
		// if(condition_flag){
		// where_condition.append(" and e.fnumber in
		// (").append(prosttype).append(")");
		// }
		// else{
		// where_condition.append(" e.fnumber in
		// (").append(prosttype).append(")");
		// }
		//			
		// condition_flag=true;
		// }else{
		// if(!condition_flag)
		// condition_flag=false;
		// }
		// if(condition_flag){
		// condition.append(" where ").append(where_condition);
		// }else{
		// condition.append(" ");
		// }
		// if(condition_in_flag){
		// condition_in.append(" where ").append(where_condition_in);
		// }else{
		// condition_in.append(" ");
		// }

		StringBuffer show_sql = new StringBuffer();
		show_sql.append("select FMarketTypeID,FTheme as FMarketName,sum(isnull(FAmount,0)) as FAmount,sum(isnull(FFactPhone,0)) as FFactPhone,");
		show_sql.append("sum(isnull(FFactVisit,0)) as FFactVisit,sum(isnull(FFactStrike,0)) as FFactStrike,");
		show_sql.append("sum(isnull(FFactPhone/FAmount,0)) as phoneEffect,sum(isnull(FFactVisit/FAmount,0)) as callInEffect,isnull(FFactStrike,0) as bargainPer,sum(isnull(FFactStrike/FAmount,0)) as bargainEffect");
		show_sql.append(" from T_MAR_MovementManage e left join T_MAR_Markettype t on e.fmovementtypeid=t.fid left join T_MAR_MovementManageE2 e2 on e.FID=e2.FParentID left join T_MAR_MovementManageE3 e3 on e.FID=e3.FParentID");

		if (condition_flag)
			show_sql.append(" where " + where_condition);
		show_sql.append(" group by Fname_l2,FTheme order by FMarketTypeID");

		// 显示SQL
		System.out.println(show_sql);
		PreparedStatement pstmt2 = conn.prepareStatement(show_sql.toString());

		rs = pstmt2.executeQuery();
		// conn.commit();
		// DbUtil.executeQuery(bosCtx,show_sql,params.toArray());
		if(conn!=null){
			conn.close();
		}

		return rs;

	}
}