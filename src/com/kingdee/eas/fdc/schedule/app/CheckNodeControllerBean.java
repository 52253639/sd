package com.kingdee.eas.fdc.schedule.app;

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

import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.schedule.CheckNodeCollection;
import com.kingdee.eas.fdc.schedule.CheckNodeFactory;
import com.kingdee.eas.fdc.schedule.GlobalTaskNodeFactory;
import com.kingdee.eas.fdc.schedule.GlobalTaskNodeInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import java.lang.String;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseControllerBean;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.fdc.schedule.CheckNodeInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;

public class CheckNodeControllerBean extends AbstractCheckNodeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.CheckNodeControllerBean");
    /**
     * 批量删除
     */
    protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		for(int i = 0; i<arrayPK.length;i++){
			_delete( ctx, arrayPK[i]); 
		}
	}
	/**
	 * 删除当前集团管控节点，如果是分类头，则不处理
	 */
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		if (!exists(ctx, pk)) {
			return;
		}
		CheckNodeInfo checkNodeInfo = getCheckNodeInfo(ctx, pk);
//		//金地特有
//		boolean isHead = checkNodeInfo.getName() != null ? false : true;
//		//如果是分类头，则不处理
//		if(isHead){
//			return;
//		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number",checkNodeInfo.getNumber(),CompareType.EQUALS));
		CheckNodeFactory.getLocalInstance(ctx).delete(filter);		
	}
}