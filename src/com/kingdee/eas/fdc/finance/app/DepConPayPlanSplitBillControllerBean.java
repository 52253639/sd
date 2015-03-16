package com.kingdee.eas.fdc.finance.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.finance.DepConPayPlanSplitEntryInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanEntryCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanEntryInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanItemCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanItemInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

public class DepConPayPlanSplitBillControllerBean extends AbstractDepConPayPlanSplitBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.DepConPayPlanSplitBillControllerBean");

    private ArrayList tempCostAccountIds = new ArrayList(); 
	protected Map _fetchData(Context ctx, Map param) throws BOSException,
			EASBizException {
		Map returnMap = new HashMap();
		
		//获得月度付款计划分录相关信息
		String payPlanID = (String) param.get("payPlanID");
		CoreBillBaseInfo bill = getPayPlanBill(ctx, payPlanID);
		
		FDCDepConPayPlanEntryCollection entrys = null;
		if(bill!=null){
//			entrys = ((FDCDepConPayPlanBillInfo)bill).getEntrys();
		}
		
		//获得月度付款计划条目相关信息
		Map itemsMap = getItemsOfEntry(entrys);
		
		//获得月度付款计划拆分相关信息
		Map splitEntryMap = getPayPlanSplitEntry(ctx, entrys);
		
		//获得月度付款计划分录上对应的合同的拆分相关信息
		Map costAccountMap = getCostAccountOfConSplit(ctx,entrys);
		
		returnMap.put("bill", bill);
		returnMap.put("entrys", entrys);
		returnMap.put("splitEntrys", splitEntryMap);
		returnMap.put("costAccountIDs",tempCostAccountIds);
		returnMap.put("items", itemsMap);
		returnMap.put("costAccount", costAccountMap);
		return returnMap;
	}
	/**
	 * 根据ID找到付款计划拆分分录
	 */
	private Map getPayPlanSplitEntry(Context ctx,FDCDepConPayPlanEntryCollection entrys) {
		Map splitEntryMap = new HashMap();
		
		for(Iterator iter = entrys.iterator();iter.hasNext();){
			FDCDepConPayPlanEntryInfo payPlanEntry = (FDCDepConPayPlanEntryInfo)iter.next();
			BOSUuid entryId = payPlanEntry.getId();
			/*SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("costAccount.id");
			selector.add("costAccount.number");
			selector.add("costAccount.name");
			selector.add("costAccount.curProject.id");
			selector.add("costAccount.curProject.number");
			selector.add("costAccount.curProject.name");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("payPlanEntry.id",entryId.toString()));
			EntityViewInfo view = new EntityViewInfo();
			view.setSelector(selector);
			view.setFilter(filter);*/
			//上面那种方式查不出来数据
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select planEntry.FID planEntryID,entry.FID entryId,acct.FID acctId,acct.FNumber acctNumber,acct.FLongNumber acctLongNumber,acct.FIsLeaf isleaf,acct.FName_l2 acctName, " +
					"prj.FID prjId,prj.FName_l2 prjName,prj.FDisplayName_l2 displayName,prj.FNumber prjNumber,prj.FLongNumber prjLongNumber from T_FNC_DepConPayPlanSplitEntry entry  " +
					"LEFT OUTER JOIN T_FNC_FDCDepConPayPlanEntry planEntry on planEntry.FID = entry.FPayPlanEntryID  " +
					"LEFT OUTER JOIN T_FDC_CostAccount acct on acct.FID = entry.FCostAccountID  " +
					"LEFT OUTER JOIN  T_FDC_CurProject prj on prj.FID = acct.FCurProject " +
					"where entry.FPayPlanEntryID =? order by entry.FIndex  ");
			builder.addParam(entryId.toString());
			IRowSet rowSet = null;
			try {
				rowSet = builder.executeQuery();
			} catch (BOSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				try {
					while(rowSet!=null && rowSet.next()){
						try {
							String tempAccountID = null;
							DepConPayPlanSplitEntryInfo splitEntry = new DepConPayPlanSplitEntryInfo();
							splitEntry.setId(BOSUuid.read(rowSet.getString("entryId")));
							FDCDepConPayPlanEntryInfo planEntry = new FDCDepConPayPlanEntryInfo();
							planEntry.setId(BOSUuid.read( rowSet.getString("planEntryID")));
							splitEntry.setPayPlanEntry(planEntry);
							if(rowSet.getString("acctId")!=null){
								CostAccountInfo acct = new CostAccountInfo();
								tempAccountID = rowSet.getString("acctId");
								acct.setId(BOSUuid.read(rowSet.getString("acctId")));
								acct.setNumber(rowSet.getString("acctNumber"));
								acct.setLongNumber(rowSet.getString("acctLongNumber"));
								acct.setName(rowSet.getString("acctName"));
								acct.setIsLeaf(rowSet.getBoolean("isLeaf"));
								CurProjectInfo project = new CurProjectInfo();
								project.setId(BOSUuid.read(rowSet.getString("prjId")));
								project.setNumber(rowSet.getString("prjNumber"));
								project.setLongNumber(rowSet.getString("prjLongNumber"));
								project.setName(rowSet.getString("displayName"));
								splitEntry.setCostAccount(acct);
								acct.setCurProject(project);
							}
							tempCostAccountIds.add(tempAccountID);
							splitEntryMap.put(entryId.toString()+"_"+tempAccountID, splitEntry);
					} catch (Exception e) {
						e.printStackTrace();
					}
}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return splitEntryMap;
	}
	/**
	 * 根据ID找到付款计划
	 */
	private CoreBillBaseInfo getPayPlanBill(Context ctx, String payPlanID)
			throws BOSException, EASBizException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("year");
		selector.add("month");
		selector.add("entrys.isUnsettledCon");
		selector.add("entrys.contractBillId");
		selector.add("entrys.contractNumber");
		selector.add("entrys.contractName");
		selector.add("entrys.contractLastestPrice");
		selector.add("entrys.items.*");
		selector.add("entrys.items.requestAmt");
		selector.add("entrys.items.requestProp");
		selector.add("entrys.items.auditAmt");
		selector.add("entrys.items.auditProp");
		selector.add("entrys.items.year");
		selector.add("entrys.items.month");
		CoreBillBaseInfo bill = (CoreBillBaseInfo)FDCDepConPayPlanBillFactory.getLocalInstance(ctx).getValue(new ObjectUuidPK(BOSUuid.read(payPlanID)),selector);
		return bill;
	}
	/**
	 * 根据付款计划分录上的合同找到这些合同的拆分科目信息
	 */
	private Map getCostAccountOfConSplit(Context ctx,FDCDepConPayPlanEntryCollection entrys) throws BOSException {
		Map costAccountMap = new HashMap();
		for(Iterator iter = entrys.iterator();iter.hasNext();){
			FDCDepConPayPlanEntryInfo entry = (FDCDepConPayPlanEntryInfo)iter.next();
			String contractBillId = entry.getContractBillId();
			ContractCostSplitEntryCollection conSplitEntrys = new ContractCostSplitEntryCollection();
			if(contractBillId!=null){//理论上来说是不可能为空的
				//这样查询竟然找不到curProject，郁闷死了！
				/*EntityViewInfo view = new EntityViewInfo();
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("costAccount.id");
				selector.add("costAccount.number");
				selector.add("costAccount.name");
				selector.add("costAccount.curProject.id");
				selector.add("costAccount.curProject.number");
				selector.add("costAccount.curProject.name");
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.id",contractBillId));
				filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.id",contractBillId));//作废的除外*/
				//用SQL来找吧！
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.appendSql("select entry.FID entryId,acct.FID acctId,acct.FNumber acctNumber,acct.FLongNumber acctLongNumber,acct.FIsLeaf isleaf,acct.FName_l2 acctName,prj.FID prjId,prj.FName_l2 prjName,prj.FDisplayName_l2 displayName,prj.FNumber prjNumber,prj.FLongNumber prjLongNumber from  T_CON_ContractCostSplitEntry entry  " +
						"inner join T_CON_ContractCostSplit head on head.FID = entry.FParentID  " +
						"inner join T_FDC_CostAccount acct on acct.FID = entry.FCostAccountID  " +
						"inner join T_FDC_CurProject prj on prj.FID = acct.FCurProject  " +
						"where head.FContractBillID = ? and FIsInValid = 0 and entry.FProductID is null and prj.fisleaf != 0 ");
				builder.addParam(contractBillId);
				IRowSet rowSet = builder.executeQuery();
				try {
					while(rowSet!=null && rowSet.next()){
						try {
							ContractCostSplitEntryInfo splitEntry = new ContractCostSplitEntryInfo();
							splitEntry.setId(BOSUuid.read(rowSet.getString("entryId")));
							CostAccountInfo acct = new CostAccountInfo();
							acct.setId(BOSUuid.read(rowSet.getString("acctId")));
							acct.setNumber(rowSet.getString("acctNumber"));
							acct.setLongNumber(rowSet.getString("acctLongNumber"));
							acct.setName(rowSet.getString("acctName"));
							acct.setIsLeaf(rowSet.getBoolean("isLeaf"));
							CurProjectInfo project = new CurProjectInfo();
							project.setId(BOSUuid.read(rowSet.getString("prjId")));
							project.setNumber(rowSet.getString("prjNumber"));
							project.setLongNumber(rowSet.getString("prjLongNumber"));
							project.setName(rowSet.getString("displayName"));
							splitEntry.setCostAccount(acct);
							acct.setCurProject(project);
							
							conSplitEntrys.add(splitEntry);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				} catch (UuidException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			costAccountMap.put(entry.getId().toString()+"_"+contractBillId, conSplitEntrys);
		}
		return costAccountMap;
	}
	/**
	 * 根据分录找到各个条目 
	 */
	private Map getItemsOfEntry(FDCDepConPayPlanEntryCollection entrys) {
		Map itemsMap = new HashMap();
		for(Iterator iter = entrys.iterator();iter.hasNext();){
			FDCDepConPayPlanEntryInfo entry = (FDCDepConPayPlanEntryInfo)iter.next();
			FDCDepConPayPlanItemCollection items = entry.getItems();
			for(Iterator _iter = items.iterator();_iter.hasNext();){
				FDCDepConPayPlanItemInfo item = (FDCDepConPayPlanItemInfo)_iter.next();
				//分录与条目两者的ID作为key
				itemsMap.put(entry.getId().toString()+"_"+item.getId().toString(), item);
			}
		}
		return itemsMap;
	}
}