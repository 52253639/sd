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
import com.kingdee.eas.fdc.contract.SettlementCostSplitInfo;

public abstract class SettlementSplitLogStrategy extends SplitLogStrategy {
	boolean appendLog(LogMap logMap) {
		try {
			return appendHappenLog(logMap);
		} catch (BOSException e) {
			logger.error("SettlementSplitLogStrategy", e);
			return false;
		}
	}
	public static  SettlementSplitLogStrategy getOldInstance(){
		return getInstance(true);
	}
	public static SettlementSplitLogStrategy getNewInstance(){
		return getInstance(false);
	}
	public static SettlementSplitLogStrategy getInstance(boolean isNegate){
		final boolean isNega=isNegate;
		return new SettlementSplitLogStrategy(){
			LogMap getLogData(FDCSplitBillInfo info) {
				FDCCostLogCollection logs=new FDCCostLogCollection();
				FDCCostLogProductEntryCollection productEntrys=new  FDCCostLogProductEntryCollection();
				SettlementCostSplitInfo settleInfo=(SettlementCostSplitInfo)info;
				for(Iterator iter=settleInfo.getEntrys().iterator();iter.hasNext();){
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
						logInfo.setSettSignAmt(amount);
						logInfo.setHappendAmt(amount);
						if(hasDirectSplitAmt(entry)){
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
