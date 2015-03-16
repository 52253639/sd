package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillEntryBase;
import com.kingdee.eas.framework.IBillEntryBase;

public class FIProSttBillEntry extends BillEntryBase implements IFIProSttBillEntry
{
    public FIProSttBillEntry()
    {
        super();
        registerInterface(IFIProSttBillEntry.class, this);
    }
    public FIProSttBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IFIProSttBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("949642B3");
    }
    private FIProSttBillEntryController getController() throws BOSException
    {
        return (FIProSttBillEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FIProSttBillEntryInfo getFIProSttBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFIProSttBillEntryInfo(getContext(), pk);
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
    public FIProSttBillEntryInfo getFIProSttBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFIProSttBillEntryInfo(getContext(), pk, selector);
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
    public FIProSttBillEntryInfo getFIProSttBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFIProSttBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FIProSttBillEntryCollection getFIProSttBillEntryCollection() throws BOSException
    {
        try {
            return getController().getFIProSttBillEntryCollection(getContext());
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
    public FIProSttBillEntryCollection getFIProSttBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFIProSttBillEntryCollection(getContext(), view);
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
    public FIProSttBillEntryCollection getFIProSttBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getFIProSttBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}