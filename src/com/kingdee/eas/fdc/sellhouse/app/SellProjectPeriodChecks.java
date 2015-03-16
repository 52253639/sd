package com.kingdee.eas.fdc.sellhouse.app;

import java.sql.SQLException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolFactory;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolInfo;
import com.kingdee.eas.basedata.assistant.SystemStatusInfo;
import com.kingdee.eas.common.BizBalanceException;
import com.kingdee.eas.common.BizCheckResult;
import com.kingdee.eas.common.BizHasDealException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.app.IBizHasDeal;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class SellProjectPeriodChecks implements IBizHasDeal {

	public BizCheckResult checkHasDeal(Context ctx, BOSUuid id)
			throws BizHasDealException {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("startPeriod.id");
		sic.add("startPeriod.beginDate");
		sic.add("startPeriod.endDate");
		sic.add("startPeriod.number");
		sic.add("systemStatus.name");
		sic.add("company.id");
		try {
			SystemStatusCtrolInfo systemInfo  = SystemStatusCtrolFactory.getLocalInstance(ctx).getSystemStatusCtrolInfo(new ObjectUuidPK(id),sic);
			
			SystemStatusInfo systemStatusInfo = systemInfo.getSystemStatus();
			
			// 1818即表示SystemEnum.FDC_SHE，客户打过房地产补丁，执行了房地产脚本，后来后卸载了补丁，
			// 在与总帐联用时，执行到这里会报找不到FDC_SHE字段的错误，故这里直接用值表示
			//if(!SystemEnum.FDC_SHE.equals(systemStatusInfo.getName())){
			if(1818 != systemStatusInfo.getName().getValue()){
				return null;
			}
			
			//同步销售项目的启用期间、当前财务期间
			String comId = systemInfo.getCompany().getId().toString();
			StringBuffer sql = new StringBuffer();
			IRowSet rs = null;
			
			PeriodInfo period = systemInfo.getStartPeriod();
			if(period!=null)
			{
				//如果存在结束初始化的销售项目
				sql.append("select Fid from T_SHE_SellProject where  FIsEndInit=1 and FOrgUnitID=? and FSalePeriodID in 	\r\n")
				.append("( select fid from T_BD_period where fnumber<? )");
				rs = DbUtil.executeQuery(ctx,sql.toString(),new Object[]{comId,new Integer(period.getNumber())});
				if(rs!=null && rs.next()){
					throw new EASBizException(new NumericExceptionSubItem("102","当前组织存在已经结束初始化的销售项目"));
				}
			}else{
				return null;
			}
			
			String periodId = period.getId().toString();
			sql = new StringBuffer();
			sql.append("update T_SHE_SellProject  set FSalePeriodID=?,FSaleNowPeriodID=?  \r\n");
			sql.append("  where FIsEndInit<>1 and FOrgUnitID=?		\r\n");
			DbUtil.execute(ctx,sql.toString(),new Object[]{periodId,periodId,comId});
		}catch (EASBizException e) {
			e.printStackTrace();
			throw new BizHasDealException(new NumericExceptionSubItem(e.getCode(), e.getMessage()), e);
		} catch (BOSException e) {
			e.printStackTrace();
			throw new BizHasDealException(BizBalanceException.SQL_ERR, e);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}

}
