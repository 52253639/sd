package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.framework.ejb.EJBFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AIMAimCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.AimCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.AimProductTypeGetter;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.aimcost.CostRptException;
import com.kingdee.eas.fdc.aimcost.DyCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.DyProductTypeGetter;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotCollection;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotFactory;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotInfo;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotProTypEntryInfo;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotSettEntryInfo;
import com.kingdee.eas.fdc.aimcost.FDCProjectCostDetail;
import com.kingdee.eas.fdc.aimcost.FDCProjectCostDetailFactory;
import com.kingdee.eas.fdc.aimcost.FDCProjectCostDetailInfo;
import com.kingdee.eas.fdc.aimcost.FDCProjectCostDetailPlanEntryInfo;
import com.kingdee.eas.fdc.aimcost.HappenDataGetter;
import com.kingdee.eas.fdc.aimcost.HappenDataInfo;
import com.kingdee.eas.fdc.aimcost.ProjectCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.DepConPayPlanSplitItemCollection;
import com.kingdee.eas.fdc.finance.DepConPayPlanSplitItemFactory;
import com.kingdee.eas.fdc.finance.DepConPayPlanSplitItemInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

public class DynCostSnapShoter {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.DynCostSnapShoter");
	public static void saveSnapShot(Context ctx, String prjId,CostMonthlySaveTypeEnum savedType) throws BOSException, EASBizException {
		if(prjId==null||savedType==null){
			throw new IllegalArgumentException("prjId and savedType cannt be null");
		}
		PeriodInfo glCurPeriod=getCurrentPeriod(ctx, prjId, savedType);
/*		glCurPeriod=new  PeriodInfo();
		glCurPeriod.setId(BOSUuid.read("iIiv5AEYEADgAFd3wKg9BoI4jEw="));*/
		checkBeforeSaveSnapShot(ctx, prjId, savedType, glCurPeriod);
		deleteBeforeSaveSnapShot(ctx, prjId, savedType, glCurPeriod);
		addNewSnapShot(ctx, prjId, savedType, glCurPeriod);
//		updateProjectCostDetail(ctx,prjId);//先注释
	}
	
	public static void updateProjectCostDetail(Context ctx, String prjId) throws BOSException, EASBizException {
		/***
		 * 先获取服务器时间
		 */
		Date sdate = new Date();//鑫苑反馈问题FDCCommonServerHelper.getServerTime();
		Calendar cal = new GregorianCalendar();
		cal.setTime(sdate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		/***
		 * 删除当前工程项目下的，此月份的数据
		 */
		FilterInfo filter = new FilterInfo();
		//1.4不支持Integer.valueOf(int) by hpw
//		filter.getFilterItems().add(new FilterItemInfo("year",Integer.valueOf(String.valueOf(year)),CompareType.EQUALS));
//		filter.getFilterItems().add(new FilterItemInfo("month",Integer.valueOf(String.valueOf(month)),CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("year",new Integer(String.valueOf(year)),CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("month",new Integer(String.valueOf(month)),CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("projectID",prjId,CompareType.EQUALS));
		FDCProjectCostDetailFactory.getLocalInstance(ctx).delete(filter);
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		/***
		 * 重新获取数据
		 * 1、获取工程项目下的成本科目
		 */
		logger.info("获取工程项目下的成本科目");
		EntityViewInfo view=new EntityViewInfo();
    	view.getSelector().add("id");
    	view.getSelector().add("level");
    	view.getSelector().add("longNumber");
    	view.getSelector().add("name");
    	view.getSelector().add("isLeaf");
    	view.getSelector().add("curProject.id");
    	view.getSelector().add("curProject.name");
    	view.getSelector().add("curProject.longNumber");
    	view.getSelector().add("curProject.costCenter.id");
    	view.getSelector().add("curProject.costCenter.name");
    	view.getSelector().add("curProject.costCenter.longNumber");
    	view.setFilter(new FilterInfo());
    	view.getFilter().appendFilterItem("curProject.id", prjId);
    	
    	view.getFilter().appendFilterItem("isEnabled", Boolean.TRUE);
    	view.getSorter().add(new SorterItemInfo("longNumber"));
    	CostAccountCollection costAccounts=CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
    	
		/***
		 * 2、取科目的合同明细
		 */
    	logger.info("取科目的合同明细");
    	ParamValue paramValue = new ParamValue();
    	int maxLevel=0;
    	Set numberSet=new HashSet();
    	Map costAcctContractDetailMap = new HashMap();
    	Set leafPrjIds = new HashSet();
    	leafPrjIds.add(prjId);
    	paramValue.put("displayNoText", Boolean.TRUE);
    	paramValue.put("displayContract", Boolean.TRUE);
    	paramValue.put("leafPrjIds", leafPrjIds);
    	int size = costAccounts.size();
    	for(int i=0;i<costAccounts.size();i++){
    		CostAccountInfo info = costAccounts.get(i);
    		if(!info.isIsLeaf())
    			continue;
    		paramValue.put("acctLongNumber", info.getLongNumber());
    		logger.info("取科目的合同明细["+i+"/"+size+"]"+info.getLongNumber());
    		
    		costAcctContractDetailMap.put(info.getLongNumber(), ProjectCostRptFacadeFactory.getLocalInstance(ctx).getCollectionContractAcctCostDetails(paramValue));
			numberSet.add(info.getLongNumber());
    	}
    	logger.info("取科目的各成本信息");
    	
    	/****
    	 * 3.取科目的各成本信息
    	 */
    	builder.clear();
    	builder.appendSql("select fcostAcctLgNumber as acctNumber,  ");
    	builder.appendSql(" sum(FUnSettSignAmt) as unSettSignAmt,");
    	builder.appendSql(" sum(FSettSignAmt) as settSignAmt,");
    	builder.appendSql(" sum(FSettAdjustAmt) as settAdjustAmt,");
    	builder.appendSql(" sum(FUnContractCostAmt) as unContractCostAmt,");
    	builder.appendSql(" sum(FSoFarHasAmt) as soFarHasAmt,");
    	builder.appendSql(" sum(FCostPayedAmt) as costPayedAmt,");
    	builder.appendSql(" sum(FPayedAmt) as payedAmt,");
    	builder.appendSql(" sum(FSoFarToAmt) as soFarToAmt,");
    	builder.appendSql(" sum(FDynCostAmt) as dynCost,");
    	builder.appendSql(" sum(FAimCostAmt) as aimCost,");
    	builder.appendSql(" sum(FDiffAmt) as diffAmt ");
    	builder.appendSql(" from T_AIM_DynCostSnapShot shot where fsavedType=? and ");
    	builder.addParam(CostMonthlySaveTypeEnum.ONLYONE_VALUE);
    	builder.appendParam("fprojectid", prjId);
    	builder.appendSql(" group by fcostAcctLgNumber ");
    	IRowSet rowSet=builder.executeQuery();
    	RetValue costValues = new RetValue();
    	try{
			while(rowSet.next()){
				String acctNumber=rowSet.getString("acctNumber");
				RetValue subRet=getRowRetValue(acctNumber, numberSet, costValues);
				//目前已发生
				subRet.setBigDecimal("soFarHasAmt", FDCNumberHelper.add(subRet.getBigDecimal("soFarHasAmt"), rowSet.getBigDecimal("soFarHasAmt")));
				//目前待发生
				subRet.setBigDecimal("soFarToAmt", FDCNumberHelper.add(subRet.getBigDecimal("soFarToAmt"), rowSet.getBigDecimal("soFarToAmt")));
				//动态成本
    			subRet.setBigDecimal("dynCost", FDCNumberHelper.add(subRet.getBigDecimal("dynCost"), rowSet.getBigDecimal("dynCost")));
    			//目标成本
    			subRet.setBigDecimal("aimCost", FDCNumberHelper.add(subRet.getBigDecimal("aimCost"), rowSet.getBigDecimal("aimCost")));
    			//差异
    			subRet.setBigDecimal("diffAmt", FDCNumberHelper.add(subRet.getBigDecimal("diffAmt"), rowSet.getBigDecimal("diffAmt")));
    			
			}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	
    	
    	/***
    	 * 4、取科目付款计划分录信息
    	 */
    	logger.info("取科目付款计划分录信息");
    	view = new EntityViewInfo();
    	filter = new FilterInfo();
//    	filter.getFilterItems().add(new FilterItemInfo("entry.payPlanEntry.parent.year",Integer.valueOf(String.valueOf(year)),CompareType.EQUALS));
//    	filter.getFilterItems().add(new FilterItemInfo("entry.payPlanEntry.parent.month",Integer.valueOf(String.valueOf(month)),CompareType.EQUALS));
    	//1.4不支持Integer.valueOf by hpw
    	filter.getFilterItems().add(new FilterItemInfo("entry.payPlanEntry.parent.year",new Integer(String.valueOf(year)),CompareType.EQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("entry.payPlanEntry.parent.month",new Integer(String.valueOf(month)),CompareType.EQUALS));
    	
    	
    	filter.getFilterItems().add(new FilterItemInfo("entry.costAccount.curProject.id",prjId,CompareType.EQUALS));
    	
    	
    	view.setFilter(filter);
    	view.getSelector().add("*");
    	view.getSelector().add("entry.costAccount.id");
    	view.getSelector().add("entry.costAccount.curProject.id");
    	view.getSelector().add("entry.amount");
    	view.getSelector().add("entry.payPlanEntry.id");
    	view.getSelector().add("entry.payPlanEntry.contractBillId");
    	
    	CoreBaseCollection conPPLSplits = DepConPayPlanSplitItemFactory.getLocalInstance(ctx).getCollection(view);
    	Map accountContractPayPlanMap = new HashMap();
    	for(Iterator it = conPPLSplits.iterator();it.hasNext();){
    		Object obj = it.next();
    		if(obj instanceof DepConPayPlanSplitItemInfo){
    			DepConPayPlanSplitItemInfo info = (DepConPayPlanSplitItemInfo)obj;
    			String key = info.getEntry().getCostAccount().getId().toString();
    			key = key + "_" + info.getEntry().getPayPlanEntry().getContractBillId();
    			ArrayList entryInfos = null;
    			if(accountContractPayPlanMap.containsKey(key)){
    				entryInfos = (ArrayList)accountContractPayPlanMap.get(key);
    			}else{
    				entryInfos = new ArrayList();
    				accountContractPayPlanMap.put(key, entryInfos);
    			}
    			
    			entryInfos.add(info);
    		}
    		
    	}
    	
    	/***
    	 * 5、保存成本明细信息
    	 */
    	logger.info("组合计算成本明细信息，准备保存");
    	IORMappingDAO dao = null;
    	try {
			dao = ORMappingDAO.getInstance(new FDCProjectCostDetailInfo().getBOSType(), ctx, EJBFactory.getConnection(ctx));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	for(Iterator it = costAccounts.iterator();it.hasNext();){
    		CostAccountInfo costAccount = (CostAccountInfo)it.next();
    		String acctid = costAccount.getId().toString();
    		FDCProjectCostDetailInfo baseInfo = new FDCProjectCostDetailInfo();
    		baseInfo.setId(BOSUuid.create(baseInfo.getBOSType()));
    		baseInfo.setProjectID(costAccount.getCurProject().getId());
    		baseInfo.setProjectName(costAccount.getCurProject().getName());
    		baseInfo.setProjectNumber(costAccount.getCurProject().getLongNumber().replace('!','.'));
    		
    		baseInfo.setCostCenterID(costAccount.getCurProject().getCostCenter().getId());
    		baseInfo.setCostCenterName(costAccount.getCurProject().getCostCenter().getName());
    		baseInfo.setCostCenterNumber(costAccount.getCurProject().getCostCenter().getLongNumber().replace('!', '.'));
    		
    		baseInfo.setCostAccountID(costAccount.getId());
    		baseInfo.setCostAccountName(costAccount.getName());
    		baseInfo.setCostAccountNumber(costAccount.getLongNumber().replace('!', '.'));
    		baseInfo.setYear(year);
    		baseInfo.setMonth(month);
    		
    		FDCProjectCostDetailInfo info = (FDCProjectCostDetailInfo)baseInfo.clone();
    		info.setId(BOSUuid.create(info.getBOSType()));
    		if(costValues.containsKey(costAccount.getLongNumber())){
    			RetValue subRet = (RetValue)costValues.get(costAccount.getLongNumber());
    			info.setAimCostAmt(subRet.getBigDecimal("aimCost"));
    			info.setDynCostAmt(subRet.getBigDecimal("dynCost"));
    			info.setSoFarHasAmt(subRet.getBigDecimal("soFarHasAmt"));
    			info.setSoFarToAmt(subRet.getBigDecimal("soFarToAmt"));
    		}
    		
    		dao.addNewBatch(baseInfo);
    		
    		if(costAcctContractDetailMap.containsKey(costAccount.getLongNumber())){
    			RetValue  projectCostContractDetail = (RetValue)costAcctContractDetailMap.get(costAccount.getLongNumber());
    			if(projectCostContractDetail!=null&&projectCostContractDetail.containsKey("accountContracts")){
    				RetValue accountContracts = (RetValue)projectCostContractDetail.get("accountContracts");
    				if(accountContracts!=null&&accountContracts.containsKey(costAccount.getId().toString())){
    					List accountContractList = (List)accountContracts.get(costAccount.getId().toString());
    					RetValue accountContractSplitValues = (RetValue)projectCostContractDetail.get("accountContractSplitValues");
    					Map contractBills = (Map)projectCostContractDetail.get("contractBillMap");
    					Map contractWithoutTextBills = (Map)projectCostContractDetail.get("contractWithoutTextMap");
    					Map lastPriceMap = (Map)projectCostContractDetail.get("lastPriceMap");
    					
    					Map pbAmtMap = (Map)projectCostContractDetail.get("hasPayAmtMap");
    					Map pbProgAmtMap = (Map)projectCostContractDetail.get("hasPayProgAmtMap");
    					Map pbSetlAmtMap = (Map)projectCostContractDetail.get("hasPaySetlAmtMap");
    					Map pbKeepAmtMap = (Map)projectCostContractDetail.get("hasPayKeepAmtMap");
    					
    					Map prqAmtMap = (Map)projectCostContractDetail.get("shouldPayAmtMap");
    					Map prqProgAmtMap = (Map)projectCostContractDetail.get("shouldPayProgAmtMap");
    					Map prqSetlAmtMap = (Map)projectCostContractDetail.get("shouldPaySetlAmtMap");
    					Map prqKeepAmtMap = (Map)projectCostContractDetail.get("shouldPayKeepAmtMap");
    					
    					Map totalSettlePriceMap = (Map)projectCostContractDetail.get("totalSettlePriceMap");
    					    					
    					for(Iterator lIt = accountContractList.iterator();lIt.hasNext();){
    						String contractId = (String) lIt.next();
    						FDCProjectCostDetailInfo costInfo = (FDCProjectCostDetailInfo)baseInfo.clone();
    						if(contractBills!=null&&contractBills.containsKey(contractId)){
    							/**
    							 * 合同
    							 */
    							
    							ContractBillInfo contract = (ContractBillInfo)contractBills.get(contractId); 
    							
    							costInfo.setIsNoText(false);
    							costInfo.setId(BOSUuid.create(costInfo.getBOSType()));
    							costInfo.setContractID(contract.getId());
    							costInfo.setContractName(contract.getName());
    							costInfo.setContractNumber(contract.getNumber());
    							costInfo.setContractProjectID(contract.getCurProject().getId());
    							costInfo.setContractProjectName(contract.getCurProject().getName());
    							costInfo.setContractProjectNumber(contract.getCurProject().getLongNumber().replace('!', '.'));
    							costInfo.setContractAmt(contract.getOriginalAmount());
    							costInfo.setContractLocalAmt(contract.getAmount());
    							costInfo.setPartBName(contract.getPartB().getName());
    							costInfo.setSignDate(contract.getSignDate());
    							costInfo.setCurrencyName(contract.getCurrency().getName());
    							
    							/***
    							 * 合同最新造价
    							 */
    							if(lastPriceMap!=null&&lastPriceMap.containsKey(contractId)){
    								costInfo.setContractLastAmt(FDCHelper.toBigDecimal(lastPriceMap.get(contractId)));
    							}
    							
    							/**
    							 * 合同累计已实现产值
    							 */
    							if(totalSettlePriceMap!=null&&totalSettlePriceMap.containsKey(contractId)){
    								costInfo.setContractTotalSettle(FDCHelper.toBigDecimal(totalSettlePriceMap.get(contractId)));
    							}
    						}
    						else if(contractWithoutTextBills!=null&&contractWithoutTextBills.containsKey(contractId)){
    							/**
    							 * 无文本合同
    							 */
    							ContractWithoutTextInfo contract = (ContractWithoutTextInfo)contractWithoutTextBills.get(contractId);
    							
    							costInfo.setIsNoText(true);
    							costInfo.setId(BOSUuid.create(costInfo.getBOSType()));
    							costInfo.setContractID(contract.getId());
    							costInfo.setContractName(contract.getName());
    							costInfo.setContractNumber(contract.getNumber());
    							costInfo.setContractProjectID(contract.getCurProject().getId());
    							costInfo.setContractProjectName(contract.getCurProject().getName());
    							costInfo.setContractProjectNumber(contract.getCurProject().getLongNumber().replace('!', '.'));
    							costInfo.setContractAmt(contract.getOriginalAmount());
    							costInfo.setContractLocalAmt(contract.getAmount());
    							costInfo.setPartBName(((SupplierInfo)(contract.get("PartB"))).getName());
    							costInfo.setSignDate(contract.getSignDate());
    							costInfo.setCurrencyName(contract.getCurrency().getName());
    							costInfo.setContractLastAmt(contract.getAmount());
    							costInfo.setContractTotalSettle(contract.getAmount());
    						}
    						
    						/***
							 * 合同已付金额
							 */
							if(pbAmtMap!=null&&pbAmtMap.containsKey(contractId)){
								costInfo.setContractPayAmt(FDCHelper.toBigDecimal(pbAmtMap.get(contractId)));
							}
							if(pbProgAmtMap!=null&&pbProgAmtMap.containsKey(contractId)){
								costInfo.setContractPayProgAmt(FDCHelper.toBigDecimal(pbProgAmtMap.get(contractId)));
							}
							if(pbSetlAmtMap!=null&&pbSetlAmtMap.containsKey(contractId)){
								costInfo.setContractPaySetlAmt(FDCHelper.toBigDecimal(pbSetlAmtMap.get(contractId)));
							}
							if(pbKeepAmtMap!=null&&pbKeepAmtMap.containsKey(contractId)){
								costInfo.setContractPayKeepAmt(FDCHelper.toBigDecimal(pbKeepAmtMap.get(contractId)));
							}
							
							/***
							 * 合同应付金额
							 */
							if(prqAmtMap!=null&&prqAmtMap.containsKey(contractId)){
								costInfo.setContractReqAmt(FDCHelper.toBigDecimal(prqAmtMap.get(contractId)));
							}
							if(prqProgAmtMap!=null&&prqProgAmtMap.containsKey(contractId)){
								costInfo.setContractReqProgAmt(FDCHelper.toBigDecimal(prqProgAmtMap.get(contractId)));
							}
							if(prqSetlAmtMap!=null&&prqSetlAmtMap.containsKey(contractId)){
								costInfo.setContractReqSetlAmt(FDCHelper.toBigDecimal(prqSetlAmtMap.get(contractId)));
							}
							if(prqKeepAmtMap!=null&&prqKeepAmtMap.containsKey(contractId)){
								costInfo.setContractReqKeepAmt(FDCHelper.toBigDecimal(prqKeepAmtMap.get(contractId)));
							}
							/***
							 * 合同、变更、结算、付款拆分金额
							 */
							if(accountContractSplitValues!=null&&accountContractSplitValues.containsKey(acctid+contractId)){
								RetValue accountContractSplitValue = (RetValue)accountContractSplitValues.get(acctid+contractId);
								
								/**
								 * 合同拆分金额
								 */
								costInfo.setContractSplitAmt(accountContractSplitValue.getBigDecimal("CONTRACTSPLIT"));
								/**
								 * 变更
								 */
								costInfo.setChangeSplitAmt(accountContractSplitValue.getBigDecimal("CNTRCHANGESPLIT"));
								/***
								 * 结算
								 */
								costInfo.setSettleSplitAmt(accountContractSplitValue.getBigDecimal("SETTLEMENTSPLIT"));
								    								
								/***
								 * 已实现产值
								 */
								costInfo.setTotalSettleAmt(accountContractSplitValue.getBigDecimal("totalSettlePrice"));
								
								/**
								 * 付款拆分
								 */
								costInfo.setPaymentSplitAmt(accountContractSplitValue.getBigDecimal("PAYMENTSPLIT"));
								
							}
							if(accountContractPayPlanMap.containsKey(acctid+"_"+contractId)){
								ArrayList entryInfos = (ArrayList)accountContractPayPlanMap.get(acctid+"_"+contractId);
								for(int i=0;i<entryInfos.size();i++){
									DepConPayPlanSplitItemInfo pplsinfo = (DepConPayPlanSplitItemInfo)entryInfos.get(i);
									FDCProjectCostDetailPlanEntryInfo costEntryInfo = new FDCProjectCostDetailPlanEntryInfo();
									costEntryInfo.setHeadID(costInfo);
									costEntryInfo.setId(BOSUuid.create(costEntryInfo.getBOSType()));
									costEntryInfo.setAccountRequestAmt(pplsinfo.getSplitAmt());
									costEntryInfo.setYear(pplsinfo.getYear());
									costEntryInfo.setMonth(pplsinfo.getMonth());
									costInfo.getEntrys().add(costEntryInfo);
								}
							}
							dao.addNewBatch(costInfo);
    					}
    				}
    			}
    		}
    	}
    	dao.executeBatch();
		
	}
	private static RetValue getRowRetValue(String acctNumber,Set acctNumberSet,RetValue retValue){
    	String number=getAcctNumber(acctNumber, acctNumberSet);
    	if(number==null){
    		throw new NullPointerException("bad number!");
    	}
    	RetValue subRet = (RetValue)retValue.get(number);
    	if(subRet==null){
    		subRet=new RetValue();
    		retValue.put(number, subRet);
    	}
		return subRet;
    }
	private static String getAcctNumber(String acctNumber,Set acctNumberSet){
    	if(acctNumber!=null){
    		acctNumber=acctNumber.replace('.', '!');
    	}
    	//R101216-187  按之前的处理当acctNumber在acctNumberSet不存在时出现死循环  by zhiyuan_tang
    	while (acctNumber != null) {
    		if(acctNumberSet.contains(acctNumber)){
    			return acctNumber;
    		} else {
    			acctNumber = getParentAcctNumber(acctNumber);
    		}
    	}
    	return null;
    }
    
    /**
     * 获取该科目的上级科目，没有时返回NULL
     * @param acctNumber
     * @return 上级科目的Number
     */
    private static String getParentAcctNumber(String acctNumber) {
    	if (acctNumber.lastIndexOf('!') > 0) {
    		return acctNumber.substring(0, acctNumber.lastIndexOf('!'));
    	} else {
    		return null;
    	}
    }

	private static void checkBeforeSaveSnapShot(Context ctx,String prjId, CostMonthlySaveTypeEnum savedType,PeriodInfo glCurPeriod) throws BOSException,EASBizException{
		if(savedType==CostMonthlySaveTypeEnum.COSTMONTHLY||savedType==CostMonthlySaveTypeEnum.FINANCEMONTHLY) {
			if(glCurPeriod != null && glCurPeriod.getId() != null) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("projectId", prjId));
				filter.appendFilterItem("period.id", glCurPeriod.getId().toString());
				filter.appendFilterItem("isUsed", Boolean.TRUE);
				
				boolean exists = DynCostSnapShotFactory.getLocalInstance(ctx).exists(filter);
				
				if(exists) {
					//该工程项目当前期间已经月结，且竣工结算账务处理已使用此数据，不能再月结
					throw new CostRptException(CostRptException.CANNTMONTHSAVE);
				}
			}
			else {
				//无法获取当前工程项目的期间，不能月结
				throw new CostRptException(CostRptException.CANNTGETPERIOD);
			}
			
			if(savedType==CostMonthlySaveTypeEnum.FINANCEMONTHLY){
				if(true) return;
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("projectId", prjId));
				filter.appendFilterItem("period.id", glCurPeriod.getId().toString());
				filter.appendFilterItem("savedType",CostMonthlySaveTypeEnum.COSTMONTHLY_VALUE);
				
				boolean exists = DynCostSnapShotFactory.getLocalInstance(ctx).exists(filter);
				
				if(!exists) {
					//财务月结对应的成本月结期间数据不存在,不能进行财务月结
					throw new CostRptException(CostRptException.NOTEXISTCOSTMONTHSAVED);
				}
			}
		}

	}
	
	private static void deleteBeforeSaveSnapShot(Context ctx,String prjId, CostMonthlySaveTypeEnum savedType,PeriodInfo glCurPeriod) throws EASBizException, BOSException{
		// 如果今天/当前期的已经存在，删除
		if(savedType==CostMonthlySaveTypeEnum.FINANCEMONTHLY) {
			return;
		}
		FilterInfo filter = new FilterInfo();
		if(savedType==CostMonthlySaveTypeEnum.COSTMONTHLY&&glCurPeriod!=null) {
			filter.getFilterItems().add(
					new FilterItemInfo("projectId", prjId));
			filter.appendFilterItem("period.id", glCurPeriod.getId().toString());
			filter.appendFilterItem("savedType",CostMonthlySaveTypeEnum.COSTMONTHLY_VALUE);
			
		} 
		
		if(savedType==CostMonthlySaveTypeEnum.COMMON||savedType==CostMonthlySaveTypeEnum.AUTOSAVE){
			Date currentDate = null;
			if(savedType==CostMonthlySaveTypeEnum.COMMON){
				currentDate=DateTimeUtils.truncateDate(new Date());
				filter.getFilterItems().add(
						new FilterItemInfo("snapShotDate", currentDate));
			}else{
				if(ctx.get("autoSaveSnapShotDate")!=null){
					currentDate=(java.sql.Date)ctx.get("autoSaveSnapShotDate");
				}else{
					currentDate=DateTimeUtils.truncateDate(new Date());
				}
				//保证一月只有一条
				Date startDate=FDCDateHelper.getFirstDayOfMonth(currentDate);
				filter.getFilterItems().add(
						new FilterItemInfo("snapShotDate", startDate,CompareType.GREATER_EQUALS));
				filter.getFilterItems().add(
						new FilterItemInfo("snapShotDate", currentDate,CompareType.LESS_EQUALS));

			}
			filter.getFilterItems().add(
					new FilterItemInfo("projectId", prjId));
			filter.appendFilterItem("savedType",savedType.getValue());
	
		}
		//仅有一份保存，用于报表动态取数，再次保存先删除之前的然后再保存  by sxhong 2008-12-12 11:30:34
		if(savedType==CostMonthlySaveTypeEnum.ONLYONE){
			filter.getFilterItems().add(
					new FilterItemInfo("projectId", prjId));
			filter.appendFilterItem("savedType",savedType.getValue());
		}
		DynCostSnapShotFactory.getLocalInstance(ctx).delete(filter);
	}
	

	private static void addNewSnapShot(Context ctx,String prjId, CostMonthlySaveTypeEnum savedType,PeriodInfo glCurPeriod) throws BOSException,EASBizException{
		HappenDataGetter happenGetter=null;
		AimProductTypeGetter aimProductTypeGetter = null;
		DyProductTypeGetter dyProductTypeGetter = null;
		// 两个get的指标一致，初始化一个指标即可
		// 各产品目标成本分摊
		AIMAimCostSplitDataGetter aimAimGetter = null;
		// 各产品动态目标成本分摊
		AimCostSplitDataGetter aimGetter = null;
		DyCostSplitDataGetter dyGetter = null;
		
		BigDecimal buildArea=null;
		BigDecimal sellArea=null;
		Map aimSellApportionMap=null;
		if(savedType==CostMonthlySaveTypeEnum.COSTMONTHLY||savedType==CostMonthlySaveTypeEnum.FINANCEMONTHLY){
			PeriodDataGetter periodDataGetter=new PeriodDataGetter(ctx,prjId,glCurPeriod);
			happenGetter=periodDataGetter.getHappenGetter();
			aimProductTypeGetter=periodDataGetter.getAimProductTypeGetter();
			dyProductTypeGetter=periodDataGetter.getDyProductTypeGetter();
			dyProductTypeGetter.setDyApportionMap(aimProductTypeGetter.getAimApportionMap());
			aimGetter=periodDataGetter.getAimGetter();
			aimGetter.initProductSplitData();
			dyGetter=periodDataGetter.getDyGetter();
			dyGetter.initProductSplitData();
			buildArea=periodDataGetter.getBuildArea();
			sellArea=periodDataGetter.getSellArea();
			aimSellApportionMap=periodDataGetter.getAimSellApportionMap();
		}else{
			happenGetter=new HappenDataGetter(ctx,prjId,true,true,true);
			aimProductTypeGetter = new AimProductTypeGetter(ctx, prjId, ProjectStageEnum.DYNCOST);
			dyProductTypeGetter = new DyProductTypeGetter(ctx, prjId);
			// 两个get的指标一致，初始化一个即可指标
			dyProductTypeGetter.setDyApportionMap(aimProductTypeGetter.getAimApportionMap());
			aimAimGetter = new AIMAimCostSplitDataGetter(ctx,prjId);
			aimAimGetter.initProductSplitData();
			aimGetter = new AimCostSplitDataGetter(ctx, prjId, aimProductTypeGetter);
			aimGetter.initProductSplitData();
			dyGetter = new DyCostSplitDataGetter(ctx, prjId, aimGetter, happenGetter, dyProductTypeGetter);
			dyGetter.initProductSplitData();
			buildArea = (BigDecimal)dyProductTypeGetter.getDyApportionMap().get(prjId+" "+ApportionTypeInfo.buildAreaType);
			sellArea =(BigDecimal)dyProductTypeGetter.getDyApportionMap().get(prjId+" "+ApportionTypeInfo.sellAreaType);
			aimSellApportionMap = ProjectHelper.getIndexValueByProjProd(ctx, prjId, ApportionTypeInfo.sellAreaType, ProjectStageEnum.AIMCOST);

		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.getSorter().add(new SorterItemInfo("number"));
		view.getSelector().add("id");
		view.getSelector().add("name");
		ChangeTypeCollection changeTypes = ChangeTypeFactory.getLocalInstance(ctx).getChangeTypeCollection(view);
		if(changeTypes==null){
			changeTypes=new ChangeTypeCollection();
		}
		
		view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("curProject.id", prjId);
		view.getFilter().appendFilterItem("isLeaf", Boolean.TRUE);
		view.getFilter().appendFilterItem("isEnabled", Boolean.TRUE);
		view.getSelector().add("id");
		view.getSelector().add("longNumber");
		CostAccountCollection accts=CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		DynCostSnapShotCollection dynCostSnapShotCollection=new  DynCostSnapShotCollection();
		for(int i=0;i<accts.size();i++){
			DynCostSnapShotInfo info = new DynCostSnapShotInfo();
			info.setCostAccountId(accts.get(i).getId());
			final String longNumber = accts.get(i).getLongNumber();
			if(!FDCHelper.isEmpty(longNumber)){
				info.setCostAcctLgNumber(longNumber.replace('!', '.'));
			}
			info.setProjectId(BOSUuid.read(prjId));
			info.put("changeTypes", changeTypes);
			info.put("happenGetter", happenGetter);
//			info.put("aimCostDataMap", aimCostDataMap);
//			info.put("dynCostDataMap", dynCostDataMap);
			info.put("aimAimGetter", aimAimGetter);
			info.put("aimGetter", aimGetter);
			info.put("dyGetter", dyGetter);
			info.put("buildArea", buildArea);
			info.put("sellArea", sellArea); 
			info.put("CostMonthlySaveTypeEnum",savedType);
			info.put("glCurPeriod",glCurPeriod);
			info.put("aimSellApportionMap",aimSellApportionMap);
			info.put("prjId", prjId);
			buildDynCostSnapShotInfo(info);
			dynCostSnapShotCollection.add(info);
		}
		if(savedType==CostMonthlySaveTypeEnum.FINANCEMONTHLY){
			submitFinanceDynCostSnapShot(ctx, dynCostSnapShotCollection);
		}else{
			submitDynCostSnapShot(ctx,dynCostSnapShotCollection);
		}
		
	}

	private static void buildDynCostSnapShotInfo(DynCostSnapShotInfo info) throws BOSException{
		if(info==null){
			return;
		}
		info.setId(BOSUuid.create(info.getBOSType()));
		String acctId=info.getCostAccountId().toString();
		CostMonthlySaveTypeEnum savedType=((CostMonthlySaveTypeEnum)info.get("CostMonthlySaveTypeEnum"));
		PeriodInfo glCurPeriod=(PeriodInfo)info.get("glCurPeriod");
		ChangeTypeCollection changeTypes=(ChangeTypeCollection)info.get("changeTypes");
		HappenDataGetter happenGetter=(HappenDataGetter)info.get("happenGetter");
//		AIMAimCostSplitDataGetter aimAimGetter = (AIMAimCostSplitDataGetter)info.get("aimAimGetter");
		AimCostSplitDataGetter aimGetter=(AimCostSplitDataGetter)info.get("aimGetter");
		DyCostSplitDataGetter dyGetter=(DyCostSplitDataGetter)info.get("dyGetter");
//		Map aimCostMap=(Map)info.get("aimCostDataMap");
//		Map dynamicCostMap=(Map)info.get("dynCostDataMap");
		BigDecimal buildArea=(BigDecimal)info.get("buildArea");
		BigDecimal sellArea=(BigDecimal)info.get("sellArea");
		HappenDataInfo conNoSettleHappenDataInfo = (HappenDataInfo) happenGetter.conSplitMap
				.get(acctId + 0);

		HappenDataInfo conSettleHappenDataInfo = (HappenDataInfo) happenGetter.conSplitMap
		.get(acctId + 1);
		HappenDataInfo settleHappenDataInfo = (HappenDataInfo) happenGetter.settleSplitMap.get(acctId);
		
		HappenDataInfo noTexthappenDataInfo = (HappenDataInfo) happenGetter.noTextSplitMap.get(acctId);
		//部分结算数据,用于计算已实现产值
		HappenDataInfo partSettledHappenDateInfo=(happenGetter.partSettleSplitMap==null)?null:(HappenDataInfo)happenGetter.partSettleSplitMap.get(acctId);
		//已付款
		HappenDataInfo payedHappenDateInfo=(happenGetter.paidSplitMap==null)?null:(HappenDataInfo)happenGetter.paidSplitMap.get(acctId);
	
		if(conNoSettleHappenDataInfo==null){
			conNoSettleHappenDataInfo=new HappenDataInfo();
		}
		if(conSettleHappenDataInfo==null){
			conSettleHappenDataInfo=new HappenDataInfo();
		}
		if(settleHappenDataInfo==null){
			settleHappenDataInfo=new HappenDataInfo();
		}
		if(noTexthappenDataInfo==null){
			noTexthappenDataInfo=new HappenDataInfo();
		}
		if(partSettledHappenDateInfo==null){
			partSettledHappenDateInfo=new HappenDataInfo();
		}
		if(payedHappenDateInfo==null){
			payedHappenDateInfo=new HappenDataInfo();
		}
		info.setApprotionTypeId(dyGetter.getSplitApportionType(acctId));
		info.setIsMonthSave(true);
		info.setPeriod(glCurPeriod);
		info.setIsUsed(false);
		info.setSavedType(savedType);
		
		if (conNoSettleHappenDataInfo != null) {
			info.setUnSettSignAmt(FDCHelper.toBigDecimal(conNoSettleHappenDataInfo.getAmount(), 2));
		}
		if(conSettleHappenDataInfo!=null){
			info.setSettSignAmt(FDCHelper.toBigDecimal(conSettleHappenDataInfo.getAmount(), 2));
		}
		
		//结算调整＝结算金额－合同结算金额-变更金额
		final BigDecimal settleTotal = FDCHelper.toBigDecimal(settleHappenDataInfo.getAmount());
		final BigDecimal settConAmount = FDCHelper.toBigDecimal(conSettleHappenDataInfo.getAmount());
//		final BigDecimal noSettleTotal = FDCHelper.toBigDecimal(conNoSettleHappenDataInfo.getAmount()); 
		final BigDecimal noTextAmount = noTexthappenDataInfo.getAmount();
		final BigDecimal aimAmount = aimGetter.getAimCost(acctId);
		final BigDecimal dynamicAmount = dyGetter.getDynamicValue(acctId);
		if(aimAmount!=null){
			info.setAimCostAmt(FDCHelper.toBigDecimal(aimAmount, 2));
		}
		if(dynamicAmount!=null){
			info.setDynCostAmt(FDCHelper.toBigDecimal(dynamicAmount, 2));
		}

		info.setUnContractCostAmt(noTextAmount);
		
		BigDecimal noSettleChangeSumAmount=FDCHelper.ZERO;
		BigDecimal settleChangeSumAmount=FDCHelper.ZERO;
		for (int i = 0; i < changeTypes.size(); i++) {
			ChangeTypeInfo change = changeTypes.get(i);
			HappenDataInfo happenDataInfo_noSett = (HappenDataInfo) happenGetter.changeSplitMap
					.get(acctId + change.getId().toString() + 0);
			HappenDataInfo happenDataInfo_Sett = (HappenDataInfo) happenGetter.changeSplitMap
			.get(acctId + change.getId().toString() + 1);
			if(happenDataInfo_noSett==null){
				happenDataInfo_noSett=new HappenDataInfo();
			}
			if(happenDataInfo_Sett==null){
				happenDataInfo_Sett=new HappenDataInfo();
			}
			DynCostSnapShotSettEntryInfo settEntryInfo=new DynCostSnapShotSettEntryInfo();
			settEntryInfo.setId(BOSUuid.create(settEntryInfo.getBOSType()));
			settEntryInfo.setParent(info);
			settEntryInfo.setChangeTypeId(change.getId());
			settEntryInfo.setUnSettleAmt(happenDataInfo_noSett.getAmount());
			settEntryInfo.setSettleAmt(happenDataInfo_Sett.getAmount());
			info.getSettEntries().add(settEntryInfo);
			
			noSettleChangeSumAmount=noSettleChangeSumAmount.add(FDCHelper.toBigDecimal(settEntryInfo.getUnSettleAmt()));
			settleChangeSumAmount=settleChangeSumAmount.add(FDCHelper.toBigDecimal(settEntryInfo.getSettleAmt()));
		}
		//设置变更的金额
		info.setUnSettleChangeAmt(noSettleChangeSumAmount);
		info.setSettleChangeAmt(settleChangeSumAmount);
		info.setSettAdjustAmt(settleTotal.subtract(settConAmount).subtract(settleChangeSumAmount));
		//hasHappenAmount 已发生
		BigDecimal hasHappenAmount = happenGetter.getHappenInfo(acctId)!=null?happenGetter.getHappenInfo(acctId).getAmount():null;
		//已实现
		BigDecimal hasCostPayedAmount = happenGetter.getHasPayInfo(acctId)!=null?happenGetter.getHasPayInfo(acctId).getAmount():null;
		//已付款
		BigDecimal hasPayedAmount = payedHappenDateInfo.getAmount();
		/*		
		 * if (noSettleTotal != null) {
			hasHappenAmount = noSettleTotal;
		}
		if (settleTotal != null) {
			if (hasHappenAmount == null) {
				hasHappenAmount = FDCHelper.ZERO;
			}
			hasHappenAmount = hasHappenAmount.add(settleTotal);
		}
		if (noTextAmount != null) {
			if (hasHappenAmount == null) {
				hasHappenAmount = FDCHelper.ZERO;
			}
			hasHappenAmount = hasHappenAmount.add(noTextAmount);
		}*/
		if(hasHappenAmount!=null){
			info.setSoFarHasAmt(FDCHelper.toBigDecimal(hasHappenAmount, 2));
		}
		//intendingHappen->setSoFarToAmt
		BigDecimal intendingHappen = null;
		if (dynamicAmount != null) {
			intendingHappen = dynamicAmount;
		}
		if (hasHappenAmount != null) {
			if (intendingHappen == null) {
				intendingHappen = FDCHelper.ZERO;
			}
			intendingHappen = intendingHappen.subtract(hasHappenAmount);
		}
		info.setSoFarToAmt(FDCHelper.toBigDecimal(intendingHappen, 2));
		//快照表增加已实现金额
		info.setCostPayedAmt(FDCHelper.toBigDecimal(hasCostPayedAmount,2));
		//已付款
		info.setPayedAmt(FDCHelper.toBigDecimal(hasPayedAmount));
		//diff
		BigDecimal diff = null;
		if (dynamicAmount != null) {
			diff = dynamicAmount;
		}
		if (aimAmount != null) {
			if (diff == null) {
				diff = FDCHelper.ZERO;
			}
			diff = diff.subtract(aimAmount);
			info.setDiffAmt(FDCHelper.toBigDecimal(diff, 2));
		}
		//单方
		BigDecimal sellPart = null;
		if (dynamicAmount != null && sellArea != null
				&& sellArea.compareTo(FDCHelper.ZERO) != 0) {
			sellPart = dynamicAmount.divide(sellArea,2,
					BigDecimal.ROUND_HALF_UP);
			if(sellPart!=null){
				info.setSalableAmt(FDCHelper.toBigDecimal(sellPart, 2));
			}
		}

		BigDecimal buildPart = null;
		if (dynamicAmount != null && buildArea != null
				&& buildArea.compareTo(FDCHelper.ZERO) != 0) {
			buildPart = dynamicAmount.divide(buildArea,2,
					BigDecimal.ROUND_HALF_UP);
			if(buildPart!=null){
				info.setConstrAmt(FDCHelper.toBigDecimal(buildPart, 2));
			}
		}
		
//		已实现产值：部分结算+已结算合同+非合同性成本之和
		BigDecimal partSettleAmt=FDCNumberHelper.toBigDecimal(partSettledHappenDateInfo.getAmount());
		BigDecimal realizedValue=FDCNumberHelper.add(partSettleAmt, settleTotal);
		realizedValue=FDCNumberHelper.add(realizedValue, noTextAmount);
		info.setRealizedValue(realizedValue);
		buildDynCostSnapShotProTypEntrys(info);
	
	}
	private static void buildDynCostSnapShotProTypEntrys(DynCostSnapShotInfo info) throws BOSException {
		String acctId=info.getCostAccountId().toString();
		String prjId=(String)info.get("prjId");
		AIMAimCostSplitDataGetter aimAimGetter=(AIMAimCostSplitDataGetter)info.get("aimAimGetter");
		AimCostSplitDataGetter aimGetter=(AimCostSplitDataGetter)info.get("aimGetter");
		DyCostSplitDataGetter dyGetter=(DyCostSplitDataGetter)info.get("dyGetter");
		Map aimAimApportionMap = aimGetter.getAimProductTypeGetter().getAimApportionMap();
		Map aimSellApportionMap=(Map)info.get("aimSellApportionMap");
		Map dynApportionMap=dyGetter.getDyProductTypeGetter().getDyApportionMap();
		
		final Map dyAmtProductMap = dyGetter.getDyProductMap(acctId);
		final Map happenCalculateData = dyGetter.getHasHappenProductMap(acctId);
		final Map hasPayProductMap = dyGetter.getHasPayProductMap(acctId);
		
		final Map aimAmtProductMap=aimGetter.getProductMap(acctId);
		final String[] productTypeIds = dyGetter.getDyProductTypeGetter().getProductTypeIds();
		Map aimAimAmtProductMap=null;
		if(aimAimGetter!=null){
			aimAimAmtProductMap=aimAimGetter.getProductMap(acctId);
		}else{
			//一体化月结的时候不会有这个操作
			aimAimAmtProductMap=new HashMap();
		}
		
		if(productTypeIds==null||productTypeIds.length==0){
			return;
		}
		for(int i=0;i<productTypeIds.length;i++){
			String productId=productTypeIds[i];
			if(productId==null){
				continue;
			}
			DynCostSnapShotProTypEntryInfo prodEntryInfo=new DynCostSnapShotProTypEntryInfo();
			prodEntryInfo.setId(BOSUuid.create(prodEntryInfo.getBOSType()));
			prodEntryInfo.setParent(info);
			prodEntryInfo.setProductTypeId(BOSUuid.read(productId));
			final BigDecimal aimAimCostAmt= (BigDecimal)aimAimAmtProductMap.get(productId);
			prodEntryInfo.setAimAimCostAmt(aimAimCostAmt);
			final BigDecimal aimCostAmt = (BigDecimal)aimAmtProductMap.get(productId);
			prodEntryInfo.setAimCostAmt(aimCostAmt);
			final BigDecimal dynCostAmt = (BigDecimal)dyAmtProductMap.get(productId);
			prodEntryInfo.setDynCostAmt(dynCostAmt);
			
			//单方
			final String indexKey = prjId+" "+productId+" "+ApportionTypeInfo.sellAreaType;
			BigDecimal aimAimIdxValue = (BigDecimal)aimAimApportionMap.get(indexKey);
			if(FDCHelper.toBigDecimal(aimAimIdxValue).signum()!=0){
				BigDecimal aimAimSellPart=FDCHelper.toBigDecimal(aimAimCostAmt).divide(aimAimIdxValue, 2, BigDecimal.ROUND_HALF_UP);
				prodEntryInfo.setAimAimSaleUnitAmt(aimAimSellPart);
			}
			BigDecimal aimIdxvalue=(BigDecimal)aimSellApportionMap.get(indexKey);
			if(FDCHelper.toBigDecimal(aimIdxvalue).signum()!=0){
				BigDecimal aimSellPart=FDCHelper.toBigDecimal(aimCostAmt).divide(aimIdxvalue, 2, BigDecimal.ROUND_HALF_UP);
				prodEntryInfo.setAimSaleUnitAmt(aimSellPart);
			}
			BigDecimal dynIdxValue=(BigDecimal)dynApportionMap.get(indexKey);
			if(FDCHelper.toBigDecimal(dynIdxValue).signum()!=0){
				BigDecimal dynSellPart=FDCHelper.toBigDecimal(dynCostAmt).divide(dynIdxValue, 2, BigDecimal.ROUND_HALF_UP);
				prodEntryInfo.setDynSaleUnitAmt(dynSellPart);
			}
			
			//hasHappen
			BigDecimal hasHappen=(BigDecimal)happenCalculateData.get(productId);
			BigDecimal notHappen=null;
			if(hasHappen==null){
				notHappen=dynCostAmt;
			}else{
				notHappen=FDCHelper.toBigDecimal(dynCostAmt).subtract(hasHappen);
			}
			prodEntryInfo.setHasHappenAmt(hasHappen);
			prodEntryInfo.setNotHappenAmt(notHappen);
			
			prodEntryInfo.setCostPayedAmt((BigDecimal)hasPayProductMap.get(productId));
			
			info.getProTypEntries().add(prodEntryInfo);
		}
		
	}

	private static void submitDynCostSnapShot(Context ctx,DynCostSnapShotCollection dynCostSnapShotCollection) throws BOSException {
		final Date date = new Date();
		java.sql.Date sqlDate=new java.sql.Date((DateTimeUtils.truncateDate(date)).getTime());
		if(ctx.get("autoSaveSnapShotDate")!=null){
			sqlDate=(java.sql.Date)ctx.get("autoSaveSnapShotDate");
		}
		final Timestamp timestamp = new Timestamp(date.getTime());
		final UserInfo currentUserInfo = ContextUtil.getCurrentUserInfo(ctx);
		final String userId=currentUserInfo!=null?currentUserInfo.getId().toString():null;
		final CtrlUnitInfo currentCtrlUnit = ContextUtil.getCurrentCtrlUnit(ctx);
		final String cuId=currentCtrlUnit!=null?currentCtrlUnit.getId().toString():null;
		String headSql="insert into T_AIM_DynCostSnapShot " +
				"(FID, FCreatorID, FCreateTime, FLastUpdateUserID, FLastUpdateTime, FControlUnitID, FProjectId, FCostAccountId, FSnapShotDate, FUnSettSignAmt, FSettSignAmt, FUnContractCostAmt, FSoFarHasAmt, FSoFarToAmt, FDynCostAmt, FAimCostAmt, FDiffAmt, FSalableAmt, FConstrAmt, FApprotionTypeId, FSettAdjustAmt, FCostAcctLgNumber, FIsMonthSave, FPeriodID, FIsUsed,FSavedType,FRealizedValue,FCostPayedAmt,fpayedAmt,funSettleChangeAmt,fsettleChangeAmt) " +
				"values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?)";
		String settleSql="insert into T_AIM_DynCostSnapShotSettEntry " +
				"(FID, FChangeTypeId, FUnSettleAmt, FSettleAmt, FParentID) " +
				"values(?, ?, ?, ?, ?)";
		String productEntrySql = "insert into T_AIM_DynCostSnpShtProTypEntry "
				+ "(FID, FParentID, FProductTypeId, FSalableUnitAmt, FAimCostAmt, FHasHappenAmt, FNotHappenAmt, FDynSaleUnitAmt, FDynCostAmt, FCostPayedAmt, FAimAimCostAmt, FAimAimSaleUnitAmt) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
		List headList=new ArrayList();
		List settleList=new ArrayList();
		List productEntryList=new ArrayList();
		
		for(int i=0;i<dynCostSnapShotCollection.size();i++){
			DynCostSnapShotInfo info=dynCostSnapShotCollection.get(i);
			String FID=info.getId().toString();
			String FCreatorID=userId;
			Timestamp FCreateTime=timestamp;
			String FLastUpdateUserID=userId;
			Timestamp FLastUpdateTime=timestamp;
			String FControlUnitID=cuId;
			String FProjectId=info.getProjectId().toString();
			String FCostAccountId=info.getCostAccountId().toString();
			java.sql.Date FSnapShotDate=sqlDate;
			BigDecimal FUnSettSignAmt=info.getUnSettSignAmt();
			BigDecimal FSettSignAmt=info.getSettSignAmt();
			BigDecimal FUnContractCostAmt=info.getUnContractCostAmt();
			BigDecimal FSoFarHasAmt=info.getSoFarHasAmt();
			BigDecimal FSoFarToAmt=info.getSoFarToAmt();
			BigDecimal FDynCostAmt=info.getDynCostAmt();
			BigDecimal FAimCostAmt=info.getAimCostAmt();
			BigDecimal FDiffAmt=info.getDiffAmt();
			BigDecimal FSalableAmt=info.getSalableAmt();
			BigDecimal FConstrAmt=info.getConstrAmt();
			//增加已实现
			BigDecimal FCostPayedAmount = info.getCostPayedAmt();
			//增加已付款，变更拆分的金额等
			BigDecimal FPayedAmount = info.getPayedAmt();
			BigDecimal FUnSettleChangeAmt = info.getUnSettleChangeAmt();
			BigDecimal FSettleChangeAmt = info.getSettleChangeAmt();
			
			final BOSUuid approtionTypeId = info.getApprotionTypeId();
//			String FApprotionTypeId=approtionTypeId==null?ApportionTypeInfo.aimCostType:approtionTypeId.toString();
			String FApprotionTypeId=approtionTypeId==null?null:approtionTypeId.toString();
			BigDecimal FSettAdjustAmt=info.getSettAdjustAmt();
			String FCostAcctLgNumber=info.getCostAcctLgNumber();
			Boolean FIsMonthSave=Boolean.valueOf(info.isIsMonthSave());
			String FPeriodID=info.getPeriod()!=null?info.getPeriod().getId().toString():null;
			Boolean FIsUsed=Boolean.valueOf(info.isIsUsed());
			String FsavedType=info.getSavedType().getValue();
			BigDecimal FRealizedValue=info.getRealizedValue();
			headList.add(Arrays.asList(new Object[]{
					FID, FCreatorID, FCreateTime, FLastUpdateUserID, FLastUpdateTime, FControlUnitID, FProjectId, FCostAccountId, FSnapShotDate, FUnSettSignAmt, FSettSignAmt, FUnContractCostAmt, FSoFarHasAmt, FSoFarToAmt, FDynCostAmt, FAimCostAmt, FDiffAmt, FSalableAmt, FConstrAmt, FApprotionTypeId, FSettAdjustAmt, FCostAcctLgNumber, FIsMonthSave, FPeriodID, FIsUsed,FsavedType,FRealizedValue,FCostPayedAmount,FPayedAmount,FUnSettleChangeAmt,FSettleChangeAmt 
			}));
			
			for(Iterator iter=info.getSettEntries().iterator();iter.hasNext();){
				DynCostSnapShotSettEntryInfo entry=(DynCostSnapShotSettEntryInfo)iter.next();
				String FEntryId=entry.getId().toString();
				String FChangeTypeId=entry.getChangeTypeId().toString();
				BigDecimal FUnSettleAmt=entry.getUnSettleAmt();
				BigDecimal FSettleAmt=entry.getSettleAmt();
				String FParentID=entry.getParent().getId().toString();
				//所有值都0就不保存了
				if(FDCHelper.toBigDecimal(FUnSettleAmt).signum()==0&&FDCHelper.toBigDecimal(FSettleAmt).signum()==0){
					continue;
				}
				settleList.add(Arrays.asList(new Object[]{
						FEntryId, FChangeTypeId, FUnSettleAmt, FSettleAmt, FParentID
				}));
				
			}
			for(Iterator iter=info.getProTypEntries().iterator();iter.hasNext();){
				DynCostSnapShotProTypEntryInfo entry=(DynCostSnapShotProTypEntryInfo)iter.next();
				String FEntryId=entry.getId().toString();
				String FParentID=entry.getParent().getId().toString();
				String FProductTypeId=entry.getProductTypeId().toString();
				BigDecimal FSalableUnitAmt=entry.getAimSaleUnitAmt();
				BigDecimal FProductAimCostAmt=entry.getAimCostAmt();
				BigDecimal FHasHappenAmt=entry.getHasHappenAmt();
				BigDecimal FNotHappenAmt=entry.getNotHappenAmt();
				BigDecimal FDynSaleUnitAmt=entry.getDynSaleUnitAmt();
				BigDecimal FProductDynCostAmt=entry.getDynCostAmt();
				BigDecimal FCostPayedAmt=entry.getCostPayedAmt();
				BigDecimal FAimAimCostAmt = null;
				BigDecimal FAimAimSaleUnitAmt = null;
				if(CostMonthlySaveTypeEnum.ONLYONE.equals(info.getSavedType())){
					FAimAimCostAmt=entry.getAimAimCostAmt();
					FAimAimSaleUnitAmt=entry.getAimAimSaleUnitAmt();
				}
				productEntryList.add(Arrays.asList(new Object[]{
						FEntryId, FParentID, FProductTypeId, FSalableUnitAmt, FProductAimCostAmt, FHasHappenAmt, FNotHappenAmt, FDynSaleUnitAmt, FProductDynCostAmt, FCostPayedAmt, FAimAimCostAmt, FAimAimSaleUnitAmt	
				}));
			}
		}
		
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.executeBatch(headSql, headList);
		builder.executeBatch(settleSql, settleList);
		builder.executeBatch(productEntrySql, productEntryList);
	}

	private static void submitFinanceDynCostSnapShot(Context ctx,DynCostSnapShotCollection dynCostSnapShotCollection) throws BOSException {
		String headSql="update T_AIM_DynCostSnapShot set FSavedType=?,FUnContractCostAmt=?,FSoFarHasAmt=?, FSoFarToAmt=? ,FRealizedValue=? ,FCostPayedAmt=?,fpayedAmt=? where FCostAccountId=? and FPeriodID=?";
		String productEntrySql="update T_AIM_DynCostSnpShtProTypEntry set FCostPayedAmt=?,FHasHappenAmt=?, FNotHappenAmt=? where FProductTypeId=? and fparentid in (select fid from T_AIM_DynCostSnapShot where  FCostAccountId=? and FPeriodID=?);";
		List headList=new ArrayList();
		List productEntryList=new ArrayList();
		
		for(int i=0;i<dynCostSnapShotCollection.size();i++){
			DynCostSnapShotInfo info=dynCostSnapShotCollection.get(i);
			BigDecimal FUnContractCostAmt=info.getUnContractCostAmt();
			String FsavedType=CostMonthlySaveTypeEnum.FINANCEMONTHLY_VALUE;
			BigDecimal FSoFarHasAmt=info.getSoFarHasAmt();
			BigDecimal FSoFarToAmt=info.getSoFarToAmt();
			BigDecimal FRealizedValue=info.getRealizedValue();
			BigDecimal FCostPayedAmt=info.getCostPayedAmt();
			BigDecimal FPayedAmt=info.getPayedAmt();
			String FPeriodID=info.getPeriod()!=null?info.getPeriod().getId().toString():null;
			String FCostAccountId=info.getCostAccountId().toString();
			
			headList.add(Arrays.asList(new Object[]{
					FsavedType,FUnContractCostAmt,FSoFarHasAmt,FSoFarToAmt,FRealizedValue,FCostPayedAmt,FPayedAmt,FCostAccountId,FPeriodID
			}));
			for(Iterator iter=info.getProTypEntries().iterator();iter.hasNext();){
				DynCostSnapShotProTypEntryInfo entry=(DynCostSnapShotProTypEntryInfo)iter.next();
				String FProductTypeId=entry.getProductTypeId().toString();
				FCostPayedAmt=entry.getCostPayedAmt();
				BigDecimal FHasHappenAmt=entry.getHasHappenAmt();
				BigDecimal FNotHappenAmt=entry.getNotHappenAmt();
				productEntryList.add(Arrays.asList(new Object[]{
						FCostPayedAmt,FHasHappenAmt,FNotHappenAmt,FProductTypeId,FCostAccountId,FPeriodID
				}));
			}
		}
		
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.executeBatch(headSql, headList);
		builder.executeBatch(productEntrySql, productEntryList);
	}
	
    public static PeriodInfo getCurrentPeriod(Context ctx, String projectId,CostMonthlySaveTypeEnum savedType) throws BOSException, EASBizException {
    	PeriodInfo periodInfo=null;
    	if(savedType==CostMonthlySaveTypeEnum.COSTMONTHLY){
    		periodInfo=FDCUtils.getCurrentPeriod(ctx, projectId, true);
    	}
    	if(savedType==CostMonthlySaveTypeEnum.FINANCEMONTHLY){
    		periodInfo=FDCUtils.getCurrentPeriod(ctx, projectId, false);
    	}
//    	periodInfo = PeriodUtils.getPrePeriodInfo(ctx,periodInfo);
    	return periodInfo;
    }
	
	public static void autoSaveSnapShot(Context ctx) throws BOSException {
		//get all can save prj
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select fid,flongnumber from T_FDC_CurProject where fisleaf=1 and FIsEnabled=1 and FProjectStatusID not in (?,?)");
		builder.addParam(ProjectStatusInfo.flowID);
		builder.addParam(ProjectStatusInfo.closeID);
		try {
			IRowSet rowSet=builder.executeQuery();
			if(rowSet==null||rowSet.size()==0){
				return ;
			}
			int total=rowSet.size();
			int index=0;
			while(rowSet.next()){
				index++;
				String prjId=rowSet.getString("fid");
				String flongnumber=rowSet.getString("flongnumber");
				logger.info("project \""+flongnumber+"\" start auto save snap shot ("+index+"/"+total+")");
				try{
					saveSnapShot(ctx, prjId, CostMonthlySaveTypeEnum.AUTOSAVE);
				}catch(Exception e){
					logger.error("saveSnapShot err:" +e.getMessage(), e);
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BOSException(e);
		}
	}
	
	
	public static void deleteDynSnapShot(Context ctx, String prjId, IObjectValue period) throws BOSException, EASBizException {
		if(prjId==null){
			return;
		}
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("projectId", prjId));
		filter.appendFilterItem("period.id", period.getString("id"));
		DynCostSnapShotFactory.getLocalInstance(ctx).delete(filter);
	}

	public static void reverseCostMonthSettled(Context ctx, String prjId, IObjectValue period) throws BOSException, EASBizException {
		if(prjId==null||period==null){
			return;
		}
		deleteDynSnapShot(ctx,prjId,period);
	}

	public static  void reverseFinanceMonthSettled(Context ctx, String prjId, IObjectValue period) throws BOSException, EASBizException {
		if(prjId==null||period==null){
			return;
		}
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("update T_AIM_DynCostSnapShot set FSavedType=? where FprojectId=? and FPeriodID=?");
		builder.addParam(CostMonthlySaveTypeEnum.COSTMONTHLY_VALUE);
		builder.addParam(prjId);
		builder.addParam(period.getString("id"));
		builder.execute();
	}
}
