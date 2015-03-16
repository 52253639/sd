package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class BuyHouseReason extends FDCDataBase implements IBuyHouseReason
{
    public BuyHouseReason()
    {
        super();
        registerInterface(IBuyHouseReason.class, this);
    }
    public BuyHouseReason(Context ctx)
    {
        super(ctx);
        registerInterface(IBuyHouseReason.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("173F32B9");
    }
    private BuyHouseReasonController getController() throws BOSException
    {
        return (BuyHouseReasonController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public BuyHouseReasonInfo getBuyHouseReasonInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBuyHouseReasonInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public BuyHouseReasonInfo getBuyHouseReasonInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBuyHouseReasonInfo(getContext(), pk);
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
    public BuyHouseReasonInfo getBuyHouseReasonInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBuyHouseReasonInfo(getContext(), oql);
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
    public BuyHouseReasonCollection getBuyHouseReasonCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBuyHouseReasonCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public BuyHouseReasonCollection getBuyHouseReasonCollection() throws BOSException
    {
        try {
            return getController().getBuyHouseReasonCollection(getContext());
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
    public BuyHouseReasonCollection getBuyHouseReasonCollection(String oql) throws BOSException
    {
        try {
            return getController().getBuyHouseReasonCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}