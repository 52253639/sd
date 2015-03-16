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

public class PrePurchaseDetailFacade extends AbstractBizCtrl implements IPrePurchaseDetailFacade
{
    public PrePurchaseDetailFacade()
    {
        super();
        registerInterface(IPrePurchaseDetailFacade.class, this);
    }
    public PrePurchaseDetailFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IPrePurchaseDetailFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("CA049DF4");
    }
    private PrePurchaseDetailFacadeController getController() throws BOSException
    {
        return (PrePurchaseDetailFacadeController)getBizController();
    }
    /**
     *获得预定数据-User defined method
     *@param map 过滤添加集合
     *@return
     */
    public Map getPrePurchaseData(Map map) throws BOSException, EASBizException
    {
        try {
            return getController().getPrePurchaseData(getContext(), map);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}