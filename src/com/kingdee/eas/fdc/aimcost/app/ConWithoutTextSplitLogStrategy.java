package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.util.Iterator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.aimcost.FDCCostLogCollection;
import com.kingdee.eas.fdc.aimcost.FDCCostLogInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostLogProductEntryCollection;
import com.kingdee.eas.fdc.aimcost.FDCCostLogProductEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCSplitBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;

public abstract class ConWithoutTextSplitLogStrategy extends SplitLogStrategy {
	boolean appendLog(LogMap logMap) {
		try {
			return appendHappenLog(logMap);
		} catch (BOSException e) {
			logger.error("conSplitLogStrategy", e);
			return false;
		}
	}
	
	public static  ConWithoutTextSplitLogStrategy getOldInstance(){
		return getInstance(true);
	}
	public static ConWithoutTextSplitLogStrategy getNewInstance(){
		return getInstance(false);
	}
	
	public static ConWithoutTextSplitLogStrategy getInstance(boolean isNegate){
		final boolean isNega=isNegate;
		return new ConWithoutTextSplitLogStrategy(){
			LogMap getLogData(FDCSplitBillInfo info) {
				FDCCostLogCollection logs=new FDCCostLogCollection();
				FDCCostLogProductEntryCollection productEntrys=new  FDCCostLogProductEntryCollection();
				PaymentSplitInfo paymentSplitInfo=(PaymentSplitInfo)info;
				for(Iterator iter=paymentSplitInfo.getEntrys().iterator();iter.hasNext();){
					PaymentSplitEntryInfo entry=(PaymentSplitEntryInfo)iter.next();
					if(entry.isIsLeaf()||isProductSplitParent(entry)){
						BigDecimal amount = entry.getAmount();
						if(FDCHelper.toBigDecimal(amount).signum()==0){
							continue;
						}
						if(isNega){
							amount=amount.negate();
						}
						FDCCostLogInfo logInfo=new FDCCostLogInfo();
						logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
						logInfo.setCostAccountId(entry.getCostAccount().getId());
						logInfo.setProjectId(entry.getCostAccount().getCurProject().getId());
						logInfo.setConWithoutTxtCostAmt(amount);
						logInfo.setHappendAmt(amount);
						logInfo.setCostPayedAmt(amount);//已实现
						logInfo.setHasPayedAmt(entry.getPayedAmt());//已付款
						if(hasDirectSplitAmt(entry)){
							FDCCostLogProductEntryInfo logEntryInfo=new FDCCostLogProductEntryInfo();
							logEntryInfo.setId(BOSUuid.create(logEntryInfo.getBOSType()));
							logEntryInfo.setProductTypeId(entry.getProduct().getId());
							logEntryInfo.setDirectHappendAmt(amount);
							logEntryInfo.setDirectCostPayedAmt(amount);
							logEntryInfo.setHasPayedAmt(entry.getPayedAmt());
							logEntryInfo.setParentID(logInfo.getId());
							productEntrys.add(logEntryInfo);
						}
						logs.add(logInfo);
					}
				}
				LogMap logMap=new LogMap();
				logMap.setFDCCostLogs(logs);
				logMap.setFDCCostLogProducts(productEntrys);
				return logMap;
			}
		};
	}
}
