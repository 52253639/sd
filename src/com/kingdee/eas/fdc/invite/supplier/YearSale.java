package com.kingdee.eas.fdc.invite.supplier;

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
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class YearSale extends CoreBillEntryBase implements IYearSale
{
    public YearSale()
    {
        super();
        registerInterface(IYearSale.class, this);
    }
    public YearSale(Context ctx)
    {
        super(ctx);
        registerInterface(IYearSale.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A790C122");
    }
    private YearSaleController getController() throws BOSException
    {
        return (YearSaleController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public YearSaleInfo getYearSaleInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getYearSaleInfo(getContext(), pk);
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
    public YearSaleInfo getYearSaleInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getYearSaleInfo(getContext(), pk, selector);
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
    public YearSaleInfo getYearSaleInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getYearSaleInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public YearSaleCollection getYearSaleCollection() throws BOSException
    {
        try {
            return getController().getYearSaleCollection(getContext());
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
    public YearSaleCollection getYearSaleCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getYearSaleCollection(getContext(), view);
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
    public YearSaleCollection getYearSaleCollection(String oql) throws BOSException
    {
        try {
            return getController().getYearSaleCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}