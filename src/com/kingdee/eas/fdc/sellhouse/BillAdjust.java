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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class BillAdjust extends FDCBill implements IBillAdjust
{
    public BillAdjust()
    {
        super();
        registerInterface(IBillAdjust.class, this);
    }
    public BillAdjust(Context ctx)
    {
        super(ctx);
        registerInterface(IBillAdjust.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("28A7B791");
    }
    private BillAdjustController getController() throws BOSException
    {
        return (BillAdjustController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public BillAdjustInfo getBillAdjustInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBillAdjustInfo(getContext(), pk);
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
    public BillAdjustInfo getBillAdjustInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBillAdjustInfo(getContext(), pk, selector);
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
    public BillAdjustInfo getBillAdjustInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBillAdjustInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public BillAdjustCollection getBillAdjustCollection() throws BOSException
    {
        try {
            return getController().getBillAdjustCollection(getContext());
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
    public BillAdjustCollection getBillAdjustCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBillAdjustCollection(getContext(), view);
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
    public BillAdjustCollection getBillAdjustCollection(String oql) throws BOSException
    {
        try {
            return getController().getBillAdjustCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}