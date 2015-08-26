package com.kingdee.eas.fdc.finance.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
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

import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherEntryFactory;
import com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherDateEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherDateEntryFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherCollection;
import com.kingdee.eas.fdc.finance.ProjectYearPlanInfo;
import com.kingdee.eas.fdc.finance.VersionTypeEnum;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryCollection;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryFactory;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryInfo;
import com.kingdee.eas.fdc.contract.ContractPayPlanFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.app.ContractBillImport;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class ProjectMonthPlanGatherControllerBean extends AbstractProjectMonthPlanGatherControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.ProjectMonthPlanGatherControllerBean");
    
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	super._unAudit(ctx, billId);
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("curProject.id");
		sel.add("version");
		sel.add("isLatest");
		sel.add("bizDate");
		sel.add("versionType");
		ProjectMonthPlanGatherInfo info =this.getProjectMonthPlanGatherInfo(ctx, new ObjectUuidPK(billId), sel);
		if(!info.isIsLatest()){
			throw new EASBizException(new NumericExceptionSubItem("100","非最新版本不能反审批！"));
		}
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id",info.getCurProject().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("versionType",info.getVersionType().getValue()));
		filter.getFilterItems().add(new FilterItemInfo("bizDate",info.getBizDate()));
		filter.getFilterItems().add(new FilterItemInfo("version",info.getVersion(),CompareType.GREATER));
		if(ProjectMonthPlanGatherFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","单据已修订，不能进行反审批操作！"));
		}
		if(info.getVersionType().equals(VersionTypeEnum.REPORT)){
			filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject.id",info.getCurProject().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("bizDate",info.getBizDate()));
			filter.getFilterItems().add(new FilterItemInfo("versionType",VersionTypeEnum.EXECUTE_VALUE));
			if(ProjectMonthPlanGatherFactory.getLocalInstance(ctx).exists(filter)){
				throw new EASBizException(new NumericExceptionSubItem("100","已存在执行版，不能进行反审批操作！"));
			}
		}

		String proId = info.getCurProject().getId().toString();
		int version = info.getVersion();
		version = version-1;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(info.getBizDate());
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		
		StringBuffer sql = new StringBuffer();
		sql.append("update T_FNC_ProjectMonthPlanGather set FIsLatest = 0 where fid = '").append(billId.toString()).append("'");
		fdcSB.addBatch(sql.toString());
		
		sql.setLength(0);
		sql.append("update T_FNC_ProjectMonthPlanGather set FIsLatest = 1 where fCurProjectid = '");
		sql.append(proId).append("'");
		sql.append("and FVersion = ").append(version);
		sql.append("and year(fbizDate) = ").append(year);
		sql.append("and month(fbizDate) = ").append(month);
		sql.append("and fversionType = '").append(info.getVersionType().getValue()).append("'");;
		fdcSB.addBatch(sql.toString());
		fdcSB.executeBatch();
	}
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._audit(ctx, billId);
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("curProject.id");
		sel.add("version");
		sel.add("bizDate");
		sel.add("versionType");
		ProjectMonthPlanGatherInfo info =this.getProjectMonthPlanGatherInfo(ctx, new ObjectUuidPK(billId), sel);
		String proId = info.getCurProject().getId().toString();
		int version = info.getVersion();
		version = version-1;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(info.getBizDate());
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		
		StringBuffer sql = new StringBuffer();
		sql.append("update T_FNC_ProjectMonthPlanGather set FIsLatest = 1 where fid = '").append(billId.toString()).append("'");
		fdcSB.addBatch(sql.toString());
		
		sql.setLength(0);
		sql.append("update T_FNC_ProjectMonthPlanGather set FIsLatest = 0 where fCurProjectid = '");
		sql.append(proId).append("' ");
		sql.append("and FVersion = ").append(version);
		sql.append("and year(fbizDate) = ").append(year);
		sql.append("and month(fbizDate) = ").append(month);
		sql.append("and fversionType = '").append(info.getVersionType().getValue()).append("'");
		fdcSB.addBatch(sql.toString());
		fdcSB.executeBatch();
	}
	protected boolean isUseName() {
		return false;
	}
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("srcId",pk.toString()));
		if(OrgUnitMonthPlanGatherEntryFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","单据已经被引用，禁止删除操作！"));
		}
		super._delete(ctx, pk);
	}
	protected BigDecimal getAccPayAmount(Context ctx,String id,String billId,Date bizDate) throws BOSException, SQLException{
		Calendar cal = Calendar.getInstance();
		cal.setTime(bizDate);
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		
		FDCSQLBuilder _builder = new FDCSQLBuilder(ctx);
		_builder.appendSql(" select sum(entry.frequestAmount-isnull(entry.factPayAmount,0))payAmount from T_CON_PayRequestBill bill left join T_CON_PayRequestBillBgEntry entry on entry.fheadid=bill.fid ");
		_builder.appendSql(" where year(bill.fbookedDate)='"+year+"' and month(bill.fbookedDate)='"+month+"' and bill.FContractId='"+id+"'");
		_builder.appendSql(" and bill.fstate in('2SUBMITTED','3AUDITTING','4AUDITTED') ");
		_builder.appendSql(" and bill.fhasClosed=0 and bill.famount is not null");
		if (billId != null) {
			_builder.appendSql(" and bill.fid!='" + billId + "'");
		}
		final IRowSet rowSet = _builder.executeQuery();
		while (rowSet.next()) {
			if(rowSet.getBigDecimal("payAmount")!=null)
				return rowSet.getBigDecimal("payAmount");
		}
		return FDCHelper.ZERO;
	}
	protected BigDecimal getAccWTPayAmount(Context ctx,String id,String billId,Date bizDate) throws BOSException, SQLException{
		Calendar cal = Calendar.getInstance();
		cal.setTime(bizDate);
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		
		FDCSQLBuilder _builder = new FDCSQLBuilder(ctx);
		_builder.appendSql(" select sum(entry.frequestAmount-isnull(entry.factPayAmount,0))payAmount from T_CON_PayRequestBill bill left join T_CON_PayRequestBillBgEntry entry on entry.fheadid=bill.fid ");
		_builder.appendSql(" left join t_con_contractwithouttext contract on contract.fid=bill.fcontractId");
		_builder.appendSql(" where contract.fid is not null and year(bill.fbookedDate)='"+year+"' and month(bill.fbookedDate)='"+month+"' and bill.FcurProjectId='"+id+"'");
		_builder.appendSql(" and bill.fstate in('2SUBMITTED','3AUDITTING','4AUDITTED') ");
		_builder.appendSql(" and bill.fhasClosed=0 and bill.famount is not null");
		if (billId != null) {
			_builder.appendSql(" and bill.fId!='" + billId + "'");
		}
		final IRowSet rowSet = _builder.executeQuery();
		while (rowSet.next()) {
			if(rowSet.getBigDecimal("payAmount")!=null)
				return rowSet.getBigDecimal("payAmount");
		}
		return FDCHelper.ZERO;
	}
	
	protected Map _checkPass(Context ctx, Map map) throws BOSException, EASBizException {
		Map result=new HashMap();
		Boolean isContract=(Boolean)map.get("isContract");
		String contractId=(String) map.get("contractId");
		String curProjectId=(String) map.get("curProjectId");
		String orgId=(String)map.get("orgId");
		Date bizDate=(Date)map.get("bizDate");
		BigDecimal amount=(BigDecimal)map.get("amount");
		String billId=(String) map.get("billId");
		if(isContract==null||contractId==null||orgId==null||bizDate==null||amount==null){
			result.put("isPass", Boolean.FALSE);
			result.put("warning", "控制参数为空！");
			return result;
		}
		HashMap hmParamIn = new HashMap();
		hmParamIn.put("CIFI_PAYPLAN", orgId);
		HashMap hmAllParam = ParamControlFactory.getLocalInstance(ctx).getParamHashMap(hmParamIn);
		if(hmAllParam.get("CIFI_PAYPLAN")==null||!Boolean.valueOf(hmAllParam.get("CIFI_PAYPLAN").toString()).booleanValue()){
			result.put("isPass", Boolean.TRUE);
			return result;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(bizDate);
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		
		EntityViewInfo view=new EntityViewInfo();
    	FilterInfo filter=new FilterInfo();
    	
    	if(isContract.booleanValue()){
    		filter.getFilterItems().add(new FilterItemInfo("contractbill.id",contractId));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("contractbill.id",null));
			filter.getFilterItems().add(new FilterItemInfo("head.curProject.id",curProjectId));
		}
    	filter.getFilterItems().add(new FilterItemInfo("head.state",FDCBillStateEnum.AUDITTED_VALUE));
    	filter.getFilterItems().add(new FilterItemInfo("head.isLatest",Boolean.TRUE));
    	
    	SorterItemInfo bizDateSort=new SorterItemInfo("head.bizDate");
    	bizDateSort.setSortType(SortType.DESCEND);
    	view.getSorter().add(bizDateSort);
    	view.getSelector().add("dateEntry.*");
    	view.getSelector().add("head.bizDate");
    	view.setFilter(filter);
    	
    	if(isContract.booleanValue()){
    		try {
				FDCSQLBuilder _builder = new FDCSQLBuilder(ctx);
				_builder.appendSql("select famount from T_FNC_ContractOutPayPlan where fstate='4AUDITTED' and fcontractid='"+contractId+"'");
				_builder.appendSql(" and year(fbizdate)="+year+" and month(fbizDate)="+month);
				final IRowSet rowSet = _builder.executeQuery();
				if (rowSet.size()>0) {
					rowSet.next();
					BigDecimal planamount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("famount"));
					
					BigDecimal accPayAmount=getAccPayAmount(ctx,contractId,billId,bizDate);
					if(planamount.subtract(accPayAmount).compareTo(amount)<0){
						result.put("isPass", Boolean.FALSE);
						result.put("warning", "该笔付款超过月度付款计划，不允许发起付款！");
						return result;
					}else{
						result.put("isPass", Boolean.TRUE);
						return result;
					}
				}else{
					ProjectMonthPlanGatherEntryCollection col=ProjectMonthPlanGatherEntryFactory.getLocalInstance(ctx).getProjectMonthPlanGatherEntryCollection(view);
		        	if(col.size()>0){
		        		for(int k=0;k<col.size();k++){
		        			Calendar comcal = Calendar.getInstance();
			        		comcal.setTime(col.get(k).getHead().getBizDate());
			        		int comyear=cal.get(Calendar.YEAR);
			        		int commonth=cal.get(Calendar.MONTH)+1;
			        		if(comyear==year&&commonth==month){
			        			if(!OrgUnitMonthPlanGatherEntryFactory.getLocalInstance(ctx).exists("select * from where head.state='4AUDITTED' and srcId='"+col.get(k).getHead().getId().toString()+"'")){
			        				continue;
			        			}
			        			ProjectMonthPlanGatherEntryInfo ppEntry=col.get(k);
				        		for(int i=0;i<ppEntry.getDateEntry().size();i++){
				        			int comYear=ppEntry.getDateEntry().get(i).getYear();
				        			int comMonth=ppEntry.getDateEntry().get(i).getMonth();
				        			if(comYear==year&&comMonth==month){
				        				BigDecimal accPayAmount=FDCHelper.ZERO;
				        				try {
				    						accPayAmount=getAccPayAmount(ctx,contractId,billId,bizDate);
				        				} catch (SQLException e) {
				        					e.printStackTrace();
				        				}
				        				if(ppEntry.getDateEntry().get(i).getAmount().subtract(accPayAmount).compareTo(amount)<0){
				        					result.put("isPass", Boolean.FALSE);
				        					result.put("warning", "该笔付款超过月度付款计划，不允许发起付款！");
				        					return result;
				        				}else{
				        					result.put("isPass", Boolean.TRUE);
				        					return result;
				        				}
				        			}
				        		}
			        		}
		        		}
		        	}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}else{
    		ProjectMonthPlanGatherEntryCollection col=ProjectMonthPlanGatherEntryFactory.getLocalInstance(ctx).getProjectMonthPlanGatherEntryCollection(view);
			if(col.size()>0){
				for(int k=0;k<col.size();k++){
        			Calendar comcal = Calendar.getInstance();
	        		comcal.setTime(col.get(k).getHead().getBizDate());
	        		int comyear=cal.get(Calendar.YEAR);
	        		int commonth=cal.get(Calendar.MONTH)+1;
	        		if(comyear==year&&commonth==month){
	        			if(!OrgUnitMonthPlanGatherEntryFactory.getLocalInstance(ctx).exists("select * from where head.state='4AUDITTED' and srcId='"+col.get(k).getHead().getId().toString()+"'")){
	        				continue;
	        			}
	        			BigDecimal accPayAmount=FDCHelper.ZERO;
	    				try {
	    					accPayAmount=getAccWTPayAmount(ctx,curProjectId,billId,bizDate);
	    				} catch (SQLException e) {
	    					e.printStackTrace();
	    				}
	    				BigDecimal payAmount=FDCHelper.ZERO;
	    				ProjectMonthPlanGatherEntryInfo ppEntry=col.get(k);
    	        		for(int i=0;i<ppEntry.getDateEntry().size();i++){
    	        			int comYear=ppEntry.getDateEntry().get(i).getYear();
		        			int comMonth=ppEntry.getDateEntry().get(i).getMonth();
		        			if(comYear==year&&comMonth==month){
    	        				payAmount=FDCHelper.add(payAmount, ppEntry.getDateEntry().get(i).getAmount());
    	        			}
	    	        	}
	    	        	if(payAmount.subtract(accPayAmount).compareTo(amount)<0){
	    					result.put("isPass", Boolean.FALSE);
	    					result.put("warning", "该笔付款超过月度付款计划，不允许发起付款！");
	    					return result;
	    				}else{
	    					result.put("isPass", Boolean.TRUE);
	    					return result;
	    				}
	        		}
				}
			}
    	}
		result.put("isPass", Boolean.FALSE);
		result.put("warning", "该笔付款不存在月度付款计划，不允许发起付款！");
		return result;
	}
}