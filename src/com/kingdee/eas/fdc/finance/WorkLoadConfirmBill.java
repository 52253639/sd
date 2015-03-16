package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.math.BigDecimal;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

public class WorkLoadConfirmBill extends FDCBill implements IWorkLoadConfirmBill
{
    public WorkLoadConfirmBill()
    {
        super();
        registerInterface(IWorkLoadConfirmBill.class, this);
    }
    public WorkLoadConfirmBill(Context ctx)
    {
        super(ctx);
        registerInterface(IWorkLoadConfirmBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E4A3CD61");
    }
    private WorkLoadConfirmBillController getController() throws BOSException
    {
        return (WorkLoadConfirmBillController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public WorkLoadConfirmBillInfo getWorkLoadConfirmBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getWorkLoadConfirmBillInfo(getContext(), pk);
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
    public WorkLoadConfirmBillInfo getWorkLoadConfirmBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getWorkLoadConfirmBillInfo(getContext(), pk, selector);
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
    public WorkLoadConfirmBillInfo getWorkLoadConfirmBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getWorkLoadConfirmBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public WorkLoadConfirmBillCollection getWorkLoadConfirmBillCollection() throws BOSException
    {
        try {
            return getController().getWorkLoadConfirmBillCollection(getContext());
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
    public WorkLoadConfirmBillCollection getWorkLoadConfirmBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getWorkLoadConfirmBillCollection(getContext(), view);
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
    public WorkLoadConfirmBillCollection getWorkLoadConfirmBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getWorkLoadConfirmBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *累计工程量-User defined method
     *@param contractId 合同id
     *@return
     */
    public BigDecimal getWorkLoad(String contractId) throws BOSException, EASBizException
    {
        try {
            return getController().getWorkLoad(getContext(), contractId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *累计工程量-User defined method
     *@param contractIds 合同ids
     *@return
     */
    public Map getWorkLoad(Set contractIds) throws BOSException, EASBizException
    {
        try {
            return getController().getWorkLoad(getContext(), contractIds);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *累计工程量（不计自身数据）-User defined method
     *@param contractId 合同id
     *@param workLoadId 确认工程量单据id
     *@return
     */
    public BigDecimal getWorkLoadWithoutId(String contractId, String workLoadId) throws BOSException, EASBizException
    {
        try {
            return getController().getWorkLoadWithoutId(getContext(), contractId, workLoadId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *关联工程量填报单-User defined method
     *@param prjFillBillIds 合同IDs
     *@return
     */
    public Map getConPrjFillBill(Set prjFillBillIds) throws BOSException, EASBizException
    {
        try {
            return getController().getConPrjFillBill(getContext(), prjFillBillIds);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取合同关联的工程量填报单-User defined method
     *@param paramMap 参数
     *@return
     */
    public Map getRefWorkAmount(Map paramMap) throws BOSException, EASBizException
    {
        try {
            return getController().getRefWorkAmount(getContext(), paramMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *保存并保存被引用的工程量填报分录-User defined method
     *@param model model
     *@param refWorkAmountList refWorkAmountList
     *@param willRemoveList willRemoveList
     */
    public void save(IObjectValue model, List refWorkAmountList, List willRemoveList) throws BOSException, EASBizException
    {
        try {
            getController().save(getContext(), model, refWorkAmountList, willRemoveList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}