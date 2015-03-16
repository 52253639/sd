package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ObjectBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IObjectBase;

public class MoneyDefineReverseEntry extends ObjectBase implements IMoneyDefineReverseEntry
{
    public MoneyDefineReverseEntry()
    {
        super();
        registerInterface(IMoneyDefineReverseEntry.class, this);
    }
    public MoneyDefineReverseEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IMoneyDefineReverseEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E3A1EFB0");
    }
    private MoneyDefineReverseEntryController getController() throws BOSException
    {
        return (MoneyDefineReverseEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MoneyDefineReverseEntryInfo getMoneyDefineReverseEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMoneyDefineReverseEntryInfo(getContext(), pk);
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
    public MoneyDefineReverseEntryInfo getMoneyDefineReverseEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMoneyDefineReverseEntryInfo(getContext(), pk, selector);
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
    public MoneyDefineReverseEntryInfo getMoneyDefineReverseEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMoneyDefineReverseEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MoneyDefineReverseEntryCollection getMoneyDefineReverseEntryCollection() throws BOSException
    {
        try {
            return getController().getMoneyDefineReverseEntryCollection(getContext());
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
    public MoneyDefineReverseEntryCollection getMoneyDefineReverseEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMoneyDefineReverseEntryCollection(getContext(), view);
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
    public MoneyDefineReverseEntryCollection getMoneyDefineReverseEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getMoneyDefineReverseEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}