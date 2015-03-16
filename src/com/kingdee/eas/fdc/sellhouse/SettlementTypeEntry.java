package com.kingdee.eas.fdc.sellhouse;

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
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class SettlementTypeEntry extends CoreBillEntryBase implements ISettlementTypeEntry
{
    public SettlementTypeEntry()
    {
        super();
        registerInterface(ISettlementTypeEntry.class, this);
    }
    public SettlementTypeEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISettlementTypeEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9F3C0F74");
    }
    private SettlementTypeEntryController getController() throws BOSException
    {
        return (SettlementTypeEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SettlementTypeEntryInfo getSettlementTypeEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSettlementTypeEntryInfo(getContext(), pk);
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
    public SettlementTypeEntryInfo getSettlementTypeEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSettlementTypeEntryInfo(getContext(), pk, selector);
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
    public SettlementTypeEntryInfo getSettlementTypeEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSettlementTypeEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SettlementTypeEntryCollection getSettlementTypeEntryCollection() throws BOSException
    {
        try {
            return getController().getSettlementTypeEntryCollection(getContext());
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
    public SettlementTypeEntryCollection getSettlementTypeEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSettlementTypeEntryCollection(getContext(), view);
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
    public SettlementTypeEntryCollection getSettlementTypeEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSettlementTypeEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}