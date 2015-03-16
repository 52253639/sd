package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.tenancy.SurrenderReasonInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.framework.util.FilterUtility;

public class SurrenderReasonControllerBean extends AbstractSurrenderReasonControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.SurrenderReasonControllerBean");

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		return super._submit(ctx, model);
	}    
	protected void _checkNumberDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		DataBaseInfo dataBaseInfo = (DataBaseInfo) model;
		SurrenderReasonInfo surrenderReasonInfo = (SurrenderReasonInfo) model;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.dataBase_Number, dataBaseInfo.getNumber(), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("parent.id", surrenderReasonInfo.getParent().getId().toString()));
		if (dataBaseInfo.getId() != null) {
			filter.getFilterItems().add( new FilterItemInfo(IFWEntityStruct.coreBase_ID, dataBaseInfo.getId(), CompareType.NOTEQUALS));
		}
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
		if (super._exists(ctx, filter)) {
			String number = this._getPropertyAlias(ctx, dataBaseInfo, IFWEntityStruct.dataBase_Number) + dataBaseInfo.getNumber();
			throw new EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { number });
		}
	}

	protected void _checkNameDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		DataBaseInfo dataBaseInfo = (DataBaseInfo) model;
		SurrenderReasonInfo surrenderReasonInfo = (SurrenderReasonInfo) model;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.dataBase_Name, dataBaseInfo.getName(),CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("parent.id", surrenderReasonInfo.getParent().getId().toString()));
		if (dataBaseInfo.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.coreBase_ID,	dataBaseInfo.getId(), CompareType.NOTEQUALS));
		}
		if (getControlType(ctx, dataBaseInfo).equals("")) {
			// 合并Filter，进行CU隔离。
			FilterInfo filterCU = getFilterForDefaultCU(ctx, model);
			// if(this.getFilterForCheckNumber(model) != null &&
			// this.getFilterForCheckNumber(model).size()>0)
			if (FilterUtility.hasFilterItem(filterCU)) {
				filter.mergeFilter(filterCU, "AND");
			}
		}
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
		// @todo 由于getPKList效率很低，使用exists()替代。
		// IObjectPK[] pks = super._getPKList(ctx,filter,sorter);

		// if (pks.length > 0)
		if (super._exists(ctx, filter)) {
			String name = this._getPropertyAlias(ctx, dataBaseInfo,
					IFWEntityStruct.dataBase_Name)
					+ dataBaseInfo.getName();
			throw new EASBizException(EASBizException.CHECKDUPLICATED,
					new Object[] { name });

		}
	}
	
	private static final String SHARE_TYPE_NAME = "controlType";
	private String controlType = null;
	private String getControlType(Context ctx, DataBaseInfo info)
			throws BOSException {
		if (controlType != null)
			return controlType;
		controlType = "";
		// QueryInfo qi=getTempQueryInfo();
		// EntityObjectInfo
		// eoi=(EntityObjectInfo)qi.getMainObject().get("entityRef");

		EntityObjectInfo eoi = getBOSEntity(ctx, info);
		boolean hasShareType = false;
		hasShareType = eoi.containsExtendedPropertyKey(SHARE_TYPE_NAME);
		if (hasShareType) {
			controlType = eoi.getExtendedProperty(SHARE_TYPE_NAME);
		} else if (eoi.getBaseEntity().getName().equals("DataBaseD")) {
			controlType = "D";
		}
		return controlType;
	}
}