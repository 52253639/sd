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

public class BuildingPriceSetEntry extends DataBase implements IBuildingPriceSetEntry
{
    public BuildingPriceSetEntry()
    {
        super();
        registerInterface(IBuildingPriceSetEntry.class, this);
    }
    public BuildingPriceSetEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IBuildingPriceSetEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B574A74A");
    }
    private BuildingPriceSetEntryController getController() throws BOSException
    {
        return (BuildingPriceSetEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public BuildingPriceSetEntryInfo getBuildingPriceSetEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildingPriceSetEntryInfo(getContext(), pk);
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
    public BuildingPriceSetEntryInfo getBuildingPriceSetEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildingPriceSetEntryInfo(getContext(), pk, selector);
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
    public BuildingPriceSetEntryInfo getBuildingPriceSetEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildingPriceSetEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public BuildingPriceSetEntryCollection getBuildingPriceSetEntryCollection() throws BOSException
    {
        try {
            return getController().getBuildingPriceSetEntryCollection(getContext());
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
    public BuildingPriceSetEntryCollection getBuildingPriceSetEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBuildingPriceSetEntryCollection(getContext(), view);
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
    public BuildingPriceSetEntryCollection getBuildingPriceSetEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getBuildingPriceSetEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}