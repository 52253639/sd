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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.bos.framework.*;

public class TenancyModification extends TenBillBase implements ITenancyModification
{
    public TenancyModification()
    {
        super();
        registerInterface(ITenancyModification.class, this);
    }
    public TenancyModification(Context ctx)
    {
        super(ctx);
        registerInterface(ITenancyModification.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4E1D0473");
    }
    private TenancyModificationController getController() throws BOSException
    {
        return (TenancyModificationController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public TenancyModificationInfo getTenancyModificationInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyModificationInfo(getContext(), pk);
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
    public TenancyModificationInfo getTenancyModificationInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyModificationInfo(getContext(), pk, selector);
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
    public TenancyModificationInfo getTenancyModificationInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyModificationInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *get-System defined method
     *@return
     */
    public TenancyModificationCollection getTenancyModificationCollection() throws BOSException
    {
        try {
            return getController().getTenancyModificationCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *get-System defined method
     *@param view view
     *@return
     */
    public TenancyModificationCollection getTenancyModificationCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTenancyModificationCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *get-System defined method
     *@param oql oql
     *@return
     */
    public TenancyModificationCollection getTenancyModificationCollection(String oql) throws BOSException
    {
        try {
            return getController().getTenancyModificationCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *新增的递增日期必须在已经收款的最后一个租期(包括该租期)之后. -User defined method
     *@param tenBillId 合同编码
     *@param incNewDate 递增日期
     *@return
     */
    public boolean incNewAddCheck(String tenBillId, Date incNewDate) throws BOSException
    {
        try {
            return getController().incNewAddCheck(getContext(), tenBillId, incNewDate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *保证已经收款的租期对应的递增行不能修改和删除-User defined method
     *@param tenBillId 合同编码
     *@param incNewDate 递增日期
     *@return
     */
    public boolean incNewEditCheck(String tenBillId, Date incNewDate) throws BOSException
    {
        try {
            return getController().incNewEditCheck(getContext(), tenBillId, incNewDate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *新增行的免租区间必须在已经收款的最后一个租期之后.-User defined method
     *@param tenBillId 合同编码
     *@param stratDate 免租开始日期
     *@param endDate 免租结束日期
     *@return
     */
    public boolean freesNewAddCheck(String tenBillId, Date stratDate, Date endDate) throws BOSException
    {
        try {
            return getController().freesNewAddCheck(getContext(), tenBillId, stratDate, endDate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *免租行对应的租期之后(包括该租期)已经收款了就不能修改删除-User defined method
     *@param tenBillId 合同编码
     *@param startDate 免租开始日期
     *@param endDate 免租结束日期
     *@return
     */
    public boolean freesNewEditCheck(String tenBillId, Date startDate, Date endDate) throws BOSException
    {
        try {
            return getController().freesNewEditCheck(getContext(), tenBillId, startDate, endDate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据租赁合同，获取合同房间分录的付款明细的最近付款行的结束日期-User defined method
     *@param tenBillID 合同编码
     *@return
     */
    public Date getLeastPaidDate(String tenBillID) throws BOSException
    {
        try {
            return getController().getLeastPaidDate(getContext(), tenBillID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}