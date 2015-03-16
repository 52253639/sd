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
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;
import java.util.List;

public class Room extends FDCDataBase implements IRoom
{
    public Room()
    {
        super();
        registerInterface(IRoom.class, this);
    }
    public Room(Context ctx)
    {
        super(ctx);
        registerInterface(IRoom.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("903E0236");
    }
    private RoomController getController() throws BOSException
    {
        return (RoomController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public RoomInfo getRoomInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public RoomInfo getRoomInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomInfo(getContext(), pk);
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
    public RoomInfo getRoomInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RoomCollection getRoomCollection() throws BOSException
    {
        try {
            return getController().getRoomCollection(getContext());
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
    public RoomCollection getRoomCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomCollection(getContext(), oql);
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
    public RoomCollection getRoomCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *售前审核-User defined method
     *@param idList 主键IDStirng形式的集合
     */
    public void doAreaAudit(List idList) throws BOSException
    {
        try {
            getController().doAreaAudit(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *实测面积复核-User defined method
     *@param idList 主键IDStirng形式的集合
     */
    public void doActualAreaAudit(List idList) throws BOSException
    {
        try {
            getController().doActualAreaAudit(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *底价复核-User defined method
     *@param idList idList
     */
    public void doBasePriceAudit(List idList) throws BOSException
    {
        try {
            getController().doBasePriceAudit(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *回收 房间-User defined method
     *@param id ID
     */
    public void reclaimRoom(String id) throws BOSException, EASBizException
    {
        try {
            getController().reclaimRoom(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *一次性放回房间的多个信息集合-User defined method
     *@param roomInfo 房间Info
     *@param collInfoNames 要返回的信息集合名称
     *@return
     */
    public Map getRoomInfoCollectionMap(RoomInfo roomInfo, String collInfoNames) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomInfoCollectionMap(getContext(), roomInfo, collInfoNames);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *批改房间资料-User defined method
     *@param idList idList
     *@param map map
     */
    public void roomIpdateBatch(List idList, Map map) throws BOSException
    {
        try {
            getController().roomIpdateBatch(getContext(), idList, map);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *新增房间面积变更历史-User defined method
     *@param idList idList
     *@param type 业务类型
     */
    public void addRoomAreaChange(List idList, RoomAreaChangeTypeEnum type) throws BOSException, EASBizException
    {
        try {
            getController().addRoomAreaChange(getContext(), idList, type);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新房间的面积信息-User defined method
     *@param roomList roomList
     */
    public void updateAreaInfo(List roomList) throws BOSException, EASBizException
    {
        try {
            getController().updateAreaInfo(getContext(), roomList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *预估复核-User defined method
     *@param roomIdList roomIdList
     */
    public void planAudit(List roomIdList) throws BOSException, EASBizException
    {
        try {
            getController().planAudit(getContext(), roomIdList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *预估反复核-User defined method
     *@param roomIdList roomIdList
     */
    public void planUnAudit(List roomIdList) throws BOSException, EASBizException
    {
        try {
            getController().planUnAudit(getContext(), roomIdList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *预售复核-User defined method
     *@param roomIdList roomIdList
     */
    public void preAudit(List roomIdList) throws BOSException, EASBizException
    {
        try {
            getController().preAudit(getContext(), roomIdList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *预售反复核-User defined method
     *@param roomIdList roomIdList
     */
    public void preUnAudit(List roomIdList) throws BOSException, EASBizException
    {
        try {
            getController().preUnAudit(getContext(), roomIdList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *实测复核-User defined method
     *@param roomIdList roomIdList
     */
    public void actAudit(List roomIdList) throws BOSException, EASBizException
    {
        try {
            getController().actAudit(getContext(), roomIdList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *实测反复核-User defined method
     *@param roomIdList roomIdList
     */
    public void actUnAudit(List roomIdList) throws BOSException, EASBizException
    {
        try {
            getController().actUnAudit(getContext(), roomIdList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}