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
import java.util.Set;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class SellOrder extends FDCDataBase implements ISellOrder
{
    public SellOrder()
    {
        super();
        registerInterface(ISellOrder.class, this);
    }
    public SellOrder(Context ctx)
    {
        super(ctx);
        registerInterface(ISellOrder.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("95F8AAA1");
    }
    private SellOrderController getController() throws BOSException
    {
        return (SellOrderController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SellOrderInfo getSellOrderInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSellOrderInfo(getContext(), pk);
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
    public SellOrderInfo getSellOrderInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSellOrderInfo(getContext(), pk, selector);
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
    public SellOrderInfo getSellOrderInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSellOrderInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SellOrderCollection getSellOrderCollection() throws BOSException
    {
        try {
            return getController().getSellOrderCollection(getContext());
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
    public SellOrderCollection getSellOrderCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSellOrderCollection(getContext(), view);
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
    public SellOrderCollection getSellOrderCollection(String oql) throws BOSException
    {
        try {
            return getController().getSellOrderCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *≥∑≈Ã-User defined method
     *@param quitRoomIds quitRoomIds
     */
    public void quitOrder(Set quitRoomIds) throws BOSException, EASBizException
    {
        try {
            getController().quitOrder(getContext(), quitRoomIds);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}