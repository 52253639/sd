package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK; //import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean; //import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.eas.framework.app.TreeBaseControllerBean;
import com.kingdee.eas.framework.util.FilterUtility;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.tenancy.NatureEnterpriseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.TreeBaseException;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.fdc.tenancy.NatureEnterpriseCollection;
import com.kingdee.eas.framework.DataBaseCollection;

public class NatureEnterpriseControllerBean extends
		AbstractNatureEnterpriseControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.tenancy.app.NatureEnterpriseControllerBean");

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		// 引用检查
		this.isReferenced(ctx, pk);
		super._delete(ctx, pk);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		IObjectPK pk = super._submit(ctx, model);
		checkname(ctx, model);
		return pk;
	}

	protected void checkname(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		TreeBaseInfo treeModel = (TreeBaseInfo) model;

		// if no parent,no need to check
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = null;
		// 父节点为空时检查根对象名称是否重复。
		if (treeModel.innerGetParent() == null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Name,
					treeModel.getName(), CompareType.EQUALS);
			filter.getFilterItems().add(filterItem);
			filter.getFilterItems().add(
					new FilterItemInfo(IFWEntityStruct.tree_Parent, null,
							CompareType.EQUALS));
			filter.setMaskString("#0 and #1");
			if (treeModel.getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID,
						treeModel.getId().toString(), CompareType.NOTEQUALS);
				filter.getFilterItems().add(filterItem);
				// filter.getFilterItems().add(new FilterItemInfo("level",new
				// Integer(treeModel.getLevel()),CompareType.EQUALS));
				// 修改，应当使用parentID，因为levle是计算生成的，不应由客户端传递。 Jacky at 2004-11-4
				filter.setMaskString("#0 and #1 and #2");
			}
		} else {
			filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Name,
					treeModel.getName(), CompareType.EQUALS);
			filter.getFilterItems().add(filterItem);
			if (treeModel.innerGetParent().getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.tree_Parent,
						treeModel.innerGetParent().getId().toString(),
						CompareType.EQUALS);
				filter.getFilterItems().add(filterItem);
				filter.setMaskString("#0 and #1");
			}
			if (treeModel.getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID,
						treeModel.getId().toString(), CompareType.NOTEQUALS);
				filter.getFilterItems().add(filterItem);
				if (treeModel.innerGetParent().getId() != null) {
					filter.setMaskString("#0 and #1 and #2");
				} else {
					filter.setMaskString("#0 and #1");
				}
			}
		}

		EntityViewInfo view = new EntityViewInfo();
		// CU隔离
		FilterInfo filterCU = getFilterForDefaultCU(ctx, treeModel);
		if (FilterUtility.hasFilterItem(filterCU)) {
			if (FilterUtility.hasFilterItem(filter)) {
				filter.mergeFilter(filterCU, "AND");
			} else {
				filter = filterCU;
			}
		}

		view.setFilter(filter);

		TreeBaseCollection results = this.getTreeBaseCollection(ctx, view);

		// DataBaseCollection results = this.getDataBaseCollection(ctx,view);

		if (results != null && results.size() > 0) {
			DataBaseInfo dataBaseInfo = (DataBaseInfo) model;
			String name = this._getPropertyAlias(ctx, dataBaseInfo,
					IFWEntityStruct.dataBase_Name)
					+ dataBaseInfo.getName();
			throw new EASBizException(EASBizException.CHECKDUPLICATED,
					new Object[] { name });
		}
	}
}