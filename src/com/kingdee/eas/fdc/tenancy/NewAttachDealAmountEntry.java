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

public class NewAttachDealAmountEntry extends CoreBillEntryBase implements INewAttachDealAmountEntry
{
    public NewAttachDealAmountEntry()
    {
        super();
        registerInterface(INewAttachDealAmountEntry.class, this);
    }
    public NewAttachDealAmountEntry(Context ctx)
    {
        super(ctx);
        registerInterface(INewAttachDealAmountEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6A7A76D2");
    }
    private NewAttachDealAmountEntryController getController() throws BOSException
    {
        return (NewAttachDealAmountEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public NewAttachDealAmountEntryInfo getNewAttachDealAmountEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getNewAttachDealAmountEntryInfo(getContext(), pk);
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
    public NewAttachDealAmountEntryInfo getNewAttachDealAmountEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getNewAttachDealAmountEntryInfo(getContext(), pk, selector);
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
    public NewAttachDealAmountEntryInfo getNewAttachDealAmountEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getNewAttachDealAmountEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public NewAttachDealAmountEntryCollection getNewAttachDealAmountEntryCollection() throws BOSException
    {
        try {
            return getController().getNewAttachDealAmountEntryCollection(getContext());
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
    public NewAttachDealAmountEntryCollection getNewAttachDealAmountEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getNewAttachDealAmountEntryCollection(getContext(), view);
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
    public NewAttachDealAmountEntryCollection getNewAttachDealAmountEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getNewAttachDealAmountEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}