package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;
import java.util.List;

public class RevUpFacade extends AbstractBizCtrl implements IRevUpFacade
{
    public RevUpFacade()
    {
        super();
        registerInterface(IRevUpFacade.class, this);
    }
    public RevUpFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IRevUpFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6172271D");
    }
    private RevUpFacadeController getController() throws BOSException
    {
        return (RevUpFacadeController)getBizController();
    }
    /**
     *executeSQL-User defined method
     *@param sqls sqls
     *@return
     */
    public String executeSQL(List sqls) throws BOSException
    {
        try {
            return getController().executeSQL(getContext(), sqls);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}