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

public class RentFreeBill extends TenBillBase implements IRentFreeBill
{
    public RentFreeBill()
    {
        super();
        registerInterface(IRentFreeBill.class, this);
    }
    public RentFreeBill(Context ctx)
    {
        super(ctx);
        registerInterface(IRentFreeBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A9348B15");
    }
    private RentFreeBillController getController() throws BOSException
    {
        return (RentFreeBillController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public RentFreeBillInfo getRentFreeBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRentFreeBillInfo(getContext(), pk);
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
    public RentFreeBillInfo getRentFreeBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRentFreeBillInfo(getContext(), pk, selector);
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
    public RentFreeBillInfo getRentFreeBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRentFreeBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RentFreeBillCollection getRentFreeBillCollection() throws BOSException
    {
        try {
            return getController().getRentFreeBillCollection(getContext());
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
    public RentFreeBillCollection getRentFreeBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRentFreeBillCollection(getContext(), view);
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
    public RentFreeBillCollection getRentFreeBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getRentFreeBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}