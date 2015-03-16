package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.List;

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
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.PriceSetSchemeCollection;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.app.DataBaseControllerBean;
import com.kingdee.eas.framework.util.FilterUtility;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.fdc.sellhouse.PriceSetSchemeInfo;

public class PriceSetSchemeControllerBean extends
		AbstractPriceSetSchemeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.PriceSetSchemeControllerBean");

	protected void _checkNameDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		PriceSetSchemeInfo set = (PriceSetSchemeInfo) model;
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(
				IFWEntityStruct.dataBase_Name, set.getName(),
				CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (set.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, set
					.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
		}
		filterItem = new FilterItemInfo("project.id", set.getProject().getId()
				.toString());
		filter.getFilterItems().add(filterItem);
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));

		if (super._exists(ctx, filter)) {
			String name = this._getPropertyAlias(ctx, set,
					IFWEntityStruct.dataBase_Name)
					+ set.getName();
			throw new EASBizException(EASBizException.CHECKDUPLICATED,
					new Object[] { name });
		}
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
		PriceSetSchemeInfo set = (PriceSetSchemeInfo) model;
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(
				IFWEntityStruct.dataBase_Number, set.getNumber(),
				CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (set.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, set
					.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
		}
		filterItem = new FilterItemInfo("project.id", set.getProject().getId()
				.toString());
		filter.getFilterItems().add(filterItem);
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));

		if (super._exists(ctx, filter)) {
			String number = this._getPropertyAlias(ctx, set,
					IFWEntityStruct.dataBase_Number)
					+ set.getNumber();
			throw new EASBizException(EASBizException.CHECKDUPLICATED,
					new Object[] { number });
		}
	}
	
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		DataBaseCodeRuleHelper.handleIntermitNumber(ctx, (DataBaseInfo)model);
		return super._submit(ctx, model);
	}

	protected void _setEnabled(Context ctx, List idList, boolean isEnabled)
			throws BOSException {
		if(idList!=null && idList.size()>0){
			try {
				String sql = "update T_SHE_PriceSetScheme set fisenabled=? where fid=?";
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
				sqlBuilder.setPrepareStatementSql(sql);
				sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);

				for (int i = 0; i < idList.size(); i++) {
					if(isEnabled){
						sqlBuilder.addParam(new Integer("1"));
					}else{
						sqlBuilder.addParam(new Integer("0"));
					}
					sqlBuilder.addParam(idList.get(i).toString());
					sqlBuilder.addBatch();
				}
				sqlBuilder.executeBatch();
			} catch (BOSException ex) {
				logger.error(ex.getMessage() + "更新定价方案失败!");
				throw new BOSException(ex.getMessage() + "更新定价方案失败!");
			}

		}
	}
}