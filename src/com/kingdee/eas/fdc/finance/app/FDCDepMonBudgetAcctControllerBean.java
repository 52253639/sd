package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctDataInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctException;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctFacadeFactory;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctHelper;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctInfo;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctFactory;
import com.kingdee.eas.util.app.ContextUtil;

/**
 * 部门月度付款计划申报表
 * 维护时请参照项目月度计划申报表
 *
 */
public class FDCDepMonBudgetAcctControllerBean extends AbstractFDCDepMonBudgetAcctControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.FDCDepMonBudgetAcctControllerBean");

    public FDCBudgetAcctInfo getMyFDCBudgetAcctInfo(Context ctx, String id,
    		String prjId, FDCBudgetPeriodInfo period) throws EASBizException,
    		BOSException {
    	if(id!=null){
			return getFDCDepMonBudgetAcctInfo(ctx, new ObjectUuidPK(id),getSelectors());
		}else{
			//TODO 取上个月的计划
//			EntityViewInfo view=new EntityViewInfo();
//			FilterInfo filter=new FilterInfo();
//			view.setFilter(filter);
//			filter.appendFilterItem("curProject.id", prjId);
//			FDCBudgetPeriodInfo prePeriod=period.getPrePeriod();
//			filter.appendFilterItem("fdcPeriod.year", new Integer(prePeriod.getYear()));
//			filter.appendFilterItem("fdcPeriod.month", new Integer(prePeriod.getMonth()));
//			filter.appendFilterItem("isLatestVer", Boolean.TRUE);
//			filter.appendFilterItem("state", FDCBillStateEnum.AUDITTED_VALUE);
//			view.put("selector", getPreMonthSelectors());
//			FDCDepMonBudgetAcctCollection c = FDCDepMonBudgetAcctFactory.getLocalInstance(ctx).getFDCDepMonBudgetAcctCollection(view);
//			if(c!=null&&c.size()>1){
//				throw new NullPointerException("exist repeat FDCDepMonBudgetAcctInfo in one month");
//			}
//			if(c!=null&&c.size()==1){
//				FDCDepMonBudgetAcctInfo info=c.get(0);
//				info.setId(null);
//				int preMonth=prePeriod.getMonth();
//				for(int j=info.getEntrys().size()-1;j>=0;j--){
//					FDCDepMonBudgetAcctEntryInfo entry=(FDCDepMonBudgetAcctEntryInfo)info.getEntrys().get(j);
//					if(entry.getItemType()==FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT){
//						info.getEntrys().remove(entry);
//					}
//					entry.setId(null);
//					entry.setAmount(null);
//					entry.setIsAdd(false);
//					for(int i=entry.getItems().size()-1;i>=0;i--){
//						FDCDepMonBudgetAcctItemInfo item=entry.getItems().get(i);
//						if(item.getMonth()==preMonth){
//							entry.getItems().remove(item);
//						}
//						if(item.getMonth()==preMonth+1){
//							entry.setAmount(item.getAmount());
//						}
//					}
//					
//				}
//				return info;
//			}
			return new FDCDepMonBudgetAcctInfo();
		}
    }
    
    /**
	 * 取上月付款计划的selector
	 * @return
	 */
	public SelectorItemCollection getPreMonthSelectors(){
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("curProject.id");
		selector.add("curProject.number");
		selector.add("curProject.name");
		selector.add("entrys.*");
		selector.add("entrys.contractBill.id");
		selector.add("entrys.costAccount.id");
		selector.add("entrys.items.*");
		return selector;
	}
	
	/**
	 * 项目月度付款计划申报selector
	 * @return
	 */
	public SelectorItemCollection getFDCMonthBudgetAcctSelectors(){
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("*");
		selector.add("entrys.*");
		selector.add("entrys.items.*");
		return selector;
	}
	
	public SelectorItemCollection getSelectors(){
		SelectorItemCollection selector=super.getSelectors();
		selector.add("deptment.name");
		return selector;
	}
	
	public String getActionUI() {
		return "com.kingdee.eas.fdc.finance.client.FDCDepMonBudgetAcctEditUI";
	}

	public FDCBudgetAcctEntryInfo getMyFDCBudgetAcctEntryInfo()
			throws EASBizException, BOSException {
		return new FDCDepMonBudgetAcctEntryInfo();
	}

	protected Map getCostMap(Context ctx, FDCBudgetAcctInfo info) throws BOSException, EASBizException {
		Map costMap = super.getCostMap(ctx, info);
		//上月0点保存数
		Map map=FDCBudgetAcctHelper.getLastPeriodCost(ctx, info.getCurProject().getId().toString(), info.getFdcPeriod());
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
		//截至上期实际付款(不包括当前月)
		Map payedMap =FDCBudgetAcctHelper.getToPeriodRequestedAmt(ctx, budgetAcctInfo.getCurProject().getId().toString(), budgetAcctInfo.getFdcPeriod().getPrePeriod());
		return payedMap;
	}
	
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._audit(ctx, billId);
		//TODO 审批时生成月度申请表
		FDCBudgetAcctFacadeFactory.getLocalInstance(ctx).updateMonthBudget(billId);
	}
	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		IObjectPK pk = super._addnew(ctx, model);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update t_fnc_fdcdepmonbudgetacct set fdeptmentid = ? where fid = ? ");
		builder.addParam(ContextUtil.getCurrentAdminUnit(ctx).getId().toString());
		builder.addParam(pk.toString());
		builder.execute();
		return pk;
	}
	
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("state");
		selector.add("curProject.id");
		selector.add("fdcPeriod.id");
		FDCBudgetAcctInfo info= (FDCBudgetAcctInfo) _getValue(ctx, new ObjectUuidPK(billId), selector);
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("curProject.id", info.getCurProject().getId().toString());
		filter.appendFilterItem("fdcPeriod.id", info.getFdcPeriod().getId().toString());
		filter.appendFilterItem("state", FDCBillStateEnum.AUDITTED);
		boolean isAuditted = FDCMonthBudgetAcctFactory.getLocalInstance(ctx).exists(filter);
		//当期已审批，则不允许新增
		if(isAuditted){
			throw new FDCBudgetAcctException(FDCBudgetAcctException.EXISTAUDITEDMONBUDGET);
		}
		super._unAudit(ctx, billId);
	}
	protected boolean checkDup(Context ctx, IObjectValue model) throws EASBizException, BOSException {
		FDCBudgetAcctInfo info=(FDCBudgetAcctInfo)model;
		String prjId=info.getCurProject().getId().toString();
		String periodId=info.getFdcPeriod().getId().toString();
		String deptmentId = info.get("deptmentId")!=null?(String)info.get("deptmentId"):"";
		checkBeforeOpenrate(ctx,info,prjId,periodId);
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("curProject.id", prjId);
		filter.appendFilterItem("deptment.id", deptmentId);
		filter.appendFilterItem("fdcPeriod.id", periodId);
		if(info.getId()!=null){
			filter.getFilterItems().add(new FilterItemInfo("id",info.getId().toString(),CompareType.NOTEQUALS));
		}
		if(info.getVerNumber()>1){
			filter.appendFilterItem("verNumber", new Float(info.getVerNumber()));
			boolean exist=_exists(ctx, filter);
			if(exist){
				throw new EASBizException(FDCBudgetAcctException.VERNUMBERDUP);
			}
		}else{
			boolean exist=_exists(ctx, filter);
			if(exist){
				throw new FDCBudgetAcctException(FDCBudgetAcctException.EXISTDUPDEPMONBUDGET);
			}
		}
		return false;
	}
	private void checkBeforeOpenrate(Context ctx, FDCBudgetAcctInfo info, String prjId, String periodId) throws EASBizException, BOSException {
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("curProject.id", prjId);
		filter.appendFilterItem("fdcPeriod.id", periodId);
		filter.appendFilterItem("state", FDCBillStateEnum.AUDITTED);
		boolean isAuditted = FDCMonthBudgetAcctFactory.getLocalInstance(ctx).exists(filter);
		//当期已审批，则不允许新增
		if(isAuditted && !FDCBillStateEnum.AUDITTED.equals(info.getState())){
			throw new FDCBudgetAcctException(FDCBudgetAcctException.EXISTAUDITEDMONBUDGET2);
		}
	}
}