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

public class OldAttachDealAmountEntry extends CoreBillEntryBase implements IOldAttachDealAmountEntry
{
    public OldAttachDealAmountEntry()
    {
        super();
        registerInterface(IOldAttachDealAmountEntry.class, this);
    }
    public OldAttachDealAmountEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IOldAttachDealAmountEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("71EA5CAB");
    }
    private OldAttachDealAmountEntryController getController() throws BOSException
    {
        return (OldAttachDealAmountEntryController)getBizController();
    }
    /**
     *getCollection-System defined method
     *@param pk pk
     *@return
     */
    public OldAttachDealAmountEntryInfo getOldAttachDealAmountEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOldAttachDealAmountEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public OldAttachDealAmountEntryInfo getOldAttachDealAmountEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOldAttachDealAmountEntryInfo(getContext(), pk, selector);
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
    public OldAttachDealAmountEntryInfo getOldAttachDealAmountEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOldAttachDealAmountEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public OldAttachDealAmountEntryCollection getOldAttachDealAmountEntryCollection() throws BOSException
    {
        try {
            return getController().getOldAttachDealAmountEntryCollection(getContext());
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
    public OldAttachDealAmountEntryCollection getOldAttachDealAmountEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOldAttachDealAmountEntryCollection(getContext(), view);
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
    public OldAttachDealAmountEntryCollection getOldAttachDealAmountEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getOldAttachDealAmountEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}