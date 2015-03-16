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

public class BuildingPriceSet extends DataBase implements IBuildingPriceSet
{
    public BuildingPriceSet()
    {
        super();
        registerInterface(IBuildingPriceSet.class, this);
    }
    public BuildingPriceSet(Context ctx)
    {
        super(ctx);
        registerInterface(IBuildingPriceSet.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B13BF608");
    }
    private BuildingPriceSetController getController() throws BOSException
    {
        return (BuildingPriceSetController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public BuildingPriceSetInfo getBuildingPriceSetInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildingPriceSetInfo(getContext(), pk, selector);
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
    public BuildingPriceSetInfo getBuildingPriceSetInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildingPriceSetInfo(getContext(), pk);
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
    public BuildingPriceSetInfo getBuildingPriceSetInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildingPriceSetInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public BuildingPriceSetCollection getBuildingPriceSetCollection() throws BOSException
    {
        try {
            return getController().getBuildingPriceSetCollection(getContext());
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
    public BuildingPriceSetCollection getBuildingPriceSetCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBuildingPriceSetCollection(getContext(), view);
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
    public BuildingPriceSetCollection getBuildingPriceSetCollection(String oql) throws BOSException
    {
        try {
            return getController().getBuildingPriceSetCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}