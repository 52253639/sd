package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.jdbc.rowset.IRowSet;

public class HappenDataGetter extends CostDataGetter implements Serializable{
	public Map hasHappenMap;

	public Map conSplitMap;

	public Map changeSplitMap;

	public Map settleSplitMap;
	public Map partSettleSplitMap;

	public Map noTextSplitMap;

	public Map paySplitMap;//已完工工程量
	
	public Map paidSplitMap;//已付款


	protected Context ctx = null;
	protected String cuId=null;
	public void clear(){
		clearMap(this.hasHappenMap);
		clearMap(this.conSplitMap);
		clearMap(this.changeSplitMap);
		clearMap(this.settleSplitMap);
		clearMap(this.noTextSplitMap);
		clearMap(this.paySplitMap);
		clearMap(this.paidSplitMap);
	}
	/**
	 * 仅供子类使用,不能用此构造函数实例化
	 */
	protected HappenDataGetter(){}
	
	/**
	 * 不执行查询操作,以供外部程序调用get方法
	 * @param ctx
	 */
	public HappenDataGetter(Context ctx){
		this.ctx=ctx;
	}
	public HappenDataGetter(String objectId,boolean isDivideChangeType,
			boolean isIncludeSettled,boolean isIncludeProduct) {
		try {
			init(objectId);
			this.initHasHappenData(objectId, isDivideChangeType,
					isIncludeSettled,isIncludeProduct);
		} catch (Exception e) {
//			ExceptionHandler.handle(e);
			e.printStackTrace();
		}
	}

	public HappenDataGetter(Context ctx, String objectId,
			boolean isDivideChangeType, boolean isIncludeSettled,boolean isIncludeProduct) throws BOSException {
		try {
			this.ctx = ctx;
			init(objectId);
			this.initHasHappenData(objectId, isDivideChangeType,
					isIncludeSettled,isIncludeProduct);
		} catch (Exception e) {
//			ExceptionHandler.handle(e);
			//修改异常处理
			e.printStackTrace();
			if(ctx!=null && e instanceof SQLException){
				throw new BOSException(e);
			}
			else if(ctx==null  && e instanceof SQLException){
//				ExceptionHandler.handle(e);
			}
		}
	}

	protected void init(String objectId) throws Exception{
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		if(isCurProject(objectId)){
			builder.appendSql("select fcontrolUnitId from T_FDC_CurProject where fid=?");
			
		}else{
			builder.appendSql("select fcontrolUnitId from T_ORG_BaseUnit where fid=?");
		}
		builder.addParam(objectId);
		IRowSet rowSet=builder.executeQuery();
		if(rowSet.size()>0){
			rowSet.next();
			this.cuId=rowSet.getString("fcontrolUnitId");
		}
	}
	
	protected void initHasHappenData(String objectId, boolean isDivideChangeType,
			boolean isIncludeSettled,boolean isIncludeProduct) throws BOSException, SQLException {
		hasHappenMap = new HashMap();
		conSplitMap = getContractSplitData(objectId, isIncludeSettled,isIncludeProduct);
		changeSplitMap = getChangeSplitData(objectId, isDivideChangeType,
				isIncludeSettled,isIncludeProduct);
		settleSplitMap = getSettleSplitData(objectId,isIncludeProduct);
		noTextSplitMap = getNoTextSplitData(objectId,isIncludeProduct);
		paySplitMap = getPaySplitData(objectId,isIncludeProduct);
		paidSplitMap = getPaidSplitData(objectId,isIncludeProduct);
		if(isUsePartSettle()){
			this.partSettleSplitMap=getPartSettleSplitData(objectId);
		}
		List acctIds = new ArrayList();
		acctIds.addAll(conSplitMap.keySet());
		acctIds.addAll(changeSplitMap.keySet());
		acctIds.addAll(settleSplitMap.keySet());
		acctIds.addAll(noTextSplitMap.keySet());
		for (int i = 0; i < acctIds.size(); i++) {
			HappenDataInfo info = new HappenDataInfo();
			String acctId = (String) acctIds.get(i);
			info.add((HappenDataInfo) conSplitMap.get(acctId));
			info.add((HappenDataInfo) changeSplitMap.get(acctId));
			info.add((HappenDataInfo) settleSplitMap.get(acctId));
			info.add((HappenDataInfo) noTextSplitMap.get(acctId));
			this.hasHappenMap.put(acctId, info);
		}
	}
	
	/**
	 * 取科目的已发生成本
	 * @param acctId
	 * @return
	 */
	public HappenDataInfo getHappenInfo(String acctId) {
		HappenDataInfo happen = (HappenDataInfo) this.hasHappenMap.get(acctId);
		return happen;
	}

	public HappenDataInfo getHasPayInfo(String acctId) {
		HappenDataInfo hasPay = (HappenDataInfo) this.paySplitMap.get(acctId);
		return hasPay;
	}
	public HappenDataInfo getPartSettleInfo(String acctId) {
		if(this.partSettleSplitMap==null){
			return null;
		}
		HappenDataInfo partSettle = (HappenDataInfo) this.partSettleSplitMap.get(acctId);
		return partSettle;
	}
	public Map getContractSplitData(String objectId, boolean isIncludeSettled,boolean isIncludeProduct)
			throws BOSException, SQLException {
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		Map conSplitMap = new HashMap();
		String select = "select FCostAccountId,con.FHasSettled,sum(entry.FAmount) amount ";
		String from = "from T_AIM_CostSplitEntry entry "
				+ "inner join T_AIM_CostSplit head on entry.FParentID=head.FId "
				+ "inner join T_CON_ContractBill con on head.FCostBillID=con.FId ";
		String where = "where FCostBillType='CONTRACTSPLIT' and  head.FControlunitId=? And head.FIsInvalid=0 And FIsProduct=0 and  entry.fobjectId=? ";
		if (!isIncludeSettled) {
			where += " and (con.FHasSettled=0 or con.FHasSettled is null) ";
		}
		String group = "group by FCostAccountId,con.FHasSettled ";
		builder.appendSql(select);
		builder.appendSql(from);
		builder.appendSql(where);
		builder.appendSql(group);
		builder.addParam(this.cuId);
		builder.addParam(objectId);
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {
			String acctId = rs.getString("FCostAccountId");
			if (acctId == null) {
				continue;
			}
			BigDecimal amount = rs.getBigDecimal("amount");
			if (amount == null) {
				continue;
			}
			HappenDataInfo info = new HappenDataInfo();
			info.setAcctId(acctId);
			info.setBillType(CostSplitBillTypeEnum.CONTRACTSPLIT);
			info.setAmount(amount);
			if (isIncludeSettled) {
				info.setUserObjectId("has");
				info.setSettled(rs.getBoolean("FHasSettled"));
			}
			conSplitMap.put(info.getKey(), info);
			if(isIncludeProduct&&!info.isSettled()){
				conSplitMap.put(acctId, info);
			}
		}
		if (isIncludeProduct) {
			String selectPro = "select FCostAccountId,FProductId,sum(entry.FProdAmount) proAmount ";
			String wherePro = "where FCostBillType='CONTRACTSPLIT' and head.FControlunitId=? And head.FIsInvalid=0 And  FIsProduct=1 and  entry.fobjectId=? ";
			wherePro += "and (con.FHasSettled=0 or con.FHasSettled is null) ";
			String groupPro = "group by FCostAccountId,FProductId ";
			builder.clear();
			builder.appendSql(selectPro + from + wherePro + groupPro);
			builder.addParam(this.cuId);
			builder.addParam(objectId);
			IRowSet rsPro = builder.executeQuery();
			while (rsPro.next()) {
				String acctId = rsPro.getString("FCostAccountId");
				if (acctId == null) {
					continue;
				}
				String proId = rsPro.getString("FProductId");
				if (proId == null) {
					continue;
				}
				BigDecimal amount = rsPro.getBigDecimal("proAmount");
				if (amount == null) {
					continue;
				}
				if (conSplitMap.containsKey(acctId)||conSplitMap.containsKey(acctId+0)) {
					HappenDataInfo info = (HappenDataInfo) conSplitMap
							.get(acctId);
					if(info==null){
						info = (HappenDataInfo) conSplitMap
						.get(acctId+0);
					}
					
					info.addProductAmount(proId, amount);
					
				}
			}
		}
		return conSplitMap;
	}

	public Map getChangeSplitData(String objectId, boolean isDivideChangeType,
			boolean isIncludeSettled,boolean isIncludeProduct) throws BOSException, SQLException {
		FDCSQLBuilder builder=new  FDCSQLBuilder(ctx);
		Map changeSplitMap = new HashMap();
		String select =null;
		if (isDivideChangeType) {
			select = "select FCostAccountId,FChangeTypeId,con.FHasSettled,sum(entry.FAmount) amount ";
		} else {
			select = "select FCostAccountId,con.FHasSettled,sum(entry.FAmount) amount ";
		}
		String from = "from T_AIM_CostSplitEntry entry "
				+ "inner join T_AIM_CostSplit head on entry.FParentId=head.FId "
				+ "inner join T_CON_ContractChangeBill change on head.FCostBillID=change.FID "
				+ "inner join T_CON_ContractBill con on change.FContractBillID=con.FID  ";
		String where = "where FCostBillType='CNTRCHANGESPLIT' and head.fcontrolunitid=? and head.FIsInvalid=0 And  entry.fobjectid=? ";

		if (!isIncludeSettled) {
			where += "and (con.FHasSettled=0 or con.FHasSettled is null) ";
		}
		String group = null;
		if (isDivideChangeType) {
			group = "group by FCostAccountId,FChangeTypeId,con.FHasSettled ";
		} else {
			group = "group by FCostAccountId,con.FHasSettled ";
		}
		builder.appendSql(select);
		builder.appendSql(from);
		builder.appendSql(where);
		builder.appendSql(group);
		builder.addParam(this.cuId);
		builder.addParam(objectId);
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {
			String acctId = rs.getString("FCostAccountId");
			if (acctId == null) {
				continue;
			}
			BigDecimal amount = rs.getBigDecimal("amount");
			if (amount == null) {
				continue;
			}
			HappenDataInfo info = new HappenDataInfo();
			info.setAcctId(acctId);
			info.setBillType(CostSplitBillTypeEnum.CNTRCHANGESPLIT);
			if (isDivideChangeType) {
				String changeTypeId = rs.getString("FChangeTypeId");
				info.setUserObjectId(changeTypeId);
			}
			info.setAmount(amount);
			info.setSettled(rs.getBoolean("FHasSettled"));
			changeSplitMap.put(info.getKey(), info);
			if(isDivideChangeType&&!info.isSettled()){
				//应该做金额的累加,因为会有不能的变更类型
				HappenDataInfo oldInfo=(HappenDataInfo)changeSplitMap.get(acctId);
				if(oldInfo!=null){
					HappenDataInfo newInfo=new HappenDataInfo();
					newInfo.setUserObjectId(oldInfo.getUserObjectId());
					newInfo.setSettled(oldInfo.isSettled);
					newInfo.setAcctId(oldInfo.getAcctId());
					newInfo.setBillType(oldInfo.getBillType());
					newInfo.setAmount(oldInfo.getAmount());
					newInfo.setProductAmountMap(oldInfo.getProductAmountMap());
					newInfo.addAmount(info.getAmount());
					oldInfo=newInfo;
				}else{
					oldInfo=info;
				}
				changeSplitMap.put(acctId, oldInfo);
			}
		}

		if (isIncludeProduct) {
			String selectPro = "select FCostAccountId,FProductId,sum(entry.FProdAmount) proAmount ";
			String wherePro = "where FCostBillType='CNTRCHANGESPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 And FIsProduct=1 and entry.fobjectId=? ";
			wherePro += "and  (con.FHasSettled=0 or con.FHasSettled is null) ";
			String groupPro = "group by FCostAccountId,FProductId ";
			builder.clear();
			builder.appendSql(selectPro);
			builder.appendSql(from);
			builder.appendSql(wherePro);
			builder.appendSql(groupPro);
			builder.addParam(this.cuId);
			builder.addParam(objectId);
			IRowSet rsPro = builder.executeQuery();
			while (rsPro.next()) {
				String acctId = rsPro.getString("FCostAccountId");
				if (acctId == null) {
					continue;
				}
				String proId = rsPro.getString("FProductId");
				if (proId == null) {
					continue;
				}
				BigDecimal amount = rsPro.getBigDecimal("proAmount");
				if (amount == null) {
					continue;
				}
				//超级陷阱把我折腾死了 by sxhong
				if (changeSplitMap.containsKey(acctId)||changeSplitMap.containsKey(acctId+0)) {
					HappenDataInfo info = (HappenDataInfo) changeSplitMap
							.get(acctId);
					if(info==null){
						info = (HappenDataInfo) changeSplitMap
						.get(acctId+0);
					}
					
					info.addProductAmount(proId, amount);
				}
			}
		}
		return changeSplitMap;
	}

	public Map getSettleSplitData(String objectId,boolean isIncludeProduct) throws BOSException,
			SQLException {
		//结算拆分的时候合同肯定是结算了的,可以不与合同做关联,  这个地方后来改了：结算单不是最终结算，合同就没有结算 JinXP
		Map settleSplitMap = new HashMap();
		String select = "select FCostAccountId,sum(entry.FAmount) amount ";
		String from = "from T_AIM_CostSplitEntry entry "
				+ "inner join T_AIM_CostSplit head on entry.FParentId=head.FId "
				+ "inner join T_CON_ContractSettlementBill settle on head.FCostBillID=settle.FID ";
		String where = "where FCostBillType='SETTLEMENTSPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 And  entry.fobjectid=? " +
				//已经结算，取最终结算数据；没有结算，取未结算数据
				"and  ((settle.FIsSettled=1 and settle.FIsFinalSettle=1) " +
//				"or (settle.FIsSettled=0 and settle.FIsFinalSettle=0) " +	//不能这样取值,否则对于已实现产值会翻倍,因为未结算部分的数据从其它地方进行取值　 by sxhong 2008-11-30 21:08:39
				")";
		String group = "group by FCostAccountId ";
		
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql(select);
		builder.appendSql(from);
		builder.appendSql(where);
		builder.appendSql(group);
		builder.addParam(this.cuId);
		builder.addParam(objectId);
		IRowSet rs = builder.executeQuery();
		
		while (rs.next()) {
			String acctId = rs.getString("FCostAccountId");
			if (acctId == null) {
				continue;
			}
			BigDecimal amount = rs.getBigDecimal("amount");
			if (amount == null) {
				continue;
			}
			HappenDataInfo info = new HappenDataInfo();
			info.setAcctId(acctId);
			info.setBillType(CostSplitBillTypeEnum.SETTLEMENTSPLIT);
			info.setAmount(amount);
//			info.setSettled(rs.getBoolean("FHasSettled"));
			info.setSettled(true);
			settleSplitMap.put(info.getKey(), info);
		}
		if(isIncludeProduct){
			// 取产品发生
			/*String selectPro = "select FCostAccountId,FProductId,sum(entry.FProdAmount) proAmount ";
			String wherePro = "where FCostBillType='SETTLEMENTSPLIT' and head.fcontrolunitid=? and head.FIsInvalid=0 And FIsProduct=1 and  entry.fobjectid=? ";
			wherePro += "and con.FHasSettled=1 ";
			String groupPro = "group by FCostAccountId,FProductId ";*/
			String selectPro = "select FCostAccountId,FProductId,sum(entry.FProdAmount) proAmount ";
			String wherePro = "where FCostBillType='SETTLEMENTSPLIT' and head.fcontrolunitid=? and head.FIsInvalid=0 " +
					"And FIsProduct=1 and  entry.fobjectid=? "+
					"and  ((settle.FIsSettled=1 and settle.FIsFinalSettle=1) " +
//不能这样取值,否则对于已实现产值会翻倍,因为未结算部分的数据从其它地方进行取值　 by sxhong 2008-11-30 21:08:39
//					"or (settle.FIsSettled=0 and settle.FIsFinalSettle=0) " +
					")";
			String groupPro = "group by FCostAccountId,FProductId ";
			builder.clear();
			builder.appendSql(selectPro);
			builder.appendSql(from);
			builder.appendSql(wherePro);
			builder.appendSql(groupPro);
			builder.addParam(this.cuId);
			builder.addParam(objectId);
			IRowSet rsPro = builder.executeQuery();
			
			while (rsPro.next()) {
				String acctId = rsPro.getString("FCostAccountId");
				if (acctId == null) {
					continue;
				}
				String proId = rsPro.getString("FProductId");
				if (proId == null) {
					continue;
				}
				BigDecimal amount = rsPro.getBigDecimal("proAmount");
				if (amount == null) {
					continue;
				}
				if (settleSplitMap.containsKey(acctId)) {
					HappenDataInfo info = (HappenDataInfo) settleSplitMap
							.get(acctId);
					info.addProductAmount(proId, amount);
				}
			}
		}
		return settleSplitMap;
	}

	public Map getPartSettleSplitData(String objectId) throws BOSException, SQLException {
		// TODO 结算拆分的时候合同肯定是结算了的,可以不与合同做关联
		//审批后才能做拆分
		Map partSettleSplitMap = new HashMap();
		String select = "select FCostAccountId,sum(entry.FAmount) amount ";
		String from = "from T_AIM_CostSplitEntry entry " + "inner join T_AIM_CostSplit head on entry.FParentId=head.FId "
		 + "inner join T_CON_ContractSettlementBill settle on head.FCostBillID=settle.FID ";
		String where = "where FCostBillType='SETTLEMENTSPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 And  entry.fobjectid=? and settle.FIsSettled=0 and settle.FIsFinalSettle=0 ";//部分结算
		String group = "group by FCostAccountId ";

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(select);
		builder.appendSql(from);
		builder.appendSql(where);
		builder.appendSql(group);
		builder.addParam(this.cuId);
		builder.addParam(objectId);
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {
			String acctId = rs.getString("FCostAccountId");
			if (acctId == null) {
				continue;
			}
			BigDecimal amount = rs.getBigDecimal("amount");
			if (amount == null) {
				continue;
			}
			HappenDataInfo info = new HappenDataInfo();
			info.setAcctId(acctId);
			info.setBillType(CostSplitBillTypeEnum.SETTLEMENTSPLIT);
			info.setAmount(amount);
			// info.setSettled(rs.getBoolean("FHasSettled"));
			info.setSettled(true);
			partSettleSplitMap.put(info.getKey(), info);
		}
		return partSettleSplitMap;
	}
	public Map getNoTextSplitData(String objectId,boolean isIncludeProduct) throws BOSException,
			SQLException {
		Map noTextSplitMap = new HashMap();
		String select = "select FCostAccountId,sum(entry.FAmount) amount ";
		String from = "from T_AIM_CostSplitEntry entry "
				+ "inner join T_AIM_CostSplit head on entry.FParentId=head.FId ";
//				+ "inner join T_FDC_CostAccount acct on entry.FCostAccountId=acct.FID ";
		String where = "where FCostBillType='NOTEXTCONSPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 and entry.fobjectid=? ";
		String group = "group by FCostAccountId ";
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql(select);
		builder.appendSql(from);
		builder.appendSql(where);
		builder.appendSql(group);
		builder.addParam(this.cuId);
		builder.addParam(objectId);
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {
			String acctId = rs.getString("FCostAccountId");
			if (acctId == null) {
				continue;
			}
			BigDecimal amount = rs.getBigDecimal("amount");
			if (amount == null) {
				continue;
			}
			HappenDataInfo info = new HappenDataInfo();
			info.setAcctId(acctId);
			info.setBillType(CostSplitBillTypeEnum.NOTEXTCONSPLIT);
			info.setAmount(amount);
			noTextSplitMap.put(info.getKey(), info);
		}
		if(isIncludeProduct){
			// 取产品发生
			String selectPro = "select FCostAccountId,FProductId,sum(entry.FProdAmount) proAmount ";
			String wherePro = " where FCostBillType='NOTEXTCONSPLIT' and head.fcontrolUnitId=? And FIsProduct=1 and head.FIsInvalid=0 and entry.fobjectId=? ";
			String groupPro = " group by FCostAccountId,FProductId ";
			builder.clear();
			builder.appendSql(selectPro);
			builder.appendSql(from);
			builder.appendSql(wherePro);
			builder.appendSql(groupPro);
			builder.addParam(this.cuId);
			builder.addParam(objectId);
			IRowSet rsPro = builder.executeQuery();
			
			while (rsPro.next()) {
				String acctId = rsPro.getString("FCostAccountId");
				if (acctId == null) {
					continue;
				}
				String proId = rsPro.getString("FProductId");
				if (proId == null) {
					continue;
				}
				BigDecimal amount = rsPro.getBigDecimal("proAmount");
				if (amount == null) {
					continue;
				}
				if (noTextSplitMap.containsKey(acctId)) {
					HappenDataInfo info = (HappenDataInfo) noTextSplitMap
							.get(acctId);
					info.addProductAmount(proId, amount);
				}
			}
		}
		return noTextSplitMap;
	}

	//已实现
	public Map getPaySplitData(String objectId,boolean isIncludeProduct) throws BOSException,
			SQLException {
		Map paySplitMap = new HashMap();
		String select = "select FCostAccountId,sum(entry.FAmount) amount ";
		String from = "from T_AIM_CostSplitEntry entry "
				+ "inner join T_AIM_CostSplit head on entry.FParentId=head.FId ";
//				+ "inner join T_FDC_CostAccount acct on entry.FCostAccountId=acct.FID ";
		String where = "where FCostBillType='PAYMENTSPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 And entry.fobjectid=? ";
		String group = "group by FCostAccountId ";
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql(select);
		builder.appendSql(from);
		builder.appendSql(where);
		builder.appendSql(group);
		builder.addParam(this.cuId);
		builder.addParam(objectId);
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {
			String acctId = rs.getString("FCostAccountId");
			if (acctId == null) {
				continue;
			}
			BigDecimal amount = rs.getBigDecimal("amount");
			if (amount == null) {
				continue;
			}
			HappenDataInfo info = new HappenDataInfo();
			info.setAcctId(acctId);
			info.setBillType(CostSplitBillTypeEnum.PAYMENTSPLIT);
			info.setAmount(amount);
			paySplitMap.put(info.getKey(), info);
		}
		// 取产品发生
		if(isIncludeProduct){
			String selectPro = "select FCostAccountId,FProductId,sum(entry.FProdAmount) proAmount ";
			String wherePro = "where head.FCostBillType='PAYMENTSPLIT' And head.FIsInvalid=0 and head.fcontrolUnitid=? and  entry.FIsProduct=1 and entry.fobjectId=? ";
			String groupPro = "group by FCostAccountId,FProductId ";
			builder.clear();
			builder.appendSql(selectPro);
			builder.appendSql(from);
			builder.appendSql(wherePro);
			builder.appendSql(groupPro);
			builder.addParam(this.cuId);
			builder.addParam(objectId);
			IRowSet rsPro = builder.executeQuery();
			while (rsPro.next()) {
				String acctId = rsPro.getString("FCostAccountId");
				if (acctId == null) {
					continue;
				}
				String proId = rsPro.getString("FProductId");
				if (proId == null) {
					continue;
				}
				BigDecimal amount = rsPro.getBigDecimal("proAmount");
				if (amount == null) {
					continue;
				}
				if (paySplitMap.containsKey(acctId)) {
					HappenDataInfo info = (HappenDataInfo) paySplitMap.get(acctId);
					info.addProductAmount(proId, amount);
				}
			}
		}
		return paySplitMap;
	}
	
	//已付款
	public Map getPaidSplitData(String objectId,boolean isIncludeProduct) throws BOSException,
	SQLException {
		Map paySplitMap = new HashMap();
		String select = "select FCostAccountId,sum(entry.FPaidAmount) amount ";
		String from = "from T_AIM_CostSplitEntry entry "
				+ "inner join T_AIM_CostSplit head on entry.FParentId=head.FId ";
		//		+ "inner join T_FDC_CostAccount acct on entry.FCostAccountId=acct.FID ";
		String where = "where FCostBillType='PAYMENTSPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 And entry.fobjectid=? ";
		String group = "group by FCostAccountId ";
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql(select);
		builder.appendSql(from);
		builder.appendSql(where);
		builder.appendSql(group);
		builder.addParam(this.cuId);
		builder.addParam(objectId);
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {
			String acctId = rs.getString("FCostAccountId");
			if (acctId == null) {
				continue;
			}
			BigDecimal amount = rs.getBigDecimal("amount");
			if (amount == null) {
				continue;
			}
			HappenDataInfo info = new HappenDataInfo();
			info.setAcctId(acctId);
			info.setBillType(CostSplitBillTypeEnum.PAYMENTSPLIT);
			info.setAmount(amount);
			paySplitMap.put(info.getKey(), info);
		}
		// 取产品发生
		if(isIncludeProduct){
			String selectPro = "select FCostAccountId,FProductId,sum(entry.FProdPaidAmount) proAmount ";
			String wherePro = "where head.FCostBillType='PAYMENTSPLIT' And head.FIsInvalid=0 and head.fcontrolUnitid=? and  entry.FIsProduct=1 and entry.fobjectId=? ";
			String groupPro = "group by FCostAccountId,FProductId ";
			builder.clear();
			builder.appendSql(selectPro);
			builder.appendSql(from);
			builder.appendSql(wherePro);
			builder.appendSql(groupPro);
			builder.addParam(this.cuId);
			builder.addParam(objectId);
			IRowSet rsPro = builder.executeQuery();
			while (rsPro.next()) {
				String acctId = rsPro.getString("FCostAccountId");
				if (acctId == null) {
					continue;
				}
				String proId = rsPro.getString("FProductId");
				if (proId == null) {
					continue;
				}
				BigDecimal amount = rsPro.getBigDecimal("proAmount");
				if (amount == null) {
					continue;
				}
				if (paySplitMap.containsKey(acctId)) {
					HappenDataInfo info = (HappenDataInfo) paySplitMap.get(acctId);
					info.addProductAmount(proId, amount);
				}
			}
		}
		return paySplitMap;
	}
	private boolean isUsePartSettle=false;
	private boolean hasInit=false;
	private  boolean isUsePartSettle(){
		if(!hasInit){
			try{
				isUsePartSettle=FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_MORESETTER);
			}catch(Exception e){
				e.printStackTrace();
			}
			hasInit=true;
		}
		return isUsePartSettle;
	}
}
