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
import com.kingdee.bos.util.BOSUuid;

public class RoomPriceManage extends FDCBill implements IRoomPriceManage
{
    public RoomPriceManage()
    {
        super();
        registerInterface(IRoomPriceManage.class, this);
    }
    public RoomPriceManage(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomPriceManage.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("33DCA7F8");
    }
    private RoomPriceManageController getController() throws BOSException
    {
        return (RoomPriceManageController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RoomPriceManageInfo getRoomPriceManageInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPriceManageInfo(getContext(), pk);
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
    public RoomPriceManageInfo getRoomPriceManageInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPriceManageInfo(getContext(), pk, selector);
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
    public RoomPriceManageInfo getRoomPriceManageInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPriceManageInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomPriceManageCollection getRoomPriceManageCollection() throws BOSException
    {
        try {
            return getController().getRoomPriceManageCollection(getContext());
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
    public RoomPriceManageCollection getRoomPriceManageCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomPriceManageCollection(getContext(), view);
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
    public RoomPriceManageCollection getRoomPriceManageCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomPriceManageCollection(getContext(), oql);
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
    public boolean excute(String id) throws BOSException, EASBizException
    {
        try {
            return getController().excute(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *是否存在下调-User defined method
     *@param billId billId
     *@return
     */
    public boolean isExistDown(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            return getController().isExistDown(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}