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

public class AgingScheduleFacade extends AbstractBizCtrl implements IAgingScheduleFacade
{
    public AgingScheduleFacade()
    {
        super();
        registerInterface(IAgingScheduleFacade.class, this);
    }
    public AgingScheduleFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IAgingScheduleFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F13ED472");
    }
    private AgingScheduleFacadeController getController() throws BOSException
    {
        return (AgingScheduleFacadeController)getBizController();
    }
    /**
     *获取报表所有数据-User defined method
     *@param params params
     *@return
     */
    public Map getAllRptData(Map params) throws BOSException, EASBizException
    {
        try {
            return getController().getAllRptData(getContext(), params);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}