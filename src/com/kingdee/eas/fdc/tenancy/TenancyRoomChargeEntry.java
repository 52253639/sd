package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class TenancyRoomChargeEntry extends CoreBillEntryBase implements ITenancyRoomChargeEntry
{
    public TenancyRoomChargeEntry()
    {
        super();
        registerInterface(ITenancyRoomChargeEntry.class, this);
    }
    public TenancyRoomChargeEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITenancyRoomChargeEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F784700C");
    }
    private TenancyRoomChargeEntryController getController() throws BOSException
    {
        return (TenancyRoomChargeEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public TenancyRoomChargeEntryInfo getTenancyRoomChargeEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyRoomChargeEntryInfo(getContext(), pk);
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
    public TenancyRoomChargeEntryInfo getTenancyRoomChargeEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyRoomChargeEntryInfo(getContext(), pk, selector);
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
    public TenancyRoomChargeEntryInfo getTenancyRoomChargeEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyRoomChargeEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public TenancyRoomChargeEntryCollection getTenancyRoomChargeEntryCollection() throws BOSException
    {
        try {
            return getController().getTenancyRoomChargeEntryCollection(getContext());
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
    public TenancyRoomChargeEntryCollection getTenancyRoomChargeEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTenancyRoomChargeEntryCollection(getContext(), view);
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
    public TenancyRoomChargeEntryCollection getTenancyRoomChargeEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTenancyRoomChargeEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}