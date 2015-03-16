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
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
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

public class PurchaseChange extends FDCBill implements IPurchaseChange
{
    public PurchaseChange()
    {
        super();
        registerInterface(IPurchaseChange.class, this);
    }
    public PurchaseChange(Context ctx)
    {
        super(ctx);
        registerInterface(IPurchaseChange.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DE662846");
    }
    private PurchaseChangeController getController() throws BOSException
    {
        return (PurchaseChangeController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public PurchaseChangeInfo getPurchaseChangeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseChangeInfo(getContext(), pk);
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
    public PurchaseChangeInfo getPurchaseChangeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseChangeInfo(getContext(), pk, selector);
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
    public PurchaseChangeInfo getPurchaseChangeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseChangeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PurchaseChangeCollection getPurchaseChangeCollection() throws BOSException
    {
        try {
            return getController().getPurchaseChangeCollection(getContext());
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
    public PurchaseChangeCollection getPurchaseChangeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPurchaseChangeCollection(getContext(), view);
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
    public PurchaseChangeCollection getPurchaseChangeCollection(String oql) throws BOSException
    {
        try {
            return getController().getPurchaseChangeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置已审批-User defined method
     *@param id id
     */
    public void setAudited(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().setAudited(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置审批中-User defined method
     *@param id id
     */
    public void setAuditing(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().setAuditing(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *設置單據狀態-User defined method
     *@param id id
     *@param state state
     */
    public void setState(BOSUuid id, FDCBillStateEnum state) throws BOSException, EASBizException
    {
        try {
            getController().setState(getContext(), id, state);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *認購單變更單反寫認購單-User defined method
     *@param id id
     */
    public void purchaseChangeToPurchase(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().purchaseChangeToPurchase(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}