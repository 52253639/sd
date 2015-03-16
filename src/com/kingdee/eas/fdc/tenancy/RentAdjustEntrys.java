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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class RentAdjustEntrys extends DataBase implements IRentAdjustEntrys
{
    public RentAdjustEntrys()
    {
        super();
        registerInterface(IRentAdjustEntrys.class, this);
    }
    public RentAdjustEntrys(Context ctx)
    {
        super(ctx);
        registerInterface(IRentAdjustEntrys.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3B9EE432");
    }
    private RentAdjustEntrysController getController() throws BOSException
    {
        return (RentAdjustEntrysController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public RentAdjustEntrysInfo getRentAdjustEntrysInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRentAdjustEntrysInfo(getContext(), pk);
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
    public RentAdjustEntrysInfo getRentAdjustEntrysInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRentAdjustEntrysInfo(getContext(), pk, selector);
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
    public RentAdjustEntrysInfo getRentAdjustEntrysInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRentAdjustEntrysInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RentAdjustEntrysCollection getRentAdjustEntrysCollection() throws BOSException
    {
        try {
            return getController().getRentAdjustEntrysCollection(getContext());
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
    public RentAdjustEntrysCollection getRentAdjustEntrysCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRentAdjustEntrysCollection(getContext(), view);
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
    public RentAdjustEntrysCollection getRentAdjustEntrysCollection(String oql) throws BOSException
    {
        try {
            return getController().getRentAdjustEntrysCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}