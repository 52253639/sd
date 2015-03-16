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

public class ContractPayPlanEntry extends CoreBillEntryBase implements IContractPayPlanEntry
{
    public ContractPayPlanEntry()
    {
        super();
        registerInterface(IContractPayPlanEntry.class, this);
    }
    public ContractPayPlanEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IContractPayPlanEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("13073BEE");
    }
    private ContractPayPlanEntryController getController() throws BOSException
    {
        return (ContractPayPlanEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ContractPayPlanEntryInfo getContractPayPlanEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractPayPlanEntryInfo(getContext(), pk);
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
    public ContractPayPlanEntryInfo getContractPayPlanEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractPayPlanEntryInfo(getContext(), pk, selector);
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
    public ContractPayPlanEntryInfo getContractPayPlanEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractPayPlanEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ContractPayPlanEntryCollection getContractPayPlanEntryCollection() throws BOSException
    {
        try {
            return getController().getContractPayPlanEntryCollection(getContext());
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
    public ContractPayPlanEntryCollection getContractPayPlanEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractPayPlanEntryCollection(getContext(), view);
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
    public ContractPayPlanEntryCollection getContractPayPlanEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractPayPlanEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}