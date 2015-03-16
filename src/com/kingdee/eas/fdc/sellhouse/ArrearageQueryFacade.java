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

public class ArrearageQueryFacade extends AbstractBizCtrl implements IArrearageQueryFacade
{
    public ArrearageQueryFacade()
    {
        super();
        registerInterface(IArrearageQueryFacade.class, this);
    }
    public ArrearageQueryFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IArrearageQueryFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EDA9A8E9");
    }
    private ArrearageQueryFacadeController getController() throws BOSException
    {
        return (ArrearageQueryFacadeController)getBizController();
    }
    /**
     *获取欠款明细表数据-User defined method
     *@param map 过滤条件集合
     *@return
     */
    public Map getArrearageDate(Map map) throws BOSException, EASBizException
    {
        try {
            return getController().getArrearageDate(getContext(), map);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}