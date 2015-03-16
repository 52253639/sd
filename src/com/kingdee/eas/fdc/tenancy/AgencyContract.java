package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class AgencyContract extends TenBillBase implements IAgencyContract
{
    public AgencyContract()
    {
        super();
        registerInterface(IAgencyContract.class, this);
    }
    public AgencyContract(Context ctx)
    {
        super(ctx);
        registerInterface(IAgencyContract.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("93E8D220");
    }
    private AgencyContractController getController() throws BOSException
    {
        return (AgencyContractController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public AgencyContractInfo getAgencyContractInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAgencyContractInfo(getContext(), pk);
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
    public AgencyContractInfo getAgencyContractInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAgencyContractInfo(getContext(), pk, selector);
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
    public AgencyContractInfo getAgencyContractInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAgencyContractInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public AgencyContractCollection getAgencyContractCollection() throws BOSException
    {
        try {
            return getController().getAgencyContractCollection(getContext());
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
    public AgencyContractCollection getAgencyContractCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAgencyContractCollection(getContext(), view);
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
    public AgencyContractCollection getAgencyContractCollection(String oql) throws BOSException
    {
        try {
            return getController().getAgencyContractCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}