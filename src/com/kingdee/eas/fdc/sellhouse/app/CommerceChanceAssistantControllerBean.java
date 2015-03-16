package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo;
import com.kingdee.eas.util.app.ContextUtil;

public class CommerceChanceAssistantControllerBean extends
		AbstractCommerceChanceAssistantControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.CommerceChanceAssistantControllerBean");

	protected void _checkNameDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		CommerceChanceAssistantInfo assInfo = (CommerceChanceAssistantInfo) model;
		FullOrgUnitInfo org = ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo();// 当前组织
		FilterInfo filter = new FilterInfo();
		if (null != assInfo.getType()) {
			filter.getFilterItems().add(
					new FilterItemInfo("type", assInfo.getType().getId()));
			filter.getFilterItems().add(
					new FilterItemInfo("name", assInfo.getName()));
			filter.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", org.getId().toString()));
//			filter.getFilterItems().add(
//					new FilterItemInfo("orgUnit.id", getOrgUnitIdSet(ctx),
//							CompareType.INCLUDE));
		}
		if (this._exists(ctx, filter)) {
			throw new EASBizException(EASBizException.CHECKDUPLICATED,
					new Object[] { assInfo.getName() });
		}

	}

	protected void _checkNumberDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		CommerceChanceAssistantInfo assInfo = (CommerceChanceAssistantInfo) model;
		FullOrgUnitInfo org = ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo();// 当前组织
		FilterInfo filter = new FilterInfo();
		if (null != assInfo.getType()) {
			filter.getFilterItems().add(
					new FilterItemInfo("type", assInfo.getType().getId()));
			filter.getFilterItems().add(
					new FilterItemInfo("number", assInfo.getNumber()));
			filter.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", org.getId().toString()));
//			filter.getFilterItems().add(
//					new FilterItemInfo("orgUnit.id", getOrgUnitIdSet(ctx),
//							CompareType.INCLUDE));
		}
		if (this._exists(ctx, filter)) {
			throw new EASBizException(EASBizException.CHECKDUPLICATED,
					new Object[] { assInfo.getNumber() });
		}

	}

//	private Set getOrgUnitIdSet(Context ctx) throws BOSException {
//		Set idSet = new HashSet();
//		FullOrgUnitInfo org = ContextUtil.getCurrentOrgUnit(ctx)
//				.castToFullOrgUnitInfo();// 当前组织
//		String longNumber = org.getLongNumber();
//		String numbers[] = longNumber.split("!");// 对长编码拆分
//		FilterInfo numFilter = new FilterInfo();
//		EntityViewInfo view = new EntityViewInfo();
//		SelectorItemCollection sic = new SelectorItemCollection();
//		sic.add("id");
//		view.setSelector(sic);
//		for (int i = 0; i < numbers.length; i++) {// 所有上级的编码
//			FilterInfo parentFilter = new FilterInfo();
//			parentFilter.getFilterItems().add(
//					new FilterItemInfo("number", numbers[i]));
//			parentFilter.mergeFilter(parentFilter, "OR");
//			numFilter.mergeFilter(parentFilter, "OR");
//		}
//		view.setFilter(numFilter);
//		FullOrgUnitCollection FullOrgUnitColl = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitCollection(view);
//		if (FullOrgUnitColl != null && FullOrgUnitColl.size() > 0) {
//			for (int i = 0; i < FullOrgUnitColl.size(); i++) {
//				String id = FullOrgUnitColl.get(i).getId().toString();
//				idSet.add(id);
//			}
//		}
//		return idSet;
//	}
}