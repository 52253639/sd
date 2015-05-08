package com.kingdee.eas.fdc.schedule.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.framework.IFWEntityStruct;

public class ProjectSpecialControllerBean extends AbstractProjectSpecialControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.ProjectSpecialControllerBean");

	/**
	 * @param ctx
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void _checkNumberDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		ProjectSpecialInfo dataBaseInfo = (ProjectSpecialInfo) model;
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number, dataBaseInfo.getNumber(), CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (dataBaseInfo.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, dataBaseInfo.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
			// filter.setMaskString("#1 and #2");
		}
		if (dataBaseInfo.getCurProject() != null) {
			filterItem = new FilterItemInfo("curProject", dataBaseInfo.getCurProject().getId().toString());
			filter.getFilterItems().add(filterItem);
		}
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
		// @todo 由于getPKList效率很低，使用exists()替代。
		// IObjectPK[] pks = super._getPKList(ctx,filter,sorter);

		// if(pks.length>0)
		if (super._exists(ctx, filter)) {
			String number = this._getPropertyAlias(ctx, dataBaseInfo, IFWEntityStruct.dataBase_Number) + dataBaseInfo.getNumber();
			throw new EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { number });
		}
	}
	/**
	 * @param ctx
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void _checkNameDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		ProjectSpecialInfo dataBaseInfo = (ProjectSpecialInfo) model;
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Name, dataBaseInfo.getName(), CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (dataBaseInfo.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, dataBaseInfo.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
			// filter.setMaskString("#1 and #2");
		}
		if (dataBaseInfo.getCurProject() != null) {
			filterItem = new FilterItemInfo("curProject", dataBaseInfo.getCurProject().getId().toString());
			filter.getFilterItems().add(filterItem);
		}
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
		// @todo 由于getPKList效率很低，使用exists()替代。
		// IObjectPK[] pks = super._getPKList(ctx,filter,sorter);

		// if(pks.length>0)
		if (super._exists(ctx, filter)) {
			String name = this._getPropertyAlias(ctx, dataBaseInfo, IFWEntityStruct.dataBase_Name) + dataBaseInfo.getName();
			throw new EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { name });
		}
	}
}