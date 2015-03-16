package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public class FDCDepConPayPlanBill extends FDCBill implements IFDCDepConPayPlanBill
{
    public FDCDepConPayPlanBill()
    {
        super();
        registerInterface(IFDCDepConPayPlanBill.class, this);
    }
    public FDCDepConPayPlanBill(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCDepConPayPlanBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F288954F");
    }
    private FDCDepConPayPlanBillController getController() throws BOSException
    {
        return (FDCDepConPayPlanBillController)getBizController();
    }
    /**
     *批量取数-User defined method
     *@param param 参数
     *@return
     */
    public Map fetchData(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().fetchData(getContext(), param);
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
    public FDCDepConPayPlanBillInfo getFDCDepConPayPlanBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCDepConPayPlanBillInfo(getContext(), pk, selector);
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
    public FDCDepConPayPlanBillCollection getFDCDepConPayPlanBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCDepConPayPlanBillCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *统计支付数据-User defined method
     *@param valuse 参数
     *@return
     */
    public Map statisticsPay(Map valuse) throws BOSException
    {
        try {
            return getController().statisticsPay(getContext(), valuse);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *统计计划应付-User defined method
     *@param value 参数
     *@return
     */
    public Map getPlanPay(Map value) throws BOSException
    {
        try {
            return getController().getPlanPay(getContext(), value);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *上报-User defined method
     *@param ids ids
     */
    public void publish(List ids) throws BOSException, EASBizException
    {
        try {
            getController().publish(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *打回-User defined method
     *@param ids ids
     */
    public void back(List ids) throws BOSException, EASBizException
    {
        try {
            getController().back(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *上报-User defined method
     *@param billid billid
     */
    public void setPublish(BOSUuid billid) throws BOSException, EASBizException
    {
        try {
            getController().setPublish(getContext(), billid);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *打回-User defined method
     *@param billid billid
     */
    public void setBack(BOSUuid billid) throws BOSException, EASBizException
    {
        try {
            getController().setBack(getContext(), billid);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据分录ID打回整单-User defined method
     *@param entryID entryID
     */
    public void setBackByEntryID(String entryID) throws BOSException
    {
        try {
            getController().setBackByEntryID(getContext(), entryID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}