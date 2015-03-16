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

public class HopedTotalPrices extends FDCDataBase implements IHopedTotalPrices
{
    public HopedTotalPrices()
    {
        super();
        registerInterface(IHopedTotalPrices.class, this);
    }
    public HopedTotalPrices(Context ctx)
    {
        super(ctx);
        registerInterface(IHopedTotalPrices.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4AB949C1");
    }
    private HopedTotalPricesController getController() throws BOSException
    {
        return (HopedTotalPricesController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public HopedTotalPricesInfo getHopedTotalPricesInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getHopedTotalPricesInfo(getContext(), pk);
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
    public HopedTotalPricesInfo getHopedTotalPricesInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getHopedTotalPricesInfo(getContext(), pk, selector);
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
    public HopedTotalPricesInfo getHopedTotalPricesInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getHopedTotalPricesInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public HopedTotalPricesCollection getHopedTotalPricesCollection() throws BOSException
    {
        try {
            return getController().getHopedTotalPricesCollection(getContext());
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
    public HopedTotalPricesCollection getHopedTotalPricesCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getHopedTotalPricesCollection(getContext(), view);
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
    public HopedTotalPricesCollection getHopedTotalPricesCollection(String oql) throws BOSException
    {
        try {
            return getController().getHopedTotalPricesCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}