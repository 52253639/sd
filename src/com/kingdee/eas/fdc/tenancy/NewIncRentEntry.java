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

public class NewIncRentEntry extends CoreBillEntryBase implements INewIncRentEntry
{
    public NewIncRentEntry()
    {
        super();
        registerInterface(INewIncRentEntry.class, this);
    }
    public NewIncRentEntry(Context ctx)
    {
        super(ctx);
        registerInterface(INewIncRentEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("646A8F72");
    }
    private NewIncRentEntryController getController() throws BOSException
    {
        return (NewIncRentEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public NewIncRentEntryInfo getNewIncRentEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getNewIncRentEntryInfo(getContext(), pk);
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
    public NewIncRentEntryInfo getNewIncRentEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getNewIncRentEntryInfo(getContext(), pk, selector);
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
    public NewIncRentEntryInfo getNewIncRentEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getNewIncRentEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public NewIncRentEntryCollection getNewIncRentEntryCollection() throws BOSException
    {
        try {
            return getController().getNewIncRentEntryCollection(getContext());
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
    public NewIncRentEntryCollection getNewIncRentEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getNewIncRentEntryCollection(getContext(), view);
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
    public NewIncRentEntryCollection getNewIncRentEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getNewIncRentEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}