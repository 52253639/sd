package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ReceiveGatherEntry extends CoreBillEntryBase implements IReceiveGatherEntry
{
    public ReceiveGatherEntry()
    {
        super();
        registerInterface(IReceiveGatherEntry.class, this);
    }
    public ReceiveGatherEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IReceiveGatherEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EF155E6F");
    }
    private ReceiveGatherEntryController getController() throws BOSException
    {
        return (ReceiveGatherEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public ReceiveGatherEntryInfo getReceiveGatherEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getReceiveGatherEntryInfo(getContext(), pk);
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
    public ReceiveGatherEntryInfo getReceiveGatherEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getReceiveGatherEntryInfo(getContext(), pk, selector);
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
    public ReceiveGatherEntryInfo getReceiveGatherEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getReceiveGatherEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ReceiveGatherEntryCollection getReceiveGatherEntryCollection() throws BOSException
    {
        try {
            return getController().getReceiveGatherEntryCollection(getContext());
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
    public ReceiveGatherEntryCollection getReceiveGatherEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getReceiveGatherEntryCollection(getContext(), view);
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
    public ReceiveGatherEntryCollection getReceiveGatherEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getReceiveGatherEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}