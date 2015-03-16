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

public class DealAmountEntry extends CoreBillEntryBase implements IDealAmountEntry
{
    public DealAmountEntry()
    {
        super();
        registerInterface(IDealAmountEntry.class, this);
    }
    public DealAmountEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IDealAmountEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("185DD625");
    }
    private DealAmountEntryController getController() throws BOSException
    {
        return (DealAmountEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public DealAmountEntryInfo getDealAmountEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDealAmountEntryInfo(getContext(), pk);
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
    public DealAmountEntryInfo getDealAmountEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDealAmountEntryInfo(getContext(), pk, selector);
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
    public DealAmountEntryInfo getDealAmountEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDealAmountEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public DealAmountEntryCollection getDealAmountEntryCollection() throws BOSException
    {
        try {
            return getController().getDealAmountEntryCollection(getContext());
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
    public DealAmountEntryCollection getDealAmountEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDealAmountEntryCollection(getContext(), view);
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
    public DealAmountEntryCollection getDealAmountEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getDealAmountEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}