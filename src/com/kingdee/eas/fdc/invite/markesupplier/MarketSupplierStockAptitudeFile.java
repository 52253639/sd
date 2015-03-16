package com.kingdee.eas.fdc.invite.markesupplier;

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
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.invite.markesupplier.app.*;

public class MarketSupplierStockAptitudeFile extends CoreBillEntryBase implements IMarketSupplierStockAptitudeFile
{
    public MarketSupplierStockAptitudeFile()
    {
        super();
        registerInterface(IMarketSupplierStockAptitudeFile.class, this);
    }
    public MarketSupplierStockAptitudeFile(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSupplierStockAptitudeFile.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B4D97FE2");
    }
    private MarketSupplierStockAptitudeFileController getController() throws BOSException
    {
        return (MarketSupplierStockAptitudeFileController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MarketSupplierStockAptitudeFileInfo getMarketSupplierStockAptitudeFileInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockAptitudeFileInfo(getContext(), pk);
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
    public MarketSupplierStockAptitudeFileInfo getMarketSupplierStockAptitudeFileInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockAptitudeFileInfo(getContext(), pk, selector);
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
    public MarketSupplierStockAptitudeFileInfo getMarketSupplierStockAptitudeFileInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockAptitudeFileInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MarketSupplierStockAptitudeFileCollection getMarketSupplierStockAptitudeFileCollection() throws BOSException
    {
        try {
            return getController().getMarketSupplierStockAptitudeFileCollection(getContext());
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
    public MarketSupplierStockAptitudeFileCollection getMarketSupplierStockAptitudeFileCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSupplierStockAptitudeFileCollection(getContext(), view);
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
    public MarketSupplierStockAptitudeFileCollection getMarketSupplierStockAptitudeFileCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSupplierStockAptitudeFileCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}