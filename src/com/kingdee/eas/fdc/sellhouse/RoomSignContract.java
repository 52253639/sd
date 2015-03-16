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
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class RoomSignContract extends FDCBill implements IRoomSignContract
{
    public RoomSignContract()
    {
        super();
        registerInterface(IRoomSignContract.class, this);
    }
    public RoomSignContract(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomSignContract.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EA57FB45");
    }
    private RoomSignContractController getController() throws BOSException
    {
        return (RoomSignContractController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public RoomSignContractInfo getRoomSignContractInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomSignContractInfo(getContext(), pk);
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
    public RoomSignContractInfo getRoomSignContractInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomSignContractInfo(getContext(), pk, selector);
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
    public RoomSignContractInfo getRoomSignContractInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomSignContractInfo(getContext(), oql);
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
    public RoomSignContractCollection getRoomSignContractCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomSignContractCollection(getContext(), oql);
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
    public RoomSignContractCollection getRoomSignContractCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomSignContractCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除-System defined method
     *@param filter filter
     *@return
     */
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException
    {
        try {
            return getController().delete(getContext(), filter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *备案-User defined method
     *@param pks pks
     *@param RecordDate 备案时间
     *@param contractNumber 合同号
     */
    public void onRecord(IObjectPK[] pks, Date RecordDate, String contractNumber) throws BOSException, EASBizException
    {
        try {
            getController().onRecord(getContext(), pks, RecordDate, contractNumber);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *签章-User defined method
     *@param pks pks
     *@param stampDate 签章日期
     */
    public void stamp(IObjectPK[] pks, Date stampDate) throws BOSException, EASBizException
    {
        try {
            getController().stamp(getContext(), pks, stampDate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *领取-User defined method
     *@param pks pks
     *@param pullDownDate 领取日期
     */
    public void pullDown(IObjectPK[] pks, Date pullDownDate) throws BOSException, EASBizException
    {
        try {
            getController().pullDown(getContext(), pks, pullDownDate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取消备案-User defined method
     *@param pks pks
     */
    public void unOnRecord(IObjectPK[] pks) throws BOSException, EASBizException
    {
        try {
            getController().unOnRecord(getContext(), pks);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取消签章-User defined method
     *@param pks pks
     */
    public void unStamp(IObjectPK[] pks) throws BOSException, EASBizException
    {
        try {
            getController().unStamp(getContext(), pks);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取消领取-User defined method
     *@param pks pks
     */
    public void unPullDown(IObjectPK[] pks) throws BOSException, EASBizException
    {
        try {
            getController().unPullDown(getContext(), pks);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}