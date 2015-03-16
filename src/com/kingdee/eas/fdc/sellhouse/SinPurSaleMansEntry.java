package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class SinPurSaleMansEntry extends TranSaleManEntry implements ISinPurSaleMansEntry
{
    public SinPurSaleMansEntry()
    {
        super();
        registerInterface(ISinPurSaleMansEntry.class, this);
    }
    public SinPurSaleMansEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISinPurSaleMansEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C6EF0942");
    }
    private SinPurSaleMansEntryController getController() throws BOSException
    {
        return (SinPurSaleMansEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SinPurSaleMansEntryInfo getSinPurSaleMansEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSinPurSaleMansEntryInfo(getContext(), pk);
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
    public SinPurSaleMansEntryInfo getSinPurSaleMansEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSinPurSaleMansEntryInfo(getContext(), pk, selector);
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
    public SinPurSaleMansEntryInfo getSinPurSaleMansEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSinPurSaleMansEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SinPurSaleMansEntryCollection getSinPurSaleMansEntryCollection() throws BOSException
    {
        try {
            return getController().getSinPurSaleMansEntryCollection(getContext());
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
    public SinPurSaleMansEntryCollection getSinPurSaleMansEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSinPurSaleMansEntryCollection(getContext(), oql);
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
    public SinPurSaleMansEntryCollection getSinPurSaleMansEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSinPurSaleMansEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}