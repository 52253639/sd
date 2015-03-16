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

public class TenancyOrderRoomEntry extends CoreBillBase implements ITenancyOrderRoomEntry
{
    public TenancyOrderRoomEntry()
    {
        super();
        registerInterface(ITenancyOrderRoomEntry.class, this);
    }
    public TenancyOrderRoomEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITenancyOrderRoomEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4D22B1C0");
    }
    private TenancyOrderRoomEntryController getController() throws BOSException
    {
        return (TenancyOrderRoomEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public TenancyOrderRoomEntryInfo getTenancyOrderRoomEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyOrderRoomEntryInfo(getContext(), pk);
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
    public TenancyOrderRoomEntryInfo getTenancyOrderRoomEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyOrderRoomEntryInfo(getContext(), pk, selector);
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
    public TenancyOrderRoomEntryInfo getTenancyOrderRoomEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyOrderRoomEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TenancyOrderRoomEntryCollection getTenancyOrderRoomEntryCollection() throws BOSException
    {
        try {
            return getController().getTenancyOrderRoomEntryCollection(getContext());
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
    public TenancyOrderRoomEntryCollection getTenancyOrderRoomEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTenancyOrderRoomEntryCollection(getContext(), view);
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
    public TenancyOrderRoomEntryCollection getTenancyOrderRoomEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTenancyOrderRoomEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}