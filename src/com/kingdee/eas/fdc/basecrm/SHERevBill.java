package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basecrm.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;

public class SHERevBill extends CoreBillBase implements ISHERevBill
{
    public SHERevBill()
    {
        super();
        registerInterface(ISHERevBill.class, this);
    }
    public SHERevBill(Context ctx)
    {
        super(ctx);
        registerInterface(ISHERevBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DFC518D6");
    }
    private SHERevBillController getController() throws BOSException
    {
        return (SHERevBillController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SHERevBillCollection getSHERevBillCollection() throws BOSException
    {
        try {
            return getController().getSHERevBillCollection(getContext());
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
    public SHERevBillCollection getSHERevBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSHERevBillCollection(getContext(), view);
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
    public SHERevBillCollection getSHERevBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getSHERevBillCollection(getContext(), oql);
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
    public SHERevBillInfo getSHERevBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSHERevBillInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public SHERevBillInfo getSHERevBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSHERevBillInfo(getContext(), pk, selector);
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
    public SHERevBillInfo getSHERevBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSHERevBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *批量审批-User defined method
     *@param billId 单据id
     */
    public void audit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *批量审批-User defined method
     *@param idList id列表
     */
    public void audit(List idList) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反射批-User defined method
     *@param billId 单据id
     */
    public void unAudit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反射批-User defined method
     *@param idList id列表
     */
    public void unAudit(List idList) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), idList);
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
    /**
     *当交易明细变动时需要处理的事件，更新对冲关系-User defined method
     *@param oldTransInfo 一定要包含业务总览分录
     *@param newTransInfo 一定要包含业务总览中的信息
     */
    public void whenTransEntryChange(TransactionInfo oldTransInfo, TransactionInfo newTransInfo) throws BOSException, EASBizException
    {
        try {
            getController().whenTransEntryChange(getContext(), oldTransInfo, newTransInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *当交易客户完全变化时， 1.删除本交易的全部对冲关系 2.更新本交易的说有收款单为预收款(也就是不对应交易的收款)-User defined method
     *@param transInfo 交易对象
     */
    public void whenTransCustChange(TransactionInfo transInfo) throws BOSException, EASBizException
    {
        try {
            getController().whenTransCustChange(getContext(), transInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}