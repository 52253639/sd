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
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

public class RoomPropertyBook extends FDCBill implements IRoomPropertyBook
{
    public RoomPropertyBook()
    {
        super();
        registerInterface(IRoomPropertyBook.class, this);
    }
    public RoomPropertyBook(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomPropertyBook.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("04BB90B9");
    }
    private RoomPropertyBookController getController() throws BOSException
    {
        return (RoomPropertyBookController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public RoomPropertyBookInfo getRoomPropertyBookInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPropertyBookInfo(getContext(), pk, selector);
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
    public RoomPropertyBookInfo getRoomPropertyBookInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPropertyBookInfo(getContext(), oql);
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
    public RoomPropertyBookInfo getRoomPropertyBookInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPropertyBookInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomPropertyBookCollection getRoomPropertyBookCollection() throws BOSException
    {
        try {
            return getController().getRoomPropertyBookCollection(getContext());
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
    public RoomPropertyBookCollection getRoomPropertyBookCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomPropertyBookCollection(getContext(), view);
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
    public RoomPropertyBookCollection getRoomPropertyBookCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomPropertyBookCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *批量保存（已存在的记录就更新，不存在的直接新增）-User defined method
     *@param idList 房间ID
     *@param valueMap 值map
     */
    public void batchSave(List idList, Map valueMap) throws BOSException, EASBizException
    {
        try {
            getController().batchSave(getContext(), idList, valueMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}