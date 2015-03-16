package com.kingdee.eas.fdc.basecrm;

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
import com.kingdee.eas.fdc.basecrm.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class FDCReceivingBillEntry extends CoreBillEntryBase implements IFDCReceivingBillEntry
{
    public FDCReceivingBillEntry()
    {
        super();
        registerInterface(IFDCReceivingBillEntry.class, this);
    }
    public FDCReceivingBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCReceivingBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("26EEC414");
    }
    private FDCReceivingBillEntryController getController() throws BOSException
    {
        return (FDCReceivingBillEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public FDCReceivingBillEntryInfo getFDCReceivingBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCReceivingBillEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public FDCReceivingBillEntryInfo getFDCReceivingBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCReceivingBillEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql oql
     *@return
     */
    public FDCReceivingBillEntryInfo getFDCReceivingBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCReceivingBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view view
     *@return
     */
    public FDCReceivingBillEntryCollection getFDCReceivingBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCReceivingBillEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public FDCReceivingBillEntryCollection getFDCReceivingBillEntryCollection() throws BOSException
    {
        try {
            return getController().getFDCReceivingBillEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql oql
     *@return
     */
    public FDCReceivingBillEntryCollection getFDCReceivingBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCReceivingBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}