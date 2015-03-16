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

public class Purchase extends FDCBill implements IPurchase
{
    public Purchase()
    {
        super();
        registerInterface(IPurchase.class, this);
    }
    public Purchase(Context ctx)
    {
        super(ctx);
        registerInterface(IPurchase.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D2CB60DC");
    }
    private PurchaseController getController() throws BOSException
    {
        return (PurchaseController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PurchaseInfo getPurchaseInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseInfo(getContext(), pk);
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
    public PurchaseInfo getPurchaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseInfo(getContext(), pk, selector);
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
    public PurchaseInfo getPurchaseInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PurchaseCollection getPurchaseCollection() throws BOSException
    {
        try {
            return getController().getPurchaseCollection(getContext());
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
    public PurchaseCollection getPurchaseCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPurchaseCollection(getContext(), view);
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
    public PurchaseCollection getPurchaseCollection(String oql) throws BOSException
    {
        try {
            return getController().getPurchaseCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *预定复核-User defined method
     *@param pks 复核单
     */
    public void checkPrePurchase(IObjectPK[] pks) throws BOSException, EASBizException
    {
        try {
            getController().checkPrePurchase(getContext(), pks);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *预定反复核-User defined method
     *@param pks 反复核
     */
    public void uncheckPrePurchase(IObjectPK[] pks) throws BOSException, EASBizException
    {
        try {
            getController().uncheckPrePurchase(getContext(), pks);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *提交预订单-User defined method
     *@param model 值对象
     */
    public void submitPrePurchase(PurchaseInfo model) throws BOSException
    {
        try {
            getController().submitPrePurchase(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审核预订单-User defined method
     *@param billId billId
     */
    public void auditPrePurchase(BOSUuid billId) throws BOSException
    {
        try {
            getController().auditPrePurchase(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *收款-User defined method
     *@param billId billId
     */
    public void receiveBill(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().receiveBill(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *佣金分录数据升级-User defined method
     */
    public void purDistillUpdate() throws BOSException
    {
        try {
            getController().purDistillUpdate(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *认购单增加营销单元-User defined method
     */
    public void purAddMarket() throws BOSException, EASBizException
    {
        try {
            getController().purAddMarket(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *直接签约保存-User defined method
     *@param model 值对象
     */
    public void immediacySave(PurchaseInfo model) throws BOSException, EASBizException
    {
        try {
            getController().immediacySave(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}