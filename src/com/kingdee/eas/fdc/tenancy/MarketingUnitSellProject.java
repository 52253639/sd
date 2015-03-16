package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class MarketingUnitSellProject extends DataBase implements IMarketingUnitSellProject
{
    public MarketingUnitSellProject()
    {
        super();
        registerInterface(IMarketingUnitSellProject.class, this);
    }
    public MarketingUnitSellProject(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketingUnitSellProject.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B5FA2AE6");
    }
    private MarketingUnitSellProjectController getController() throws BOSException
    {
        return (MarketingUnitSellProjectController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public MarketingUnitSellProjectInfo getMarketingUnitSellProjectInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketingUnitSellProjectInfo(getContext(), pk);
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
    public MarketingUnitSellProjectInfo getMarketingUnitSellProjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketingUnitSellProjectInfo(getContext(), pk, selector);
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
    public MarketingUnitSellProjectInfo getMarketingUnitSellProjectInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketingUnitSellProjectInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MarketingUnitSellProjectCollection getMarketingUnitSellProjectCollection() throws BOSException
    {
        try {
            return getController().getMarketingUnitSellProjectCollection(getContext());
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
    public MarketingUnitSellProjectCollection getMarketingUnitSellProjectCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketingUnitSellProjectCollection(getContext(), view);
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
    public MarketingUnitSellProjectCollection getMarketingUnitSellProjectCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketingUnitSellProjectCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}