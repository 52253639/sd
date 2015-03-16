package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;
import javax.ejb.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
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
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fm.common.DateHelper;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArrearsFacadeControllerBean extends AbstractArrearsFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.ArrearsFacadeControllerBean");
    private static final String LIQUIDATED="违约金";
    private static final String COMPANY="公司";
    protected Map _getArrearsList(Context ctx, Map param)throws BOSException
    {

		Map result = new HashMap();

		// 查询语句
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct ");
		sql.append(" tb.FID as id, ");
		
		sql.append(" bu.fid as org, ");
		sql.append(" lm.frate as rate, ");
		sql.append(" lm.famount as appLiquidated, ");
		sql.append(" tbop.factrevamount as actLiquidated, ");
		sql.append(" lm.freliefamount as reliefLiquidated, ");
		sql.append(" isnull(tbop.fappamount,0)-isnull(tbop.factrevamount,0) as unPayLiquidated, ");
		sql.append(" de.fname_l2 as department, ");
		
		sql.append(" sp.FName_l2 as projectName, ");
		sql.append(" tb.FTenancyName as contractName, ");
		sql.append(" (CASE WHEN md.FMoneyType = 'DepositAmount' then '1' else case WHEN md.FMoneyType = 'RentAmount' then '2' else '3' END END) as moneyType, ");
		sql.append(" md.FName_l2 as moneyName, ");
		sql.append(" trpe.FLeaseSeq as lease, ");
		sql.append(" tre.FRoomLongNum as room, ");
		sql.append(" trpe.FAppDate as appDate, ");
		sql.append(" trpe.FAppAmount as appAmount, ");
		//增加费用的起始日期和结束日期 eric_wang 2010.08.23
		sql.append(" trpe.FStartDate as startDate, ");
		sql.append(" trpe.FEndDate as endDate, ");
		
		sql.append(" trpe.FActRevDate as actDate, ");
		sql.append(" isnull(trpe.FActRevAmount,0)-isnull(trpe.FHasTransferredAmount,0)-isnull(trpe.FHasRefundmentAmount,0)-isnull(trpe.FHasAdjustedAmount,0) as actAmount, ");
		sql.append(" isnull(trpe.fappamount,0)-(isnull(trpe.FActRevAmount,0)-isnull(trpe.FHasTransferredAmount,0)-isnull(trpe.FHasRefundmentAmount,0)-isnull(trpe.FHasAdjustedAmount,0)) as unPayAmount, ");
		sql.append(" us.FName_l2 as advisor ");

		sql.append(" from T_TEN_TenancyBill as tb ");
		sql.append(" left join T_SHE_SellProject as sp ");
		sql.append(" on sp.FID = tb.FSellProjectID ");
		sql.append(" left join T_ORG_BaseUnit as bu ");
		sql.append(" on bu.FID = sp.FOrgUnitID ");
		sql.append(" left join T_TEN_TenancyRoomEntry as tre ");
		sql.append(" on tre.FTenancyID = tb.FID ");
		sql.append(" left join T_TEN_TenancyRoomPayListEntry as trpe ");
		sql.append(" on trpe.FTenRoomID = tre.FID ");
		sql.append(" left join T_SHE_MoneyDefine as md ");
		sql.append(" on md.FID = trpe.FMoneyDefineID ");
		sql.append(" left join T_SHE_Room as rm ");
		sql.append(" on rm.FID = tre.FRoomID ");
		sql.append(" left join T_PM_User as us ");
		sql.append(" on us.FID = tb.FTenancyAdviserID ");
		sql.append(" left join T_TEN_TenancyCustomerEntry as tce ");
		sql.append(" on tce.FTenancyBillID = tb.FID ");
		sql.append(" left join T_TEN_QuitTenancy as qt ");
		sql.append(" on qt.FTenancyBillID = tb.FID ");
		sql.append(" left join T_ORG_BaseUnit as de ");
		sql.append(" on de.FID = tb.FOrgUnitID ");
		sql.append(" left join T_TEN_LiquidatedManage as lm ");
		sql.append(" on trpe.FId = lm.FTenBillOtherPayID ");
		sql.append(" left join T_TEN_TenBillOtherPay as tbop ");
		sql.append(" on tbop.FId = lm.FGenOtherPayID ");
		
		
		// 过滤条件
		sql.append(" where 1 = 1 ");
		// 没有退租、或者已退租且退租日期大于起始日期
		sql.append(" and ((qt.FID is null or qt.FState <> '4AUDITTED') or (qt.FState = '4AUDITTED' and qt.FQuitDate > trpe.FStartDate) ) ");
		if (param != null) {
			// 应付金额不为0
			sql
					.append(" and (trpe.FAppAmount is null or trpe.FAppAmount <> 0) ");
			// 组织
			Set orgUnit = (Set) param.get("orgUnit");
			if (orgUnit != null && orgUnit.size() > 0) {
				sql
						.append(" and sp.FID in "
								+ FMHelper.setTran2String(orgUnit));
			}
			// 项目
			String project = (String) param.get("project");
			if (project != null) {
				sql.append(" and sp.FID = '" + project + "' ");
			}
			// 合同业务开始
			Date billBeginDate = (Date) param.get("billBeginDate");
			if (billBeginDate != null) {
				sql.append(" and tb.FTenancyDate >= {ts '"
						+ DateHelper.getSQLBegin(billBeginDate) + "'} ");
			}
			// 合同业务结束
			Date billEndDate = (Date) param.get("billEndDate");
			if (billEndDate != null) {
				sql.append(" and tb.FTenancyDate < {ts '"
						+ DateHelper.getSQLBegin(DateHelper
								.getNextDay(billEndDate)) + "'} ");
			}
			// 客户
			String f7Customer = (String) param.get("f7Customer");
			if (f7Customer != null) {
				sql.append(" and tce.FFdcCustomerID = '" + f7Customer + "' ");
			}
			// 欠款类型
			Boolean delayAndNo = (Boolean) param.get("delayAndNo");
			Boolean delayAndYes = (Boolean) param.get("delayAndYes");
			Boolean noDelay = (Boolean) param.get("noDelay");
			Date now = DateHelper.getSQLBegin(new Date());
			if (delayAndNo != null && delayAndNo.booleanValue()) {
				sql.append(" and trpe.FAppDate < {ts '" + now + "'} ");
				sql.append(" and isnull(trpe.FAppAmount,0) - isnull(trpe.FActRevAmount,0) > 0 ");
			} else if (delayAndYes != null && delayAndYes.booleanValue()) {
				sql.append(" and trpe.FAppDate < {ts '" + now + "'} ");
				sql.append(" and isnull(trpe.FAppAmount,0) - isnull(trpe.FActRevAmount,0) <= 0 ");
			} else if (noDelay != null && noDelay.booleanValue()) {
				sql.append(" and trpe.FAppDate >= {ts '" + now + "'} ");
			}
			// 逾期天数
			Integer arrearageDay = (Integer) param.get("arrearageDay");
			if (arrearageDay != null && arrearageDay.intValue() != 0) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -arrearageDay.intValue());
				sql.append(" and trpe.FAppDate = {ts '"
						+ DateHelper.getSQLBegin(cal.getTime()).toString()
								.trim() + "'} ");
			}
			// 款项类型
			Boolean deposit = (Boolean) param.get("deposit");
			Boolean rent = (Boolean) param.get("rent");
			Boolean expenses = (Boolean) param.get("expenses");
			
			Set moneyTypes = new HashSet();
			
			if (deposit != null && deposit.booleanValue()) {
				moneyTypes.add(MoneyTypeEnum.DEPOSITAMOUNT_VALUE);
			}
			if (rent != null && rent.booleanValue()) {
				moneyTypes.add(MoneyTypeEnum.RENTAMOUNT_VALUE);
			}
			if (expenses != null && expenses.booleanValue()) {
				moneyTypes.add(MoneyTypeEnum.PERIODICITYAMOUNT_VALUE);
			}
			
			sql.append(" and md.FMoneyType in "
					+ FMHelper.setTran2String(moneyTypes));
			
			// 款项应付日期开始
			Date appBeginDate = (Date) param.get("appBeginDate");
			if (appBeginDate != null) {
				sql.append(" and trpe.FAppDate >= {ts '"
						+ DateHelper.getSQLBegin(appBeginDate) + "'} ");
			}
			// 款项应付日期结束
			Date appEndDate = (Date) param.get("appEndDate");
			if (appBeginDate != null) {
				sql.append(" and trpe.FAppDate < {ts '"
						+ DateHelper.getSQLBegin(DateHelper
								.getNextDay(appEndDate)) + "'} ");
			}
			if(param.get("id")!=null){
				sql.append(" and tb.Fid ='"+param.get("id").toString()+"' ");
			}
		}
		if (param != null) {
			Boolean otherPay =(Boolean) param.get("otherPay");
			Set otherMoneyTypes = new HashSet();
			if (otherPay != null && otherPay.booleanValue()) {
				otherMoneyTypes.add(MoneyTypeEnum.LATEFEE_VALUE);
				otherMoneyTypes.add(MoneyTypeEnum.COMMISSIONCHARGE_VALUE);
				otherMoneyTypes.add(MoneyTypeEnum.FITMENTAMOUNT_VALUE);
				otherMoneyTypes.add(MoneyTypeEnum.ELSEAMOUNT_VALUE);
				otherMoneyTypes.add(MoneyTypeEnum.REPLACEFEE_VALUE);
				otherMoneyTypes.add(MoneyTypeEnum.BREACHFEE_VALUE);
				
				sql.append(" union ");
				
				sql.append(" select distinct ");
				sql.append(" tb.FID as id, ");
				
				sql.append(" bu.fid as org, ");
				sql.append(" lm.frate as rate, ");
				sql.append(" lm.famount as appLiquidated, ");
				sql.append(" tbop.factrevamount as actLiquidated, ");
				sql.append(" lm.freliefamount as reliefLiquidated, ");
				sql.append(" isnull(tbop.fappamount,0)-isnull(tbop.factrevamount,0) as unPayLiquidated, ");
				sql.append(" de.fname_l2 as department, ");
				
				sql.append(" sp.FName_l2 as projectName, ");
				sql.append(" tb.FTenancyName as contractName, ");
				sql
						.append(" (CASE WHEN md.FMoneyType = 'LateFee' then '4' else case WHEN md.FMoneyType = 'CommissionCharge' then '5' else case WHEN md.FMoneyType = 'FitmentAmount' then '6' else case WHEN md.FMoneyType = 'ElseAmount' then '7' else case WHEN md.FMoneyType = 'ReplaceFee' then '8' else '9' END END END END END ) as moneyType, ");
				sql.append(" md.FName_l2 as moneyName, ");
				sql.append(" '' as lease, ");
				sql.append(" '' as room, ");
				sql.append(" tbopay.FAppDate as appDate, ");
				sql.append(" tbopay.FAppAmount as appAmount, ");
				//增加费用的起始日期和结束日期 eric_wang 2010.08.23
				sql.append(" tbopay.FStartDate as startDate, ");
				sql.append(" tbopay.FEndDate as endDate, ");
				
				sql.append(" tbopay.FActRevDate as actDate, ");
				sql.append(" isnull(tbopay.FActRevAmount,0)-isnull(tbopay.FHasTransferredAmount,0)-isnull(tbopay.FHasRefundmentAmount,0)-isnull(tbopay.FHasAdjustedAmount,0) as actAmount, ");
				sql.append(" isnull(tbopay.fappamount,0)-(isnull(tbopay.FActRevAmount,0)-isnull(tbopay.FHasTransferredAmount,0)-isnull(tbopay.FHasRefundmentAmount,0)-isnull(tbopay.FHasAdjustedAmount,0)) as unPayAmount, ");
				sql.append(" us.FName_l2 as advisor ");

				sql.append(" from T_TEN_TenancyBill as tb ");
				sql.append(" left join T_SHE_SellProject as sp ");
				sql.append(" on sp.FID = tb.FSellProjectID ");
				sql.append(" left join T_ORG_BaseUnit as bu ");
				sql.append(" on bu.FID = sp.FOrgUnitID ");
				sql.append(" left join T_TEN_TenBillOtherPay as tbopay ");
				sql.append(" on tbopay.FHeadID = tb.FID ");
				sql.append(" left join T_SHE_MoneyDefine as md ");
				sql.append(" on md.FID = tbopay.FMoneyDefineID ");
				sql.append(" left join T_PM_User as us ");
				sql.append(" on us.FID = tb.FTenancyAdviserID ");
				sql.append(" left join T_TEN_TenancyCustomerEntry as tce ");
				sql.append(" on tce.FTenancyBillID = tb.FID ");
				sql.append(" left join T_TEN_QuitTenancy as qt ");
				sql.append(" on qt.FTenancyBillID = tb.FID ");
				sql.append(" left join T_ORG_BaseUnit as de ");
				sql.append(" on de.FID = tb.FOrgUnitID ");
				sql.append(" left join T_TEN_LiquidatedManage as lm ");
				sql.append(" on tbopay.FId = lm.FTenBillOtherPayID ");
				sql.append(" left join T_TEN_TenBillOtherPay as tbop ");
				sql.append(" on tbop.FId = lm.FGenOtherPayID ");
				
				
				// 过滤条件
				sql.append(" where 1 = 1 ");
				// 没有退租、或者已退租且退租日期大于起始日期
				sql.append(" and ((qt.FID is null or qt.FState <> '4AUDITTED') or (qt.FState = '4AUDITTED' and qt.FQuitDate > tbopay.FStartDate) ) ");
			
				// 应付金额不为0
				sql
						.append(" and (tbopay.FAppAmount is null or tbopay.FAppAmount <> 0) ");
				// 组织
				Set orgUnit = (Set) param.get("orgUnit");
				if (orgUnit != null && orgUnit.size() > 0) {
					sql
							.append(" and sp.FID in "
									+ FMHelper.setTran2String(orgUnit));
				}
				// 项目
				String project = (String) param.get("project");
				if (project != null) {
					sql.append(" and sp.FID = '" + project + "' ");
				}
				// 合同业务开始
				Date billBeginDate = (Date) param.get("billBeginDate");
				if (billBeginDate != null) {
					sql.append(" and tb.FTenancyDate >= {ts '"
							+ DateHelper.getSQLBegin(billBeginDate) + "'} ");
				}
				// 合同业务结束
				Date billEndDate = (Date) param.get("billEndDate");
				if (billEndDate != null) {
					sql.append(" and tb.FTenancyDate < {ts '"
							+ DateHelper.getSQLBegin(DateHelper
									.getNextDay(billEndDate)) + "'} ");
				}
				// 客户
				String f7Customer = (String) param.get("f7Customer");
				if (f7Customer != null) {
					sql.append(" and tce.FFdcCustomerID = '" + f7Customer + "' ");
				}
				// 欠款类型
				Boolean delayAndNo = (Boolean) param.get("delayAndNo");
				Boolean delayAndYes = (Boolean) param.get("delayAndYes");
				Boolean noDelay = (Boolean) param.get("noDelay");
				Date now = DateHelper.getSQLBegin(new Date());
				if (delayAndNo != null && delayAndNo.booleanValue()) {
					sql.append(" and tbopay.FAppDate < {ts '" + now + "'} ");
					sql
							.append(" and isnull(tbopay.FAppAmount,0) - isnull(tbopay.FActRevAmount,0) > 0 ");
				} else if (delayAndYes != null && delayAndYes.booleanValue()) {
					sql.append(" and tbopay.FAppDate < {ts '" + now + "'} ");
					sql
							.append(" and isnull(tbopay.FAppAmount,0) - isnull(tbopay.FActRevAmount,0) <= 0 ");
				} else if (noDelay != null && noDelay.booleanValue()) {
					sql.append(" and tbopay.FAppDate >= {ts '" + now + "'} ");
				}
				// 逾期天数
				Integer arrearageDay = (Integer) param.get("arrearageDay");
				if (arrearageDay != null && arrearageDay.intValue() != 0) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -arrearageDay.intValue());
					sql.append(" and tbopay.FAppDate = {ts '"
							+ DateHelper.getSQLBegin(cal.getTime()).toString()
									.trim() + "'} ");
				}
				
				sql.append(" and md.FMoneyType in "+ FMHelper.setTran2String(otherMoneyTypes));
				
				// 款项应付日期开始
				Date appBeginDate = (Date) param.get("appBeginDate");
				if (appBeginDate != null) {
					sql.append(" and tbopay.FAppDate >= {ts '"
							+ DateHelper.getSQLBegin(appBeginDate) + "'} ");
				}
				// 款项应付日期结束
				Date appEndDate = (Date) param.get("appEndDate");
				if (appBeginDate != null) {
					sql.append(" and tbopay.FAppDate < {ts '"
							+ DateHelper.getSQLBegin(DateHelper
									.getNextDay(appEndDate)) + "'} ");
				}
				if(param.get("id")!=null){
					sql.append(" and tb.Fid ='"+param.get("id").toString()+"' ");
				}
				
				sql.append(" and md.fname_l2 !='"+LIQUIDATED+"'");
			}
		}
		sql.append(" order by tb.FID,sp.FName_l2,moneyType,md.FName_l2 ");
		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
		// 合同ID集合，用于查询客户
		Set ids = new HashSet();
		// 用于返回的结果集
		List lst = new ArrayList();
		Map cusMap = new HashMap();
		try {
			while (rs.next()) {
				String id = rs.getString("id");
				String projectName = rs.getString("projectName");
				
				String org = getCompanyByOrg(ctx,rs.getString("org"));
				BigDecimal rate =rs.getBigDecimal("rate");
				BigDecimal appLiquidated =rs.getBigDecimal("appLiquidated");
				BigDecimal actLiquidated =rs.getBigDecimal("actLiquidated");
				BigDecimal reliefLiquidated =rs.getBigDecimal("reliefLiquidated");
				BigDecimal unPayLiquidated =rs.getBigDecimal("unPayLiquidated");
				
				String contractName = rs.getString("contractName");
				String moneyType = rs.getString("moneyType");
				String moneyName = rs.getString("moneyName");
				int lease = rs.getInt("lease");
				String room = rs.getString("room");
				Date appDate = rs.getDate("appDate");
				BigDecimal appAmount = rs.getBigDecimal("appAmount");
				Date actDate = rs.getDate("actDate");
				Date startDate = rs.getDate("startDate");
				Date endDate = rs.getDate("endDate");
				BigDecimal actAmount = rs.getBigDecimal("actAmount");
				BigDecimal unPayAmount = rs.getBigDecimal("unPayAmount");
				// int arrearageDay = rs.getInt("arrearageDay");
				String advisor = rs.getString("advisor");
				String department =rs.getString("department");
				
				ids.add(id);

				Map tenancy = new HashMap();
				tenancy.put("id", id);
				
				tenancy.put("org", org);
				tenancy.put("rate", rate);
				tenancy.put("appLiquidated", appLiquidated);
				tenancy.put("actLiquidated", actLiquidated);
				tenancy.put("reliefLiquidated", reliefLiquidated);
				tenancy.put("unPayLiquidated", unPayLiquidated);
				
				tenancy.put("projectName", projectName);
				tenancy.put("contractName", contractName);
				tenancy.put("moneyType", moneyType);
				tenancy.put("moneyName", moneyName);
				tenancy.put("lease", new Integer(lease));
				tenancy.put("room", room);
				tenancy.put("appDate", appDate);
				tenancy.put("appAmount", appAmount);
				tenancy.put("actDate", actDate);
				tenancy.put("startDate", startDate);
				tenancy.put("endDate", endDate);
				tenancy.put("actAmount", actAmount);
				tenancy.put("unPayAmount", unPayAmount);
				
				if (unPayAmount == null
						|| unPayAmount.compareTo(FDCHelper.ZERO) > 0) {
					Date now = DateHelper.getDayBegin();
					tenancy.put("arrearageDay", new Integer(DateHelper
							.getDiffDays(appDate, now) - 1));
				}
				tenancy.put("advisor", advisor);
				tenancy.put("department", department);
				lst.add(tenancy);
			}
			// 查询客户
			if (ids.size() > 0) {
				sql = new StringBuffer();
				sql.append(" select ");
				sql.append(" tb.FID as id, ");
				sql.append(" fc.FName_l2 as customer ");

				sql.append(" from T_TEN_TenancyBill as tb ");
				sql.append(" left join T_TEN_TenancyCustomerEntry as tce ");
				sql.append(" on tce.FTenancyBillID = tb.FID ");
				sql.append(" left join T_SHE_FDCCustomer as fc ");
				sql.append(" on fc.FID = tce.FFdcCustomerID ");

				sql.append(" where tb.FID in " + FMHelper.setTran2String(ids));
				sql.append(" order by tb.FID ");

				rs = DbUtil.executeQuery(ctx, sql.toString());
				String oldId = null;
				StringBuffer cus = new StringBuffer();
				while (rs.next()) {
					String id = rs.getString("id");
					String customer = rs.getString("customer");
					if (oldId == null || (id != null && id.equals(oldId))) {
						cus.append(customer).append(",");
					} else {
						cusMap.put(oldId, cus.deleteCharAt(cus.length() - 1)
								.toString());
						cus = new StringBuffer();
						cus.append(customer).append(",");
					}
					if (rs.isLast()) {
						cusMap.put(id, cus.deleteCharAt(cus.length() - 1)
								.toString());
					}
					oldId = id;
				}
			}
			result.put("tenancy", lst);
			result.put("customers", cusMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}

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
}