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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.contract.app.*;

public class ContractChangeBill extends FDCBill implements IContractChangeBill
{
    public ContractChangeBill()
    {
        super();
        registerInterface(IContractChangeBill.class, this);
    }
    public ContractChangeBill(Context ctx)
    {
        super(ctx);
        registerInterface(IContractChangeBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F2141C04");
    }
    private ContractChangeBillController getController() throws BOSException
    {
        return (ContractChangeBillController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ContractChangeBillCollection getContractChangeBillCollection() throws BOSException
    {
        try {
            return getController().getContractChangeBillCollection(getContext());
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
    public ContractChangeBillCollection getContractChangeBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractChangeBillCollection(getContext(), view);
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
    public ContractChangeBillCollection getContractChangeBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractChangeBillCollection(getContext(), oql);
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
    public ContractChangeBillInfo getContractChangeBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractChangeBillInfo(getContext(), pk);
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
    public ContractChangeBillInfo getContractChangeBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractChangeBillInfo(getContext(), pk, selector);
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
    public ContractChangeBillInfo getContractChangeBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractChangeBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *下发-User defined method
     *@param idSet id集合
     */
    public void disPatch(IObjectPK[] idSet) throws BOSException, EASBizException
    {
        try {
            getController().disPatch(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *签证-User defined method
     *@param idSet id集合
     *@param cols cols
     *@return
     */
    public boolean visa(IObjectPK[] idSet, IObjectCollection cols) throws BOSException, EASBizException
    {
        try {
            return getController().visa(getContext(), idSet, cols);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *结算-User defined method
     *@param pk pk
     *@param changeBill changeBill
     */
    public void settle(IObjectPK pk, IObjectValue changeBill) throws BOSException, EASBizException
    {
        try {
            getController().settle(getContext(), pk, changeBill);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取消下发-User defined method
     *@param pk id
     */
    public void unDispatch(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().unDispatch(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取消签证-User defined method
     *@param pk pk
     */
    public void unVisa(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().unVisa(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *为了在点击了变更指令单结算UI上的"提交"按钮之后可以走工作流需要的方法-User defined method
     *@param model model
     */
    public void submitForWF(ContractChangeBillInfo model) throws BOSException, EASBizException
    {
        try {
            getController().submitForWF(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *变更指令单结算之后设置为审批中状态-User defined method
     *@param pk pk
     */
    public void setSettAuttingForWF(BOSUuid pk) throws BOSException, EASBizException
    {
        try {
            getController().setSettAuttingForWF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *变更指令单结算之后设置为已审批状态-User defined method
     *@param pk pk
     */
    public void setSettAuditedForWF(BOSUuid pk) throws BOSException, EASBizException
    {
        try {
            getController().setSettAuditedForWF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *执行情况确认-User defined method
     *@param billId 内码
     */
    public void confirmExecute(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().confirmExecute(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *之前中渝提单要把变更指令单的结算做一个完整的审批工作流,但是现在为了解决该提单"合同执行部分无法区分设计变更与工程签证业务"问题又要将变更结算作为流程中的一个节点来做,于是又增加了这个方法-User defined method
     *@param billId 内码
     */
    public void changeSettle(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().changeSettle(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *成本类变更拆分-User defined method
     *@param billId 内码
     */
    public void costChangeSplit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().costChangeSplit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *非成本类变更拆分-User defined method
     *@param billId 内码
     */
    public void nonCostChangeSplit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().nonCostChangeSplit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}