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
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class ReceiveDistillCommisionEntry extends CoreBase implements IReceiveDistillCommisionEntry
{
    public ReceiveDistillCommisionEntry()
    {
        super();
        registerInterface(IReceiveDistillCommisionEntry.class, this);
    }
    public ReceiveDistillCommisionEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IReceiveDistillCommisionEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E97BCCDB");
    }
    private ReceiveDistillCommisionEntryController getController() throws BOSException
    {
        return (ReceiveDistillCommisionEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public ReceiveDistillCommisionEntryInfo getReceiveDistillCommisionEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getReceiveDistillCommisionEntryInfo(getContext(), pk);
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
    public ReceiveDistillCommisionEntryInfo getReceiveDistillCommisionEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getReceiveDistillCommisionEntryInfo(getContext(), pk, selector);
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
    public ReceiveDistillCommisionEntryInfo getReceiveDistillCommisionEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getReceiveDistillCommisionEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ReceiveDistillCommisionEntryCollection getReceiveDistillCommisionEntryCollection() throws BOSException
    {
        try {
            return getController().getReceiveDistillCommisionEntryCollection(getContext());
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
    public ReceiveDistillCommisionEntryCollection getReceiveDistillCommisionEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getReceiveDistillCommisionEntryCollection(getContext(), view);
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
    public ReceiveDistillCommisionEntryCollection getReceiveDistillCommisionEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getReceiveDistillCommisionEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}