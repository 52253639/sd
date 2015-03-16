package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.aimcost.FDCCostLogChangeSettEntryCollection;
import com.kingdee.eas.fdc.aimcost.FDCCostLogChangeSettEntryInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostLogCollection;
import com.kingdee.eas.fdc.aimcost.FDCCostLogInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostLogProductEntryCollection;
import com.kingdee.eas.fdc.aimcost.FDCCostLogProductEntryInfo;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCSplitBillInfo;
import com.kingdee.eas.fdc.contract.client.SettlementCostSplitListUI;

public abstract class SplitLogStrategy {
	protected final static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.SplitLogStrategy");
	protected FDCSQLBuilder builder=null;
	abstract boolean appendLog(LogMap logMap);
	abstract LogMap getLogData(FDCSplitBillInfo splitInfo);
	
	public boolean invoke(Context ctx,FDCSplitBillInfo splitInfo){
		if(builder==null){
			builder=new FDCSQLBuilder(ctx);
		}
		return appendLog(getLogData(splitInfo));
	}
	
	
	public static class LogMap{
		private Map map=new  HashMap();
		public void put(String key,Object value){
			if(key==null){
				return;
			}
			map.put(key, value);
		}
		public Object get(String key){
			return map.get(key);
		}
		public void setFDCCostLogs(FDCCostLogCollection logs){
			put("FDCCostLogCollection",logs);
		}
		public FDCCostLogCollection getFDCCostLogs(){
			return (FDCCostLogCollection)get("FDCCostLogCollection");
		}
		
		public void setFDCCostLogProducts(FDCCostLogProductEntryCollection productEntrys){
			put("FDCCostLogProductEntryCollection", productEntrys);
		}
		public FDCCostLogProductEntryCollection getFDCCostLogProducts(){
			return (FDCCostLogProductEntryCollection)get("FDCCostLogProductEntryCollection");
		}
		
		public void setFDCCostLogChanges(FDCCostLogChangeSettEntryCollection changeEntrys){
			put("FDCCostLogChangeSettEntryCollection", changeEntrys);
		}
		public FDCCostLogChangeSettEntryCollection getFDCCostLogChanges(){
			return (FDCCostLogChangeSettEntryCollection)get("FDCCostLogChangeSettEntryCollection");
		}
	}
	
	public boolean isProductSplitParent(FDCSplitBillEntryInfo entry){
		if(!entry.isIsLeaf()&&entry.getSplitType()!=null&&entry.getSplitType()==CostSplitTypeEnum.PRODSPLIT){
			return true;
		}
		return false;
	}
	public boolean hasDirectSplitAmt(FDCSplitBillEntryInfo entry){
		if(entry.isIsLeaf()&&entry.getProduct()!=null){
			return true;
		}
		return false;
	}
	
	boolean appendHappenLog(LogMap logMap) throws BOSException {
		String sql="insert into T_AIM_FDCCostLog " +
				"(fid,fprojectid,fcostaccountid,funsettconamt,fsettconamt,funsettsignamt,fsettsignamt,fhappendamt) "
				+"values(?,?,?,?,?,?,?,?)";
		String productSql="insert into T_AIM_FDCCostLogProdEntry (fid,fparentid,fproducttypeid,fdirecthappendamt) values(?,?,?,?)";
		String changeSql="insert into T_AIM_FDCCostLogChangeEntry (fid,fparentid,fchangetypeid,funsettleamt,fsettleamt) values(?,?,?,?,?)";
		if(logMap==null){
			return true;
		}
		FDCCostLogCollection costLogs = logMap.getFDCCostLogs();
		
		if(costLogs!=null&&costLogs.size()>0){
			List logList=new ArrayList();
			for(int i=0;i<costLogs.size();i++){
				FDCCostLogInfo info=costLogs.get(i);
				String fid=info.getId().toString();
				String fprojectid=info.getProjectId().toString();
				String fcostaccountid=info.getCostAccountId().toString();
				BigDecimal funsettconamt=info.getUnSettConAmt();
				BigDecimal fsettconamt=info.getSettConAmt();
				BigDecimal funsettsignamt=info.getUnSettSignAmt();
				BigDecimal fsettsignamt=info.getSettSignAmt();
				BigDecimal fhappendamt=info.getHappendAmt();
				logList.add(Arrays.asList(new Object[]{
						fid,fprojectid,fcostaccountid,funsettconamt,fsettconamt,funsettsignamt,fsettsignamt,fhappendamt
				}));
			}
			builder.executeBatch(sql, logList);
		}
		FDCCostLogProductEntryCollection costLogProducts = logMap.getFDCCostLogProducts();
		if(costLogProducts!=null&&costLogProducts.size()>0){
			List logProductList=new ArrayList();
			for(int i=0;i<costLogProducts.size();i++){
				FDCCostLogProductEntryInfo productEntry = costLogProducts.get(i);
				String fid=productEntry.getId().toString();
				String fparentid=productEntry.getParentID().toString();
				String fproducttypeid=productEntry.getProductTypeId().toString();
				BigDecimal fdirecthappendamt=productEntry.getDirectHappendAmt();
				logProductList.add(Arrays.asList(new Object[]{fid,fparentid,fproducttypeid,fdirecthappendamt}));
			}
			builder.executeBatch(productSql, logProductList);
		}
		
		final FDCCostLogChangeSettEntryCollection costLogChanges = logMap.getFDCCostLogChanges();
		if(costLogChanges!=null&&costLogChanges.size()>0){
			List changeList=new ArrayList();
			for(int i=0;i<costLogChanges.size();i++){
				FDCCostLogChangeSettEntryInfo changeEntry=costLogChanges.get(i);
				String fid=changeEntry.getId().toString();
				String fparentid=changeEntry.getParentID().toString();
				String fchangetypeid=changeEntry.getChangeTypeId().toString();
				BigDecimal funsettleamt=changeEntry.getUnSettleAmt();
				BigDecimal fsettleamt=changeEntry.getSettleAmt();
				changeList.add(Arrays.asList(new Object[]{
						fid,fparentid,fchangetypeid,funsettleamt,fsettleamt
				}));
			}
			builder.executeBatch(changeSql, changeList);
		}
		return true;
	}
	
	boolean appendConWithoutTxtLog(LogMap logMap) throws BOSException {
		String sql="insert into T_AIM_FDCCostLog " +
				"(fid,fprojectid,fcostaccountid,fconwithouttxtcostamt,fhappendamt,fcostpayedamt,fhaspayedamt) "
				+"values(?,?,?,?,?,?,?)";
		String productSql="insert into T_AIM_FDCCostLogProdEntry (fid,fparentid,fproducttypeid,fdirecthappendamt,fdirectcostpayedamt,fdirecthaspayedamt) values(?,?,?,?,?,?)";
		if(logMap==null){
			return true;
		}
		FDCCostLogCollection costLogs = logMap.getFDCCostLogs();
		
		if(costLogs!=null&&costLogs.size()>0){
			List logList=new ArrayList();
			for(int i=0;i<costLogs.size();i++){
				FDCCostLogInfo info=costLogs.get(i);
				String fid=info.getId().toString();
				String fprojectid=info.getProjectId().toString();
				String fcostaccountid=info.getCostAccountId().toString();
				BigDecimal fconwithouttxtcostamt=info.getConWithoutTxtCostAmt();
				BigDecimal fhappendamt=info.getHappendAmt();
				BigDecimal fcostpayedamt=info.getCostPayedAmt();
				BigDecimal fhaspayedamt=info.getHasPayedAmt();
				logList.add(Arrays.asList(new Object[]{
						fid,fprojectid,fcostaccountid,fconwithouttxtcostamt,fhappendamt,fcostpayedamt,fhaspayedamt
				}));
			}
			builder.executeBatch(sql, logList);
		}
		FDCCostLogProductEntryCollection costLogProducts = logMap.getFDCCostLogProducts();
		if(costLogProducts!=null&&costLogProducts.size()>0){
			List logProductList=new ArrayList();
			for(int i=0;i<costLogProducts.size();i++){
				FDCCostLogProductEntryInfo productEntry = costLogProducts.get(i);
				String fid=productEntry.getId().toString();
				String fparentid=productEntry.getParentID().toString();
				String fproducttypeid=productEntry.getProductTypeId().toString();
				BigDecimal fdirecthappendamt=productEntry.getDirectHappendAmt();
				BigDecimal fdirectcostpayedamt=productEntry.getDirectCostPayedAmt();
				BigDecimal fdirecthaspayedamt=productEntry.getDirectHasPayedAmt();
				logProductList.add(Arrays.asList(new Object[]{
						fid,fparentid,fproducttypeid,fdirecthappendamt,fdirectcostpayedamt,fdirecthaspayedamt
				}));
			}
			builder.executeBatch(productSql, logProductList);
		}

		return true;
	}
	
	boolean appendPaymentLog(LogMap logMap) throws BOSException {
		String sql="insert into T_AIM_FDCCostLog " +
				"(fid,fprojectid,fcostaccountid,fcostpayedamt,fhaspayedamt) "
				+"values(?,?,?,?,?)";
		String productSql="insert into T_AIM_FDCCostLogProdEntry (fid,fparentid,fproducttypeid,fdirectcostpayedamt,fdirecthaspayedamt) values(?,?,?,?,?)";
		if(logMap==null){
			return true;
		}
		FDCCostLogCollection costLogs = logMap.getFDCCostLogs();
		
		if(costLogs!=null&&costLogs.size()>0){
			List logList=new ArrayList();
			for(int i=0;i<costLogs.size();i++){
				FDCCostLogInfo info=costLogs.get(i);
				String fid=info.getId().toString();
				String fprojectid=info.getProjectId().toString();
				String fcostaccountid=info.getCostAccountId().toString();
				BigDecimal fcostpayedamt=info.getCostPayedAmt();
				BigDecimal fhaspayedamt=info.getHasPayedAmt();
				logList.add(Arrays.asList(new Object[]{
						fid,fprojectid,fcostaccountid,fcostpayedamt,fhaspayedamt
				}));
			}
			builder.executeBatch(sql, logList);
		}
		FDCCostLogProductEntryCollection costLogProducts = logMap.getFDCCostLogProducts();
		if(costLogProducts!=null&&costLogProducts.size()>0){
			List logProductList=new ArrayList();
			for(int i=0;i<costLogProducts.size();i++){
				FDCCostLogProductEntryInfo productEntry = costLogProducts.get(i);
				String fid=productEntry.getId().toString();
				String fparentid=productEntry.getParentID().toString();
				String fproducttypeid=productEntry.getProductTypeId().toString();
				BigDecimal fdirectcostpayedamt=productEntry.getDirectCostPayedAmt();
				BigDecimal fdirecthaspayedamt=productEntry.getDirectHasPayedAmt();
				logProductList.add(Arrays.asList(new Object[]{
						fid,fparentid,fproducttypeid,fdirectcostpayedamt,fdirecthaspayedamt
				}));
			}
			builder.executeBatch(productSql, logProductList);
		}

		return true;
	}
	
	public static boolean appendSplitLog(Context ctx,Map map){
		if(map==null){
			return true;
		}
		String type=(String)map.get("type");
		SplitLogStrategy oldStrategy=null;
		SplitLogStrategy newStrategy=null;
		if(type!=null){
			if(type.equals(SplitType.ContractSplit)){
				oldStrategy=ConSplitLogStrategy.getOldInstance();
				newStrategy=ConSplitLogStrategy.getNewInstance();
			}
			if(type.equals(SplitType.ChangeSplit)){
				oldStrategy=ChangeSplitLogStrategy.getOldInstance();
				newStrategy=ChangeSplitLogStrategy.getNewInstance();
			}
			if(type.equals(SplitType.SettlementSplit)){
				oldStrategy=SettlementSplitLogStrategy.getOldInstance();
				newStrategy=SettlementSplitLogStrategy.getNewInstance();
			}
			if(type.equals(SplitType.ConWithoutTxtSplit)){
				oldStrategy=ConWithoutTextSplitLogStrategy.getOldInstance();
				newStrategy=ConWithoutTextSplitLogStrategy.getNewInstance();
			}
			if(type.equals(SplitType.PaymentSplit)){
				oldStrategy=PaymentSplitLogStrategy.getOldInstance();
				newStrategy=PaymentSplitLogStrategy.getNewInstance();
			}
			FDCSplitBillInfo oldSplit=(FDCSplitBillInfo)map.get("oldSplit");
			FDCSplitBillInfo newSplit=(FDCSplitBillInfo)map.get("newSplit");
			if(oldStrategy!=null){
				oldStrategy.invoke(ctx, oldSplit);
			}
			if(newStrategy!=null){
				newStrategy.invoke(ctx, newSplit);
			}
		}
		return true;
	}
	
	public static class  SplitType{
		public static final String ContractSplit="ContractSplit";
		public static final String ChangeSplit="ChangeSplit";
		public static final String SettlementSplit="SettlementSplit";
		public static final String ConWithoutTxtSplit="ConWithoutTxtSplit";
		public static final String PaymentSplit="PaymentSplit";
	}
}


