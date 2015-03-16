package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.util.Iterator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.aimcost.FDCCostLogChangeSettEntryCollection;
import com.kingdee.eas.fdc.aimcost.FDCCostLogChangeSettEntryInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostLogCollection;
import com.kingdee.eas.fdc.aimcost.FDCCostLogInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostLogProductEntryCollection;
import com.kingdee.eas.fdc.aimcost.FDCCostLogProductEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCSplitBillInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;

public abstract class ChangeSplitLogStrategy extends SplitLogStrategy {
	boolean appendLog(LogMap logMap) {
		try {
			return appendHappenLog(logMap);
		} catch (BOSException e) {
			logger.error("conSplitLogStrategy", e);
			return false;
		}
	}
	
	public static ChangeSplitLogStrategy getOldInstance(){
		return getInstance(true);
	}
	public static ChangeSplitLogStrategy getNewInstance(){
		return getInstance(false);
	}
	
	public static ChangeSplitLogStrategy getInstance(boolean isNegate){
		final boolean isNega=isNegate;
		return new ChangeSplitLogStrategy(){
			LogMap getLogData(FDCSplitBillInfo info) {
				FDCCostLogCollection logs=new FDCCostLogCollection();
				FDCCostLogProductEntryCollection productEntrys=new  FDCCostLogProductEntryCollection();
				FDCCostLogChangeSettEntryCollection changeEntrys=new FDCCostLogChangeSettEntryCollection();
				ConChangeSplitInfo oldInfo=(ConChangeSplitInfo)info;
				boolean isSettle=oldInfo.getContractChange().getContractBill().isHasSettled();
				BOSUuid changeTypeId=oldInfo.getContractChange().getChangeType().getId();
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
						
						FDCCostLogChangeSettEntryInfo changeEntry=new FDCCostLogChangeSettEntryInfo();
						changeEntry.setId(BOSUuid.create(changeEntry.getBOSType()));
						changeEntry.setParentID(logInfo.getId());
						changeEntry.setChangeTypeId(changeTypeId);
						if(isSettle){
							changeEntry.setSettleAmt(amount);
						}else{
							logInfo.setUnSettSignAmt(amount);
							logInfo.setHappendAmt(amount);
							changeEntry.setUnSettleAmt(amount);
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
						changeEntrys.add(changeEntry);
					}
				}
				LogMap logMap=new LogMap();
				logMap.setFDCCostLogs(logs);
				logMap.setFDCCostLogProducts(productEntrys);
				logMap.setFDCCostLogChanges(changeEntrys);
				return logMap;
			}
		};
	}
}
