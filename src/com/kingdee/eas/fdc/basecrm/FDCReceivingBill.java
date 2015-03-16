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
import java.util.Map;
import com.kingdee.eas.fdc.basecrm.app.*;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASAppException;
import com.kingdee.bos.framework.*;
import java.util.ArrayList;
import com.kingdee.bos.util.BOSUuid;

public class FDCReceivingBill extends FDCBill implements IFDCReceivingBill
{
    public FDCReceivingBill()
    {
        super();
        registerInterface(IFDCReceivingBill.class, this);
    }
    public FDCReceivingBill(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCReceivingBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F12182FE");
    }
    private FDCReceivingBillController getController() throws BOSException
    {
        return (FDCReceivingBillController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public FDCReceivingBillInfo getFDCReceivingBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCReceivingBillInfo(getContext(), pk);
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
    public FDCReceivingBillInfo getFDCReceivingBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCReceivingBillInfo(getContext(), pk, selector);
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
    public FDCReceivingBillInfo getFDCReceivingBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCReceivingBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public FDCReceivingBillCollection getFDCReceivingBillCollection() throws BOSException
    {
        try {
            return getController().getFDCReceivingBillCollection(getContext());
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
    public FDCReceivingBillCollection getFDCReceivingBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCReceivingBillCollection(getContext(), view);
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
    public FDCReceivingBillCollection getFDCReceivingBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCReceivingBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *提交收款-User defined method
     *@param rev 收款单
     *@param handleClazzName 处理类名
     *@return
     */
    public String submitRev(IObjectValue rev, String handleClazzName) throws BOSException, EASBizException
    {
        try {
            return getController().submitRev(getContext(), rev, handleClazzName);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *单个收款-User defined method
     *@param recidList 收款单ID集合
     */
    public void receive(ArrayList recidList) throws BOSException, EASBizException
    {
        try {
            getController().receive(getContext(), recidList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除-User defined method
     *@param fdcReceivingID 收款单ID
     *@param handleClazzName 处理类名
     */
    public void delete(BOSUuid fdcReceivingID, String handleClazzName) throws BOSException, EASBizException
    {
        try {
            getController().delete(getContext(), fdcReceivingID, handleClazzName);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *维护收款单状态-User defined method
     *@param recidList 收款单ID集合
     */
    public void canceReceive(ArrayList recidList) throws BOSException, EASBizException
    {
        try {
            getController().canceReceive(getContext(), recidList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *调整-User defined method
     *@param billId 单据id
     *@return
     */
    public Map adjust(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            return getController().adjust(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *生成出纳收款单-User defined method
     *@param idList id集合
     *@param isCreate 是否已创建
     */
    public void createCashBill(ArrayList idList, boolean isCreate) throws BOSException, EASAppException
    {
        try {
            getController().createCashBill(getContext(), idList, isCreate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *调整收款单（可部分金额调整）-User defined method
     *@param billId 收款单Id
     *@param map 参数
     */
    public void adjustReceiveBill(BOSUuid billId, Map map) throws BOSException, EASBizException
    {
        try {
            getController().adjustReceiveBill(getContext(), billId, map);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *单个收款-User defined method
     *@param BOSUuid BOSUuid
     */
    public void receive(BOSUuid BOSUuid) throws BOSException, EASBizException
    {
        try {
            getController().receive(getContext(), BOSUuid);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}