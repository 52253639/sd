package com.kingdee.eas.fdc.finance.app.voucher;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.finance.FDCAdjustBillCollection;
import com.kingdee.eas.fdc.finance.FDCAdjustBillEntryInfo;

public abstract class AbstractAdjustCreator implements IAdjustCreator {

	public void create() {

	}
	protected FDCAdjustBillCollection createAdjustBill(){
		return null;
	}
	
	abstract AdjustBillEntryMap createPreAdjustbillEntry();
	
	abstract AdjustBillEntryMap createAftAdjustbillEntry();
	
	abstract void save();
	
	abstract void generateVoucher();

	
	static class AdjustBillEntryMap{
		private Map innerMap=new HashMap();
		public FDCAdjustBillEntryInfo get(String contractId,String prjId,String costAccountId){
			String key=prjId+contractId+costAccountId;
			return (FDCAdjustBillEntryInfo)innerMap.get(key);
		}
		
		public FDCAdjustBillEntryInfo getAndAdd(String prjId,String contractId,String costAccountId){
			FDCAdjustBillEntryInfo entry = get(prjId, contractId, costAccountId);
			if(entry==null){
				entry=getNewAdjustBillEntryInfo();
				CurProjectInfo prj=new CurProjectInfo();
				prj.setId(BOSUuid.read(prjId));
				entry.setCurProject(prj);
				CostAccountInfo costAcct=new CostAccountInfo();
				costAcct.setId(BOSUuid.read(costAccountId));
				entry.setCostAccount(costAcct);
				String key=prjId+contractId+costAccountId;
				innerMap.put(key,entry);
			}
			return entry;
		}
		
		
		/**
		 * @return
		 */
		private FDCAdjustBillEntryInfo getNewAdjustBillEntryInfo() {
			FDCAdjustBillEntryInfo info;
			info = new FDCAdjustBillEntryInfo();
			info.setId(BOSUuid.create(info.getBOSType()));
			info.setCostDifAmt(FDCHelper.ZERO);
			info.setCostAmt(FDCHelper.ZERO);
			info.setHappenCostAmt(FDCHelper.ZERO);
			info.setPreHappenCostAmt(FDCHelper.ZERO);
			info.setPayedDifAmt(FDCHelper.ZERO);
			info.setPayedAmt(FDCHelper.ZERO);
			info.setPrePayedAmt(FDCHelper.ZERO);
			info.setContractSplitAmt(FDCHelper.ZERO);
			info.setChangeSplitAmt(FDCHelper.ZERO);
			info.setGrtSplitAmt(FDCHelper.ZERO);
			info.setPreGrtSplitAmt(FDCHelper.ZERO);
			info.setSettleSplitAmt(FDCHelper.ZERO);
			info.setPreQualityAmt(FDCHelper.ZERO);
			info.setQualityAmt(FDCHelper.ZERO);
			info.setQualityDifAmt(FDCHelper.ZERO);
			return info;
		}
	}
}
