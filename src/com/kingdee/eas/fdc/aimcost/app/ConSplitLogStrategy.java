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

public abstract class ConSplitLogStrategy extends SplitLogStrategy {
	boolean appendLog(LogMap logMap) {
		try {
			return appendHappenLog(logMap);
		} catch (BOSException e) {
			logger.error("conSplitLogStrategy", e);
			return false;
		}
	}
	
	public static  ConSplitLogStrategy getOldInstance(){
		return getInstance(true);
	}
	public static ConSplitLogStrategy getNewInstance(){
		return getInstance(false);
	}
	
	private static ConSplitLogStrategy getInstance(boolean isNegate){
		final boolean isNega=isNegate;
		return new ConSplitLogStrategy(){
			LogMap getLogData(FDCSplitBillInfo info) {
				FDCCostLogCollection logs=new FDCCostLogCollection();
				FDCCostLogProductEntryCollection productEntrys=new  FDCCostLogProductEntryCollection();
				ContractCostSplitInfo oldInfo=(ContractCostSplitInfo)info;
				boolean isSettle=oldInfo.getContractBill().isHasSettled();
				for(Iterator iter=oldInfo.getEntrys().iterator();iter.hasNext();){
					FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)iter.next();
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
						if(isSettle){
							logInfo.setSettConAmt(amount);
						}else{
							logInfo.setUnSettConAmt(amount);
							logInfo.setUnSettSignAmt(amount);
							logInfo.setHappendAmt(amount);
						}
						if(hasDirectSplitAmt(entry)&&!isSettle){
							FDCCostLogProductEntryInfo logEntryInfo=new FDCCostLogProductEntryInfo();
							logEntryInfo.setId(BOSUuid.create(logEntryInfo.getBOSType()));
							logEntryInfo.setProductTypeId(entry.getProduct().getId());
							logEntryInfo.setDirectHappendAmt(amount);
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
