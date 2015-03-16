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

public class CommerceChanceTrack extends FDCBill implements ICommerceChanceTrack
{
    public CommerceChanceTrack()
    {
        super();
        registerInterface(ICommerceChanceTrack.class, this);
    }
    public CommerceChanceTrack(Context ctx)
    {
        super(ctx);
        registerInterface(ICommerceChanceTrack.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B0E101E1");
    }
    private CommerceChanceTrackController getController() throws BOSException
    {
        return (CommerceChanceTrackController)getBizController();
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public CommerceChanceTrackCollection getCommerceChanceTrackCollection(String oql) throws BOSException
    {
        try {
            return getController().getCommerceChanceTrackCollection(getContext(), oql);
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
    public CommerceChanceTrackCollection getCommerceChanceTrackCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCommerceChanceTrackCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public CommerceChanceTrackCollection getCommerceChanceTrackCollection() throws BOSException
    {
        try {
            return getController().getCommerceChanceTrackCollection(getContext());
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
    public CommerceChanceTrackInfo getCommerceChanceTrackInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCommerceChanceTrackInfo(getContext(), pk);
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
    public CommerceChanceTrackInfo getCommerceChanceTrackInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCommerceChanceTrackInfo(getContext(), pk, selector);
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
    public CommerceChanceTrackInfo getCommerceChanceTrackInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCommerceChanceTrackInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}