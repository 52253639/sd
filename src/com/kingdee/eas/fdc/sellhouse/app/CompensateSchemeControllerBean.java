package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.PropertyInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.sellhouse.CompensateSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.SellHouseException;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;

public class CompensateSchemeControllerBean extends AbstractCompensateSchemeControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.CompensateSchemeControllerBean");

	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		handleIntermitNumber(ctx, (CompensateSchemeInfo) model);
		return super._save(ctx, model);
	}

	private static void handleIntermitNumber(Context ctx, CompensateSchemeInfo info) throws BOSException, CodingRuleException, EASBizException {
		// 如果用户在客户端手工选择了断号,则此处不必在抢号
		if (info.getNumber() != null && info.getNumber().length() > 0)
			return;

		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);

		String costUnitId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();

		if (StringUtils.isEmpty(costUnitId)) {
			return;
		}

		if (iCodingRuleManager.isExist(info, costUnitId)) {
			// 选择了断号支持或者没有选择新增显示,获取并设置编号
			if (iCodingRuleManager.isUseIntermitNumber(info, costUnitId) || !iCodingRuleManager.isAddView(info, costUnitId))
			// 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
			{
				// 启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
				String number = iCodingRuleManager.getNumber(info, costUnitId);
				info.setNumber(number);
			}
		}
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		return super._submit(ctx, model);
	}

	protected void _checkNameDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		CompensateSchemeInfo com = (CompensateSchemeInfo) model;
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number, com.getNumber(), CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (com.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, com.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
		}
		filterItem = new FilterItemInfo("project.id", com.getSellProject().getId().toString());
		filter.getFilterItems().add(filterItem);
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));

		if (super._exists(ctx, filter)) {
			String number = this._getPropertyAlias(ctx, com, IFWEntityStruct.dataBase_Number) + com.getNumber();
			throw new EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { number });
		}
	}

	protected String _getPropertyAlias(Context ctx, CompensateSchemeInfo dataBaseInfo, String propertyName) {
		IMetaDataLoader loader = MetaDataLoaderFactory.getMetaDataLoader(ctx);
		EntityObjectInfo entity = null;
		entity = loader.getEntity(dataBaseInfo.getBOSType());
		PropertyInfo property = null;
		/*
		 * PropertyCollection propertycol = null; propertycol =
		 * entity.getProperties();
		 */
		property = entity.getPropertyByName(propertyName);
		if (property == null) {
			return "";
		} else {
			return property.getAlias();
		}
	}

	protected void _checkNumberDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		CompensateSchemeInfo com = (CompensateSchemeInfo) model;
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Name, com.getName(), CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (com.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, com.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
		}
		filterItem = new FilterItemInfo("project.id", com.getSellProject().getId().toString());
		filter.getFilterItems().add(filterItem);
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));

		if (super._exists(ctx, filter)) {
			String name = this._getPropertyAlias(ctx, com, IFWEntityStruct.dataBase_Name) + com.getName();
			throw new EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { name });
		}
	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		CompensateSchemeInfo info = super.getCompensateSchemeInfo(ctx, pk);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("scheme.id", info.getId().toString()));
		if (RoomAreaCompensateFactory.getLocalInstance(ctx).exists(filter)) {
			throw new SellHouseException(SellHouseException.USEDBYCOMPENSATE);
		}
		super._delete(ctx, pk);
	}
}