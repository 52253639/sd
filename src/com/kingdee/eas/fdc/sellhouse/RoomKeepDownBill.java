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
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class RoomKeepDownBill extends FDCBill implements IRoomKeepDownBill
{
    public RoomKeepDownBill()
    {
        super();
        registerInterface(IRoomKeepDownBill.class, this);
    }
    public RoomKeepDownBill(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomKeepDownBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("10008BC4");
    }
    private RoomKeepDownBillController getController() throws BOSException
    {
        return (RoomKeepDownBillController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RoomKeepDownBillInfo getRoomKeepDownBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomKeepDownBillInfo(getContext(), pk);
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
    public RoomKeepDownBillInfo getRoomKeepDownBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomKeepDownBillInfo(getContext(), pk, selector);
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
    public RoomKeepDownBillInfo getRoomKeepDownBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomKeepDownBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomKeepDownBillCollection getRoomKeepDownBillCollection() throws BOSException
    {
        try {
            return getController().getRoomKeepDownBillCollection(getContext());
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
    public RoomKeepDownBillCollection getRoomKeepDownBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomKeepDownBillCollection(getContext(), view);
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
    public RoomKeepDownBillCollection getRoomKeepDownBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomKeepDownBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取消保留-User defined method
     *@param roomId 房间String形式的主键
     *@param billId 单据ID
     */
    public void cancelKeepDown(String roomId, String billId) throws BOSException, EASBizException
    {
        try {
            getController().cancelKeepDown(getContext(), roomId, billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据当前客户，能否对房间进行转认购，转签约等操作-User defined method
     *@param roomId 房间主键ID
     *@param sheCustomer 售楼客户
     *@return
     */
    public boolean checkRoomKeepDown(String roomId, IObjectValue sheCustomer) throws BOSException, EASBizException
    {
        try {
            return getController().checkRoomKeepDown(getContext(), roomId, sheCustomer);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}