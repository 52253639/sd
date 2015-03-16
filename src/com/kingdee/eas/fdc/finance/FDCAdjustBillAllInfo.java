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
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class FDCAdjustBillAllInfo extends CoreBase implements IFDCAdjustBillAllInfo
{
    public FDCAdjustBillAllInfo()
    {
        super();
        registerInterface(IFDCAdjustBillAllInfo.class, this);
    }
    public FDCAdjustBillAllInfo(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCAdjustBillAllInfo.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8C4D6F83");
    }
    private FDCAdjustBillAllInfoController getController() throws BOSException
    {
        return (FDCAdjustBillAllInfoController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCAdjustBillAllInfoInfo getFDCAdjustBillAllInfoInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCAdjustBillAllInfoInfo(getContext(), pk);
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
    public FDCAdjustBillAllInfoInfo getFDCAdjustBillAllInfoInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCAdjustBillAllInfoInfo(getContext(), pk, selector);
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
    public FDCAdjustBillAllInfoInfo getFDCAdjustBillAllInfoInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCAdjustBillAllInfoInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCAdjustBillAllInfoCollection getFDCAdjustBillAllInfoCollection() throws BOSException
    {
        try {
            return getController().getFDCAdjustBillAllInfoCollection(getContext());
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
    public FDCAdjustBillAllInfoCollection getFDCAdjustBillAllInfoCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCAdjustBillAllInfoCollection(getContext(), view);
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
    public FDCAdjustBillAllInfoCollection getFDCAdjustBillAllInfoCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCAdjustBillAllInfoCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}