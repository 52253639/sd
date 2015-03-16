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

public class HopedUnitPrice extends FDCDataBase implements IHopedUnitPrice
{
    public HopedUnitPrice()
    {
        super();
        registerInterface(IHopedUnitPrice.class, this);
    }
    public HopedUnitPrice(Context ctx)
    {
        super(ctx);
        registerInterface(IHopedUnitPrice.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("616AEFF8");
    }
    private HopedUnitPriceController getController() throws BOSException
    {
        return (HopedUnitPriceController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public HopedUnitPriceInfo getHopedUnitPriceInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getHopedUnitPriceInfo(getContext(), pk);
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
    public HopedUnitPriceInfo getHopedUnitPriceInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getHopedUnitPriceInfo(getContext(), pk, selector);
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
    public HopedUnitPriceInfo getHopedUnitPriceInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getHopedUnitPriceInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public HopedUnitPriceCollection getHopedUnitPriceCollection() throws BOSException
    {
        try {
            return getController().getHopedUnitPriceCollection(getContext());
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
    public HopedUnitPriceCollection getHopedUnitPriceCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getHopedUnitPriceCollection(getContext(), view);
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
    public HopedUnitPriceCollection getHopedUnitPriceCollection(String oql) throws BOSException
    {
        try {
            return getController().getHopedUnitPriceCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}