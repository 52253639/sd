package com.kingdee.eas.fdc.sellhouse.app;

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
import com.kingdee.bos.dao.ormapping.ObjectReferedException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.sellhouse.ChangeManageFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeReasonInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomFactory;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseControllerBean;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeReasonCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.framework.util.FilterUtility;

public class ChangeReasonControllerBean extends AbstractChangeReasonControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.ChangeReasonControllerBean");
    
    protected void _isReferenced(Context arg0, IObjectPK arg1) throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("changeReason.id", arg1.toString()));
		
		if(ChangeManageFactory.getLocalInstance(arg0).exists(filter))
		{
			throw new ObjectReferedException(arg1);
		}
	}
    protected void _checkNumberDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		ChangeReasonInfo dataBaseInfo = (ChangeReasonInfo) model;
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number, dataBaseInfo.getNumber(), CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		
		filter.getFilterItems().add(new FilterItemInfo("bizType", dataBaseInfo.getBizType().getValue()));
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", dataBaseInfo.getOrgUnit().getId()));
		
		if (dataBaseInfo.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, dataBaseInfo.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
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
    
    protected void _checkNameDup(Context ctx , IObjectValue model) throws BOSException , EASBizException{
    	ChangeReasonInfo dataBaseInfo = (ChangeReasonInfo) model;
	    FilterInfo filter = new FilterInfo();
	    FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Name ,dataBaseInfo.getName() ,CompareType.EQUALS);
	    filter.getFilterItems().add(filterItem);
	    
	    filter.getFilterItems().add(new FilterItemInfo("bizType", dataBaseInfo.getBizType().getValue()));
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", dataBaseInfo.getOrgUnit().getId()));
		
	    if (dataBaseInfo.getId() != null){
	        filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID ,dataBaseInfo.getId(),CompareType.NOTEQUALS);
	        filter.getFilterItems().add(filterItem);
	    }
	    EntityViewInfo view = new EntityViewInfo();
	    view.setFilter(filter);
	    SorterItemCollection sorter = new SorterItemCollection();
	    sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
	    if (super._exists(ctx,filter)){
	        String name = this._getPropertyAlias(ctx,dataBaseInfo,IFWEntityStruct.dataBase_Name) + dataBaseInfo.getName();
	        throw new EASBizException(EASBizException.CHECKDUPLICATED ,new Object[] { name } );
	    }
	}
}