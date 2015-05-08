package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;

public class FDCScheduleFacade extends AbstractBizCtrl implements IFDCScheduleFacade
{
    public FDCScheduleFacade()
    {
        super();
        registerInterface(IFDCScheduleFacade.class, this);
    }
    public FDCScheduleFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCScheduleFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("796DDBC0");
    }
    private FDCScheduleFacadeController getController() throws BOSException
    {
        return (FDCScheduleFacadeController)getBizController();
    }
    /**
     *获取查询-User defined method
     *@param billID 业务ID
     *@return
     */
    public EntityViewInfo getView(String billID) throws BOSException
    {
        try {
            return getController().getView(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}