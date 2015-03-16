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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class FDCAdjustBill extends FDCBill implements IFDCAdjustBill
{
    public FDCAdjustBill()
    {
        super();
        registerInterface(IFDCAdjustBill.class, this);
    }
    public FDCAdjustBill(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCAdjustBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D24B04CC");
    }
    private FDCAdjustBillController getController() throws BOSException
    {
        return (FDCAdjustBillController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCAdjustBillInfo getFDCAdjustBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCAdjustBillInfo(getContext(), pk);
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
    public FDCAdjustBillInfo getFDCAdjustBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCAdjustBillInfo(getContext(), pk, selector);
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
    public FDCAdjustBillInfo getFDCAdjustBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCAdjustBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCAdjustBillCollection getFDCAdjustBillCollection() throws BOSException
    {
        try {
            return getController().getFDCAdjustBillCollection(getContext());
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
    public FDCAdjustBillCollection getFDCAdjustBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCAdjustBillCollection(getContext(), view);
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
    public FDCAdjustBillCollection getFDCAdjustBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCAdjustBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}