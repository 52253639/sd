package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.contract.ContractBillExecuteDataHander;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractPayItemCollection;
import com.kingdee.eas.fdc.contract.ContractPayItemFactory;
import com.kingdee.eas.fdc.contract.ContractPayItemInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.finance.AcctConCostInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctDataCollection;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctDataFactory;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctDataInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryCollection;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctException;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctHelper;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctItemInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctItemTypeEnum;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodCollection;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodFactory;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctInfo;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctCollection;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctFactory;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctInfo;
import com.kingdee.eas.fdc.finance.FDCYearBudgetAcctCollection;
import com.kingdee.eas.fdc.finance.FDCYearBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCYearBudgetAcctFactory;
import com.kingdee.eas.fdc.finance.FDCYearBudgetAcctInfo;
import com.kingdee.eas.fdc.finance.FDCYearBudgetAcctItemCollection;
import com.kingdee.eas.fdc.finance.FDCYearBudgetAcctItemInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public abstract class FDCBudgetAcctControllerBean extends AbstractFDCBudgetAcctControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.FDCBudgetAcctControllerBean");

    protected String _getLogInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        FDCBudgetAcctInfo info = (FDCBudgetAcctInfo) super._getValue(ctx, pk);
		String retValue = "";
		if (info.getNumber() != null) {
			retValue = info.getNumber();
			if (info.getName() != null) {
				retValue = retValue + " " + info.getName();
			}
		}
        return retValue;
    }  
	protected Map _fetchData(Context ctx, Map param) throws BOSException, EASBizException {
		Map retMap=new  HashMap();
		try {
			initParma_Permission(ctx, param);
			retMap.putAll(getInitMap(ctx));
			String id = (String) param.get("id");
			String prjId = (String) param.get("projectId");
			FDCBudgetPeriodInfo period=(FDCBudgetPeriodInfo)param.get("fdcPeriod");
			boolean isRecension = param.get("isRecension")==null?false:((Boolean)param.get("isRecension")).booleanValue();
			FDCBudgetAcctInfo budgetAcctInfo = getMyFDCBudgetAcctInfo(ctx, id,prjId,period);
			if (prjId == null && (budgetAcctInfo.getCurProject() == null || budgetAcctInfo.getCurProject().getId() == null)) {
				throw new NullPointerException("cann't get prjId");
			}
			prjId = prjId == null ? budgetAcctInfo.getCurProject().getId().toString() : prjId;
			period = period == null ? budgetAcctInfo.getFdcPeriod(): period;
			budgetAcctInfo.setFdcPeriod(period);
			if(budgetAcctInfo.getCurProject()==null){
				CurProjectInfo curProjectInfo=new CurProjectInfo();
				curProjectInfo.setId(BOSUuid.read(prjId));
				budgetAcctInfo.setCurProject(curProjectInfo);
			}
			// costaccouts
			CostAccountCollection costAccounts = getCostAccounts(ctx, prjId);
			int maxLevel = 0;
			for (int i = 0; i < costAccounts.size(); i++) {
				if (maxLevel < costAccounts.get(i).getLevel()) {
					maxLevel = costAccounts.get(i).getLevel();
				}
			}
			retMap.put("costAccounts", costAccounts);
			retMap.put("maxLevel", new Integer(maxLevel));
			// contract
			ContractCostSplitEntryCollection splitEntrys = getCostSplitEntrys(ctx, prjId);

			Map conAcctMap = new HashMap();
			Set contractIdSet = new HashSet();
			for (Iterator iter = splitEntrys.iterator(); iter.hasNext();) {
				ContractCostSplitEntryInfo entry = (ContractCostSplitEntryInfo) iter.next();
				String contractId = entry.getParent().getContractBill().getId().toString();
				// if(entry.getCostAccount()==null||entry.getCostAccount().getId()==null){
				// continue;
				// }
				String acctId = entry.getCostAccount().getId().toString();
				conAcctMap.put(contractId + acctId, entry);
				contractIdSet.add(contractId);
			}
			// 合同最新造价
			String[] contractids = new String[contractIdSet.size()];
			contractIdSet.toArray(contractids);
			Map lastAmtMap = null;
			try{
				if(contractids.length >0){
					lastAmtMap = ContractBillExecuteDataHander.getLastAmt_Batch(ctx, contractids);
				}
			} catch (Exception e) {
				throw new BOSException(e);
			}
			
			Map entrysMap = new HashMap();// 预算分录项
			Map retBudgetEntrysMap = new HashMap();// 分组的预算分录
			Set isAddSet=new HashSet();///与待签订合同相关联的合同ID
			if (budgetAcctInfo.getId() == null) {
				budgetAcctInfo.setVerNumber(1.0f);
			}
			FDCBudgetAcctEntryCollection budgetAcctEntrys = budgetAcctInfo.getFDCBudgetAcctEntrys();
			budgetAcctInfo.remove("entrys");
			for (Iterator iter = budgetAcctEntrys.iterator(); iter.hasNext();) {
				FDCBudgetAcctEntryInfo entry = (FDCBudgetAcctEntryInfo) iter.next();
				if (entry.getItemType()==FDCBudgetAcctItemTypeEnum.CONTRACT&&entry.getContractBill() != null) {
					String key = entry.getCostAccount().getId().toString() + entry.getContractBill().getId().toString();
					entrysMap.put(key, entry);
					if(entry.isIsAdd()){
						isAddSet.add(entry.getContractBill().getId().toString());
					}
				} else if (entry.getItemType()==FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT){
					//修订版本没有把最初版本合同关联待签订合同时生成的记录带过来，但待签订合同记录中已有合同ID
					//故此处把此合同ID加入isAddSet，确保已被关联的合同不显示
					if(entry.getContractBill() != null){
						isAddSet.add(entry.getContractBill().getId().toString());
					}
					//处理待签订合同小计
					entry.setEmptyRow(false);
					//插入成本金额
					addBudgetEntry(ctx,retBudgetEntrysMap, entry);
				}
			}
			
			// 将合同信息添加到分录
			for (Iterator iter = conAcctMap.values().iterator(); iter.hasNext();) {
				ContractCostSplitEntryInfo entry = (ContractCostSplitEntryInfo) iter.next();
				String contractId = entry.getParent().getContractBill() != null ? entry.getParent().getContractBill().getId().toString() : null;
				String costAcctId = entry.getCostAccount().getId().toString();
				if (costAcctId == null || contractId == null) {
					continue;
				}
				if(isAddSet.contains(contractId)){
					//过滤掉与待签订合同相关联的合同
					continue;
				}
				FDCBudgetAcctEntryInfo budgetEntry = (FDCBudgetAcctEntryInfo) entrysMap.get(costAcctId + contractId);
				if (budgetEntry == null) {
					budgetEntry = getMyFDCBudgetAcctEntryInfo();
					budgetEntry.setItemType(FDCBudgetAcctItemTypeEnum.CONTRACT);
					budgetEntry.setEmptyRow(true);
				} else {
					budgetEntry.setEmptyRow(false);
				}
				//已审批及该科目没有数据map为null
//				if(aimCostMap!=null){
//					budgetEntry.setAimCost((BigDecimal)aimCostMap.get(costAcctId));
//				}
//				if(dynCostMap!=null){
//					budgetEntry.setDynCost((BigDecimal) dynCostMap.get(costAcctId));
//					
//				}

				budgetEntry.setCostAccount(entry.getCostAccount());
				budgetEntry.setContractBill(entry.getParent().getContractBill());
				if (budgetEntry.getContractBill() != null) {
					budgetEntry.setNumber(budgetEntry.getContractBill().getNumber());
					budgetEntry.setName(budgetEntry.getContractBill().getName());
					budgetEntry.setDeptment(budgetEntry.getContractBill().getRespDept());
					budgetEntry.setConAmt(budgetEntry.getContractBill().getAmount());
					budgetEntry.setLstCost(budgetEntry.getLstCost());//为什么要这么子写? by hpw
				}
				budgetEntry.setSplitAmt(entry.getAmount());
				budgetEntry.setConLatestPrice((BigDecimal) lastAmtMap.get(contractId));
				addBudgetEntry(ctx,retBudgetEntrysMap, budgetEntry);
			}

			// sort by contractbill number
			Comparator comparator = getComparator();
			Map payedMap=getPayedMap(ctx, budgetAcctInfo);

			Map realizedValueMap = null;
			if(budgetAcctInfo instanceof FDCMonthBudgetAcctInfo || budgetAcctInfo instanceof FDCDepMonBudgetAcctInfo){
				try {
					//审批后取保存的数据，审批前取最新 by hpw 2009-07-14
					if(FDCBillStateEnum.AUDITTED.equals(budgetAcctInfo.getState())){
						realizedValueMap = getAcctConCost(ctx, budgetAcctInfo.getId());
					}else{
						realizedValueMap = getBudgetAcctNewRealizedValue(ctx, prjId);
					}
				} catch (Exception e) {
					throw new BOSException(e);
				}
			}
			for (Iterator iterator = retBudgetEntrysMap.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				if (key != null && key.endsWith(FDCBudgetAcctItemTypeEnum.CONTRACT_VALUE)) {
					AbstractObjectCollection c = (AbstractObjectCollection) retBudgetEntrysMap.get(key);
					for(Iterator iter2=c.iterator();iter2.hasNext();){
						FDCBudgetAcctEntryInfo entry=(FDCBudgetAcctEntryInfo)iter2.next();
						String costAccountId=entry.getCostAccount().getId().toString();
						String contractId=entry.getContractBill().getId().toString();
						String tmpKey=costAccountId+contractId;
						if(budgetAcctInfo instanceof FDCMonthBudgetAcctInfo || budgetAcctInfo instanceof FDCDepMonBudgetAcctInfo){
							entry.put("allCost", realizedValueMap.get(tmpKey));
						}
						entry.put("allPay", payedMap.get(tmpKey));
					}
					
					
					FDCHelper.sortObjectCollection(c, comparator);
				}
			}
			if (isRecension) {
				handleRecension(ctx, budgetAcctInfo);
				handleNumber(ctx, budgetAcctInfo);
			}
			//获取付款事项、形象进度及其他数据 性能优化（客户端循环调用远程取数）
			if(budgetAcctInfo instanceof FDCMonthBudgetAcctInfo){
				retMap.put("otherData",getOtherData(ctx,contractIdSet));
			}
			retMap.put("editData", budgetAcctInfo);
			retMap.put("retBudgetEntrys", retBudgetEntrysMap);
			
			retMap.put("costMap", getCostMap(ctx, budgetAcctInfo));
		}finally{
			clearCache(ctx);
		}
		return retMap;
	}
	
	private Map getOtherData(Context ctx, Set contractIds) throws BOSException{
    	if(contractIds.size()==0){
    		return new HashMap();
    	}
    	Map retValue=new HashMap();
    	//付款事项
    	EntityViewInfo view=new EntityViewInfo();
//    	view.getSelector().add("*");
    	view.getSelector().add("contractBill");
    	view.getSelector().add("payItemDate");
    	view.getSelector().add("paymentType.name");
    	view.getSelector().add("payCondition");
    	view.getSelector().add("prop");
    	view.getSelector().add("amount");
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractbill",contractIds,CompareType.INCLUDE));
		view.setFilter(filter);
			ContractPayItemCollection payItemColl = ContractPayItemFactory.getLocalInstance(ctx).getContractPayItemCollection(view);
			if(payItemColl!=null&&payItemColl.size()!=0){
				ContractPayItemCollection newItems = null;
				String contractId = null;
				for(Iterator iter=payItemColl.iterator();iter.hasNext();){
					ContractPayItemInfo item = (ContractPayItemInfo)iter.next();
					if(item!=null&&item.getContractbill()!=null&&item.getContractbill().getId()!=null){
						contractId = item.getContractbill().getId().toString();
						if(retValue.containsKey(contractId)){
							ContractPayItemCollection oldItems = (ContractPayItemCollection)retValue.get(contractId);
							oldItems.add(item);
						}else{
							newItems = new ContractPayItemCollection();
							newItems.add(item);
						}
						retValue.put("payItem"+contractId, newItems);
					}
				}
			}
    	//形象进度
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select r.fcontractid,r.fprocess from t_con_payrequestbill r ");
		builder.appendSql("inner join (select max(fcreatetime) fcreatetime,fcontractid from t_con_payrequestbill group by fcontractid) r2 on r2.fcontractid=r.fcontractid and r2.fcreatetime=r.fcreatetime ");
		builder.appendSql("where ");
		builder.appendParam("r.fcontractid", contractIds.toArray());
		builder.appendSql(" and r.fprocess is not null ");
		IRowSet rs = builder.executeQuery();
		try {
			while(rs.next()){
				String contractId =rs.getString("fcontractid");
				String process =rs.getString("fprocess");
				retValue.put("process"+contractId, process);
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
    	return retValue;
    }
	/**
	 * 项目年度了的截止上年付款,以及项目月度的累计付款
	 * @param ctx
	 * @param budgetAcctInfo
	 * @return
	 */
	protected Map getPayedMap(Context ctx, FDCBudgetAcctInfo budgetAcctInfo)throws BOSException  {
		Map map=new HashMap();
		return map;
	}
	protected void handleRecension(Context ctx,FDCBudgetAcctInfo budgetAcctInfo )throws BOSException {
		budgetAcctInfo.setId(null);
		budgetAcctInfo.setState(FDCBillStateEnum.SAVED);
	}
	protected void handleNumber(Context ctx,FDCBudgetAcctInfo budgetAcctInfo )throws BOSException, EASBizException {
		if(budgetAcctInfo.getId()==null&&budgetAcctInfo.getVerNumber()>1){
			budgetAcctInfo.setNumber(null);
		}
	}
	private  Comparator getComparator(){
		return new Comparator(){
			public int compare(Object o1, Object o2) {
				if(o1 instanceof FDCBudgetAcctEntryInfo &&o2 instanceof FDCBudgetAcctEntryInfo){
					FDCBudgetAcctEntryInfo entry1=(FDCBudgetAcctEntryInfo)o1;
					FDCBudgetAcctEntryInfo entry2=(FDCBudgetAcctEntryInfo)o2;
					if(entry1.getContractBill()!=null&&entry2.getContractBill()!=null){
						String number1=entry1.getContractBill().getNumber();
						String number2=entry2.getContractBill().getNumber();
						number1=number1!=null?number1:"";
						number2=number2!=null?number2:"";
						return number1.compareTo(number2);
					}
//					if(entry1.getContractBill()==null&&entry2.getContractBill()==null){
//						//待签订合同
//						return entry1.getSeq()-entry2.getSeq();
//					}
				}
				return 0;
			}
		};
	}
	/**
	 * 添加分录到返回map内,添加的时候会根据情况进行过滤
	 * @param ctx
	 * @param budgetEntryMap
	 * @param entry
	 */
	private void addBudgetEntry(Context ctx,Map budgetEntryMap,FDCBudgetAcctEntryInfo entry){
		if(getDeptmentId(ctx)!=null){
			if(entry.getDeptment()==null||entry.getDeptment().getId()==null
					||!entry.getDeptment().getId().toString().equals(getDeptmentId(ctx))){
				//过滤掉其它责任部门的人
				return;
			}
		}
		if (getRespPersonId(ctx) != null) {
			if (entry.getContractBill() == null
					|| entry.getContractBill().getRespPerson() == null
					|| entry.getContractBill().getRespPerson().getId() == null 
					|| !entry.getContractBill().getRespPerson().getId().toString().equals(getRespPersonId(ctx))) {
				return;
			}
		}
		if (getCreatorId(ctx) != null) {
			if (entry.getContractBill() == null
					|| entry.getContractBill().getCreator() == null
					|| entry.getContractBill().getCreator().getId() == null 
					|| !entry.getContractBill().getCreator().getId().toString().equals(getCreatorId(ctx))) {
				return;
			}
		}
		if (getEditorId(ctx) != null) {
			if (entry.getCreator() == null
					|| entry.getCreator().getId() == null
					|| !entry.getCreator().getId().toString().equals(getEditorId(ctx))) {
				return;
			}
		}
		String costAccountId=entry.getCostAccount().getId().toString();
		String key=costAccountId+entry.getItemType().getValue();
		FDCBudgetAcctEntryCollection collection=(FDCBudgetAcctEntryCollection)budgetEntryMap.get(key);
		if(collection==null){
			collection=new FDCBudgetAcctEntryCollection();
			budgetEntryMap.put(key, collection);
		}
		collection.add(entry);
	}

	private ContractCostSplitEntryCollection getCostSplitEntrys(Context ctx, String prjId) throws BOSException {
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add("parent.contractBill.id");
		view.getSelector().add("parent.contractBill.amount");
		view.getSelector().add("parent.contractBill.name");
		view.getSelector().add("parent.contractBill.number");
		view.getSelector().add("parent.contractBill.partB.name");
		view.getSelector().add("parent.contractBill.respDept.id");
		view.getSelector().add("parent.contractBill.respDept.name");
		view.getSelector().add("parent.contractBill.respDept.number");
		view.getSelector().add("parent.contractBill.creator.name");
		view.getSelector().add("parent.contractBill.respPerson.name");
		view.getSelector().add("amount");
		filter.appendFilterItem("costAccount.curProject.id", prjId);
		filter.appendFilterItem("costAccount.isLeaf", Boolean.TRUE);
		filter.appendFilterItem("splitType", CostSplitTypeEnum.PRODSPLIT_VALUE);
		filter.appendFilterItem("product.id", null);
		filter.getFilterItems().add(new FilterItemInfo("splitType",CostSplitTypeEnum.PRODSPLIT_VALUE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("splitType",null));
		filter.appendFilterItem("isLeaf", Boolean.TRUE);
		filter.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
		if(getDeptmentId(ctx)!=null){
			filter.appendFilterItem("parent.contractBill.respDept.id", getDeptmentId(ctx));
			filter.setMaskString("#0 and #1 and ((#2 and #3) or ((#4 or #5) and #6))) and #7 and #8");
		}else{
			filter.setMaskString("#0 and #1 and ((#2 and #3) or ((#4 or #5) and #6))) and #7");
		}
		ContractCostSplitEntryCollection splitEntrys=ContractCostSplitEntryFactory.getLocalInstance(ctx).getContractCostSplitEntryCollection(view);
		return splitEntrys;
	}

	private CostAccountCollection getCostAccounts(Context ctx, String prjId) throws BOSException {
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("curProject.id", prjId);
		view.getSelector().add("id");
		view.getSelector().add("number");
		view.getSelector().add("longNumber");
		view.getSelector().add("name");
		view.getSelector().add("isLeaf");
		view.getSelector().add("level");
		view.getSorter().add(new SorterItemInfo("longNumber"));
		return CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
	}

	public abstract FDCBudgetAcctInfo getMyFDCBudgetAcctInfo(Context ctx,String id,String prjId,FDCBudgetPeriodInfo period) throws EASBizException, BOSException;
	public abstract FDCBudgetAcctEntryInfo getMyFDCBudgetAcctEntryInfo() throws EASBizException, BOSException;
	/**
	 * 权限项的UI
	 * @return
	 */
	public abstract String getActionUI();
	
	public SelectorItemCollection getSelectors(){
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("id");
		selector.add("number");
		selector.add("verNumber");
		selector.add("state");
		selector.add("isLatestVer");
		selector.add("fdcPeriod.*");
		selector.add("curProject.id");
		selector.add("curProject.number");
		selector.add("curProject.name");
		selector.add("entrys.*");
		selector.add("entrys.contractBill.id");
		selector.add("entrys.contractBill.partB.id");
		selector.add("entrys.contractBill.creator.name");
		selector.add("entrys.contractBill.respPerson.name");
		selector.add("entrys.creator.id");
		selector.add("entrys.creator.number");
		selector.add("entrys.creator.name");
		selector.add("entrys.deptment.id");
		selector.add("entrys.deptment.number");
		selector.add("entrys.deptment.name");
		selector.add("entrys.costAccount.id");
		selector.add("entrys.items.*");
		selector.add("auditor.name");
		selector.add("auditor.number");
		return selector;
	}
	
	protected void checkNameDup(Context ctx, FDCBillInfo billInfo) throws BOSException, EASBizException {
	}
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		handleBeforSaveAndSubmit(ctx, model);
		return super._save(ctx, model);
	}
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		handleBeforSaveAndSubmit(ctx, model);
		return super._submit(ctx, model);
	}
	
	/**
	 * 描述：保存提交之前需要做的一些处理，由于调用的地方比较多，故提取成一个方法
	 * @param ctx
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 * @author zhiyuan_tang 2010-12-23
	 */
	private void handleBeforSaveAndSubmit(Context ctx, IObjectValue model) throws BOSException, EASBizException  {
		handlePeriod(ctx,model);
		checkDup(ctx,model);
		FDCBudgetAcctInfo info=(FDCBudgetAcctInfo)model;
		boolean isRecense=info.getVerNumber()>1;
		if(!isRecense){
			info.setVerNumber(1.0f);
		}
		info.setIsLatestVer(false);
		for(Iterator iter=info.getFDCBudgetAcctEntrys().iterator();iter.hasNext();){
			FDCBudgetAcctEntryInfo entry=(FDCBudgetAcctEntryInfo)iter.next();
			entry.setProjectId(info.getCurProject().getId().toString());
		}
	}
	
	/**
	 * 描述：R101213-178:项目月度付款计划单据在走工作流过程中会造成表上的内部预算期间字段的丢失。 故重写用于工作流审批的save方法
	 * @author zhiyuan_tang 2010-12-23
	 */
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		handleBeforSaveAndSubmit(ctx, model);
		super._save(ctx, pk, model);
	}
	
	/**
	 * 描述：R101213-178:项目月度付款计划单据在走工作流过程中会造成表上的内部预算期间字段的丢失。 故重写用于工作流审批的submit方法
	 * @author zhiyuan_tang 2010-12-23
	 */
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		handleBeforSaveAndSubmit(ctx, model);
		super._submit(ctx, pk, model);
	}
	
	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		return super._addnew(ctx, model);
	}
	protected void _update(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		//过滤后在保存的时候要做数据合并,以避免数据的丢失
		String ui=getActionUI();
		String isFilterByDeptmentAction="ActionQuery";
		boolean isHasQueryDeptment = PermissionFactory.getLocalInstance(ctx).hasFunctionPermission(
				new ObjectUuidPK(ContextUtil.getCurrentUserInfo(ctx).getId().toString()),
				new ObjectUuidPK(ContextUtil.getCurrentOrgUnit(ctx).getId().toString()),
				new MetaDataPK(ui),
				new MetaDataPK(isFilterByDeptmentAction)
		);
		
		//没有部门查询的权限只能查当前部门的数据
		if(!isHasQueryDeptment){
			String deptmentId=ContextUtil.getCurrentAdminUnit(ctx).getId().toString();
			model.put("deptmentId", deptmentId);
		}
		String deptmentId=model.getString("deptmentId");
		String id=model.getString("id");
		FDCBudgetAcctEntryCollection entrys = ((FDCBudgetAcctInfo)model).getFDCBudgetAcctEntrys();
		//
		if(id!=null&&deptmentId!=null&&!(model instanceof FDCDepMonBudgetAcctInfo)){
			FDCBudgetAcctInfo myFDCBudgetAcctInfo = getMyFDCBudgetAcctInfo(ctx, id, null, null);
			for(Iterator iter=myFDCBudgetAcctInfo.getFDCBudgetAcctEntrys().iterator();iter.hasNext();){
				FDCBudgetAcctEntryInfo entry=(FDCBudgetAcctEntryInfo)iter.next();
				if(entry.getDeptment()!=null&&entry.getDeptment().getId().equals(deptmentId)){
					continue;
				}
				entrys.add(entry);
			}
		}
		
		super._update(ctx, pk, model);
	}
	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		super._delete(ctx, arrayPK);
	}
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		super._delete(ctx, pk);
	}
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._audit(ctx, billId);
		
		//保存科目信息
		FDCBudgetAcctInfo info=getMyFDCBudgetAcctInfo(ctx,billId.toString(),null,null);
		info.setState(FDCBillStateEnum.SUBMITTED);
		Map costMap = getCostMap(ctx, info);
		//delete first
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("delete from T_FNC_FDCBudgetAcctData where ffdcbudgetid=? ");
		builder.addParam(billId.toString());
		builder.execute();
		builder.clear();
		String sql="insert into T_FNC_FDCBudgetAcctData (fid,ffdcbudgetid,fcostaccountid,faimcost,fdyncost,flstcost,flstpay) values(?,?,?,?,?,?,?)";
		List params=new ArrayList();
		for(Iterator iter=costMap.keySet().iterator();iter.hasNext();){
			FDCBudgetAcctDataInfo dataInfo=(FDCBudgetAcctDataInfo)costMap.get(iter.next());
			dataInfo.setId(BOSUuid.create(dataInfo.getBOSType()));
			params.add(Arrays.asList(new Object[]{dataInfo.getId().toString(),billId.toString(),dataInfo.getCostAccountId(),dataInfo.getAimCost(),dataInfo.getDynCost(),dataInfo.getLstCost(),dataInfo.getLstPay()}));
		}
		builder.executeBatch(sql, params);
		
		//最后一个审批的版本为最终版本
		String table=(info instanceof FDCYearBudgetAcctInfo)?"T_FNC_FDCYearBudgetAcct":"T_FNC_FDCMonthBudgetAcct";
		builder.clear();
		builder.appendSql("update ");
		builder.appendSql(table);
		builder.appendSql(" set fislatestVer=? where fprojectid=? and ffdcperiodid=? and fid<>?");
		builder.addParam(Boolean.FALSE);
		builder.addParam(info.getCurProject().getId().toString());
		builder.addParam(info.getFdcPeriod().getId().toString());
		builder.addParam(info.getId().toString());
		builder.execute();
		
		builder.clear();
		builder.appendSql("update ");
		builder.appendSql(table);
		builder.appendSql(" set fislatestVer=? where fprojectid=? and ffdcperiodid=? and fid=?");
		builder.addParam(Boolean.TRUE);
		builder.addParam(info.getCurProject().getId().toString());
		builder.addParam(info.getFdcPeriod().getId().toString());
		builder.addParam(info.getId().toString());
		builder.execute();
		
		if(info instanceof FDCMonthBudgetAcctInfo || info instanceof FDCDepMonBudgetAcctInfo){
			/**
			 * 已完工审批时取最新,并保存
			 * by hpw date:2009-07-14
			 */
			String prjId = info.getCurProject().getId().toString();
			String periodId = info.getFdcPeriod().getId().toString();
			builder.clear();
			builder.appendSql("delete from T_FNC_AcctConCost where fhead = ? ");
			builder.addParam(billId.toString());
			builder.execute();
			
			builder.clear();
			builder.appendSql("select fcostaccountid,FContractBillID, sum(Amount) amount from ( \n");
			builder.appendSql("select FCostAccountId,settle.FContractBillID,entry.FAmount amount from T_AIM_CostSplitEntry entry  \n");
			builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
			builder.appendSql("inner join T_CON_ContractSettlementBill settle on head.FCostBillID=settle.FID  \n");
			builder.appendSql("where FCostBillType='SETTLEMENTSPLIT' and head.fcontrolUnitId in (select fcontrolUnitId from T_FDC_CurProject where fid=entry.fobjectid) and head.FIsInvalid=0 And  entry.fobjectid=? and  ((settle.FIsSettled=1 and settle.FIsFinalSettle=1) or (settle.FIsSettled=0 and settle.FIsFinalSettle=0))  \n");
			builder.appendSql("union all  \n");
			
			builder.appendSql("select FCostAccountId,settle.FContractBillID,entry.FAmount amount from T_AIM_CostSplitEntry entry  \n");
			builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
			builder.appendSql("inner join T_CON_ContractSettlementBill settle on head.FCostBillID=settle.FID  \n");
			builder.appendSql("where FCostBillType='SETTLEMENTSPLIT' and head.fcontrolUnitId in (select fcontrolUnitId from T_FDC_CurProject where fid=entry.fobjectid) and head.FIsInvalid=0 And  entry.fobjectid=? and  ((settle.FIsSettled=0 and settle.FIsFinalSettle=0) )  \n");
			builder.appendSql(")t group by fcostaccountid,FContractBillID; \n");
			builder.addParam(prjId);
			builder.addParam(prjId);
			IRowSet rowSet=builder.executeQuery();
			String insertSql = "insert into T_FNC_AcctConCost (fid,fhead,fproject,ffdcperiod,faccount,fcontract,flstcost) values(?,?,?,?,?,?,?) ";
			List values=new ArrayList();
			BOSObjectType type = (new AcctConCostInfo()).getBOSType();
			try {
				while(rowSet.next()){
					String costAccountID = rowSet.getString("fcostAccountid");
					String contractID = rowSet.getString("FContractBillID");
					BigDecimal lstCost = rowSet.getBigDecimal("amount");
					String id = BOSUuid.create(type).toString();
					values.add(Arrays.asList(new Object[]{id,billId.toString(),prjId,periodId,costAccountID,contractID,lstCost}));
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
			builder.executeBatch(insertSql, values);
		}
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._unAudit(ctx, billId);
		// 反审批时删除已完工
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.clear();
		builder.appendSql("delete from T_FNC_AcctConCost where fhead=? ");
		builder.addParam(billId.toString());
		builder.execute();
	}

	private String getDeptmentId(Context ctx){
		return (String)getInitMap(ctx).get("deptmentId");
	}
	private String getRespPersonId(Context ctx){
		return (String)getInitMap(ctx).get("respPersonId");
	}
	private String getCreatorId(Context ctx){
		return (String)getInitMap(ctx).get("creatorId");
	}
	private String getEditorId(Context ctx){
		return (String)getInitMap(ctx).get("editorId");
	}
	/**
	 * 初始化参数及权限
	 * @param ctx
	 * @param params
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void initParma_Permission(Context ctx,Map params) throws EASBizException, BOSException{
		//get params
		HashMap paramMap = FDCUtils.getDefaultFDCParam(ctx, ContextUtil.getCurrentFIUnit(ctx).getId().toString());
		Map map=new HashMap();
		ctx.put("initMap", map);
		
		/*
		 * @author ling_peng
		 */
		map.put("isChooseWhichPayType", Boolean.valueOf(FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_BUDGET_PLANDIF)));
		map.put("isShowMonthActDif", Boolean.valueOf(FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_BUDGET_ACTDIF)));
		
		map.put("isShowCost", Boolean.valueOf(FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_BUDGET_DISPLAYCOST)));
		map.put("isShowTotal", Boolean.valueOf(FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_TOTALCOST)));
		String ui=getActionUI();
		String isFilterByDeptmentAction="ActionQuery";
		String isShowDynAimCostAction="ActionViewCost";
		if(params.get("type")!=null&&params.get("type").equals("FDCBudgetAcctExecUI")){
			isFilterByDeptmentAction="ActionDeptQuery";
		}
		/*
		 * 是否有按部门查询权限
		 */
		boolean isHasQueryDeptment = PermissionFactory.getLocalInstance(ctx).hasFunctionPermission(
					new ObjectUuidPK(ContextUtil.getCurrentUserInfo(ctx).getId().toString()),
					new ObjectUuidPK(ContextUtil.getCurrentOrgUnit(ctx).getId().toString()),
					new MetaDataPK(ui),
					new MetaDataPK(isFilterByDeptmentAction)
			);
		/*
		 * 是否显示目标成本动态成本列
		 */
		boolean isShowDynAimCost= PermissionFactory.getLocalInstance(ctx).hasFunctionPermission(
				new ObjectUuidPK(ContextUtil.getCurrentUserInfo(ctx).getId().toString()),
				new ObjectUuidPK(ContextUtil.getCurrentOrgUnit(ctx).getId().toString()),
				new MetaDataPK(ui),
				new MetaDataPK(isShowDynAimCostAction)
		);
		
		map.put("isHasQueryDeptment", Boolean.valueOf(isHasQueryDeptment));
		map.put("isShowDynAimCost", Boolean.valueOf(isShowDynAimCost));
		if(isHasQueryDeptment){
			String deptmentId=(String)params.get("deptmentId");
			map.put("deptmentId", deptmentId);
		}else{
			//没有部门查询的权限只能查当前部门的数据
			String deptmentId=ContextUtil.getCurrentAdminUnit(ctx).getId().toString();
			map.put("deptmentId", deptmentId);
		}
		if(params.get("respPersonId")!=null)
			map.put("respPersonId", params.get("respPersonId"));
		if(params.get("creatorId")!=null)
			map.put("creatorId", params.get("creatorId"));
		if(params.get("editorId")!=null)
			map.put("editorId", params.get("editorId"));
		
	}
	private Map getInitMap(Context ctx){
		if(ctx.get("initMap")!=null){
			return (Map)ctx.get("initMap");
		}else{
			return new HashMap();
		}
	}
	private void clearCache(Context ctx){
		if(ctx!=null){
			Object map = ctx.remove("initMap");
			if(map!=null&&map instanceof Map){
				((Map)map).clear();
			}
		}
	}
	
	/** 
	 * 将符合条件的记录取值,在界面进行展示
	 * map里面有多个子map
	 * 一/用于放置年度,月度的计划信息
	 * 二/科目上的信息放在costMap里面
	 * @see com.kingdee.eas.fdc.finance.app.AbstractFDCBudgetAcctControllerBean#_fetchExecDate(com.kingdee.bos.Context, java.util.Map)
	 */
	protected Map _fetchExecDate(Context ctx, Map param) throws BOSException, EASBizException {
		TimeTools.getInstance().reset();
		TimeTools.getInstance().msValuePrintln("------start ----------");
		Map retMap=new  HashMap();
		try {
			initParma_Permission(ctx, param);
			retMap.putAll(getInitMap(ctx));
			String prjId = (String) param.get("projectId");
			FDCBudgetPeriodInfo period = (FDCBudgetPeriodInfo) param.get("fdcPeriod");
			if(prjId==null||period==null){
				throw new NullPointerException("project id and date cann't be null!");
			}
			FDCBudgetAcctInfo yearBudgetAcctInfo = getYearFDCBudgetAcctInfo(ctx,prjId,period);
			FDCBudgetAcctInfo monthBudgetAcctInfo = getMonthFDCBudgetAcctInfo(ctx,prjId,period);
			// costaccouts
			CostAccountCollection costAccounts = getCostAccounts(ctx, prjId);
			int maxLevel = 0;
			for (int i = 0; i < costAccounts.size(); i++) {
				if (maxLevel < costAccounts.get(i).getLevel()) {
					maxLevel = costAccounts.get(i).getLevel();
				}
			}
			retMap.put("costAccounts", costAccounts);
			retMap.put("maxLevel", new Integer(maxLevel));
			// contract
			ContractCostSplitEntryCollection splitEntrys = getCostSplitEntrys(ctx, prjId);

			Map conAcctMap = new HashMap();
			Set contractIdSet = new HashSet();
			for (Iterator iter = splitEntrys.iterator(); iter.hasNext();) {
				ContractCostSplitEntryInfo entry = (ContractCostSplitEntryInfo) iter.next();
				String contractId = entry.getParent().getContractBill().getId().toString();
				// if(entry.getCostAccount()==null||entry.getCostAccount().getId()==null){
				// continue;
				// }
				String acctId = entry.getCostAccount().getId().toString();
				conAcctMap.put(contractId + acctId, entry);
				contractIdSet.add(contractId);
			}
			// 合同最新造价
			String[] contractids = new String[contractIdSet.size()];
			contractIdSet.toArray(contractids);
			Map lastAmtMap = null;//最新造价
			Map toPeriodPayMap=null;//截止当月付款
			Map curPeriodPayMap=null;//当月付款
			Map yearPayMap=null;//取一个项目下的本年度已申请金额做为报表的已付款金额 
			Map toPeriodCostMap=null;//累计发生-成本
			Map curPeriodCostMap=null;//当月成本
			Map yearCostMap=null;//本年成本
			Map splitPayMap=null;//拆分的已付款数据     @author ling_peng
			try {
				if(contractids.length > 0){
					TimeTools.getInstance().msValuePrintln("---getLastAmt_Batch ----");
					lastAmtMap = ContractBillExecuteDataHander.getLastAmt_Batch(ctx, contractids);
					TimeTools.getInstance().msValuePrintln("---getToPeriodRequestedAmt ----");
					toPeriodPayMap=FDCBudgetAcctHelper.getToPeriodRequestedAmt(ctx, prjId, period);
					TimeTools.getInstance().msValuePrintln("---getCurPeriodRequestedAmt ----");
					curPeriodPayMap=FDCBudgetAcctHelper.getCurPeriodRequestedAmt(ctx, prjId, period);
					TimeTools.getInstance().msValuePrintln("---getYearRequestedAmt ----");
					yearPayMap=FDCBudgetAcctHelper.getYearRequestedAmt(ctx, prjId, period);
					TimeTools.getInstance().msValuePrintln("---getToPeriodCost ----");
					toPeriodCostMap=FDCBudgetAcctHelper.getToPeriodCost(ctx, prjId, period);
					TimeTools.getInstance().msValuePrintln("---getDifMap ----");
					curPeriodCostMap=FDCHelper.getDifMap(toPeriodCostMap, FDCBudgetAcctHelper.getLastPeriodCost(ctx, prjId, period));
					yearCostMap=FDCHelper.getDifMap(toPeriodCostMap, FDCBudgetAcctHelper.getLastYearCost(ctx, prjId, period));
					TimeTools.getInstance().msValuePrintln("---end getDifMap ----");
					//拆分的已付款数据    @author ling_peng
					TimeTools.getInstance().msValuePrintln("---getSplitPayMap");
					splitPayMap=FDCBudgetAcctHelper.getPayedAmt(ctx, prjId);
				}else{
					lastAmtMap = new HashMap();
					toPeriodPayMap=new HashMap();
					curPeriodPayMap=new HashMap();
					yearPayMap=new HashMap();
					toPeriodCostMap=new HashMap();
					curPeriodCostMap=new HashMap();
					yearCostMap=new HashMap();
					// @author ling_peng
					splitPayMap=new HashMap();
				}
			} catch (Exception e) {
				throw new BOSException(e);
			}
			TimeTools.getInstance().msValuePrintln("------start 预算年度分录项----------");
			Map yearEntrysMap = new HashMap();// 预算年度分录项
			Map monthEntrysMap = new HashMap();// 预算月度分录项
			Map retBudgetEntrysMap = new HashMap();// 分组的预算分录

			FDCBudgetAcctEntryCollection budgetAcctEntrys = yearBudgetAcctInfo.getFDCBudgetAcctEntrys();
			yearBudgetAcctInfo.remove("entrys");
			for (Iterator iter = budgetAcctEntrys.iterator(); iter.hasNext();) {
				FDCBudgetAcctEntryInfo entry = (FDCBudgetAcctEntryInfo) iter.next();
				if (entry.getItemType()==FDCBudgetAcctItemTypeEnum.CONTRACT&&entry.getContractBill() != null) {
					String key = entry.getCostAccount().getId().toString() + entry.getContractBill().getId().toString();
					yearEntrysMap.put(key, entry);
				}else {
					entry.setEmptyRow(false);
					addBudgetExecEntry(ctx,retBudgetEntrysMap, entry);
				}
			}
			
			TimeTools.getInstance().msValuePrintln("------start monthBudgetAcctInfo----------");
			budgetAcctEntrys = monthBudgetAcctInfo.getFDCBudgetAcctEntrys();
			monthBudgetAcctInfo.remove("entrys");
			for (Iterator iter = budgetAcctEntrys.iterator(); iter.hasNext();) {
				FDCBudgetAcctEntryInfo entry = (FDCBudgetAcctEntryInfo) iter.next();
				if (entry.getItemType()==FDCBudgetAcctItemTypeEnum.CONTRACT&&entry.getContractBill() != null) {
					String key = entry.getCostAccount().getId().toString() + entry.getContractBill().getId().toString();
					monthEntrysMap.put(key, entry);
				} else if(entry.getItemType()==FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT&&entry.getContractBill()==null){
					//contract!=null 已经关联了合同
					entry.setEmptyRow(false);
					addBudgetExecEntry(ctx,retBudgetEntrysMap, entry);
				}
			}
			
			for (Iterator iter = conAcctMap.values().iterator(); iter.hasNext();) {
				ContractCostSplitEntryInfo entry = (ContractCostSplitEntryInfo) iter.next();
				FDCBudgetAcctEntryInfo budgetEntry=buildExecEntry(entry,period,yearEntrysMap,monthEntrysMap,lastAmtMap);
//				setExecCost(budgetEntry, curPeriodCostMap, yearCostMap, toPeriodCostMap);
				//@author ling_peng
				setExecPay(budgetEntry, curPeriodPayMap, yearPayMap, toPeriodPayMap,splitPayMap);
				if(budgetEntry!=null){
					addBudgetExecEntry(ctx,retBudgetEntrysMap, budgetEntry);
				}
			}
			TimeTools.getInstance().msValuePrintln("------start sort----------");
			// sort by contractbill number
			Comparator comparator = getComparator();
			for (Iterator iterator = retBudgetEntrysMap.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				if (key != null && key.endsWith(FDCBudgetAcctItemTypeEnum.CONTRACT_VALUE)) {
					AbstractObjectCollection c = (AbstractObjectCollection) retBudgetEntrysMap.get(key);
					FDCHelper.sortObjectCollection(c, comparator);
				}
			}
			if(yearBudgetAcctInfo!=null){
				retMap.put("curProject",yearBudgetAcctInfo.getCurProject());
			}else if(monthBudgetAcctInfo!=null){
				retMap.put("curProject", monthBudgetAcctInfo.getCurProject());
			}
			if(retMap.get("curProject")==null){
				SelectorItemCollection selector=new SelectorItemCollection();
				selector.add("id");
				selector.add("number");
				selector.add("name");
				CurProjectInfo curProject=CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(prjId),selector);
				retMap.put("curProject", curProject);
				
			}
			retMap.put("fdcPeriod", period);
			retMap.put("retBudgetEntrys", retBudgetEntrysMap);
			Map costMap = getExecCostMap(ctx, monthBudgetAcctInfo, curPeriodCostMap, yearCostMap, toPeriodCostMap);
			//加入成本信息
			retMap.put("costMap",costMap);
		}finally{
			clearCache(ctx);
		}
		TimeTools.getInstance().msValuePrintln("------end ----------");
		return retMap;
	}
	private FDCBudgetAcctEntryInfo buildExecEntry(ContractCostSplitEntryInfo entry,FDCBudgetPeriodInfo period,Map yearEntryMap,Map monthEntryMap,Map lastAmtMap) {
		String contractId = entry.getParent().getContractBill() != null ? entry.getParent().getContractBill().getId().toString() : null;
		String costAcctId = entry.getCostAccount().getId().toString();
		if (costAcctId == null || contractId == null) {
			return null;
		}
		//当前期
		int currentPeriod=period.getMonth();
		String key=costAcctId + contractId;
		FDCYearBudgetAcctEntryInfo yearBudgetEntry=(FDCYearBudgetAcctEntryInfo)yearEntryMap.get(key);

		FDCMonthBudgetAcctEntryInfo monthBudgetEntry=(FDCMonthBudgetAcctEntryInfo)monthEntryMap.get(key);
		
		FDCBudgetAcctEntryInfo budgetEntry =monthBudgetEntry;
		if(budgetEntry==null){
			budgetEntry = new FDCMonthBudgetAcctEntryInfo();
			budgetEntry.setItemType(FDCBudgetAcctItemTypeEnum.CONTRACT);
			if(yearBudgetEntry==null){
				budgetEntry.setEmptyRow(true);
			}
		}

		if(yearBudgetEntry!=null){
			//设置年度预算
			budgetEntry.setBigDecimal("yearCost", yearBudgetEntry.getCost());
			budgetEntry.setBigDecimal("yearPay", yearBudgetEntry.getAmount());
			BigDecimal tmpSumCost=FDCHelper.ZERO;
			BigDecimal tmpSumAmt=FDCHelper.ZERO;
			for(Iterator iter=yearBudgetEntry.getItems().iterator();iter.hasNext();){
				FDCBudgetAcctItemInfo item=(FDCBudgetAcctItemInfo)iter.next();
				if(item.getMonth()==currentPeriod){
					//年度本期预算
					budgetEntry.setBigDecimal("budgetCost", item.getCost());
					budgetEntry.setBigDecimal("budgetPay", item.getAmount());
				}
				if(item.getMonth()<=currentPeriod){
					tmpSumAmt=FDCNumberHelper.add(item.getAmount(), tmpSumAmt);
					tmpSumCost=FDCNumberHelper.add(item.getCost(), tmpSumCost);
				}
				//年度同期累计
				budgetEntry.setBigDecimal("toDateYearPay", tmpSumAmt);
				budgetEntry.setBigDecimal("toDateYearCost", tmpSumCost);
			}
		}

		budgetEntry.setCostAccount(entry.getCostAccount());
		budgetEntry.setContractBill(entry.getParent().getContractBill());
		if (budgetEntry.getContractBill() != null) {
			budgetEntry.setNumber(budgetEntry.getContractBill().getNumber());
			budgetEntry.setName(budgetEntry.getContractBill().getName());
			budgetEntry.setDeptment(budgetEntry.getContractBill().getRespDept());
		}
		budgetEntry.setSplitAmt(entry.getAmount());
		budgetEntry.setConLatestPrice((BigDecimal) lastAmtMap.get(contractId));	
		return budgetEntry;
	}
	/**
	 * 执行表付款列
	 * @param budgetEntry
	 * @param curPeriodPayMap
	 * @param yearPayMap
	 * @param toPeriodPayMap
	 */
	private void setExecPay(FDCBudgetAcctEntryInfo budgetEntry,Map curPeriodPayMap,Map yearPayMap,Map toPeriodPayMap,Map splitPayMap){
		String contractId = budgetEntry.getContractBill().getId().toString();
		String costAcctId = budgetEntry.getCostAccount().getId().toString();
		String key=costAcctId + contractId;
		//本月实际累计付款
		budgetEntry.setBigDecimal("actPay", (BigDecimal)curPeriodPayMap.get(key));
		//拆分的已付款数据  @author ling_peng
		budgetEntry.setBigDecimal("shifu", (BigDecimal)splitPayMap.get(key));
		
		//本年实际累计付款
		budgetEntry.setBigDecimal("yearActPay", (BigDecimal)yearPayMap.get(key));
		//累计发生-付款
		budgetEntry.setBigDecimal("lstAllPay", (BigDecimal)toPeriodPayMap.get(key));
	}
	private FDCBudgetAcctInfo getMonthFDCBudgetAcctInfo(Context ctx, String prjId, FDCBudgetPeriodInfo period) throws BOSException {
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("curProject.id", prjId);
		view.getFilter().appendFilterItem("isLatestVer", Boolean.TRUE);
		view.getFilter().appendFilterItem("fdcPeriod.year", new Integer(period.getYear()));
		view.getFilter().appendFilterItem("fdcPeriod.month", new Integer(period.getMonth()));
		view.getFilter().appendFilterItem("state", FDCBillStateEnum.AUDITTED_VALUE);
		view.put("selector", getSelectors());
		FDCMonthBudgetAcctCollection c=FDCMonthBudgetAcctFactory.getLocalInstance(ctx).getFDCMonthBudgetAcctCollection(view);
		if(c!=null&&c.size()>0){
			return c.get(0);
		}
		FDCMonthBudgetAcctInfo info = new FDCMonthBudgetAcctInfo();
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("id");
		selector.add("number");
		selector.add("name");
		try{
			CurProjectInfo prj=CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(prjId),selector);
			info.setCurProject(prj);
		}catch(EASBizException e){
			throw new BOSException(e);
		}
		info.setFdcPeriod(period);
		return info;
	}
	private FDCBudgetAcctInfo getYearFDCBudgetAcctInfo(Context ctx, String prjId, FDCBudgetPeriodInfo period) throws BOSException{
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("curProject.id", prjId);
		view.getFilter().appendFilterItem("isLatestVer", Boolean.TRUE);
		view.getFilter().appendFilterItem("fdcPeriod.year", new Integer(period.getYear()));
		view.getFilter().appendFilterItem("fdcPeriod.isYear", Boolean.TRUE);
		view.getFilter().appendFilterItem("state", FDCBillStateEnum.AUDITTED_VALUE);
		view.put("selector", getSelectors());
		FDCYearBudgetAcctCollection c=FDCYearBudgetAcctFactory.getLocalInstance(ctx).getFDCYearBudgetAcctCollection(view);
		if(c!=null&&c.size()>0){
			return c.get(0);
		}
		FDCYearBudgetAcctInfo info = new FDCYearBudgetAcctInfo();
		
		
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("id");
		selector.add("number");
		selector.add("name");
		try{
			CurProjectInfo prj=CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(prjId),selector);
			info.setCurProject(prj);
		}catch(EASBizException e){
			throw new BOSException(e);
		}
		info.setFdcPeriod(period);
		return info;
	}
	
	private void addBudgetExecEntry(Context ctx,Map budgetEntryMap,FDCBudgetAcctEntryInfo entry){
		if(getDeptmentId(ctx)!=null){
			if(entry.getDeptment()==null||entry.getDeptment().getId()==null
					||!entry.getDeptment().getId().toString().equals(getDeptmentId(ctx))){
				//过滤掉其它责任部门的人
				return;
			}
		}
		
		
		String costAccountId=entry.getCostAccount().getId().toString();
		String key=costAccountId+entry.getItemType().getValue();
		if(entry instanceof FDCYearBudgetAcctEntryInfo){
			key=key+"year";
			FDCBudgetAcctEntryCollection collection=(FDCBudgetAcctEntryCollection)budgetEntryMap.get(key);
			if(collection==null){
				collection=new FDCBudgetAcctEntryCollection();
				budgetEntryMap.put(key, collection);
			}
			if(collection.size()>1){
				//add 
				FDCBudgetAcctEntryInfo oldEntry=collection.get(0);
				mergeFDCYearBudgetEntry((FDCYearBudgetAcctEntryInfo)oldEntry, (FDCYearBudgetAcctEntryInfo)entry);
			}else{
				collection.add(entry);
			}
		}else{
			FDCBudgetAcctEntryCollection collection=(FDCBudgetAcctEntryCollection)budgetEntryMap.get(key);
			if(collection==null){
				collection=new FDCBudgetAcctEntryCollection();
				budgetEntryMap.put(key, collection);
			}
			collection.add(entry);
		}

	}
	
	private FDCYearBudgetAcctEntryInfo mergeFDCYearBudgetEntry(FDCYearBudgetAcctEntryInfo oldBudget,FDCYearBudgetAcctEntryInfo newBudget){
		oldBudget.setCost(FDCNumberHelper.add(oldBudget.getCost(), newBudget.getCost()));
		oldBudget.setAmount(FDCNumberHelper.add(oldBudget.getAmount(), newBudget.getAmount()));
		FDCYearBudgetAcctItemCollection items=oldBudget.getItems();
		if(items==null||items.size()==0){
			oldBudget.put("items", newBudget.getItems());
			return oldBudget;
		}
		
		for(Iterator iter=newBudget.getItems().iterator();iter.hasNext();){
			FDCYearBudgetAcctItemInfo item=(FDCYearBudgetAcctItemInfo)iter.next();
			boolean isFound=false;
			for(int i=0;i<items.size();i++){
				if(item.getMonth()==items.get(i).getMonth()){
					isFound=true;
					items.get(i).setCost(FDCNumberHelper.add(item.getCost(), items.get(i).getCost()));
					items.get(i).setAmount(FDCNumberHelper.add(item.getAmount(), items.get(i).getAmount()));
					break;
				}
				if(!isFound){
					items.add(item);
				}
			}
		}
		return oldBudget;
	}
	private void handlePeriod(Context ctx, IObjectValue model) throws BOSException, EASBizException  {
		FDCBudgetAcctInfo budget=(FDCBudgetAcctInfo)model;
		FDCBudgetPeriodInfo fdcPeriod = budget.getFdcPeriod();
		if(fdcPeriod!=null&&fdcPeriod.getId()==null){
			EntityViewInfo view=new EntityViewInfo();
			view.getSelector().add("*");
			FilterInfo filter=new  FilterInfo();
			filter.appendFilterItem("year", new Integer(fdcPeriod.getYear()));
			filter.appendFilterItem("month", new Integer(fdcPeriod.getMonth()));
			filter.appendFilterItem("isYear", Boolean.valueOf(fdcPeriod.isIsYear()));
			view.setFilter(filter);
			FDCBudgetPeriodCollection c = FDCBudgetPeriodFactory.getLocalInstance(ctx).getFDCBudgetPeriodCollection(view);
			if(c!=null&&c.size()>0){
				fdcPeriod=c.get(0);
				budget.setFdcPeriod(fdcPeriod);
			}else{
				//add a new period
				fdcPeriod.setId(BOSUuid.create(fdcPeriod.getBOSType()));
				Date date=fdcPeriod.toDate();
				Date firstDate=fdcPeriod.isIsYear()?FDCDateHelper.getFirstYearDate(date):FDCDateHelper.getFirstDayOfMonth(date);
				Date lastDate=fdcPeriod.isIsYear()?FDCDateHelper.getLastYearDate(fdcPeriod.getYear()):FDCDateHelper.getLastDayOfMonth(date);
				fdcPeriod.setStartDate(firstDate);
				fdcPeriod.setEndDate(lastDate);
				FDCBudgetPeriodFactory.getLocalInstance(ctx).addnew(fdcPeriod);
			}
		}
	}
	
	protected boolean checkDup(Context ctx, IObjectValue model) throws EASBizException, BOSException {
		FDCBudgetAcctInfo info=(FDCBudgetAcctInfo)model;
		String prjId=info.getCurProject().getId().toString();
		String periodId=info.getFdcPeriod().getId().toString();
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("curProject.id", prjId);
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
				throw new FDCBudgetAcctException(FDCBudgetAcctException.DUP);
			}
		}
		return false;
	}
	
	protected Map getCostMap(Context ctx,FDCBudgetAcctInfo info)throws BOSException,EASBizException{
		//取月结表里面的,一经保存则不变
		Map map=new HashMap();
		if(info.getState()!=null&&info.getState().equals(FDCBillStateEnum.AUDITTED)){
			EntityViewInfo view=new EntityViewInfo();
			view.setFilter(new FilterInfo());
			view.getFilter().appendFilterItem("fdcbudgetId", info.getId().toString());
			view.getSelector().add("*");
			FDCBudgetAcctDataCollection c = FDCBudgetAcctDataFactory.getLocalInstance(ctx).getFDCBudgetAcctDataCollection(view);
			for(Iterator iter=c.iterator();iter.hasNext();){
				FDCBudgetAcctDataInfo data=(FDCBudgetAcctDataInfo)iter.next();
				map.put(data.getCostAccountId(), data);
			}
			
		}else{
			String prjId=info.getCurProject().getId().toString();
			Map param=new HashMap();
			param.put("prjId", prjId);
			Map costMap=FDCCostRptFacadeFactory.getLocalInstance(ctx).getCostMap(param);
			Map aimCostMap=(Map)costMap.get("aimCost");
			Map dynCostMap=(Map)costMap.get("dynCost");
			
			if(aimCostMap!=null&&aimCostMap.size()>0){
				for(Iterator iter=aimCostMap.keySet().iterator();iter.hasNext();){
					FDCBudgetAcctDataInfo dataInfo=new FDCBudgetAcctDataInfo();
					Object key = iter.next();
					dataInfo.setCostAccountId((String)key);
					dataInfo.setAimCost((BigDecimal)aimCostMap.get(key));
					map.put(key, dataInfo);
				}
			}
			if(dynCostMap!=null&&dynCostMap.size()>0){
				for(Iterator iter=dynCostMap.keySet().iterator();iter.hasNext();){
					Object key = iter.next();
					FDCBudgetAcctDataInfo dataInfo=null;
					if(map.get(key)!=null){
						dataInfo=(FDCBudgetAcctDataInfo)map.get(key);
					}else{
						dataInfo=new FDCBudgetAcctDataInfo();
						map.put(key, dataInfo);
					}
					dataInfo.setCostAccountId((String)key);
					dataInfo.setDynCost((BigDecimal)dynCostMap.get(key));
				}
			}
			
		}
		return map;
	}
	
	protected Map getExecCostMap(Context ctx,FDCBudgetAcctInfo info,Map curPeriodCostMap,Map yearCostMap,Map toPeriodCostMap)throws BOSException,EASBizException{
		//取月结表里面的,一经保存则不变
		Map map=new HashMap();
		if(info.getState()!=null&&info.getState().equals(FDCBillStateEnum.AUDITTED)){
			EntityViewInfo view=new EntityViewInfo();
			view.setFilter(new FilterInfo());
			view.getFilter().appendFilterItem("fdcbudgetId", info.getId().toString());
			view.getSelector().add("*");
			FDCBudgetAcctDataCollection c = FDCBudgetAcctDataFactory.getLocalInstance(ctx).getFDCBudgetAcctDataCollection(view);
			for(Iterator iter=c.iterator();iter.hasNext();){
				FDCBudgetAcctDataInfo data=(FDCBudgetAcctDataInfo)iter.next();
				map.put(data.getCostAccountId(), data);
			}
			
		}else{
			if(info.getCurProject()!=null){
				Map param=new HashMap();
				String prjId=info.getCurProject().getId().toString();
				param.put("prjId", prjId);
				Map costMap=FDCCostRptFacadeFactory.getLocalInstance(ctx).getCostMap(param);
				Map aimCostMap=(Map)costMap.get("aimCost");
				Map dynCostMap=(Map)costMap.get("dynCost");
				
				if(aimCostMap!=null&&aimCostMap.size()>0){
					for(Iterator iter=aimCostMap.keySet().iterator();iter.hasNext();){
						FDCBudgetAcctDataInfo dataInfo=new FDCBudgetAcctDataInfo();
						Object key = iter.next();
						dataInfo.setCostAccountId((String)key);
						dataInfo.setAimCost((BigDecimal)aimCostMap.get(key));
						map.put(key, dataInfo);
					}
				}
				if(dynCostMap!=null&&dynCostMap.size()>0){
					for(Iterator iter=dynCostMap.keySet().iterator();iter.hasNext();){
						Object key = iter.next();
						FDCBudgetAcctDataInfo dataInfo=null;
						if(map.get(key)!=null){
							dataInfo=(FDCBudgetAcctDataInfo)map.get(key);
						}else{
							dataInfo=new FDCBudgetAcctDataInfo();
							map.put(key, dataInfo);
						}
						dataInfo.setCostAccountId((String)key);
						dataInfo.setDynCost((BigDecimal)dynCostMap.get(key));
					}
				}
			}
			
		}
		//增加成本的Key
		for(Iterator iter=curPeriodCostMap.keySet().iterator();iter.hasNext();){
			String key=(String)iter.next();
			if(!map.containsKey(key)){
				map.put(key, new FDCBudgetAcctDataInfo());
			}
		}
		for(Iterator iter=yearCostMap.keySet().iterator();iter.hasNext();){
			String key=(String)iter.next();
			if(!map.containsKey(key)){
				map.put(key, new FDCBudgetAcctDataInfo());
			}
		}
		for(Iterator iter=toPeriodCostMap.keySet().iterator();iter.hasNext();){
			String key=(String)iter.next();
			if(!map.containsKey(key)){
				map.put(key, new FDCBudgetAcctDataInfo());
			}
		}
		for(Iterator iter=map.keySet().iterator();iter.hasNext();){
			String key=(String)iter.next();
			FDCBudgetAcctDataInfo dataInfo=null;
			if(map.get(key)!=null){
				dataInfo=(FDCBudgetAcctDataInfo)map.get(key);
			}else{
				dataInfo=new FDCBudgetAcctDataInfo();
				map.put(key, dataInfo);
			}
			//本月实际累计付款
			dataInfo.setBigDecimal("actCost", (BigDecimal)curPeriodCostMap.get(key));
			//本年实际累计成本
			dataInfo.setBigDecimal("yearActCost", (BigDecimal)yearCostMap.get(key));
			//累计发生-成本
			dataInfo.setBigDecimal("lstAllCost", (BigDecimal)toPeriodCostMap.get(key));
		}
		return map;
	}
	
	/**
	 * 细化到 科目+合同 两个维度
	 * 时小鸿说所有调用到getNewRealizedValue方法的都应细化，本次只处理项目月度申报表
	 * 先移过来，下次发补丁再放到fdcbudgetaccthelper
	 * 
	 * 结算单FIsSettled,FIsFinalSettle字段
	 * 未最终结算时 0,0
	 * 最终结算时1,1,并且之前的多次结算值为1,0
	 * 最终结算，拆分时取的是结算累加金额,若最终结算未拆分不取数
	 * @author pengwei_hou date:2009-07-13
	 * 
	 * @param ctx
	 * @param prjId
	 * @return
	 * @throws Exception
	 */
	public Map getBudgetAcctNewRealizedValue(Context ctx,String prjId) throws Exception{
		Map map=new HashMap();
		//使用一个sql，一次取数
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select fcostaccountid,FContractBillID, sum(Amount) amount from ( \n");
		builder.appendSql("select FCostAccountId,settle.FContractBillID,entry.FAmount amount from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql("inner join T_CON_ContractSettlementBill settle on head.FCostBillID=settle.FID  \n");
		builder.appendSql("where FCostBillType='SETTLEMENTSPLIT' and head.fcontrolUnitId in (select fcontrolUnitId from T_FDC_CurProject where fid=entry.fobjectid) and head.FIsInvalid=0 And  entry.fobjectid=? and  ((settle.FIsSettled=1 and settle.FIsFinalSettle=1) )  \n");
		builder.appendSql("union all  \n");

		builder.appendSql("select FCostAccountId,settle.FContractBillID,entry.FAmount amount from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql("inner join T_CON_ContractSettlementBill settle on head.FCostBillID=settle.FID  \n");
		builder.appendSql("where FCostBillType='SETTLEMENTSPLIT' and head.fcontrolUnitId in (select fcontrolUnitId from T_FDC_CurProject where fid=entry.fobjectid) and head.FIsInvalid=0 And  entry.fobjectid=? and  ((settle.FIsSettled=0 and settle.FIsFinalSettle=0) )  \n");
		builder.appendSql(")t group by fcostaccountid,FContractBillID; \n");
		builder.addParam(prjId);
		builder.addParam(prjId);
		IRowSet rowSet=builder.executeQuery();
		while(rowSet.next()){
			String costAccountID = rowSet.getString("fcostAccountid");
			String contractID = rowSet.getString("FContractBillID");
			map.put(costAccountID+contractID, rowSet.getBigDecimal("amount"));
		}
		return map;
	}
	
	/**
	 * 分录 合同科目已完工值
	 * @author pengwei_hou date:2009-07-14
	 * @param ctx
	 * @param billId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private Map getAcctConCost(Context ctx,BOSUuid billId) throws BOSException, SQLException{
		Map acctConCostMap = new HashMap();
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select faccount,fcontract,flstcost from t_fnc_acctconcost where fhead = ? ");
		builder.addParam(billId.toString());
		IRowSet rowSet = builder.executeQuery();
		while(rowSet.next()){
			String costAccountID = rowSet.getString("faccount");
			String contractID = rowSet.getString("fcontract");
			acctConCostMap.put(costAccountID+contractID, rowSet.getBigDecimal("flstcost"));
		}
		return acctConCostMap;
	}
}
