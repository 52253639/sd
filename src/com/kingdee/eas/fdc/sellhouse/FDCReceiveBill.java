package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.ArrayList;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;

public class FDCReceiveBill extends DataBase implements IFDCReceiveBill
{
    public FDCReceiveBill()
    {
        super();
        registerInterface(IFDCReceiveBill.class, this);
    }
    public FDCReceiveBill(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCReceiveBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9412AC80");
    }
    private FDCReceiveBillController getController() throws BOSException
    {
        return (FDCReceiveBillController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCReceiveBillInfo getFDCReceiveBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCReceiveBillInfo(getContext(), pk);
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
    public FDCReceiveBillInfo getFDCReceiveBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCReceiveBillInfo(getContext(), pk, selector);
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
    public FDCReceiveBillInfo getFDCReceiveBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCReceiveBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCReceiveBillCollection getFDCReceiveBillCollection() throws BOSException
    {
        try {
            return getController().getFDCReceiveBillCollection(getContext());
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
    public FDCReceiveBillCollection getFDCReceiveBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCReceiveBillCollection(getContext(), view);
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
    public FDCReceiveBillCollection getFDCReceiveBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCReceiveBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据出纳收款单提交-User defined method
     *@param casRev 出纳收款单
     *@return
     */
    public String submitByCasRev(ReceivingBillInfo casRev) throws BOSException, EASBizException
    {
        try {
            return getController().submitByCasRev(getContext(), casRev);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据出纳收款单提交-User defined method
     *@param casRevColl casRevColl
     */
    public void submitByCasRevColl(ReceivingBillCollection casRevColl) throws BOSException, EASBizException
    {
        try {
            getController().submitByCasRevColl(getContext(), casRevColl);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *生成临时应收单(在提交时)-User defined method
     *@param listIds listIds
     */
    public void addTemporaBill(ArrayList listIds) throws BOSException, EASBizException
    {
        try {
            getController().addTemporaBill(getContext(), listIds);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取得本张收款单的打印次数-User defined method
     *@param billID 单据ID
     *@return
     */
    public int getPrintCount(String billID) throws BOSException, EASBizException
    {
        try {
            return getController().getPrintCount(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新单据打印次数-User defined method
     *@param billID 单据ID
     *@param printCount 打印次数
     */
    public void updatePrintCount(String billID, int printCount) throws BOSException, EASBizException
    {
        try {
            getController().updatePrintCount(getContext(), billID, printCount);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *生成临时应收单(在提交时)-User defined method
     *@param listBills 提交时先生成的临时应收单（几张）
     *@param billInfo 零星收款单的INFO类
     */
    public void addTemporaBill(ArrayList listBills, IObjectValue billInfo) throws BOSException, EASBizException
    {
        try {
            getController().addTemporaBill(getContext(), listBills, billInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}