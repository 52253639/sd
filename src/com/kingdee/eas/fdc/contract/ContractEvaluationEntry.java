package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class ContractEvaluationEntry extends CoreBillEntryBase implements IContractEvaluationEntry
{
    public ContractEvaluationEntry()
    {
        super();
        registerInterface(IContractEvaluationEntry.class, this);
    }
    public ContractEvaluationEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IContractEvaluationEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F63B8289");
    }
    private ContractEvaluationEntryController getController() throws BOSException
    {
        return (ContractEvaluationEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ContractEvaluationEntryInfo getContractEvaluationEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractEvaluationEntryInfo(getContext(), pk);
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
    public ContractEvaluationEntryInfo getContractEvaluationEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractEvaluationEntryInfo(getContext(), pk, selector);
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
    public ContractEvaluationEntryInfo getContractEvaluationEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractEvaluationEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ContractEvaluationEntryCollection getContractEvaluationEntryCollection() throws BOSException
    {
        try {
            return getController().getContractEvaluationEntryCollection(getContext());
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
    public ContractEvaluationEntryCollection getContractEvaluationEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractEvaluationEntryCollection(getContext(), view);
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
    public ContractEvaluationEntryCollection getContractEvaluationEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractEvaluationEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}