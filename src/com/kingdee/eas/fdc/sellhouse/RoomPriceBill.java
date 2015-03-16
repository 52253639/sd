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

public class RoomPriceBill extends FDCBill implements IRoomPriceBill
{
    public RoomPriceBill()
    {
        super();
        registerInterface(IRoomPriceBill.class, this);
    }
    public RoomPriceBill(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomPriceBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E897B75A");
    }
    private RoomPriceBillController getController() throws BOSException
    {
        return (RoomPriceBillController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public RoomPriceBillInfo getRoomPriceBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPriceBillInfo(getContext(), pk);
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
    public RoomPriceBillInfo getRoomPriceBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPriceBillInfo(getContext(), pk, selector);
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
    public RoomPriceBillInfo getRoomPriceBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPriceBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RoomPriceBillCollection getRoomPriceBillCollection() throws BOSException
    {
        try {
            return getController().getRoomPriceBillCollection(getContext());
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
    public RoomPriceBillCollection getRoomPriceBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomPriceBillCollection(getContext(), view);
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
    public RoomPriceBillCollection getRoomPriceBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomPriceBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *执行-User defined method
     *@param id ID
     *@return
     */
    public boolean execute(String id) throws BOSException, EASBizException
    {
        try {
            return getController().execute(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *updateIsCalByRoomArea-User defined method
     *@param module module
     */
    public void updateIsCalByRoomArea(RoomPriceBillEntryInfo module) throws BOSException, EASBizException
    {
        try {
            getController().updateIsCalByRoomArea(getContext(), module);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}