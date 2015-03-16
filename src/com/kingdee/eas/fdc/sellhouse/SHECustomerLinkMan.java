package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basecrm.FDCBaseLinkMan;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basecrm.IFDCBaseLinkMan;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class SHECustomerLinkMan extends FDCBaseLinkMan implements ISHECustomerLinkMan
{
    public SHECustomerLinkMan()
    {
        super();
        registerInterface(ISHECustomerLinkMan.class, this);
    }
    public SHECustomerLinkMan(Context ctx)
    {
        super(ctx);
        registerInterface(ISHECustomerLinkMan.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4ED0F48D");
    }
    private SHECustomerLinkManController getController() throws BOSException
    {
        return (SHECustomerLinkManController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SHECustomerLinkManInfo getSHECustomerLinkManInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSHECustomerLinkManInfo(getContext(), pk);
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
    public SHECustomerLinkManInfo getSHECustomerLinkManInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSHECustomerLinkManInfo(getContext(), pk, selector);
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
    public SHECustomerLinkManInfo getSHECustomerLinkManInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSHECustomerLinkManInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SHECustomerLinkManCollection getSHECustomerLinkManCollection() throws BOSException
    {
        try {
            return getController().getSHECustomerLinkManCollection(getContext());
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
    public SHECustomerLinkManCollection getSHECustomerLinkManCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSHECustomerLinkManCollection(getContext(), view);
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
    public SHECustomerLinkManCollection getSHECustomerLinkManCollection(String oql) throws BOSException
    {
        try {
            return getController().getSHECustomerLinkManCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}