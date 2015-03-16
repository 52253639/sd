package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.sellhouse.app.*;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;

public class ZhuanKuanFacade extends AbstractBizCtrl implements IZhuanKuanFacade
{
    public ZhuanKuanFacade()
    {
        super();
        registerInterface(IZhuanKuanFacade.class, this);
    }
    public ZhuanKuanFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IZhuanKuanFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3F9696EA");
    }
    private ZhuanKuanFacadeController getController() throws BOSException
    {
        return (ZhuanKuanFacadeController)getBizController();
    }
    /**
     *定金转房款-User defined method
     *@param objColl 把所有的对象放到一个集合里面去
     *@param zkMap zkMap
     */
    public void zhuanFangKuan(CoreBaseCollection objColl, Map zkMap) throws BOSException, EASBizException
    {
        try {
            getController().zhuanFangKuan(getContext(), objColl, zkMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}