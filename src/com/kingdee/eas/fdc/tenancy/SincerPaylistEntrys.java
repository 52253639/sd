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
import com.kingdee.eas.fdc.basecrm.IRevList;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basecrm.RevList;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class SincerPaylistEntrys extends RevList implements ISincerPaylistEntrys
{
    public SincerPaylistEntrys()
    {
        super();
        registerInterface(ISincerPaylistEntrys.class, this);
    }
    public SincerPaylistEntrys(Context ctx)
    {
        super(ctx);
        registerInterface(ISincerPaylistEntrys.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9D1F2546");
    }
    private SincerPaylistEntrysController getController() throws BOSException
    {
        return (SincerPaylistEntrysController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public SincerPaylistEntrysInfo getSincerPaylistEntrysInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSincerPaylistEntrysInfo(getContext(), pk);
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
    public SincerPaylistEntrysInfo getSincerPaylistEntrysInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSincerPaylistEntrysInfo(getContext(), pk, selector);
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
    public SincerPaylistEntrysInfo getSincerPaylistEntrysInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSincerPaylistEntrysInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SincerPaylistEntrysCollection getSincerPaylistEntrysCollection() throws BOSException
    {
        try {
            return getController().getSincerPaylistEntrysCollection(getContext());
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
    public SincerPaylistEntrysCollection getSincerPaylistEntrysCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSincerPaylistEntrysCollection(getContext(), view);
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
    public SincerPaylistEntrysCollection getSincerPaylistEntrysCollection(String oql) throws BOSException
    {
        try {
            return getController().getSincerPaylistEntrysCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}