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
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class RoomDes extends FDCDataBase implements IRoomDes
{
    public RoomDes()
    {
        super();
        registerInterface(IRoomDes.class, this);
    }
    public RoomDes(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomDes.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A00455FC");
    }
    private RoomDesController getController() throws BOSException
    {
        return (RoomDesController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public RoomDesInfo getRoomDesInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomDesInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public RoomDesInfo getRoomDesInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomDesInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public RoomDesInfo getRoomDesInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomDesInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RoomDesCollection getRoomDesCollection() throws BOSException
    {
        try {
            return getController().getRoomDesCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public RoomDesCollection getRoomDesCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomDesCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public RoomDesCollection getRoomDesCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomDesCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *清空楼栋-User defined method
     *@param building 楼栋信息
     *@return
     */
    public boolean cleanBuilding(IObjectValue building) throws BOSException, EASBizException
    {
        try {
            return getController().cleanBuilding(getContext(), building);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *房间定义特殊提交-User defined method
     *@param building 楼栋信息
     *@param Roomdes 房间定义信息
     *@param buildingUnit 单元信息
     *@return
     */
    public boolean roomDesSumbit(IObjectValue building, IObjectCollection Roomdes, IObjectCollection buildingUnit) throws BOSException, EASBizException
    {
        try {
            return getController().roomDesSumbit(getContext(), building, Roomdes, buildingUnit);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}