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

public class OtherBillEntry extends CoreBillEntryBase implements IOtherBillEntry
{
    public OtherBillEntry()
    {
        super();
        registerInterface(IOtherBillEntry.class, this);
    }
    public OtherBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IOtherBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("220E0964");
    }
    private OtherBillEntryController getController() throws BOSException
    {
        return (OtherBillEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public OtherBillEntryInfo getOtherBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherBillEntryInfo(getContext(), pk);
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
    public OtherBillEntryInfo getOtherBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherBillEntryInfo(getContext(), pk, selector);
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
    public OtherBillEntryInfo getOtherBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public OtherBillEntryCollection getOtherBillEntryCollection() throws BOSException
    {
        try {
            return getController().getOtherBillEntryCollection(getContext());
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
    public OtherBillEntryCollection getOtherBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOtherBillEntryCollection(getContext(), view);
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
    public OtherBillEntryCollection getOtherBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getOtherBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}