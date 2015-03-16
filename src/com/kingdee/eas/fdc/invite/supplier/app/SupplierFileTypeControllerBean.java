package com.kingdee.eas.fdc.invite.supplier.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
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
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseControllerBean;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeManageFactory;
import com.kingdee.util.NumericExceptionSubItem;

public class SupplierFileTypeControllerBean extends AbstractSupplierFileTypeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.SupplierFileTypeControllerBean");
    
    protected void _isReferenced(Context arg0, IObjectPK arg1) throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("supplierFileType.id", arg1.toString()));
		if(SupplierAttachListFactory.getLocalInstance(arg0).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","已经被档案附件清单引用，不能进行删除操作！"));
		}
		if(SupplierPersonFactory.getLocalInstance(arg0).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","已经被职员构成引用，不能进行删除操作！"));
		}
		if(SupplierStockFactory.getLocalInstance(arg0).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","已经被供应商档案登记引用，不能进行删除操作！"));
		}
	}

	protected void _updatePartial(Context ctx, IObjectValue model, SelectorItemCollection selector) throws BOSException, EASBizException {
		FDCDataBaseInfo info=(FDCDataBaseInfo)model;
		if(selector.contains(new SelectorItemInfo("isEnabled"))&&!info.isIsEnabled()){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("supplierFileType.id", info.getId().toString()));
			if(SupplierAttachListFactory.getLocalInstance(ctx).exists(filter)){
				throw new EASBizException(new NumericExceptionSubItem("100","已经被档案附件清单引用，不能进行禁用操作！"));
			}
			if(SupplierPersonFactory.getLocalInstance(ctx).exists(filter)){
				throw new EASBizException(new NumericExceptionSubItem("100","已经被职员构成引用，不能进行禁用操作！"));
			}
			if(SupplierStockFactory.getLocalInstance(ctx).exists(filter)){
				throw new EASBizException(new NumericExceptionSubItem("100","已经被供应商档案登记引用，不能进行禁用操作！"));
			}
		}
		super._updatePartial(ctx, model, selector);
	}
}