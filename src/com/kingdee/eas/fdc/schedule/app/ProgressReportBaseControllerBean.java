package com.kingdee.eas.fdc.schedule.app;

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
import com.kingdee.eas.fdc.schedule.ProgressReportBaseCollection;
import com.kingdee.eas.fdc.schedule.ProgressReportBaseInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;

public class ProgressReportBaseControllerBean extends AbstractProgressReportBaseControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.ProgressReportBaseControllerBean");
    
    protected boolean _checkNumberBlank(Context ctx, IObjectPK pk, IObjectValue model) throws EASBizException,
    		BOSException {
    	//不校验编码为空
//    	return super._checkNumberBlank(ctx, pk, model);
    	return true;
    }
    
    protected boolean _checkNumberDup(Context ctx, IObjectPK pk, IObjectValue model) throws EASBizException,
    		BOSException {
    	//不校验名称重复
//    	return super._checkNumberDup(ctx, pk, model);
    	return true;
    }
    
    public void checkBill(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	//重写FdcBillControllerBean中的方法，不需要校验名称和编码
//    	super.checkBill(ctx, model);
    }
}