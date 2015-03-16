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

public class HopedFloor extends FDCDataBase implements IHopedFloor
{
    public HopedFloor()
    {
        super();
        registerInterface(IHopedFloor.class, this);
    }
    public HopedFloor(Context ctx)
    {
        super(ctx);
        registerInterface(IHopedFloor.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C347BB5F");
    }
    private HopedFloorController getController() throws BOSException
    {
        return (HopedFloorController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public HopedFloorInfo getHopedFloorInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getHopedFloorInfo(getContext(), pk, selector);
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
    public HopedFloorInfo getHopedFloorInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getHopedFloorInfo(getContext(), pk);
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
    public HopedFloorInfo getHopedFloorInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getHopedFloorInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public HopedFloorCollection getHopedFloorCollection() throws BOSException
    {
        try {
            return getController().getHopedFloorCollection(getContext());
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
    public HopedFloorCollection getHopedFloorCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getHopedFloorCollection(getContext(), view);
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
    public HopedFloorCollection getHopedFloorCollection(String oql) throws BOSException
    {
        try {
            return getController().getHopedFloorCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}