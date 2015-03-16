package com.kingdee.eas.fdc.invite.supplier.app;

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
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseDataCollection;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexGroupInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexGroupCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.basedata.app.FDCTreeBaseDataControllerBean;
import com.kingdee.eas.framework.DataBaseCollection;

public class FDCSplAuditIndexGroupControllerBean extends AbstractFDCSplAuditIndexGroupControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.FDCSplAuditIndexGroupControllerBean");
    
    protected void _checkNameDup(Context ctx, IObjectValue model)throws BOSException, EASBizException{
    	FDCSplAuditIndexGroupInfo dataBaseInfo = (FDCSplAuditIndexGroupInfo) model;
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Name, dataBaseInfo.getName(), CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (dataBaseInfo.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, dataBaseInfo.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
		}
		if(dataBaseInfo.getParent()!=null){
			filterItem = new FilterItemInfo("indexGroup.id", dataBaseInfo.getParent().getId());
			filter.getFilterItems().add(filterItem);
		}
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
		if (super._exists(ctx, filter)) {
			String name = this._getPropertyAlias(ctx, dataBaseInfo, IFWEntityStruct.dataBase_Name) + dataBaseInfo.getName();
			throw new EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { name });
		}
    }
}