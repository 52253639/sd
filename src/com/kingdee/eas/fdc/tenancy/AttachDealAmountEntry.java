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

public class AttachDealAmountEntry extends CoreBillEntryBase implements IAttachDealAmountEntry
{
    public AttachDealAmountEntry()
    {
        super();
        registerInterface(IAttachDealAmountEntry.class, this);
    }
    public AttachDealAmountEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IAttachDealAmountEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("44C627E0");
    }
    private AttachDealAmountEntryController getController() throws BOSException
    {
        return (AttachDealAmountEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public AttachDealAmountEntryInfo getAttachDealAmountEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachDealAmountEntryInfo(getContext(), pk);
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
    public AttachDealAmountEntryInfo getAttachDealAmountEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachDealAmountEntryInfo(getContext(), pk, selector);
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
    public AttachDealAmountEntryInfo getAttachDealAmountEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachDealAmountEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public AttachDealAmountEntryCollection getAttachDealAmountEntryCollection() throws BOSException
    {
        try {
            return getController().getAttachDealAmountEntryCollection(getContext());
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
    public AttachDealAmountEntryCollection getAttachDealAmountEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAttachDealAmountEntryCollection(getContext(), view);
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
    public AttachDealAmountEntryCollection getAttachDealAmountEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getAttachDealAmountEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}