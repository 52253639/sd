package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;
import javax.ejb.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitLayerTypeFactory;
import com.kingdee.eas.basedata.org.OrgUnitLayerTypeInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.tenancy.FirstLeaseTypeEnum;
import com.kingdee.eas.fdc.tenancy.RentTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fm.common.DateHelper;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomerRentalFacadeControllerBean extends AbstractCustomerRentalFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.CustomerRentalFacadeControllerBean");
    private static final String COMPANY="公司";
    private static final String RENT="租金";
    private static final String DEPOSIT="租赁押金";
    /**
     * com.kingdee.eas.fdc.tenancy.client.CustomerRentalUI
     * 客户租赁情况
     * 
     * */
    protected Map _getCustomerRentalList(Context ctx, Map param)throws BOSException
    {
    	Map result = new HashMap();
    	
//    	//年应收 实收
//		String sql4 = "select isnull(sum(trpe.FAppAmount),0) as appAmount ,isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0) as actAmount," +
//		" isnull(sum(trpe.fappamount),0)-(isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0)) as lastAmount from  T_TEN_TenancyRoomEntry tre " +
//		" left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID left join T_TEN_TenancyRoomPayListEntry as trpe on trpe.FTenRoomID = tre.FID left join T_SHE_MoneyDefine as md on md.FID = trpe.FMoneyDefineID  where md.fname_l2 ='"+RENT+"' " +
//
//		//		" and tb.fid = '"+id+"' and tre.froomid='"+roomid+"'";
//	
//		if(param!=null&&param.get("year")!=null){
//			sql4=sql4+" and "+param.get("year")+"=YEAR (trpe.fappDate) ";
//		}
//    	
//		String sql3="select isnull(sum(trpe.FAppAmount),0) as leaAppAmount ,isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0) as leaActAmount," +
//		" isnull(sum(trpe.fappamount),0)-(isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0)) as leaLastAmount from  T_TEN_TenancyRoomEntry tre " +
//		" left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID left join T_TEN_TenancyRoomPayListEntry as trpe on trpe.FTenRoomID = tre.FID left join T_SHE_MoneyDefine md on md.FID = trpe.FMoneyDefineID  " +
//		" where md.fname_l2 ='"+RENT+"' " ;
////		+"and tb.fid = '"+id+"' and tre.froomid='"+roomid+"'";
//		if(param!=null&&param.get("year")!=null&&param.get("month")!=null){
//			String month=param.get("month").toString();
//			if(Integer.parseInt(month)<10) month="0"+month;
//			
//			Calendar time=Calendar.getInstance(); 
//			time.clear(); 
//			time.set(Calendar.YEAR,Integer.parseInt(param.get("year").toString())); 
//			time.set(Calendar.MONTH,Integer.parseInt(param.get("month").toString())-1);
//			int days=time.getActualMaximum(Calendar.DAY_OF_MONTH);
//			String day="";
//			if(days<10){
//				day="0"+days;
//			}else{
//				day=String.valueOf(days);
//			}
//			sql3=sql3+" and {ts '"+param.get("year")+"-"+month+"-"+day+"'} >=trpe.fstartDate ";
//			sql3=sql3+" and {ts '"+param.get("year")+"-"+month+"-01'} <=trpe.fendDate ";
//		}
//		
//		String sql5 = "select max(rb.fbookeddate) as depositDate ,0-isnull(sum(rbe.frevAmount),0) as endDepositAmount  from T_TEN_TenancyRoomPayListEntry trpe left join T_BDC_FDCReceivingBillEntry rbe on rbe.frevListId= trpe.fid left join T_TEN_TenancyRoomEntry tre on trpe.FTenRoomID = tre.FID left join T_SHE_MoneyDefine as md on md.FID = trpe.FMoneyDefineID " +
//		" left join T_BDC_FDCReceivingBill rb on rb.fid =rbe.fheadid left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID where " +
////		"tb.fid = '"+id+"' and" +
//				" rb.frevBillType='"+RevBillTypeEnum.REFUNDMENT_VALUE+"' and rb.fBillStatus in('12','14') and md.fname_l2 ='"+DEPOSIT+"'； and tre.froomid='"+roomid+"'";
//		IRowSet rs5 = DbUtil.executeQuery(ctx, sql5.toString());
//	    	
//		String sql6 = "select isnull(sum(trpe.FAppAmount),0) as depositAmount from  T_TEN_TenancyRoomEntry tre " +
//		" left join T_TEN_TenancyRoomPayListEntry as trpe on trpe.FTenRoomID = tre.FID left join T_SHE_MoneyDefine as md on md.FID = trpe.FMoneyDefineID  where md.fname_l2 ='"+DEPOSIT+"' and tre.froomid = '"+roomid+"'";
//	
//		IRowSet rs6  = DbUtil.executeQuery(ctx, sql6.toString());
//	    	
//	//从写 @by huanghefh
//	//实收金额
//	String sqlact= "select isnull(sum(trpe.FAppAmount),0) as appAmount ,isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0) as actAmount," +
//	" isnull(sum(trpe.fappamount),0)-(isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0)) as lastAmount from  T_TEN_TenancyRoomEntry tre " +
//	" left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID left join T_TEN_TenancyRoomPayListEntry as trpe on trpe.FTenRoomID = tre.FID left join T_SHE_MoneyDefine as md on md.FID = trpe.FMoneyDefineID  where md.fname_l2 ='"+RENT+"' " +
//	" and tb.fid = '"+id+"' and tre.froomid='"+roomid+"'";
//	if(param!=null&&param.get("year")!=null&&param.get("month")!=null){
//	String month=param.get("month").toString();
//	if(Integer.parseInt(month)<10) month="0"+month;
//	
//	Calendar time=Calendar.getInstance(); 
//	time.clear(); 
//	time.set(Calendar.YEAR,Integer.parseInt(param.get("year").toString())); 
//	time.set(Calendar.MONTH,Integer.parseInt(param.get("month").toString())-1);
//	int days=time.getActualMaximum(Calendar.DAY_OF_MONTH);
//	String day="";
//	if(days<10){
//		day="0"+days;
//	}else{
//		day=String.valueOf(days);
//	}
//	
//	sqlact=sqlact+" and {ts '"+param.get("year")+"-"+month+"-01'} >=trpe.fappDate ";
//	}
//	
//	IRowSet rsact  = DbUtil.executeQuery(ctx, sqlact.toString());
    	
    	
    	// 查询语句
		StringBuffer sql = new StringBuffer();
		sql.append(" select room.fid as roomid,tb.fid as id,tre.fid as treid,bu.fid as org,sp.fname_l2 as projectName, tb.ftenCustomerDes as customer,tb.fnumber as tenancyNumber,tb.ftenancyName as tenancyName,  ");
		sql.append(" room.fname_l2 as tenRoomsDes,isnull(tre.fbuildingArea,0) as totalBuildingArea,tb.fdeliveryRoomDate as actDeliveryRoomDate, ");
		sql.append(" isnull(tre.cfactdayprice,0) as actdayprice,tb.fstartDate as startDate,tb.fendDate as endDate,tb.fendDate as actQuitTenDate,isnull((tb.fleaseCount*tb.FLeaseTime),0) as leaseCount, ");
		sql.append(" tb.fendDate as appDepositDate, us.FName_l2 as tenancyAdviser,de.fname_l2 as department,tb.FFirstLeaseType as firstLeaseType,tb.ffirstLeaseEndDate as firstLeaseEndDate,tb.fleaseTime as leaseTime");
		//@by huanghefh
		sql.append(" ,bp.Fname_l2 as buildingProperty ,isnull(Max(tre.fdayprice),0) as dayPrice,isnull(Max(tre.CFactDayPrice),0) as actdayprice");
		sql.append(" from T_TEN_TenancyRoomEntry tre ");
		sql.append(" left join T_SHE_Room room on room.fid = tre.FRoomID ");
		sql.append(" left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ");
		sql.append(" left join T_SHE_SellProject sp on sp.FID = tb.FSellProjectID");
		sql.append(" left join T_ORG_BaseUnit bu on bu.FID = sp.FOrgUnitID");
		sql.append(" left join T_PM_User us on us.FID = tb.FTenancyAdviserID ");
		sql.append(" left join T_ORG_BaseUnit de on de.FID = tb.FOrgUnitID ");
		sql.append(" left join T_TEN_TenancyRoomPayListEntry trpe on tre.FID = trpe.FTenRoomID ");
		sql.append(" left join T_SHE_MoneyDefine md on md.FID = trpe.FMoneyDefineID ");
		sql.append(" ,T_SHE_BuildingProperty bp");
		sql.append(" where 1=1 ");
		sql.append(" and bp.fid=room.fbuildingPropertyID ");
		sql.append(" and tb.ftenancystate in('Audited','Executing','ContinueTenancying') ");
		sql.append(" and md.fname_l2 ='"+RENT+"' ");
		if (param != null) {
			// 组织
			Set orgUnit = (Set) param.get("orgUnit");
			if (orgUnit != null && orgUnit.size() > 0) {
				sql.append(" and sp.FID in "+ FMHelper.setTran2String(orgUnit));
			}
			// 项目
			String project = (String) param.get("project");
			if (project != null) {
				sql.append(" and sp.FID = '" + project + "' ");
			}
			// 合同业务开始
			Date startDate = (Date) param.get("startDate");
			if (startDate != null) {
				sql.append(" and tb.fstartDate >= {ts '"
						+ DateHelper.getSQLBegin(startDate) + "'} ");
			}
			// 合同业务结束      DateHelper.getNextDay((endDate)) 删除
			Date endDate = (Date) param.get("endDate");
			if (endDate != null) {
				sql.append(" and tb.fendDate <= {ts '"
						+ DateHelper.getSQLBegin(endDate) + "'} ");
			}
			if(param.get("id")!=null){
				sql.append(" and tb.Fid ='"+param.get("id").toString()+"' ");
			}
			if(param.get("customer")!=null){
				try {
					FDCCustomerInfo info = FDCCustomerFactory.getLocalInstance(ctx).getFDCCustomerInfo(
							new ObjectUuidPK(BOSUuid.read(param.get("customer").toString())));
					sql.append(" and tb.ftenCustomerDes like '%"+info.getName()+"%'");
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (UuidException e) {
					e.printStackTrace();
				}
			}
			if(param.get("year")!=null){
				sql.append(" and "+param.get("year")+"=YEAR (trpe.fappDate) ");
			}
		}
		sql.append(" group by room.fid ,tb.fid ,bu.fid ,tre.fid,sp.fname_l2 , tb.ftenCustomerDes ,tb.fnumber ,tb.ftenancyName ,  ");
		sql.append(" room.fname_l2 ,tre.fbuildingArea,tb.fdeliveryRoomDate, ");
		sql.append(" tre.fdayprice,tb.fstartDate ,tb.fendDate ,tb.fendDate ,tb.fleaseCount,tb.FLeaseTime, ");
		sql.append(" us.FName_l2 ,de.fname_l2 ,tb.FFirstLeaseType ,tb.ffirstLeaseEndDate ,tb.fleaseTime ");
		sql.append(" ,bp.Fname_l2 ,tre.CFactDayPrice,tre.CFactDayPrice,tre.FStandardRoomRentPrice");
		sql.append(" having sum(trpe.FAppAmount) >0 ");
		sql.append(" order by bu.fid,sp.FName_l2,tb.ftenCustomerDes,tb.fnumber ");
		//f)	缺少排序，增加默认按合同编码排序
		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
		
		List lst = new ArrayList();
		Map cusMap = new HashMap();
		try {
			while (rs.next()) {
				String id = rs.getString("id");
				String roomid=rs.getString("roomid");
				String projectName = rs.getString("projectName");
				String org = getCompanyByOrg(ctx,rs.getString("org"));
				String customer=rs.getString("customer");
				String treid=rs.getString("treid");
				String tenancyNumber =rs.getString("tenancyNumber");
				String tenancyName=rs.getString("tenancyName");
				String tenRoomsDes=rs.getString("tenRoomsDes");
				String buildingProperty=rs.getString("buildingProperty");
				
				BigDecimal totalBuildingArea=rs.getBigDecimal("totalBuildingArea");
				int totalBuildingAmount=1;
				Date actDeliveryRoomDate=rs.getDate("actDeliveryRoomDate");
				BigDecimal dayPrice=rs.getBigDecimal("dayPrice");
				Date startDate=rs.getDate("startDate");
				Date endDate=rs.getDate("endDate");
				Date actQuitTenDate=rs.getDate("actQuitTenDate");
				int leaseCount=rs.getInt("leaseCount");
				Date appDepositDate=rs.getDate("appDepositDate");
				String tenancyAdviser=rs.getString("tenancyAdviser");
				String department=rs.getString("department");
				//单价
				BigDecimal unitStandard=rs.getBigDecimal("dayPrice");
				BigDecimal unitActual=rs.getBigDecimal("actdayprice");
				BigDecimal monthStandPrice=rs.getBigDecimal("dayPrice").multiply(rs.getBigDecimal("totalBuildingArea")).multiply(new BigDecimal(30));
				BigDecimal unitDiffer=new BigDecimal(0); 
				BigDecimal unitRate=new BigDecimal(0); 
				//应收
				BigDecimal appAmount=new BigDecimal(0);
				BigDecimal actAmount=new BigDecimal(0); 
				BigDecimal lastAmount=new BigDecimal(0); 
				//累计未收
				BigDecimal totalLastAmount=new BigDecimal(0); 
				//总金额
				BigDecimal totalStandard=new BigDecimal(0);
				BigDecimal totalActual=new BigDecimal(0);
				BigDecimal totalDiffer=new BigDecimal(0); 
				BigDecimal totalRate=new BigDecimal(0);
				//实收金额
				BigDecimal actStandard=new BigDecimal(0);
				BigDecimal actActual=new BigDecimal(0); 
				BigDecimal actRate=new BigDecimal(0); 
				BigDecimal actDiffer=new BigDecimal(0); 
				
				//年应收 实收
				String sql4 = "select isnull(sum(trpe.FAppAmount),0) as appAmount ,isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0) as actAmount," +
				" isnull(sum(trpe.fappamount),0)-(isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0)) as lastAmount from  T_TEN_TenancyRoomEntry tre " +
				" left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID left join T_TEN_TenancyRoomPayListEntry as trpe on trpe.FTenRoomID = tre.FID left join T_SHE_MoneyDefine as md on md.FID = trpe.FMoneyDefineID  where md.fname_l2 ='"+RENT+"' " +
				" and tb.fid = '"+id+"' and tre.froomid='"+roomid+"'";
			
				if(param!=null&&param.get("year")!=null){
					sql4=sql4+" and "+param.get("year")+"=YEAR (trpe.fappDate) ";
				}
				
				IRowSet rs4  = DbUtil.executeQuery(ctx, sql4.toString());
	
				while (rs4.next()) {
					appAmount=rs4.getBigDecimal("appAmount");
					actAmount=rs4.getBigDecimal("actAmount");
					lastAmount=rs4.getBigDecimal("lastAmount");
				}
			
				if(appAmount==null||appAmount.compareTo(new BigDecimal(0))==0) continue;
				
				/*********************获取租期集合***************************/

				int actLeaseCount=0;
				int lastLeaseCount=0;
				if(param!=null&&param.get("year")!=null&&param.get("month")!=null){
					
					Calendar time=Calendar.getInstance(); 
					time.clear(); 
					time.set(Calendar.YEAR,Integer.parseInt(param.get("year").toString())); 
					time.set(Calendar.MONTH,Integer.parseInt(param.get("month").toString())-1);
					time.set(Calendar.DATE,1);
					
					List lease= TenancyHelper.getLeaseList(startDate, time.getTime(), FirstLeaseTypeEnum.getEnum(rs.getString("firstLeaseType")), rs.getDate("firstLeaseEndDate"), rs.getInt("leaseTime"));
					if(lease==null) lease=new ArrayList();
					actLeaseCount=lease.size();
					actLeaseCount=actLeaseCount*rs.getInt("leaseTime");//已用月 等于已用周期*周期
					lastLeaseCount=leaseCount-actLeaseCount;
				}
				/************************本期应收************************/

				BigDecimal leaAppAmount=new BigDecimal(0);
				BigDecimal leaActAmount=new BigDecimal(0); 
				BigDecimal leaLastAmount=new BigDecimal(0); 
				
				String sql3="select isnull(sum(trpe.FAppAmount),0) as leaAppAmount ,isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0) as leaActAmount," +
					" isnull(sum(trpe.fappamount),0)-(isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0)) as leaLastAmount from  T_TEN_TenancyRoomEntry tre " +
					" left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID left join T_TEN_TenancyRoomPayListEntry as trpe on trpe.FTenRoomID = tre.FID left join T_SHE_MoneyDefine md on md.FID = trpe.FMoneyDefineID  " +
					" where md.fname_l2 ='"+RENT+"' and tb.fid = '"+id+"' and tre.froomid='"+roomid+"'";
				if(param!=null&&param.get("year")!=null&&param.get("month")!=null){
					String month=param.get("month").toString();
					if(Integer.parseInt(month)<10) month="0"+month;
					
					Calendar time=Calendar.getInstance(); 
					time.clear(); 
					time.set(Calendar.YEAR,Integer.parseInt(param.get("year").toString())); 
					time.set(Calendar.MONTH,Integer.parseInt(param.get("month").toString())-1);
					int days=time.getActualMaximum(Calendar.DAY_OF_MONTH);
					String day="";
					if(days<10){
						day="0"+days;
					}else{
						day=String.valueOf(days);
					}
					sql3=sql3+" and {ts '"+param.get("year")+"-"+month+"-"+day+"'} >=trpe.fstartDate ";
					sql3=sql3+" and {ts '"+param.get("year")+"-"+month+"-01'} <=trpe.fendDate ";
				}
				
				IRowSet rs3  = DbUtil.executeQuery(ctx, sql3.toString());
				while (rs3.next()) {
					leaAppAmount=rs3.getBigDecimal("leaAppAmount");
					if(rs.getInt("leaseTime")!=0){
						leaAppAmount=leaAppAmount.divide(new BigDecimal(rs.getInt("leaseTime")),2, BigDecimal.ROUND_HALF_UP);// 除去叠加的周期性费用
					}else{
					}
					
					leaActAmount=rs3.getBigDecimal("leaActAmount");
					leaLastAmount=leaAppAmount.subtract(leaActAmount);
				}
				/*******************退还押金、日期、金额、结余*****************************/

				
				String sql5 = "select max(rb.fbookeddate) as depositDate ,0-isnull(sum(rbe.frevAmount),0) as endDepositAmount  from T_TEN_TenancyRoomPayListEntry trpe left join T_BDC_FDCReceivingBillEntry rbe on rbe.frevListId= trpe.fid left join T_TEN_TenancyRoomEntry tre on trpe.FTenRoomID = tre.FID left join T_SHE_MoneyDefine as md on md.FID = trpe.FMoneyDefineID " +
							" left join T_BDC_FDCReceivingBill rb on rb.fid =rbe.fheadid left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID where tb.fid = '"+id+"' and rb.frevBillType='"+RevBillTypeEnum.REFUNDMENT_VALUE+"' and rb.fBillStatus in('12','14') and md.fname_l2 ='"+DEPOSIT+"' and tre.froomid='"+roomid+"'";
				IRowSet rs5 = DbUtil.executeQuery(ctx, sql5.toString());

				BigDecimal endDepositAmount=new BigDecimal(0); 
				Date depositDate=null;
				while (rs5.next()) {
					depositDate=rs5.getDate("depositDate");
					endDepositAmount=rs5.getBigDecimal("endDepositAmount");
				}
				
				BigDecimal depositAmount =new BigDecimal(0); 
				//押金
				String sql6 = "select isnull(sum(trpe.FAppAmount),0) as depositAmount from  T_TEN_TenancyRoomEntry tre " +
				" left join T_TEN_TenancyRoomPayListEntry as trpe on trpe.FTenRoomID = tre.FID left join T_SHE_MoneyDefine as md on md.FID = trpe.FMoneyDefineID  where md.fname_l2 ='"+DEPOSIT+"' and tre.froomid = '"+roomid+"' and tre.fid='"+treid+"'";

				IRowSet rs6  = DbUtil.executeQuery(ctx, sql6.toString());
	
				while (rs6.next()) {
					depositAmount=rs6.getBigDecimal("depositAmount");
				}
			
				BigDecimal depositLastAmount =new BigDecimal(0); 
				if(endDepositAmount!=null ){
					depositLastAmount=depositAmount.subtract(endDepositAmount);
				}else{
					depositLastAmount=depositAmount;
				}
				//从写 @by huanghefh
				//实收金额
				String sqlact= "select isnull(sum(trpe.FAppAmount),0) as appAmount ,isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0) as actAmount," +
				" isnull(sum(trpe.fappamount),0)-(isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0)) as lastAmount from  T_TEN_TenancyRoomEntry tre " +
				" left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID left join T_TEN_TenancyRoomPayListEntry as trpe on trpe.FTenRoomID = tre.FID left join T_SHE_MoneyDefine as md on md.FID = trpe.FMoneyDefineID  where md.fname_l2 ='"+RENT+"' " +
				" and tb.fid = '"+id+"' and tre.froomid='"+roomid+"'";
			if(param!=null&&param.get("year")!=null&&param.get("month")!=null){
				String month=param.get("month").toString();
				if(Integer.parseInt(month)<10) month="0"+month;
				
				Calendar time=Calendar.getInstance(); 
				time.clear(); 
				time.set(Calendar.YEAR,Integer.parseInt(param.get("year").toString())); 
				time.set(Calendar.MONTH,Integer.parseInt(param.get("month").toString())-1);
				int days=time.getActualMaximum(Calendar.DAY_OF_MONTH);
				String day="";
				if(days<10){
					day="0"+days;
				}else{
					day=String.valueOf(days);
				}
				
				sqlact=sqlact+" and {ts '"+param.get("year")+"-"+month+"-01'} >=trpe.fappDate ";
			}
			
			IRowSet rsact  = DbUtil.executeQuery(ctx, sqlact.toString());
			while (rsact.next()) {
				actStandard=rsact.getBigDecimal("appAmount");
				actActual=rsact.getBigDecimal("actAmount");
				actDiffer=rsact.getBigDecimal("lastAmount");
			}
			
			//总金额
			totalStandard=monthStandPrice.multiply((new BigDecimal(actLeaseCount)));//月出租* 已租月期
			totalActual=actStandard;
			//单元
			unitDiffer=unitActual.compareTo(new BigDecimal(0))!=0?unitActual.subtract(unitStandard):new BigDecimal(0);
			if(unitStandard.compareTo(new BigDecimal(0))!=0&&unitDiffer.compareTo(new BigDecimal(0))!=0){
				unitRate=unitDiffer.multiply(new BigDecimal(100)).divide(unitStandard, 2, BigDecimal.ROUND_HALF_UP);
			}
			//实收actStandard
			actDiffer=actActual.compareTo(new BigDecimal(0))!=0?actActual.subtract(actStandard):new BigDecimal(0);
			if(actStandard.compareTo(new BigDecimal(0))!=0&&actDiffer.compareTo(new BigDecimal(0))!=0){
				actRate=actDiffer.multiply(new BigDecimal(100)).divide(actStandard, 2, BigDecimal.ROUND_HALF_UP);
			}
			//总金额
			totalDiffer=totalActual.compareTo(new BigDecimal(0))!=0?totalActual.subtract(totalStandard):new BigDecimal(0);
			if(totalStandard.compareTo(new BigDecimal(0))!=0&&totalDiffer.compareTo(new BigDecimal(0))!=0){
				totalRate=totalDiffer.multiply(new BigDecimal(100)).divide(totalStandard, 2, BigDecimal.ROUND_HALF_UP);
			}
			//未收
			totalLastAmount=actStandard.subtract(actActual);
			
				
				Map customerRental = new HashMap();
				
				customerRental.put("id", id);
				customerRental.put("org", org);
				customerRental.put("projectName", projectName);
				customerRental.put("customer", customer);
				customerRental.put("tenancyNumber", tenancyNumber);
				customerRental.put("tenancyName", tenancyName);
				
				customerRental.put("tenRoomsDes", tenRoomsDes);
				customerRental.put("totalBuildingArea", totalBuildingArea);
				customerRental.put("totalBuildingAmount", ""+totalBuildingAmount);
			
				customerRental.put("actDeliveryRoomDate", actDeliveryRoomDate);
				customerRental.put("dayPrice", dayPrice);
				customerRental.put("startDate", startDate);
				customerRental.put("endDate", endDate);
				customerRental.put("actQuitTenDate", actQuitTenDate);
				
				customerRental.put("leaseCount", ""+leaseCount);
				customerRental.put("actLeaseCount", ""+actLeaseCount);
				customerRental.put("lastLeaseCount", ""+lastLeaseCount);
				customerRental.put("appAmount", appAmount);
				customerRental.put("actAmount", actAmount);
				
				customerRental.put("lastAmount", lastAmount);
				customerRental.put("leaAppAmount", leaAppAmount);
				customerRental.put("leaActAmount", leaActAmount);
				customerRental.put("leaLastAmount", leaLastAmount);
				//日单价
				customerRental.put("unitStandard", unitStandard);
				customerRental.put("unitActual", unitActual);
				customerRental.put("unitDiffer", unitDiffer);
				customerRental.put("unitRate", unitRate);
				//总金额
				customerRental.put("totalStandard", totalStandard);
				customerRental.put("totalActual", totalActual);
				customerRental.put("totalDiffer", totalDiffer);
				customerRental.put("totalRate", totalRate);
				//实收金额
				customerRental.put("actStandard", actStandard);
				customerRental.put("actActual", actActual);
				customerRental.put("actDiffer", actDiffer);
				customerRental.put("actRate", actRate);
				
				customerRental.put("totalLastAmount", totalLastAmount);
				customerRental.put("depositAmount", depositAmount);
				customerRental.put("appDepositDate", appDepositDate);
				customerRental.put("depositDate", depositDate);
				customerRental.put("endDepositAmount", endDepositAmount);
							
				customerRental.put("depositLastAmount", depositLastAmount);
				customerRental.put("department", department);
				customerRental.put("tenancyAdviser", tenancyAdviser);
				customerRental.put("buildingProperty", buildingProperty);
				
				lst.add(customerRental);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result.put("customer", lst);
        return result;
    }
    
    private String getCompanyByOrg(Context ctx,String orgid) throws  BOSException{
    	FullOrgUnitInfo com=null;
		try {
			com = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitInfo(new ObjectUuidPK(orgid));
			if(com.isIsAdminOrgUnit()){
				AdminOrgUnitInfo admin=AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitInfo(new ObjectUuidPK(orgid));
				OrgUnitLayerTypeInfo lay=OrgUnitLayerTypeFactory.getLocalInstance(ctx).getOrgUnitLayerTypeInfo(new ObjectUuidPK(admin.getUnitLayerType().getId()));
				if(COMPANY.equals(lay.getName())){
					return com.getName();
				}else{
					return getCompanyByOrg(ctx,com.getParent().getId().toString());
				}
				
			}else{
				return getCompanyByOrg(ctx,com.getParent().getId().toString());
			}
		}
		catch (EASBizException e) {
			e.printStackTrace();
		}
		return null;
    }
    /**
     * com.kingdee.eas.fdc.tenancy.client.CustomerRentalListUI
     * 房源租赁明细表
     */
    /**
     * a)	本年应收金额、本年已收金额、本年未收金额：数据有误，目前等于累计所有数
     * d)	缺少排序，增加默认按公司、项目、楼栋、楼层、单元排序
     * 房源日单价取数不对。标准取法为合同分录现成的，不需要在计算  20110608 hh
     * */
    public Map _getCustomerRentalListList(Context ctx, Map param)
    throws BOSException
  {
    Map result = new HashMap();

    
    

    String sql4 = "  select sum(trpe.FAppAmount) as appAmount ,isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0) as actAmount, isnull(sum(trpe.fappamount),0)-(isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0)) as lastAmount";
    	sql4    =sql4+ "  , tre.fid as fid from  T_TEN_TenancyRoomEntry tre  left join T_TEN_TenancyRoomPayListEntry as trpe on trpe.FTenRoomID = tre.FID left join T_SHE_MoneyDefine as md on md.FID = trpe.FMoneyDefineID  where md.fname_l2 ='租金'  ";
    if ((param != null) && (param.get("year") != null)) {
    	sql4 = sql4 + " and " + param.get("year") + "=YEAR (trpe.fappDate) ";
    }
    	sql4 = sql4+" group by tre.fid  ";
    String sql1 = " select tre.FStandardRentType as tretype,mae.FRentType as maetype,isnull(tre.fstandardRoomRentPrice,0) as unitStandard ,isnull(mae.fpriceAmount,0) as unitActual";
    	sql1=sql1+" , tre.fid  from  T_TEN_TenancyRoomEntry tre  left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID left join T_TEN_DealAmountEntry mae on mae.FTenancyRoomID =tre.fid left join T_SHE_MoneyDefine as md on md.FID = mae.FMoneyDefineID  where md.fname_l2 ='租金' ";
    String sql2 = " select isnull(sum(trpe.FAppAmount),0) as allAppAmount ,isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0) as allActAmount, isnull(sum(trpe.fappamount),0)-(isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0)) as allLastAmount";
    	sql2=sql2+" ,tre.fid  as fid from  T_TEN_TenancyRoomEntry tre  left join T_TEN_TenancyRoomPayListEntry as trpe on trpe.FTenRoomID = tre.FID left join T_SHE_MoneyDefine as md on md.FID = trpe.FMoneyDefineID  where md.fname_l2 ='租金' ";
    	sql2 = sql2+" group by tre.fid  ";
    String sql3 = " select isnull(sum(trpe.FAppAmount),0) as leaAppAmount ,isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0) as leaActAmount, isnull(sum(trpe.fappamount),0)-(isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0)) as leaLastAmount" +
    			  " ,tre.fid as fid  from  T_TEN_TenancyRoomEntry tre  left join T_TEN_TenancyRoomPayListEntry as trpe on trpe.FTenRoomID = tre.FID left join T_SHE_MoneyDefine md on md.FID = trpe.FMoneyDefineID   where md.fname_l2 ='租金' ";
  if ((param != null) && (param.get("year") != null) && (param.get("month") != null)) {
    String month = param.get("month").toString();
    if (Integer.parseInt(month) < 10) month = "0" + month;

    Calendar time = Calendar.getInstance();
    time.clear();
    time.set(1, Integer.parseInt(param.get("year").toString()));
    time.set(2, Integer.parseInt(param.get("month").toString()) - 1);
    int days = time.getActualMaximum(5);
    String day = "";
    if (days < 10)
      day = "0" + days;
    else {
      day = String.valueOf(days);
    }
    sql3 = sql3 + " and {ts '" + param.get("year") + "-" + month + "-" + day + "'} >=trpe.fstartDate ";
    sql3 = sql3 + " and {ts '" + param.get("year") + "-" + month + "-01'} <=trpe.fendDate ";
    sql3 = sql3+" group by tre.fid  ";
  }

  String sql5 = "select max(rb.fbookeddate) as depositDate ,0-isnull(sum(rbe.frevAmount),0) as endDepositAmount " +
  		" ,tre.fid as fid  from T_TEN_TenancyRoomPayListEntry trpe left join T_BDC_FDCReceivingBillEntry rbe on rbe.frevListId= trpe.fid left join T_TEN_TenancyRoomEntry tre on trpe.FTenRoomID = tre.FID left join T_SHE_MoneyDefine as md on md.FID = trpe.FMoneyDefineID  left join T_BDC_FDCReceivingBill rb on rb.fid =rbe.fheadid where  rb.frevBillType='" + "refundment" + "' and rb.fBillStatus in('12','14') and md.fname_l2 ='" + "租赁押金" + "' ";
  		sql5=sql5+" group by tre.fid  ";
  String sql6 = "select isnull(sum(trpe.FAppAmount),0) as depositAmount" +
  		" , tre.fid as fid from  T_TEN_TenancyRoomEntry tre  left join T_TEN_TenancyRoomPayListEntry as trpe on trpe.FTenRoomID = tre.FID left join T_SHE_MoneyDefine as md on md.FID = trpe.FMoneyDefineID  where md.fname_l2 ='租赁押金' ";
  		 sql6 = sql6+" group by tre.fid  ";
    
 
    
    
    
    
    
    
    
    
    
    
    
    
  		//tre.FActQuitTenDate 才是真正的实际退房日期，但由于一直是null（不为null说明不是('Audited','Executing','ContinueTenancying')有效期，数据不能被过滤出来）；
    StringBuffer sql = new StringBuffer();
    sql.append("select * from (");
    sql.append(" select tre.fid as id,bu.fid as org,sp.fname_l2 as projectName,build.fname_l2 as build,rm.FFloor as floor,rm.fname_l2 as cell,pt.fname_l2 as type, tb.ftenCustomerDes as customer,tb.fnumber as tenancyNumber,tb.ftenancyName as tenancyName,  ");
    sql.append(" isnull(rm.ftenancyArea,0) as totalBuildingArea,tb.fdeliveryRoomDate as actDeliveryRoomDate, ");
    sql.append(" tb.fstartDate as startDate,tb.fendDate as endDate,tb.fendDate as actQuitTenDate,(tb.fleaseCount*tb.FLeaseTime) as leaseCount , ");
    sql.append(" tb.fendDate as appDepositDate, us.FName_l2 as tenancyAdviser,de.fname_l2 as department,tb.FFirstLeaseType as firstLeaseType,tb.ffirstLeaseEndDate as firstLeaseEndDate,tb.fleaseTime as leaseTime  ,isnull(Max(tre.fdayprice),0) as dayPrice,isnull(Max(tre.CFactDayPrice),0) as actdayprice ");
    
    sql.append(" from t_she_building as build ");
    sql.append(" left join T_SHE_Room as rm ");
    sql.append(" on build.FID = rm.FbuildingID ");
    sql.append(" left join T_FDC_ProductType as pt ");
    sql.append(" on pt.FID = rm.FProductTypeID ");
    sql.append(" left join T_TEN_TenancyRoomEntry as tre ");
    sql.append(" on tre.froomid = rm.FID ");
    sql.append(" left join T_TEN_TenancyBill as tb ");
    sql.append(" on tre.FTenancyID = tb.FID ");
    sql.append(" left join T_SHE_SellProject as sp ");
    sql.append(" on sp.FID = tb.FSellProjectID ");
    sql.append(" left join T_SHE_Subarea as sub ");
    sql.append(" on sub.FSellProjectID = sp.fid ");
    sql.append(" left join T_ORG_BaseUnit as bu ");
    sql.append(" on bu.FID = sp.FOrgUnitID ");
    sql.append(" left join T_ORG_BaseUnit as de ");
    sql.append(" on de.FID = tb.FOrgUnitID ");
    sql.append(" left join T_PM_User as us ");
    sql.append(" on us.FID = tb.FTenancyAdviserID ");
    sql.append(" left join T_TEN_TenancyRoomPayListEntry trpe on tre.FID = trpe.FTenRoomID ");
    sql.append(" left join T_SHE_MoneyDefine md on md.FID = trpe.FMoneyDefineID ");
    sql.append(" where 1=1 ");

    sql.append(" and tb.ftenancystate in('Audited','Executing','ContinueTenancying') ");
    sql.append(" and md.fname_l2 ='租金' ");
    if (param != null)
    {
      Set orgUnit = (Set)param.get("orgUnit");
      if ((orgUnit != null) && (orgUnit.size() > 0)) {
        sql.append(" and sp.FID in " + FMHelper.setTran2String(orgUnit));
      }

      String project = (String)param.get("project");
      if (project != null) {
        sql.append(" and sp.FID = '" + project + "' ");
      }

      String subarea = (String)param.get("subarea");
      if (subarea != null) {
        sql.append(" and sub.FID = '" + subarea + "' ");
      }

      String build = (String)param.get("build");
      if (build != null) {
        sql.append(" and build.FID = '" + build + "' ");
      }

      Date startDate = (Date)param.get("startDate");
      if (startDate != null) {
        sql.append(" and tb.fstartDate >= {ts '" + 
          DateHelper.getSQLBegin(startDate) + "'} ");
      }

      Date endDate = (Date)param.get("endDate");
      if (endDate != null) {
        sql.append(" and tb.fendDate < {ts '" + 
          DateHelper.getSQLBegin(
          DateHelper.getNextDay(endDate)) + "'} ");
      }
      if (param.get("id") != null) {
        sql.append(" and tb.Fid ='" + param.get("id").toString() + "' ");
      }
      if (param.get("customer") != null) {
        try {
          FDCCustomerInfo info = FDCCustomerFactory.getLocalInstance(ctx).getFDCCustomerInfo(
            new ObjectUuidPK(BOSUuid.read(param.get("customer").toString())));
          sql.append(" and tb.ftenCustomerDes like '%" + info.getName() + "%'");
        } catch (EASBizException e) {
          e.printStackTrace();
        } catch (UuidException e) {
          e.printStackTrace();
        }
      }
      if (param.get("year") != null) {
        sql.append(" and " + param.get("year") + "=YEAR (trpe.fappDate) ");
      }
    }
    sql.append(" group by tre.fid ,bu.fid ,sp.fname_l2 ,build.fname_l2 ,rm.FFloor ,rm.fname_l2 ,pt.fname_l2 , tb.ftenCustomerDes ,tb.fnumber ,tb.ftenancyName ,  ");
    sql.append(" rm.ftenancyArea,tb.fdeliveryRoomDate  , ");
    sql.append(" tb.fstartDate ,tb.fendDate ,tre.FActQuitTenDate ,tb.fleaseCount,tb.FLeaseTime, ");
    sql.append(" us.FName_l2 ,de.fname_l2 ,tb.FFirstLeaseType ,tb.ffirstLeaseEndDate ,tb.fleaseTime ");
    sql.append(" having sum(trpe.FAppAmount) >0 ");
    sql.append(")parent ");
    sql.append(" left join ("+sql1+")sql1 on sql1.fid =parent.id " );
    sql.append(" left join ("+sql2+")sql2 on sql2.fid =parent.id " );
    sql.append(" left join ("+sql3+")sql3 on sql3.fid =parent.id " );
    sql.append(" left join ("+sql4+")sql4 on sql4.fid =parent.id " );
    sql.append(" left join ("+sql5+")sql5 on sql5.fid =parent.id " );
    sql.append(" left join ("+sql6+")sql6 on sql6.fid =parent.id " );
  sql.append("order by org,projectName,build,floor,cell,customer,tenancyNumber,tenancyName");
    //按公司、项目、楼栋、楼层、单元排序 已完成
    IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
    List lst = new ArrayList();
    Map cusMap = new HashMap();
    try {
      while (rs.next()) {
        String id = rs.getString("id");
        String projectName = rs.getString("projectName");
        String org = getCompanyByOrg(ctx, rs.getString("org"));
        String build = rs.getString("build");
        String floor = rs.getString("floor");
        String cell = rs.getString("cell");
        String type = rs.getString("type");

        String customer = rs.getString("customer");
        String tenancyNumber = rs.getString("tenancyNumber");
        String tenancyName = rs.getString("tenancyName");
        BigDecimal totalBuildingArea = rs.getBigDecimal("totalBuildingArea");
        Date actDeliveryRoomDate = rs.getDate("actDeliveryRoomDate");
        Date startDate = rs.getDate("startDate");
        Date endDate = rs.getDate("endDate");
        Date actQuitTenDate = rs.getDate("actQuitTenDate");
        int leaseCount = rs.getInt("leaseCount");
        Date appDepositDate = rs.getDate("appDepositDate");
        String tenancyAdviser = rs.getString("tenancyAdviser");
        String department = rs.getString("department");

        BigDecimal appAmount = new BigDecimal(0);
        BigDecimal actAmount = new BigDecimal(0);
        BigDecimal lastAmount = new BigDecimal(0);
        
        BigDecimal unitStandard = new BigDecimal(0);
        BigDecimal unitActual = new BigDecimal(0);
        BigDecimal unitRate = new BigDecimal(0);


        BigDecimal allAppAmount = new BigDecimal(0);
        BigDecimal allActAmount = new BigDecimal(0);
        BigDecimal allLastAmount = new BigDecimal(0);


        BigDecimal leaAppAmount = new BigDecimal(0);
        BigDecimal leaActAmount = new BigDecimal(0);
        BigDecimal leaLastAmount = new BigDecimal(0);
        

        
        
        

        	//sql4
          appAmount = rs.getBigDecimal("appAmount");
          actAmount = rs.getBigDecimal("actAmount");
          lastAmount = rs.getBigDecimal("lastAmount");
        //sql1
          unitStandard =rs.getBigDecimal("dayPrice");
          unitActual = rs.getBigDecimal("actdayprice");

        BigDecimal unitDiffer = unitActual.subtract(unitStandard);
        if (unitStandard.compareTo(new BigDecimal(0)) != 0) {
          unitRate = unitDiffer.multiply(new BigDecimal(100)).divide(unitStandard, 2, 4);
        }
      //sql2
          allAppAmount = rs.getBigDecimal("allAppAmount");
          allActAmount = rs.getBigDecimal("allActAmount");
          allLastAmount = rs.getBigDecimal("allLastAmount");

        int actLeaseCount = 0;
        int lastLeaseCount = 0;
        if ((param != null) && (param.get("year") != null) && (param.get("month") != null))
        {
          Calendar time = Calendar.getInstance();
          time.clear();
          time.set(1, Integer.parseInt(param.get("year").toString()));
          time.set(2, Integer.parseInt(param.get("month").toString()) - 1);
          time.set(5, 1);

          List lease = TenancyHelper.getLeaseList(startDate, time.getTime(), FirstLeaseTypeEnum.getEnum(rs.getString("firstLeaseType")), rs.getDate("firstLeaseEndDate"), rs.getInt("leaseTime"));
          if (lease == null) lease = new ArrayList();

          actLeaseCount = lease.size();
          if(actLeaseCount>leaseCount)
        	  actLeaseCount =leaseCount;//huanghefh
          lastLeaseCount = leaseCount - actLeaseCount;
        }
      //sql3
          leaAppAmount = rs.getBigDecimal("leaAppAmount");
          leaActAmount = rs.getBigDecimal("leaActAmount");
          leaLastAmount = rs.getBigDecimal("leaLastAmount");

        BigDecimal endDepositAmount = new BigDecimal(0);
        Date depositDate = null;
          depositDate = rs.getDate("depositDate");
          endDepositAmount = rs.getBigDecimal("endDepositAmount");

        BigDecimal depositAmount = new BigDecimal(0);
          depositAmount = rs.getBigDecimal("depositAmount");

        BigDecimal depositLastAmount = new BigDecimal(0);
        if (endDepositAmount != null)
          depositLastAmount = depositAmount.subtract(endDepositAmount);
        else {
          depositLastAmount = depositAmount;
        }

        Map customerRental = new HashMap();

        customerRental.put("id", id);
        customerRental.put("org", org);
        customerRental.put("projectName", projectName);
        customerRental.put("build", build);
        customerRental.put("floor", floor);
        customerRental.put("cell", cell);
        customerRental.put("type", type);
        customerRental.put("customer", customer);
        customerRental.put("tenancyNumber", tenancyNumber);
        customerRental.put("tenancyName", tenancyName);
        customerRental.put("totalBuildingArea", totalBuildingArea);
        customerRental.put("actDeliveryRoomDate", actDeliveryRoomDate);
        customerRental.put("startDate", startDate);
        customerRental.put("endDate", endDate);
        customerRental.put("actQuitTenDate", actQuitTenDate);

        customerRental.put("leaseCount", Integer.valueOf(leaseCount));
        customerRental.put("actLeaseCount", Integer.valueOf(actLeaseCount));
        customerRental.put("lastLeaseCount", Integer.valueOf(lastLeaseCount));

        customerRental.put("appAmount", appAmount);
        customerRental.put("actAmount", actAmount);

        customerRental.put("lastAmount", lastAmount);
        customerRental.put("leaAppAmount", leaAppAmount);
        customerRental.put("leaActAmount", leaActAmount);
        customerRental.put("leaLastAmount", leaLastAmount);

        customerRental.put("allAppAmount", allAppAmount);
        customerRental.put("allActAmount", allActAmount);
        customerRental.put("allLastAmount", allLastAmount);

        customerRental.put("unitStandard", unitStandard);
        customerRental.put("unitActual", unitActual);
        customerRental.put("unitDiffer", unitDiffer);
        customerRental.put("unitRate", unitRate);

        customerRental.put("depositAmount", depositAmount);
        customerRental.put("appDepositDate", appDepositDate);
        customerRental.put("depositDate", depositDate);
        customerRental.put("endDepositAmount", endDepositAmount);

        customerRental.put("depositLastAmount", depositLastAmount);
        customerRental.put("department", department);
        customerRental.put("tenancyAdviser", tenancyAdviser);
        lst.add(customerRental);
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    result.put("customer", lst);
    return result;
  }
	protected Map _getCustomerRentalOtherPayList(Context ctx, Map param)
			throws BOSException {
    	Map result = new HashMap();
    	// 查询语句
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.* from ( ");
		sql.append(" select tb.fid as id,md.fid as mdid,md.fname_l2 as moneyDefine,bu.fid as org,sp.fname_l2 as projectName, tb.ftenCustomerDes as customer,tb.fnumber as tenancyNumber,tb.ftenancyName as tenancyName,  ");
		sql.append(" tb.ftenRoomsDes as tenRoomsDes,tb.fstartDate as startDate,tb.fendDate as endDate,tb.fendDate as actQuitTenDate,(tb.fleaseCount*tb.FLeaseTime) as leaseCount, ");
		sql.append(" us.FName_l2 as tenancyAdviser,de.fname_l2 as department from T_TEN_TenancyRoomPayListEntry trpe ");
		sql.append(" left join T_SHE_MoneyDefine md on md.FID = trpe.FMoneyDefineID ");
		sql.append(" left join T_TEN_TenancyRoomEntry tre on tre.FID = trpe.FTenRoomID ");
		sql.append(" left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID ");
		sql.append(" left join T_SHE_SellProject sp on sp.FID = tb.FSellProjectID");
		sql.append(" left join T_ORG_BaseUnit bu on bu.FID = sp.FOrgUnitID");
		sql.append(" left join T_PM_User us on us.FID = tb.FTenancyAdviserID ");
		sql.append(" left join T_ORG_BaseUnit de on de.FID = tb.FOrgUnitID ");
		sql.append(" where 1=1 ");
		sql.append(" and tb.ftenancystate in('Audited','Executing','ContinueTenancying') ");
		sql.append(" and md.fname_l2 !='"+RENT+"' ");
		sql.append(" and trpe.fappamount >0 ");
		
		if (param != null) {
			// 组织
			Set orgUnit = (Set) param.get("orgUnit");
			if (orgUnit != null && orgUnit.size() > 0) {
				sql.append(" and sp.FID in "+ FMHelper.setTran2String(orgUnit));
			}
			// 项目
			String project = (String) param.get("project");
			if (project != null) {
				sql.append(" and sp.FID = '" + project + "' ");
			}
			// 合同业务开始
			Date startDate = (Date) param.get("startDate");
			if (startDate != null) {
				sql.append(" and tb.fstartDate >= {ts '"
						+ DateHelper.getSQLBegin(startDate) + "'} ");
			}
			// 合同业务结束
			Date endDate = (Date) param.get("endDate");
			if (endDate != null) {
				sql.append(" and tb.fendDate < {ts '"
						+ DateHelper.getSQLBegin(DateHelper
								.getNextDay(endDate)) + "'} ");
			}
			if(param.get("id")!=null){
				sql.append(" and tb.Fid ='"+param.get("id").toString()+"' ");
			}
			if(param.get("customer")!=null){
				try {
					FDCCustomerInfo info = FDCCustomerFactory.getLocalInstance(ctx).getFDCCustomerInfo(
							new ObjectUuidPK(BOSUuid.read(param.get("customer").toString())));
					sql.append(" and tb.ftenCustomerDes like '%"+info.getName()+"%'");
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (UuidException e) {
					e.printStackTrace();
				}
			}
			if(param.get("moneyDefine")!=null){
				sql.append(" and md.Fid ='"+param.get("moneyDefine").toString()+"' ");
			}
			if(param.get("year")!=null){
				sql.append(" and "+param.get("year")+"=YEAR (trpe.fappDate) ");
			}
		}
		sql.append(" GROUP BY tb.fid,md.fid,md.fname_l2,bu.fid,sp.fname_l2,tb.ftenCustomerDes,tb.fnumber,tb.ftenancyName,");
		sql.append(" tb.ftenRoomsDes,tb.fstartDate,tb.fendDate,tb.FLeaseTime,us.FName_l2,tb.fleaseCount,de.fname_l2 ");
		
		sql.append(" union ");
		
		sql.append(" select tb.fid as id,md.fid as mdid,md.fname_l2 as moneyDefine,bu.fid as org,sp.fname_l2 as projectName, tb.ftenCustomerDes as customer,tb.fnumber as tenancyNumber,tb.ftenancyName as tenancyName,  ");
		sql.append(" tb.ftenRoomsDes as tenRoomsDes,tb.fstartDate as startDate,tb.fendDate as endDate,tb.fendDate as actQuitTenDate,(tb.fleaseCount*tb.FLeaseTime) as leaseCount, ");
		sql.append(" us.FName_l2 as tenancyAdviser,de.fname_l2 as department from T_TEN_TenBillOtherPay tbop ");
		sql.append(" left join T_SHE_MoneyDefine md on md.FID = tbop.FMoneyDefineID ");
		sql.append(" left join T_TEN_TenancyBill tb on tb.fid = tbop.FHeadID ");
		sql.append(" left join T_SHE_SellProject sp on sp.FID = tb.FSellProjectID");
		sql.append(" left join T_ORG_BaseUnit bu on bu.FID = sp.FOrgUnitID");
		sql.append(" left join T_PM_User us on us.FID = tb.FTenancyAdviserID ");
		sql.append(" left join T_ORG_BaseUnit de on de.FID = tb.FOrgUnitID ");
		sql.append(" where 1=1 ");
		sql.append(" and tb.ftenancystate in('Audited','Executing','ContinueTenancying') ");
		sql.append(" and md.fname_l2 !='"+RENT+"' ");
		sql.append(" and tbop.fappamount >0 ");
		if (param != null) {
			// 组织
			Set orgUnit = (Set) param.get("orgUnit");
			if (orgUnit != null && orgUnit.size() > 0) {
				sql.append(" and sp.FID in "+ FMHelper.setTran2String(orgUnit));
			}
			// 项目
			String project = (String) param.get("project");
			if (project != null) {
				sql.append(" and sp.FID = '" + project + "' ");
			}
			// 合同业务开始
			Date startDate = (Date) param.get("startDate");
			if (startDate != null) {
				sql.append(" and tb.fstartDate >= {ts '"
						+ DateHelper.getSQLBegin(startDate) + "'} ");
			}
			// 合同业务结束
			Date endDate = (Date) param.get("endDate");
			if (endDate != null) {
				sql.append(" and tb.fendDate < {ts '"
						+ DateHelper.getSQLBegin(DateHelper
								.getNextDay(endDate)) + "'} ");
			}
			if(param.get("id")!=null){
				sql.append(" and tb.Fid ='"+param.get("id").toString()+"' ");
			}
			if(param.get("customer")!=null){
				try {
					FDCCustomerInfo info = FDCCustomerFactory.getLocalInstance(ctx).getFDCCustomerInfo(
							new ObjectUuidPK(BOSUuid.read(param.get("customer").toString())));
					sql.append(" and tb.ftenCustomerDes like '%"+info.getName()+"%'");
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (UuidException e) {
					e.printStackTrace();
				}
			}
			if(param.get("moneyDefine")!=null){
				sql.append(" and md.Fid ='"+param.get("moneyDefine").toString()+"' ");
			}
			if(param.get("year")!=null){
				sql.append(" and "+param.get("year")+"=YEAR (tbop.fappDate) ");
			}
		}
		sql.append(" GROUP BY tb.fid,md.fid,md.fname_l2,bu.fid,sp.fname_l2,tb.ftenCustomerDes,tb.fnumber,tb.ftenancyName,");
		sql.append(" tb.ftenRoomsDes,tb.fstartDate,tb.fendDate,tb.FLeaseTime,us.FName_l2,tb.fleaseCount,de.fname_l2 ");
		sql.append(" ) t");
		

		sql.append(" order by t.projectName,t.tenRoomsDes ");
		
		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
		
		List lst = new ArrayList();
		Map cusMap = new HashMap();
		try {
			while (rs.next()) {
				String id = rs.getString("id");
				String mdid = rs.getString("mdid");
				String moneyDefine=rs.getString("moneyDefine");
				String projectName = rs.getString("projectName");
				String org = getCompanyByOrg(ctx,rs.getString("org"));
				String customer=rs.getString("customer");
				String tenancyNumber =rs.getString("tenancyNumber");
				String tenancyName=rs.getString("tenancyName");
				String tenRoomsDes=rs.getString("tenRoomsDes");
				BigDecimal totalBuildingArea= new BigDecimal(0);
				int totalBuildingAmount=0;
//				Date actDeliveryRoomDate=rs.getDate("actDeliveryRoomDate");
//				BigDecimal dayPrice=rs.getBigDecimal("dayPrice");
				Date startDate=rs.getDate("startDate");
				Date endDate=rs.getDate("endDate");
				Date actQuitTenDate=rs.getDate("actQuitTenDate");
				int leaseCount=rs.getInt("leaseCount");
//				Date appDepositDate=rs.getDate("appDepositDate");
				String tenancyAdviser=rs.getString("tenancyAdviser");
				String department=rs.getString("department");
				
				
				BigDecimal appAmount=new BigDecimal(0);
				BigDecimal actAmount=new BigDecimal(0); 
				BigDecimal lastAmount=new BigDecimal(0); 
				
				String sql4 ="select sum(trpe.FAppAmount) as appAmount ,isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0) as actAmount," +
					" isnull(sum(trpe.fappamount),0)-(isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0)) as lastAmount from  T_TEN_TenancyRoomEntry tre " +
					" left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID left join T_TEN_TenancyRoomPayListEntry as trpe on trpe.FTenRoomID = tre.FID left join T_SHE_MoneyDefine as md on md.FID = trpe.FMoneyDefineID  where md.fid ='"+mdid+"' " +
					" and tb.fid = '"+id+"'";
				
				if(param!=null&&param.get("year")!=null){
					sql4=sql4+" and "+param.get("year")+"=YEAR (trpe.fappDate) ";
				}
				
				IRowSet rs4  = DbUtil.executeQuery(ctx, sql4.toString());

				while (rs4.next()) {
					appAmount=rs4.getBigDecimal("appAmount");
					actAmount=rs4.getBigDecimal("actAmount");
					lastAmount=rs4.getBigDecimal("lastAmount");
				}
				
				if(appAmount==null||appAmount.compareTo(new BigDecimal(0))==0){
					String sql5 = "select sum(tbop.FAppAmount) as appAmount ,isnull(sum(tbop.FActRevAmount),0)-isnull(sum(tbop.FHasTransferredAmount),0)-isnull(sum(tbop.FHasRefundmentAmount),0)-isnull(sum(tbop.FHasAdjustedAmount),0) as actAmount," +
						" isnull(sum(tbop.fappamount),0)-(isnull(sum(tbop.FActRevAmount),0)-isnull(sum(tbop.FHasTransferredAmount),0)-isnull(sum(tbop.FHasRefundmentAmount),0)-isnull(sum(tbop.FHasAdjustedAmount),0)) as lastAmount from  T_TEN_TenBillOtherPay tbop " +
						" left join T_TEN_TenancyBill tb on tb.fid = tbop.fheadid left join T_SHE_MoneyDefine as md on md.FID = tbop.FMoneyDefineID  where md.fid ='"+mdid+"' " +
						" and tb.fid = '"+id+"'";
				
					if(param!=null&&param.get("year")!=null){
						sql5=sql5+" and "+param.get("year")+"=YEAR (tbop.fappDate) ";
					}
					
					IRowSet rs5  = DbUtil.executeQuery(ctx, sql5.toString());

					while (rs5.next()) {
						appAmount=rs5.getBigDecimal("appAmount");
						actAmount=rs5.getBigDecimal("actAmount");
						lastAmount=rs5.getBigDecimal("lastAmount");
					}
				}
				
				if(appAmount==null||appAmount.compareTo(new BigDecimal(0))==0) continue;
				
				
				String sql1 = " select isnull(sum(tre.fbuildingArea),0) as totalBuildingArea,count(tb.fid) as totalBuildingAmount from T_TEN_TenancyRoomEntry tre "+
					" left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID where tb.fid='"+id+"'";
				
				IRowSet rs1  = DbUtil.executeQuery(ctx, sql1.toString());
				while (rs1.next()){
					totalBuildingArea=rs1.getBigDecimal("totalBuildingArea");
					totalBuildingAmount=rs1.getInt("totalBuildingAmount");
				}				
				
				int actLeaseCount=0;
				
				BigDecimal leaAppAmount=new BigDecimal(0);
				BigDecimal leaActAmount=new BigDecimal(0); 
				BigDecimal leaLastAmount=new BigDecimal(0); 
				
				String sql2="select max(trpe.fleaseSeq) as fleaseSeq,isnull(sum(trpe.FAppAmount),0) as leaAppAmount ,isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0) as leaActAmount," +
					" isnull(sum(trpe.fappamount),0)-(isnull(sum(trpe.FActRevAmount),0)-isnull(sum(trpe.FHasTransferredAmount),0)-isnull(sum(trpe.FHasRefundmentAmount),0)-isnull(sum(trpe.FHasAdjustedAmount),0)) as leaLastAmount from  T_TEN_TenancyRoomEntry tre " +
					" left join T_TEN_TenancyBill tb on tb.fid = tre.FTenancyID left join T_TEN_TenancyRoomPayListEntry as trpe on trpe.FTenRoomID = tre.FID left join T_SHE_MoneyDefine md on md.FID = trpe.FMoneyDefineID  " +
					" where  md.fid ='"+mdid+"' and tb.fid = '"+id+"'";


				if(param!=null&&param.get("year")!=null&&param.get("month")!=null){
					String month=param.get("month").toString();
					if(Integer.parseInt(month)<10) month="0"+month;
					
					Calendar time=Calendar.getInstance(); 
					time.clear(); 
					time.set(Calendar.YEAR,Integer.parseInt(param.get("year").toString())); 
					time.set(Calendar.MONTH,Integer.parseInt(param.get("month").toString())-1);
					int days=time.getActualMaximum(Calendar.DAY_OF_MONTH);
					String day="";
					if(days<10){
						day="0"+days;
					}else{
						day=String.valueOf(days);
					}
					
					sql2=sql2+" and {ts '"+param.get("year")+"-"+month+"-"+day+"'} >=trpe.fstartDate ";
					sql2=sql2+" and {ts '"+param.get("year")+"-"+month+"-01'} <=trpe.fendDate ";
				}
				IRowSet rs2  = DbUtil.executeQuery(ctx, sql2.toString());
				while (rs2.next()) {
					actLeaseCount=rs2.getInt("fleaseSeq");
					leaAppAmount=rs2.getBigDecimal("leaAppAmount");
					leaActAmount=rs2.getBigDecimal("leaActAmount");
					leaLastAmount=rs2.getBigDecimal("leaLastAmount");
				}
				
				if(actLeaseCount==0){
					String sql3="select isnull(sum(tbop.FAppAmount),0) as leaAppAmount ,isnull(sum(tbop.FActRevAmount),0)-isnull(sum(tbop.FHasTransferredAmount),0)-isnull(sum(tbop.FHasRefundmentAmount),0)-isnull(sum(tbop.FHasAdjustedAmount),0) as leaActAmount," +
					" isnull(sum(tbop.fappamount),0)-(isnull(sum(tbop.FActRevAmount),0)-isnull(sum(tbop.FHasTransferredAmount),0)-isnull(sum(tbop.FHasRefundmentAmount),0)-isnull(sum(tbop.FHasAdjustedAmount),0)) as leaLastAmount from  T_TEN_TenBillOtherPay tbop " +
					" left join T_TEN_TenancyBill tb on tb.fid = tbop.fheadid left join T_SHE_MoneyDefine md on md.FID = tbop.FMoneyDefineID  " +
					" where  md.fid ='"+mdid+"' and tb.fid = '"+id+"'";
					
					if(param!=null&&param.get("year")!=null&&param.get("month")!=null){
						String month=param.get("month").toString();
						if(Integer.parseInt(month)<10) month="0"+month;
						
						Calendar time=Calendar.getInstance(); 
						time.clear(); 
						time.set(Calendar.YEAR,Integer.parseInt(param.get("year").toString())); 
						time.set(Calendar.MONTH,Integer.parseInt(param.get("month").toString())-1);
						int days=time.getActualMaximum(Calendar.DAY_OF_MONTH);
						String day="";
						if(days<10){
							day="0"+days;
						}else{
							day=String.valueOf(days);
						}
						sql2=sql2+" and {ts '"+param.get("year")+"-"+month+"-"+day+"'} >=tbop.fstartDate ";
						sql2=sql2+" and {ts '"+param.get("year")+"-"+month+"-01'} <=tbop.fendDate ";
					}
					IRowSet rs3  = DbUtil.executeQuery(ctx, sql3.toString());
					while (rs3.next()) {
						leaAppAmount=rs3.getBigDecimal("leaAppAmount");
						leaActAmount=rs3.getBigDecimal("leaActAmount");
						leaLastAmount=rs3.getBigDecimal("leaLastAmount");
					}
				}
				
				int lastLeaseCount=leaseCount-actLeaseCount;
				
				Map customerRental = new HashMap();
				
				customerRental.put("id", id);
				customerRental.put("org", org);
				customerRental.put("moneyDefine", moneyDefine);
				customerRental.put("projectName", projectName);
				customerRental.put("customer", customer);
				customerRental.put("tenancyNumber", tenancyNumber);
				customerRental.put("tenancyName", tenancyName);
				
				customerRental.put("tenRoomsDes", tenRoomsDes);
				customerRental.put("totalBuildingArea", totalBuildingArea);
				customerRental.put("totalBuildingAmount", ""+totalBuildingAmount);
			
//				customerRental.put("actDeliveryRoomDate", actDeliveryRoomDate);
//				customerRental.put("dayPrice", dayPrice);
				customerRental.put("startDate", startDate);
				customerRental.put("endDate", endDate);
				customerRental.put("actQuitTenDate", actQuitTenDate);
				
				customerRental.put("leaseCount", ""+leaseCount);
				customerRental.put("actLeaseCount", ""+actLeaseCount);
				customerRental.put("lastLeaseCount", ""+lastLeaseCount);
				customerRental.put("appAmount", appAmount);
				customerRental.put("actAmount", actAmount);
				
				customerRental.put("lastAmount", lastAmount);
				customerRental.put("leaAppAmount", leaAppAmount);
				customerRental.put("leaActAmount", leaActAmount);
				customerRental.put("leaLastAmount", leaLastAmount);
				
//				customerRental.put("unitStandard", unitStandard);
//				customerRental.put("unitActual", unitActual);
//				
//				customerRental.put("unitDiffer", unitDiffer);
//				customerRental.put("unitRate", unitRate);
//				customerRental.put("totalStandard", totalStandard);
//				customerRental.put("totalActual", totalActual);
//				customerRental.put("totalDiffer", totalDiffer);
//				
//				customerRental.put("totalRate", totalRate);
//				customerRental.put("actStandard", actStandard);
//				customerRental.put("actActual", actActual);
//				customerRental.put("actDiffer", actDiffer);
//				customerRental.put("actRate", actRate);
//				
//				customerRental.put("totalLastAmount", totalLastAmount);
//				customerRental.put("depositAmount", depositAmount);
//				customerRental.put("appDepositDate", appDepositDate);
//				customerRental.put("depositDate", depositDate);
//				customerRental.put("endDepositAmount", endDepositAmount);
//							
//				customerRental.put("depositLastAmount", depositLastAmount);
				customerRental.put("department", department);
				customerRental.put("tenancyAdviser", tenancyAdviser);
				
				lst.add(customerRental);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result.put("customer", lst);
        return result;
    }
	
	public BigDecimal toMonthPrice(String type,BigDecimal price){
		if(type==null) return new BigDecimal(0);
		if(RentTypeEnum.RENTBYYEAR_VALUE.equals(type)) return price.divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP);
		if(RentTypeEnum.RENTBYQUARTER_VALUE.equals(type)) return price.divide(new BigDecimal(3), 2, BigDecimal.ROUND_HALF_UP);
		if(RentTypeEnum.RENTBYMONTH_VALUE.equals(type)) return price;
		if(RentTypeEnum.RENTBYWEEK_VALUE.equals(type)) return (price.multiply(new BigDecimal(54))).divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP);
		if(RentTypeEnum.RENTBYYEAR_VALUE.equals(type)) return (price.multiply(new BigDecimal(365))).divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP);
		
		return price;
	}
	//日单价计算 @by huanghefh
	public BigDecimal toDayPrice(String type,BigDecimal price){
		if(type==null) return new BigDecimal(0);
		if(RentTypeEnum.RENTBYYEAR_VALUE.equals(type)) return price.divide(new BigDecimal(365), 2, BigDecimal.ROUND_HALF_UP);
		if(RentTypeEnum.RENTBYQUARTER_VALUE.equals(type)) return price.divide(new BigDecimal(90), 2, BigDecimal.ROUND_HALF_UP);
		if(RentTypeEnum.RENTBYMONTH_VALUE.equals(type)) return price.divide(new BigDecimal(30), 2, BigDecimal.ROUND_HALF_UP);
		if(RentTypeEnum.RENTBYWEEK_VALUE.equals(type)) return price.divide(new BigDecimal(7), 2, BigDecimal.ROUND_HALF_UP);
		if(RentTypeEnum.RENTBYDAY_VALUE.equals(type)) return price;
		
		return price;
	}
	
}