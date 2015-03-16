package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.fdc.basedata.CurProjProEntrApporDataCollection;
import com.kingdee.eas.fdc.basedata.CurProjProEntrApporDataInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ICurProjProductEntries;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;

/**
 * 目标成本用
 * @author liupd
 *
 */
public class ResProductTypeGetter extends CostDataGetter implements Serializable{
	public Map productTypesMap = new HashMap();

	Context ctx = null;

	public ResProductTypeGetter(String projectId) {
		try {
			this.initProductType(projectId);
		} catch (BOSException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}

	public ResProductTypeGetter(Context ctx, String projectId) {
		try {
			this.ctx = ctx;
			this.initProductType(projectId);
		} catch (BOSException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}

	public CurProjProEntrApporDataCollection getAppors(String productId) {
		CurProjProductEntriesInfo info = (CurProjProductEntriesInfo) this.productTypesMap
				.get(productId);
		return info.getCurProjProEntrApporData(ProjectStageEnum.RESEARCH);
	}

	public boolean containsId(String id) {
		return this.productTypesMap.containsKey(id);
	}

	public int getProductTypeSize() {
		return this.productTypesMap.size();
	}

	public Map getSortedProductMap() {
		Set keySet = this.productTypesMap.keySet();
		Map prodcutMap = new TreeMap();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			CurProjProductEntriesInfo value = (CurProjProductEntriesInfo) this.productTypesMap
					.get(productId);
			prodcutMap.put(value.getProductType().getNumber(), value
					.getProductType());
		}
		return prodcutMap;
	}

	public String[] getProductTypeIds() {
		String[] productTypeIds = new String[this.getProductTypeSize()];
		Set keySet = this.productTypesMap.keySet();
		int i = 0;
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String productTypeId = (String) iter.next();
			productTypeIds[i] = productTypeId;
			i++;
		}
		return productTypeIds;
	}

	public BigDecimal getProductApprotion(String productId, String apportionId) {
		BigDecimal value = FDCHelper.ZERO;
		CurProjProductEntriesInfo proEntry = (CurProjProductEntriesInfo) this.productTypesMap
				.get(productId);
		if (proEntry == null) {
			return value;
		}
		CurProjProEntrApporDataCollection appors = proEntry
				.getCurProjProEntrApporData(ProjectStageEnum.RESEARCH);
		for (int k = 0; k < appors.size(); k++) {
			CurProjProEntrApporDataInfo appor = appors.get(k);
			if (appor.getApportionType().getId().toString().equals(apportionId)) {
				value = appor.getValue();
			}
		}
		return value;
	}

	private void initProductType(String selectObjId) throws BOSException {
		productTypesMap.clear();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("curProject.id", selectObjId));
		filter.getFilterItems().add(
				new FilterItemInfo("productType.isEnabled", new Integer(1)));
		filter.getFilterItems().add(
				new FilterItemInfo("isAccObj", new Integer(1)));
		// 产品类型
		view.getSelector().add("productType.*");
		// 分摊数据
		// view.getSelector().add("curProjProEntrApporData.apportionType.*");
		// view.getSelector().add("curProjProEntrApporData.value");

		ICurProjProductEntries inst = null;
		if (this.ctx == null) {
			inst = CurProjProductEntriesFactory.getRemoteInstance();
		} else {
			inst = CurProjProductEntriesFactory.getLocalInstance(ctx);
		}
		CurProjProductEntriesCollection collObj = inst
				.getCurProjProductEntriesCollection(view);
		for (int i = 0; i < collObj.size(); i++) {
			String productId = collObj.get(i).getProductType().getId()
					.toString();
			this.productTypesMap.put(productId, collObj.get(i));
		}

	}

}
