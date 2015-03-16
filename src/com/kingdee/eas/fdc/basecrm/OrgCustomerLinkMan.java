package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basecrm.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class OrgCustomerLinkMan extends FDCBaseLinkMan implements IOrgCustomerLinkMan
{
    public OrgCustomerLinkMan()
    {
        super();
        registerInterface(IOrgCustomerLinkMan.class, this);
    }
    public OrgCustomerLinkMan(Context ctx)
    {
        super(ctx);
        registerInterface(IOrgCustomerLinkMan.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6855C61A");
    }
    private OrgCustomerLinkManController getController() throws BOSException
    {
        return (OrgCustomerLinkManController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public OrgCustomerLinkManInfo getOrgCustomerLinkManInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOrgCustomerLinkManInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public OrgCustomerLinkManInfo getOrgCustomerLinkManInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOrgCustomerLinkManInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public OrgCustomerLinkManInfo getOrgCustomerLinkManInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOrgCustomerLinkManInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public OrgCustomerLinkManCollection getOrgCustomerLinkManCollection() throws BOSException
    {
        try {
            return getController().getOrgCustomerLinkManCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public OrgCustomerLinkManCollection getOrgCustomerLinkManCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOrgCustomerLinkManCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public OrgCustomerLinkManCollection getOrgCustomerLinkManCollection(String oql) throws BOSException
    {
        try {
            return getController().getOrgCustomerLinkManCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}