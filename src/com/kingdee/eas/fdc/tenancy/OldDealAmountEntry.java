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

public class OldDealAmountEntry extends CoreBillEntryBase implements IOldDealAmountEntry
{
    public OldDealAmountEntry()
    {
        super();
        registerInterface(IOldDealAmountEntry.class, this);
    }
    public OldDealAmountEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IOldDealAmountEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("AED03F30");
    }
    private OldDealAmountEntryController getController() throws BOSException
    {
        return (OldDealAmountEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk pk
     *@return
     */
    public OldDealAmountEntryInfo getOldDealAmountEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOldDealAmountEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public OldDealAmountEntryInfo getOldDealAmountEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOldDealAmountEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql oql
     *@return
     */
    public OldDealAmountEntryInfo getOldDealAmountEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOldDealAmountEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@return
     */
    public OldDealAmountEntryCollection getOldDealAmountEntryCollection() throws BOSException
    {
        try {
            return getController().getOldDealAmountEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param view view
     *@return
     */
    public OldDealAmountEntryCollection getOldDealAmountEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOldDealAmountEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql oql
     *@return
     */
    public OldDealAmountEntryCollection getOldDealAmountEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getOldDealAmountEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}