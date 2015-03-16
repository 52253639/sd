package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;

public class HasPaySplitDataGetter extends CostDataGetter implements Serializable{
	private Map splitEntryMap = new HashMap();

	private Map dynamicCostMap = new HashMap();

	private DyProductTypeGetter productTypeGetter = null;

	private HappenDataGetter happenGetter = null ;

	private AimCostSplitDataGetter aimGetter = null ;

	public HasPaySplitDataGetter(String objectId,
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

	public HasPaySplitDataGetter(Context ctx,String objectId,
			AimCostSplitDataGetter aimGetter, HappenDataGetter happenGetter,DyProductTypeGetter productTypeGetter) {
		try {
			this.happenGetter = happenGetter;
			this.aimGetter = aimGetter;
			if(this.productTypeGetter==null){
				this.productTypeGetter = new DyProductTypeGetter(ctx,objectId);
			}else{
				this.productTypeGetter=productTypeGetter;
			}
			this.initDynamicCostData(objectId);
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

	private void initDynamicCostData(String objectId) throws BOSException {
		dynamicCostMap.clear();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		BOSObjectType bosType = BOSUuid.read(objectId).getType();
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			filter.getFilterItems().add(
					new FilterItemInfo("account.curProject.id", objectId));
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("account.fullOrgUnit.id", objectId));
		}
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("adjustEntrys.*"));
		view.getSelector().add(new SelectorItemInfo("intendingCostEntrys.*"));
		view.getSelector().add(new SelectorItemInfo("adjustEntrys.product.*"));
		view.getSelector().add(
				new SelectorItemInfo("adjustEntrys.adjustType.*"));
		view.getSelector().add(
				new SelectorItemInfo("adjustEntrys.adjustReason.*"));
		view.getSelector().add(
				new SelectorItemInfo("intendingCostEntrys.product.*"));
		DynamicCostCollection DynamicCostCollection = DynamicCostFactory
				.getRemoteInstance().getDynamicCostCollection(view);
		for (int i = 0; i < DynamicCostCollection.size(); i++) {
			DynamicCostInfo info = DynamicCostCollection.get(i);
			CostAccountInfo acct = info.getAccount();
			dynamicCostMap.put(acct.getId().toString(), info);
		}

		view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("apportionType.*");
		filter = new FilterInfo();
		view.setFilter(filter);
		view.getFilter().getFilterItems().add(
				new FilterItemInfo("dynamicCostId", this.getDynamicIdSet(),
						CompareType.INCLUDE));
		DynamicCostProductSplitEntryCollection splits = DynamicCostProductSplitEntryFactory
				.getRemoteInstance().getDynamicCostProductSplitEntryCollection(
						view);
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

	public DynamicCostProductSplitEntryCollection createSetting(
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
		DynamicCostProductSplitEntryCollection dySplits = this.createSetting(
				splitColl, aimProductMap, adjustMap, hasPayMap);
		// 根据拆分方案得到产品数据
		Map dyProductData = this.getHasPayProductData(acctId, dySplits);
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

	public Set getDynamicIdSet() {
		Collection col = this.dynamicCostMap.values();
		Set idSet = new HashSet();
		for (Iterator iter = col.iterator(); iter.hasNext();) {
			DynamicCostInfo dynamic = (DynamicCostInfo) iter.next();
			idSet.add(dynamic.getId().toString());
		}
		return idSet;
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

	private BigDecimal getDynamicValue(String acctId) {
		DynamicCostInfo dynamicInfo = this.getDynamicInfo(acctId);
		BigDecimal dynamicAmount = this.aimGetter.getAimCost(acctId);
		if (dynamicInfo != null && dynamicInfo.getAdjustSumAmount() != null) {
			dynamicAmount = dynamicAmount.add(dynamicInfo.getAdjustSumAmount());
		}
		return dynamicAmount;
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

	private void fillSplitData(Map calculateData, BigDecimal amount,
			DynamicCostProductSplitEntryCollection splits) throws BOSException {
		if (splits.size() <= 0) {
			return;
		}
		ApportionTypeInfo apportionType = splits.get(0).getApportionType();
		// 根据分摊类型拆分
		Map areaData = new HashMap();
		if (apportionType == null) {
			return;
		}
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
		Set keySet = calculateData.keySet();
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

}
