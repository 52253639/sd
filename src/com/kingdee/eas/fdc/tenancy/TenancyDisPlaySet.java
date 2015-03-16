package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class TenancyDisPlaySet extends DataBase implements ITenancyDisPlaySet
{
    public TenancyDisPlaySet()
    {
        super();
        registerInterface(ITenancyDisPlaySet.class, this);
    }
    public TenancyDisPlaySet(Context ctx)
    {
        super(ctx);
        registerInterface(ITenancyDisPlaySet.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("01ADF0B7");
    }
    private TenancyDisPlaySetController getController() throws BOSException
    {
        return (TenancyDisPlaySetController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public TenancyDisPlaySetInfo getTenancyDisPlaySetInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyDisPlaySetInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public TenancyDisPlaySetInfo getTenancyDisPlaySetInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyDisPlaySetInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql oql
     *@return
     */
    public TenancyDisPlaySetInfo getTenancyDisPlaySetInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyDisPlaySetInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TenancyDisPlaySetCollection getTenancyDisPlaySetCollection() throws BOSException
    {
        try {
            return getController().getTenancyDisPlaySetCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view view
     *@return
     */
    public TenancyDisPlaySetCollection getTenancyDisPlaySetCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTenancyDisPlaySetCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql oql
     *@return
     */
    public TenancyDisPlaySetCollection getTenancyDisPlaySetCollection(String oql) throws BOSException
    {
        try {
            return getController().getTenancyDisPlaySetCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}