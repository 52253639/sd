package com.kingdee.eas.fdc.sellhouse.app;

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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseControllerBean;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.sellhouse.HopedFloorInfo;
import com.kingdee.eas.fdc.sellhouse.HopedFloorCollection;
import com.kingdee.eas.framework.DataBaseCollection;

public class HopedFloorControllerBean extends AbstractHopedFloorControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.HopedFloorControllerBean");
    
    protected void addnewCheck(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		this._checkNumberBlank(ctx, model);
		this._checkNameBlank(ctx, model);
		this._checkNumberDup(ctx, model);
//		this._checkNameDup(ctx, model);   //服务器客户端 都加了 名称重复验证。。
	}
    
    protected void updateCheck(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		this._checkNumberBlank(ctx, model);
		this._checkNameBlank(ctx, model);
		DataBaseInfo oldModel = this.getDataBaseInfo(ctx, pk);
		if (!((DataBaseInfo) model).getNumber().equals(oldModel.getNumber())) {
			this._checkNumberDup(ctx, model);
		}

//		if (!((DataBaseInfo) model).getName().equals(oldModel.getName())) {
//			this._checkNameDup(ctx, model);
//		}
	}
}