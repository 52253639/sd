package com.kingdee.eas.fdc.invite.supplier;

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
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class FDCSupplierServiceType extends CoreBillEntryBase implements IFDCSupplierServiceType
{
    public FDCSupplierServiceType()
    {
        super();
        registerInterface(IFDCSupplierServiceType.class, this);
    }
    public FDCSupplierServiceType(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCSupplierServiceType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("591DD2B6");
    }
    private FDCSupplierServiceTypeController getController() throws BOSException
    {
        return (FDCSupplierServiceTypeController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCSupplierServiceTypeInfo getFDCSupplierServiceTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSupplierServiceTypeInfo(getContext(), pk);
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
    public FDCSupplierServiceTypeInfo getFDCSupplierServiceTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSupplierServiceTypeInfo(getContext(), pk, selector);
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
    public FDCSupplierServiceTypeInfo getFDCSupplierServiceTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSupplierServiceTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCSupplierServiceTypeCollection getFDCSupplierServiceTypeCollection() throws BOSException
    {
        try {
            return getController().getFDCSupplierServiceTypeCollection(getContext());
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
    public FDCSupplierServiceTypeCollection getFDCSupplierServiceTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCSupplierServiceTypeCollection(getContext(), view);
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
    public FDCSupplierServiceTypeCollection getFDCSupplierServiceTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCSupplierServiceTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}