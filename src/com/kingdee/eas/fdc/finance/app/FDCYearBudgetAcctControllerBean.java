package com.kingdee.eas.fdc.finance.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

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
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import java.math.BigDecimal;

import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctHelper;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctDataInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctException;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fdc.finance.FDCYearBudgetAcctCollection;
import com.kingdee.eas.fdc.finance.FDCYearBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCYearBudgetAcctFactory;
import com.kingdee.eas.fdc.finance.FDCYearBudgetAcctInfo;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctCollection;
import com.kingdee.jdbc.rowset.IRowSet;

public class FDCYearBudgetAcctControllerBean extends AbstractFDCYearBudgetAcctControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.FDCYearBudgetAcctControllerBean");

	public FDCBudgetAcctInfo getMyFDCBudgetAcctInfo(Context ctx,String id,String prjId,FDCBudgetPeriodInfo date) throws EASBizException, BOSException {
		if(id!=null){
			return getFDCYearBudgetAcctInfo(ctx, new ObjectUuidPK(id),getSelectors());
		}else{
			return new FDCYearBudgetAcctInfo();
		}
		
	}

	public FDCBudgetAcctEntryInfo getMyFDCBudgetAcctEntryInfo() throws EASBizException, BOSException {
		return new FDCYearBudgetAcctEntryInfo();
	}
	protected void handleRecension(Context ctx,FDCBudgetAcctInfo budgetAcctInfo) throws BOSException {
		super.handleRecension(ctx,budgetAcctInfo);
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select max(fvernumber) as vernumber from t_fnc_fdcyearbudgetacct where fprojectid=? and ffdcperiodId=?");
		builder.addParam(budgetAcctInfo.getCurProject().getId().toString());
		builder.addParam(budgetAcctInfo.getFdcPeriod().getId().toString());
		IRowSet row=builder.executeQuery();
		try{
			if(row.next()){
				budgetAcctInfo.setVerNumber(row.getFloat("vernumber")+0.1f);
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		
	}
	
	protected Map getCostMap(Context ctx, FDCBudgetAcctInfo info) throws BOSException, EASBizException {
		Map costMap = super.getCostMap(ctx, info);
		//取截止上年累计成本
		Map map=FDCBudgetAcctHelper.getLastYearCost(ctx, info.getCurProject().getId().toString(), info.getFdcPeriod());
		for(Iterator iter=map.keySet().iterator();iter.hasNext();){
			String costAccountId=(String)iter.next();
			BigDecimal dyncostamt=(BigDecimal)map.get(costAccountId);
			FDCBudgetAcctDataInfo dataInfo=null;
			if(costMap.get(costAccountId)!=null){
				dataInfo=(FDCBudgetAcctDataInfo)costMap.get(costAccountId);
			}else{
				dataInfo=new FDCBudgetAcctDataInfo();
				costMap.put(costAccountId, dataInfo);
			}
			dataInfo.setLstCost(dyncostamt);
		}
		return costMap;
	}
	
	protected Map getPayedMap(Context ctx, FDCBudgetAcctInfo budgetAcctInfo) throws BOSException {
		Map payedMap=FDCBudgetAcctHelper.getLastYearRequestedAmt(ctx, budgetAcctInfo.getCurProject().getId().toString(), budgetAcctInfo.getFdcPeriod());
		return payedMap;
	}
	
	public void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		
		/**
		 * 目前实现，如果该版本(最早版本)存在修订版本则不能反审批,以后可根据具体需求做调整.
		 */
		FDCYearBudgetAcctInfo info = 
			FDCYearBudgetAcctFactory.getLocalInstance(ctx).getFDCYearBudgetAcctInfo(new ObjectUuidPK(billId));
		
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select max(fvernumber) as maxvernumber,min(fvernumber) as minvernumber from t_fnc_fdcyearbudgetacct where fprojectid=? and ffdcperiodId=?");
		builder.addParam(info.getCurProject().getId().toString());
		builder.addParam(info.getFdcPeriod().getId().toString());
		IRowSet row=builder.executeQuery();
		float minVerNumber = 0;
		float maxVerNumber = 0;
		try{
			if(row.next()){
				minVerNumber = row.getFloat("minvernumber");
				maxVerNumber = row.getFloat("maxvernumber");
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		if(Float.compare(info.getVerNumber(),maxVerNumber) < 0 && Float.compare(info.getVerNumber(),minVerNumber) ==0){
			throw new FDCBudgetAcctException(FDCBudgetAcctException.EXISTRECENSIONVER);
		}
		builder.clear();
		builder.appendSql("update T_FNC_FDCYearBudgetAcct set fislatestVer=? where fid=?");
		builder.addParam(Boolean.FALSE);
		builder.addParam(billId.toString());
		builder.execute();
		super._unAudit(ctx, billId);
	}
	public String getActionUI() {
		return "com.kingdee.eas.fdc.finance.client.FDCYearBudgetAcctEditUI";
	}
}
