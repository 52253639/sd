package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.app.*;
import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;

public class NewListingTempletDistributFacade extends AbstractBizCtrl implements INewListingTempletDistributFacade
{
    public NewListingTempletDistributFacade()
    {
        super();
        registerInterface(INewListingTempletDistributFacade.class, this);
    }
    public NewListingTempletDistributFacade(Context ctx)
    {
        super(ctx);
        registerInterface(INewListingTempletDistributFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7044C0FF");
    }
    private NewListingTempletDistributFacadeController getController() throws BOSException
    {
        return (NewListingTempletDistributFacadeController)getBizController();
    }
    /**
     *分配-User defined method
     *@param templetId 模版ID
     *@param selectOrgs 分配到的组织
     */
    public void Distribute(String templetId, FullOrgUnitCollection selectOrgs) throws BOSException, EASBizException
    {
        try {
            getController().Distribute(getContext(), templetId, selectOrgs);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}