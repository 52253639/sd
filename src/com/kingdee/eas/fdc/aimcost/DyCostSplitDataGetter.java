package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

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
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.app.ContextUtil;

public class DyCostSplitDataGetter extends CostDataGetter implements Serializable{
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.aimcost.DyCostSplitDataGetter");
	
	protected Map splitEntryMap = new HashMap();

	protected Map dynamicCostMap = new HashMap();

	protected DyProductTypeGetter productTypeGetter;

	protected HappenDataGetter happenGetter;

	protected AimCostSplitDataGetter aimGetter;
	public DyProductTypeGetter getDyProductTypeGetter(){
		return this.productTypeGetter;
	}
	public HappenDataGetter getHappenDataGetter(){
		return this.happenGetter;
	}
	public AimCostSplitDataGetter getAimCostSplitDataGetter(){
		return this.aimGetter;
	}
	public void clear(){
		clearMap(this.splitEntryMap);
		clearMap(this.dynamicCostMap);
	}
	protected DyCostSplitDataGetter(){};
	public DyCostSplitDataGetter(String objectId,
			AimCostSplitDataGetter aimGetter, HappenDataGetter happenGetter) {
		try {
			this.happenGetter = happenGetter;
			this.aimGetter = aimGetter;
			this.productTypeGetter = new DyProductTypeGetter(objectId);
			this.initDynamicCostData(objectId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected Context ctx = null;

	public DyCostSplitDataGetter(Context ctx, String objectId,
			AimCostSplitDataGetter aimGetter, HappenDataGetter happenGetter) {
		try {
			this.ctx = ctx;
			this.happenGetter = happenGetter;
			this.aimGetter = aimGetter;
			this.productTypeGetter = new DyProductTypeGetter(objectId);
			this.initDynamicCostData(objectId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 如下两个构造方法通过传入DyProductTypeGetter来减少重复取数
	 * @param objectId
	 * @param aimGetter
	 * @param happenGetter
	 * @param productTypeGetter
	 */
	public DyCostSplitDataGetter(String objectId,
			AimCostSplitDataGetter aimGetter, HappenDataGetter happenGetter,DyProductTypeGetter productTypeGetter) {
		try {
			this.happenGetter = happenGetter;
			this.aimGetter = aimGetter;
			if(productTypeGetter==null){
				this.productTypeGetter = new DyProductTypeGetter(objectId);
			}else{
				this.productTypeGetter=productTypeGetter;
			}
			this.initDynamicCostData(objectId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DyCostSplitDataGetter(Context ctx, String objectId,
			AimCostSplitDataGetter aimGetter, HappenDataGetter happenGetter,DyProductTypeGetter productTypeGetter) {
		try {
			this.ctx = ctx;
			this.happenGetter = happenGetter;
			this.aimGetter = aimGetter;
			
			if(productTypeGetter==null){
				this.productTypeGetter = new DyProductTypeGetter(objectId);
			}else{
				this.productTypeGetter=productTypeGetter;
			}
			TimeTools.getInstance().msValuePrintln(" start initDynamicCostData");
			this.initDynamicCostData(objectId);
			TimeTools.getInstance().msValuePrintln(" end initDynamicCostData");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updateProductMap(String dynamicId,
			DynamicCostProductSplitEntryCollection col) {
		this.splitEntryMap.put(dynamicId, col);
	}

	public void updateDynamic(String acctId, DynamicCostInfo dynamic) {
		this.dynamicCostMap.put(acctId, dynamic);
	}

	public DynamicCostProductSplitEntryCollection getProductSplitEntry(
			String dynamicId) {
		DynamicCostProductSplitEntryCollection col = (DynamicCostProductSplitEntryCollection) this.splitEntryMap
				.get(dynamicId);
		return col;
	}

	protected void initDynamicCostData(String objectId) throws BOSException {

		//是否启用财务一体化
		boolean finacial= false;
		if(ctx != null && ctx.get("finacial") != null){
			finacial =  ((Boolean)ctx.get("finacial")).booleanValue();
		}
		
		dynamicCostMap.clear();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.getSelector().add("id");
		view.setFilter(filter);
		if (isCurProject(objectId)) {
//			filter.getFilterItems().add(
//					new FilterItemInfo("account.curProject.id", objectId));
			filter.getFilterItems().add(
					new FilterItemInfo("curProject.id", objectId));
		} else {
//			filter.getFilterItems().add(
//					new FilterItemInfo("account.fullOrgUnit.id", objectId));
			filter.getFilterItems().add(
					new FilterItemInfo("fullOrgUnit.id", objectId));
		}
		CostAccountCollection costAccColl = new CostAccountCollection();
		if(ctx != null){
			costAccColl = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		}else{
			costAccColl = CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
		}
		Set costAccIds = new HashSet();
		for(int i = 0; i < costAccColl.size(); i++){
			CostAccountInfo costAccountInfo = costAccColl.get(i);
			costAccIds.add(costAccountInfo.getId().toString());
		}
		
		/**
		 * costAccIds不为null，是“[]”，如果size=0的话，就return
		 * 
		 */ 
		if(costAccIds == null || costAccIds.size() <= 0){
			return;
		}
		view = new EntityViewInfo();
		filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("account.id",costAccIds,CompareType.INCLUDE));
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("adjustEntrys.*"));
		// adjusterName is null by hpw 2009-12-22
		view.getSelector().add(new SelectorItemInfo("adjustEntrys.adjuster.name"));
		view.getSelector().add(new SelectorItemInfo("intendingCostEntrys.*"));
		view.getSelector().add(new SelectorItemInfo("adjustEntrys.product.id"));
		view.getSelector().add(new SelectorItemInfo("adjustEntrys.product.name"));
		view.getSelector().add(new SelectorItemInfo("adjustEntrys.product.number"));
		view.getSelector().add(new SelectorItemInfo("adjustEntrys.adjustType.id"));
		view.getSelector().add(new SelectorItemInfo("adjustEntrys.adjustType.name"));
		view.getSelector().add(new SelectorItemInfo("adjustEntrys.adjustType.number"));
		view.getSelector().add(new SelectorItemInfo("adjustEntrys.adjustReason.id"));
		view.getSelector().add(new SelectorItemInfo("adjustEntrys.adjustReason.name"));
		view.getSelector().add(new SelectorItemInfo("adjustEntrys.adjustReason.number"));
		view.getSelector().add(new SelectorItemInfo("intendingCostEntrys.product.id"));
		view.getSelector().add(new SelectorItemInfo("intendingCostEntrys.product.number"));
		view.getSelector().add(new SelectorItemInfo("intendingCostEntrys.product.name"));
		DynamicCostCollection DynamicCostCollection = new DynamicCostCollection();
		if (ctx != null) {
			DynamicCostCollection = DynamicCostFactory.getLocalInstance(ctx)
			.getDynamicCostCollection(view);
		} else {
			DynamicCostCollection = DynamicCostFactory.getRemoteInstance()
					.getDynamicCostCollection(view);
		}
		if(DynamicCostCollection==null || DynamicCostCollection.size() == 0 )
			return ;
		Set idSet = new HashSet();
		for (int i = 0; i < DynamicCostCollection.size(); i++) {
			DynamicCostInfo info = DynamicCostCollection.get(i);
			CostAccountInfo acct = info.getAccount();
			dynamicCostMap.put(acct.getId().toString(), info);
			idSet.add(info.getId().toString());
		}
		view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("product.id");
		view.getSelector().add("apportionType.id");
		view.getSelector().add("apportionType.name");
		view.getSelector().add("apportionType.number");
		filter = new FilterInfo();
		view.setFilter(filter);
		view.getFilter().getFilterItems().add(
				new FilterItemInfo("dynamicCostId", idSet,CompareType.INCLUDE));
		//取最新版本的分摊方案
		view.getFilter().getFilterItems().add(
				new FilterItemInfo("isLatestVer",new Integer(1)));
//		//如果未启用财务成本一体化，则取没有期间的方案
//		if(!finacial){
//			view.getFilter().getFilterItems().add(
//					new FilterItemInfo("period",CompareType.EMPTY));
//		}
		//月结报表期间
//		if(ctx.get("period") != null && ctx.get("period") instanceof PeriodInfo){
//			PeriodInfo periodInfo = (PeriodInfo)ctx.get("period");
//			view.getFilter().getFilterItems().add(
//					new FilterItemInfo("period",periodInfo.getId().toString()));	
//		}else{
			// 增加对当前期间的判断
		if (finacial && isCurProject(objectId)) {
			PeriodInfo curPeriod;
			try {
				if(ctx.get("period") != null && ctx.get("period") instanceof PeriodInfo){
					curPeriod = (PeriodInfo)ctx.get("period");
				}else{					
					curPeriod = FDCUtils.getCurrentPeriod(ctx, objectId, true);
				}
				// 获得有分摊方案的最近期间
				PeriodInfo period = checkPeriod(ctx, idSet, curPeriod);
				if (period != null) {
					view.getFilter().getFilterItems().add(
							new FilterItemInfo("period", period.getId()
									.toString()));
				} else {
					view.getFilter().getFilterItems().add(
							new FilterItemInfo("period", curPeriod.getId()
									.toString()));
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			}
		} else {
			view.getFilter().getFilterItems().add(
					new FilterItemInfo("period",null));
		}			
//		}
		DynamicCostProductSplitEntryCollection splits = new DynamicCostProductSplitEntryCollection();
		
		//when use 'include' or 'notinclude' as compare type,the compare value cannot be a set without elements
		if(idSet!=null && idSet.size()>0){
			if(ctx!=null){
				splits = DynamicCostProductSplitEntryFactory
					.getLocalInstance(ctx).getDynamicCostProductSplitEntryCollection(view);
			}else{
				splits = DynamicCostProductSplitEntryFactory
					.getRemoteInstance().getDynamicCostProductSplitEntryCollection(view);
			}
		}
		for (int i = 0; i < splits.size(); i++) {
			DynamicCostProductSplitEntryInfo info = splits.get(i);
			String costEntryId = info.getDynamicCostId();
			if (splitEntryMap.containsKey(costEntryId)) {
				DynamicCostProductSplitEntryCollection coll = (DynamicCostProductSplitEntryCollection) splitEntryMap
						.get(costEntryId);
				coll.add(info);
			} else {
				DynamicCostProductSplitEntryCollection newColl = new DynamicCostProductSplitEntryCollection();
				newColl.add(info);
				splitEntryMap.put(costEntryId, newColl);
			}
		}
	}
	//检查期间是否存在分摊数据 如果存在，返回本期间；否则返回上一期间
	private PeriodInfo checkPeriod(Context ctx,Set idSet,PeriodInfo period)throws BOSException,EASBizException{
		if(idSet == null || idSet.size() == 0 || period == null){
			return null;
		}
		PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(ctx,period);
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("period.id");
		view.getSelector().add("period.name");
		FilterInfo filter = new FilterInfo();
		if(nextPeriod != null && nextPeriod.getNumber() != 0){
			filter.getFilterItems().add(new FilterItemInfo("period.number",
					Integer.toString(nextPeriod.getNumber()),CompareType.LESS));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("period.number",
					Integer.toString(period.getNumber()),CompareType.LESS_EQUALS));			
		}
		filter.getFilterItems().add(new FilterItemInfo("dynamicCostId",idSet,CompareType.INCLUDE));
		view.getSorter().add(new SorterItemInfo("period.number"));
		view.getSorter().get(0).setSortType(SortType.DESCEND);
		view.setFilter(filter);
		DynamicCostProductSplitEntryCollection coll = DynamicCostProductSplitEntryFactory
			.getLocalInstance(ctx).getDynamicCostProductSplitEntryCollection(view);
		if(coll != null && coll.size() >0){
			DynamicCostProductSplitEntryInfo entry = coll.get(0);
			return entry.getPeriod();
		}else{
//			PeriodInfo prePeriod = PeriodUtils.getPrePeriodInfo(ctx,period);
//			if(prePeriod != null){
//				return prePeriod;
//			}else{
			return period;
//			}
		}
	}
	public DynamicCostProductSplitEntryCollection createSetting(
			DynamicCostProductSplitEntryCollection splitColl,
			Map aimProductMap, Map adjustMap, Map hasHappenMap) {
		// 数据库拆分方案
		DynamicCostProductSplitEntryCollection dySplits = new DynamicCostProductSplitEntryCollection();
		Map dySplitsMap = new HashMap();
		ApportionTypeInfo apportionType = null;
		if (splitColl != null) {
			for (int i = 0; i < splitColl.size(); i++) {
				DynamicCostProductSplitEntryInfo info = splitColl.get(i);
				if (info.getApportionType() != null) {
					apportionType = info.getApportionType();
				}
				info.setAimCostAmount(FDCHelper.ZERO);
				info.setIntendingDirectAmount(FDCHelper.ZERO);
				info.setHanppenDirectAmount(FDCHelper.ZERO);
				dySplitsMap.put(info.getProduct().getId().toString(), info);
			}
		}
		// 加上只有调整的产品分摊
		Set set = adjustMap.keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			if (dySplitsMap.containsKey(productId)) {
				DynamicCostProductSplitEntryInfo split = (DynamicCostProductSplitEntryInfo) dySplitsMap
						.get(productId);
				split.setIntendingDirectAmount((BigDecimal) adjustMap
						.get(productId));
			} else {
				DynamicCostProductSplitEntryInfo split = new DynamicCostProductSplitEntryInfo();
				ProductTypeInfo product = new ProductTypeInfo();
				product.setId(BOSUuid.read(productId));
				split.setProduct(product);
				split.setApportionType(apportionType);
				split.setIntendingDirectAmount((BigDecimal) adjustMap
						.get(productId));
				dySplitsMap.put(productId, split);
			}
		}
		// 加上只有目标成本的产品分摊
		set = aimProductMap.keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			if (dySplitsMap.containsKey(productId)) {
				DynamicCostProductSplitEntryInfo split = (DynamicCostProductSplitEntryInfo) dySplitsMap
						.get(productId);
				split.setAimCostAmount((BigDecimal) aimProductMap
						.get(productId));
			} else {
				DynamicCostProductSplitEntryInfo split = new DynamicCostProductSplitEntryInfo();
				ProductTypeInfo product = new ProductTypeInfo();
				product.setId(BOSUuid.read(productId));
				split.setProduct(product);
				split.setApportionType(apportionType);
				split.setAimCostAmount((BigDecimal) aimProductMap
						.get(productId));
				dySplitsMap.put(productId, split);
			}
		}
		// 已发生直接指定
		set = hasHappenMap.keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			if (dySplitsMap.containsKey(productId)) {
				DynamicCostProductSplitEntryInfo split = (DynamicCostProductSplitEntryInfo) dySplitsMap
						.get(productId);
				split.setHanppenDirectAmount((BigDecimal) hasHappenMap
						.get(productId));
			} else {
				DynamicCostProductSplitEntryInfo split = new DynamicCostProductSplitEntryInfo();
				ProductTypeInfo product = new ProductTypeInfo();
				product.setId(BOSUuid.read(productId));
				split.setProduct(product);
				split.setApportionType(apportionType);
				split.setHanppenDirectAmount((BigDecimal) hasHappenMap
						.get(productId));
				dySplitsMap.put(productId, split);
			}
		}
		
//		if (this.aimGetter.getAimProductTypeGetter().getProductTypeSize() > 0) {
//			String[] typeIds = this.aimGetter.getAimProductTypeGetter().getProductTypeIds();
//			String productId = null;
//			for (int i = 0; i < typeIds.length; i++) {
//				productId = typeIds[i];
//				if (!dySplitsMap.containsKey(productId)) {
//					DynamicCostProductSplitEntryInfo split = new DynamicCostProductSplitEntryInfo();
//					ProductTypeInfo product = new ProductTypeInfo();
//					product.setId(BOSUuid.read(productId));
//					split.setProduct(product);
//					split.setApportionType(apportionType);
//					split.setHanppenDirectAmount(FDCHelper.ZERO);
//					dySplitsMap.put(productId, split);
//				}
//			}
//		}
		
		
		Collection dys = dySplitsMap.values();
		for (Iterator iter = dys.iterator(); iter.hasNext();) {
			DynamicCostProductSplitEntryInfo info = (DynamicCostProductSplitEntryInfo) iter
					.next();
			dySplits.add(info);
		}
		return dySplits;
	}

	/**
	 * 得到产品的动态成本数据
	 * @param acctId
	 * @return
	 * @throws BOSException
	 */
	public Map getDyProductMap(String acctId) throws BOSException {
		DynamicCostInfo dynamic = this.getDynamicInfo(acctId);
		if(dynamic==null) {
			//把目标成本的金额放进来
			return aimGetter.getProductMap(acctId);
		}
		return this.getDyProductMap(acctId, dynamic.getAdjustEntrys());
	}

	public Map getDyProductMap(String acctId,
			AdjustRecordEntryCollection adjustEntrys) throws BOSException {
		DynamicCostInfo dynamic = this.getDynamicInfo(acctId);
		Map aimProductMap = this.aimGetter.getProductMap(acctId);
		DynamicCostProductSplitEntryCollection splitColl = new DynamicCostProductSplitEntryCollection();
		if (dynamic != null && dynamic.getId() != null) {
			splitColl = this.getProductSplitEntry(dynamic.getId().toString());
		}
		Map adjustMap = getAdjustDirectMap(adjustEntrys);
		DynamicCostProductSplitEntryCollection dySplits = this.createSetting(
				splitColl, aimProductMap, adjustMap, new HashMap());
		// 根据拆分方案得到产品数据
		Map dyProductData = this.getDyProductData(acctId, dySplits);
		return dyProductData;
	}
	
	/**
	 * 描述：获取产品的动态成本（是否包括新增未保存的记录）
	 * @param acctId
	 * @param adjustEntrys
	 * @param isIncludeNew 是否包括新增未保存的记录
	 * @return
	 * @throws BOSException
	 */
	public Map getDyProductMap(String acctId,
			AdjustRecordEntryCollection adjustEntrys, boolean isIncludeNew) throws BOSException {
		
		if (!isIncludeNew) {
			return getDyProductMap(acctId, adjustEntrys);
		} else {
			DynamicCostInfo dynamic = this.getDynamicInfo(acctId);
			Map aimProductMap = this.aimGetter.getProductMap(acctId);
			DynamicCostProductSplitEntryCollection splitColl = new DynamicCostProductSplitEntryCollection();
			if (dynamic != null && dynamic.getId() != null) {
				splitColl = this.getProductSplitEntry(dynamic.getId().toString());
			}
			Map adjustMap = getAdjustDirectMap(adjustEntrys);
			DynamicCostProductSplitEntryCollection dySplits = this.createSetting(
					splitColl, aimProductMap, adjustMap, new HashMap());
			/*
			 * 根据拆分方案得到产品数据
			 * 要取得 科目目标成本 
			 */
			BigDecimal dyCost = this.aimGetter.getAimCost(acctId);//FDCHelper.ZERO;
			for (int i = 0; i < adjustEntrys.size(); i++) {
				AdjustRecordEntryInfo info = adjustEntrys.get(i);
				BigDecimal costAmount = info.getCostAmount();
				if (costAmount != null) {
					dyCost = dyCost.add(costAmount);
				}
			}
			Map dyProductData = this.getDyProductData(dyCost, dySplits);
			return dyProductData;
		}
	}

	public Map getHasHappenProductMap(String acctId) throws BOSException {
		Map aimProductMap = this.aimGetter.getProductMap(acctId);
		DynamicCostInfo dynamic = this.getDynamicInfo(acctId);
		DynamicCostProductSplitEntryCollection splitColl = new DynamicCostProductSplitEntryCollection();
		AdjustRecordEntryCollection adjustEntrys = new AdjustRecordEntryCollection();
		if (dynamic != null && dynamic.getId() != null) {
			splitColl = this.getProductSplitEntry(dynamic.getId().toString());
			adjustEntrys = dynamic.getAdjustEntrys();
		}
		HappenDataInfo happen = this.happenGetter.getHappenInfo(acctId);
		Map hasHappenMap = new HashMap();
		if (happen != null && happen.getProductAmountMap() != null) {
			hasHappenMap = happen.getProductAmountMap();
		}
		Map adjustMap = getAdjustDirectMap(adjustEntrys);
		DynamicCostProductSplitEntryCollection dySplits = this.createSetting(
				splitColl, aimProductMap, adjustMap, hasHappenMap);
		// 根据拆分方案得到产品数据
		Map dyProductData = this.getHasHappenProductData(acctId, dySplits);
		return dyProductData;
	}

	public Map getAdjustDirectMap(AdjustRecordEntryCollection adjustCostEntrys) {
		// 调整的直接费用
		Map adjustMap = new HashMap();
		if (adjustCostEntrys != null) {
			for (int i = 0; i < adjustCostEntrys.size(); i++) {
				AdjustRecordEntryInfo info = adjustCostEntrys.get(i);
				BigDecimal costAmount = info.getCostAmount();
				ProductTypeInfo product = info.getProduct();
				if (costAmount != null && product != null) {
					if (adjustMap.containsKey(product.getId().toString())) {
						BigDecimal value = (BigDecimal) adjustMap.get(product
								.getId().toString());
						adjustMap.put(product.getId().toString(), value
								.add(costAmount));
					} else {
						adjustMap.put(product.getId().toString(), costAmount);
					}
				}
			}
		}
		return adjustMap;
	}

	public DynamicCostInfo getDynamicInfo(String acctId) {
		DynamicCostInfo dynamic = (DynamicCostInfo) this.dynamicCostMap
				.get(acctId);
		return dynamic;
	}
	
	public Map getDyProductData(String acctId,
			DynamicCostProductSplitEntryCollection splits) throws BOSException {
		BigDecimal dynamicAmount = getDynamicValue(acctId);

		// 动态成本分摊
		Map dyCalculateData = new HashMap();
		// 先扣掉直接费用
		for (int j = 0; j < splits.size(); j++) {
			DynamicCostProductSplitEntryInfo split = splits.get(j);
			String productSplitId = split.getProduct().getId().toString();
			if (this.productTypeGetter.containsId(productSplitId)) {
				BigDecimal directAmount = FDCHelper.ZERO;
				if (split.getAimCostAmount() != null) {
					directAmount = directAmount.add(split.getAimCostAmount());
				}
				if (split.getIntendingDirectAmount() != null) {
					directAmount = directAmount.add(split
							.getIntendingDirectAmount());
				}
				dyCalculateData.put(productSplitId, directAmount);
				dynamicAmount = dynamicAmount.subtract(directAmount);
			}
		}
		fillSplitData(dyCalculateData, dynamicAmount, splits);
		return dyCalculateData;
	}
	
	/**
	 * 描述：修改getDyProductData函数时，请同步修改该函数
	 * @param dynamicAmount
	 * @param splits
	 * @return
	 * @throws BOSException
	 * @author zhiyuan_tang 2010-12-01
	 */
	public Map getDyProductData(BigDecimal dynamicAmount, DynamicCostProductSplitEntryCollection splits) throws BOSException {
		
		if (dynamicAmount == null) {
			dynamicAmount = FDCNumberHelper.ZERO;
		}
		// 动态成本分摊
		Map dyCalculateData = new HashMap();
		// 先扣掉直接费用
		for (int j = 0; j < splits.size(); j++) {
			DynamicCostProductSplitEntryInfo split = splits.get(j);
			String productSplitId = split.getProduct().getId().toString();
			if (this.productTypeGetter.containsId(productSplitId)) {
				BigDecimal directAmount = FDCHelper.ZERO;
				if (split.getAimCostAmount() != null) {
					directAmount = directAmount.add(split.getAimCostAmount());
				}
				if (split.getIntendingDirectAmount() != null) {
					directAmount = directAmount.add(split
							.getIntendingDirectAmount());
				}
				dyCalculateData.put(productSplitId, directAmount);
				dynamicAmount = dynamicAmount.subtract(directAmount);
			}
		}
		fillSplitData(dyCalculateData, dynamicAmount, splits);
		return dyCalculateData;
	}

	/**
	 * 得到动态成本值
	 * @param acctId
	 * @return
	 */
	public BigDecimal getDynamicValue(String acctId) {
		DynamicCostInfo dynamicInfo = this.getDynamicInfo(acctId);
		BigDecimal dynamicAmount = this.aimGetter.getAimCost(acctId);
		if (dynamicInfo != null && dynamicInfo.getAdjustSumAmount() != null) {
			dynamicAmount = dynamicAmount.add(dynamicInfo.getAdjustSumAmount());
		}
		return dynamicAmount;
	}

	public Map getHasHappenProductData(String acctId,
			DynamicCostProductSplitEntryCollection splits) throws BOSException {
		HappenDataInfo happen = this.happenGetter.getHappenInfo(acctId);
		BigDecimal happenAmount = FDCHelper.ZERO;
		if (happen != null) {
			happenAmount = happen.getAmount();
		}
		// 已发生成本分摊
		Map happenCalculateData = new HashMap();
		// 先扣掉直接费用
		for (int j = 0; j < splits.size(); j++) {
			DynamicCostProductSplitEntryInfo split = splits.get(j);
			String productSplitId = split.getProduct().getId().toString();
			if (this.productTypeGetter.containsId(productSplitId)) {
				BigDecimal directAmount = FDCHelper.ZERO;
				if (split.getHanppenDirectAmount() != null) {
					directAmount = directAmount.add(split
							.getHanppenDirectAmount());
				}
				happenCalculateData.put(productSplitId, directAmount);
				happenAmount = happenAmount.subtract(directAmount);
			}
		}
		fillSplitData(happenCalculateData, happenAmount, splits);
		return happenCalculateData;
	}

	private void fillSplitData(Map calculateData, BigDecimal amount,
			DynamicCostProductSplitEntryCollection splits) throws BOSException {
		if (splits.size() <= 0||splits.get(0).getApportionType()==null) {
			if(this.productTypeGetter!=null&&FDCHelper.toBigDecimal(amount).signum()>0){
				Map sortedProductMap = this.productTypeGetter.getSortedProductMap();
				if(sortedProductMap!=null&&sortedProductMap.size()>0){
					String prodNumber =(String)sortedProductMap.keySet().iterator().next();
					String prodId=((ProductTypeInfo)sortedProductMap.get(prodNumber)).getId().toString();
					BigDecimal directAmount = (BigDecimal) calculateData.get(prodId);
					BigDecimal finalAmount = FDCHelper.add(directAmount, amount);
					calculateData.put(prodId, finalAmount);
					
				}
				
			}
			return;
		}
		ApportionTypeInfo apportionType = splits.get(0).getApportionType();
		// 根据分摊类型拆分
		Map areaData = new HashMap();
/*		if (apportionType == null) {
			return;
		}*/
		BigDecimal areaSum = FDCHelper.ZERO;
		for (int j = 0; j < splits.size(); j++) {
			String productSplitId = splits.get(j).getProduct().getId()
					.toString();
			if (splits.get(j).getDescription() == null) {
				areaData.put(productSplitId, FDCHelper.ZERO);
				continue;
			}
			String apportionId = apportionType.getId().toString();
			if (apportionId.equals(ApportionTypeInfo.aimCostType)) {
				BigDecimal value = FDCHelper.ZERO;
				if (splits.get(j).getAimCostAmount() != null) {
					value = splits.get(j).getAimCostAmount();
				}
				areaData.put(productSplitId, value);
				areaSum = areaSum.add(value);
			}else if (apportionId.equals(ApportionTypeInfo.appointType)) {
				BigDecimal value = FDCHelper.ZERO;
				if (splits.get(j).getAppointAmount() != null) {
					value = splits.get(j).getAppointAmount();
				}
				areaData.put(productSplitId, value);
				areaSum = areaSum.add(value);
			} else {
				BigDecimal appValue = this.productTypeGetter
						.getProductApprotion(productSplitId, apportionType
								.getId().toString());
				areaData.put(productSplitId, appValue);
				areaSum = areaSum.add(appValue);
			}
		}
		String lastProductId = null;
		BigDecimal lastRestAmount = amount.add(FDCHelper.ZERO);
		Set keySet=new TreeSet(calculateData.keySet());
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String prodId = (String) iter.next();
			BigDecimal splitAmount = FDCHelper.ZERO;
			if (areaSum.compareTo(FDCHelper.ZERO) != 0) {
				BigDecimal area = (BigDecimal) areaData.get(prodId);
				if (area == null) {
					area = FDCHelper.ZERO;
				}
				splitAmount = amount.multiply(area).divide(areaSum, 2,
						BigDecimal.ROUND_HALF_UP);
				if (splitAmount.compareTo(FDCHelper.ZERO) != 0) {
					lastProductId = prodId;
					lastRestAmount = lastRestAmount.subtract(splitAmount);
				}
			} else {
				lastProductId = prodId;
			}
			BigDecimal directAmount = (BigDecimal) calculateData.get(prodId);
			BigDecimal finalAmount = directAmount.add(splitAmount);
			calculateData.put(prodId, finalAmount);
		}
		// 拆分剩余部分，加入最后一个产品
		if (lastProductId != null
				&& lastRestAmount.compareTo(FDCHelper.ZERO) != 0) {
			BigDecimal calAmount = (BigDecimal) calculateData
					.get(lastProductId);
			BigDecimal finalAmount = calAmount.add(lastRestAmount);
			calculateData.put(lastProductId, finalAmount);
		}
	}
	
	public void initProductSplitData(){
		
	}
	
	/**
	 * 得到已实现各产品的值
	 * @param acctId
	 * @return
	 * @throws BOSException
	 */
	public Map getHasPayProductMap(String acctId) throws BOSException {
		Map aimProductMap = this.aimGetter.getProductMap(acctId);
		DynamicCostInfo dynamic = this.getDynamicInfo(acctId);
		DynamicCostProductSplitEntryCollection splitColl = new DynamicCostProductSplitEntryCollection();
		AdjustRecordEntryCollection adjustEntrys = new AdjustRecordEntryCollection();
		if (dynamic != null && dynamic.getId() != null) {
			splitColl = this.getProductSplitEntry(dynamic.getId().toString());
			adjustEntrys = dynamic.getAdjustEntrys();
		}
		HappenDataInfo hasPay = this.happenGetter.getHasPayInfo(acctId);
		Map hasPayMap = new HashMap();
		if (hasPay != null && hasPay.getProductAmountMap() != null) {
			hasPayMap = hasPay.getProductAmountMap();
		}
		Map adjustMap = getAdjustDirectMap(adjustEntrys);
		DynamicCostProductSplitEntryCollection dySplits = this.createPaySplitSetting(
				splitColl, aimProductMap, adjustMap, hasPayMap);
		// 根据拆分方案得到产品数据
		Map dyProductData = this.getHasPayProductData(acctId, dySplits);
		return dyProductData;
	}
	
	public Map getHasPayProductData(String acctId,
			DynamicCostProductSplitEntryCollection splits) throws BOSException {
		HappenDataInfo happen = this.happenGetter.getHasPayInfo(acctId);
		BigDecimal happenAmount = FDCHelper.ZERO;
		if (happen != null) {
			happenAmount = happen.getAmount();
		}
		// 已发生成本分摊
		Map happenCalculateData = new HashMap();
		// 先扣掉直接费用
		for (int j = 0; j < splits.size(); j++) {
			DynamicCostProductSplitEntryInfo split = splits.get(j);
			String productSplitId = split.getProduct().getId().toString();
			if (this.productTypeGetter.containsId(productSplitId)) {
				BigDecimal directAmount = FDCHelper.ZERO;
				if (split.getHanppenDirectAmount() != null) {
					directAmount = directAmount.add(split
							.getHanppenDirectAmount());
				}
				happenCalculateData.put(productSplitId, directAmount);
				happenAmount = happenAmount.subtract(directAmount);
			}
		}
		fillSplitData(happenCalculateData, happenAmount, splits);
		return happenCalculateData;
	}
	
	public DynamicCostProductSplitEntryCollection createPaySplitSetting(
			DynamicCostProductSplitEntryCollection splitColl,
			Map aimProductMap, Map adjustMap, Map hasPayMap) {
		// 数据库拆分方案
		DynamicCostProductSplitEntryCollection dySplits = new DynamicCostProductSplitEntryCollection();
		Map dySplitsMap = new HashMap();
		ApportionTypeInfo apportionType = null;
		if (splitColl != null) {
			for (int i = 0; i < splitColl.size(); i++) {
				DynamicCostProductSplitEntryInfo info = splitColl.get(i);
				if (info.getApportionType() != null) {
					apportionType = info.getApportionType();
				}
				info.setAimCostAmount(FDCHelper.ZERO);
				info.setIntendingDirectAmount(FDCHelper.ZERO);
				info.setHanppenDirectAmount(FDCHelper.ZERO);
				dySplitsMap.put(info.getProduct().getId().toString(), info);
			}
		}
		// 加上只有调整的产品分摊
		Set set = adjustMap.keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			if (dySplitsMap.containsKey(productId)) {
				DynamicCostProductSplitEntryInfo split = (DynamicCostProductSplitEntryInfo) dySplitsMap
						.get(productId);
				split.setIntendingDirectAmount((BigDecimal) adjustMap
						.get(productId));
			} else {
				DynamicCostProductSplitEntryInfo split = new DynamicCostProductSplitEntryInfo();
				ProductTypeInfo product = new ProductTypeInfo();
				product.setId(BOSUuid.read(productId));
				split.setProduct(product);
				split.setApportionType(apportionType);
				split.setIntendingDirectAmount((BigDecimal) adjustMap
						.get(productId));
				dySplitsMap.put(productId, split);
			}
		}
		// 加上只有目标成本的产品分摊
		set = aimProductMap.keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			if (dySplitsMap.containsKey(productId)) {
				DynamicCostProductSplitEntryInfo split = (DynamicCostProductSplitEntryInfo) dySplitsMap
						.get(productId);
				split.setAimCostAmount((BigDecimal) aimProductMap
						.get(productId));
			} else {
				DynamicCostProductSplitEntryInfo split = new DynamicCostProductSplitEntryInfo();
				ProductTypeInfo product = new ProductTypeInfo();
				product.setId(BOSUuid.read(productId));
				split.setProduct(product);
				split.setApportionType(apportionType);
				split.setAimCostAmount((BigDecimal) aimProductMap
						.get(productId));
				dySplitsMap.put(productId, split);
			}
		}
		// 加上只有已发生付款的产品分摊
		set = hasPayMap.keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			if (dySplitsMap.containsKey(productId)) {
				DynamicCostProductSplitEntryInfo split = (DynamicCostProductSplitEntryInfo) dySplitsMap
						.get(productId);
				split.setHanppenDirectAmount((BigDecimal) hasPayMap
						.get(productId));
			} else {
				DynamicCostProductSplitEntryInfo split = new DynamicCostProductSplitEntryInfo();
				ProductTypeInfo product = new ProductTypeInfo();
				product.setId(BOSUuid.read(productId));
				split.setProduct(product);
				split.setApportionType(apportionType);
				split.setHanppenDirectAmount((BigDecimal) hasPayMap
						.get(productId));
				dySplitsMap.put(productId, split);
			}
		}
		Collection dys = dySplitsMap.values();
		for (Iterator iter = dys.iterator(); iter.hasNext();) {
			DynamicCostProductSplitEntryInfo info = (DynamicCostProductSplitEntryInfo) iter
					.next();
			dySplits.add(info);
		}
		return dySplits;
	}
	
	public BOSUuid getSplitApportionType(String acctId){
		final DynamicCostInfo dynamicInfo = getDynamicInfo(acctId);
		if(dynamicInfo==null){
			return null;
		}
		
		final DynamicCostProductSplitEntryCollection entrys = this.getProductSplitEntry(dynamicInfo.getId().toString());
		if(entrys==null||entrys.size()==0){
			return null;
		}
		for(int i=0;i<entrys.size();i++){
			final DynamicCostProductSplitEntryInfo entry = entrys.get(i);
			if(entry.getApportionType()!=null&&entry.getDescription()!=null&&entry.getDescription().equals("isChoose")){
				return entry.getApportionType().getId();
			}
		}
		return null;
	}
}
