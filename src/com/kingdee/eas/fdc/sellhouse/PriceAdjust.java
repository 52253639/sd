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

public class PriceAdjust extends FDCBill implements IPriceAdjust
{
    public PriceAdjust()
    {
        super();
        registerInterface(IPriceAdjust.class, this);
    }
    public PriceAdjust(Context ctx)
    {
        super(ctx);
        registerInterface(IPriceAdjust.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1F8B4222");
    }
    private PriceAdjustController getController() throws BOSException
    {
        return (PriceAdjustController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public PriceAdjustInfo getPriceAdjustInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPriceAdjustInfo(getContext(), pk);
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
    public PriceAdjustInfo getPriceAdjustInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPriceAdjustInfo(getContext(), pk, selector);
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
    public PriceAdjustInfo getPriceAdjustInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPriceAdjustInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PriceAdjustCollection getPriceAdjustCollection() throws BOSException
    {
        try {
            return getController().getPriceAdjustCollection(getContext());
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
    public PriceAdjustCollection getPriceAdjustCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPriceAdjustCollection(getContext(), view);
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
    public PriceAdjustCollection getPriceAdjustCollection(String oql) throws BOSException
    {
        try {
            return getController().getPriceAdjustCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *Ö´ÐÐ-User defined method
     *@param id ID
     *@return
     */
    public boolean execute(String id) throws BOSException, EASBizException
    {
        try {
            return getController().execute(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}