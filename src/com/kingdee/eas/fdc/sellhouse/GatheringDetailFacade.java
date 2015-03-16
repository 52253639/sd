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

public class GatheringDetailFacade extends AbstractBizCtrl implements IGatheringDetailFacade
{
    public GatheringDetailFacade()
    {
        super();
        registerInterface(IGatheringDetailFacade.class, this);
    }
    public GatheringDetailFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IGatheringDetailFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FD2B29F7");
    }
    private GatheringDetailFacadeController getController() throws BOSException
    {
        return (GatheringDetailFacadeController)getBizController();
    }
    /**
     *获取收款单数据-User defined method
     *@param param param
     *@return
     */
    public Map getAllData(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getAllData(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}