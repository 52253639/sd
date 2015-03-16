package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;

public class ChangeNameFacade extends AbstractBizCtrl implements IChangeNameFacade
{
    public ChangeNameFacade()
    {
        super();
        registerInterface(IChangeNameFacade.class, this);
    }
    public ChangeNameFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IChangeNameFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6A7527B0");
    }
    private ChangeNameFacadeController getController() throws BOSException
    {
        return (ChangeNameFacadeController)getBizController();
    }
    /**
     *changeName-User defined method
     *@param id id
     *@param newCustomerID ÐÂ¿Í»§
     */
    public void changeName(String id, String newCustomerID) throws BOSException, EASBizException
    {
        try {
            getController().changeName(getContext(), id, newCustomerID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}