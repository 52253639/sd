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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;
import java.util.List;

public class RoomLoan extends FDCBill implements IRoomLoan
{
    public RoomLoan()
    {
        super();
        registerInterface(IRoomLoan.class, this);
    }
    public RoomLoan(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomLoan.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("608A3046");
    }
    private RoomLoanController getController() throws BOSException
    {
        return (RoomLoanController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RoomLoanInfo getRoomLoanInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomLoanInfo(getContext(), pk);
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
    public RoomLoanInfo getRoomLoanInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomLoanInfo(getContext(), pk, selector);
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
    public RoomLoanInfo getRoomLoanInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomLoanInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomLoanCollection getRoomLoanCollection() throws BOSException
    {
        try {
            return getController().getRoomLoanCollection(getContext());
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
    public RoomLoanCollection getRoomLoanCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomLoanCollection(getContext(), view);
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
    public RoomLoanCollection getRoomLoanCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomLoanCollection(getContext(), oql);
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
    /**
     *查询-User defined method
     *@param filterStr 过滤条件
     *@return
     */
    public IRowSet exeQuery(String filterStr) throws BOSException
    {
        try {
            return getController().exeQuery(getContext(), filterStr);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *得到可以生成按揭或者公服务的房间信息-User defined method
     *@param projectID projectID
     *@return
     */
    public IRowSet getRoomList(String projectID) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomList(getContext(), projectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}