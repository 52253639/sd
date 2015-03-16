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
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class SinCustomerEntrys extends CoreBillBase implements ISinCustomerEntrys
{
    public SinCustomerEntrys()
    {
        super();
        registerInterface(ISinCustomerEntrys.class, this);
    }
    public SinCustomerEntrys(Context ctx)
    {
        super(ctx);
        registerInterface(ISinCustomerEntrys.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4759782E");
    }
    private SinCustomerEntrysController getController() throws BOSException
    {
        return (SinCustomerEntrysController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public SinCustomerEntrysInfo getSinCustomerEntrysInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSinCustomerEntrysInfo(getContext(), pk);
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
    public SinCustomerEntrysInfo getSinCustomerEntrysInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSinCustomerEntrysInfo(getContext(), pk, selector);
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
    public SinCustomerEntrysInfo getSinCustomerEntrysInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSinCustomerEntrysInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SinCustomerEntrysCollection getSinCustomerEntrysCollection() throws BOSException
    {
        try {
            return getController().getSinCustomerEntrysCollection(getContext());
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
    public SinCustomerEntrysCollection getSinCustomerEntrysCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSinCustomerEntrysCollection(getContext(), view);
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
    public SinCustomerEntrysCollection getSinCustomerEntrysCollection(String oql) throws BOSException
    {
        try {
            return getController().getSinCustomerEntrysCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}