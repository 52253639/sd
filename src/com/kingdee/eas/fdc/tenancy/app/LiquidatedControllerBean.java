package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseControllerBean;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.app.DataBaseCodeRuleHelper;
import com.kingdee.eas.fdc.tenancy.LiquidatedCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.tenancy.LiquidatedInfo;
import com.kingdee.eas.framework.DataBaseCollection;

public class LiquidatedControllerBean extends AbstractLiquidatedControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.LiquidatedControllerBean");
    
    protected void _checkNameDup(Context ctx, IObjectValue model)
	throws BOSException, EASBizException {
//		LiquidatedInfo Liquidated = (LiquidatedInfo) model;
//		FilterInfo filter = new FilterInfo();
//		FilterItemInfo filterItem = new FilterItemInfo(
//				IFWEntityStruct.dataBase_Number, Liquidated.getNumber(),
//				CompareType.EQUALS);
//		filter.getFilterItems().add(filterItem);
//		if (Liquidated.getId() != null) {
//			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID,
//					Liquidated.getId(), CompareType.NOTEQUALS);
//			filter.getFilterItems().add(filterItem);
//		}
//		filterItem = new FilterItemInfo("sellProject.id", Liquidated
//				.getSellProject().getId().toString());
//		filter.getFilterItems().add(filterItem);
//		EntityViewInfo view = new EntityViewInfo();
//		view.setFilter(filter);
//		SorterItemCollection sorter = new SorterItemCollection();
//		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
//		
//		if (super._exists(ctx, filter)) {
//			String number = this._getPropertyAlias(ctx, Liquidated,
//					IFWEntityStruct.dataBase_Number)
//					+ Liquidated.getNumber();
//			throw new EASBizException(EASBizException.CHECKDUPLICATED,
//					new Object[] { number });
//		}
		}
		
		protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		for (int i = 0; i < arrayPK.length; i++) {
			DataBaseCodeRuleHelper.recycleNumber(ctx, (DataBaseInfo) _getValue(ctx, arrayPK[i]));
		}
		super._delete(ctx, arrayPK);
		}
		
		protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		DataBaseCodeRuleHelper.recycleNumber(ctx, (DataBaseInfo) _getValue(ctx, pk));
		super._delete(ctx, pk);
		}
		
		protected void _checkNumberDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
//		LiquidatedInfo Liquidated = (LiquidatedInfo) model;
//		FilterInfo filter = new FilterInfo();
//		FilterItemInfo filterItem = new FilterItemInfo(
//				IFWEntityStruct.dataBase_Name, Liquidated.getName(),
//				CompareType.EQUALS);
//		filter.getFilterItems().add(filterItem);
//		if (Liquidated.getId() != null) {
//			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID,
//					Liquidated.getId(), CompareType.NOTEQUALS);
//			filter.getFilterItems().add(filterItem);
//		}
//		filterItem = new FilterItemInfo("sellProject.id", Liquidated
//				.getSellProject().getId().toString());
//		filter.getFilterItems().add(filterItem);
//		EntityViewInfo view = new EntityViewInfo();
//		view.setFilter(filter);
//		SorterItemCollection sorter = new SorterItemCollection();
//		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
//		
//		if (super._exists(ctx, filter)) {
//			String name = this._getPropertyAlias(ctx, Liquidated,
//					IFWEntityStruct.dataBase_Name)
//					+ Liquidated.getName();
//			throw new EASBizException(EASBizException.CHECKDUPLICATED,
//					new Object[] { name });
//		}
		}
		
		protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		DataBaseCodeRuleHelper.handleIntermitNumber(ctx, (DataBaseInfo)model);
		return super._submit(ctx, model);
		}
}