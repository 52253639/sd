package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;

public class TenancyBill extends TenBillBase implements ITenancyBill
{
    public TenancyBill()
    {
        super();
        registerInterface(ITenancyBill.class, this);
    }
    public TenancyBill(Context ctx)
    {
        super(ctx);
        registerInterface(ITenancyBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7BA91DDE");
    }
    private TenancyBillController getController() throws BOSException
    {
        return (TenancyBillController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public TenancyBillInfo getTenancyBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyBillInfo(getContext(), pk);
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
    public TenancyBillInfo getTenancyBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyBillInfo(getContext(), pk, selector);
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
    public TenancyBillInfo getTenancyBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TenancyBillCollection getTenancyBillCollection() throws BOSException
    {
        try {
            return getController().getTenancyBillCollection(getContext());
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
    public TenancyBillCollection getTenancyBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTenancyBillCollection(getContext(), view);
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
    public TenancyBillCollection getTenancyBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getTenancyBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *租赁交接-User defined method
     *@param tenAttachEntryColl tenAttachEntryColl
     *@param tenancyRoomEntryColl tenancyRoomEntryColl
     *@param tenancyBillInfo tenancyBillInfo
     *@param handleRoomEntryColl handleRoomEntryColl
     */
    public void handleTenancyRoom(IObjectCollection tenAttachEntryColl, TenancyRoomEntryCollection tenancyRoomEntryColl, TenancyBillInfo tenancyBillInfo, HandleRoomEntrysCollection handleRoomEntryColl) throws BOSException
    {
        try {
            getController().handleTenancyRoom(getContext(), tenAttachEntryColl, tenancyRoomEntryColl, tenancyBillInfo, handleRoomEntryColl);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *资金结转（其实就是事物的新增多张收款单）-User defined method
     *@param receivingBills 收款单
     */
    public void carryForward(ReceivingBillCollection receivingBills) throws BOSException, EASBizException
    {
        try {
            getController().carryForward(getContext(), receivingBills);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *作废-User defined method
     *@param pk pk
     */
    public void blankOut(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().blankOut(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *补录租赁开始日期-User defined method
     *@param tenancyBillInfo tenancyBillInfo
     *@param repairStartDate 补录的租赁开始日期
     *@param firstLease 首期类型
     *@param firstLeaseDate 首期结束日期
     */
    public void repairStartDate(TenancyBillInfo tenancyBillInfo, Date repairStartDate, FirstLeaseTypeEnum firstLease, Date firstLeaseDate) throws BOSException, EASBizException
    {
        try {
            getController().repairStartDate(getContext(), tenancyBillInfo, repairStartDate, firstLease, firstLeaseDate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审批-User defined method
     *@param billID 单据编号
     */
    public void antiAudit(BOSUuid billID) throws BOSException, EASBizException
    {
        try {
            getController().antiAudit(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}