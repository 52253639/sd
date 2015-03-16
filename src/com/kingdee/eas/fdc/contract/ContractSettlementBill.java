package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class ContractSettlementBill extends FDCBill implements IContractSettlementBill
{
    public ContractSettlementBill()
    {
        super();
        registerInterface(IContractSettlementBill.class, this);
    }
    public ContractSettlementBill(Context ctx)
    {
        super(ctx);
        registerInterface(IContractSettlementBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1D604E7D");
    }
    private ContractSettlementBillController getController() throws BOSException
    {
        return (ContractSettlementBillController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ContractSettlementBillInfo getContractSettlementBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractSettlementBillInfo(getContext(), pk);
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
    public ContractSettlementBillInfo getContractSettlementBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractSettlementBillInfo(getContext(), pk, selector);
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
    public ContractSettlementBillInfo getContractSettlementBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractSettlementBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ContractSettlementBillCollection getContractSettlementBillCollection() throws BOSException
    {
        try {
            return getController().getContractSettlementBillCollection(getContext());
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
    public ContractSettlementBillCollection getContractSettlementBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractSettlementBillCollection(getContext(), view);
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
    public ContractSettlementBillCollection getContractSettlementBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractSettlementBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *拆分-User defined method
     *@param pk pk
     */
    public void split(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().split(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *是否已经完全拆分-User defined method
     *@param pk pk
     *@return
     */
    public boolean isAllSplit(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().isAllSplit(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *自动删除拆分-User defined method
     *@param pk pk
     *@return
     */
    public boolean autoDelSplit(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().autoDelSplit(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *造价指标库录入-User defined method
     *@param pk pk
     */
    public void costIndex(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().costIndex(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}