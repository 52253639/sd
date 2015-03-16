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

public class OldIncRentEntry extends CoreBillEntryBase implements IOldIncRentEntry
{
    public OldIncRentEntry()
    {
        super();
        registerInterface(IOldIncRentEntry.class, this);
    }
    public OldIncRentEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IOldIncRentEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("379D0179");
    }
    private OldIncRentEntryController getController() throws BOSException
    {
        return (OldIncRentEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public OldIncRentEntryInfo getOldIncRentEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOldIncRentEntryInfo(getContext(), pk);
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
    public OldIncRentEntryInfo getOldIncRentEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOldIncRentEntryInfo(getContext(), pk, selector);
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
    public OldIncRentEntryInfo getOldIncRentEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOldIncRentEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public OldIncRentEntryCollection getOldIncRentEntryCollection() throws BOSException
    {
        try {
            return getController().getOldIncRentEntryCollection(getContext());
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
    public OldIncRentEntryCollection getOldIncRentEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOldIncRentEntryCollection(getContext(), view);
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
    public OldIncRentEntryCollection getOldIncRentEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getOldIncRentEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}