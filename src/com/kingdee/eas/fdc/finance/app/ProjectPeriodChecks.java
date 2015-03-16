package com.kingdee.eas.fdc.finance.app;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolFactory;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolInfo;
import com.kingdee.eas.basedata.assistant.SystemStatusInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.BizBalanceException;
import com.kingdee.eas.common.BizCheckResult;
import com.kingdee.eas.common.BizHasDealException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.app.IBizHasDeal;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusException;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class ProjectPeriodChecks implements IBizHasDeal {

	public BizCheckResult checkHasDeal(Context ctx, BOSUuid id) 	throws BizHasDealException {

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("startPeriod.id");
		sic.add("startPeriod.beginDate");
		sic.add("startPeriod.endDate");
		sic.add("startPeriod.number");
		sic.add("systemStatus.name");
		sic.add("company.id");
		//sic.add("startDate");
		try {
			SystemStatusCtrolInfo systemInfo  = SystemStatusCtrolFactory.getLocalInstance(ctx).getSystemStatusCtrolInfo(new ObjectUuidPK(id),sic);
			
			SystemStatusInfo systemStatusInfo = systemInfo.getSystemStatus();
			
			//与总帐联用，卸载merge补丁报错，改为值
			if(!(37==systemStatusInfo.getName().getValue())){
				return null;
			}
			
			String comId = systemInfo.getCompany().getId().toString();
			StringBuffer sql = new StringBuffer();
			IRowSet rs = null;
		
			// 启用成本财务一体化
			boolean isIncorporation = FDCUtils.IsInCorporation(ctx, comId);
			if (!isIncorporation) {
				return null;
			}
			//同步工程项目的启用期间、当前成本期间、当前财务期间
			PeriodInfo period = systemInfo.getStartPeriod();
			if(period==null){
				// 启用成本财务一体化				
				if (isIncorporation){
					throw new ProjectPeriodStatusException(ProjectPeriodStatusException.NOT_COM_START);
				}
			}else{
	        	//如果存在结束初始化的工程项目
				sql.append("select Fid from T_FNC_ProjectPeriodStatus where  FIsClosed=1 and FOrgUnitID=? and FSTARTPERIODID in 	\r\n")
				.append("( select fid from T_BD_period where fnumber<? )");
				rs = DbUtil.executeQuery(ctx,sql.toString(),new Object[]{comId,new Integer(period.getNumber())});
				if(rs!=null && rs.next()){
					throw new ProjectPeriodStatusException(ProjectPeriodStatusException.HASCLOSED);
				}
			}
			String periodId = period.getId().toString();
			sql = new StringBuffer();
			sql.append("update T_FNC_ProjectPeriodStatus  set FStartPeriodID=?,FCostPeriodID=?,FFinacialPeriodID=? where fid in		\r\n");
			sql.append(" ( select a.Fid from T_FNC_ProjectPeriodStatus 	a															\r\n");
			sql.append(" inner join t_fdc_curproject c on c.fid=a.fprojectid	 where a. FIsClosed<>1 and a.FOrgUnitID=?	and c.FstartDate<=?	)		\r\n");
			DbUtil.execute(ctx,sql.toString(),new Object[]{periodId,periodId,periodId,comId,period.getEndDate()});
			
			//对于日期大于结束期间末日的
			sql = new StringBuffer();
			sql.append("  select a.Fid,c.FstartDate from T_FNC_ProjectPeriodStatus 	a															\r\n");
			sql.append(" inner join t_fdc_curproject c on c.fid=a.fprojectid	 where  a. FIsClosed<>1 and  a.FOrgUnitID=?	and c.FstartDate>?			\r\n");
			rs = DbUtil.executeQuery(ctx,sql.toString(),new Object[]{comId,period.getEndDate()});
			
			sql = new StringBuffer();
			sql.append("update T_FNC_ProjectPeriodStatus  set FStartPeriodID=?,FCostPeriodID=?,FFinacialPeriodID=? where fid =?		\r\n");
			CompanyOrgUnitInfo companyInfo = null ;	
	        Map comMap = new HashMap();
			while(rs!=null && rs.next()){
				String fid = rs.getString("Fid");
				Date startDate = rs.getDate("FstartDate");
				
				if(comMap.containsKey(comId)){
					companyInfo = (CompanyOrgUnitInfo)comMap.get(comId);
				}else{
					companyInfo = GlUtils.getCurrentCompany(ctx, comId,null,false) ;
					comMap.put(comId,companyInfo);
				}
		        PeriodInfo startperiod =  
		        	FDCUtils.fetchPeriod(ctx,companyInfo.getAccountPeriodType().getId().toString(),startDate);
		        if(startperiod==null){
		        	throw new ProjectPeriodStatusException(ProjectPeriodStatusException.BOOKEDDATENOTPERIOD);
		        }
		        periodId = startperiod.getId().toString();
		        DbUtil.execute(ctx,sql.toString(),new Object[]{periodId,periodId,periodId,fid});
			}
			
		} catch (EASBizException e) {
			e.printStackTrace();
			throw new BizHasDealException(new NumericExceptionSubItem(e.getCode(), e.getMessage()), e);
		} catch (BOSException e) {
			e.printStackTrace();
			throw new BizHasDealException(BizBalanceException.SQL_ERR, e);
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			throw new BizHasDealException(BizHasDealException.SQL_ERR, e);
		}
		
		return null;
	}

}
