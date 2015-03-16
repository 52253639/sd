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

public class BuildingRoomEntrys extends DataBase implements IBuildingRoomEntrys
{
    public BuildingRoomEntrys()
    {
        super();
        registerInterface(IBuildingRoomEntrys.class, this);
    }
    public BuildingRoomEntrys(Context ctx)
    {
        super(ctx);
        registerInterface(IBuildingRoomEntrys.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("01373A39");
    }
    private BuildingRoomEntrysController getController() throws BOSException
    {
        return (BuildingRoomEntrysController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public BuildingRoomEntrysInfo getBuildingRoomEntrysInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildingRoomEntrysInfo(getContext(), pk);
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
    public BuildingRoomEntrysInfo getBuildingRoomEntrysInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildingRoomEntrysInfo(getContext(), pk, selector);
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
    public BuildingRoomEntrysInfo getBuildingRoomEntrysInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildingRoomEntrysInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public BuildingRoomEntrysCollection getBuildingRoomEntrysCollection() throws BOSException
    {
        try {
            return getController().getBuildingRoomEntrysCollection(getContext());
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
    public BuildingRoomEntrysCollection getBuildingRoomEntrysCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBuildingRoomEntrysCollection(getContext(), view);
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
    public BuildingRoomEntrysCollection getBuildingRoomEntrysCollection(String oql) throws BOSException
    {
        try {
            return getController().getBuildingRoomEntrysCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}