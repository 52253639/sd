package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.fdc.sellhouse.app.*;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public class ReceiptInvoiceFacade extends AbstractBizCtrl implements IReceiptInvoiceFacade
{
    public ReceiptInvoiceFacade()
    {
        super();
        registerInterface(IReceiptInvoiceFacade.class, this);
    }
    public ReceiptInvoiceFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IReceiptInvoiceFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4B3B8E2A");
    }
    private ReceiptInvoiceFacadeController getController() throws BOSException
    {
        return (ReceiptInvoiceFacadeController)getBizController();
    }
    /**
     *取得操作记录-User defined method
     *@param id 收款单或发票的id
     *@return
     */
    public IObjectCollection getRecord(BOSUuid id) throws BOSException
    {
        try {
            return getController().getRecord(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新操作记录-User defined method
     *@param billType 单据
     *@param pk pk
     *@param recordType 操作类型
     *@param content 内容
     *@param description 备注
     */
    public void updateRecord(int billType, String pk, RecordTypeEnum recordType, String content, String description) throws BOSException
    {
        try {
            getController().updateRecord(getContext(), billType, pk, recordType, content, description);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新收据，并更新操作记录-User defined method
     *@param pk pk
     *@param fdcPK fdcPK
     *@param receiptID 收据ID
     *@param receiptNum 收据号
     *@param oldReceiptNum 原收据号
     *@param description 备注
     */
    public void updateReceipt(String pk, String fdcPK, String receiptID, String receiptNum, String oldReceiptNum, String description) throws BOSException
    {
        try {
            getController().updateReceipt(getContext(), pk, fdcPK, receiptID, receiptNum, oldReceiptNum, description);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *回收收据，并更新记录-User defined method
     *@param pk pk
     *@param fdcPK fdcPK
     *@param receiptNum 收据号
     *@param description 备注
     *@param paraMap 参数
     */
    public void retackReceipt(String pk, String fdcPK, String receiptNum, String description, Map paraMap) throws BOSException
    {
        try {
            getController().retackReceipt(getContext(), pk, fdcPK, receiptNum, description, paraMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新发票，并更新操作记录-User defined method
     *@param pk pk
     *@param oldInvoice 原发票号
     *@param chequePK 票据PK
     *@param newInvoice 新发票号
     *@param description 备注
     */
    public void updateInvoice(String pk, String oldInvoice, String chequePK, String newInvoice, String description) throws BOSException
    {
        try {
            getController().updateInvoice(getContext(), pk, oldInvoice, chequePK, newInvoice, description);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *通过更名单取得票据信息-User defined method
     *@param chgID 更名单ID
     *@return
     */
    public List getChequebyChgID(String chgID) throws BOSException
    {
        try {
            return getController().getChequebyChgID(getContext(), chgID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更名单更新票据-User defined method
     *@param param 参数集合
     */
    public void updateChequeForChg(List param) throws BOSException
    {
        try {
            getController().updateChequeForChg(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *通过退房单或换房取得票据信息-User defined method
     *@param sourceID pk
     *@param sourceType 源单类型
     *@return
     */
    public List getChequeForRoom(String sourceID, String sourceType) throws BOSException
    {
        try {
            return getController().getChequeForRoom(getContext(), sourceID, sourceType);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *退房或换房时回收票据-User defined method
     *@param param 参数集合
     */
    public void retakeChequeForRoom(List param) throws BOSException
    {
        try {
            getController().retakeChequeForRoom(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *回收发票-User defined method
     *@param pk pk
     *@param number 发票号
     *@param description 备注
     */
    public void retackInvoice(String pk, String number, String description) throws BOSException
    {
        try {
            getController().retackInvoice(getContext(), pk, number, description);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *收款单清除发票-User defined method
     *@param pk pk
     *@param isClearAll 是否清除所有同发票的收款单
     */
    public void clearInvoice(String pk, boolean isClearAll) throws BOSException
    {
        try {
            getController().clearInvoice(getContext(), pk, isClearAll);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据房间ID取得主认购单的主客户-User defined method
     *@param roomID 房间ID
     *@return
     */
    public FDCCustomerInfo getCustomerByRoom(String roomID) throws BOSException
    {
        try {
            return getController().getCustomerByRoom(getContext(), roomID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *批量回收收据，并且更新数据-User defined method
     *@param paramMap 参数
     *@param fdcPK fdcPK
     *@param receiptNum 收据号
     *@param description 备注
     */
    public void retackReceiptBatch(Map paramMap, String fdcPK, String receiptNum, String description) throws BOSException
    {
        try {
            getController().retackReceiptBatch(getContext(), paramMap, fdcPK, receiptNum, description);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}