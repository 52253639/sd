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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.ArrayList;
import com.kingdee.bos.util.BOSUuid;

public class ReceiveGather extends FDCBill implements IReceiveGather
{
    public ReceiveGather()
    {
        super();
        registerInterface(IReceiveGather.class, this);
    }
    public ReceiveGather(Context ctx)
    {
        super(ctx);
        registerInterface(IReceiveGather.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("15549BC3");
    }
    private ReceiveGatherController getController() throws BOSException
    {
        return (ReceiveGatherController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public ReceiveGatherInfo getReceiveGatherInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getReceiveGatherInfo(getContext(), pk);
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
    public ReceiveGatherInfo getReceiveGatherInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getReceiveGatherInfo(getContext(), pk, selector);
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
    public ReceiveGatherInfo getReceiveGatherInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getReceiveGatherInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ReceiveGatherCollection getReceiveGatherCollection() throws BOSException
    {
        try {
            return getController().getReceiveGatherCollection(getContext());
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
    public ReceiveGatherCollection getReceiveGatherCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getReceiveGatherCollection(getContext(), view);
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
    public ReceiveGatherCollection getReceiveGatherCollection(String oql) throws BOSException
    {
        try {
            return getController().getReceiveGatherCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *生成出纳收款单-User defined method
     *@param billID 汇总单ID
     */
    public void createReceivingBill(BOSUuid billID) throws BOSException, EASBizException
    {
        try {
            getController().createReceivingBill(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除出纳收款单-User defined method
     *@param info 出纳收款单
     */
    public void delReceivingToRev(IObjectValue info) throws BOSException, EASBizException
    {
        try {
            getController().delReceivingToRev(getContext(), info);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *出纳收款单生成凭证-User defined method
     *@param revList 出纳收款单ID集合
     */
    public void createVoucherToRev(ArrayList revList) throws BOSException, EASBizException
    {
        try {
            getController().createVoucherToRev(getContext(), revList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *出纳收款单删除凭证-User defined method
     *@param revList 出纳收款单ID集合
     */
    public void delVoucherToRev(ArrayList revList) throws BOSException, EASBizException
    {
        try {
            getController().delVoucherToRev(getContext(), revList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *出纳收款单删除凭证-User defined method
     *@param sourceBillPk 出纳收款单ID
     */
    public void delVoucherToRev(IObjectPK sourceBillPk) throws BOSException, EASBizException
    {
        try {
            getController().delVoucherToRev(getContext(), sourceBillPk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置审批中状态-User defined method
     *@param billId billId
     */
    public void setAudittingStatus(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setAudittingStatus(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置提交状态-User defined method
     *@param billId billId
     */
    public void setSubmitStatus(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setSubmitStatus(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}