package com.kingdee.eas.fdc.schedule.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
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
import com.kingdee.eas.framework.CoreBillEntryBaseCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.schedule.TaskMaterialItemEntryCollection;
import com.kingdee.eas.framework.app.CoreBillEntryBaseControllerBean;
import com.kingdee.eas.fdc.schedule.TaskMaterialItemEntryInfo;

public class TaskMaterialItemEntryControllerBean extends AbstractTaskMaterialItemEntryControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.TaskMaterialItemEntryControllerBean");

	protected void _audit(Context ctx, IObjectPK itemPK) throws BOSException,
			EASBizException {
		TaskMaterialItemEntryInfo itemInfo = new TaskMaterialItemEntryInfo();
		itemInfo.setId(BOSUuid.read(itemPK.toString()));
		itemInfo.setState(FDCBillStateEnum.AUDITTED);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("state");
		_updatePartial(ctx, itemInfo, sic);
	}

	protected void _unAudit(Context ctx, IObjectPK itemPK) throws BOSException,
			EASBizException {
		TaskMaterialItemEntryInfo itemInfo = new TaskMaterialItemEntryInfo();
		itemInfo.setId(BOSUuid.read(itemPK.toString()));
		itemInfo.setState(FDCBillStateEnum.SUBMITTED);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("state");
		_updatePartial(ctx, itemInfo, sic);
	}
}