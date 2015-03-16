package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillEntryBase;
import com.kingdee.eas.framework.IBillEntryBase;

public class AgencyPerson extends BillEntryBase implements IAgencyPerson
{
    public AgencyPerson()
    {
        super();
        registerInterface(IAgencyPerson.class, this);
    }
    public AgencyPerson(Context ctx)
    {
        super(ctx);
        registerInterface(IAgencyPerson.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("531047E3");
    }
    private AgencyPersonController getController() throws BOSException
    {
        return (AgencyPersonController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public AgencyPersonInfo getAgencyPersonInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAgencyPersonInfo(getContext(), pk);
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
    public AgencyPersonInfo getAgencyPersonInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAgencyPersonInfo(getContext(), pk, selector);
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
    public AgencyPersonInfo getAgencyPersonInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAgencyPersonInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public AgencyPersonCollection getAgencyPersonCollection() throws BOSException
    {
        try {
            return getController().getAgencyPersonCollection(getContext());
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
    public AgencyPersonCollection getAgencyPersonCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAgencyPersonCollection(getContext(), view);
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
    public AgencyPersonCollection getAgencyPersonCollection(String oql) throws BOSException
    {
        try {
            return getController().getAgencyPersonCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}