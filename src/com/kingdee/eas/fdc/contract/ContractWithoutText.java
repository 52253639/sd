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
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.contract.app.*;

public class ContractWithoutText extends FDCBill implements IContractWithoutText
{
    public ContractWithoutText()
    {
        super();
        registerInterface(IContractWithoutText.class, this);
    }
    public ContractWithoutText(Context ctx)
    {
        super(ctx);
        registerInterface(IContractWithoutText.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3D9A5388");
    }
    private ContractWithoutTextController getController() throws BOSException
    {
        return (ContractWithoutTextController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ContractWithoutTextCollection getContractWithoutTextCollection() throws BOSException
    {
        try {
            return getController().getContractWithoutTextCollection(getContext());
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
    public ContractWithoutTextCollection getContractWithoutTextCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractWithoutTextCollection(getContext(), view);
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
    public ContractWithoutTextCollection getContractWithoutTextCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractWithoutTextCollection(getContext(), oql);
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
    public ContractWithoutTextInfo getContractWithoutTextInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractWithoutTextInfo(getContext(), pk);
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
    public ContractWithoutTextInfo getContractWithoutTextInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractWithoutTextInfo(getContext(), pk, selector);
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
    public ContractWithoutTextInfo getContractWithoutTextInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractWithoutTextInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取合同类型编码-User defined method
     *@param id id
     *@return
     */
    public String getContractTypeNumber(IObjectPK id) throws BOSException, EASBizException
    {
        try {
            return getController().getContractTypeNumber(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取用款部门-User defined method
     *@param id id
     *@return
     */
    public String getUseDepartment(String id) throws BOSException, EASBizException
    {
        try {
            return getController().getUseDepartment(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取付款类型-User defined method
     *@param id id
     *@return
     */
    public String getPaymentType(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            return getController().getPaymentType(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *关联历史合同-User defined method
     *@param billId billId
     *@param programming programming
     */
    public void synUpdateProgramming(String billId, ProgrammingContractInfo programming) throws BOSException, EASBizException
    {
        try {
            getController().synUpdateProgramming(getContext(), billId, programming);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *关联返还历史合同-User defined method
     *@param billId billId
     *@param programming programming
     */
    public void synReUpdateProgramming(String billId, IObjectValue programming) throws BOSException, EASBizException
    {
        try {
            getController().synReUpdateProgramming(getContext(), billId, programming);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getNoPValue-User defined method
     *@param pk pk
     *@param sel sel
     *@return
     */
    public IObjectValue getNoPValue(IObjectPK pk, SelectorItemCollection sel) throws BOSException
    {
        try {
            return getController().getNoPValue(getContext(), pk, sel);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}