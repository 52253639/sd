package com.kingdee.eas.fdc.sellhouse;

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
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class SincerReceiveEntry extends TranPayListEntry implements ISincerReceiveEntry
{
    public SincerReceiveEntry()
    {
        super();
        registerInterface(ISincerReceiveEntry.class, this);
    }
    public SincerReceiveEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISincerReceiveEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A0835602");
    }
    private SincerReceiveEntryController getController() throws BOSException
    {
        return (SincerReceiveEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public SincerReceiveEntryInfo getSincerReceiveEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSincerReceiveEntryInfo(getContext(), pk);
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
    public SincerReceiveEntryInfo getSincerReceiveEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSincerReceiveEntryInfo(getContext(), pk, selector);
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
    public SincerReceiveEntryInfo getSincerReceiveEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSincerReceiveEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SincerReceiveEntryCollection getSincerReceiveEntryCollection() throws BOSException
    {
        try {
            return getController().getSincerReceiveEntryCollection(getContext());
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
    public SincerReceiveEntryCollection getSincerReceiveEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSincerReceiveEntryCollection(getContext(), view);
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
    public SincerReceiveEntryCollection getSincerReceiveEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSincerReceiveEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}