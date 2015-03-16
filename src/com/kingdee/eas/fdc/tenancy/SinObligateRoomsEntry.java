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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class SinObligateRoomsEntry extends CoreBillBase implements ISinObligateRoomsEntry
{
    public SinObligateRoomsEntry()
    {
        super();
        registerInterface(ISinObligateRoomsEntry.class, this);
    }
    public SinObligateRoomsEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISinObligateRoomsEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4977CB04");
    }
    private SinObligateRoomsEntryController getController() throws BOSException
    {
        return (SinObligateRoomsEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public SinObligateRoomsEntryInfo getSinObligateRoomsEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSinObligateRoomsEntryInfo(getContext(), pk);
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
    public SinObligateRoomsEntryInfo getSinObligateRoomsEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSinObligateRoomsEntryInfo(getContext(), pk, selector);
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
    public SinObligateRoomsEntryInfo getSinObligateRoomsEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSinObligateRoomsEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SinObligateRoomsEntryCollection getSinObligateRoomsEntryCollection() throws BOSException
    {
        try {
            return getController().getSinObligateRoomsEntryCollection(getContext());
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
    public SinObligateRoomsEntryCollection getSinObligateRoomsEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSinObligateRoomsEntryCollection(getContext(), view);
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
    public SinObligateRoomsEntryCollection getSinObligateRoomsEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSinObligateRoomsEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}