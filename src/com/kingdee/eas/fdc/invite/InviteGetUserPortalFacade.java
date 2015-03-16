package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.app.*;
import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;

public class InviteGetUserPortalFacade extends AbstractBizCtrl implements IInviteGetUserPortalFacade
{
    public InviteGetUserPortalFacade()
    {
        super();
        registerInterface(IInviteGetUserPortalFacade.class, this);
    }
    public InviteGetUserPortalFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IInviteGetUserPortalFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("78B5C29A");
    }
    private InviteGetUserPortalFacadeController getController() throws BOSException
    {
        return (InviteGetUserPortalFacadeController)getBizController();
    }
    /**
     *获取用户的portal-User defined method
     *@param clientIP clientIP
     *@return
     */
    public String getUserPortal(String clientIP) throws BOSException, InviteProjectException
    {
        try {
            return getController().getUserPortal(getContext(), clientIP);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}