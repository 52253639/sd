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
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class RoomJoin extends FDCBill implements IRoomJoin
{
    public RoomJoin()
    {
        super();
        registerInterface(IRoomJoin.class, this);
    }
    public RoomJoin(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomJoin.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("60894880");
    }
    private RoomJoinController getController() throws BOSException
    {
        return (RoomJoinController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public RoomJoinInfo getRoomJoinInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomJoinInfo(getContext(), pk);
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
    public RoomJoinInfo getRoomJoinInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomJoinInfo(getContext(), pk, selector);
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
    public RoomJoinInfo getRoomJoinInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomJoinInfo(getContext(), oql);
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
    public RoomJoinCollection getRoomJoinCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomJoinCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RoomJoinCollection getRoomJoinCollection() throws BOSException
    {
        try {
            return getController().getRoomJoinCollection(getContext());
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
    public RoomJoinCollection getRoomJoinCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomJoinCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *批量保存（已存在的记录就更新，不存在的直接新增）-User defined method
     *@param roomJoins 入伙集合
     */
    public void batchSave(RoomJoinCollection roomJoins) throws BOSException, EASBizException
    {
        try {
            getController().batchSave(getContext(), roomJoins);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}