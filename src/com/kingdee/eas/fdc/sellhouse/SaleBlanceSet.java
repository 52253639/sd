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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class SaleBlanceSet extends DataBase implements ISaleBlanceSet
{
    public SaleBlanceSet()
    {
        super();
        registerInterface(ISaleBlanceSet.class, this);
    }
    public SaleBlanceSet(Context ctx)
    {
        super(ctx);
        registerInterface(ISaleBlanceSet.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EAEBCD87");
    }
    private SaleBlanceSetController getController() throws BOSException
    {
        return (SaleBlanceSetController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public SaleBlanceSetInfo getSaleBlanceSetInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSaleBlanceSetInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public SaleBlanceSetInfo getSaleBlanceSetInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSaleBlanceSetInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql oql
     *@return
     */
    public SaleBlanceSetInfo getSaleBlanceSetInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSaleBlanceSetInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SaleBlanceSetCollection getSaleBlanceSetCollection() throws BOSException
    {
        try {
            return getController().getSaleBlanceSetCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view view
     *@return
     */
    public SaleBlanceSetCollection getSaleBlanceSetCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSaleBlanceSetCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql oql
     *@return
     */
    public SaleBlanceSetCollection getSaleBlanceSetCollection(String oql) throws BOSException
    {
        try {
            return getController().getSaleBlanceSetCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}