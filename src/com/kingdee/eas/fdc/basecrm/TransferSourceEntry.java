package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basecrm.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class TransferSourceEntry extends CoreBase implements ITransferSourceEntry
{
    public TransferSourceEntry()
    {
        super();
        registerInterface(ITransferSourceEntry.class, this);
    }
    public TransferSourceEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITransferSourceEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DCBE7750");
    }
    private TransferSourceEntryController getController() throws BOSException
    {
        return (TransferSourceEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public TransferSourceEntryInfo getTransferSourceEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTransferSourceEntryInfo(getContext(), pk);
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
    public TransferSourceEntryInfo getTransferSourceEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTransferSourceEntryInfo(getContext(), pk, selector);
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
    public TransferSourceEntryInfo getTransferSourceEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTransferSourceEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TransferSourceEntryCollection getTransferSourceEntryCollection() throws BOSException
    {
        try {
            return getController().getTransferSourceEntryCollection(getContext());
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
    public TransferSourceEntryCollection getTransferSourceEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTransferSourceEntryCollection(getContext(), view);
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
    public TransferSourceEntryCollection getTransferSourceEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTransferSourceEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}