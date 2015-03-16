package com.kingdee.eas.fdc.aimcost.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryFactory;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryFactory;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.AimProductTypeGetter;
import com.kingdee.eas.fdc.aimcost.CostDataGetter;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryFactory;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostSplitInfo;
import com.kingdee.eas.fdc.aimcost.DyCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.DyProductTypeGetter;
import com.kingdee.eas.fdc.aimcost.DynamicCostCollection;
import com.kingdee.eas.fdc.aimcost.DynamicCostFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.HappenDataGetter;
import com.kingdee.eas.fdc.aimcost.HappenDataInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.contract.XTable;
import com.kingdee.eas.fdc.finance.SettledMonthlyHelper;
import com.kingdee.jdbc.rowset.IRowSet;

public class PeriodDataGetter extends CostDataGetter implements Serializable {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.PeriodDataGetter");
	private HappenDataGetter happenGetter;
	private AimProductTypeGetter aimProductTypeGetter;
	private DyProductTypeGetter dyProductTypeGetter;
	private AimCostSplitDataGetter aimGetter;
	private DyCostSplitDataGetter dyGetter;
	private BigDecimal buildArea;
	private BigDecimal sellArea;
	private Map aimSellApportionMap;
	private Context ctx;
	private String prjId;
	private PeriodInfo periodInfo;
	private String periodId;

	public PeriodDataGetter(Context ctx, String prjId, PeriodInfo periodInfo) throws BOSException {
		if(prjId==null||periodInfo==null){
			throw new IllegalArgumentException("PeriodDataGetter init:prjId and periodInfo cannt be null");
		}
		this.ctx = ctx;
		this.prjId = prjId;
		this.periodInfo = periodInfo;
		this.periodId=periodInfo.getId().toString();
		initGetter();
	}
	private PeriodDataGetter(){};
	public static final PeriodDataGetter getFinanceDataGetter(Context ctx,String prjId,PeriodInfo periodInfo) throws BOSException{
		if(prjId==null||periodInfo==null){
			throw new IllegalArgumentException("PeriodDataGetter init:prjId and periodInfo cannt be null");
		}
		PeriodDataGetter getter=new PeriodDataGetter();
		getter.ctx=ctx;
		getter.prjId=prjId;
		getter.periodInfo=periodInfo;
		getter.periodId=periodInfo.getId().toString();
		
		getter.happenGetter = getter.new PeriodHappenDataGetter(ctx, prjId, true, true,true,getter.periodId,true);
		getter.aimProductTypeGetter = getter.new PeriodAimProductTypeGetter(ctx, prjId,getter.periodId);
		getter.dyProductTypeGetter = getter.new PeriodDyProductTypeGetter(ctx, prjId,getter.periodId);
		// 两个get的指标一致，初始化一个即可指标
		getter.dyProductTypeGetter.setDyApportionMap(getter.aimProductTypeGetter.getAimApportionMap());
		getter.aimGetter = getter.new PeriodAimCostSplitDataGetter(ctx, prjId, getter.aimProductTypeGetter,getter.periodId);
		getter.aimGetter.initProductSplitData();
		getter.dyGetter = getter.new PeriodDyCostSplitDataGetter(ctx, prjId, getter.aimGetter, getter.happenGetter, getter.dyProductTypeGetter,getter.periodId);
		getter.dyGetter.initProductSplitData();

/*		getter.buildArea = (BigDecimal) getter.dyProductTypeGetter.getDyApportionMap().get(prjId + " " + ApportionTypeInfo.buildAreaType);
		getter.sellArea = (BigDecimal) getter.dyProductTypeGetter.getDyApportionMap().get(prjId + " " + ApportionTypeInfo.sellAreaType);
		getter.aimSellApportionMap = ProjectHelper.getIndexValueByProjProd(ctx, prjId, ApportionTypeInfo.sellAreaType, ProjectStageEnum.AIMCOST, getter.periodInfo);
*/		
		return getter;
	}
	private void initGetter() throws BOSException {
		happenGetter = new PeriodHappenDataGetter(ctx, prjId, true, true,true,periodId);
		aimProductTypeGetter = new PeriodAimProductTypeGetter(ctx, prjId,periodId);
		dyProductTypeGetter = new PeriodDyProductTypeGetter(ctx, prjId,periodId);
		// 两个get的指标一致，初始化一个即可指标
		dyProductTypeGetter.setDyApportionMap(aimProductTypeGetter.getAimApportionMap());
		aimGetter = new PeriodAimCostSplitDataGetter(ctx, prjId, aimProductTypeGetter,periodId);
		aimGetter.initProductSplitData();
		dyGetter = new PeriodDyCostSplitDataGetter(ctx, prjId, aimGetter, happenGetter, dyProductTypeGetter,periodId);
		dyGetter.initProductSplitData();

		buildArea = (BigDecimal) dyProductTypeGetter.getDyApportionMap().get(prjId + " " + ApportionTypeInfo.buildAreaType);
		sellArea = (BigDecimal) dyProductTypeGetter.getDyApportionMap().get(prjId + " " + ApportionTypeInfo.sellAreaType);
		aimSellApportionMap = ProjectHelper.getIndexValueByProjProd(ctx, prjId, ApportionTypeInfo.sellAreaType, ProjectStageEnum.AIMCOST, this.periodInfo);
	}

	public HappenDataGetter getHappenGetter() {
		return happenGetter;
	}

	public AimProductTypeGetter getAimProductTypeGetter() {
		return aimProductTypeGetter;
	}

	public DyProductTypeGetter getDyProductTypeGetter() {
		return dyProductTypeGetter;
	}

	public AimCostSplitDataGetter getAimGetter() {
		return aimGetter;
	}

	public DyCostSplitDataGetter getDyGetter() {
		return dyGetter;
	}

	public BigDecimal getBuildArea() {
		return buildArea;
	}

	public BigDecimal getSellArea() {
		return sellArea;
	}

	public Map getAimSellApportionMap() {
		return aimSellApportionMap;
	}

	/**
	 * -------------------------------------------------- 内部Getter类
	 * ---------------------------------------------------
	 */
	class PeriodAimProductTypeGetter extends AimProductTypeGetter {
		private String periodId=null;
		public PeriodAimProductTypeGetter(Context ctx, String projectId,String periodId) throws BOSException {
			this.ctx = ctx;
			this.projectId=projectId;
			this.periodId=periodId;
			this.initProductType(projectId);
		}

		public Map getAimApportionMap() throws BOSException {
			if (aimApportionMap == null) {
				aimApportionMap = ProjectHelper.getIndexValueByProjProd(ctx, prjId, ProjectStageEnum.DYNCOST, periodId);
			}
			System.out.println(periodInfo);
			return aimApportionMap;
		}

		public void setAimApportionMap(Map aimApportionMap) {
			this.aimApportionMap = aimApportionMap;
		}
	}

	class PeriodDyProductTypeGetter extends DyProductTypeGetter {
		private String periodId=null;
		public PeriodDyProductTypeGetter(Context ctx, String projectId,String periodId) throws BOSException {
			this.projectId=projectId;
			this.ctx = ctx;
			this.periodId=periodId;
			this.initProductType(projectId);
		}

		public Map getDyApportionMap() throws BOSException {
			if (dyApportionMap == null) {
				dyApportionMap = ProjectHelper.getIndexValueByProjProd(ctx, prjId, ProjectStageEnum.DYNCOST, periodId);
			}
			return dyApportionMap;
		}

		public void setDyApportionMap(Map dyApportionMap) {
			this.dyApportionMap = dyApportionMap;
		}
	}

	 class PeriodAimCostSplitDataGetter extends AimCostSplitDataGetter {
		private String periodId=null;
		public PeriodAimCostSplitDataGetter(Context ctx, String objectId, AimProductTypeGetter productTypeGetter,String periodId) throws BOSException {
			this.ctx = ctx;
			this.periodId=periodId;
			try{
				init(objectId,productTypeGetter);
			}catch (SQLException e) {
				throw new BOSException(e);
			}
		}

		protected void initAimCostData(String objectId) throws BOSException, SQLException {
			aimCostXTable = new XTable();
			aimCostXTable.addColumn("aimCostSum");
			aimCostXTable.addColumn("entryColl");
			EntityViewInfo aimView = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			aimView.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("orgOrProId", objectId));
			filter.getFilterItems().add(new FilterItemInfo("isLastVersion", new Integer(1)));
			SettledMonthlyHelper.addPeriodFilter(filter, "id", new AimCostInfo().getBOSType(), this.periodId, objectId, null);

			aimView.getSelector().add(new SelectorItemInfo("*"));
			AimCostCollection aimCostCollection = null;
			if (ctx == null) {
				aimCostCollection = AimCostFactory.getRemoteInstance().getAimCostCollection(aimView);
			} else {
				aimCostCollection = AimCostFactory.getLocalInstance(ctx).getAimCostCollection(aimView);
			}

			if (aimCostCollection.size() >= 1) {
				AimCostInfo aimInfo = aimCostCollection.get(0);
				EntityViewInfo entryView = new EntityViewInfo();
				filter = new FilterInfo();
				entryView.setFilter(filter);
				filter.getFilterItems().add(new FilterItemInfo("head", aimInfo.getId().toString()));
				entryView.getSelector().add(new SelectorItemInfo("*"));
				entryView.getSelector().add(new SelectorItemInfo("product.*"));
				entryView.getSorter().add(new SorterItemInfo("costAccount"));
				entryView.getSorter().add(new SorterItemInfo("entryName"));
				CostEntryCollection costEntrys = null;
				if (ctx == null) {
					costEntrys = CostEntryFactory.getRemoteInstance().getCostEntryCollection(entryView);
				} else {
					costEntrys = CostEntryFactory.getLocalInstance(ctx).getCostEntryCollection(entryView);
				}

				Set costEntryIdSet = new HashSet();
				for (int i = 0; i < costEntrys.size(); i++) {
					CostEntryInfo entry = costEntrys.get(i);
					costEntryIdSet.add(entry.getId().toString());
					costEntryMap.put(entry.getId().toString(), entry);
					CostAccountInfo costAccount = entry.getCostAccount();
					BigDecimal value = entry.getCostAmount();
					if (value == null) {
						value = FDCHelper.ZERO;
					}
					Map row = aimCostXTable.getRow(costAccount.getId().toString());
					if (row != null) {
						BigDecimal sum = (BigDecimal) row.get("aimCostSum");
						row.put("aimCostSum", sum.add(value));
						CostEntryCollection coll = (CostEntryCollection) row.get("entryColl");
						coll.add(entry);

					} else {
						Map newRow = aimCostXTable.addRow(costAccount.getId().toString());
						CostEntryCollection newColl = new CostEntryCollection();
						newColl.add(entry);
						newRow.put("aimCostSum", value);
						newRow.put("entryColl", newColl);
					}
				}
				// 初始化目标成本拆分数据
				if (costEntryIdSet.size() > 0) {
					EntityViewInfo view = new EntityViewInfo();
					view.getSelector().clear();
					view.getSelector().add("*");
					view.getSelector().add("product.id");
					// view.getSelector().add("apportionType.*");
					view.getSelector().add("apportionType.id");
					view.getSelector().add("apportionType.number");
					view.getSelector().add("apportionType.name");
					filter = new FilterInfo();
					view.setFilter(filter);
					// 用costEntryIdset 过滤不需要加版本
					filter.getFilterItems().add(new FilterItemInfo("costEntryId", costEntryIdSet, CompareType.INCLUDE));
					AimCostProductSplitEntryCollection splits = null;
					if (ctx == null) {
						splits = AimCostProductSplitEntryFactory.getRemoteInstance().getAimCostProductSplitEntryCollection(view);
					} else {
						splits = AimCostProductSplitEntryFactory.getLocalInstance(ctx).getAimCostProductSplitEntryCollection(view);
					}
					aimEntryProductMap.clear();
					for (int i = 0; i < splits.size(); i++) {
						AimCostProductSplitEntryInfo info = splits.get(i);
						String costEntryId = info.getCostEntryId();
						if (aimEntryProductMap.containsKey(costEntryId)) {
							AimCostProductSplitEntryCollection coll = (AimCostProductSplitEntryCollection) aimEntryProductMap.get(costEntryId);
							coll.add(info);
						} else {
							AimCostProductSplitEntryCollection newColl = new AimCostProductSplitEntryCollection();
							newColl.add(info);
							aimEntryProductMap.put(costEntryId, newColl);
						}
					}
				}
			}
		}
	}

	 class PeriodHappenDataGetter extends HappenDataGetter {
		private final BOSObjectType bosType = new CostSplitInfo().getBOSType();
		private String periodId=null;	
		public PeriodHappenDataGetter(Context ctx, String objectId, boolean isDivideChangeType, boolean isIncludeSettled,boolean isIncludeProduct,String periodId) throws BOSException {
			this.ctx = ctx;
			this.periodId=periodId;
			try {
				init(objectId);
				this.initHasHappenData(objectId, isDivideChangeType,
						isIncludeSettled,isIncludeProduct);
			} catch (Exception e) {
				throw new BOSException(e);
			}
		}

		public PeriodHappenDataGetter(Context ctx, String objectId, boolean isDivideChangeType, boolean isIncludeSettled,boolean isIncludeProduct,String periodId,boolean isFinancePeriod) throws BOSException {
			this.ctx = ctx;
			this.periodId = periodId;
			try {
				init(objectId);
				if (isFinancePeriod) {
					this.initFinanceHasHappenData(objectId, isDivideChangeType, isIncludeSettled, isIncludeProduct);
				} else {
					this.initHasHappenData(objectId, isDivideChangeType, isIncludeSettled, isIncludeProduct);
				}
			} catch (Exception e) {
				throw new BOSException(e);
			}
		}
		
		protected void initFinanceHasHappenData(String objectId, boolean isDivideChangeType,
				boolean isIncludeSettled,boolean isIncludeProduct) throws BOSException, SQLException {
			hasHappenMap = new HashMap();
			noTextSplitMap = getNoTextSplitData(objectId,true);
			paySplitMap = getPaySplitData(objectId,true);

			List acctIds = new ArrayList();
			acctIds.addAll(noTextSplitMap.keySet());
			for (int i = 0; i < acctIds.size(); i++) {
				HappenDataInfo info = new HappenDataInfo();
				String acctId = (String) acctIds.get(i);
				info.add((HappenDataInfo) noTextSplitMap.get(acctId));
				this.hasHappenMap.put(acctId, info);
			}
		}
		
		
		// 覆盖的方法
		public Map getContractSplitData(String objectId, boolean isIncludeSettled,boolean isIncludeProduct) throws BOSException, SQLException {
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			Map conSplitMap = new HashMap();
			String select = "select FCostAccountId,con.FHasSettled,sum(entry.FAmount) amount ";
			String from = "from T_AIM_CostSplitEntry entry "
					+ "inner join T_AIM_CostSplit head on entry.FParentID=head.FId "
					+ "inner join T_CON_ContractBill con on head.FCostBillID=con.FId ";
			String where = "where FCostBillType='CONTRACTSPLIT' and  head.FControlunitId=? And head.FIsInvalid=0 And FIsProduct=0 and  entry.fobjectId=? ";
			where += " and " + SettledMonthlyHelper.getPeriodExistsSql("head.fid", bosType, periodId);
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
				wherePro += " and " + SettledMonthlyHelper.getPeriodExistsSql("head.fid", bosType, periodId);
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
			where += " and " + SettledMonthlyHelper.getPeriodExistsSql("head.fid", bosType, periodId);
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
				if(isIncludeProduct&&!info.isSettled()){
					changeSplitMap.put(acctId, info);
				}
			}

			if (isIncludeProduct) {
				String selectPro = "select FCostAccountId,FProductId,sum(entry.FProdAmount) proAmount ";
				String wherePro = "where FCostBillType='CNTRCHANGESPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 And FIsProduct=1 and entry.fobjectId=? ";
				wherePro += "and  (con.FHasSettled=0 or con.FHasSettled is null) ";
				wherePro += " and " + SettledMonthlyHelper.getPeriodExistsSql("head.fid", bosType, periodId);
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
			//TODO 结算拆分的时候合同肯定是结算了的,可以不与合同做关联
			Map settleSplitMap = new HashMap();
	/*		String select = "select FCostAccountId,con.FHasSettled,sum(entry.FAmount) amount ";
			String from = "from T_AIM_CostSplitEntry entry "
					+ "inner join T_AIM_CostSplit head on entry.FParentId=head.FId "
//					+ "inner join T_FDC_CostAccount acct on entry.FCostAccountId=acct.FID "
					+ "inner join T_CON_ContractSettlementBill settle on head.FCostBillID=settle.FID "
					+ "inner join T_CON_ContractBill con on settle.FContractBillID=con.FID  ";
			String where = "where FCostBillType='SETTLEMENTSPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 And  entry.fobjectid=?";
			where += " and con.FHasSettled=1 ";
			String group = "group by FCostAccountId,con.FHasSettled ";*/
			
			String select = "select FCostAccountId,sum(entry.FAmount) amount ";
			String from = "from T_AIM_CostSplitEntry entry "
					+ "inner join T_AIM_CostSplit head on entry.FParentId=head.FId "
					+ "inner join T_CON_ContractSettlementBill settle on head.FCostBillID=settle.FID ";
			String where = "where FCostBillType='SETTLEMENTSPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 And  entry.fobjectid=? ";
			where += " and " + SettledMonthlyHelper.getPeriodExistsSql("head.fid", bosType, periodId);
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
//				info.setSettled(rs.getBoolean("FHasSettled"));
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
				String wherePro = "where FCostBillType='SETTLEMENTSPLIT' and head.fcontrolunitid=? and head.FIsInvalid=0 And FIsProduct=1 and  entry.fobjectid=? ";
				wherePro += " and " + SettledMonthlyHelper.getPeriodExistsSql("head.fid", bosType, periodId);
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

		public Map getNoTextSplitData(String objectId,boolean isIncludeProduct) throws BOSException,
				SQLException {
			Map noTextSplitMap = new HashMap();
			String select = "select FCostAccountId,sum(entry.FAmount) amount ";
			String from = "from T_AIM_CostSplitEntry entry "
					+ "inner join T_AIM_CostSplit head on entry.FParentId=head.FId ";
//					+ "inner join T_FDC_CostAccount acct on entry.FCostAccountId=acct.FID ";
			String where = "where FCostBillType='NOTEXTCONSPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 and entry.fobjectid=? ";
			where += " and " + SettledMonthlyHelper.getPeriodExistsSql("head.fid", bosType, periodId);
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
				wherePro += " and " + SettledMonthlyHelper.getPeriodExistsSql("head.fid", bosType, periodId);
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

		public Map getPaySplitData(String objectId,boolean isIncludeProduct) throws BOSException,
				SQLException {
			Map paySplitMap = new HashMap();
			String select = "select FCostAccountId,sum(entry.FAmount) amount ";
			String from = "from T_AIM_CostSplitEntry entry "
					+ "inner join T_AIM_CostSplit head on entry.FParentId=head.FId ";
//					+ "inner join T_FDC_CostAccount acct on entry.FCostAccountId=acct.FID ";
			String where = "where FCostBillType='PAYMENTSPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 And entry.fobjectid=? ";
			where += " and " + SettledMonthlyHelper.getPeriodExistsSql("head.fid", bosType, periodId);
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
				wherePro += " and " + SettledMonthlyHelper.getPeriodExistsSql("head.fid", bosType, periodId);
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
		
		public Map getPaidSplitData(String objectId,boolean isIncludeProduct) throws BOSException,SQLException {
			return new HashMap();
		}
	}

	 class PeriodDyCostSplitDataGetter extends DyCostSplitDataGetter {
		private String periodId=null;
		public PeriodDyCostSplitDataGetter(Context ctx, String objectId, AimCostSplitDataGetter aimGetter, HappenDataGetter happenGetter, DyProductTypeGetter productTypeGetter,String periodId) throws BOSException {
			this.ctx = ctx;
			this.happenGetter = happenGetter;
			this.aimGetter = aimGetter;
			this.periodId=periodId;
			if(productTypeGetter==null){
				this.productTypeGetter = new DyProductTypeGetter(objectId);
			}else{
				this.productTypeGetter=productTypeGetter;
			}
			TimeTools.getInstance().msValuePrintln(" start initDynamicCostData");
			this.initDynamicCostData(objectId);
			TimeTools.getInstance().msValuePrintln(" end initDynamicCostData");
		}

		protected void initDynamicCostData(String objectId) throws BOSException {
			super.initDynamicCostData(objectId);
			for(Iterator iter=dynamicCostMap.values().iterator();iter.hasNext();){
				DynamicCostInfo info=(DynamicCostInfo)iter.next();
				if(info!=null){
					info.setAdjustSumAmount(FDCHelper.ZERO);
				}
			}
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			if (isCurProject(objectId)) {
				filter.getFilterItems().add(new FilterItemInfo("parent.account.curProject.id", objectId));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("parent.account.fullOrgUnit.id", objectId));
			}
			
			BOSObjectType bosType = new AdjustRecordEntryInfo().getBOSType();
			SettledMonthlyHelper.addPeriodFilter(filter, "id", bosType, periodId);
			view.getSelector().add(new SelectorItemInfo("*"));
			view.getSelector().add(new SelectorItemInfo("parent.*"));
			view.getSelector().add(new SelectorItemInfo("product.*"));
			view.getSelector().add(new SelectorItemInfo("adjustType.*"));
			view.getSelector().add(new SelectorItemInfo("adjustReason.*"));
			AdjustRecordEntryCollection adjustRecordEntryCollection=null; 
			if(ctx!=null){
				adjustRecordEntryCollection = AdjustRecordEntryFactory.getLocalInstance(ctx).getAdjustRecordEntryCollection(view);
			}else{
				adjustRecordEntryCollection = AdjustRecordEntryFactory.getRemoteInstance().getAdjustRecordEntryCollection(view);
			}
			Set idSet = new HashSet();
			for(Iterator iter=adjustRecordEntryCollection.iterator();iter.hasNext();){
				AdjustRecordEntryInfo entry=(AdjustRecordEntryInfo)iter.next();
				DynamicCostInfo info=(DynamicCostInfo)dynamicCostMap.get(entry.getParent().getAccount().getId().toString());
				if(info!=null){
					info.getAdjustEntrys().add(entry);
					info.setAdjustSumAmount(info.getAdjustSumAmount().add(FDCHelper.toBigDecimal(entry.getCostAmount())));
				}else{
					info=entry.getParent();
					info.getAdjustEntrys().clear();
					info.getAdjustEntrys().add(entry);
					info.setAdjustSumAmount(FDCHelper.toBigDecimal(entry.getCostAmount()));
					dynamicCostMap.put(info.getAccount().getId().toString(), info);
				}
				idSet.add(info.getId().toString());
			}
			
			//加上目标成本部分,没有待发生但是有目标成本的部分
			
			
/*			DynamicCostCollection DynamicCostCollection = new DynamicCostCollection();
			for(Iterator iter=dynamicCostMap.values().iterator();iter.hasNext();){
				DynamicCostInfo info=(DynamicCostInfo)iter.next();
				DynamicCostCollection.add(info);
			}*/
			// add period getter TODO should add prj filter
/*			logger.info("dynamicCost view:" + view.toString());
			if (ctx != null) {
				DynamicCostCollection = DynamicCostFactory.getLocalInstance(ctx).getDynamicCostCollection(view);
			} else {
				DynamicCostCollection = DynamicCostFactory.getRemoteInstance().getDynamicCostCollection(view);
			}
			Set idSet = new HashSet();
			for (int i = 0; i < DynamicCostCollection.size(); i++) {
				DynamicCostInfo info = DynamicCostCollection.get(i);
				BigDecimal adjustSum = FDCHelper.ZERO;
				for (Iterator iter = info.getAdjustEntrys().iterator(); iter.hasNext();) {
					AdjustRecordEntryInfo adjust = (AdjustRecordEntryInfo) iter.next();
					adjustSum.add(FDCHelper.toBigDecimal(adjust.getCostAmount()));
				}
				info.setAdjustSumAmount(adjustSum);
				CostAccountInfo acct = info.getAccount();
				dynamicCostMap.put(acct.getId().toString(), info);
				idSet.add(info.getId().toString());
			}
*/
/*			view = new EntityViewInfo();
			view.getSelector().add("*");
			view.getSelector().add("product.id");
			view.getSelector().add("apportionType.id");
			view.getSelector().add("apportionType.name");
			view.getSelector().add("apportionType.number");
			filter = new FilterInfo();
			view.setFilter(filter);
			view.getFilter().getFilterItems().add(new FilterItemInfo("dynamicCostId", idSet, CompareType.INCLUDE));
			DynamicCostProductSplitEntryCollection splits = new DynamicCostProductSplitEntryCollection();
			if (ctx != null) {
				splits = DynamicCostProductSplitEntryFactory.getLocalInstance(ctx).getDynamicCostProductSplitEntryCollection(view);
			} else {
				splits = DynamicCostProductSplitEntryFactory.getRemoteInstance().getDynamicCostProductSplitEntryCollection(view);
			}
			for (int i = 0; i < splits.size(); i++) {
				DynamicCostProductSplitEntryInfo info = splits.get(i);
				String costEntryId = info.getDynamicCostId();
				if (splitEntryMap.containsKey(costEntryId)) {
					DynamicCostProductSplitEntryCollection coll = (DynamicCostProductSplitEntryCollection) splitEntryMap.get(costEntryId);
					coll.add(info);
				} else {
					DynamicCostProductSplitEntryCollection newColl = new DynamicCostProductSplitEntryCollection();
					newColl.add(info);
					splitEntryMap.put(costEntryId, newColl);
				}
			}*/
		}

	}

}
