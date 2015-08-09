package com.kingdee.eas.fdc.contract;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCAction;
import com.kingdee.eas.fdc.finance.ContractPayPlanCollection;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.eas.fdc.finance.IContractPayPlan;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;

public class ContractBillExecuteDataHander {
	private static Logger log = Logger.getLogger("com.kingdee.eas.fdc.contract.ContractBillExecuteDataHander");
	/**
	 * 获取合同执行情况数据
	 * 
	 * @param orgId
	 * @param params
	 * @param hasContract
	 *            包含合同
	 * @param hasNotText
	 *            包含无文本合同
	 * @return
	 * @throws BOSException
	 */
	public static List getContractExeData(final Set projectIds, final Map params, final boolean hasContract, final boolean hasNotText)
			throws BOSException {
		IFDCAction action = new IFDCAction() {
			private static final long serialVersionUID = -3353659956019261166L;
			public Object actionPerformed(Context ctx) throws Exception {
				try {
					System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<< getContractExeData... >>>>>>>>>>>>>>>>>>>>>>>>>>>");
					EntityViewInfo oldView = (EntityViewInfo) params.get("EntityViewInfo");
					List ret = null;
					long start = System.currentTimeMillis();
					if (hasContract) {
						ret = handleContract(ctx, projectIds, oldView, params);
					}
					System.out.println("handleContract1...:" + (System.currentTimeMillis() - start) + "; " + oldView.getFilter());
					start = System.currentTimeMillis();
					if (hasNotText) {
						FilterInfo oldFilter = oldView.getFilter();
						if (ret == null) {
							ret = handleNoTextContract(ctx, projectIds, oldFilter);
						} else {
							ret.addAll(handleNoTextContract(ctx, projectIds, oldFilter));
						}
					}
					System.out.println("handleNoTextContract2...:" + (System.currentTimeMillis() - start));
					start = System.currentTimeMillis();
					if (ret != null) {
						Collections.sort(ret);
					}
					return ret;
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					throw e;
				}
			}
		};
		return (List) FDCCommonServerHelper.execAction(action);
	}

	/**
	 * 获取合同计划付款信息
	 * 
	 * @param ctx
	 * @param idSet
	 * @return
	 * @throws Exception
	 */
	private static Map getPlanDatas(Context ctx, Set idSet) throws Exception {
		Map planMap = new HashMap();
		IContractPayPlan icpp = ContractPayPlanFactory.getLocalInstance(ctx);
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractId", idSet, CompareType.INCLUDE));
		evi.setFilter(filter);
		evi.getSelector().add(new SelectorItemInfo("id"));
		evi.getSelector().add(new SelectorItemInfo("payDate"));
		evi.getSelector().add(new SelectorItemInfo("payAmount"));
		evi.getSelector().add(new SelectorItemInfo("payOriAmount"));
		evi.getSelector().add(new SelectorItemInfo("contractId"));
		SorterItemInfo sorter = new SorterItemInfo("payDate");
		sorter.setSortType(SortType.ASCEND);
		evi.getSorter().add(sorter);
		ContractPayPlanCollection cppc = icpp.getContractPayPlanCollection(evi);
		if (cppc != null && cppc.size() != 0) {
			for (int i = 0; i < cppc.size(); i++) {
				Map map = new HashMap();
				map.put("payOriAmount", cppc.get(i).getPayOriAmount());
				map.put("payDate", cppc.get(i).getPayDate());
				map.put("payAmount", cppc.get(i).getPayAmount());
				// 返回合同ID,因为付款计划打开的时候传入的时合同ID
				String contractId = cppc.get(i).getContractId().getId().toString();
				map.put("id", contractId);
				ArrayList al = (ArrayList) planMap.get(contractId);
				if (al == null) {
					al = new ArrayList();
					planMap.put(contractId, al);
				}
				al.add(map);
			}
		}
		return planMap;
	}

	/**
	 * 获取合同实际付款信息
	 * 
	 * @param ctx
	 * @param idSet
	 * @param signFilter
	 * @return
	 * @throws BOSException
	 */
	private static Map getRealDatas(Context ctx, Set idSet, FilterInfo signFilter) throws BOSException {
		Set reId=new HashSet();
		Map realMap = new HashMap();
		EntityViewInfo evi;
		FilterInfo filter;
		SorterItemInfo sorter;
		IPayRequestBill ire=PayRequestBillFactory.getLocalInstance(ctx);
		evi = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractId", idSet, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED_VALUE,CompareType.NOTEQUALS));
		
		evi.setFilter(filter);
		evi.getSelector().add(new SelectorItemInfo("id"));
		evi.getSelector().add(new SelectorItemInfo("payDate"));
		evi.getSelector().add(new SelectorItemInfo("originalAmount"));
		evi.getSelector().add(new SelectorItemInfo("amount"));
		evi.getSelector().add(new SelectorItemInfo("bookedDate"));
		evi.getSelector().add(new SelectorItemInfo("contractId"));
		
		sorter = new SorterItemInfo("bookedDate");
		sorter.setSortType(SortType.ASCEND);
		evi.getSorter().add(sorter);
		PayRequestBillCollection irc = ire.getPayRequestBillCollection(evi);
		if (irc != null && irc.size() != 0) {
			for (int i = 0; i < irc.size(); i++) {
				Map map = new HashMap();
				map.put("reBookedDate", irc.get(i).getBookedDate());
				map.put("reOriAmt", irc.get(i).getOriginalAmount());
				map.put("reAmt", irc.get(i).getAmount());
				map.put("reId", irc.get(i).getId().toString());

				reId.add(irc.get(i).getId().toString());
				
				ArrayList al = (ArrayList) realMap.get(irc.get(i).getContractId());
				if (al == null) {
					al = new ArrayList();
					realMap.put(irc.get(i).getContractId(), al);
				}
				al.add(map);
			}
		}
		
		// real Map
		IPaymentBill ipb = PaymentBillFactory.getLocalInstance(ctx);
		evi = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("fdcPayReqID", reId, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("billStatus", BillStatusEnum.PAYED));
		if (signFilter != null) {
			filter.mergeFilter(signFilter, "and");
		}
		evi.setFilter(filter);
		evi.getSelector().add(new SelectorItemInfo("payDate"));
		evi.getSelector().add(new SelectorItemInfo("actPayLocAmt"));
		evi.getSelector().add(new SelectorItemInfo("actPayAmt"));
		evi.getSelector().add(new SelectorItemInfo("fdcPayReqID"));
		sorter = new SorterItemInfo("payDate");
		sorter.setSortType(SortType.ASCEND);
		PaymentBillCollection pbc = ipb.getPaymentBillCollection(evi);
		if (pbc != null && pbc.size() != 0) {
			for (int i = 0; i < pbc.size(); i++) {
				Map map=(Map) realMap.get(pbc.get(i).getFdcPayReqID());
				if(map==null){
					map = new HashMap();
					map.put("payDate", pbc.get(i).getPayDate());
					map.put("paySrcAmount", pbc.get(i).getActPayAmt());
					map.put("payAmount", pbc.get(i).getActPayLocAmt());
				}else{
					map.put("payDate", pbc.get(i).getPayDate());
					map.put("paySrcAmount", FDCHelper.add(map.get("paySrcAmount"), pbc.get(i).getActPayAmt()));
					map.put("payAmount", FDCHelper.add(map.get("payAmount"), pbc.get(i).getActPayLocAmt()));
				}
				realMap.put(pbc.get(i).getFdcPayReqID(), map);
			}
		}
		return realMap;
	}
	/**
	 * 批量-取合同最新造价（如果已结算，则取结算价；否则取合同金额+变更金额+奖励-索赔-扣款(已被申请单关联的)）
	 * 
	 * @param String
	 *            []
	 * @return Map
	 * @throws BOSException
	 * @throws SQLException
	 * @deprecated 已移到FDCUtils工具类里面 by sxhong 2008-11-24 14:04:23
	 */
	public static Map getLastAmt_Batch(Context ctx, String[] contractIdList) throws Exception {
		return FDCUtils.getLastAmt_Batch(ctx, contractIdList);
	}

	/**
	 * 获取合同执行情况信息
	 * 
	 * @param ctx
	 * @param projectIds
	 * @param oldView
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private static List handleContract(Context ctx, Set projectIds, EntityViewInfo oldView, Map params) throws Exception {
		List ret = new ArrayList();
		Map contractId2ExeData = new HashMap();
		ContractBillCollection contracts = listContractBill(ctx, projectIds, oldView); 
		if (contracts != null && !contracts.isEmpty()) {
			for (int i = 0; i < contracts.size(); i++) {
				ContractExecuteData data = new ContractExecuteData();
				contractId2ExeData.put(contracts.get(i).getId().toString(), data);
				ret.add(data);
				data.setContract(contracts.get(i));
			}
			fillContractPriceDatas(ctx, contractId2ExeData, oldView, params);
		}
		return ret;
	}

	// 获取合同付款信息（计划与实际付款）
	private static void getPayRecord(Context ctx, Map contractId2ExeData, EntityViewInfo oldView, Map planMap, Map realMap,
			boolean isDisplayPlan) throws Exception {
		Set contractIdSet = new HashSet();
		contractIdSet.addAll(contractId2ExeData.keySet());
		FilterInfo signFilter = new FilterInfo();
		if (oldView != null && oldView.getFilter() != null) {
			for (java.util.Iterator iter = oldView.getFilter().getFilterItems().iterator(); iter.hasNext();) {
				FilterItemInfo item = (FilterItemInfo) iter.next();
				if (item.getPropertyName().equals("signDate")) {
					FilterItemInfo clone = (FilterItemInfo) item.clone();
					clone.setPropertyName("paydate");
					signFilter.getFilterItems().add(clone);
				}
			}
		}
		// 计划付款信息
		if (isDisplayPlan && signFilter.getFilterItems().size() == 0) {
			Map temp = getPlanDatas(ctx, contractIdSet);
			if (temp != null) {
				planMap.putAll(temp);
			}
		}
		// 实际付款信息
		Map temp = getRealDatas(ctx, contractIdSet, signFilter);
		if (temp != null) {
			realMap.putAll(temp);
		}
	}

	// 填充合同执行情况数据的付款信息（计划与实际）
	private static void fillPayInfo(Context ctx, Map contractId2ExeData, EntityViewInfo oldView, boolean isDisplayPlan) throws Exception {
		Map planMap = new HashMap();
		Map realMap = new HashMap();
		getPayRecord(ctx, contractId2ExeData, oldView, planMap, realMap, isDisplayPlan);
		Collection entryCol = contractId2ExeData.values();
		Iterator entryIter = entryCol.iterator();
		while (entryIter.hasNext()) {
			ContractExecuteData data = (ContractExecuteData) entryIter.next();
			String contractId = data.getContract().getId().toString();
			List plans = (List) planMap.get(contractId);
			if (plans != null && !plans.isEmpty()) {
				for (int i = 0; i < plans.size(); ++i) {
					Map planInfo = (Map) plans.get(i);
					ContractExecuteData leafData = new ContractExecuteData();
					data.getChildren().add(leafData);
					leafData.setPlanPayId(planInfo.get("id"));
					leafData.setPlanPayDate(planInfo.get("payDate"));
					leafData.setPlanPayAmount((BigDecimal) planInfo.get("payAmount"));
					leafData.setPlanPaySrcAmount((BigDecimal) planInfo.get("payOriAmount"));
				}
			}
			List reals = (List) realMap.get(contractId);
			if (reals != null && !reals.isEmpty()) {
				for (int i = 0; i < reals.size(); ++i) {
					Map realInfo = (Map) reals.get(i);
					ContractExecuteData leafData = null;
					if (i < data.getChildren().size()) {
						leafData = (ContractExecuteData) data.getChildren().get(i);
					} else {
						leafData = new ContractExecuteData();
						data.getChildren().add(leafData);
					}
					leafData.setPlanPayId(realInfo.get("reId"));
					leafData.setPlanPayDate(realInfo.get("reBookedDate"));
					leafData.setPlanPayAmount((BigDecimal) realInfo.get("reAmt"));
					leafData.setPlanPaySrcAmount((BigDecimal) realInfo.get("reOriAmt"));
					
					Map payMap=(Map) realMap.get(realInfo.get("reId"));
					if(payMap!=null){
						leafData.setRealPayDate(payMap.get("payDate"));
						leafData.setRealPaySrcAmount((BigDecimal) payMap.get("paySrcAmount"));
						leafData.setRealPayAmount((BigDecimal) payMap.get("payAmount"));
					}
//					leafData.setRealPayId(realInfo.get("id"));
//					leafData.setRealPayDate(realMap.get("payDate"));
//					leafData.setPayBizDate(realInfo.get("bizDate"));
//					leafData.setRealPaySrcAmount((BigDecimal) realInfo.get("paySrcAmount"));
//					leafData.setRealPayAmount((BigDecimal) realInfo.get("payAmount"));
					// exchangeRate lastExhangeRate
//					BigDecimal projectPriceInContract = (BigDecimal) realInfo.get("projectPriceInContract");
					// if (realInfo.get("lastExhangeRate") != null) {
					// projectPriceInContract = FDCHelper.multiply(realInfo.get("lastExhangeRate"), projectPriceInContract);
					// } else if (realInfo.get("exchangeRate") != null) {
					// projectPriceInContract = FDCHelper.multiply(realInfo.get("exchangeRate"), projectPriceInContract);
					// }
//					leafData.setProjectPriceInContract(projectPriceInContract);
				}
			}
		}
	}

	// 填充合同价格数据（如已实现产值、变更金额、合同最新本位币造价最新原币造价、累计完工工程量、完工未付款、未付款、合同计划付款、实际付款等...）
	private static void fillContractPriceDatas(Context ctx, Map contractId2ExeData, EntityViewInfo oldView, Map params) throws Exception {
		Set contractIdSet = new HashSet();
		contractIdSet.addAll(contractId2ExeData.keySet());
		// 结算金额（已实现产值）
//		Map totalSettMap = ContractFacadeFactory.getLocalInstance(ctx).getTotalSettlePrice(contractIdSet);
		// 变更金额
		String[] contractIds = new String[contractId2ExeData.size()];
		contractIdSet.toArray(contractIds);
//		Map changeAmtMap = ContractFacadeFactory.getLocalInstance(ctx).getChangeAmt(contractIds);
		// 合同最新本位币造价
		Map lastAmtMap = FDCUtils.getLastAmt_Batch(ctx, contractIds);
		// 合同最新原币造价
		Map lastSrcAmpMap = FDCUtils.getLastOriginalAmt_Batch(ctx, contractIds);
		// 累计已完工工程量
		Collection entryCol = contractId2ExeData.values();
		Map orgId2ContractIdSet = new HashMap();
		for (Iterator iter = entryCol.iterator(); iter.hasNext();) {
			ContractBillInfo contract = ((ContractExecuteData) iter.next()).getContract();
			if (contract != null) {
				if (contract.getCurProject().getFullOrgUnit() != null) {
					String orgId = contract.getCurProject().getFullOrgUnit().getId().toString();
					Set conIdSet = (Set) orgId2ContractIdSet.get(orgId);
					if (conIdSet == null) {
						conIdSet = new HashSet();
						orgId2ContractIdSet.put(orgId, conIdSet);
					}
					conIdSet.add(contract.getId().toString());
				}
			}
		}
//		Map completePrjAmtMap = ContractExecFacadeFactory.getLocalInstance(ctx).getCompleteAmt(orgId2ContractIdSet);
		boolean isDisplayPlan = ((Boolean) params.get("isDisplayPlan")).booleanValue();
		boolean isMoreSett = ((Boolean) params.get("isMoreSett")).booleanValue();
		boolean allNotPaidParam = ((Boolean) params.get("allNotPaidParam")).booleanValue();
		fillPayInfo(ctx, contractId2ExeData, oldView, isDisplayPlan);
		Iterator entryIter = entryCol.iterator();
		while (entryIter.hasNext()) {
			ContractExecuteData data = (ContractExecuteData) entryIter.next();
			String contractId = data.getContract().getId().toString();
//			data.setTotalSettPrice((BigDecimal) totalSettMap.get(contractId.concat("_SettlePrice")));
			data.setContractLastAmount((BigDecimal) lastAmtMap.get(contractId));
			data.setContractLastSrcAmount((BigDecimal) lastSrcAmpMap.get(contractId));
//			data.setChangeAmount((BigDecimal) changeAmtMap.get(contractId));
//			data.setCompleteProjectAmount((BigDecimal) completePrjAmtMap.get(contractId));
			sumContract(data);
			calcNotPay(data, isMoreSett, allNotPaidParam);
		}

	}

	// 根据参数计算并设置完工未付款、未付款
	private static void calcNotPay(ContractExecuteData data, boolean isMoreSett, boolean allNotPaidParam) {
		// 按周鹏2010-12-24日需求， 完工未付款与未付款均减合同内工程款而不是实际已付金额 modify by zhiqiao_yang at 2010-12-24
		BigDecimal payAmount = data.getProjectPriceInContract(); // data.getRealPayAmount();
		BigDecimal completeNotPay = FDCHelper.ZERO;
		if (isMoreSett) { // 启用了多次结算
			if (data.getContract().isHasSettled()) { // 合同是最终结算
				if (allNotPaidParam) // 在启用了多次结算下，并且启用公式 ：完工未付款 ＝ 累计完工工程量-已付款
					completeNotPay = FDCHelper.subtract(data.getCompleteProjectAmount(), payAmount);
				else
					completeNotPay = FDCHelper.subtract(data.getContractLastAmount(), payAmount);
			} else { // 合同非最终结算
				if (allNotPaidParam) // 在启用了多次结算下，并且启用公式 ：完工未付款 ＝ 累计完工工程量-已付款
					completeNotPay = FDCHelper.subtract(data.getCompleteProjectAmount(), payAmount);
				else
					completeNotPay = FDCHelper.subtract(data.getTotalSettPrice(), payAmount);
			}
		} else { // 未启用多次结算
			if (data.getContract().isHasSettled()) { // 合同是最终结算
				completeNotPay = FDCHelper.subtract(data.getContractLastAmount(), payAmount);
			} else { // 合同非最终结算
				completeNotPay = FDCHelper.subtract(data.getCompleteProjectAmount(), payAmount);
			}
		}
		data.setCompleteNotPay(completeNotPay);
		data.setNotPay(FDCHelper.subtract(data.getContractLastAmount(), payAmount));

	}

	// 统计合同累计完工工程量、合同计划付款、实际付款、实际付款原币
	private static void sumContract(ContractExecuteData data) {
		if (!data.getChildren().isEmpty()) {
			BigDecimal planAmt = FDCHelper.ZERO;
			BigDecimal planSrcAmt = FDCHelper.ZERO;
			BigDecimal realAmt = FDCHelper.ZERO;
			BigDecimal realSrcAmt = FDCHelper.ZERO;
			BigDecimal projectPriceInContract = FDCConstants.ZERO;
			for (int i = 0; i < data.getChildren().size(); ++i) {
				ContractExecuteData child = (ContractExecuteData) data.getChildren().get(i);
				planAmt = FDCHelper.add(planAmt, child.getPlanPayAmount());
				planSrcAmt = FDCHelper.add(planSrcAmt, child.getPlanPaySrcAmount());
				realAmt = FDCHelper.add(realAmt, child.getRealPayAmount());
				projectPriceInContract = FDCHelper.add(projectPriceInContract, child.getProjectPriceInContract());
				realSrcAmt = FDCHelper.add(realSrcAmt, child.getRealPaySrcAmount());
			}
			data.setPlanPayAmount(planAmt);
			data.setRealPayAmount(realAmt);
			data.setProjectPriceInContract(projectPriceInContract);
			data.setRealPaySrcAmount(realSrcAmt);
			data.setPlanPaySrcAmount(planSrcAmt);
		}
	}

	/**
	 * 根据过滤获取所有合同
	 * 
	 * @Modified Owen_wen 该方法不应该改变oldView参数。
	 * @param ctx
	 * @param projectIds
	 *            工程项目ID
	 * @param oldView
	 *            自定义过滤界面的filter
	 * @return
	 * @throws BOSException
	 */
	private static ContractBillCollection listContractBill(Context ctx, Set projectIds, EntityViewInfo oldView) throws BOSException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", projectIds, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("isAmtWithoutCost", Boolean.FALSE));
		SorterItemInfo sorter = new SorterItemInfo("number");
		sorter.setSortType(SortType.DESCEND);
		EntityViewInfo view = (EntityViewInfo) oldView.clone();
		view.getSelector().add("*");
		view.getSelector().add("currency.name");
		view.getSelector().add("currency.precision");
		view.getSelector().add("partB.name");
		view.getSelector().add("creator.name");
		view.getSelector().add("curProject.name");
		view.getSelector().add("curProject.id");
		view.getSelector().add("curProject.fullOrgUnit");
		view.getSelector().add("contractType.name");
		FilterInfo oldFilter = view.getFilter();
		if (oldFilter != null) {
			// 这些代码是搞啥子的？？ by zhiqiao_yang
			// 回zhiqiao_yang， 从自定义过滤界面传过来的signDate条件，在这里不需要，因此删除此过滤条件。 by	Owen_wen
			// 删除掉signDate
			for (int i = oldFilter.getFilterItems().size() - 1; i >= 0; i--) {
				FilterItemInfo item = oldFilter.getFilterItems().get(i);
				String property = item.getPropertyName();
				if (property.startsWith("UPPER")) {
					property = property.substring(6, property.length() - 1);
					item.setPropertyName(property);
				}
				if ("signDate".equals(property)) {
					oldFilter.getFilterItems().get(i).setCompareValue(null);
					oldFilter.getFilterItems().get(i).setCompareType(CompareType.NOTEQUALS);
				}
			}
			filter.mergeFilter(oldFilter, "and");
		}
		view.setFilter(filter);
		return ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
	}

	private static List handleNoTextContract(Context ctx, Set projectIds, FilterInfo oldFilter) throws Exception {
		List ret = new ArrayList();
		ContractWithoutTextCollection contracts = listContractWithoutText(ctx, projectIds, oldFilter);
		if (contracts != null && !contracts.isEmpty()) {
			Map contractId2ExeData = new HashMap();
			for (int i = 0; i < contracts.size(); ++i) {
				ContractWithoutTextInfo contract = contracts.get(i);
				ContractExecuteData data = new ContractExecuteData();
				ret.add(data);
				contractId2ExeData.put(contract.getId().toString(), data);
				data.setNoTextContract(contract);
			}
			Set contractIdSet = new HashSet();
			contractIdSet.addAll(contractId2ExeData.keySet());
			addNoTextContractPayDatas(ctx, contractId2ExeData, oldFilter);
			Map conNoTextCompleteAmtMap = ContractExecFacadeFactory.getLocalInstance(ctx)
					._getCompletePrjAmtForNoTextContract(contractIdSet);
			EntityViewInfo ev = new EntityViewInfo();
			ev.getSelector().add("realSupplier.name");
			ev.getSelector().add("totalSettlePrice");
			ev.getSelector().add("contractId");
			FilterInfo fi = new FilterInfo();
			fi.getFilterItems().add(new FilterItemInfo("contractId", contractIdSet, CompareType.INCLUDE));
			ev.setFilter(fi);
			PayRequestBillCollection payReqCol = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(ev);
			for (int i = 0; i < ret.size(); ++i) {
				ContractExecuteData data = (ContractExecuteData) ret.get(i);
				String contractId = data.getNoTextContract().getId().toString();
				data.setCompleteProjectAmount((BigDecimal) conNoTextCompleteAmtMap.get(contractId));
				for (int j = 0; j < payReqCol.size(); ++j) {
					if (payReqCol.get(j).getRealSupplier() != null && contractId.equals(payReqCol.get(j).getContractId())) {// 无文本合同的对方单位为实际收款单位
						data.setPartB(payReqCol.get(j).getRealSupplier().getName());
						break;
					}
				}
				if (data.getChildren() != null && !data.getChildren().isEmpty()) {
					BigDecimal realAmt = FDCHelper.ZERO;
					BigDecimal projectPriceInContract = FDCConstants.ZERO;
					for (int j = 0; j < data.getChildren().size(); ++j) {
						ContractExecuteData child = (ContractExecuteData) data.getChildren().get(j);
						if (child.getRealPayAmount() != null) {
							realAmt = realAmt.add(child.getRealPayAmount());
						}
						if (child.getProjectPriceInContract() != null) {
							projectPriceInContract = projectPriceInContract.add(child.getProjectPriceInContract());
						}
					}
					data.setRealPayAmount(realAmt);
					data.setProjectPriceInContract(projectPriceInContract);
				}
				data.setCompleteNotPay(FDCHelper.subtract(data.getCompleteProjectAmount(), data.getRealPayAmount()));
			}
		}
		return ret;
	}

	// 获得无文本合同付款单数据
	private static void addNoTextContractPayDatas(Context ctx, Map contractId2ExeData, FilterInfo oldFilter) throws Exception {
		Set contractIdSet = new HashSet();
		contractIdSet.addAll(contractId2ExeData.keySet());
		// 无文本合同生成的付款单中已付款的付款单
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBillId", contractIdSet, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("billStatus", BillStatusEnum.PAYED));
		
		if (oldFilter != null) {
			for (Iterator it = oldFilter.getFilterItems().iterator(); it.hasNext();) {
				FilterItemInfo item = (FilterItemInfo) it.next();
				if (item.getPropertyName().equals("signDate")) {
					FilterItemInfo clone = (FilterItemInfo) item.clone();
					clone.setPropertyName("payDate");
					filter.getFilterItems().add(clone);
				}
			}
		}
		
		evi.setFilter(filter);
		evi.getSelector().add(new SelectorItemInfo("id"));
		evi.getSelector().add(new SelectorItemInfo("payDate"));
		evi.getSelector().add(new SelectorItemInfo("actPayAmt"));
		evi.getSelector().add(new SelectorItemInfo("actPayLocAmt"));
		evi.getSelector().add(new SelectorItemInfo("exchangeRate"));
		evi.getSelector().add(new SelectorItemInfo("lastExhangeRate"));
		evi.getSelector().add(new SelectorItemInfo("projectPriceInContract"));
		evi.getSelector().add(new SelectorItemInfo("contractBillId"));
		evi.getSelector().add(new SelectorItemInfo("bizDate"));
		SorterItemInfo sorter = new SorterItemInfo("payDate");
		sorter.setSortType(SortType.ASCEND);
		evi.getSorter().add(sorter);
		PaymentBillCollection pbc = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection(evi);
		if (pbc != null && pbc.size() != 0) {
			for (int i = 0; i < pbc.size(); i++) {
				String contractId = pbc.get(i).getContractBillId();
				ContractExecuteData data = (ContractExecuteData) contractId2ExeData.get(contractId);
				ContractExecuteData child = new ContractExecuteData();
				data.getChildren().add(child);
				child.setRealPayId(pbc.get(i).getId().toString());
				child.setRealPayDate(pbc.get(i).getPayDate());
				child.setPayBizDate(pbc.get(i).getBizDate());	//增加付款单的业务日期
				child.setRealPayAmount(pbc.get(i).getActPayLocAmt());
				child.setRealPaySrcAmount(pbc.get(i).getActPayAmt());
				// 不显示无文本合同的合同内工程款
				// BigDecimal projectPriceInContract = (BigDecimal) pbc.get(i).getProjectPriceInContract();
				// if (pbc.get(i).getLastExhangeRate() != null) {
				// projectPriceInContract = FDCHelper.multiply(pbc.get(i).getLastExhangeRate(), projectPriceInContract);
				// } else if (pbc.get(i).getExchangeRate() != null) {
				// projectPriceInContract = FDCHelper.multiply(pbc.get(i).getExchangeRate(), projectPriceInContract);
				// }
				// child.setProjectPriceInContract(pbc.get(i).getProjectPriceInContract());
			}
		}
	}

	/**
	 * 根据过滤条件，列出所有无文本合同，
	 * 
	 * @Modified Owen_wen 该方法不应该改变oldFilter参数值
	 * @param ctx
	 * @param projectIds
	 *            工程项目ID
	 * @param oldFilter
	 *            过滤面板传过来的Filter
	 * @return
	 * @throws BOSException
	 */
	private static ContractWithoutTextCollection listContractWithoutText(Context ctx, Set projectIds, FilterInfo oldFilter)
			throws BOSException {
		// 无文本合同
		EntityViewInfo viewInfo = new EntityViewInfo();
		viewInfo.getSelector().add("*");
		viewInfo.getSelector().add("curProject.name");
		viewInfo.getSelector().add("currency.name");
		viewInfo.getSelector().add("currency.precision");
		viewInfo.getSelector().add("contractType.name");
		viewInfo.getSelector().add("creator.name");
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("curProject.id", projectIds, CompareType.INCLUDE));
		if (oldFilter != null) {
			FilterInfo oldFilter2 = (FilterInfo) oldFilter.clone();
			// 删除掉signDate
			for (int i = oldFilter2.getFilterItems().size() - 1; i >= 0; i--) {
				FilterItemInfo item = oldFilter2.getFilterItems().get(i);
				String property = item.getPropertyName();
				if (property.startsWith("UPPER")) {
					property = property.substring(6, property.length() - 1);
					item.setPropertyName(property);
				}
				if ("signDate".equals(property)) {
					oldFilter2.getFilterItems().get(i).setCompareValue(null);
					oldFilter2.getFilterItems().get(i).setCompareType(CompareType.NOTEQUALS);
				}
				if ("partB.id".equals(property)) {
					oldFilter2 = new FilterInfo();
					oldFilter2.getFilterItems().add(new FilterItemInfo("id", null));
					break;
				}
				if ("hasSettled".equals(property)) {
					oldFilter2 = new FilterInfo();
					oldFilter2.getFilterItems().add(new FilterItemInfo("id", null));
					break;
				}
			}
			
			filterInfo.mergeFilter(oldFilter2, "and");
		}
		viewInfo.setFilter(filterInfo);
		return ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextCollection(viewInfo);
	}
}
